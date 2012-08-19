/**
 *
 */
package org.elixirian.jsonstatham.core.convertible;

import static org.elixirian.kommonlee.util.MessageFormatter.format;

import org.elixirian.jsonstatham.core.util.JsonUtil;
import org.elixirian.jsonstatham.exception.JsonStathamException;

/**
 * <pre>
 *     ___  _____                                _____
 *    /   \/    /_________  ___ ____ __ ______  /    /   ______  ______
 *   /        / /  ___ \  \/  //___// //     / /    /   /  ___ \/  ___ \
 *  /        \ /  _____/\    //   //   __   / /    /___/  _____/  _____/
 * /____/\____\\_____/   \__//___//___/ /__/ /________/\_____/ \_____/
 * </pre>
 *
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2012-08-18)
 */
public abstract class AbstractJsonScanner implements JsonScanner
{
	private final String jsonString;
	private final int length;
	private int index = 0;
	private char previousChar;
	private boolean usePreviousChar;
	private int positionInLine;
	private int currentLine;
	private boolean ended;

	public AbstractJsonScanner(final String jsonString)
	{
		this.jsonString = jsonString;
		this.length = jsonString.length();
		this.positionInLine = 1;
		this.currentLine = 1;
	}

	@Override
	public String getJsonString()
	{
		return jsonString;
	}

	public char nextChar()
	{
		return nextChar0();
	}

	private char nextChar0()
	{
		char c;
		if (usePreviousChar)
		{
			usePreviousChar = false;
			c = previousChar;
		}
		else
		{
			if (length >= index)
			{
				c = 0;
				ended = true;
			}
			else
			{
				c = jsonString.charAt(index);
			}
		}

		if ('\r' == previousChar)
		{
			currentLine++;
			positionInLine = '\n' == c ? 0 : 1;
		}
		else if ('\n' == previousChar)
		{
			currentLine++;
			positionInLine = 0;
		}
		else
		{
			positionInLine++;
		}
		previousChar = c;
		index++;
		return previousChar;
	}

	private String nextChar0(final int howMany)
	{
		if (0 == howMany)
		{
			return "";
		}
		final char[] chars = new char[howMany];
		int index = 0;
		while (index < howMany)
		{
			chars[index] = nextChar0();
			if (ended)
			{
				throw new JsonStathamException(format(
						"Getting next %s char%s failed.[param: index howMany: %s][index: %s, length: %s, ended: %s]", howMany,
						1 < howMany ? "s" : "", howMany, index, length, ended));
			}
			index++;
		}
		return new String(chars);
	}

	public char nextNonWhiteSpaceChar()
	{
		return nextNonWhiteSpaceChar0();
	}

	private char nextNonWhiteSpaceChar0()
	{
		for (;;)
		{
			final char c = nextChar0();
			if (0 == c || ' ' < c)
			{
				return c;
			}
		}
	}

	public String nextStringUntilQuoteEnded(final char quoteChar)
	{
		final StringBuilder stringBuilder = new StringBuilder();
		for (;;)
		{
			char c = nextChar0();
			switch (c)
			{
				case 0:
				case '\n':
				case '\r':
					throw new JsonStathamException(
							"Invalid JSON String. It is not terminated properly.[expected: some char not one of 0, \\n, \\r][actual: "
									+ c + "]");
				case '\\':
					c = nextChar0();
					switch (c)
					{
						case 'n':
							stringBuilder.append("\n");
							break;
						case 'r':
							stringBuilder.append('\r');
							break;
						case 't':
							stringBuilder.append('\t');
							break;
						case 'f':
							stringBuilder.append('\f');
							break;
						case 'b':
							stringBuilder.append('\b');
							break;
						case 'u':
							c = (char) Integer.parseInt(nextChar0(4), 16);
							stringBuilder.append(c);
							break;
						case '"':
						case '\'':
						case '\\':
						case '/':
							stringBuilder.append(c);
							break;
						default:
							throw new JsonStathamException("Invalid escaping. [found: \\" + c + "]");
					}
					break;
				default:
					if (quoteChar == c)
					{
						return stringBuilder.toString();
					}
					stringBuilder.append(c);
			}
		}
	}

	public Object nextValue()
	{
		char c = nextNonWhiteSpaceChar0();
		if (c == '"' | c == '\'')
		{
			/* It looks like a String value so get the value inside quotes. */
			return nextStringUntilQuoteEnded(c);
		}
		if (c == '{')
		{
			return newJsonObjectConvertible(this);
		}
		if (c == '[')
		{
			return newJsonArrayConvertible(this);
		}
		final StringBuilder stringBuilder = new StringBuilder();
		while (' ' <= c && 0 > ":,}]\\/\"{[;=#".indexOf(c))
		{
			stringBuilder.append(c);
			c = nextChar0();
		}
		backToPrevious0();

		final String value = stringBuilder.toString()
				.trim();
		if (0 == value.length())
		{
			throw new JsonStathamException("No value is found!");
		}
		return JsonUtil.fromStringToValueIfPossible(value);
	}

	private void backToPrevious0()
	{
		if (usePreviousChar)
		{
			throw new JsonStathamException(
					"It cannot move back to the second previous char. It can only move back to one previous char. [usePreviousChar: "
							+ usePreviousChar + "]");
		}
		if (0 >= index)
		{
			throw new JsonStathamException("It cannot move to anywhere before the first char. [index: " + index + "]");
		}
		index--;
		positionInLine--;
		usePreviousChar = true;
		ended = false;
	}

	protected abstract JsonObjectConvertible newJsonObjectConvertible(JsonScanner jsonScanner);

	protected abstract JsonArray newJsonArrayConvertible(final JsonScanner jsonScanner);
}

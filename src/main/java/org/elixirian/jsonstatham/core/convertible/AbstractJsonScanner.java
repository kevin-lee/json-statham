/**
 * This project is licensed under the Apache License, Version 2.0
 * if the following condition is met:
 * (otherwise it cannot be used by anyone but the author, Kevin, only)
 *
 * The original KommonLee project is owned by Lee, Seong Hyun (Kevin).
 *
 * -What does it mean to you?
 * Nothing, unless you want to take the ownership of
 * "the original project" (not yours or forked & modified one).
 * You are free to use it for both non-commercial and commercial projects
 * and free to modify it as the Apache License allows.
 *
 * -So why is this condition necessary?
 * It is only to protect the original project (See the case of Java).
 *
 *
 * Copyright 2009 Lee, Seong Hyun (Kevin)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.elixirian.jsonstatham.core.convertible;

import static org.elixirian.kommonlee.util.MessageFormatter.format;
import static org.elixirian.kommonlee.util.Objects.toStringBuilder;

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
	private boolean previousCharRequiredAlready = false;
	private int previousPositionInLine = 0;
	private int currentLine = 1;
	private boolean ended;

	public AbstractJsonScanner(final String jsonString)
	{
		this.jsonString = jsonString;
		this.length = jsonString.length();
	}

	@Override
	public String getJsonString()
	{
		return jsonString;
	}

	@Override
	public char nextChar()
	{
		return nextChar0();
	}

	private char nextChar0()
	{
		char c;
		if (previousCharRequiredAlready)
		{
			previousCharRequiredAlready = false;
			c = jsonString.charAt(index);
		}
		else
		{
			if (length <= index)
			{
				c = 0;
				ended = true;
			}
			else
			{
				c = jsonString.charAt(index);
			}
		}

		if ('\r' == c)
		{
			currentLine++;
			previousPositionInLine = 0;
		}
		else if ('\n' == c)
		{
			if ('\r' != previousChar)
			{
				currentLine++;
				previousPositionInLine = 0;
			}
		}
		else
		{
			previousPositionInLine++;
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
						"Getting next %s char%s failed.[param: index howMany: %s][index: %s, length: %s, ended: %s]",
						Integer.valueOf(howMany), 1 < howMany ? "s" : "", Integer.valueOf(howMany), Integer.valueOf(index),
						Integer.valueOf(length), Boolean.valueOf(ended)));
			}
			index++;
		}
		return new String(chars);
	}

	@Override
	public char nextNonWhiteSpaceChar()
	{
		return nextNonWhiteSpaceChar0();
	}

	private char nextNonWhiteSpaceChar0()
	{
		while (true)
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
		while (true)
		{
			char c = nextChar0();
			switch (c)
			{
				case 0:
				case '\n':
				case '\r':
					throw new JsonStathamException(
							"Invalid JSON String. It is not terminated properly.[expected: some char that is none of 0, \\n and \\r][actual: "
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
							throw new JsonStathamException("Invalid escaping. [int char: " + (int) c + "][escaped char found: \\" + c
									+ "][char only: '" + c + "']");
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

	@Override
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
			backToPrevious0();
			return newJsonObjectConvertible(this);
		}
		if (c == '[')
		{
			backToPrevious0();
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
			throw new JsonStathamException("No value is found!\nPrevious Char: " + getPreviousCharInfo());
		}
		return JsonUtil.fromStringToValueIfPossible(value);
	}

	@Override
	public void backToPrevious()
	{
		backToPrevious0();
	}

	private void backToPrevious0()
	{
		if (previousCharRequiredAlready)
		{
			throw new JsonStathamException(
					"It cannot move back to the second previous char. It can only move back to one previous char. [previousCharRequiredAlready: "
							+ previousCharRequiredAlready + "]");
		}
		if (0 >= index)
		{
			throw new JsonStathamException("It cannot move to anywhere before the first char. [index: " + index + "]");
		}
		index--;
		previousPositionInLine--;
		previousChar = 0 == index ? 0 : jsonString.charAt(index - 1);

		previousCharRequiredAlready = true;
		ended = false;
	}

	protected abstract JsonObject newJsonObjectConvertible(JsonScanner jsonScanner);

	protected abstract AbstractJsonArray newJsonArrayConvertible(JsonScanner jsonScanner);

	@Override
	public String getPreviousCharInfo()
	{
		if (0 == index)
		{
			return "Not started![index: 0, length: %s]" + length;
		}
		return format(
				"[index (start: 0): %s, previousPositionInLine (start: 1): %s, currentLine: %s, length: %s, ended: %s]",
				Integer.valueOf(index - 1), Integer.valueOf(previousPositionInLine), Integer.valueOf(currentLine),
				Integer.valueOf(length), Boolean.valueOf(ended));
	}

	@Override
	public String toString()
	{
		/* @formatter:off */
		return toStringBuilder(this)
				.add("length", length)
				.add("index", index)
				.add("previousChar", previousChar)
				.add("previousCharRequiredAlready", previousCharRequiredAlready)
				.add("previousPositionInLine", previousPositionInLine)
				.add("currentLine", currentLine)
				.add("ended", ended)
				.toString();
		/* @formatter:on */
	}
}

/**
 *
 */
package org.elixirian.jsonstatham.exception;

import static org.elixirian.kommonlee.util.MessageFormatter.format;

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
 * @version 0.0.1 (2009-11-21)
 */
public class JsonStathamException extends RuntimeException
{
	private static final long serialVersionUID = -6441560700078967071L;

	public JsonStathamException(final String message)
	{
		super(message);
	}

	public JsonStathamException(final String message, final Throwable cause)
	{
		super(message, cause);
	}

	public JsonStathamException(final Throwable cause)
	{
		super(cause);
	}

	public static JsonStathamException newJsonStathamException(final String message, final Object... args)
	{
		return new JsonStathamException(format(message, args));
	}

	public static JsonStathamException newJsonStathamException(final Throwable cause, final String message,
			final Object... args)
	{
		return new JsonStathamException(format(message, args), cause);
	}
}

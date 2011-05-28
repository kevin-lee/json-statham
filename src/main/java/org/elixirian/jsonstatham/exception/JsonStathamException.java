/**
 * 
 */
package org.elixirian.jsonstatham.exception;

/**
 * <pre>
 *     ___  _____  __________  ___________ _____  ____
 *    /   \/    / /      \   \/   /_    _//     \/   /
 *   /        /  /    ___/\      / /   / /          /
 *  /        \  /    ___/  \    /_/   /_/          /
 * /____/\____\/_______/    \__//______/___/\_____/
 * </pre>
 * 
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2009-11-21)
 */
public class JsonStathamException extends RuntimeException
{
	private static final long serialVersionUID = -6441560700078967071L;

	public JsonStathamException()
	{
		super();
	}

	public JsonStathamException(String message)
	{
		super(message);
	}

	public JsonStathamException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public JsonStathamException(Throwable cause)
	{
		super(cause);
	}
}

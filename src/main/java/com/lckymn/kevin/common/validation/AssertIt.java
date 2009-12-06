/**
 * 
 */
package com.lckymn.kevin.common.validation;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.01 (2009-11-28)
 */
public final class AssertIt
{
	private AssertIt()
	{
		throw new IllegalStateException("AssertIt class cannot be instantiated.");
	}

	public static void isNotNull(final Object object, final String message)
	{
		if (null == object)
		{
			throw new IllegalArgumentException(message);
		}
	}

	public static void isNull(final Object object, final String message)
	{
		if (null != object)
		{
			throw new IllegalArgumentException(message);
		}
	}

	public static void isNotEmpty(final String value, final String message)
	{
		isNotNull(value, message);
		if (0 == value.length())
		{
			throw new IllegalArgumentException(message);
		}
	}

	public static void isEmpty(final String value, final String message)
	{
		isNull(value, message);
		if (0 != value.length())
		{
			throw new IllegalArgumentException(message);
		}
	}
	
	public static void isTrue(final boolean expresion, final String message)
	{
		if (!expresion)
		{
			throw new IllegalArgumentException(message);
		}
	}
}

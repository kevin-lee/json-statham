/**
 * 
 */
package com.lckymn.kevin.common.reflect;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-10-04)
 */
public abstract class TypeHolder<T>
{
	protected final Type type;

	protected TypeHolder()
	{
		this.type = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public Type getType()
	{
		return type;
	}
}

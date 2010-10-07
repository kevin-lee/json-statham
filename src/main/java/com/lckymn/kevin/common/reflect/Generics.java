/**
 * 
 */
package com.lckymn.kevin.common.reflect;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Collection;

import com.lckymn.kevin.common.type.Pair;
import com.lckymn.kevin.common.util.CommonConstants;
import com.lckymn.kevin.common.util.Objects;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-09-30)
 */
public final class Generics
{
	private Generics()
	{
		throw new IllegalStateException(getClass().getName() + CommonConstants.CANNOT_BE_INSTANTIATED);
	}

	private static Type extractType(Type type)
	{
		if (type instanceof Class<?>)
		{
			Class<?> paramType = (Class<?>) type;
			System.out.println("!class: " + paramType.getName());
			if (paramType.isArray())
			{
				System.out.print("component: ");
				return extractType(paramType.getComponentType());
			}
			return paramType;
		}
		// But if not, need to start resolving.
		if (type instanceof ParameterizedType)
		{
			System.out.println("ParameterizedType: " + type);
			final ParameterizedType parameterizedType = ((ParameterizedType) type);
			System.out.println("RowType: " + parameterizedType.getRawType() + " | OwnerType: "
					+ parameterizedType.getOwnerType());
			for (Type eachType : parameterizedType.getActualTypeArguments())
			{
				return extractType(eachType);
			}
		}
		if (type instanceof GenericArrayType)
		{
			System.out.println("GenericArrayType: " + type);
			return extractType(((GenericArrayType) type).getGenericComponentType());
		}
		if (type instanceof TypeVariable)
		{
			System.out.println("TypeVariable: " + type + " | " + ((TypeVariable) type).getName());
			return ((TypeVariable) type);
		}
		if (type instanceof WildcardType)
		{
			System.out.println("WildcardType: " + type);
			WildcardType wildcardType = (WildcardType) type;
			System.out.println("lower bounds");
			for (Type eachType : wildcardType.getLowerBounds())
			{
				return extractType(eachType);
			}
			System.out.println("upper bounds");
			for (Type eachType : wildcardType.getUpperBounds())
			{
				return extractType(eachType);
			}
		}
		return null;
	}

	public static <E> E getGenericInfo(Type type)
	{
		if (type instanceof ParameterizedType)
		{
			System.out.println("ParameterizedType: " + type);
			final ParameterizedType parameterizedType = ((ParameterizedType) type);
			System.out.println("RowType: " + parameterizedType.getRawType() + " | OwnerType: "
					+ parameterizedType.getOwnerType());
			return (E) extractType(parameterizedType.getActualTypeArguments()[0]);
		}
		return null;
	}

	public static class KeyValuePair<K, V> implements Pair<K, V>
	{
		private final K key;
		private final V value;

		public KeyValuePair(K key, V value)
		{
			this.key = key;
			this.value = value;
		}

		@Override
		public K getLeft()
		{
			return key;
		}

		@Override
		public V getRight()
		{
			return value;
		}

		@Override
		public String toString()
		{
			return Objects.toStringBuilder(this)
					.add("key", key)
					.add("value", value)
					.toString();
		}
	}

	// public static <K, V> Pair<K, V> getGenericInfoKV(Type type)
	// {
	// final K key = extractFromParameterizedType(type, 0);
	// final V value = extractFromParameterizedType(type, 1);
	// return new KeyValuePair<K, V>(key, value);
	// }

	public static <T extends Type> T extractFromParameterizedType(Type type, int index)
	{
		if (type instanceof ParameterizedType)
		{
			final ParameterizedType parameterizedType = ((ParameterizedType) type);
			System.out.println("RowType: " + parameterizedType.getRawType() + " | OwnerType: "
					+ parameterizedType.getOwnerType());

			@SuppressWarnings("unchecked")
			final T t = (T) parameterizedType.getActualTypeArguments()[index];
			return t;
		}
		return null;
	}
}

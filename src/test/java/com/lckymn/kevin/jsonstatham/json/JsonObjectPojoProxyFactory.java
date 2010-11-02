/**
 * 
 */
package com.lckymn.kevin.jsonstatham.json;

import static com.lckymn.kevin.common.util.Conditions.*;
import static com.lckymn.kevin.common.util.Objects.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;

import com.lckymn.kevin.common.reflect.Classes;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-03-07)
 */
public final class JsonObjectPojoProxyFactory
{
	public static JsonObjectPojo newJsonObjectPojo(final JsonObjectPojo jsonObjectPojo, final Long id,
			final String name, final Collection<Address> addressCollection) throws IllegalArgumentException,
			NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException
	{
		ProxyFactory proxyFactory = new ProxyFactory();
		proxyFactory.setSuperclass(jsonObjectPojo.getClass());
		final Set<Address> addressSet = new HashSet<Address>(addressCollection);
		proxyFactory.setHandler(new MethodHandler() {
			@Override
			public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable
			{
				final String methodName = thisMethod.getName();

				if ("getId".equals(methodName))
				{
					return id;
				}
				else if ("getName".equals(methodName))
				{
					return name;
				}
				else if ("getAddresses".equals(methodName))
				{
					return addressCollection.iterator();
				}
				else if ("getAddressSet".equals(methodName))
				{
					return addressSet;
				}
				else if ("hashCode".equals(methodName))
				{
					return Integer.valueOf(hash(id, name, addressSet));
				}
				else if ("equals".equals(methodName))
				{
					final Object jsonObjectPojo = args[0];
					if (areIdentical(self, jsonObjectPojo))
					{
						return Boolean.TRUE;
					}
					final JsonObjectPojo that = castIfInstanceOf(JsonObjectPojo.class, jsonObjectPojo);
					/* @formatter:off */
					return Boolean.valueOf(isNotNull(that) && 
											and(equal(id, that.getId()), 
												equal(name, that.getName()),
												equal(addressSet, that.getAddressSet())));
					/* @formatter:on */
				}
				else if ("toString".equals(methodName))
				{
					return toStringBuilder(jsonObjectPojo).add("id", id)
							.add("name", name)
							.add("addresses", addressSet)
							.toString();
				}
				else
				{
					return proceed.invoke(self, args);
				}
			}
		});
		return (JsonObjectPojo) proxyFactory.create(new Class[] { Long.class, String.class, Set.class }, new Object[] {
				null, null, null });
	}

	public static NestedJsonObjectWithValueAccessor newNestedJsonObjectWithValueAccessor(
			final NestedJsonObjectWithValueAccessor nestedJsonObjectWithValueAccessor, final Long primaryKey,
			final String name, final NestedJsonObjectWithValueAccessor parent) throws IllegalArgumentException,
			NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException
	{
		ProxyFactory proxyFactory = new ProxyFactory();
		proxyFactory.setSuperclass(nestedJsonObjectWithValueAccessor.getClass());
		proxyFactory.setHandler(new MethodHandler() {
			@Override
			public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable
			{
				final String methodName = thisMethod.getName();

				if ("getPrimaryKey".equals(methodName))
				{
					return primaryKey;
				}
				else if ("getName".equals(methodName))
				{
					return name;
				}
				else if ("getParent".equals(methodName))
				{
					return parent;
				}
				else if ("hashCode".equals(methodName))
				{
					return Integer.valueOf(hash(hash(hash(primaryKey), name), parent));
				}
				else if ("equals".equals(methodName))
				{
					final Object nestedJsonObjectWithValueAccessor = args[0];
					if (areIdentical(self, nestedJsonObjectWithValueAccessor))
					{
						return Boolean.TRUE;
					}
					final NestedJsonObjectWithValueAccessor that =
						castIfInstanceOf(NestedJsonObjectWithValueAccessor.class, nestedJsonObjectWithValueAccessor);
					/* @formatter:off */
					return Boolean.valueOf(isNotNull(that) && 
											and(equal(primaryKey, that.getPrimaryKey()), 
												equal(name, that.getName()),
												equal(parent, that.getParent())));
					/* @formatter:on */
				}
				else
				{
					return proceed.invoke(self, args);
				}
			}
		});
		return (NestedJsonObjectWithValueAccessor) proxyFactory.create(
				Classes.classArrayOf(Long.class, String.class, NestedJsonObjectWithValueAccessor.class),
				Classes.objectArrayOf(null, null, null));
	}
}

/**
 * This project is licensed under the Apache License, Version 2.0
 * if the following condition is met:
 * (otherwise it cannot be used by anyone but the author, Kevin, only)
 *
 * The original JSON Statham project is owned by Lee, Seong Hyun (Kevin).
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
package org.elixirian.jsonstatham.json;

import static org.elixirian.kommonlee.util.Conditional.*;
import static org.elixirian.kommonlee.util.Objects.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;

import org.elixirian.kommonlee.reflect.Classes;

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
 * @version 0.0.1 (2010-03-07)
 */
public final class JsonObjectPojoProxyFactory
{
  public static JsonObjectPojo newJsonObjectPojo(final JsonObjectPojo jsonObjectPojo, final Long id, final String name,
      final Collection<Address> addressCollection) throws IllegalArgumentException, NoSuchMethodException,
      InstantiationException, IllegalAccessException, InvocationTargetException
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
          if (identical(self, jsonObjectPojo))
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
          if (identical(self, nestedJsonObjectWithValueAccessor))
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

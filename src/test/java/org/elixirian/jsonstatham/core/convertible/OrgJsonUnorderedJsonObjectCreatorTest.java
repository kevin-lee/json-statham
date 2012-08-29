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

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.HashMap;

import org.elixirian.jsonstatham.core.convertible.JsonObject;
import org.elixirian.jsonstatham.core.convertible.JsonObjectCreator;
import org.elixirian.jsonstatham.core.convertible.OrgJsonUnorderedJsonObjectCreator;
import org.json.JSONObject;
import org.junit.Test;


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
 * @version 0.0.1 (2010-02-03)
 */
public class OrgJsonUnorderedJsonObjectCreatorTest
{
  /**
   * Test method for
   * {@link org.elixirian.jsonstatham.core.convertible.OrgJsonUnorderedJsonObjectCreator#newJsonObjectConvertible()}
   * .
   *
   * @throws NoSuchFieldException
   * @throws SecurityException
   * @throws IllegalAccessException
   * @throws IllegalArgumentException
   */
  @Test
  public void testNewJSONObject() throws SecurityException, NoSuchFieldException, IllegalArgumentException,
      IllegalAccessException
  {
    final JsonObjectCreator jsonObjectCreator = new OrgJsonUnorderedJsonObjectCreator();
    final JsonObject jsonObject = jsonObjectCreator.newJsonObjectConvertible();

    final Field jsonObjectField = jsonObject.getClass()
        .getDeclaredField("orgJsonObject");
    jsonObjectField.setAccessible(true);
    final Object actual = jsonObjectField.get(jsonObject);
    assertThat(actual, notNullValue());
    assertThat(actual, is(JSONObject.class));
    assertSame(actual.getClass(), JSONObject.class);

    final Field mapField = actual.getClass()
        .getDeclaredField("map");
    mapField.setAccessible(true);
    final Object mapObject = mapField.get(actual);
    assertThat(mapObject, notNullValue());
    assertThat(mapObject, is(HashMap.class));
    assertSame(mapObject.getClass(), HashMap.class);

  }
}

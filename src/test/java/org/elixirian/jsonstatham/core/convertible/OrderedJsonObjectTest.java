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
package org.elixirian.jsonstatham.core.convertible;

import static org.fest.assertions.api.Assertions.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.LinkedHashMap;

import org.elixirian.jsonstatham.exception.JsonStathamException;
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
 * @version 0.0.1 (2010-06-03)
 */
public class OrderedJsonObjectTest
{
  private static final String[] NAMES = { "surnname", "givenName", "number", "email", "someKey" };

  @SuppressWarnings("boxing")
  private static final Object[] VALUES = { "Lee", "Kevin", 1, "kevin@some.email.com", 12.50 };

  @Test
  public final void testOrgJsonJsonObjectConvertible()
  {
    final JsonObject jsonObject = new OrderedJsonObject();
    assertThat(jsonObject.getActualObject(), is(instanceOf(JsonObject.class)));
    assertThat(((JsonObject) jsonObject.getActualObject()), is(equalTo(jsonObject)));
  }

  @Test
  public final void testIsJsonObject()
  {
    final JsonObject jsonObject = new OrderedJsonObject(new LinkedHashMap<String, Object>());
    assertThat(jsonObject.isJsonObject()).isTrue();
  }

  @Test
  public final void testIsJsonArray()
  {
    final JsonObject jsonObject = new OrderedJsonObject(new LinkedHashMap<String, Object>());
    assertThat(jsonObject.isJsonArray()).isFalse();
  }

  @Test
  public void testGetNames()
  {
    final JsonObject jsonObject = new OrderedJsonObject(new LinkedHashMap<String, Object>());

    for (int i = 0, size = NAMES.length; i < size; i++)
    {
      jsonObject.put(NAMES[i], VALUES[i]);
    }
    assertThat(jsonObject.getNames(), is(equalTo(NAMES)));
  }

  @Test
  public void testGet()
  {
    final JsonObject jsonObject = new OrderedJsonObject();

    for (int i = 0, size = NAMES.length; i < size; i++)
    {
      jsonObject.put(NAMES[i], VALUES[i]);
    }
    for (int i = 0, size = NAMES.length; i < size; i++)
    {
      assertThat(jsonObject.get(NAMES[i]), is(equalTo(VALUES[i])));
    }
  }

  @Test
  public final void testPut()
  {
    final JsonObject jsonObjectConvertible1 = new OrderedJsonObject();
    jsonObjectConvertible1.put("name", "Kevin Lee");

    final JsonObject jsonObjectConvertible2 = new OrderedJsonObject();
    jsonObjectConvertible2.put("jsonObjectConvertible1", jsonObjectConvertible1);

    assertThat(((JsonObject) jsonObjectConvertible2.getActualObject()).get("jsonObjectConvertible1"),
        is(instanceOf(JsonConvertible.class)));

    assertThat(((JsonObject) jsonObjectConvertible2.getActualObject()).get("jsonObjectConvertible1"),
        is(instanceOf(JsonObject.class)));
    assertThat(((JsonObject) jsonObjectConvertible2.get("jsonObjectConvertible1")), is(jsonObjectConvertible1));
    assertThat(((JsonObject) jsonObjectConvertible2.get("jsonObjectConvertible1")), equalTo(jsonObjectConvertible1));

    final JsonObject jsonObjectConvertible3 = new OrderedJsonObject();
    final String idName = "id";
    final Integer idValue = Integer.valueOf(999);
    final String surnameName = "surname";
    final String surnameValue = "Lee";
    final String givenNameName = "givenName";
    final String givenNameValue = "Kevin";
    final String jsonObjectConvertible3String = "{\"id\":999,\"surname\":\"Lee\",\"givenName\":\"Kevin\"}";
    jsonObjectConvertible3.put(idName, idValue);
    jsonObjectConvertible3.put(surnameName, surnameValue);
    jsonObjectConvertible3.put(givenNameName, givenNameValue);

    assertThat(((JsonObject) jsonObjectConvertible3.getActualObject()), is(equalTo(jsonObjectConvertible3)));

    assertThat(jsonObjectConvertible3.<Integer> get(idName), is(equalTo(idValue)));
    assertThat((String) jsonObjectConvertible3.get(surnameName), is(equalTo(surnameValue)));
    assertThat((String) jsonObjectConvertible3.get(givenNameName), is(equalTo(givenNameValue)));
    assertThat(jsonObjectConvertible3.toString(), is(equalTo(jsonObjectConvertible3String)));
  }

  @Test(expected = JsonStathamException.class)
  public final void testPutWithNullKey()
  {
    new OrderedJsonObject().put(null, "Test");
  }

  @Test
  public final void testGetActualObject()
  {
    final JsonObject jsonObjectConvertible1 = new OrderedJsonObject();
    jsonObjectConvertible1.put("name", "Kevin Lee");

    final JsonObject jsonObjectConvertible2 = new OrderedJsonObject();
    jsonObjectConvertible2.put("jsonObjectConvertible1", jsonObjectConvertible1);

    assertThat(jsonObjectConvertible2.getActualObject(), is(instanceOf(JsonObject.class)));
    assertThat((JsonObject) jsonObjectConvertible2.getActualObject(), is(equalTo(jsonObjectConvertible2)));

    final JsonObject jsonObjectConvertible3 = new OrderedJsonObject();
    final String idName = "id";
    final Integer idValue = Integer.valueOf(999);
    final String surnameName = "surname";
    final String surnameValue = "Lee";
    final String givenNameName = "givenName";
    final String givenNameValue = "Kevin";
    jsonObjectConvertible3.put(idName, idValue)
        .put(surnameName, surnameValue)
        .put(givenNameName, givenNameValue);

    assertThat(jsonObjectConvertible3.getActualObject(), is(instanceOf(JsonObject.class)));
    assertThat(((JsonObject) jsonObjectConvertible3.getActualObject()), is(equalTo(jsonObjectConvertible3)));
  }

  @Test
  public final void testToString()
  {
    final JsonObject jsonObjectConvertible1 = new OrderedJsonObject();
    jsonObjectConvertible1.put("name", "Kevin Lee");

    final JsonObject jsonObjectConvertible2 = new OrderedJsonObject();
    jsonObjectConvertible2.put("jsonObjectConvertible1", jsonObjectConvertible1);
    final String jsonObjectConvertible2String = "{\"jsonObjectConvertible1\":{\"name\":\"Kevin Lee\"}}";

    assertThat(jsonObjectConvertible2.toString(), is(equalTo(jsonObjectConvertible2String)));

    final JsonObject jsonObjectConvertible3 = new OrderedJsonObject();
    jsonObjectConvertible3.put("id", Integer.valueOf(999))
        .put("surname", "Lee")
        .put("givenName", "Kevin");
    final String jsonObjectConvertible3String = "{\"id\":999,\"surname\":\"Lee\",\"givenName\":\"Kevin\"}";

    assertThat(jsonObjectConvertible3.toString(), is(equalTo(jsonObjectConvertible3String)));
  }

}

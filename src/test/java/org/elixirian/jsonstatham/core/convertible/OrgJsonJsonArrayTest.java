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

import java.util.LinkedHashMap;

import org.elixirian.jsonstatham.core.convertible.JsonArray;
import org.elixirian.jsonstatham.core.convertible.JsonConvertible;
import org.elixirian.jsonstatham.core.convertible.OrgJsonJsonArray;
import org.elixirian.jsonstatham.core.convertible.OrgJsonJsonObject;
import org.json.JSONArray;
import org.json.JSONException;
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
 * @version 0.0.1 (2010-09-13)
 */
public class OrgJsonJsonArrayTest
{
  @SuppressWarnings("boxing")
  private static final Object[] VALUES = { "Lee", "Kevin", 1, "kevin@some.email.com", 12.50 };

  /**
   * Test method for
   * {@link org.elixirian.jsonstatham.core.convertible.OrgJsonJsonArray#OrgJsonJsonArrayConvertible(org.json.JSONArray)}
   * .
   */
  @Test
  public final void testOrgJsonJsonArrayConvertible()
  {
    final JSONArray orgJsonArray = new JSONArray();
    final JsonArray jsonArray = new OrgJsonJsonArray(orgJsonArray);
    assertThat(jsonArray.getActualObject(), is(instanceOf(JSONArray.class)));
    assertThat(((JSONArray) jsonArray.getActualObject()), is(orgJsonArray));
    assertThat(((JSONArray) jsonArray.getActualObject()), equalTo(orgJsonArray));
  }

  /**
   * Test method for {@link org.elixirian.jsonstatham.core.convertible.OrgJsonJsonArray#get(int)}.
   */
  @Test
  public final void testGet()
  {
    final JSONArray orgJsonArray = new JSONArray();
    for (int i = 0, size = VALUES.length; i < size; i++)
    {
      orgJsonArray.put(VALUES[i]);
    }
    final JsonArray jsonArray = new OrgJsonJsonArray(orgJsonArray);
    for (int i = 0, size = VALUES.length; i < size; i++)
    {
      assertThat(jsonArray.get(i), is(equalTo(VALUES[i])));
    }
  }

  /**
   * Test method for
   * {@link org.elixirian.jsonstatham.core.convertible.OrgJsonJsonArray#put(java.lang.Object)}.
   *
   * @throws JSONException
   */
  @Test
  public final void testPut() throws JSONException
  {
    final JSONArray orgJsonArray = new JSONArray();
    final JsonArray jsonArray = new OrgJsonJsonArray(orgJsonArray);
    for (int i = 0, size = VALUES.length; i < size; i++)
    {
      jsonArray.put(VALUES[i]);
    }
    for (int i = 0, size = VALUES.length; i < size; i++)
    {
      assertThat(orgJsonArray.get(i), is(equalTo(VALUES[i])));
    }

    final JSONArray orgJsonArray2 = new JSONArray();
    final JsonArray jsonArrayConvertible2 = new OrgJsonJsonArray(orgJsonArray2);

    @SuppressWarnings("boxing")
    final Object[] valueObjects =
      { new OrgJsonJsonObject(new JSONObject(new LinkedHashMap<String, Object>())).put("first", "test1")
          .put("second", "test2")
          .put("third", "test3"), new OrgJsonJsonArray(new JSONArray()).put("test")
          .put("value"), "Kevin", 1234, 9864L, 1.0F, 10.456D, true };
    for (int i = 0, size = valueObjects.length; i < size; i++)
    {
      jsonArrayConvertible2.put(valueObjects[i]);
    }
    for (int i = 0, size = valueObjects.length; i < size; i++)
    {
      final Object object = valueObjects[i];
      if (object instanceof JsonConvertible)
      {
        assertThat(orgJsonArray2.get(i), is(equalTo(((JsonConvertible) object).getActualObject())));
      }
      else
      {
        assertThat(orgJsonArray2.get(i), is(equalTo(valueObjects[i])));
      }
    }
  }

  /**
   * Test method for {@link org.elixirian.jsonstatham.core.convertible.OrgJsonJsonArray#length()}.
   */
  @SuppressWarnings("boxing")
  @Test
  public final void testLength()
  {
    final JSONArray orgJsonArray = new JSONArray();
    final JsonArray jsonArray = new OrgJsonJsonArray(orgJsonArray);
    for (int i = 0, size = VALUES.length; i < size; i++)
    {
      jsonArray.put(VALUES[i]);
    }
    assertThat(jsonArray.length(), is(equalTo(VALUES.length)));
  }

  /**
   * Test method for {@link org.elixirian.jsonstatham.core.convertible.OrgJsonJsonArray#getActualObject()}
   * .
   */
  @Test
  public final void testGetActualObject()
  {
    final JSONArray orgJsonArray = new JSONArray();
    final JsonArray jsonArray = new OrgJsonJsonArray(orgJsonArray);
    assertThat(jsonArray.getActualObject(), is(equalTo((Object) orgJsonArray)));
  }

  /**
   * Test method for {@link org.elixirian.jsonstatham.core.convertible.OrgJsonJsonArray#toString()}.
   */
  @Test
  public final void testToString()
  {
    final JSONArray orgJsonArray = new JSONArray();
    final JsonArray jsonArray = new OrgJsonJsonArray(orgJsonArray);
    for (int i = 0, size = VALUES.length; i < size; i++)
    {
      jsonArray.put(VALUES[i]);
    }
    assertThat(jsonArray.toString(), is(equalTo(orgJsonArray.toString())));
  }

}

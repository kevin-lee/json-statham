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

import org.elixirian.jsonstatham.core.convertible.AbstractOrgJsonJsonObjectConvertibleCreator;
import org.elixirian.jsonstatham.core.convertible.JsonObject;
import org.elixirian.jsonstatham.core.convertible.OrgJsonJsonObject;
import org.elixirian.jsonstatham.exception.JsonStathamException;
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
 * @version 0.0.1 (2010-06-03)
 */
public class AbstractOrgJsonJsonObjectConvertibleCreatorTest
{

  /**
   * Test method for
   * {@link org.elixirian.jsonstatham.core.convertible.AbstractOrgJsonJsonObjectConvertibleCreator#newJsonObjectConvertible()}
   * .
   */
  @Test
  public final void testNewJsonObjectConvertible()
  {
    final JsonObject jsonObject = new OrgJsonJsonObject(new JSONObject());
    final AbstractOrgJsonJsonObjectConvertibleCreator orgJsonJsonObjectConvertibleCreator =
      new AbstractOrgJsonJsonObjectConvertibleCreator() {

        @Override
        public JsonObject newJsonObjectConvertible()
        {
          return jsonObject;
        }
      };

    assertThat(orgJsonJsonObjectConvertibleCreator.newJsonObjectConvertible(), is(jsonObject));
    assertThat(orgJsonJsonObjectConvertibleCreator.newJsonObjectConvertible(), equalTo(jsonObject));
  }

  /**
   * Test method for
   * {@link org.elixirian.jsonstatham.core.convertible.AbstractOrgJsonJsonObjectConvertibleCreator#nullJsonObjectConvertible()}
   * .
   */
  @Test
  public final void testNullJsonObjectConvertible()
  {
    final AbstractOrgJsonJsonObjectConvertibleCreator orgJsonJsonObjectConvertibleCreator =
      new AbstractOrgJsonJsonObjectConvertibleCreator() {

        @Override
        public JsonObject newJsonObjectConvertible()
        {
          return null;
        }
      };

    assertThat(orgJsonJsonObjectConvertibleCreator.nullJsonObjectConvertible(),
        is(AbstractOrgJsonJsonObjectConvertibleCreator.NULL_JSON_OBJECT_CONVERTIBLE));
    assertThat(orgJsonJsonObjectConvertibleCreator.nullJsonObjectConvertible(),
        equalTo(AbstractOrgJsonJsonObjectConvertibleCreator.NULL_JSON_OBJECT_CONVERTIBLE));
  }

  @Test(expected = JsonStathamException.class)
  public void testAbstractOrgJsonJsonObjectConvertibleCreator$NULL_JSON_OBJECT_CONVERTIBLE() throws Exception
  {
    AbstractOrgJsonJsonObjectConvertibleCreator.NULL_JSON_OBJECT_CONVERTIBLE.put(null, null);
  }
}

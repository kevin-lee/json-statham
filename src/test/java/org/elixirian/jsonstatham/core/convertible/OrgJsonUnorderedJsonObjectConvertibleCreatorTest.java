/**
 * 
 */
package org.elixirian.jsonstatham.core.convertible;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.HashMap;

import org.elixirian.jsonstatham.core.convertible.JsonObjectConvertible;
import org.elixirian.jsonstatham.core.convertible.JsonObjectConvertibleCreator;
import org.elixirian.jsonstatham.core.convertible.OrgJsonUnorderedJsonObjectConvertibleCreator;
import org.json.JSONObject;
import org.junit.Test;


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
 * @version 0.0.1 (2010-02-03)
 */
public class OrgJsonUnorderedJsonObjectConvertibleCreatorTest
{
  /**
   * Test method for
   * {@link org.elixirian.jsonstatham.core.convertible.OrgJsonUnorderedJsonObjectConvertibleCreator#newJsonObjectConvertible()}
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
    JsonObjectConvertibleCreator jsonObjectCreator = new OrgJsonUnorderedJsonObjectConvertibleCreator();
    JsonObjectConvertible jsonObjectConvertible = jsonObjectCreator.newJsonObjectConvertible();

    Field jsonObjectField = jsonObjectConvertible.getClass()
        .getDeclaredField("jsonObject");
    jsonObjectField.setAccessible(true);
    Object jsonObject = jsonObjectField.get(jsonObjectConvertible);
    assertThat(jsonObject, notNullValue());
    assertThat(jsonObject, is(JSONObject.class));
    assertSame(jsonObject.getClass(), JSONObject.class);

    Field mapField = jsonObject.getClass()
        .getDeclaredField("map");
    mapField.setAccessible(true);
    Object mapObject = mapField.get(jsonObject);
    assertThat(mapObject, notNullValue());
    assertThat(mapObject, is(HashMap.class));
    assertSame(mapObject.getClass(), HashMap.class);

  }
}

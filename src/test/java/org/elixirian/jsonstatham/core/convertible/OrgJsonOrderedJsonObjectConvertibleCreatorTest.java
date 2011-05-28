/**
 * 
 */
package org.elixirian.jsonstatham.core.convertible;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.elixirian.jsonstatham.core.convertible.JsonObjectConvertible;
import org.elixirian.jsonstatham.core.convertible.JsonObjectConvertibleCreator;
import org.elixirian.jsonstatham.core.convertible.OrgJsonOrderedJsonObjectConvertibleCreator;
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
public class OrgJsonOrderedJsonObjectConvertibleCreatorTest
{
  /**
   * Test method for
   * {@link org.elixirian.jsonstatham.core.convertible.OrgJsonOrderedJsonObjectConvertibleCreator#newJsonObjectConvertible()}
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
    JsonObjectConvertibleCreator jsonObjectCreator = new OrgJsonOrderedJsonObjectConvertibleCreator();
    JsonObjectConvertible jsonObjectConvertible = jsonObjectCreator.newJsonObjectConvertible();

    Field jsonObjectField = jsonObjectConvertible.getClass()
        .getDeclaredField("jsonObject");
    jsonObjectField.setAccessible(true);
    Object jsonObject = jsonObjectField.get(jsonObjectConvertible);
    assertThat(jsonObject, notNullValue());
    assertThat(jsonObject, is(instanceOf(JSONObject.class)));
    assertSame(jsonObject.getClass(), JSONObject.class);

    Field mapField = jsonObject.getClass()
        .getDeclaredField("map");
    mapField.setAccessible(true);
    Object mapObject = mapField.get(jsonObject);
    assertThat(mapObject, notNullValue());
    assertThat(mapObject, is(instanceOf(LinkedHashMap.class)));
    assertSame(mapObject.getClass(), LinkedHashMap.class);
    assertNotSame(mapObject.getClass(), HashMap.class);
  }
}

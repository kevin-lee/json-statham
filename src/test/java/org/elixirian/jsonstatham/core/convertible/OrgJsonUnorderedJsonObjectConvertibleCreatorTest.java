/**
 *
 */
package org.elixirian.jsonstatham.core.convertible;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.HashMap;

import org.elixirian.jsonstatham.core.convertible.JsonObject;
import org.elixirian.jsonstatham.core.convertible.JsonObjectConvertibleCreator;
import org.elixirian.jsonstatham.core.convertible.OrgJsonUnorderedJsonObjectConvertibleCreator;
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
    final JsonObjectConvertibleCreator jsonObjectCreator = new OrgJsonUnorderedJsonObjectConvertibleCreator();
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

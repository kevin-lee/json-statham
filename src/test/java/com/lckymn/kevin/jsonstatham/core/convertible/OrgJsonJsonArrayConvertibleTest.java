/**
 * 
 */
package com.lckymn.kevin.jsonstatham.core.convertible;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.LinkedHashMap;

import org.json.JSONArray;
import org.json.JSONException;
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
 * @version 0.0.1 (2010-09-13)
 */
public class OrgJsonJsonArrayConvertibleTest
{
  @SuppressWarnings("boxing")
  private static final Object[] VALUES = { "Lee", "Kevin", 1, "kevin@some.email.com", 12.50 };

  /**
   * Test method for
   * {@link com.lckymn.kevin.jsonstatham.core.convertible.OrgJsonJsonArrayConvertible#OrgJsonJsonArrayConvertible(org.json.JSONArray)}
   * .
   */
  @Test
  public final void testOrgJsonJsonArrayConvertible()
  {
    final JSONArray jsonArray = new JSONArray();
    final JsonArrayConvertible jsonArrayConvertible = new OrgJsonJsonArrayConvertible(jsonArray);
    assertThat(jsonArrayConvertible.getActualObject(), is(instanceOf(JSONArray.class)));
    assertThat(((JSONArray) jsonArrayConvertible.getActualObject()), is(jsonArray));
    assertThat(((JSONArray) jsonArrayConvertible.getActualObject()), equalTo(jsonArray));
  }

  /**
   * Test method for {@link com.lckymn.kevin.jsonstatham.core.convertible.OrgJsonJsonArrayConvertible#get(int)}.
   */
  @Test
  public final void testGet()
  {
    final JSONArray jsonArray = new JSONArray();
    for (int i = 0, size = VALUES.length; i < size; i++)
    {
      jsonArray.put(VALUES[i]);
    }
    final JsonArrayConvertible jsonArrayConvertible = new OrgJsonJsonArrayConvertible(jsonArray);
    for (int i = 0, size = VALUES.length; i < size; i++)
    {
      assertThat(jsonArrayConvertible.get(i), is(equalTo(VALUES[i])));
    }
  }

  /**
   * Test method for
   * {@link com.lckymn.kevin.jsonstatham.core.convertible.OrgJsonJsonArrayConvertible#put(java.lang.Object)}.
   * 
   * @throws JSONException
   */
  @Test
  public final void testPut() throws JSONException
  {
    final JSONArray jsonArray = new JSONArray();
    final JsonArrayConvertible jsonArrayConvertible = new OrgJsonJsonArrayConvertible(jsonArray);
    for (int i = 0, size = VALUES.length; i < size; i++)
    {
      jsonArrayConvertible.put(VALUES[i]);
    }
    for (int i = 0, size = VALUES.length; i < size; i++)
    {
      assertThat(jsonArray.get(i), is(equalTo(VALUES[i])));
    }

    final JSONArray jsonArray2 = new JSONArray();
    final JsonArrayConvertible jsonArrayConvertible2 = new OrgJsonJsonArrayConvertible(jsonArray2);

    @SuppressWarnings("boxing")
    final Object[] valueObjects =
      { new OrgJsonJsonObjectConvertible(new JSONObject(new LinkedHashMap<String, Object>())).put("first", "test1")
          .put("second", "test2")
          .put("third", "test3"), new OrgJsonJsonArrayConvertible(new JSONArray()).put("test")
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
        assertThat(jsonArray2.get(i), is(equalTo(((JsonConvertible) object).getActualObject())));
      }
      else
      {
        assertThat(jsonArray2.get(i), is(equalTo(valueObjects[i])));
      }
    }
  }

  /**
   * Test method for {@link com.lckymn.kevin.jsonstatham.core.convertible.OrgJsonJsonArrayConvertible#length()}.
   */
  @SuppressWarnings("boxing")
  @Test
  public final void testLength()
  {
    final JSONArray jsonArray = new JSONArray();
    final JsonArrayConvertible jsonArrayConvertible = new OrgJsonJsonArrayConvertible(jsonArray);
    for (int i = 0, size = VALUES.length; i < size; i++)
    {
      jsonArrayConvertible.put(VALUES[i]);
    }
    assertThat(jsonArrayConvertible.length(), is(equalTo(VALUES.length)));
  }

  /**
   * Test method for {@link com.lckymn.kevin.jsonstatham.core.convertible.OrgJsonJsonArrayConvertible#getActualObject()}
   * .
   */
  @Test
  public final void testGetActualObject()
  {
    final JSONArray jsonArray = new JSONArray();
    final JsonArrayConvertible jsonArrayConvertible = new OrgJsonJsonArrayConvertible(jsonArray);
    assertThat(jsonArrayConvertible.getActualObject(), is(equalTo((Object) jsonArray)));
  }

  /**
   * Test method for {@link com.lckymn.kevin.jsonstatham.core.convertible.OrgJsonJsonArrayConvertible#toString()}.
   */
  @Test
  public final void testToString()
  {
    final JSONArray jsonArray = new JSONArray();
    final JsonArrayConvertible jsonArrayConvertible = new OrgJsonJsonArrayConvertible(jsonArray);
    for (int i = 0, size = VALUES.length; i < size; i++)
    {
      jsonArrayConvertible.put(VALUES[i]);
    }
    assertThat(jsonArrayConvertible.toString(), is(equalTo(jsonArray.toString())));
  }

}

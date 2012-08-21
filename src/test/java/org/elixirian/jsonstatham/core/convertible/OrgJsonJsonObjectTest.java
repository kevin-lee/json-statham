/**
 *
 */
package org.elixirian.jsonstatham.core.convertible;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.LinkedHashMap;

import org.elixirian.jsonstatham.core.convertible.JsonObject;
import org.elixirian.jsonstatham.core.convertible.OrgJsonJsonObject;
import org.elixirian.jsonstatham.exception.JsonStathamException;
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
 * @version 0.0.1 (2010-06-03)
 */
public class OrgJsonJsonObjectTest
{
  private static final String[] NAMES = { "surnname", "givenName", "number", "email", "someKey" };

  @SuppressWarnings("boxing")
  private static final Object[] VALUES = { "Lee", "Kevin", 1, "kevin@some.email.com", 12.50 };

  /**
   * Test method for
   * {@link org.elixirian.jsonstatham.core.convertible.OrgJsonJsonObject#OrgJsonJsonObjectConvertible(org.json.JSONObject)}
   * .
   */
  @Test
  public final void testOrgJsonJsonObjectConvertible()
  {
    final JSONObject JSONObject = new JSONObject();
    final JsonObject jsonObject = new OrgJsonJsonObject(JSONObject);
    assertThat(jsonObject.getActualObject(), is(instanceOf(JSONObject.class)));
    assertThat(((JSONObject) jsonObject.getActualObject()), is(JSONObject));
    assertThat(((JSONObject) jsonObject.getActualObject()), equalTo(JSONObject));
  }

  @Test
  public void testGetNames()
  {
    final JSONObject orgJsonObject = new JSONObject(new LinkedHashMap<String, Object>());
    final JsonObject jsonObject = new OrgJsonJsonObject(orgJsonObject);

    for (int i = 0, size = NAMES.length; i < size; i++)
    {
      jsonObject.put(NAMES[i], VALUES[i]);
    }
    assertThat(jsonObject.getNames(), is(equalTo(NAMES)));
  }

  @Test
  public void testGet()
  {
    final JSONObject orgJsonObject = new JSONObject();
    final JsonObject jsonObject = new OrgJsonJsonObject(orgJsonObject);

    for (int i = 0, size = NAMES.length; i < size; i++)
    {
      jsonObject.put(NAMES[i], VALUES[i]);
    }
    for (int i = 0, size = NAMES.length; i < size; i++)
    {
      assertThat(jsonObject.get(NAMES[i]), is(equalTo(VALUES[i])));
    }
  }

  /**
   * Test method for
   * {@link org.elixirian.jsonstatham.core.convertible.OrgJsonJsonObject#put(java.lang.String, java.lang.Object)}
   * .
   *
   * @throws JSONException
   */
  @Test
  public final void testPut() throws JSONException
  {
    final JSONObject orgJsonObject1 = new JSONObject();
    final JsonObject jsonObjectConvertible1 = new OrgJsonJsonObject(orgJsonObject1);
    jsonObjectConvertible1.put("name", "Kevin Lee");

    final JSONObject orgJsonObject2 = new JSONObject();
    final JsonObject jsonObjectConvertible2 = new OrgJsonJsonObject(orgJsonObject2);
    jsonObjectConvertible2.put("jsonObjectConvertible1", jsonObjectConvertible1);

    assertThat(((JSONObject) jsonObjectConvertible2.getActualObject()).get("jsonObjectConvertible1"),
        is(not(instanceOf(OrgJsonJsonObject.class))));
    assertThat(((JSONObject) jsonObjectConvertible2.getActualObject()).get("jsonObjectConvertible1"),
        is(instanceOf(JSONObject.class)));
    assertThat(((JSONObject) orgJsonObject2.get("jsonObjectConvertible1")), is(orgJsonObject1));
    assertThat(((JSONObject) orgJsonObject2.get("jsonObjectConvertible1")), equalTo(orgJsonObject1));

    final JSONObject orgJsonObject3 = new JSONObject();
    final JsonObject jsonObjectConvertible3 = new OrgJsonJsonObject(orgJsonObject3);
    final String idName = "id";
    final Integer idValue = Integer.valueOf(999);
    final String surnameName = "surname";
    final String surnameValue = "Lee";
    final String givenNameName = "givenName";
    final String givenNameValue = "Kevin";
    jsonObjectConvertible3.put(idName, idValue);
    jsonObjectConvertible3.put(surnameName, surnameValue);
    jsonObjectConvertible3.put(givenNameName, givenNameValue);

    assertThat(((JSONObject) jsonObjectConvertible3.getActualObject()), is(orgJsonObject3));
    assertThat(((JSONObject) jsonObjectConvertible3.getActualObject()), equalTo(orgJsonObject3));
    assertThat((Integer) orgJsonObject3.get(idName), is(idValue));
    assertThat((String) orgJsonObject3.get(surnameName), is(surnameValue));
    assertThat((String) orgJsonObject3.get(givenNameName), is(givenNameValue));
    assertThat(jsonObjectConvertible3.toString(), equalTo(orgJsonObject3.toString()));
  }

  /**
   * Test method for
   * {@link org.elixirian.jsonstatham.core.convertible.OrgJsonJsonObject#put(java.lang.String, java.lang.Object)}
   * .
   *
   * @throws JSONException
   */
  @Test(expected = JsonStathamException.class)
  public final void testPutWithNullKey() throws JSONException
  {
    new OrgJsonJsonObject(new JSONObject()).put(null, "Test");
  }

  /**
   * Test method for
   * {@link org.elixirian.jsonstatham.core.convertible.OrgJsonJsonObject#getActualObject()}.
   */
  @Test
  public final void testGetActualObject()
  {
    final JSONObject orgJsonObject1 = new JSONObject();
    final JsonObject jsonObjectConvertible1 = new OrgJsonJsonObject(orgJsonObject1);
    jsonObjectConvertible1.put("name", "Kevin Lee");

    final JSONObject orgJsonObject2 = new JSONObject();
    final JsonObject jsonObjectConvertible2 = new OrgJsonJsonObject(orgJsonObject2);
    jsonObjectConvertible2.put("jsonObjectConvertible1", jsonObjectConvertible1);

    assertThat(jsonObjectConvertible2.getActualObject(), is(instanceOf(JSONObject.class)));
    assertThat((JSONObject) jsonObjectConvertible2.getActualObject(), is(orgJsonObject2));
    assertThat((JSONObject) jsonObjectConvertible2.getActualObject(), equalTo(orgJsonObject2));

    final JSONObject orgJsonObject3 = new JSONObject();
    final JsonObject jsonObjectConvertible3 = new OrgJsonJsonObject(orgJsonObject3);
    final String idName = "id";
    final Integer idValue = Integer.valueOf(999);
    final String surnameName = "surname";
    final String surnameValue = "Lee";
    final String givenNameName = "givenName";
    final String givenNameValue = "Kevin";
    jsonObjectConvertible3.put(idName, idValue)
        .put(surnameName, surnameValue)
        .put(givenNameName, givenNameValue);

    assertThat(jsonObjectConvertible3.getActualObject(), is(instanceOf(JSONObject.class)));
    assertThat(((JSONObject) jsonObjectConvertible3.getActualObject()), is(orgJsonObject3));
    assertThat(((JSONObject) jsonObjectConvertible3.getActualObject()), equalTo(orgJsonObject3));
  }

  /**
   * Test method for {@link org.elixirian.jsonstatham.core.convertible.OrgJsonJsonObject#toString()}.
   */
  @Test
  public final void testToString()
  {
    final JSONObject orgJsonObject1 = new JSONObject();
    final JsonObject jsonObjectConvertible1 = new OrgJsonJsonObject(orgJsonObject1);
    jsonObjectConvertible1.put("name", "Kevin Lee");

    final JSONObject orgJsonObject2 = new JSONObject();
    final JsonObject jsonObjectConvertible2 = new OrgJsonJsonObject(orgJsonObject2);
    jsonObjectConvertible2.put("jsonObjectConvertible1", jsonObjectConvertible1);

    assertThat(jsonObjectConvertible2.toString(), equalTo(orgJsonObject2.toString()));

    final JSONObject orgJsonObject3 = new JSONObject();
    final JsonObject jsonObjectConvertible3 = new OrgJsonJsonObject(orgJsonObject3);
    jsonObjectConvertible3.put("id", Integer.valueOf(999))
        .put("surname", "Lee")
        .put("givenName", "Kevin");

    assertThat(jsonObjectConvertible3.toString(), equalTo(orgJsonObject3.toString()));
  }

}

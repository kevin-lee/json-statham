/**
 * 
 */
package com.lckymn.kevin.jsonstatham.core.impl;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import com.lckymn.kevin.jsonstatham.core.JsonObjectConvertible;
import com.lckymn.kevin.jsonstatham.exception.JsonStathamException;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-06-03)
 */
public class OrgJsonJsonObjectConvertibleTest
{

	/**
	 * Test method for
	 * {@link com.lckymn.kevin.jsonstatham.core.impl.OrgJsonJsonObjectConvertible#OrgJsonJsonObjectConvertible(org.json.JSONObject)}.
	 */
	@Test
	public final void testOrgJsonJsonObjectConvertible()
	{
		final JSONObject jsonObject = new JSONObject();
		final JsonObjectConvertible jsonObjectConvertible = new OrgJsonJsonObjectConvertible(jsonObject);
		assertThat(jsonObjectConvertible.getActualObject(), is(instanceOf(JSONObject.class)));
		assertThat(((JSONObject) jsonObjectConvertible.getActualObject()), is(jsonObject));
		assertThat(((JSONObject) jsonObjectConvertible.getActualObject()), equalTo(jsonObject));
	}

	/**
	 * Test method for {@link com.lckymn.kevin.jsonstatham.core.impl.OrgJsonJsonObjectConvertible#put(java.lang.String, java.lang.Object)}.
	 * 
	 * @throws JSONException
	 */
	@Test
	public final void testPut() throws JSONException
	{
		final JSONObject jsonObject1 = new JSONObject();
		final JsonObjectConvertible jsonObjectConvertible1 = new OrgJsonJsonObjectConvertible(jsonObject1);
		jsonObjectConvertible1.put("name", "Kevin Lee");

		final JSONObject jsonObject2 = new JSONObject();
		final JsonObjectConvertible jsonObjectConvertible2 = new OrgJsonJsonObjectConvertible(jsonObject2);
		jsonObjectConvertible2.put("jsonObjectConvertible1", jsonObjectConvertible1);

		assertThat(((JSONObject) jsonObjectConvertible2.getActualObject()).get("jsonObjectConvertible1"),
				is(not(instanceOf(OrgJsonJsonObjectConvertible.class))));
		assertThat(((JSONObject) jsonObjectConvertible2.getActualObject()).get("jsonObjectConvertible1"), is(instanceOf(JSONObject.class)));
		assertThat(((JSONObject) jsonObject2.get("jsonObjectConvertible1")), is(jsonObject1));
		assertThat(((JSONObject) jsonObject2.get("jsonObjectConvertible1")), equalTo(jsonObject1));

		final JSONObject jsonObject3 = new JSONObject();
		final JsonObjectConvertible jsonObjectConvertible3 = new OrgJsonJsonObjectConvertible(jsonObject3);
		final String idName = "id";
		final Integer idValue = Integer.valueOf(999);
		final String surnameName = "surname";
		final String surnameValue = "Lee";
		final String givenNameName = "givenName";
		final String givenNameValue = "Kevin";
		jsonObjectConvertible3.put(idName, idValue);
		jsonObjectConvertible3.put(surnameName, surnameValue);
		jsonObjectConvertible3.put(givenNameName, givenNameValue);

		assertThat(((JSONObject) jsonObjectConvertible3.getActualObject()), is(jsonObject3));
		assertThat(((JSONObject) jsonObjectConvertible3.getActualObject()), equalTo(jsonObject3));
		assertThat((Integer) jsonObject3.get(idName), is(idValue));
		assertThat((String) jsonObject3.get(surnameName), is(surnameValue));
		assertThat((String) jsonObject3.get(givenNameName), is(givenNameValue));
		assertThat(jsonObjectConvertible3.toString(), equalTo(jsonObject3.toString()));
	}

	/**
	 * Test method for {@link com.lckymn.kevin.jsonstatham.core.impl.OrgJsonJsonObjectConvertible#put(java.lang.String, java.lang.Object)}.
	 * 
	 * @throws JSONException
	 */
	@Test(expected = JsonStathamException.class)
	public final void testPutWithNullKey() throws JSONException
	{
		new OrgJsonJsonObjectConvertible(new JSONObject()).put(null, "Test");
	}

	/**
	 * Test method for {@link com.lckymn.kevin.jsonstatham.core.impl.OrgJsonJsonObjectConvertible#getActualObject()}.
	 */
	@Test
	public final void testGetActualObject()
	{
		final JSONObject jsonObject1 = new JSONObject();
		final JsonObjectConvertible jsonObjectConvertible1 = new OrgJsonJsonObjectConvertible(jsonObject1);
		jsonObjectConvertible1.put("name", "Kevin Lee");

		final JSONObject jsonObject2 = new JSONObject();
		final JsonObjectConvertible jsonObjectConvertible2 = new OrgJsonJsonObjectConvertible(jsonObject2);
		jsonObjectConvertible2.put("jsonObjectConvertible1", jsonObjectConvertible1);

		assertThat(jsonObjectConvertible2.getActualObject(), is(instanceOf(JSONObject.class)));
		assertThat((JSONObject) jsonObjectConvertible2.getActualObject(), is(jsonObject2));
		assertThat((JSONObject) jsonObjectConvertible2.getActualObject(), equalTo(jsonObject2));

		final JSONObject jsonObject3 = new JSONObject();
		final JsonObjectConvertible jsonObjectConvertible3 = new OrgJsonJsonObjectConvertible(jsonObject3);
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
		assertThat(((JSONObject) jsonObjectConvertible3.getActualObject()), is(jsonObject3));
		assertThat(((JSONObject) jsonObjectConvertible3.getActualObject()), equalTo(jsonObject3));
	}

	/**
	 * Test method for {@link com.lckymn.kevin.jsonstatham.core.impl.OrgJsonJsonObjectConvertible#toString()}.
	 */
	@Test
	public final void testToString()
	{
		final JSONObject jsonObject1 = new JSONObject();
		final JsonObjectConvertible jsonObjectConvertible1 = new OrgJsonJsonObjectConvertible(jsonObject1);
		jsonObjectConvertible1.put("name", "Kevin Lee");

		final JSONObject jsonObject2 = new JSONObject();
		final JsonObjectConvertible jsonObjectConvertible2 = new OrgJsonJsonObjectConvertible(jsonObject2);
		jsonObjectConvertible2.put("jsonObjectConvertible1", jsonObjectConvertible1);

		assertThat(jsonObjectConvertible2.toString(), equalTo(jsonObject2.toString()));

		final JSONObject jsonObject3 = new JSONObject();
		final JsonObjectConvertible jsonObjectConvertible3 = new OrgJsonJsonObjectConvertible(jsonObject3);
		jsonObjectConvertible3.put("id", Integer.valueOf(999))
				.put("surname", "Lee")
				.put("givenName", "Kevin");

		assertThat(jsonObjectConvertible3.toString(), equalTo(jsonObject3.toString()));
	}

}

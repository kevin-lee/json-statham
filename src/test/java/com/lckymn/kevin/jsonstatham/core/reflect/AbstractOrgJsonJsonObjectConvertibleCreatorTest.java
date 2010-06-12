/**
 * 
 */
package com.lckymn.kevin.jsonstatham.core.reflect;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.json.JSONObject;
import org.junit.Test;

import com.lckymn.kevin.jsonstatham.core.JsonObjectConvertible;
import com.lckymn.kevin.jsonstatham.core.reflect.AbstractOrgJsonJsonObjectConvertibleCreator;
import com.lckymn.kevin.jsonstatham.core.reflect.OrgJsonJsonObjectConvertible;
import com.lckymn.kevin.jsonstatham.exception.JsonStathamException;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-06-03)
 */
public class AbstractOrgJsonJsonObjectConvertibleCreatorTest
{

	/**
	 * Test method for {@link com.lckymn.kevin.jsonstatham.core.reflect.AbstractOrgJsonJsonObjectConvertibleCreator#newJsonObjectConvertible()}
	 * .
	 */
	@Test
	public final void testNewJsonObjectConvertible()
	{
		final JsonObjectConvertible jsonObjectConvertible = new OrgJsonJsonObjectConvertible(new JSONObject());
		final AbstractOrgJsonJsonObjectConvertibleCreator orgJsonJsonObjectConvertibleCreator =
			new AbstractOrgJsonJsonObjectConvertibleCreator()
			{

				@Override
				public JsonObjectConvertible newJsonObjectConvertible()
				{
					return jsonObjectConvertible;
				}
			};

		assertThat(orgJsonJsonObjectConvertibleCreator.newJsonObjectConvertible(), is(jsonObjectConvertible));
		assertThat(orgJsonJsonObjectConvertibleCreator.newJsonObjectConvertible(), equalTo(jsonObjectConvertible));
	}

	/**
	 * Test method for
	 * {@link com.lckymn.kevin.jsonstatham.core.reflect.AbstractOrgJsonJsonObjectConvertibleCreator#nullJsonObjectConvertible()}.
	 */
	@Test
	public final void testNullJsonObjectConvertible()
	{
		final AbstractOrgJsonJsonObjectConvertibleCreator orgJsonJsonObjectConvertibleCreator =
			new AbstractOrgJsonJsonObjectConvertibleCreator()
			{

				@Override
				public JsonObjectConvertible newJsonObjectConvertible()
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

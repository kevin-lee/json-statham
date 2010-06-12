/**
 * 
 */
package com.lckymn.kevin.jsonstatham.core.reflect;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.lang.reflect.Field;

import org.json.JSONArray;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.lckymn.kevin.jsonstatham.core.JsonArrayConvertible;
import com.lckymn.kevin.jsonstatham.core.JsonArrayConvertibleCreator;
import com.lckymn.kevin.jsonstatham.core.reflect.OrgJsonJsonArrayConvertibleCreator;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-06-03)
 */
public class OrgJsonJsonArrayConvertibleCreatorTest
{

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception
	{
	}

	/**
	 * Test method for {@link com.lckymn.kevin.jsonstatham.core.reflect.OrgJsonJsonArrayConvertibleCreator#newJsonArrayConvertible()}.
	 * 
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	@Test
	public final void testNewJsonArrayConvertible() throws SecurityException, NoSuchFieldException, IllegalArgumentException,
			IllegalAccessException
	{
		final JsonArrayConvertibleCreator jsonArrayConvertibleCreator = new OrgJsonJsonArrayConvertibleCreator();
		final JsonArrayConvertible jsonArrayConvertible = jsonArrayConvertibleCreator.newJsonArrayConvertible();

		final Field jsonObjectField = jsonArrayConvertible.getClass()
				.getDeclaredField("jsonArray");
		jsonObjectField.setAccessible(true);
		Object jsonObject = jsonObjectField.get(jsonArrayConvertible);
		assertThat(jsonObject, notNullValue());
		assertThat(jsonObject, is(instanceOf(JSONArray.class)));
		assertSame(jsonObject.getClass(), JSONArray.class);
	}

}

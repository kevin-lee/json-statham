/**
 * 
 */
package com.lckymn.kevin.jsonstatham.core.impl;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.HashMap;

import org.json.JSONObject;
import org.junit.Test;

import com.lckymn.kevin.jsonstatham.core.JSONObjectCreator;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-02-03)
 */
public class HashMapJSONObjectCreatorTest
{
	/**
	 * Test method for {@link com.lckymn.kevin.jsonstatham.core.impl.HashMapJSONObjectCreator#newJSONObject()}.
	 * 
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	@Test
	public void testNewJSONObject() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException
	{
		JSONObjectCreator jsonObjectCreator = new HashMapJSONObjectCreator();
		JSONObject jsonObject = jsonObjectCreator.newJSONObject();

		Field mapField = jsonObject.getClass()
				.getDeclaredField("map");
		mapField.setAccessible(true);
		Object mapObject = mapField.get(jsonObject);
		assertThat(mapObject, notNullValue());
		assertThat(mapObject, is(HashMap.class));
		assertSame(mapObject.getClass(), HashMap.class);

	}
}

/**
 * 
 */
package com.lckymn.kevin.jsonstatham;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.lckymn.kevin.jsonstatham.core.JsonStatham;
import com.lckymn.kevin.jsonstatham.core.impl.OrderedNonIndentedJsonStatham;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.01 (2009-11-21)
 */
public class OrderedNonIndentedJsonStathamTest
{
	private JsonStatham jsonStatham;

	private static final String street = "ABC Street";
	private static final String suburb = "";
	private static final String city = "Sydney";
	private static final String state = "NSW";
	private static final String postcode = "2000";

	// private static final String dateValue = "2009-11-28";

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
		jsonStatham = new OrderedNonIndentedJsonStatham();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception
	{
	}

	/**
	 * Test method for {@link com.lckymn.kevin.jsonstatham.core.impl.AbstractJsonStatham#convertIntoJson(java.lang.Object)}.
	 */
	@Test
	public void testSimpleJsonObject()
	{
		Address address = new Address(street, suburb, city, state, postcode);

		final String expected = "{\"street\":\"" + street + "\",\"suburb\":\"" + suburb + "\",\"city\":\"" + city + "\",\"state\":\""
				+ state + "\",\"postcode\":\"" + postcode + "\"}";
		final String result = jsonStatham.convertIntoJson(address);
		System.out.println(result);
		assertEquals(expected, result);

	}

	/**
	 * Test method for {@link com.lckymn.kevin.jsonstatham.core.impl.AbstractJsonStatham#convertIntoJson(java.lang.Object)}.
	 */
	@Test
	public void testNestedJsonObject()
	{
		final long id = 1;
		final String name = "jsonObject";
		final Address address = new Address(street, suburb, city, state, postcode);
		// final Date date = new Date(2009, 11, 28);
		NestedJsonObject jsonObject = new NestedJsonObject();
		jsonObject.setPrimaryKey(Long.valueOf(id));
		jsonObject.setName(name);
		jsonObject.setAddress(address);
		// jsonObject.setDate(date);

		final String expected = "{\"id\":1,\"name\":\"jsonObject\",\"address\":{\"street\":\"" + street + "\",\"suburb\":\"" + suburb
				+ "\",\"city\":\"" + city + "\",\"state\":\"" + state + "\",\"postcode\":\"" + postcode + "\"}}";
		final String result = jsonStatham.convertIntoJson(jsonObject);
		System.out.println(result);
		assertEquals(expected, result);
	}
}

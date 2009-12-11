/**
 * 
 */
package com.lckymn.kevin.jsonstatham;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.lckymn.kevin.jsonstatham.core.JsonStatham;
import com.lckymn.kevin.jsonstatham.core.impl.OrderedNonIndentedJsonStatham;
import com.lckymn.kevin.jsonstatham.exception.JsonStathamException;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.01 (2009-11-21)
 */
public class NonIndentedJsonStathamTest
{
	private static final List<String> streetList = Arrays.asList("ABC Street", "90/120 Swanston St");
	private static final List<String> suburbList = Arrays.asList("", "Test Suburb");
	private static final List<String> cityList = Arrays.asList("Sydney", "Melbourne");
	private static final List<String> stateList = Arrays.asList("NSW", "VIC");
	private static final List<String> postcodeList = Arrays.asList("2000", "3000");

	private JsonStatham jsonStatham;

	private Address address;

	// private static final String dateValue = "2009-11-28";

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		System.out.println("### NonIndentedJsonStathamTest starts ###");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{
		System.out.println("\n### NonIndentedJsonStathamTest ends ###");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		jsonStatham = new OrderedNonIndentedJsonStatham();
		address = new Address(streetList.get(0), suburbList.get(0), cityList.get(0), stateList.get(0), postcodeList.get(0));
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception
	{
	}

	@Test(expected = JsonStathamException.class)
	public void testUnknownType()
	{
		class UnknownType
		{
		}
		jsonStatham.convertIntoJson(new UnknownType());
	}

	@Test
	public void testNull()
	{
		System.out.println("\nOrderedNonIndentedJsonStathamTest.testNull()");
		final String expected = "null";
		System.out.println("expected:\n" + expected);
		final String result = jsonStatham.convertIntoJson(null);
		System.out.println("result:\n" + result);
		assertEquals(expected.toString(), result);
	}

	/**
	 * Test method for {@link com.lckymn.kevin.jsonstatham.core.impl.AbstractJsonStatham#convertIntoJson(java.lang.Object)} with List as the
	 * parameter object.
	 */
	@Test
	public void testList()
	{
		List<Address> addressList = new ArrayList<Address>();
		for (int i = 0, size = stateList.size(); i < size; i++)
		{
			addressList.add(new Address(streetList.get(i), suburbList.get(i), cityList.get(i), stateList.get(i), postcodeList.get(i)));
		}

		final StringBuilder stringBuilder = new StringBuilder("[");
		for (Address address : addressList)
		{
			stringBuilder.append("{\"street\":\"")
					.append(address.getStreet())
					.append("\",\"suburb\":\"")
					.append(address.getSuburb())
					.append("\",\"city\":\"")
					.append(address.getCity())
					.append("\",\"state\":\"")
					.append(address.getState())
					.append("\",\"postcode\":\"")
					.append(address.getPostcode())
					.append("\"},");
		}
		if (1 < stringBuilder.length())
		{
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
		}
		stringBuilder.append("]");

		final String expected = stringBuilder.toString();
		System.out.println("\nOrderedNonIndentedJsonStathamTest.testList()");
		System.out.println("expected:\n" + expected);
		final String result = jsonStatham.convertIntoJson(addressList);
		System.out.println("result:\n" + result);
		assertEquals(expected, result);
	}

	/**
	 * Test method for {@link com.lckymn.kevin.jsonstatham.core.impl.AbstractJsonStatham#convertIntoJson(java.lang.Object)}.
	 */
	@Test
	public void testMap()
	{
		Map<String, Address> addressMap = new LinkedHashMap<String, Address>();
		for (int i = 0, size = stateList.size(); i < size; i++)
		{
			addressMap.put("address" + i, new Address(streetList.get(i), suburbList.get(i), cityList.get(i), stateList.get(i),
					postcodeList.get(i)));
		}

		final StringBuilder stringBuilder = new StringBuilder("{");
		for (Entry<String, Address> entry : addressMap.entrySet())
		{
			Address address = entry.getValue();
			stringBuilder.append("\"" + entry.getKey() + "\":")
					.append("{\"street\":\"")
					.append(address.getStreet())
					.append("\",\"suburb\":\"")
					.append(address.getSuburb())
					.append("\",\"city\":\"")
					.append(address.getCity())
					.append("\",\"state\":\"")
					.append(address.getState())
					.append("\",\"postcode\":\"")
					.append(address.getPostcode())
					.append("\"},");
		}
		if (1 < stringBuilder.length())
		{
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
		}
		stringBuilder.append("}");

		final String expected = stringBuilder.toString();
		System.out.println("\nOrderedNonIndentedJsonStathamTest.testMap()");
		System.out.println("expected:\n" + expected);
		final String result = jsonStatham.convertIntoJson(addressMap);
		System.out.println("result:\n" + result);
		assertEquals(expected.toString(), result);
	}

	/**
	 * Test method for {@link com.lckymn.kevin.jsonstatham.core.impl.AbstractJsonStatham#convertIntoJson(java.lang.Object)}.
	 */
	@Test
	public void testSimpleJsonObject()
	{
		final String expected = "{\"street\":\"" + streetList.get(0) + "\",\"suburb\":\"" + suburbList.get(0) + "\",\"city\":\""
				+ cityList.get(0) + "\",\"state\":\"" + stateList.get(0) + "\",\"postcode\":\"" + postcodeList.get(0) + "\"}";
		final String result = jsonStatham.convertIntoJson(address);
		System.out.println("\nOrderedNonIndentedJsonStathamTest.testSimpleJsonObject()");
		System.out.println("expected:\n" + expected);
		System.out.println("actual:\n" + result);
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
		// final Date date = new Date(2009, 11, 28);
		NestedJsonObject jsonObject = new NestedJsonObject();
		jsonObject.setPrimaryKey(Long.valueOf(id));
		jsonObject.setName(name);
		jsonObject.setAddress(address);
		// jsonObject.setDate(date);

		final String expected = "{\"id\":" + id + ",\"name\":\"" + name + "\",\"address\":{\"street\":\"" + streetList.get(0)
				+ "\",\"suburb\":\"" + suburbList.get(0) + "\",\"city\":\"" + cityList.get(0) + "\",\"state\":\"" + stateList.get(0)
				+ "\",\"postcode\":\"" + postcodeList.get(0) + "\"}}";
		final String result = jsonStatham.convertIntoJson(jsonObject);
		System.out.println("\nOrderedNonIndentedJsonStathamTest.testNestedJsonObject()");
		System.out.println("expected:\n" + expected);
		System.out.println("actual:\n" + result);
		assertEquals(expected, result);
	}
}

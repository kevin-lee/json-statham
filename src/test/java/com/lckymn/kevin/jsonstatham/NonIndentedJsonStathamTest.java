/**
 * 
 */
package com.lckymn.kevin.jsonstatham;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
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
import com.lckymn.kevin.jsonstatham.core.impl.NonIndentedJsonStatham;
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

	private List<Address> addressList;

	private Map<String, Address> addressMap;

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
		jsonStatham = new NonIndentedJsonStatham();
		address = new Address(streetList.get(0), suburbList.get(0), cityList.get(0), stateList.get(0), postcodeList.get(0));

		addressList = new ArrayList<Address>();
		for (int i = 0, size = streetList.size(); i < size; i++)
		{
			addressList.add(new Address(streetList.get(i), suburbList.get(i), cityList.get(i), stateList.get(i), postcodeList.get(i)));
		}

		addressMap = new LinkedHashMap<String, Address>();
		for (int i = 0, size = streetList.size(); i < size; i++)
		{
			addressMap.put("address" + i, new Address(streetList.get(i), suburbList.get(i), cityList.get(i), stateList.get(i),
					postcodeList.get(i)));
		}
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
		System.out.println("actual: ");
		final String result = jsonStatham.convertIntoJson(null);
		System.out.println(result);
		assertEquals(expected.toString(), result);
	}

	private String getAddressArrayString()
	{
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
		return stringBuilder.toString();
	}

	@Test
	public void testArray()
	{
		System.out.println("\nNonIndentedJsonStathamTest.testArray()");
		final String expected = getAddressArrayString();
		System.out.println("expected:\n" + expected);
		System.out.println("actual: ");
		final String result = jsonStatham.convertIntoJson(addressList.toArray(new Address[addressList.size()]));
		System.out.println(result);
		assertEquals(expected, result);
	}

	/**
	 * Test method for {@link com.lckymn.kevin.jsonstatham.core.impl.NonIndentedJsonStatham#convertIntoJson(java.lang.Object)} with List as
	 * the parameter object.
	 */
	@Test
	public void testList()
	{
		final String expected = getAddressArrayString();
		System.out.println("\nOrderedNonIndentedJsonStathamTest.testList()");
		System.out.println("expected:\n" + expected);
		System.out.println("actual: ");
		final String result = jsonStatham.convertIntoJson(addressList);
		System.out.println(result);
		assertEquals(expected, result);
	}

	private String getAddressMapString()
	{
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
		return stringBuilder.toString();
	}

	/**
	 * Test method for {@link com.lckymn.kevin.jsonstatham.core.impl.NonIndentedJsonStatham#convertIntoJson(java.lang.Object)}.
	 */
	@Test
	public void testMap()
	{
		final String expected = getAddressMapString();
		System.out.println("\nOrderedNonIndentedJsonStathamTest.testMap()");
		System.out.println("expected:\n" + expected);
		System.out.println("actual: ");
		final String result = jsonStatham.convertIntoJson(addressMap);
		System.out.println(result);
		assertEquals(expected, result);
	}

	@Test
	public void testNestedMap()
	{
		final String expected = "{\"test1\":" + getAddressMapString() + ",\"test2\":" + getAddressMapString() + "}";
		System.out.println("\nNonIndentedJsonStathamTest.testNestedMap()");
		System.out.println("expected: \n" + expected);
		Map<String, Object> nestedMap = new HashMap<String, Object>();
		nestedMap.put("test1", addressMap);
		nestedMap.put("test2", addressMap);
		System.out.println("actual: ");
		final String result = jsonStatham.convertIntoJson(nestedMap);
		System.out.println(result);
		assertEquals(expected, result);
	}

	/**
	 * Test method for {@link com.lckymn.kevin.jsonstatham.core.impl.NonIndentedJsonStatham#convertIntoJson(java.lang.Object)}.
	 */
	@Test
	public void testSimpleJsonObject()
	{
		final String expected = "{\"street\":\"" + streetList.get(0) + "\",\"suburb\":\"" + suburbList.get(0) + "\",\"city\":\""
				+ cityList.get(0) + "\",\"state\":\"" + stateList.get(0) + "\",\"postcode\":\"" + postcodeList.get(0) + "\"}";
		System.out.println("\nOrderedNonIndentedJsonStathamTest.testSimpleJsonObject()");
		System.out.println("expected:\n" + expected);
		System.out.println("actual: ");
		final String result = jsonStatham.convertIntoJson(address);
		System.out.println(result);
		assertEquals(expected, result);
	}

	/**
	 * Test method for {@link com.lckymn.kevin.jsonstatham.core.impl.NonIndentedJsonStatham#convertIntoJson(java.lang.Object)}.
	 */
	@Test
	public void testNestedJsonObject()
	{
		final long id = 1;
		final String name = "jsonObject";
		NestedJsonObject jsonObject = new NestedJsonObject();
		jsonObject.setPrimaryKey(Long.valueOf(id));
		jsonObject.setName(name);
		jsonObject.setAddress(address);

		final String expected = "{\"id\":" + id + ",\"name\":\"" + name + "\",\"address\":{\"street\":\"" + streetList.get(0)
				+ "\",\"suburb\":\"" + suburbList.get(0) + "\",\"city\":\"" + cityList.get(0) + "\",\"state\":\"" + stateList.get(0)
				+ "\",\"postcode\":\"" + postcodeList.get(0) + "\"}}";
		System.out.println("\nOrderedNonIndentedJsonStathamTest.testNestedJsonObject()");
		System.out.println("expected:\n" + expected);
		System.out.println("actual: ");
		final String result = jsonStatham.convertIntoJson(jsonObject);
		System.out.println(result);
		assertEquals(expected, result);
	}

	@Test(expected = JsonStathamException.class)
	public void testJsonObjectWithDuplicateKeys() throws IOException
	{
		JsonObjectWithDuplicateKeys jsonObjectWithDuplicateKeys = new JsonObjectWithDuplicateKeys();
		jsonObjectWithDuplicateKeys.setUsername("kevinlee");
		jsonObjectWithDuplicateKeys.setName("Kevin");
		jsonObjectWithDuplicateKeys.setFullName("Kevin Lee");
		jsonObjectWithDuplicateKeys.setEmail("kevin@test.test");

		System.out.println("\nresult: ");
		String result = "";
		try
		{
			result = jsonStatham.convertIntoJson(jsonObjectWithDuplicateKeys);
		}
		catch (JsonStathamException e)
		{
			System.out.println(e.getMessage());
			throw e;
		}
		System.out.println(result);
	}

	@Test
	public void testComplexJsonObjectWithMethodUse()
	{
		ComplexJsonObjectWithMethodUse jsonObject = new ComplexJsonObjectWithMethodUse();
		jsonObject.setPrimaryKey(Long.valueOf(1));
		jsonObject.setName("Kevin");
		jsonObject.setAddress(address);
		Date date = new Date();
		jsonObject.setDateWithoutValueAccessor(date);
		jsonObject.setDate(date);

		final String expected = "{\"id\":1,\"name\":\"Kevin\","
				+ "\"address\":{\"street\":\"ABC Street\",\"suburb\":\"\",\"city\":\"Sydney\",\"state\":\"NSW\",\"postcode\":\"2000\"},"
				+ "\"dateWithoutValueAccessor\":\"" + date.toString() + "\"," + "\"date\":\"" + jsonObject.getDateString() + "\"}";
		System.out.println("\nNonIndentedJsonStathamTest.testComplexJsonObjectWithMethodUse()");
		System.out.println("expected:\n" + expected);
		System.out.println("actual: ");
		final String result = jsonStatham.convertIntoJson(jsonObject);
		System.out.println(result);
		assertThat(result, is(expected));
	}
}

/**
 * 
 */
package com.lckymn.kevin.jsonstatham.core.reflect;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.lckymn.kevin.jsonstatham.Address;
import com.lckymn.kevin.jsonstatham.ComplexJsonObjectWithValueAccessor;
import com.lckymn.kevin.jsonstatham.ComplexJsonObjectWithValueAccessorWithoutItsName;
import com.lckymn.kevin.jsonstatham.JsonObjectContainingCollection;
import com.lckymn.kevin.jsonstatham.JsonObjectContainingEnums;
import com.lckymn.kevin.jsonstatham.JsonObjectContainingIterable;
import com.lckymn.kevin.jsonstatham.JsonObjectContainingIterator;
import com.lckymn.kevin.jsonstatham.JsonObjectContainingList;
import com.lckymn.kevin.jsonstatham.JsonObjectContainingMapEntrySet;
import com.lckymn.kevin.jsonstatham.JsonObjectContainingSet;
import com.lckymn.kevin.jsonstatham.JsonObjectPojo;
import com.lckymn.kevin.jsonstatham.JsonObjectPojoImpl;
import com.lckymn.kevin.jsonstatham.JsonObjectPojoProxyFactory;
import com.lckymn.kevin.jsonstatham.JsonObjectWithDuplicateKeys;
import com.lckymn.kevin.jsonstatham.JsonObjectWithoutFieldName;
import com.lckymn.kevin.jsonstatham.NestedJsonObject;
import com.lckymn.kevin.jsonstatham.NestedJsonObjectWithValueAccessor;
import com.lckymn.kevin.jsonstatham.SecondSubClassWithOwnFields;
import com.lckymn.kevin.jsonstatham.SecondSubClassWithoutOwnFields;
import com.lckymn.kevin.jsonstatham.SomeImplementingClass;
import com.lckymn.kevin.jsonstatham.SomeInterface;
import com.lckymn.kevin.jsonstatham.SubClass;
import com.lckymn.kevin.jsonstatham.SubClassWithNoJsonObjectSuperClass;
import com.lckymn.kevin.jsonstatham.SubClassWithValueAccessor;
import com.lckymn.kevin.jsonstatham.SubClassWithValueAccessorWithAbstractMethod;
import com.lckymn.kevin.jsonstatham.SubClassWithValueAccessorWithOverriddenMethod;
import com.lckymn.kevin.jsonstatham.SubClassWithValueAccessorWithoutItsName;
import com.lckymn.kevin.jsonstatham.JsonObjectContainingEnums.Access;
import com.lckymn.kevin.jsonstatham.JsonObjectContainingEnums.Role;
import com.lckymn.kevin.jsonstatham.core.JsonArrayConvertible;
import com.lckymn.kevin.jsonstatham.core.JsonArrayConvertibleCreator;
import com.lckymn.kevin.jsonstatham.core.JsonObjectConvertible;
import com.lckymn.kevin.jsonstatham.core.JsonObjectConvertibleCreator;
import com.lckymn.kevin.jsonstatham.core.JsonStatham;
import com.lckymn.kevin.jsonstatham.core.reflect.AbstractOrgJsonJsonObjectConvertibleCreator;
import com.lckymn.kevin.jsonstatham.core.reflect.KnownBasicTypeDecider;
import com.lckymn.kevin.jsonstatham.core.reflect.KnownDataStructureTypeProcessorDecider;
import com.lckymn.kevin.jsonstatham.core.reflect.KnownObjectReferenceTypeProcessorDecider;
import com.lckymn.kevin.jsonstatham.core.reflect.OrgJsonJsonArrayConvertible;
import com.lckymn.kevin.jsonstatham.core.reflect.OrgJsonJsonObjectConvertible;
import com.lckymn.kevin.jsonstatham.core.reflect.ReflectionJsonStatham;
import com.lckymn.kevin.jsonstatham.exception.JsonStathamException;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2009-11-21)
 * @version 0.0.2 (2010-03-06) more test cases including the one testing proxy object created by javassist are added.
 * @version 0.0.3 (2010-05-10) test case for testing enum type fields is added.
 */
public class ReflectionJsonStathamTest
{
	private static final List<String> streetList = Arrays.asList("ABC Street", "90/120 Swanston St");
	private static final List<String> suburbList = Arrays.asList("", "Test Suburb");
	private static final List<String> cityList = Arrays.asList("Sydney", "Melbourne");
	private static final List<String> stateList = Arrays.asList("NSW", "VIC");
	private static final List<String> postcodeList = Arrays.asList("2000", "3000");
	private static final String[] SOME_STRING_VALUE_ARRAY = { "111", "222", "aaa", "bbb", "ccc" };

	private static final Answer<JsonObjectConvertible> ANSWER_FOR_NEW_JSON_OBJECT_CONVERTIBLE = new Answer<JsonObjectConvertible>()
	{
		@Override
		public JsonObjectConvertible answer(@SuppressWarnings("unused") InvocationOnMock invocation) throws Throwable
		{
			return new OrgJsonJsonObjectConvertible(new JSONObject(new LinkedHashMap<String, Object>()));
		}
	};
	private static final Answer<JsonObjectConvertible> ANSWER_FOR_NULL_JSON_OBJECT_CONVERTIBLE = new Answer<JsonObjectConvertible>()
	{

		@Override
		public JsonObjectConvertible answer(@SuppressWarnings("unused") InvocationOnMock invocation) throws Throwable
		{
			return AbstractOrgJsonJsonObjectConvertibleCreator.NULL_JSON_OBJECT_CONVERTIBLE;
		}

	};

	private static final Answer<JsonArrayConvertible> ANSWER_FOR_JSON_ARRAY_CONVERTIBLE = new Answer<JsonArrayConvertible>()
	{

		@Override
		public JsonArrayConvertible answer(InvocationOnMock invocation) throws Throwable
		{
			return new OrgJsonJsonArrayConvertible(new JSONArray());
		}
	};

	private List<Address> addressList;

	private Map<String, Address> addressMap;

	private JsonStatham jsonStatham;

	private Address address;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		System.out.println("### ReflectionJsonStathamTest starts ###");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{
		System.out.println("\n### ReflectionJsonStathamTest ends ###");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		final JsonObjectConvertibleCreator jsonObjectCreator = mock(JsonObjectConvertibleCreator.class);
		when(jsonObjectCreator.newJsonObjectConvertible()).thenAnswer(ANSWER_FOR_NEW_JSON_OBJECT_CONVERTIBLE);
		when(jsonObjectCreator.nullJsonObjectConvertible()).thenAnswer(ANSWER_FOR_NULL_JSON_OBJECT_CONVERTIBLE);

		final JsonArrayConvertibleCreator jsonArrayCreator = mock(JsonArrayConvertibleCreator.class);
		when(jsonArrayCreator.newJsonArrayConvertible()).thenAnswer(ANSWER_FOR_JSON_ARRAY_CONVERTIBLE);

		jsonStatham =
			new ReflectionJsonStatham(jsonObjectCreator, jsonArrayCreator, new KnownDataStructureTypeProcessorDecider(),
					new KnownObjectReferenceTypeProcessorDecider(), new KnownBasicTypeDecider());
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
		System.out.println("\nReflectionJsonStathamTest.testNull()");
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
		System.out.println("\nReflectionJsonStathamTest.testArray()");
		final String expected = getAddressArrayString();
		System.out.println("expected:\n" + expected);
		System.out.println("actual: ");
		final String result = jsonStatham.convertIntoJson(addressList.toArray(new Address[addressList.size()]));
		System.out.println(result);
		assertEquals(expected, result);
	}

	/**
	 * Test method for {@link com.lckymn.kevin.jsonstatham.core.reflect.ReflectionJsonStatham#convertIntoJson(java.lang.Object)} with List as
	 * the parameter object.
	 */
	@Test
	public void testList()
	{
		final String expected = getAddressArrayString();
		System.out.println("\nReflectionJsonStathamTest.testList()");
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
	 * Test method for {@link com.lckymn.kevin.jsonstatham.core.reflect.ReflectionJsonStatham#convertIntoJson(java.lang.Object)}.
	 */
	@Test
	public void testMap()
	{
		final String expected = getAddressMapString();
		System.out.println("\nReflectionJsonStathamTest.testMap()");
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
		System.out.println("\nReflectionJsonStathamTest.testNestedMap()");
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
	 * Test method for {@link com.lckymn.kevin.jsonstatham.core.reflect.ReflectionJsonStatham#convertIntoJson(java.lang.Object)}.
	 */
	@Test
	public void testAddress()
	{
		System.out.println("\nReflectionJsonStathamTest.testAddress()");

		final String expected =
			"{\"street\":\"" + streetList.get(0) + "\",\"suburb\":\"" + suburbList.get(0) + "\",\"city\":\"" + cityList.get(0)
					+ "\",\"state\":\"" + stateList.get(0) + "\",\"postcode\":\"" + postcodeList.get(0) + "\"}";
		System.out.println("expected:\n" + expected);
		System.out.println("actual: ");
		final String result = jsonStatham.convertIntoJson(address);
		System.out.println(result);
		assertEquals(expected, result);
	}

	/**
	 * Test method for {@link com.lckymn.kevin.jsonstatham.core.reflect.ReflectionJsonStatham#convertIntoJson(java.lang.Object)}.
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

		final String expected =
			"{\"id\":" + id + ",\"name\":\"" + name + "\",\"address\":{\"street\":\"" + streetList.get(0) + "\",\"suburb\":\""
					+ suburbList.get(0) + "\",\"city\":\"" + cityList.get(0) + "\",\"state\":\"" + stateList.get(0) + "\",\"postcode\":\""
					+ postcodeList.get(0) + "\"}}";
		System.out.println("\nReflectionJsonStathamTest.testNestedJsonObject()");
		System.out.println("expected:\n" + expected);
		System.out.println("actual: ");
		final String result = jsonStatham.convertIntoJson(jsonObject);
		System.out.println(result);
		assertThat(result, equalTo(expected));
	}

	@Test(expected = JsonStathamException.class)
	public void testJsonObjectWithDuplicateKeys() throws IOException
	{
		System.out.println("\nReflectionJsonStathamTest.testJsonObjectWithDuplicateKeys()");
		JsonObjectWithDuplicateKeys jsonObjectWithDuplicateKeys = new JsonObjectWithDuplicateKeys();
		jsonObjectWithDuplicateKeys.setUsername("kevinlee");
		jsonObjectWithDuplicateKeys.setName("Kevin");
		jsonObjectWithDuplicateKeys.setFullName("Kevin Lee");
		jsonObjectWithDuplicateKeys.setEmail("kevin@test.test");

		System.out.println("result: ");
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
	public void testJsonObjectWithoutFieldName()
	{
		System.out.println("\nReflectionJsonStathamTest.testJsonObjectWithoutFieldName()");
		final int id = 5;
		final String name = "Kevin Lee";
		final String address = "123 ABC Street";
		final JsonObjectWithoutFieldName jsonObjectWithoutFieldName = new JsonObjectWithoutFieldName(id, name, address);
		final String expected = "{\"id\":" + id + ",\"name\":\"" + name + "\",\"address\":\"" + address + "\"}";
		System.out.println("expected:\n" + expected);
		System.out.println("actual: ");
		final String result = jsonStatham.convertIntoJson(jsonObjectWithoutFieldName);
		System.out.println(result);
		assertThat(result, equalTo(expected));
	}

	@Test
	public void testComplexJsonObjectWithMethodUse()
	{
		ComplexJsonObjectWithValueAccessor jsonObject = new ComplexJsonObjectWithValueAccessor();
		jsonObject.setPrimaryKey(Long.valueOf(1));
		jsonObject.setName("Kevin");
		jsonObject.setAddress(address);
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		jsonObject.setDate(date);
		jsonObject.setDateWithValueAccessor(date);
		jsonObject.setCalendar(calendar);
		jsonObject.setCalendarWithValueAccessor(calendar);

		final String expected =
			"{\"id\":1,\"name\":\"Kevin\"," + "\"address\":{\"street\":\"" + address.getStreet() + "\",\"suburb\":\"" + address.getSuburb()
					+ "\",\"city\":\"" + address.getCity() + "\",\"state\":\"" + address.getState() + "\",\"postcode\":\""
					+ address.getPostcode() + "\"}," + "\"date\":\"" + date.toString() + "\"," + "\"dateWithValueAccessor\":\""
					+ jsonObject.getDateString() + "\",\"calendar\":\"" + jsonObject.getCalendar()
							.getTime()
							.toString() + "\",\"calendarWithValueAccessor\":\"" + jsonObject.getCalendarString() + "\"}";
		System.out.println("\nReflectionJsonStathamTest.testComplexJsonObjectWithMethodUse()");
		System.out.println("expected:\n" + expected);
		System.out.println("actual: ");
		final String result = jsonStatham.convertIntoJson(jsonObject);
		System.out.println(result);
		assertThat(result, equalTo(expected));
	}

	@Test
	public void testComplexJsonObjectWithValueAccessorWithoutItsName()
	{
		ComplexJsonObjectWithValueAccessorWithoutItsName jsonObject = new ComplexJsonObjectWithValueAccessorWithoutItsName();
		jsonObject.setPrimaryKey(Long.valueOf(1));
		jsonObject.setName("Kevin");
		jsonObject.setRegistered(true);
		jsonObject.setAddress(address);
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		jsonObject.setDate(date);
		jsonObject.setDateWithValueAccessor(date);
		jsonObject.setCalendar(calendar);
		jsonObject.setCalendarWithValueAccessor(calendar);

		final String expected =
			"{\"id\":1,\"name\":\"Kevin\",\"registered\":true,\"address\":{\"street\":\"" + address.getStreet() + "\",\"suburb\":\""
					+ address.getSuburb() + "\",\"city\":\"" + address.getCity() + "\",\"state\":\"" + address.getState()
					+ "\",\"postcode\":\"" + address.getPostcode() + "\"}," + "\"date\":\"" + date.toString() + "\","
					+ "\"dateWithValueAccessor\":\"" + jsonObject.getDateWithValueAccessor() + "\",\"calendar\":\""
					+ jsonObject.getCalendar()
							.getTime()
							.toString() + "\",\"calendarWithValueAccessor\":\"" + jsonObject.getCalendarWithValueAccessor() + "\"}";
		System.out.println("\nReflectionJsonStathamTest.testComplexJsonObjectWithValueAccessorWithoutItsName()");
		System.out.println("expected:\n" + expected);
		System.out.println("actual: ");
		final String result = jsonStatham.convertIntoJson(jsonObject);
		System.out.println(result);
		assertThat(result, equalTo(expected));
	}

	private String getExpectedJsonArray(String name, String value, String setName)
	{
		StringBuilder stringBuilder = new StringBuilder("{\"").append(name)
				.append("\":\"")
				.append(value)
				.append("\",\"")
				.append(setName)
				.append("\":[");
		for (String element : SOME_STRING_VALUE_ARRAY)
		{
			stringBuilder.append("\"")
					.append(element)
					.append("\"")
					.append(",");
		}
		return stringBuilder.deleteCharAt(stringBuilder.length() - 1)
				.append("]}")
				.toString();
	}

	private <V extends Object, T extends Collection<V>> T initialiseCollectionWithStringValues(T t, V... values)
	{
		for (V value : values)
		{
			t.add(value);
		}
		return t;
	}

	@Test
	public void testJsonObjectContainingCollection()
	{
		final String nameValue = "testJsonWithCollection";
		Collection<String> collection = initialiseCollectionWithStringValues(new ArrayList<String>(), SOME_STRING_VALUE_ARRAY);

		JsonObjectContainingCollection jsonObjectContainingCollection = new JsonObjectContainingCollection(nameValue, collection);
		final String expected = getExpectedJsonArray("name", nameValue, "valueCollection");
		System.out.println("\nReflectionJsonStathamTest.testJsonObjectContainingCollection()");
		System.out.println("expected:\n" + expected);
		System.out.println("actual: ");
		final String result = jsonStatham.convertIntoJson(jsonObjectContainingCollection);
		System.out.println(result);
		assertThat(result, equalTo(expected));
	}

	@Test
	public void testJsonObjectContainingList()
	{
		final String nameValue = "testJsonWithList";
		List<String> list = initialiseCollectionWithStringValues(new ArrayList<String>(), SOME_STRING_VALUE_ARRAY);

		JsonObjectContainingList jsonObjectContainingList = new JsonObjectContainingList(nameValue, list);
		final String expected = getExpectedJsonArray("name", nameValue, "valueList");
		System.out.println("\nReflectionJsonStathamTest.testJsonObjectContainingList()");
		System.out.println("expected:\n" + expected);
		System.out.println("actual: ");
		final String result = jsonStatham.convertIntoJson(jsonObjectContainingList);
		System.out.println(result);
		assertThat(result, equalTo(expected));
	}

	@Test
	public void testJsonObjectContainingSet()
	{
		final String nameValue = "testJsonWithSet";
		Set<String> set = initialiseCollectionWithStringValues(new LinkedHashSet<String>(), SOME_STRING_VALUE_ARRAY);

		JsonObjectContainingSet jsonObjectContainingSet = new JsonObjectContainingSet(nameValue, set);
		final String expected = getExpectedJsonArray("name", nameValue, "valueSet");
		System.out.println("\nReflectionJsonStathamTest.testJsonObjectContainingSet()");
		System.out.println("expected:\n" + expected);
		System.out.println("actual: ");
		final String result = jsonStatham.convertIntoJson(jsonObjectContainingSet);
		System.out.println(result);
		assertThat(result, equalTo(expected));
	}

	@Test
	public void testJsonObjectContainingMapEntrySetSet()
	{
		final String nameValue = "testJsonObjectContainingMapEntrySetSet";

		JsonObjectContainingMapEntrySet jsonObjectContainingSet = new JsonObjectContainingMapEntrySet(nameValue, addressMap.entrySet());

		StringBuilder stringBuilder = new StringBuilder("{\"name\":\"testJsonObjectContainingMapEntrySetSet\",\"valueMapEntrySet\":[");
		for (Entry<String, Address> entry : addressMap.entrySet())
		{
			Address address = entry.getValue();
			stringBuilder.append("{\"" + entry.getKey() + "\":")
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
					.append("\"}},");
		}
		final String expected = stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), "]}")
				.toString();

		System.out.println("\nReflectionJsonStathamTest.testJsonObjectContainingMapEntrySetSet()");
		System.out.println("expected:\n" + expected);
		System.out.println("actual: ");
		final String result = jsonStatham.convertIntoJson(jsonObjectContainingSet);
		System.out.println(result);
		assertThat(result, equalTo(expected));
	}

	@Test
	public void testJsonObjectContainingIterator()
	{
		final String nameValue = "testJsonObjectContainingIterator";
		Collection<String> collection = initialiseCollectionWithStringValues(new ArrayList<String>(), SOME_STRING_VALUE_ARRAY);

		JsonObjectContainingIterator jsonObjectContainingCollection = new JsonObjectContainingIterator(nameValue, collection.iterator());
		final String expected = getExpectedJsonArray("name", nameValue, "valueIterator");
		System.out.println("\nReflectionJsonStathamTest.testJsonObjectContainingIterator()");
		System.out.println("expected:\n" + expected);
		System.out.println("actual: ");
		final String result = jsonStatham.convertIntoJson(jsonObjectContainingCollection);
		System.out.println(result);
		assertThat(result, equalTo(expected));
	}

	@Test
	public void testJsonObjectContainingIterable()
	{
		final String nameValue = "testJsonObjectContainingIterable";
		Iterable<String> iterable = new Iterable<String>()
		{
			@Override
			public Iterator<String> iterator()
			{
				return initialiseCollectionWithStringValues(new ArrayList<String>(), SOME_STRING_VALUE_ARRAY).iterator();
			}
		};

		JsonObjectContainingIterable jsonObjectContainingCollection = new JsonObjectContainingIterable(nameValue, iterable);
		final String expected = getExpectedJsonArray("name", nameValue, "valueIterable");
		System.out.println("\nReflectionJsonStathamTest.testJsonObjectContainingIterator()");
		System.out.println("expected:\n" + expected);
		System.out.println("actual: ");
		final String result = jsonStatham.convertIntoJson(jsonObjectContainingCollection);
		System.out.println(result);
		assertThat(result, equalTo(expected));
	}

	@Test
	public void testJsonObjectWithInterfaceInheritance()
	{
		final String name = "Kevin Lee";
		final int number = 99;
		final String email = "kevinlee@test.test";
		SomeInterface jsonObject = new SomeImplementingClass(name, number, email);
		System.out.println("\nReflectionJsonStathamTest.testJsonObjectWithInterfaceInheritance()");
		final String expected = "{\"name\":\"" + name + "\",\"number\":" + number + ",\"email\":\"" + email + "\"}";
		System.out.println("expected:\n" + expected);
		System.out.println("actual: ");
		final String result = jsonStatham.convertIntoJson(jsonObject);
		System.out.println(result);
		assertThat(result, equalTo(expected));
	}

	@Test
	public void testJsonObjectWithImplementationInheritance()
	{
		final String name = "Kevin";
		final int number = 5;
		final String email = "kevin@test.test";
		SubClass jsonObject = new SubClass(name, number, email);
		System.out.println("\nReflectionJsonStathamTest.testJsonObjectWithImplementationInheritance()");
		final String expected = "{\"name\":\"" + name + "\",\"number\":" + number + ",\"email\":\"" + email + "\"}";
		System.out.println("expected:\n" + expected);
		System.out.println("actual: ");
		final String result = jsonStatham.convertIntoJson(jsonObject);
		System.out.println(result);
		assertThat(result, equalTo(expected));
	}

	@Test
	public void testJsonObjectWithDoubleImplementationInheritanceAndNoOwnFieldsInSecondSubClass()
	{
		final String name = "Kevin";
		final int number = 11;
		final String email = "kevin@test.blahblah";
		SubClass jsonObject = new SecondSubClassWithoutOwnFields(name, number, email);
		System.out.println("\nReflectionJsonStathamTest.testJsonObjectWithDoubleImplementationInheritanceAndNoOwnFieldsInSecondSubClass()");
		final String expected = "{\"name\":\"" + name + "\",\"number\":" + number + ",\"email\":\"" + email + "\"}";
		System.out.println("expected:\n" + expected);
		System.out.println("actual: ");
		final String result = jsonStatham.convertIntoJson(jsonObject);
		System.out.println(result);
		assertThat(result, equalTo(expected));
	}

	@Test
	public void testJsonObjectWithDoubleImplementationInheritanceAndOwnFieldsInSecondSubClass()
	{
		final String name = "Mr. Lee";
		final int number = 999;
		final String email = "kevin@another.email";
		final String comment = "Blah blah";
		SecondSubClassWithOwnFields jsonObject = new SecondSubClassWithOwnFields(name, number, email, address, comment);
		System.out.println("\nReflectionJsonStathamTest.testJsonObjectWithDoubleImplementationInheritanceAndOwnFieldsInSecondSubClass()");
		final String expected =
			"{\"name\":\"" + name + "\",\"number\":" + number + ",\"email\":\"" + email + "\"," + "\"address\":" + "{\"street\":\""
					+ address.getStreet() + "\",\"suburb\":\"" + address.getSuburb() + "\",\"city\":\"" + address.getCity()
					+ "\",\"state\":\"" + address.getState() + "\",\"postcode\":\"" + address.getPostcode() + "\"},\"comment\":\""
					+ comment + "\"}";
		System.out.println("expected:\n" + expected);
		System.out.println("actual: ");
		final String result = jsonStatham.convertIntoJson(jsonObject);
		System.out.println(result);
		assertThat(result, equalTo(expected));
	}

	@Test
	public void testJsonObjectWithImplementationInheritanceWithNoJsonObjectSuperClass()
	{
		final String name = "Kevin";
		final int number = 5;
		final String email = "kevin@test.test";
		SubClassWithNoJsonObjectSuperClass jsonObject = new SubClassWithNoJsonObjectSuperClass(name, number, email);
		System.out.println("\nReflectionJsonStathamTest.testJsonObjectWithImplementationInheritanceWithNoJsonObjectSuperClass()");
		final String expected = "{\"email\":\"" + email + "\"}";
		System.out.println("expected:\n" + expected);
		System.out.println("actual: ");
		final String result = jsonStatham.convertIntoJson(jsonObject);
		System.out.println(result);
		assertThat(result, equalTo(expected));
	}

	@Test
	public void testJsonObjectWithImplementationInheritanceWithValueAccessor()
	{
		final String name = "Kevin";
		final int number = 5;
		final String email = "kevin@test.test";
		SubClassWithValueAccessor jsonObject = new SubClassWithValueAccessor(name, number, email);
		System.out.println("\nReflectionJsonStathamTest.testJsonObjectWithImplementationInheritanceWithValueAccessor()");
		final String expected =
			"{\"name\":\"My name is " + name + "\",\"number\":\"The number is " + number + "\",\"email\":\"My email address is " + email
					+ "\"}";
		System.out.println("expected:\n" + expected);
		System.out.println("actual: ");
		final String result = jsonStatham.convertIntoJson(jsonObject);
		System.out.println(result);
		assertThat(result, equalTo(expected));
	}

	@Test
	public void testJsonObjectWithImplementationInheritanceWithValueAccessorWithoutItsName()
	{
		final String name = "Kevin";
		final int number = 5;
		final String email = "kevin@test.test";
		SubClassWithValueAccessorWithoutItsName jsonObject = new SubClassWithValueAccessorWithoutItsName(name, number, email);
		System.out.println("\nReflectionJsonStathamTest.testJsonObjectWithImplementationInheritanceWithValueAccessorWithoutItsName()");
		final String expected =
			"{\"name\":\"My name is " + name + "\",\"number\":\"The number is " + number + "\",\"email\":\"My email address is " + email
					+ "\"}";
		System.out.println("expected:\n" + expected);
		System.out.println("actual: ");
		final String result = jsonStatham.convertIntoJson(jsonObject);
		System.out.println(result);
		assertThat(result, equalTo(expected));
	}

	@Test
	public void testJsonObjectWithImplementationInheritanceWithValueAccessorWithAbstractMethod()
	{
		final String name = "Kevin";
		final int number = 5;
		final String email = "kevin@test.test";
		SubClassWithValueAccessorWithAbstractMethod jsonObject = new SubClassWithValueAccessorWithAbstractMethod(name, number, email);
		System.out.println("\nReflectionJsonStathamTest.testJsonObjectWithImplementationInheritanceWithValueAccessorWithAbstractMethod()");
		final String expected =
			"{\"name\":\"My name is nobody.\",\"number\":\"The number is 100.\",\"email\":\"My email address is " + email + "\"}";
		System.out.println("expected:\n" + expected);
		System.out.println("actual: ");
		final String result = jsonStatham.convertIntoJson(jsonObject);
		System.out.println(result);
		assertThat(result, equalTo(expected));
	}

	@Test
	public void testJsonObjectWithImplementationInheritanceWithValueAccessorWithOverriddenMethod()
	{
		final String name = "Kevin";
		final int number = 5;
		final String email = "kevin@test.test";
		SubClassWithValueAccessorWithOverriddenMethod jsonObject = new SubClassWithValueAccessorWithOverriddenMethod(name, number, email);
		System.out.println("\nReflectionJsonStathamTest.testJsonObjectWithImplementationInheritanceWithValueAccessorWithOverriddenMethod()");
		final String expected =
			"{\"name\":\"My name is " + name + "\",\"number\":\"The number is " + number + "\",\"email\":\"My email address is " + email
					+ "\"}";
		System.out.println("expected:\n" + expected);
		System.out.println("actual: ");
		final String result = jsonStatham.convertIntoJson(jsonObject);
		System.out.println(result);
		assertThat(result, equalTo(expected));
	}

	@Test
	public void testProxiedJsonObjectPojo() throws IllegalArgumentException, NoSuchMethodException, InstantiationException,
			IllegalAccessException, InvocationTargetException
	{
		final long id = 999L;
		final String name = "ProxiedPojo";
		JsonObjectPojo jsonObjectPojo =
			JsonObjectPojoProxyFactory.newJsonObjectPojo(new JsonObjectPojoImpl(null, null, null), id, name, addressList);

		System.out.println("\nReflectionJsonStathamTest.testProxiedJsonObjectPojo()");
		final String expected = "{\"id\":" + id + ",\"name\":\"" + name + "\",\"addresses\":" + getAddressArrayString() + "}";
		System.out.println("expected:\n" + expected);
		System.out.println("actual: ");
		final String result = jsonStatham.convertIntoJson(jsonObjectPojo);
		System.out.println(result);
		assertThat(result, equalTo(expected));
	}

	@Test
	public void testProxiedJsonObjectPojoHavingProxiedJsonObjectPojo() throws IllegalArgumentException, NoSuchMethodException,
			InstantiationException, IllegalAccessException, InvocationTargetException
	{
		final long primaryKey = 999L;
		final String name = "ProxiedPojo";
		final long primaryKey2 = 555L;
		final String name2 = "ProxiedParent";
		final long primaryKey3 = 333L;
		final String name3 = "Not proxied";
		NestedJsonObjectWithValueAccessor nestedJsonObjectWithValueAccessor =
			JsonObjectPojoProxyFactory.newNestedJsonObjectWithValueAccessor(new NestedJsonObjectWithValueAccessor(null, null, null),
					primaryKey, name, JsonObjectPojoProxyFactory.newNestedJsonObjectWithValueAccessor(
							new NestedJsonObjectWithValueAccessor(null, null, null), primaryKey2, name2,
							new NestedJsonObjectWithValueAccessor(primaryKey3, name3, null)));

		System.out.println("\nReflectionJsonStathamTest.testProxiedJsonObjectPojoHavingProxiedJsonObjectPojo()");
		final String expected =
			"{\"id\":" + primaryKey + ",\"name\":\"" + name + "\",\"parent\":{\"id\":" + primaryKey2 + ",\"name\":\"" + name2
					+ "\",\"parent\":{\"id\":" + primaryKey3 + ",\"name\":\"" + name3 + "\",\"parent\":null}}}";
		System.out.println("expected:\n" + expected);
		System.out.println("actual: ");
		final String result = jsonStatham.convertIntoJson(nestedJsonObjectWithValueAccessor);
		System.out.println(result);
		assertThat(result, equalTo(expected));
	}

	@Test
	public void testJsonObjectContainingEnums()
	{
		System.out.println("\nReflectionJsonStathamTest.testJsonObjectContainingEnums()");
		String expected =
			"{\"name\":\"" + "Kevin" + "\",\"number\":" + 1 + ",\"passed\":" + true + ",\"role\":\"" + "SYSTEM_ADMIN" + "\",\"access\":[]}";
		System.out.println("expected:\n" + expected);
		System.out.println("actual: ");
		String result = jsonStatham.convertIntoJson(new JsonObjectContainingEnums("Kevin", 1, true, Role.SYSTEM_ADMIN));
		System.out.println(result);
		assertThat(result, equalTo(expected));

		expected =
			"{\"name\":\"" + "Kevin" + "\",\"number\":" + 1 + ",\"passed\":" + true + ",\"role\":\"" + "MEMBER"
					+ "\",\"access\":[\"Access to blog\",\"Access to email\"]}";
		System.out.println("expected:\n" + expected);
		System.out.println("actual: ");
		result = jsonStatham.convertIntoJson(new JsonObjectContainingEnums("Kevin", 1, true, Role.MEMBER, Access.BLOG, Access.EMAIL));
		System.out.println(result);
		assertThat(result, equalTo(expected));

		expected =
			"{\"name\":\"" + "Kevin" + "\",\"number\":" + 1 + ",\"passed\":" + true + ",\"role\":\"" + "MEMBER"
					+ "\",\"access\":[\"Access to blog\",\"Access to wiki\",\"Access to email\",\"Access to twitter\"]}";
		System.out.println("expected:\n" + expected);
		System.out.println("actual: ");
		result =
			jsonStatham.convertIntoJson(new JsonObjectContainingEnums("Kevin", 1, true, Role.MEMBER, Access.BLOG, Access.WIKI,
					Access.EMAIL, Access.TWITTER));
		System.out.println(result);
		assertThat(result, equalTo(expected));
	}
}

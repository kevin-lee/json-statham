/**
 * 
 */
package com.lckymn.kevin.jsonstatham.core.reflect.json2java;

import static com.lckymn.kevin.common.util.MessageFormatter.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.lckymn.kevin.common.reflect.TypeHolder;
import com.lckymn.kevin.common.util.Objects;
import com.lckymn.kevin.jsonstatham.annotation.JsonField;
import com.lckymn.kevin.jsonstatham.annotation.JsonObject;
import com.lckymn.kevin.jsonstatham.core.convertible.JsonArrayConvertible;
import com.lckymn.kevin.jsonstatham.core.convertible.JsonArrayConvertibleCreator;
import com.lckymn.kevin.jsonstatham.core.convertible.JsonObjectConvertible;
import com.lckymn.kevin.jsonstatham.core.convertible.JsonObjectConvertibleCreator;
import com.lckymn.kevin.jsonstatham.core.convertible.OrgJsonJsonArrayConvertible;
import com.lckymn.kevin.jsonstatham.core.convertible.OrgJsonJsonObjectConvertible;
import com.lckymn.kevin.jsonstatham.exception.JsonStathamException;
import com.lckymn.kevin.jsonstatham.json.Address;
import com.lckymn.kevin.jsonstatham.json.ComplexJsonObjectWithValueAccessor;
import com.lckymn.kevin.jsonstatham.json.ComplexJsonObjectWithValueAccessorWithoutItsName;
import com.lckymn.kevin.jsonstatham.json.JsonObjectContainingCollection;
import com.lckymn.kevin.jsonstatham.json.JsonObjectContainingEnums;
import com.lckymn.kevin.jsonstatham.json.JsonObjectContainingEnums.Access;
import com.lckymn.kevin.jsonstatham.json.JsonObjectContainingEnums.Role;
import com.lckymn.kevin.jsonstatham.json.JsonObjectContainingIterable;
import com.lckymn.kevin.jsonstatham.json.JsonObjectContainingIterator;
import com.lckymn.kevin.jsonstatham.json.JsonObjectContainingList;
import com.lckymn.kevin.jsonstatham.json.JsonObjectContainingMapEntrySet;
import com.lckymn.kevin.jsonstatham.json.JsonObjectContainingSet;
import com.lckymn.kevin.jsonstatham.json.JsonObjectPojo;
import com.lckymn.kevin.jsonstatham.json.JsonObjectPojoImpl;
import com.lckymn.kevin.jsonstatham.json.JsonObjectPojoProxyFactory;
import com.lckymn.kevin.jsonstatham.json.JsonObjectWithDuplicateKeys;
import com.lckymn.kevin.jsonstatham.json.JsonObjectWithoutFieldName;
import com.lckymn.kevin.jsonstatham.json.NestedJsonObject;
import com.lckymn.kevin.jsonstatham.json.NestedJsonObjectWithValueAccessor;
import com.lckymn.kevin.jsonstatham.json.SecondSubClassWithOwnFields;
import com.lckymn.kevin.jsonstatham.json.SecondSubClassWithoutOwnFields;
import com.lckymn.kevin.jsonstatham.json.SomeImplementingClass;
import com.lckymn.kevin.jsonstatham.json.SomeInterface;
import com.lckymn.kevin.jsonstatham.json.SubClass;
import com.lckymn.kevin.jsonstatham.json.SubClassWithNoJsonObjectSuperClass;
import com.lckymn.kevin.jsonstatham.json.SubClassWithValueAccessor;
import com.lckymn.kevin.jsonstatham.json.SubClassWithValueAccessorWithAbstractMethod;
import com.lckymn.kevin.jsonstatham.json.SubClassWithValueAccessorWithOverriddenMethod;
import com.lckymn.kevin.jsonstatham.json.SubClassWithValueAccessorWithoutItsName;
import com.lckymn.kevin.jsonstatham.json.json2java.JsonObjectHavingNestedGenericTypes;
import com.lckymn.kevin.jsonstatham.json.json2java.JsonPojoHavingMap;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-09-08)
 */
public class ReflectionJsonToJavaConverterTest
{
	private static final List<String> streetList = Arrays.asList("ABC Street", "90/120 Swanston St");
	private static final List<String> suburbList = Arrays.asList("", "Test Suburb");
	private static final List<String> cityList = Arrays.asList("Sydney", "Melbourne");
	private static final List<String> stateList = Arrays.asList("NSW", "VIC");
	private static final List<String> postcodeList = Arrays.asList("2000", "3000");
	private static final String[] SOME_STRING_VALUE_ARRAY = { "111", "222", "aaa", "bbb", "ccc" };

	private static final Answer<JsonObjectConvertible> ANSWER_FOR_NEW_JSON_OBJECT_CONVERTIBLE =
		new Answer<JsonObjectConvertible>()
		{
			@Override
			public JsonObjectConvertible answer(@SuppressWarnings("unused") InvocationOnMock invocation)
					throws Throwable
			{
				return new OrgJsonJsonObjectConvertible(new JSONObject(new LinkedHashMap<String, Object>()));
			}
		};

	private static final Answer<JsonObjectConvertible> ANSWER_FOR_NEW_JSON_OBJECT_CONVERTIBLE_WITH_JSON_STRING =
		new Answer<JsonObjectConvertible>()
		{
			@Override
			public JsonObjectConvertible answer(InvocationOnMock invocation) throws Throwable
			{
				try
				{
					return new OrgJsonJsonObjectConvertible(new JSONObject((String) invocation.getArguments()[0]));
				}
				catch (Exception e)
				{
					throw new JsonStathamException(e);
				}
			}

		};

	private static final Answer<JsonObjectConvertible> ANSWER_FOR_NULL_JSON_OBJECT_CONVERTIBLE =
		new Answer<JsonObjectConvertible>()
		{

			@Override
			public JsonObjectConvertible answer(@SuppressWarnings("unused") InvocationOnMock invocation)
					throws Throwable
			{
				return new JsonObjectConvertible()
				{
					@Override
					public String[] getNames()
					{
						throw new JsonStathamException(
								"The getNames method in NullJsonObjectConvertible cannot be used.");
					}

					@Override
					public Object get(@SuppressWarnings("unused") String name)
					{
						throw new JsonStathamException("The get method in NullJsonObjectConvertible cannot be used.");
					}

					@Override
					public Object getActualObject()
					{
						return JSONObject.NULL;
					}

					@Override
					public JsonObjectConvertible put(@SuppressWarnings("unused") String name,
							@SuppressWarnings("unused") Object value) throws JsonStathamException
					{
						throw new JsonStathamException("The put method in NullJsonObjectConvertible cannot used.");
					}

					@Override
					public String toString()
					{
						return JSONObject.NULL.toString();
					}
				};
			}

		};

	private static final Answer<JsonArrayConvertible> ANSWER_FOR_JSON_ARRAY_CONVERTIBLE =
		new Answer<JsonArrayConvertible>()
		{

			@Override
			public JsonArrayConvertible answer(@SuppressWarnings("unused") InvocationOnMock invocation)
					throws Throwable
			{
				return new OrgJsonJsonArrayConvertible(new JSONArray());
			}
		};

	private static final Answer<JsonArrayConvertible> ANSWER_FOR_JSON_ARRAY_CONVERTIBLE_WITH_JSON_STRING =
		new Answer<JsonArrayConvertible>()
		{
			@Override
			public JsonArrayConvertible answer(InvocationOnMock invocation) throws Throwable
			{
				return new OrgJsonJsonArrayConvertible(new JSONArray((String) invocation.getArguments()[0]));
			}
		};

	private List<Address> addressList;

	private Map<String, Address> addressMap;

	private ReflectionJsonToJavaConverter reflectionJsonToJavaConverter;

	private Address address;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		System.out.println("### ReflectionJsonToJavaConverterTest starts ###");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{
		System.out.println("### ReflectionJsonToJavaConverterTest finishes ###");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		final JsonObjectConvertibleCreator jsonObjectConvertibleCreator = mock(JsonObjectConvertibleCreator.class);
		when(jsonObjectConvertibleCreator.newJsonObjectConvertible()).thenAnswer(ANSWER_FOR_NEW_JSON_OBJECT_CONVERTIBLE);
		when(jsonObjectConvertibleCreator.newJsonObjectConvertible(anyString())).thenAnswer(
				ANSWER_FOR_NEW_JSON_OBJECT_CONVERTIBLE_WITH_JSON_STRING);
		when(jsonObjectConvertibleCreator.nullJsonObjectConvertible()).thenAnswer(
				ANSWER_FOR_NULL_JSON_OBJECT_CONVERTIBLE);

		final JsonArrayConvertibleCreator jsonArrayConvertibleCreator = mock(JsonArrayConvertibleCreator.class);
		when(jsonArrayConvertibleCreator.newJsonArrayConvertible()).thenAnswer(ANSWER_FOR_JSON_ARRAY_CONVERTIBLE);
		when(jsonArrayConvertibleCreator.newJsonArrayConvertible(anyString())).thenAnswer(
				ANSWER_FOR_JSON_ARRAY_CONVERTIBLE_WITH_JSON_STRING);

		reflectionJsonToJavaConverter =
			new ReflectionJsonToJavaConverter(jsonObjectConvertibleCreator, jsonArrayConvertibleCreator);
		address =
			new Address(streetList.get(0), suburbList.get(0), cityList.get(0), stateList.get(0), postcodeList.get(0));

		addressList = new ArrayList<Address>();
		for (int i = 0, size = streetList.size(); i < size; i++)
		{
			addressList.add(new Address(streetList.get(i), suburbList.get(i), cityList.get(i), stateList.get(i),
					postcodeList.get(i)));
		}

		addressMap = new LinkedHashMap<String, Address>();
		for (int i = 0, size = streetList.size(); i < size; i++)
		{
			addressMap.put(
					"address" + i,
					new Address(streetList.get(i), suburbList.get(i), cityList.get(i), stateList.get(i),
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
	public final void testConvertFromJsonWithIllegalJson() throws JsonStathamException, IllegalArgumentException,
			InstantiationException, IllegalAccessException, InvocationTargetException
	{
		System.out.println("\nReflectionJsonToJavaConverterTest.testConvertFromJsonWithIllegalJson()");
		reflectionJsonToJavaConverter.convertFromJson(Object.class, "{\"some\",\"value\",\"This is not JSON\"}");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testUnknownType() throws JsonStathamException, IllegalArgumentException, InstantiationException,
			IllegalAccessException, InvocationTargetException
	{
		System.out.println("\nReflectionJsonToJavaConverterTest.testUnknownType().UnknownType.testUnknownType()");
		class UnknownType
		{
		}
		reflectionJsonToJavaConverter.convertFromJson(UnknownType.class, "{}");
	}

	@Test
	public void testNull() throws JsonStathamException, IllegalArgumentException, InstantiationException,
			IllegalAccessException, InvocationTargetException
	{
		System.out.println("\nReflectionJsonToJavaConverterTest.testNull()");
		final String json = "null";
		System.out.println("json:\n" + json);
		System.out.println("java: ");

		/* test convertFromJson */
		final Object result = reflectionJsonToJavaConverter.convertFromJson((Class<Object>) null, json);
		System.out.println(result);
		assertThat(result, is(nullValue()));
	}

	@Test(expected = JsonStathamException.class)
	public void testLocalJsonClass() throws Exception
	{
		System.out.println("\nReflectionJsonToJavaConverterTest.testLocalJsonClass().TestPojo.testLocalJsonClass()");
		@SuppressWarnings("hiding")
		@JsonObject
		class TestPojo
		{
			@JsonField
			private Object object = null;

			@Override
			public int hashCode()
			{
				return Objects.hash(object);
			}

			@Override
			public boolean equals(Object testPojo)
			{
				if (this == testPojo)
				{
					return true;
				}
				if (!(testPojo instanceof TestPojo))
				{
					return false;
				}
				final TestPojo that = (TestPojo) testPojo;
				return Objects.equals(this.object, that.object);
			}
		}
		final String json = "{\"object\":null}";
		System.out.println("json:\n" + json);
		System.out.println("java: ");
		try
		{
			reflectionJsonToJavaConverter.convertFromJson(TestPojo.class, json);
		}
		catch (Exception e)
		{
			System.out.println("exceptionMessage: " + e.getMessage());
			throw e;
		}
	}

	@JsonObject
	private static class TestPojo
	{
		@JsonField
		private Object object = null;

		@Override
		public int hashCode()
		{
			return Objects.hash(object);
		}

		@Override
		public boolean equals(Object testPojo)
		{
			if (this == testPojo)
			{
				return true;
			}
			if (!(testPojo instanceof TestPojo))
			{
				return false;
			}
			final TestPojo that = (TestPojo) testPojo;
			return Objects.equals(this.object, that.object);
		}
	}

	@Test
	public void testJsonHavingNullValue() throws JsonStathamException, IllegalArgumentException,
			InstantiationException, IllegalAccessException, InvocationTargetException
	{
		System.out.println("\nReflectionJsonToJavaConverterTest.testJsonHavingNullValue()");
		final String json = "{\"object\":null}";
		System.out.println("json:\n" + json);
		System.out.println("java: ");
		final TestPojo result = reflectionJsonToJavaConverter.convertFromJson(TestPojo.class, json);
		System.out.println(result);
		assertTrue(new TestPojo().equals(result));
		assertEquals(new TestPojo().hashCode(), result.hashCode());

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

	private String toString(Object object)
	{
		if (object.getClass()
				.isArray())
		{
			final int length = Array.getLength(object);
			final StringBuilder stringBuilder = new StringBuilder("[");
			for (int i = 0; i < length; i++)
			{
				stringBuilder.append(Array.get(object, i))
						.append(",");
			}
			if (0 < length)
			{
				stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			}
			return stringBuilder.append("]")
					.toString();
		}
		return object.toString();
	}

	@Test
	public void testArray() throws JsonStathamException, IllegalArgumentException, InstantiationException,
			IllegalAccessException, InvocationTargetException
	{
		System.out.println("\nReflectionJsonToJavaConverterTest.testArray()");

		final String intArrayJson = "[1,2,3,4,5,8,23,56]";
		final int[] intArray = new int[] { 1, 2, 3, 4, 5, 8, 23, 56 };
		System.out.println("\njson:\n" + intArrayJson);
		System.out.println("java: ");
		final int[] resultIntArray = reflectionJsonToJavaConverter.convertFromJson(int[].class, intArrayJson);
		System.out.println(toString(resultIntArray));
		assertThat(resultIntArray, is(equalTo(intArray)));

		final String doubleArrayJson = "[1.2,2.6,3.3,4.8,5.234,8.567,23.48754,56.0547]";
		final double[] doubleArray = new double[] { 1.2, 2.6, 3.3, 4.8, 5.234, 8.567, 23.48754, 56.0547 };
		System.out.println("\njson:\n" + doubleArrayJson);
		System.out.println("java: ");
		final double[] resultDoubleArray =
			reflectionJsonToJavaConverter.convertFromJson(double[].class, doubleArrayJson);
		System.out.println(toString(resultDoubleArray));
		assertThat(resultDoubleArray, is(equalTo(doubleArray)));

		final String booleanArrayJson = "[true,false,false,true,false,true,false,true,true]";
		final boolean[] booleanArray = new boolean[] { true, false, false, true, false, true, false, true, true };
		System.out.println("\njson:\n" + booleanArrayJson);
		System.out.println("java: ");
		final boolean[] resultBooleanArray =
			reflectionJsonToJavaConverter.convertFromJson(boolean[].class, booleanArrayJson);
		System.out.println(toString(resultBooleanArray));
		assertThat(resultBooleanArray, is(equalTo(booleanArray)));
	}

	@Test
	public void testArrayHavingPojo() throws JsonStathamException, IllegalArgumentException, InstantiationException,
			IllegalAccessException, InvocationTargetException
	{
		System.out.println("\nReflectionJsonToJavaConverterTest.testArrayHavingPojo()");
		final String json = getAddressArrayString();
		System.out.println("json:\n" + json);
		System.out.println("java: ");
		final Address[] addresses = reflectionJsonToJavaConverter.convertFromJson(Address[].class, json);
		System.out.println(toString(addresses));
		assertThat(addresses, is(equalTo(addressList.toArray(new Address[addressList.size()]))));
	}

	@Test
	public void testList() throws JsonStathamException, IllegalArgumentException, InstantiationException,
			IllegalAccessException, InvocationTargetException
	{
		System.out.println("\nReflectionJsonToJavaConverterTest.testList()");
		final String json = getAddressArrayString();
		System.out.println("json:\n" + json);
		System.out.println("java: ");

		final List<Address> result = reflectionJsonToJavaConverter.convertFromJson(new TypeHolder<List<Address>>()
		{
		}, json);
		System.out.println(result);
		assertThat(result, is(equalTo(addressList)));
	}

	private String getAddressMapString()
	{
		final StringBuilder stringBuilder = new StringBuilder("{");
		for (final Entry<String, Address> entry : addressMap.entrySet())
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

	@Test
	public void testMap() throws JsonStathamException, IllegalArgumentException, InstantiationException,
			IllegalAccessException, InvocationTargetException
	{
		System.out.println("\nReflectionJsonToJavaConverterTest.testMap()");
		final String json = getAddressMapString();
		System.out.println("json:\n" + json);
		System.out.println("java: ");

		final Map<String, Address> result =
			reflectionJsonToJavaConverter.convertFromJson(new TypeHolder<Map<String, Address>>()
			{
			}, json);
		System.out.println(result);
		assertThat(result, is(equalTo(addressMap)));
	}

	@Test
	public void testNestedMap() throws JsonStathamException, IllegalArgumentException, InstantiationException,
			IllegalAccessException, InvocationTargetException
	{
		System.out.println("\nReflectionJsonToJavaConverterTest.testNestedMap()");
		final String json = "{\"test1\":" + getAddressMapString() + ",\"test2\":" + getAddressMapString() + "}";
		System.out.println("json: \n" + json);
		final Map<String, Map<String, Address>> nestedMap = new HashMap<String, Map<String, Address>>();
		nestedMap.put("test1", addressMap);
		nestedMap.put("test2", addressMap);
		System.out.println("java: ");

		final Map<String, Map<String, Address>> result =
			reflectionJsonToJavaConverter.convertFromJson(new TypeHolder<Map<String, Map<String, Address>>>()
			{
			}, json);
		System.out.println(result);
		assertThat(result, is(equalTo(nestedMap)));
	}

	@Test
	public void testAddress() throws JsonStathamException, IllegalArgumentException, InstantiationException,
			IllegalAccessException, InvocationTargetException
	{
		System.out.println("\nReflectionJsonToJavaConverterTest.testAddress()");

		final String json =
			"{\"street\":\"" + streetList.get(0) + "\",\"suburb\":\"" + suburbList.get(0) + "\",\"city\":\""
					+ cityList.get(0) + "\",\"state\":\"" + stateList.get(0) + "\",\"postcode\":\""
					+ postcodeList.get(0) + "\"}";
		System.out.println("json:\n" + json);
		System.out.println("java: ");
		final Address result = reflectionJsonToJavaConverter.convertFromJson(Address.class, json);
		System.out.println(result);
		assertThat(result, is(equalTo(address)));
	}

	@Test
	public void testNestedJsonObject() throws JsonStathamException, IllegalArgumentException, InstantiationException,
			IllegalAccessException, InvocationTargetException
	{
		System.out.println("\nReflectionJsonToJavaConverterTest.testNestedJsonObject()");
		final long id = 1;
		final String name = "jsonObject";
		final NestedJsonObject jsonObject = new NestedJsonObject();
		jsonObject.setPrimaryKey(Long.valueOf(id));
		jsonObject.setName(name);
		jsonObject.setAddress(address);

		final String json =
			"{\"id\":" + id + ",\"name\":\"" + name + "\",\"address\":{\"street\":\"" + streetList.get(0)
					+ "\",\"suburb\":\"" + suburbList.get(0) + "\",\"city\":\"" + cityList.get(0) + "\",\"state\":\""
					+ stateList.get(0) + "\",\"postcode\":\"" + postcodeList.get(0) + "\"}}";
		System.out.println("json:\n" + json);
		System.out.println("java: ");
		final NestedJsonObject result = reflectionJsonToJavaConverter.convertFromJson(NestedJsonObject.class, json);
		System.out.println(result);
		assertThat(result, is(equalTo(jsonObject)));
	}

	@Test(expected = JsonStathamException.class)
	public void testJsonObjectWithDuplicateKeys() throws IOException, IllegalArgumentException, InstantiationException,
			IllegalAccessException, InvocationTargetException
	{
		System.out.println("\nReflectionJsonToJavaConverterTest.testJsonObjectWithDuplicateKeys()");
		final JsonObjectWithDuplicateKeys jsonObjectWithDuplicateKeys = new JsonObjectWithDuplicateKeys();
		jsonObjectWithDuplicateKeys.setUsername("kevinlee");
		jsonObjectWithDuplicateKeys.setName("Kevin");
		jsonObjectWithDuplicateKeys.setFullName("Kevin Lee");
		jsonObjectWithDuplicateKeys.setEmail("kevin@test.test");

		System.out.println("result: ");
		JsonObjectWithDuplicateKeys result = null;
		try
		{
			result = reflectionJsonToJavaConverter.convertFromJson(JsonObjectWithDuplicateKeys.class, "{}");
		}
		catch (JsonStathamException e)
		{
			System.out.println(e.getMessage());
			throw e;
		}
		System.out.println(result);
	}

	@Test
	public void testJsonObjectWithoutFieldName() throws JsonStathamException, IllegalArgumentException,
			InstantiationException, IllegalAccessException, InvocationTargetException
	{
		System.out.println("\nReflectionJsonToJavaConverterTest.testJsonObjectWithoutFieldName()");
		final int id = 5;
		final String name = "Kevin Lee";
		final String address = "123 ABC Street";
		final JsonObjectWithoutFieldName jsonObjectWithoutFieldName = new JsonObjectWithoutFieldName(id, name, address);
		final String json = "{\"id\":" + id + ",\"name\":\"" + name + "\",\"address\":\"" + address + "\"}";
		System.out.println("json:\n" + json);
		System.out.println("java: ");
		final JsonObjectWithoutFieldName result =
			reflectionJsonToJavaConverter.convertFromJson(JsonObjectWithoutFieldName.class, json);
		System.out.println(result);
		assertThat(result, is(equalTo(jsonObjectWithoutFieldName)));
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testComplexJsonObjectWithMethodUse() throws JsonStathamException, IllegalArgumentException,
			InstantiationException, IllegalAccessException, InvocationTargetException
	{
		System.out.println("\nReflectionJsonToJavaConverterTest.testComplexJsonObjectWithMethodUse()");
		final ComplexJsonObjectWithValueAccessor jsonObject = new ComplexJsonObjectWithValueAccessor();
		jsonObject.setPrimaryKey(Long.valueOf(1));
		jsonObject.setName("Kevin");
		jsonObject.setAddress(address);
		final Date date = new Date();
		jsonObject.setDate(new Date(date.getYear(), date.getMonth(), date.getDay(), date.getHours(), date.getMinutes(),
				date.getSeconds()));
		final Date date2 = new Date(date.getYear(), date.getMonth(), date.getDay());
		jsonObject.setDateWithValueAccessor(date2);

		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(2010, 9, 27, 0, 0, 0));
		calendar.getTimeInMillis();
		jsonObject.setCalendar(calendar);
		final Calendar calendar2 = (Calendar) calendar.clone();
		calendar2.setTime(date2);
		calendar2.getTimeInMillis();
		jsonObject.setCalendarWithValueAccessor(calendar2);

		final String json =
			"{\"id\":1,\"name\":\"Kevin\"," + "\"address\":{\"street\":\"" + address.getStreet() + "\",\"suburb\":\""
					+ address.getSuburb() + "\",\"city\":\"" + address.getCity() + "\",\"state\":\""
					+ address.getState() + "\",\"postcode\":\"" + address.getPostcode() + "\"}," + "\"date\":"
					+ jsonObject.getDate()
							.getTime() + "," + "\"dateWithValueAccessor\":" + jsonObject.getDateWithValueAccessor()
							.getTime() + ",\"calendar\":" + jsonObject.getCalendar()
							.getTimeInMillis() + ",\"calendarWithValueAccessor\":"
					+ jsonObject.getCalendarWithValueAccessor()
							.getTimeInMillis() + "}";
		System.out.println("json:\n" + json);
		System.out.println("java: ");
		final ComplexJsonObjectWithValueAccessor result =
			reflectionJsonToJavaConverter.convertFromJson(ComplexJsonObjectWithValueAccessor.class, json);
		System.out.println(result);
		assertThat(result, is(equalTo(jsonObject)));
	}

	@Test
	public void testComplexJsonObjectWithValueAccessorWithoutItsName() throws JsonStathamException,
			IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException
	{
		System.out.println("\nReflectionJsonToJavaConverterTest.testComplexJsonObjectWithValueAccessorWithoutItsName()");
		final ComplexJsonObjectWithValueAccessorWithoutItsName jsonObject =
			new ComplexJsonObjectWithValueAccessorWithoutItsName();
		jsonObject.setPrimaryKey(Long.valueOf(1));
		jsonObject.setName("Kevin");
		jsonObject.setRegistered(true);
		jsonObject.setEnabled(false);
		jsonObject.setAddress(address);
		@SuppressWarnings("deprecation")
		final Date date = new Date(2010, 9, 27);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.getTimeInMillis();
		jsonObject.setDate(date);
		jsonObject.setDateWithValueAccessor(date);
		jsonObject.setCalendar(calendar);
		jsonObject.setCalendarWithValueAccessor(calendar);

		final String json =
			"{\"id\":1,\"name\":\"Kevin\",\"registered\":true,\"enabled\":false,\"address\":{\"street\":\""
					+ address.getStreet() + "\",\"suburb\":\"" + address.getSuburb() + "\",\"city\":\""
					+ address.getCity() + "\",\"state\":\"" + address.getState() + "\",\"postcode\":\""
					+ address.getPostcode() + "\"}," + "\"date\":" + jsonObject.getDate()
							.getTime() + "," + "\"dateWithValueAccessor\":" + jsonObject.dateWithValueAccessor()
							.getTime() + ",\"calendar\":" + jsonObject.getCalendar()
							.getTimeInMillis() + ",\"calendarWithValueAccessor\":"
					+ jsonObject.calendarWithValueAccessor()
							.getTimeInMillis() + "}";
		System.out.println("json:\n" + json);
		System.out.println("java: ");
		final ComplexJsonObjectWithValueAccessorWithoutItsName result =
			reflectionJsonToJavaConverter.convertFromJson(ComplexJsonObjectWithValueAccessorWithoutItsName.class, json);
		System.out.println(result);
		assertThat(result, is(equalTo(jsonObject)));
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
	public void testJsonObjectContainingCollection() throws JsonStathamException, IllegalArgumentException,
			InstantiationException, IllegalAccessException, InvocationTargetException
	{
		System.out.println("\nReflectionJsonToJavaConverterTest.testJsonObjectContainingCollection()");
		final String nameValue = "testJsonWithCollection";
		Collection<String> collection =
			initialiseCollectionWithStringValues(new ArrayList<String>(), SOME_STRING_VALUE_ARRAY);

		final JsonObjectContainingCollection jsonObjectContainingCollection =
			new JsonObjectContainingCollection(nameValue, collection);
		final String json = getExpectedJsonArray("name", nameValue, "valueCollection");
		System.out.println("json:\n" + json);
		System.out.println("java: ");
		final JsonObjectContainingCollection result =
			reflectionJsonToJavaConverter.convertFromJson(JsonObjectContainingCollection.class, json);
		System.out.println(result);
		assertThat(result, is(equalTo(jsonObjectContainingCollection)));
	}

	@Test
	public void testJsonObjectContainingList() throws JsonStathamException, IllegalArgumentException,
			InstantiationException, IllegalAccessException, InvocationTargetException
	{
		System.out.println("\nReflectionJsonToJavaConverterTest.testJsonObjectContainingList()");
		final String nameValue = "testJsonWithList";
		final List<String> list =
			initialiseCollectionWithStringValues(new ArrayList<String>(), SOME_STRING_VALUE_ARRAY);

		final JsonObjectContainingList jsonObjectContainingList = new JsonObjectContainingList(nameValue, list);
		final String json = getExpectedJsonArray("name", nameValue, "valueList");
		System.out.println("json:\n" + json);
		System.out.println("java: ");
		final JsonObjectContainingList result =
			reflectionJsonToJavaConverter.convertFromJson(JsonObjectContainingList.class, json);
		System.out.println(result);
		assertThat(result, is(equalTo(jsonObjectContainingList)));
	}

	@Test
	public void testJsonObjectContainingSet() throws JsonStathamException, IllegalArgumentException,
			InstantiationException, IllegalAccessException, InvocationTargetException
	{
		System.out.println("\nReflectionJsonToJavaConverterTest.testJsonObjectContainingSet()");
		final String nameValue = "testJsonWithSet";
		final Set<String> set = initialiseCollectionWithStringValues(new HashSet<String>(), SOME_STRING_VALUE_ARRAY);

		final JsonObjectContainingSet jsonObjectContainingSet = new JsonObjectContainingSet(nameValue, set);
		final String json = getExpectedJsonArray("name", nameValue, "valueSet");
		System.out.println("json:\n" + json);
		System.out.println("java: ");
		final JsonObjectContainingSet result =
			reflectionJsonToJavaConverter.convertFromJson(JsonObjectContainingSet.class, json);
		System.out.println(result);
		assertThat(result, is(equalTo(jsonObjectContainingSet)));
	}

	@Test
	public void testJsonObjectContainingMapEntrySetSet() throws JsonStathamException, IllegalArgumentException,
			InstantiationException, IllegalAccessException, InvocationTargetException
	{
		System.out.println("\nReflectionJsonToJavaConverterTest.testJsonObjectContainingMapEntrySetSet()");
		final String nameValue = "testJsonObjectContainingMapEntrySetSet";

		final JsonObjectContainingMapEntrySet jsonObjectContainingSet =
			new JsonObjectContainingMapEntrySet(nameValue, addressMap.entrySet());

		final StringBuilder stringBuilder =
			new StringBuilder("{\"name\":\"testJsonObjectContainingMapEntrySetSet\",\"valueMapEntrySet\":[");
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
		final String json = stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), "]}")
				.toString();

		System.out.println("json:\n" + json);
		System.out.println("java: ");
		final JsonObjectContainingMapEntrySet result =
			reflectionJsonToJavaConverter.convertFromJson(JsonObjectContainingMapEntrySet.class, json);
		System.out.println(result);
		assertThat(result, is(equalTo(jsonObjectContainingSet)));

		final String json2 = "{\"name\":\"testJsonObjectContainingMapEntrySetSet\",\"valueMapEntrySet\":[]}";
		final JsonObjectContainingMapEntrySet jsonObjectContainingSet2 =
			new JsonObjectContainingMapEntrySet(nameValue, new HashSet<Entry<String, Address>>());
		System.out.println("json:\n" + json2);
		System.out.println("java: ");
		final JsonObjectContainingMapEntrySet result2 =
			reflectionJsonToJavaConverter.convertFromJson(JsonObjectContainingMapEntrySet.class, json2);
		System.out.println(result2);
		assertThat(result2, is(equalTo(jsonObjectContainingSet2)));

	}

	@SuppressWarnings("boxing")
	@Test
	public final void testJsonPojoHavingMap() throws ArrayIndexOutOfBoundsException, IllegalArgumentException,
			InstantiationException, IllegalAccessException, InvocationTargetException
	{
		System.out.println("\nReflectionJsonToJavaConverterTest.testJsonPojoHavingMap()");
		Map<String, Long> map = new HashMap<String, Long>();
		map.put("Kevin", 1L);
		map.put("Lee", 2L);
		map.put("Kevin Lee", 3L);
		final JsonPojoHavingMap jsonPojoHavingMap = new JsonPojoHavingMap("Kevin", map);
		final String json = "{\"stringToLongMap\":{\"Kevin Lee\":3,\"Lee\":2,\"Kevin\":1},\"name\":\"Kevin\"}";
		final JsonPojoHavingMap result = reflectionJsonToJavaConverter.convertFromJson(JsonPojoHavingMap.class, json);
		assertThat(result, is(equalTo(jsonPojoHavingMap)));
	}

	@Test
	public void testJsonObjectContainingIterator() throws JsonStathamException, IllegalArgumentException,
			InstantiationException, IllegalAccessException, InvocationTargetException
	{
		System.out.println("\nReflectionJsonToJavaConverterTest.testJsonObjectContainingIterator()");
		final String nameValue = "testJsonObjectContainingIterator";
		final Collection<String> collection =
			initialiseCollectionWithStringValues(new ArrayList<String>(), SOME_STRING_VALUE_ARRAY);

		final JsonObjectContainingIterator jsonObjectContainingCollection =
			new JsonObjectContainingIterator(nameValue, collection.iterator());
		final String json = getExpectedJsonArray("name", nameValue, "valueIterator");
		System.out.println("json:\n" + json);
		System.out.println("java: ");
		final JsonObjectContainingIterator result =
			reflectionJsonToJavaConverter.convertFromJson(JsonObjectContainingIterator.class, json);
		System.out.println(result);
		assertThat(result, is(equalTo(jsonObjectContainingCollection)));

	}

	@Test
	public void testJsonObjectContainingIterable() throws JsonStathamException, IllegalArgumentException,
			InstantiationException, IllegalAccessException, InvocationTargetException
	{
		System.out.println("\nReflectionJsonToJavaConverterTest.testJsonObjectContainingIterable()");
		final String nameValue = "testJsonObjectContainingIterable";
		final Iterable<String> iterable = new Iterable<String>()
		{
			@Override
			public Iterator<String> iterator()
			{
				return initialiseCollectionWithStringValues(new ArrayList<String>(), SOME_STRING_VALUE_ARRAY).iterator();
			}
		};

		final JsonObjectContainingIterable jsonObjectContainingCollection =
			new JsonObjectContainingIterable(nameValue, iterable);
		final String json = getExpectedJsonArray("name", nameValue, "valueIterable");
		System.out.println("json:\n" + json);
		System.out.println("java: ");
		final JsonObjectContainingIterable result =
			reflectionJsonToJavaConverter.convertFromJson(JsonObjectContainingIterable.class, json);
		System.out.println(result);
		assertThat(result, is(equalTo(jsonObjectContainingCollection)));
	}

	@Test
	public void testJsonObjectContainingNestedGenericTypes() throws ArrayIndexOutOfBoundsException,
			IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException
	{
		System.out.println("\nReflectionJsonToJavaConverterTest.testJsonObjectContainingNestedGenericTypes()");
		final List<Address> listOfAddress = new ArrayList<Address>();
		final List<List<Address>> listOfListOfAddress = new ArrayList<List<Address>>();
		final List<List<List<Address>>> listOfListOfListOfAddress = new ArrayList<List<List<Address>>>();
		listOfAddress.add(new Address("ABC St", "ZZZ", "Sydney", "NSW", "2000"));
		listOfListOfAddress.add(listOfAddress);
		listOfListOfListOfAddress.add(listOfListOfAddress);
		final List<Address> listOfAddress2 = new ArrayList<Address>();
		final List<List<Address>> listOfListOfAddress2 = new ArrayList<List<Address>>();
		listOfAddress2.add(new Address("123 Street", "AAA", "Melbourne", "VIC", "3000"));
		listOfListOfAddress2.add(listOfAddress2);
		listOfListOfListOfAddress.add(listOfListOfAddress2);
		final List<Address> listOfAddress3 = new ArrayList<Address>();
		final List<List<Address>> listOfListOfAddress3 = new ArrayList<List<Address>>();
		listOfAddress3.add(new Address("Some Street", "LLL", "Brisbane", "QL", "4000"));
		listOfListOfAddress3.add(listOfAddress3);
		listOfListOfListOfAddress.add(listOfListOfAddress3);
		final JsonObjectHavingNestedGenericTypes jsonObjectHavingNestedGenericTypes =
			new JsonObjectHavingNestedGenericTypes(listOfListOfListOfAddress);
		// System.out.println(ReflectionJsonStathams.newReflectionJsonStathamInAction().convertIntoJson(jsonObjectHavingNestedGenericTypes));
		final String json =
			"{\"listOfListOfListOfAddress\":[[[{\"street\":\"ABC St\",\"suburb\":\"ZZZ\",\"city\":\"Sydney\",\"state\":\"NSW\",\"postcode\":\"2000\"}]],[[{\"street\":\"123 Street\",\"suburb\":\"AAA\",\"city\":\"Melbourne\",\"state\":\"VIC\",\"postcode\":\"3000\"}]],[[{\"street\":\"Some Street\",\"suburb\":\"LLL\",\"city\":\"Brisbane\",\"state\":\"QL\",\"postcode\":\"4000\"}]]]}";
		System.out.println("json:\n" + json);
		System.out.println("java: ");
		final JsonObjectHavingNestedGenericTypes result =
			reflectionJsonToJavaConverter.convertFromJson(JsonObjectHavingNestedGenericTypes.class, json);
		System.out.println(result);
		assertThat(result, is(equalTo(jsonObjectHavingNestedGenericTypes)));
	}

	@Test
	public void testJsonObjectWithInterfaceInheritance() throws JsonStathamException, IllegalArgumentException,
			InstantiationException, IllegalAccessException, InvocationTargetException
	{
		System.out.println("\nReflectionJsonToJavaConverterTest.testJsonObjectWithInterfaceInheritance()");
		final String name = "Kevin Lee";
		final int number = 99;
		final String email = "kevinlee@test.test";
		final SomeInterface jsonObject = new SomeImplementingClass(name, number, email);
		final String json = "{\"name\":\"" + name + "\",\"number\":" + number + ",\"email\":\"" + email + "\"}";
		System.out.println("json:\n" + json);
		System.out.println("java: ");
		final SomeInterface result = reflectionJsonToJavaConverter.convertFromJson(SomeImplementingClass.class, json);
		System.out.println(result);
		assertThat(result, is(equalTo(jsonObject)));
	}

	@Test
	public void testJsonObjectWithImplementationInheritance() throws JsonStathamException, IllegalArgumentException,
			InstantiationException, IllegalAccessException, InvocationTargetException
	{
		System.out.println("\nReflectionJsonToJavaConverterTest.testJsonObjectWithImplementationInheritance()");
		final String name = "Kevin";
		final int number = 5;
		final String email = "kevin@test.test";
		final SubClass jsonObject = new SubClass(name, number, email);
		final String json = "{\"name\":\"" + name + "\",\"number\":" + number + ",\"email\":\"" + email + "\"}";
		System.out.println("json:\n" + json);
		System.out.println("java: ");
		final SubClass result = reflectionJsonToJavaConverter.convertFromJson(SubClass.class, json);
		System.out.println(result);
		assertThat(result, is(equalTo(jsonObject)));
	}

	@Test
	public void testJsonObjectWithDoubleImplementationInheritanceAndNoOwnFieldsInSecondSubClass()
			throws JsonStathamException, IllegalArgumentException, InstantiationException, IllegalAccessException,
			InvocationTargetException
	{
		System.out.println("\nReflectionJsonToJavaConverterTest.testJsonObjectWithDoubleImplementationInheritanceAndNoOwnFieldsInSecondSubClass()");
		final String name = "Kevin";
		final int number = 11;
		final String email = "kevin@test.blahblah";
		final SubClass jsonObject = new SecondSubClassWithoutOwnFields(name, number, email);
		final String json = "{\"name\":\"" + name + "\",\"number\":" + number + ",\"email\":\"" + email + "\"}";
		System.out.println("json:\n" + json);
		System.out.println("java: ");
		final SubClass result = reflectionJsonToJavaConverter.convertFromJson(SubClass.class, json);
		System.out.println(result);
		assertThat(result, is(equalTo(jsonObject)));
	}

	@Test
	public void testJsonObjectWithDoubleImplementationInheritanceAndOwnFieldsInSecondSubClass()
			throws JsonStathamException, IllegalArgumentException, InstantiationException, IllegalAccessException,
			InvocationTargetException
	{
		System.out.println("\nReflectionJsonToJavaConverterTest.testJsonObjectWithDoubleImplementationInheritanceAndOwnFieldsInSecondSubClass()");
		final String name = "Mr. Lee";
		final int number = 999;
		final String email = "kevin@another.email";
		final String comment = "Blah blah";
		final SecondSubClassWithOwnFields jsonObject =
			new SecondSubClassWithOwnFields(name, number, email, address, comment);
		final String json =
			"{\"name\":\"" + name + "\",\"number\":" + number + ",\"email\":\"" + email + "\"," + "\"address\":"
					+ "{\"street\":\"" + address.getStreet() + "\",\"suburb\":\"" + address.getSuburb()
					+ "\",\"city\":\"" + address.getCity() + "\",\"state\":\"" + address.getState()
					+ "\",\"postcode\":\"" + address.getPostcode() + "\"},\"comment\":\"" + comment + "\"}";
		System.out.println("json:\n" + json);
		System.out.println("java: ");
		final SecondSubClassWithOwnFields result =
			reflectionJsonToJavaConverter.convertFromJson(SecondSubClassWithOwnFields.class, json);
		System.out.println(result);
		assertThat(result, is(equalTo(jsonObject)));
	}

	@Test
	public void testJsonObjectWithImplementationInheritanceWithNoJsonObjectSuperClass() throws JsonStathamException,
			IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException
	{
		System.out.println("\nReflectionJsonToJavaConverterTest.testJsonObjectWithImplementationInheritanceWithNoJsonObjectSuperClass()");
		final String email = "kevin@test.test";
		final SubClassWithNoJsonObjectSuperClass jsonObject = new SubClassWithNoJsonObjectSuperClass(null, 0, email);
		final String json = "{\"email\":\"" + email + "\"}";
		System.out.println("json:\n" + json);
		System.out.println("java: ");
		final SubClassWithNoJsonObjectSuperClass result =
			reflectionJsonToJavaConverter.convertFromJson(SubClassWithNoJsonObjectSuperClass.class, json);
		System.out.println(result);
		assertThat(result, is(equalTo(jsonObject)));
	}

	@Test
	public void testJsonObjectWithImplementationInheritanceWithValueAccessor() throws JsonStathamException,
			IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException
	{
		System.out.println("\nReflectionJsonToJavaConverterTest.testJsonObjectWithImplementationInheritanceWithValueAccessor()");
		final String name = "Kevin";
		final int number = 5;
		final String email = "kevin@test.test";
		final SubClassWithValueAccessor jsonObject = new SubClassWithValueAccessor(name, number, email);
		final String json = "{\"name\":\"" + name + "\",\"number\":" + number + ",\"email\":\"" + email + "\"}";
		System.out.println("json:\n" + json);
		System.out.println("java: ");
		final SubClassWithValueAccessor result =
			reflectionJsonToJavaConverter.convertFromJson(SubClassWithValueAccessor.class, json);
		System.out.println(result);
		assertThat(result, is(equalTo(jsonObject)));
	}

	@Test
	public void testJsonObjectWithImplementationInheritanceWithValueAccessorWithoutItsName()
			throws JsonStathamException, IllegalArgumentException, InstantiationException, IllegalAccessException,
			InvocationTargetException
	{
		System.out.println("\nReflectionJsonToJavaConverterTest.testJsonObjectWithImplementationInheritanceWithValueAccessorWithoutItsName()");
		final String name = "Kevin";
		final int number = 5;
		final String email = "kevin@test.test";
		final SubClassWithValueAccessorWithoutItsName jsonObject =
			new SubClassWithValueAccessorWithoutItsName(name, number, email);
		final String json = "{\"name\":\"" + name + "\",\"number\":" + number + ",\"email\":\"" + email + "\"}";
		System.out.println("json:\n" + json);
		System.out.println("java: ");
		final SubClassWithValueAccessorWithoutItsName result =
			reflectionJsonToJavaConverter.convertFromJson(SubClassWithValueAccessorWithoutItsName.class, json);
		System.out.println(result);
		assertThat(result, is(equalTo(jsonObject)));
	}

	@Test
	public void testJsonObjectWithImplementationInheritanceWithValueAccessorWithAbstractMethod()
			throws JsonStathamException, IllegalArgumentException, InstantiationException, IllegalAccessException,
			InvocationTargetException
	{
		System.out.println("\nReflectionJsonToJavaConverterTest.testJsonObjectWithImplementationInheritanceWithValueAccessorWithAbstractMethod()");
		final String name = "Kevin";
		final int number = 5;
		final String email = "kevin@test.test";
		final SubClassWithValueAccessorWithAbstractMethod jsonObject =
			new SubClassWithValueAccessorWithAbstractMethod(name, number, email);
		@SuppressWarnings("boxing")
		final String json = format("{\"name\":\"%s\",\"number\":%s,\"email\":\"%s\"}", name, number, email);
		System.out.println("json:\n" + json);
		System.out.println("java: ");
		final SubClassWithValueAccessorWithAbstractMethod result =
			reflectionJsonToJavaConverter.convertFromJson(SubClassWithValueAccessorWithAbstractMethod.class, json);
		System.out.println(result);
		assertThat(result, is(equalTo(jsonObject)));
	}

	@Test
	public void testJsonObjectWithImplementationInheritanceWithValueAccessorWithOverriddenMethod()
			throws JsonStathamException, IllegalArgumentException, InstantiationException, IllegalAccessException,
			InvocationTargetException
	{
		System.out.println("\nReflectionJsonToJavaConverterTest.testJsonObjectWithImplementationInheritanceWithValueAccessorWithOverriddenMethod()");
		final String name = "Kevin";
		final int number = 5;
		final String email = "kevin@test.test";
		final SubClassWithValueAccessorWithOverriddenMethod jsonObject =
			new SubClassWithValueAccessorWithOverriddenMethod(name, number, email);
		final String json = "{\"name\":\"" + name + "\",\"number\":" + number + ",\"email\":\"" + email + "\"}";
		System.out.println("json:\n" + json);
		System.out.println("java: ");
		final SubClassWithValueAccessorWithOverriddenMethod result =
			reflectionJsonToJavaConverter.convertFromJson(SubClassWithValueAccessorWithOverriddenMethod.class, json);
		System.out.println(result);
		assertThat(result, is(equalTo(jsonObject)));
	}

	@Test
	public void testProxiedJsonObjectPojo() throws IllegalArgumentException, NoSuchMethodException,
			InstantiationException, IllegalAccessException, InvocationTargetException
	{
		System.out.println("\nReflectionJsonToJavaConverterTest.testProxiedJsonObjectPojo()");
		final long id = 999L;
		final String name = "ProxiedPojo";
		final JsonObjectPojo jsonObjectPojo =
			JsonObjectPojoProxyFactory.newJsonObjectPojo(new JsonObjectPojoImpl(null, null, null), Long.valueOf(id),
					name, addressList);

		final String json =
			"{\"id\":" + id + ",\"name\":\"" + name + "\",\"addresses\":" + getAddressArrayString() + "}";
		System.out.println("json:\n" + json);
		System.out.println("java: ");
		final JsonObjectPojo result = reflectionJsonToJavaConverter.convertFromJson(JsonObjectPojoImpl.class, json);
		System.out.println(result);
		assertThat(result, is(equalTo(jsonObjectPojo)));
	}

	@Test
	public void testProxiedJsonObjectPojoHavingProxiedJsonObjectPojo() throws IllegalArgumentException,
			NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException
	{
		System.out.println("\nReflectionJsonToJavaConverterTest.testProxiedJsonObjectPojoHavingProxiedJsonObjectPojo()");
		final long primaryKey = 999L;
		final String name = "ProxiedPojo";
		final long primaryKey2 = 555L;
		final String name2 = "ProxiedParent";
		final long primaryKey3 = 333L;
		final String name3 = "Not proxied";
		final NestedJsonObjectWithValueAccessor nestedJsonObjectWithValueAccessor =
			JsonObjectPojoProxyFactory.newNestedJsonObjectWithValueAccessor(new NestedJsonObjectWithValueAccessor(null,
					null, null), Long.valueOf(primaryKey), name,
					JsonObjectPojoProxyFactory.newNestedJsonObjectWithValueAccessor(
							new NestedJsonObjectWithValueAccessor(null, null, null), Long.valueOf(primaryKey2), name2,
							new NestedJsonObjectWithValueAccessor(Long.valueOf(primaryKey3), name3, null)));

		final String json =
			"{\"id\":" + primaryKey + ",\"name\":\"" + name + "\",\"parent\":{\"id\":" + primaryKey2 + ",\"name\":\""
					+ name2 + "\",\"parent\":{\"id\":" + primaryKey3 + ",\"name\":\"" + name3 + "\",\"parent\":null}}}";
		System.out.println("json:\n" + json);
		System.out.println("java: ");
		final NestedJsonObjectWithValueAccessor result =
			reflectionJsonToJavaConverter.convertFromJson(NestedJsonObjectWithValueAccessor.class, json);
		System.out.println(result);
		assertThat(result, is(equalTo(nestedJsonObjectWithValueAccessor)));
	}

	@Test
	public void testJsonObjectContainingEnums() throws JsonStathamException, IllegalArgumentException,
			InstantiationException, IllegalAccessException, InvocationTargetException
	{
		System.out.println("\nReflectionJsonToJavaConverterTest.testJsonObjectContainingEnums()");

		String json =
			"{\"name\":\"" + "Kevin" + "\",\"number\":" + 1 + ",\"passed\":" + true + ",\"role\":\"" + "SYSTEM_ADMIN"
					+ "\",\"access\":[]}";
		System.out.println("json:\n" + json);
		System.out.println("java: ");
		JsonObjectContainingEnums result =
			reflectionJsonToJavaConverter.convertFromJson(JsonObjectContainingEnums.class, json);
		System.out.println(result);
		assertThat(result, is(equalTo(new JsonObjectContainingEnums("Kevin", 1, true, Role.SYSTEM_ADMIN))));

		json =
			"{\"name\":\"" + "Kevin" + "\",\"number\":" + 1 + ",\"passed\":" + true + ",\"role\":\"" + "MEMBER"
					+ "\",\"access\":[\"BLOG\",\"EMAIL\"]}";
		System.out.println("json:\n" + json);
		System.out.println("java: ");
		result = reflectionJsonToJavaConverter.convertFromJson(JsonObjectContainingEnums.class, json);
		System.out.println(result);
		assertThat(result, is(equalTo(new JsonObjectContainingEnums("Kevin", 1, true, Role.MEMBER, Access.BLOG,
				Access.EMAIL))));

		json =
			"{\"name\":\"" + "Kevin" + "\",\"number\":" + 1 + ",\"passed\":" + true + ",\"role\":\"" + "MEMBER"
					+ "\",\"access\":[\"BLOG\",\"WIKI\",\"EMAIL\",\"TWITTER\"]}";
		System.out.println("json:\n" + json);
		System.out.println("java: ");
		result = reflectionJsonToJavaConverter.convertFromJson(JsonObjectContainingEnums.class, json);
		System.out.println(result);
		assertThat(result, is(equalTo(new JsonObjectContainingEnums("Kevin", 1, true, Role.MEMBER, Access.BLOG,
				Access.WIKI, Access.EMAIL, Access.TWITTER))));
	}
}

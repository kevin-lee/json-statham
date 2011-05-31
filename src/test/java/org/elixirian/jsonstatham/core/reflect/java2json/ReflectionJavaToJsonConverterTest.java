/**
 * 
 */
package org.elixirian.jsonstatham.core.reflect.java2json;

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
import java.util.Map.Entry;
import java.util.Set;

import org.elixirian.jsonstatham.annotation.JsonField;
import org.elixirian.jsonstatham.annotation.JsonObject;
import org.elixirian.jsonstatham.core.convertible.JsonArray;
import org.elixirian.jsonstatham.core.convertible.JsonArrayConvertible;
import org.elixirian.jsonstatham.core.convertible.JsonArrayConvertibleCreator;
import org.elixirian.jsonstatham.core.convertible.JsonObjectConvertible;
import org.elixirian.jsonstatham.core.convertible.JsonObjectConvertibleCreator;
import org.elixirian.jsonstatham.core.convertible.JsonObjectImpl;
import org.elixirian.jsonstatham.core.reflect.java2json.KnownDataStructureTypeProcessorDecider;
import org.elixirian.jsonstatham.core.reflect.java2json.KnownObjectReferenceTypeProcessorDecider;
import org.elixirian.jsonstatham.core.reflect.java2json.OneProcessorForKnownTypeDecider;
import org.elixirian.jsonstatham.core.reflect.java2json.ReflectionJavaToJsonConverter;
import org.elixirian.jsonstatham.exception.JsonStathamException;
import org.elixirian.jsonstatham.json.Address;
import org.elixirian.jsonstatham.json.ComplexJsonObjectWithValueAccessor;
import org.elixirian.jsonstatham.json.ComplexJsonObjectWithValueAccessorWithoutItsName;
import org.elixirian.jsonstatham.json.JsonObjectContainingCollection;
import org.elixirian.jsonstatham.json.JsonObjectContainingEnums;
import org.elixirian.jsonstatham.json.JsonObjectContainingIterable;
import org.elixirian.jsonstatham.json.JsonObjectContainingIterator;
import org.elixirian.jsonstatham.json.JsonObjectContainingList;
import org.elixirian.jsonstatham.json.JsonObjectContainingMapEntrySet;
import org.elixirian.jsonstatham.json.JsonObjectContainingSet;
import org.elixirian.jsonstatham.json.JsonObjectPojo;
import org.elixirian.jsonstatham.json.JsonObjectPojoImpl;
import org.elixirian.jsonstatham.json.JsonObjectPojoProxyFactory;
import org.elixirian.jsonstatham.json.JsonObjectWithDuplicateKeys;
import org.elixirian.jsonstatham.json.JsonObjectWithoutFieldName;
import org.elixirian.jsonstatham.json.NestedJsonObject;
import org.elixirian.jsonstatham.json.NestedJsonObjectWithValueAccessor;
import org.elixirian.jsonstatham.json.SecondSubClassWithOwnFields;
import org.elixirian.jsonstatham.json.SecondSubClassWithoutOwnFields;
import org.elixirian.jsonstatham.json.SomeImplementingClass;
import org.elixirian.jsonstatham.json.SomeInterface;
import org.elixirian.jsonstatham.json.SubClass;
import org.elixirian.jsonstatham.json.SubClassWithNoJsonObjectSuperClass;
import org.elixirian.jsonstatham.json.SubClassWithValueAccessor;
import org.elixirian.jsonstatham.json.SubClassWithValueAccessorWithAbstractMethod;
import org.elixirian.jsonstatham.json.SubClassWithValueAccessorWithOverriddenMethod;
import org.elixirian.jsonstatham.json.SubClassWithValueAccessorWithoutItsName;
import org.elixirian.jsonstatham.json.JsonObjectContainingEnums.Access;
import org.elixirian.jsonstatham.json.JsonObjectContainingEnums.Role;
import org.elixirian.jsonstatham.test.ItemDefinition;
import org.elixirian.jsonstatham.test.MultipleSelectionItem;
import org.elixirian.jsonstatham.test.Option;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 * <pre>
 *     ___  _____  __________  ___________ _____  ____
 *    /   \/    / /      \   \/   /_    _//     \/   /
 *   /        /  /    ___/\      / /   / /          /
 *  /        \  /    ___/  \    /_/   /_/          /
 * /____/\____\/_______/    \__//______/___/\_____/
 * </pre>
 * 
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2009-11-21)
 * @version 0.0.2 (2010-03-06) more test cases including the one testing proxy object created by javassist are added.
 * @version 0.0.3 (2010-05-10) test case for testing enum type fields is added.
 */
public class ReflectionJavaToJsonConverterTest
{
  private static final List<String> streetList = Arrays.asList("ABC Street", "90/120 Swanston St");
  private static final List<String> suburbList = Arrays.asList("", "Test Suburb");
  private static final List<String> cityList = Arrays.asList("Sydney", "Melbourne");
  private static final List<String> stateList = Arrays.asList("NSW", "VIC");
  private static final List<String> postcodeList = Arrays.asList("2000", "3000");
  private static final String[] SOME_STRING_VALUE_ARRAY = { "111", "222", "aaa", "bbb", "ccc" };

  private static final Answer<JsonObjectConvertible> ANSWER_FOR_NEW_JSON_OBJECT_CONVERTIBLE =
    new Answer<JsonObjectConvertible>() {
      @Override
      public JsonObjectConvertible answer(@SuppressWarnings("unused") InvocationOnMock invocation) throws Throwable
      {
        // TODO remove after testing.
        // return new OrgJsonJsonObjectConvertible(new JSONObject(new LinkedHashMap<String, Object>()));
        return JsonObjectImpl.newOrderedJsonObject();
      }
    };
  private static final Answer<JsonObjectConvertible> ANSWER_FOR_NULL_JSON_OBJECT_CONVERTIBLE =
    new Answer<JsonObjectConvertible>() {

      @Override
      public JsonObjectConvertible answer(@SuppressWarnings("unused") InvocationOnMock invocation) throws Throwable
      {
        // return AbstractOrgJsonJsonObjectConvertibleCreator.NULL_JSON_OBJECT_CONVERTIBLE;
        return new JsonObjectConvertible() {
          @Override
          public String[] getNames()
          {
            throw new JsonStathamException("The getNames method in NullJsonObjectConvertible cannot be used.");
          }

          @Override
          public Object get(@SuppressWarnings("unused") String name)
          {
            throw new JsonStathamException("The get method in NullJsonObjectConvertible cannot be used.");
          }

          @Override
          public Object getActualObject()
          {
            return JsonObjectImpl.NULL_JSON_OBJECT;
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
            return JsonObjectImpl.NULL_JSON_OBJECT.toString();
          }
        };
      }

    };

  private static final Answer<JsonArrayConvertible> ANSWER_FOR_JSON_ARRAY_CONVERTIBLE =
    new Answer<JsonArrayConvertible>() {

      @Override
      public JsonArrayConvertible answer(@SuppressWarnings("unused") InvocationOnMock invocation) throws Throwable
      {
        return JsonArray.newJsonArray();
      }
    };

  private List<Address> addressList;

  private Map<String, Address> addressMap;

  private ReflectionJavaToJsonConverter reflectionJavaToJsonConverter;

  private Address address;

  /**
   * @throws java.lang.Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception
  {
    System.out.println("###  ReflectionJavaToJsonConverterTest starts ###");
  }

  /**
   * @throws java.lang.Exception
   */
  @AfterClass
  public static void tearDownAfterClass() throws Exception
  {
    System.out.println("\n### ReflectionJavaToJsonConverterTest ends ###");
  }

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception
  {
    final JsonObjectConvertibleCreator jsonObjectConvertibleCreator = mock(JsonObjectConvertibleCreator.class);
    when(jsonObjectConvertibleCreator.newJsonObjectConvertible()).thenAnswer(ANSWER_FOR_NEW_JSON_OBJECT_CONVERTIBLE);
    when(jsonObjectConvertibleCreator.nullJsonObjectConvertible()).thenAnswer(ANSWER_FOR_NULL_JSON_OBJECT_CONVERTIBLE);

    final JsonArrayConvertibleCreator jsonArrayConvertibleCreator = mock(JsonArrayConvertibleCreator.class);
    when(jsonArrayConvertibleCreator.newJsonArrayConvertible()).thenAnswer(ANSWER_FOR_JSON_ARRAY_CONVERTIBLE);

    reflectionJavaToJsonConverter =
      new ReflectionJavaToJsonConverter(jsonObjectConvertibleCreator, jsonArrayConvertibleCreator,
          new KnownDataStructureTypeProcessorDecider(), new KnownObjectReferenceTypeProcessorDecider(),
          new OneProcessorForKnownTypeDecider());
    address = new Address(streetList.get(0), suburbList.get(0), cityList.get(0), stateList.get(0), postcodeList.get(0));

    addressList = new ArrayList<Address>();
    for (int i = 0, size = streetList.size(); i < size; i++)
    {
      addressList.add(new Address(streetList.get(i), suburbList.get(i), cityList.get(i), stateList.get(i),
          postcodeList.get(i)));
    }

    addressMap = new LinkedHashMap<String, Address>();
    for (int i = 0, size = streetList.size(); i < size; i++)
    {
      addressMap.put("address" + i, new Address(streetList.get(i), suburbList.get(i), cityList.get(i),
          stateList.get(i), postcodeList.get(i)));
    }
  }

  /**
   * @throws java.lang.Exception
   */
  @After
  public void tearDown() throws Exception
  {
  }

  @Test(expected = IllegalArgumentException.class)
  public void testUnknownType() throws IllegalArgumentException, JsonStathamException, IllegalAccessException
  {
    class UnknownType
    {
    }
    reflectionJavaToJsonConverter.convertIntoJson(new UnknownType());
  }

  @Test
  public void testNull() throws IllegalArgumentException, JsonStathamException, IllegalAccessException
  {
    System.out.println("\nReflectionJsonStathamTest.testNull()");
    final String expected = "null";
    System.out.println("expected:\n" + expected);
    System.out.println("actual: ");

    /* test convertIntoJson */
    final String result1_1 = reflectionJavaToJsonConverter.convertIntoJson(null);
    System.out.println(result1_1);
    assertThat(result1_1, is(equalTo(expected.toString())));

    /* test convertFromJson */
    // final Object result1_2 = jsonStatham.convertFromJson(null, expected);
    // assertThat(result1_2, is(nullValue()));
  }

  @Test
  public void testJsonHavingNullValue() throws IllegalArgumentException, JsonStathamException, IllegalAccessException
  {
    System.out.println("\nJsonStathamInActionTest.testJsonHavingNullValue()");
    @JsonObject
    class TestPojo
    {
      @SuppressWarnings("unused")
      @JsonField
      private Object object = null;
    }
    final String expected2 = "{\"object\":null}";
    System.out.println("expected:\n" + expected2);
    System.out.println("actual: ");
    final String result2 = reflectionJavaToJsonConverter.convertIntoJson(new TestPojo());
    System.out.println(result2);
    assertEquals(expected2.toString(), result2);

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
  public void testEmptyArray() throws IllegalArgumentException, JsonStathamException, IllegalAccessException
  {
    System.out.println("\nReflectionJavaToJsonConverterTest.testEmptyArray()");

    final String expected = "[]";
    System.out.println("\nexpected:\n" + expected);
    System.out.println("actual: ");
    final String resultIntArray = reflectionJavaToJsonConverter.convertIntoJson(new int[] {});
    System.out.println(resultIntArray);
    assertThat(resultIntArray, is(equalTo(expected)));

    System.out.println("\nexpected:\n" + expected);
    System.out.println("actual: ");
    final String result = reflectionJavaToJsonConverter.convertIntoJson(new Object[0]);
    System.out.println(result);
    assertThat(result, is(equalTo(expected)));
  }

  @Test
  public void testArray() throws IllegalArgumentException, JsonStathamException, IllegalAccessException
  {
    System.out.println("\nJsonStathamInActionTest.testArray()");

    final String expectedIntArray = "[1,2,3,4,5,8,23,56]";
    System.out.println("\nexpected:\n" + expectedIntArray);
    System.out.println("actual: ");
    final String resultIntArray = reflectionJavaToJsonConverter.convertIntoJson(new int[] { 1, 2, 3, 4, 5, 8, 23, 56 });
    System.out.println(resultIntArray);
    assertThat(resultIntArray, is(equalTo(expectedIntArray)));

    final String expectedDoubleArray = "[1.2,2.6,3.3,4.8,5.234,8.567,23.48754,56.0547]";
    System.out.println("\nexpected:\n" + expectedDoubleArray);
    System.out.println("actual: ");
    final String resultDoubleArray =
      reflectionJavaToJsonConverter.convertIntoJson(new double[] { 1.2, 2.6, 3.3, 4.8, 5.234, 8.567, 23.48754, 56.0547 });
    System.out.println(resultDoubleArray);
    assertThat(resultDoubleArray, is(equalTo(expectedDoubleArray)));

    final String expectedBooleanArray = "[true,false,false,true,false,true,false,true,true]";
    System.out.println("\nexpected:\n" + expectedBooleanArray);
    System.out.println("actual: ");
    final String resultBooleanArray =
      reflectionJavaToJsonConverter.convertIntoJson(new boolean[] { true, false, false, true, false, true, false, true,
          true });
    System.out.println(resultBooleanArray);
    assertThat(resultBooleanArray, is(equalTo(expectedBooleanArray)));
  }

  @Test
  public void testArrayHavingPojo() throws IllegalArgumentException, JsonStathamException, IllegalAccessException
  {
    System.out.println("\nJsonStathamInActionTest.testArrayHavingPojo()");
    final String expected = getAddressArrayString();
    System.out.println("expected:\n" + expected);
    System.out.println("actual: ");
    final String result =
      reflectionJavaToJsonConverter.convertIntoJson(addressList.toArray(new Address[addressList.size()]));
    System.out.println(result);
    assertEquals(expected, result);
  }

  @Test
  public void testArray2() throws IllegalArgumentException, JsonStathamException, IllegalAccessException
  {
    System.out.println("\nReflectionJavaToJsonConverterTest.testArray2()");

    final List<String> stringList = Arrays.asList("aaa", "bbb", "ccc");
    final String expected =
      "[\"" + stringList.get(0) + "\",\"" + stringList.get(1) + "\",\"" + stringList.get(2) + "\"]";
    System.out.println("expected:\n" + expected);
    System.out.println("actual: ");
    final String result =
      reflectionJavaToJsonConverter.convertIntoJson(new String[] { stringList.get(0), stringList.get(1),
          stringList.get(2) });
    System.out.println(result);
    assertThat(result, is(equalTo(expected)));
  }

  @Test
  public void testCollection() throws IllegalArgumentException, JsonStathamException, IllegalAccessException
  {
    System.out.println("\nReflectionJavaToJsonConverterTest.testCollection()");
    final List<String> stringList = Arrays.asList("aaa", "bbb", "ccc");
    final String expected =
      "[\"" + stringList.get(0) + "\",\"" + stringList.get(1) + "\",\"" + stringList.get(2) + "\"]";
    System.out.println("expected:\n" + expected);
    System.out.println("actual: ");
    final String result =
      reflectionJavaToJsonConverter.convertIntoJson(Arrays.asList(stringList.get(0), stringList.get(1),
          stringList.get(2)));
    System.out.println(result);
    assertThat(result, is(equalTo(expected)));
  }

  /**
   * Test method for {@link org.elixirian.jsonstatham.core.JsonStathamInAction#convertIntoJson(java.lang.Object)} with
   * List as the parameter object.
   * 
   * @throws IllegalAccessException
   * @throws JsonStathamException
   * @throws IllegalArgumentException
   */
  @Test
  public void testList() throws IllegalArgumentException, JsonStathamException, IllegalAccessException
  {
    final String expected = getAddressArrayString();
    System.out.println("\nReflectionJsonStathamTest.testList()");
    System.out.println("expected:\n" + expected);
    System.out.println("actual: ");
    final String result = reflectionJavaToJsonConverter.convertIntoJson(addressList);
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
   * Test method for {@link org.elixirian.jsonstatham.core.JsonStathamInAction#convertIntoJson(java.lang.Object)}.
   * 
   * @throws IllegalAccessException
   * @throws JsonStathamException
   * @throws IllegalArgumentException
   */
  @Test
  public void testMap() throws IllegalArgumentException, JsonStathamException, IllegalAccessException
  {
    final String expected = getAddressMapString();
    System.out.println("\nReflectionJsonStathamTest.testMap()");
    System.out.println("expected:\n" + expected);
    System.out.println("actual: ");
    final String result = reflectionJavaToJsonConverter.convertIntoJson(addressMap);
    System.out.println(result);
    assertEquals(expected, result);
  }

  @Test
  public void testMap2() throws IllegalArgumentException, JsonStathamException, IllegalAccessException
  {
    System.out.println("\nReflectionJavaToJsonConverterTest.testMap2()");
    final List<String> surnames = new ArrayList<String>();
    final List<String> givenNames = new ArrayList<String>();
    surnames.add("Lee");
    givenNames.add("Kevin");
    surnames.add("Kent");
    givenNames.add("Clark");
    surnames.add("Wayne");
    givenNames.add("Bruce");
    final String expected =
      "{\"" + surnames.get(0) + "\":\"" + givenNames.get(0) + "\",\"" + surnames.get(1) + "\":\"" + givenNames.get(1)
          + "\",\"" + surnames.get(2) + "\":\"" + givenNames.get(2) + "\"}";
    System.out.println("expected:\n" + expected);
    System.out.println("actual: ");
    final Map<String, String> surnameToGivenNameMap = new LinkedHashMap<String, String>();
    surnameToGivenNameMap.put("Lee", "Kevin");
    surnameToGivenNameMap.put("Kent", "Clark");
    surnameToGivenNameMap.put("Wayne", "Bruce");
    final String result = reflectionJavaToJsonConverter.convertIntoJson(surnameToGivenNameMap);
    System.out.println(result);
    assertThat(result, is(equalTo(expected)));

  }

  @Test
  public void testNestedMap() throws IllegalArgumentException, JsonStathamException, IllegalAccessException
  {
    final String expected = "{\"test1\":" + getAddressMapString() + ",\"test2\":" + getAddressMapString() + "}";
    System.out.println("\nReflectionJsonStathamTest.testNestedMap()");
    System.out.println("expected: \n" + expected);
    Map<String, Map<String, Address>> nestedMap = new HashMap<String, Map<String, Address>>();
    nestedMap.put("test1", addressMap);
    nestedMap.put("test2", addressMap);
    System.out.println("actual: ");
    final String result = reflectionJavaToJsonConverter.convertIntoJson(nestedMap);
    System.out.println(result);
    assertEquals(expected, result);
  }

  @SuppressWarnings({ "boxing", "unchecked" })
  @Test
  public void testMapHavingNestedLists() throws IllegalArgumentException, JsonStathamException, IllegalAccessException
  {
    System.out.println("\nReflectionJavaToJsonConverterTest.testMapHavingNestedLists()");
    /* @formatter:off */
		final String expected = 
			"{\"Kevin\":[[1,2,3,4,5,6,7,8,9,10],[11,12,13,14,15,16,17,18,19,20],[21,22,23,24,25,26,27,28,29,30]]," +
			 "\"Lee\":[[100,200,300,400,500,600,700,800,900,1000],[1100,1200,1300,1400,1500,1600,1700,1800,1900,11000]]}";
		/* @formatter:on */
    System.out.println("expected:\n" + expected);
    System.out.println("actual: ");
    final Map<String, List<List<Integer>>> map = new LinkedHashMap<String, List<List<Integer>>>();
    /* @formatter:off */
		map.put("Kevin",
			Arrays.asList(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10),
						  Arrays.asList(11, 12, 13, 14, 15, 16, 17, 18, 19, 20),
						  Arrays.asList(21, 22, 23, 24, 25, 26, 27, 28, 29, 30)));
		map.put("Lee",
			Arrays.asList(Arrays.asList(100, 200, 300, 400, 500, 600, 700, 800, 900, 1000),
						  Arrays.asList(1100, 1200, 1300, 1400, 1500, 1600, 1700, 1800, 1900, 11000)));
		/* @formatter:on */
    final String result = reflectionJavaToJsonConverter.convertIntoJson(map);
    System.out.println(result);
    assertThat(result, is(equalTo(expected)));
  }

  /**
   * Test method for {@link org.elixirian.jsonstatham.core.JsonStathamInAction#convertIntoJson(java.lang.Object)}.
   * 
   * @throws IllegalAccessException
   * @throws JsonStathamException
   * @throws IllegalArgumentException
   */
  @Test
  public void testAddress() throws IllegalArgumentException, JsonStathamException, IllegalAccessException
  {
    System.out.println("\nReflectionJsonStathamTest.testAddress()");

    final String expected =
      "{\"street\":\"" + streetList.get(0) + "\",\"suburb\":\"" + suburbList.get(0) + "\",\"city\":\""
          + cityList.get(0) + "\",\"state\":\"" + stateList.get(0) + "\",\"postcode\":\"" + postcodeList.get(0) + "\"}";
    System.out.println("expected:\n" + expected);
    System.out.println("actual: ");
    final String result = reflectionJavaToJsonConverter.convertIntoJson(address);
    System.out.println(result);
    assertEquals(expected, result);
  }

  /**
   * Test method for {@link org.elixirian.jsonstatham.core.JsonStathamInAction#convertIntoJson(java.lang.Object)}.
   * 
   * @throws IllegalAccessException
   * @throws JsonStathamException
   * @throws IllegalArgumentException
   */
  @Test
  public void testNestedJsonObject() throws IllegalArgumentException, JsonStathamException, IllegalAccessException
  {
    final long id = 1;
    final String name = "jsonObject";
    NestedJsonObject jsonObject = new NestedJsonObject();
    jsonObject.setPrimaryKey(Long.valueOf(id));
    jsonObject.setName(name);
    jsonObject.setAddress(address);
    jsonObject.setIntNumber(Integer.MAX_VALUE);
    jsonObject.setDoubleNumber(Double.MAX_VALUE);

    final String expected =
      "{\"id\":" + id + ",\"name\":\"" + name + "\",\"address\":{\"street\":\"" + streetList.get(0)
          + "\",\"suburb\":\"" + suburbList.get(0) + "\",\"city\":\"" + cityList.get(0) + "\",\"state\":\""
          + stateList.get(0) + "\",\"postcode\":\"" + postcodeList.get(0) + "\"},\"intNumber\":" + Integer.MAX_VALUE
          + ",\"doubleNumber\":" + Double.MAX_VALUE + "}";
    System.out.println("\nReflectionJsonStathamTest.testNestedJsonObject()");
    System.out.println("expected:\n" + expected);
    System.out.println("actual: ");
    final String result = reflectionJavaToJsonConverter.convertIntoJson(jsonObject);
    System.out.println(result);
    assertThat(result, is(equalTo(expected)));

    final Long id2 = Long.valueOf(id + 100);
    final String name2 = name + "4Testing";
    jsonObject.setPrimaryKey(id2);
    jsonObject.setName(name2);
    jsonObject.setAddress(new Address(streetList.get(1), suburbList.get(1), cityList.get(1), stateList.get(1),
        postcodeList.get(1)));
    jsonObject.setIntNumber(Integer.MIN_VALUE);
    jsonObject.setDoubleNumber(Double.MIN_VALUE);

    final String expected2 =
      "{\"id\":" + id2 + ",\"name\":\"" + name2 + "\",\"address\":{\"street\":\"" + streetList.get(1)
          + "\",\"suburb\":\"" + suburbList.get(1) + "\",\"city\":\"" + cityList.get(1) + "\",\"state\":\""
          + stateList.get(1) + "\",\"postcode\":\"" + postcodeList.get(1) + "\"},\"intNumber\":" + Integer.MIN_VALUE
          + ",\"doubleNumber\":" + Double.MIN_VALUE + "}";
    System.out.println("\nReflectionJsonStathamTest.testNestedJsonObject()");
    System.out.println("expected:\n" + expected2);
    System.out.println("actual: ");
    final String result2 = reflectionJavaToJsonConverter.convertIntoJson(jsonObject);
    System.out.println(result2);
    assertThat(result2, is(equalTo(expected2)));

    final Long id3 = Long.valueOf(id + 100);
    final String name3 = name + "4Testing";
    jsonObject.setPrimaryKey(id3);
    jsonObject.setName(name3);
    jsonObject.setAddress(new Address(streetList.get(0), suburbList.get(0), cityList.get(0), stateList.get(0),
        postcodeList.get(0)));
    jsonObject.setIntNumber(Integer.MAX_VALUE >>> 1);
    jsonObject.setDoubleNumber(1234.1000D);

    final String expected3 =
      "{\"id\":" + id3 + ",\"name\":\"" + name3 + "\",\"address\":{\"street\":\"" + streetList.get(0)
          + "\",\"suburb\":\"" + suburbList.get(0) + "\",\"city\":\"" + cityList.get(0) + "\",\"state\":\""
          + stateList.get(0) + "\",\"postcode\":\"" + postcodeList.get(0) + "\"},\"intNumber\":"
          + (Integer.MAX_VALUE >>> 1) + ",\"doubleNumber\":1234.1}";
    System.out.println("\nReflectionJsonStathamTest.testNestedJsonObject()");
    System.out.println("expected:\n" + expected3);
    System.out.println("actual: ");
    final String result3 = reflectionJavaToJsonConverter.convertIntoJson(jsonObject);
    System.out.println(result3);
    assertThat(result3, is(equalTo(expected3)));

    jsonObject.setPrimaryKey(Long.valueOf(id));
    jsonObject.setName(name);
    jsonObject.setAddress(address);
    jsonObject.setIntNumber(Integer.MAX_VALUE);
    jsonObject.setDoubleNumber(1234.0D);

    final String expected4 =
      "{\"id\":" + id + ",\"name\":\"" + name + "\",\"address\":{\"street\":\"" + streetList.get(0)
          + "\",\"suburb\":\"" + suburbList.get(0) + "\",\"city\":\"" + cityList.get(0) + "\",\"state\":\""
          + stateList.get(0) + "\",\"postcode\":\"" + postcodeList.get(0)
          + "\"},\"intNumber\":2147483647,\"doubleNumber\":1234}";
    System.out.println("\nReflectionJsonStathamTest.testNestedJsonObject()");
    System.out.println("expected:\n" + expected4);
    System.out.println("actual: ");
    final String result4 = reflectionJavaToJsonConverter.convertIntoJson(jsonObject);
    System.out.println(result4);
    assertThat(result4, is(equalTo(expected4)));

    jsonObject.setPrimaryKey(Long.valueOf(id));
    jsonObject.setName(name);
    jsonObject.setAddress(address);
    jsonObject.setIntNumber(Integer.MAX_VALUE);
    jsonObject.setDoubleNumber(123456789.1234D);

    final String expected5 =
      "{\"id\":" + id + ",\"name\":\"" + name + "\",\"address\":{\"street\":\"" + streetList.get(0)
          + "\",\"suburb\":\"" + suburbList.get(0) + "\",\"city\":\"" + cityList.get(0) + "\",\"state\":\""
          + stateList.get(0) + "\",\"postcode\":\"" + postcodeList.get(0) + "\"},\"intNumber\":" + Integer.MAX_VALUE
          + ",\"doubleNumber\":" + 123456789.1234D + "}";
    System.out.println("\nReflectionJsonStathamTest.testNestedJsonObject()");
    System.out.println("expected:\n" + expected5);
    System.out.println("actual: ");
    final String result5 = reflectionJavaToJsonConverter.convertIntoJson(jsonObject);
    System.out.println(result5);
    assertThat(result5, is(equalTo(expected5)));
  }

  @Test(expected = JsonStathamException.class)
  public void testJsonObjectWithDuplicateKeys() throws IOException, IllegalArgumentException, IllegalAccessException
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
      result = reflectionJavaToJsonConverter.convertIntoJson(jsonObjectWithDuplicateKeys);
    }
    catch (JsonStathamException e)
    {
      System.out.println(e.getMessage());
      throw e;
    }
    System.out.println(result);
  }

  @Test
  public void testJsonObjectWithoutFieldName() throws IllegalArgumentException, JsonStathamException,
      IllegalAccessException
  {
    System.out.println("\nReflectionJsonStathamTest.testJsonObjectWithoutFieldName()");
    final int id = 5;
    final String name = "Kevin Lee";
    final String address = "123 ABC Street";
    final JsonObjectWithoutFieldName jsonObjectWithoutFieldName = new JsonObjectWithoutFieldName(id, name, address);
    final String expected = "{\"id\":" + id + ",\"name\":\"" + name + "\",\"address\":\"" + address + "\"}";
    System.out.println("expected:\n" + expected);
    System.out.println("actual: ");
    final String result = reflectionJavaToJsonConverter.convertIntoJson(jsonObjectWithoutFieldName);
    System.out.println(result);
    assertThat(result, is(equalTo(expected)));
  }

  @Test
  public void testComplexJsonObjectWithMethodUse() throws IllegalArgumentException, JsonStathamException,
      IllegalAccessException
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
      "{\"id\":1,\"name\":\"Kevin\"," + "\"address\":{\"street\":\"" + address.getStreet() + "\",\"suburb\":\""
          + address.getSuburb() + "\",\"city\":\"" + address.getCity() + "\",\"state\":\"" + address.getState()
          + "\",\"postcode\":\"" + address.getPostcode() + "\"}," + "\"date\":\"" + date.toString() + "\","
          + "\"dateWithValueAccessor\":\"" + jsonObject.getDateString() + "\",\"calendar\":\""
          + jsonObject.getCalendar()
              .getTime()
              .toString() + "\",\"calendarWithValueAccessor\":\"" + jsonObject.getCalendarString() + "\"}";
    System.out.println("\nReflectionJsonStathamTest.testComplexJsonObjectWithMethodUse()");
    System.out.println("expected:\n" + expected);
    System.out.println("actual: ");
    final String result = reflectionJavaToJsonConverter.convertIntoJson(jsonObject);
    System.out.println(result);
    assertThat(result, is(equalTo(expected)));
  }

  @Test
  public void testComplexJsonObjectWithValueAccessorWithoutItsName() throws IllegalArgumentException,
      JsonStathamException, IllegalAccessException
  {
    ComplexJsonObjectWithValueAccessorWithoutItsName jsonObject =
      new ComplexJsonObjectWithValueAccessorWithoutItsName();
    jsonObject.setPrimaryKey(Long.valueOf(1));
    jsonObject.setName("Kevin");
    jsonObject.setRegistered(true);
    jsonObject.setEnabled(false);
    jsonObject.setAddress(address);
    Date date = new Date();
    Calendar calendar = Calendar.getInstance();
    jsonObject.setDate(date);
    jsonObject.setDateWithValueAccessor(date);
    jsonObject.setCalendar(calendar);
    jsonObject.setCalendarWithValueAccessor(calendar);

    final String expected =
      "{\"id\":1,\"name\":\"Kevin\",\"registered\":true,\"enabled\":false,\"address\":{\"street\":\""
          + address.getStreet() + "\",\"suburb\":\"" + address.getSuburb() + "\",\"city\":\"" + address.getCity()
          + "\",\"state\":\"" + address.getState() + "\",\"postcode\":\"" + address.getPostcode() + "\"},"
          + "\"date\":\"" + date.toString() + "\"," + "\"dateWithValueAccessor\":\""
          + jsonObject.getDateWithValueAccessor() + "\",\"calendar\":\"" + jsonObject.getCalendar()
              .getTime()
              .toString() + "\",\"calendarWithValueAccessor\":\"" + jsonObject.getCalendarWithValueAccessor() + "\"}";
    System.out.println("\nReflectionJsonStathamTest.testComplexJsonObjectWithValueAccessorWithoutItsName()");
    System.out.println("expected:\n" + expected);
    System.out.println("actual: ");
    final String result = reflectionJavaToJsonConverter.convertIntoJson(jsonObject);
    System.out.println(result);
    assertThat(result, is(equalTo(expected)));
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
  public void testJsonObjectContainingCollection() throws IllegalArgumentException, JsonStathamException,
      IllegalAccessException
  {
    final String nameValue = "testJsonWithCollection";
    Collection<String> collection =
      initialiseCollectionWithStringValues(new ArrayList<String>(), SOME_STRING_VALUE_ARRAY);

    JsonObjectContainingCollection jsonObjectContainingCollection =
      new JsonObjectContainingCollection(nameValue, collection);
    final String expected = getExpectedJsonArray("name", nameValue, "valueCollection");
    System.out.println("\nReflectionJsonStathamTest.testJsonObjectContainingCollection()");
    System.out.println("expected:\n" + expected);
    System.out.println("actual: ");
    final String result = reflectionJavaToJsonConverter.convertIntoJson(jsonObjectContainingCollection);
    System.out.println(result);
    assertThat(result, is(equalTo(expected)));
  }

  @Test
  public void testJsonObjectContainingList() throws IllegalArgumentException, JsonStathamException,
      IllegalAccessException
  {
    final String nameValue = "testJsonWithList";
    List<String> list = initialiseCollectionWithStringValues(new ArrayList<String>(), SOME_STRING_VALUE_ARRAY);

    JsonObjectContainingList jsonObjectContainingList = new JsonObjectContainingList(nameValue, list);
    final String expected = getExpectedJsonArray("name", nameValue, "valueList");
    System.out.println("\nReflectionJsonStathamTest.testJsonObjectContainingList()");
    System.out.println("expected:\n" + expected);
    System.out.println("actual: ");
    final String result = reflectionJavaToJsonConverter.convertIntoJson(jsonObjectContainingList);
    System.out.println(result);
    assertThat(result, is(equalTo(expected)));
  }

  @Test
  public void testJsonObjectContainingSet() throws IllegalArgumentException, JsonStathamException,
      IllegalAccessException
  {
    final String nameValue = "testJsonWithSet";
    Set<String> set = initialiseCollectionWithStringValues(new LinkedHashSet<String>(), SOME_STRING_VALUE_ARRAY);

    JsonObjectContainingSet jsonObjectContainingSet = new JsonObjectContainingSet(nameValue, set);
    final String expected = getExpectedJsonArray("name", nameValue, "valueSet");
    System.out.println("\nReflectionJsonStathamTest.testJsonObjectContainingSet()");
    System.out.println("expected:\n" + expected);
    System.out.println("actual: ");
    final String result = reflectionJavaToJsonConverter.convertIntoJson(jsonObjectContainingSet);
    System.out.println(result);
    assertThat(result, is(equalTo(expected)));
  }

  @Test
  public void testJsonObjectContainingMapEntrySetSet() throws IllegalArgumentException, JsonStathamException,
      IllegalAccessException
  {
    final String nameValue = "testJsonObjectContainingMapEntrySetSet";

    JsonObjectContainingMapEntrySet jsonObjectContainingSet =
      new JsonObjectContainingMapEntrySet(nameValue, addressMap.entrySet());

    StringBuilder stringBuilder =
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
    final String expected = stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), "]}")
        .toString();

    System.out.println("\nReflectionJsonStathamTest.testJsonObjectContainingMapEntrySetSet()");
    System.out.println("expected:\n" + expected);
    System.out.println("actual: ");
    final String result = reflectionJavaToJsonConverter.convertIntoJson(jsonObjectContainingSet);
    System.out.println(result);
    assertThat(result, is(equalTo(expected)));
  }

  @Test
  public void testJsonObjectContainingIterator() throws IllegalArgumentException, JsonStathamException,
      IllegalAccessException
  {
    final String nameValue = "testJsonObjectContainingIterator";
    Collection<String> collection =
      initialiseCollectionWithStringValues(new ArrayList<String>(), SOME_STRING_VALUE_ARRAY);

    JsonObjectContainingIterator jsonObjectContainingCollection =
      new JsonObjectContainingIterator(nameValue, collection.iterator());
    final String expected = getExpectedJsonArray("name", nameValue, "valueIterator");
    System.out.println("\nReflectionJsonStathamTest.testJsonObjectContainingIterator()");
    System.out.println("expected:\n" + expected);
    System.out.println("actual: ");
    final String result = reflectionJavaToJsonConverter.convertIntoJson(jsonObjectContainingCollection);
    System.out.println(result);
    assertThat(result, is(equalTo(expected)));
  }

  @Test
  public void testJsonObjectContainingIterable() throws IllegalArgumentException, JsonStathamException,
      IllegalAccessException
  {
    final String nameValue = "testJsonObjectContainingIterable";
    Iterable<String> iterable = new Iterable<String>() {
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
    final String result = reflectionJavaToJsonConverter.convertIntoJson(jsonObjectContainingCollection);
    System.out.println(result);
    assertThat(result, is(equalTo(expected)));
  }

  @Test
  public void testJsonObjectWithInterfaceInheritance() throws IllegalArgumentException, JsonStathamException,
      IllegalAccessException
  {
    final String name = "Kevin Lee";
    final int number = 99;
    final String email = "kevinlee@test.test";
    SomeInterface jsonObject = new SomeImplementingClass(name, number, email);
    System.out.println("\nReflectionJsonStathamTest.testJsonObjectWithInterfaceInheritance()");
    final String expected = "{\"name\":\"" + name + "\",\"number\":" + number + ",\"email\":\"" + email + "\"}";
    System.out.println("expected:\n" + expected);
    System.out.println("actual: ");
    final String result = reflectionJavaToJsonConverter.convertIntoJson(jsonObject);
    System.out.println(result);
    assertThat(result, is(equalTo(expected)));
  }

  @Test
  public void testJsonObjectWithImplementationInheritance() throws IllegalArgumentException, JsonStathamException,
      IllegalAccessException
  {
    final String name = "Kevin";
    final int number = 5;
    final String email = "kevin@test.test";
    SubClass jsonObject = new SubClass(name, number, email);
    System.out.println("\nReflectionJsonStathamTest.testJsonObjectWithImplementationInheritance()");
    final String expected = "{\"name\":\"" + name + "\",\"number\":" + number + ",\"email\":\"" + email + "\"}";
    System.out.println("expected:\n" + expected);
    System.out.println("actual: ");
    final String result = reflectionJavaToJsonConverter.convertIntoJson(jsonObject);
    System.out.println(result);
    assertThat(result, is(equalTo(expected)));
  }

  @Test
  public void testJsonObjectWithDoubleImplementationInheritanceAndNoOwnFieldsInSecondSubClass()
      throws IllegalArgumentException, JsonStathamException, IllegalAccessException
  {
    final String name = "Kevin";
    final int number = 11;
    final String email = "kevin@test.blahblah";
    SubClass jsonObject = new SecondSubClassWithoutOwnFields(name, number, email);
    System.out.println("\nReflectionJsonStathamTest.testJsonObjectWithDoubleImplementationInheritanceAndNoOwnFieldsInSecondSubClass()");
    final String expected = "{\"name\":\"" + name + "\",\"number\":" + number + ",\"email\":\"" + email + "\"}";
    System.out.println("expected:\n" + expected);
    System.out.println("actual: ");
    final String result = reflectionJavaToJsonConverter.convertIntoJson(jsonObject);
    System.out.println(result);
    assertThat(result, is(equalTo(expected)));
  }

  @Test
  public void testJsonObjectWithDoubleImplementationInheritanceAndOwnFieldsInSecondSubClass()
      throws IllegalArgumentException, JsonStathamException, IllegalAccessException
  {
    final String name = "Mr. Lee";
    final int number = 999;
    final String email = "kevin@another.email";
    final String comment = "Blah blah";
    SecondSubClassWithOwnFields jsonObject = new SecondSubClassWithOwnFields(name, number, email, address, comment);
    System.out.println("\nReflectionJsonStathamTest.testJsonObjectWithDoubleImplementationInheritanceAndOwnFieldsInSecondSubClass()");
    final String expected =
      "{\"name\":\"" + name + "\",\"number\":" + number + ",\"email\":\"" + email + "\"," + "\"address\":"
          + "{\"street\":\"" + address.getStreet() + "\",\"suburb\":\"" + address.getSuburb() + "\",\"city\":\""
          + address.getCity() + "\",\"state\":\"" + address.getState() + "\",\"postcode\":\"" + address.getPostcode()
          + "\"},\"comment\":\"" + comment + "\"}";
    System.out.println("expected:\n" + expected);
    System.out.println("actual: ");
    final String result = reflectionJavaToJsonConverter.convertIntoJson(jsonObject);
    System.out.println(result);
    assertThat(result, is(equalTo(expected)));
  }

  @Test
  public void testJsonObjectWithImplementationInheritanceWithNoJsonObjectSuperClass() throws IllegalArgumentException,
      JsonStathamException, IllegalAccessException
  {
    final String name = "Kevin";
    final int number = 5;
    final String email = "kevin@test.test";
    SubClassWithNoJsonObjectSuperClass jsonObject = new SubClassWithNoJsonObjectSuperClass(name, number, email);
    System.out.println("\nReflectionJsonStathamTest.testJsonObjectWithImplementationInheritanceWithNoJsonObjectSuperClass()");
    final String expected = "{\"email\":\"" + email + "\"}";
    System.out.println("expected:\n" + expected);
    System.out.println("actual: ");
    final String result = reflectionJavaToJsonConverter.convertIntoJson(jsonObject);
    System.out.println(result);
    assertThat(result, is(equalTo(expected)));
  }

  @Test
  public void testJsonObjectWithImplementationInheritanceWithValueAccessor() throws IllegalArgumentException,
      JsonStathamException, IllegalAccessException
  {
    final String name = "Kevin";
    final int number = 5;
    final String email = "kevin@test.test";
    SubClassWithValueAccessor jsonObject = new SubClassWithValueAccessor(name, number, email);
    System.out.println("\nReflectionJsonStathamTest.testJsonObjectWithImplementationInheritanceWithValueAccessor()");
    final String expected =
      "{\"name\":\"My name is " + name + "\",\"number\":\"The number is " + number
          + "\",\"email\":\"My email address is " + email + "\"}";
    System.out.println("expected:\n" + expected);
    System.out.println("actual: ");
    final String result = reflectionJavaToJsonConverter.convertIntoJson(jsonObject);
    System.out.println(result);
    assertThat(result, is(equalTo(expected)));
  }

  @Test
  public void testJsonObjectWithImplementationInheritanceWithValueAccessorWithoutItsName()
      throws IllegalArgumentException, JsonStathamException, IllegalAccessException
  {
    final String name = "Kevin";
    final int number = 5;
    final String email = "kevin@test.test";
    SubClassWithValueAccessorWithoutItsName jsonObject =
      new SubClassWithValueAccessorWithoutItsName(name, number, email);
    System.out.println("\nReflectionJsonStathamTest.testJsonObjectWithImplementationInheritanceWithValueAccessorWithoutItsName()");
    final String expected =
      "{\"name\":\"My name is " + name + "\",\"number\":\"The number is " + number
          + "\",\"email\":\"My email address is " + email + "\"}";
    System.out.println("expected:\n" + expected);
    System.out.println("actual: ");
    final String result = reflectionJavaToJsonConverter.convertIntoJson(jsonObject);
    System.out.println(result);
    assertThat(result, is(equalTo(expected)));
  }

  @Test
  public void testJsonObjectWithImplementationInheritanceWithValueAccessorWithAbstractMethod()
      throws IllegalArgumentException, JsonStathamException, IllegalAccessException
  {
    final String name = "Kevin";
    final int number = 5;
    final String email = "kevin@test.test";
    SubClassWithValueAccessorWithAbstractMethod jsonObject =
      new SubClassWithValueAccessorWithAbstractMethod(name, number, email);
    System.out.println("\nReflectionJsonStathamTest.testJsonObjectWithImplementationInheritanceWithValueAccessorWithAbstractMethod()");
    final String expected =
      "{\"name\":\"My name is nobody.\",\"number\":\"The number is 100.\",\"email\":\"My email address is " + email
          + "\"}";
    System.out.println("expected:\n" + expected);
    System.out.println("actual: ");
    final String result = reflectionJavaToJsonConverter.convertIntoJson(jsonObject);
    System.out.println(result);
    assertThat(result, is(equalTo(expected)));
  }

  @Test
  public void testJsonObjectWithImplementationInheritanceWithValueAccessorWithOverriddenMethod()
      throws IllegalArgumentException, JsonStathamException, IllegalAccessException
  {
    final String name = "Kevin";
    final int number = 5;
    final String email = "kevin@test.test";
    SubClassWithValueAccessorWithOverriddenMethod jsonObject =
      new SubClassWithValueAccessorWithOverriddenMethod(name, number, email);
    System.out.println("\nReflectionJsonStathamTest.testJsonObjectWithImplementationInheritanceWithValueAccessorWithOverriddenMethod()");
    final String expected =
      "{\"name\":\"My name is " + name + "\",\"number\":\"The number is " + number
          + "\",\"email\":\"My email address is " + email + "\"}";
    System.out.println("expected:\n" + expected);
    System.out.println("actual: ");
    final String result = reflectionJavaToJsonConverter.convertIntoJson(jsonObject);
    System.out.println(result);
    assertThat(result, is(equalTo(expected)));
  }

  @Test
  public void testProxiedJsonObjectPojo() throws IllegalArgumentException, NoSuchMethodException,
      InstantiationException, IllegalAccessException, InvocationTargetException
  {
    final long id = 999L;
    final String name = "ProxiedPojo";
    JsonObjectPojo jsonObjectPojo =
      JsonObjectPojoProxyFactory.newJsonObjectPojo(new JsonObjectPojoImpl(null, null, null), Long.valueOf(id), name,
          addressList);

    System.out.println("\nReflectionJsonStathamTest.testProxiedJsonObjectPojo()");
    final String expected =
      "{\"id\":" + id + ",\"name\":\"" + name + "\",\"addresses\":" + getAddressArrayString() + "}";
    System.out.println("expected:\n" + expected);
    System.out.println("actual: ");
    final String result = reflectionJavaToJsonConverter.convertIntoJson(jsonObjectPojo);
    System.out.println(result);
    assertThat(result, is(equalTo(expected)));
  }

  @Test
  public void testProxiedJsonObjectPojoHavingProxiedJsonObjectPojo() throws IllegalArgumentException,
      NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException
  {
    final long primaryKey = 999L;
    final String name = "ProxiedPojo";
    final long primaryKey2 = 555L;
    final String name2 = "ProxiedParent";
    final long primaryKey3 = 333L;
    final String name3 = "Not proxied";
    NestedJsonObjectWithValueAccessor nestedJsonObjectWithValueAccessor =
      JsonObjectPojoProxyFactory.newNestedJsonObjectWithValueAccessor(new NestedJsonObjectWithValueAccessor(null, null,
          null), Long.valueOf(primaryKey), name, JsonObjectPojoProxyFactory.newNestedJsonObjectWithValueAccessor(
          new NestedJsonObjectWithValueAccessor(null, null, null), Long.valueOf(primaryKey2), name2,
          new NestedJsonObjectWithValueAccessor(Long.valueOf(primaryKey3), name3, null)));

    System.out.println("\nReflectionJsonStathamTest.testProxiedJsonObjectPojoHavingProxiedJsonObjectPojo()");
    final String expected =
      "{\"id\":" + primaryKey + ",\"name\":\"" + name + "\",\"parent\":{\"id\":" + primaryKey2 + ",\"name\":\"" + name2
          + "\",\"parent\":{\"id\":" + primaryKey3 + ",\"name\":\"" + name3 + "\",\"parent\":null}}}";
    System.out.println("expected:\n" + expected);
    System.out.println("actual: ");
    final String result = reflectionJavaToJsonConverter.convertIntoJson(nestedJsonObjectWithValueAccessor);
    System.out.println(result);
    assertThat(result, is(equalTo(expected)));
  }

  @Test
  public void testJsonObjectContainingEnums() throws IllegalArgumentException, JsonStathamException,
      IllegalAccessException
  {
    System.out.println("\nReflectionJsonStathamTest.testJsonObjectContainingEnums()");
    String expected =
      "{\"name\":\"" + "Kevin" + "\",\"number\":" + 1 + ",\"passed\":" + true + ",\"role\":\"" + "SYSTEM_ADMIN"
          + "\",\"access\":[]}";
    System.out.println("expected:\n" + expected);
    System.out.println("actual: ");
    String result =
      reflectionJavaToJsonConverter.convertIntoJson(new JsonObjectContainingEnums("Kevin", 1, true, Role.SYSTEM_ADMIN));
    System.out.println(result);
    assertThat(result, is(equalTo(expected)));

    expected =
      "{\"name\":\"" + "Kevin" + "\",\"number\":" + 1 + ",\"passed\":" + true + ",\"role\":\"" + "MEMBER"
          + "\",\"access\":[\"BLOG\",\"EMAIL\"]}";
    System.out.println("expected:\n" + expected);
    System.out.println("actual: ");
    result =
      reflectionJavaToJsonConverter.convertIntoJson(new JsonObjectContainingEnums("Kevin", 1, true, Role.MEMBER,
          Access.BLOG, Access.EMAIL));
    System.out.println(result);
    assertThat(result, is(equalTo(expected)));

    expected =
      "{\"name\":\"" + "Kevin" + "\",\"number\":" + 1 + ",\"passed\":" + true + ",\"role\":\"" + "MEMBER"
          + "\",\"access\":[\"BLOG\",\"WIKI\",\"EMAIL\",\"TWITTER\"]}";
    System.out.println("expected:\n" + expected);
    System.out.println("actual: ");
    result =
      reflectionJavaToJsonConverter.convertIntoJson(new JsonObjectContainingEnums("Kevin", 1, true, Role.MEMBER,
          Access.BLOG, Access.WIKI, Access.EMAIL, Access.TWITTER));
    System.out.println(result);
    assertThat(result, is(equalTo(expected)));
  }

  @Test
  public void testWithMultipleSelectionItem() throws IllegalArgumentException, JsonStathamException,
      IllegalAccessException
  {
    System.out.println("\nReflectionJavaToJsonConverterTest.testWithMultipleSelectionItem()");

    /* given */

    final String expected =
      "{\"name\":\"Global Warming\",\"instructions\":\"In your opinion, global warming...\",\"options\":[{\"code\":\"A\",\"text\":\"is just a fad.\"},{\"code\":\"B\",\"text\":\"already started to affect our lives.\"},{\"code\":\"C\",\"text\":\"will not have any impact on our lives in the next 10 years.\"},{\"code\":\"D\",\"text\":\"is really a problem for the next generation.\"},{\"code\":\"E\",\"text\":\"will not have any effect for at least 100 years.\"}]}";
    System.out.println("expected:\n" + expected);
    final ItemDefinition itemDefinition =
      new MultipleSelectionItem("Global Warming", "In your opinion, global warming...", Arrays.asList(new Option("A",
          "is just a fad."), new Option("B", "already started to affect our lives."), new Option("C",
          "will not have any impact on our lives in the next 10 years."), new Option("D",
          "is really a problem for the next generation."), new Option("E",
          "will not have any effect for at least 100 years.")));

    /* when */
    System.out.println("actual: ");
    String result = reflectionJavaToJsonConverter.convertIntoJson(itemDefinition);
    System.out.println(result);

    /* then */
    assertThat(result, is(equalTo(expected)));
  }
}

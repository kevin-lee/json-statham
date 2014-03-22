/**
 * This project is licensed under the Apache License, Version 2.0
 * if the following condition is met:
 * (otherwise it cannot be used by anyone but the author, Kevin, only)
 *
 * The original JSON Statham project is owned by Lee, Seong Hyun (Kevin).
 *
 * -What does it mean to you?
 * Nothing, unless you want to take the ownership of
 * "the original project" (not yours or forked & modified one).
 * You are free to use it for both non-commercial and commercial projects
 * and free to modify it as the Apache License allows.
 *
 * -So why is this condition necessary?
 * It is only to protect the original project (See the case of Java).
 *
 *
 * Copyright 2009 Lee, Seong Hyun (Kevin)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.elixirian.jsonstatham.core;

import static org.assertj.core.api.Assertions.*;
import static org.elixirian.kommonlee.util.MessageFormatter.*;
import static org.elixirian.kommonlee.util.Objects.*;
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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.elixirian.jsonstatham.annotation.Json;
import org.elixirian.jsonstatham.annotation.JsonField;
import org.elixirian.jsonstatham.core.convertible.AbstractJsonObject;
import org.elixirian.jsonstatham.core.convertible.ImmutableJsonNameValuePair;
import org.elixirian.jsonstatham.core.convertible.JsonArray;
import org.elixirian.jsonstatham.core.convertible.JsonArrayCreator;
import org.elixirian.jsonstatham.core.convertible.JsonArrayWithOrderedJsonObject;
import org.elixirian.jsonstatham.core.convertible.JsonConvertible;
import org.elixirian.jsonstatham.core.convertible.JsonNameValuePair;
import org.elixirian.jsonstatham.core.convertible.JsonObject;
import org.elixirian.jsonstatham.core.convertible.JsonObjectCreator;
import org.elixirian.jsonstatham.core.convertible.OrderedJsonObject;
import org.elixirian.jsonstatham.core.reflect.ReflectionJsonStathams;
import org.elixirian.jsonstatham.core.reflect.java2json.KnownDataStructureTypeProcessorDecider;
import org.elixirian.jsonstatham.core.reflect.java2json.KnownObjectReferenceTypeProcessorDecider;
import org.elixirian.jsonstatham.core.reflect.java2json.OneProcessorForKnownTypeDecider;
import org.elixirian.jsonstatham.core.reflect.java2json.ReflectionJavaToJsonConverter;
import org.elixirian.jsonstatham.core.reflect.json2java.DefaultJsonToJavaConfig;
import org.elixirian.jsonstatham.core.reflect.json2java.ReflectionJsonToJavaConverter;
import org.elixirian.jsonstatham.exception.JsonStathamException;
import org.elixirian.jsonstatham.json.Address;
import org.elixirian.jsonstatham.json.ComplexJsonObjectWithValueAccessor;
import org.elixirian.jsonstatham.json.ComplexJsonObjectWithValueAccessorWithoutItsName;
import org.elixirian.jsonstatham.json.JsonObjectContainingCollection;
import org.elixirian.jsonstatham.json.JsonObjectContainingEnums;
import org.elixirian.jsonstatham.json.JsonObjectContainingEnums.Access;
import org.elixirian.jsonstatham.json.JsonObjectContainingEnums.Role;
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
import org.elixirian.jsonstatham.json.json2java.JsonObjectHavingNestedGenericTypes;
import org.elixirian.jsonstatham.json.json2java.JsonPojoHavingMap;
import org.elixirian.jsonstatham.json.json2java.ObjectHavingJsonObjectAndJsonArray;
import org.elixirian.jsonstatham.test.ItemConfig;
import org.elixirian.jsonstatham.test.ItemDefinition;
import org.elixirian.jsonstatham.test.MultipleSelectionItem;
import org.elixirian.jsonstatham.test.Option;
import org.elixirian.kommonlee.reflect.TypeHolder;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 * <pre>
 *     ___  _____                                _____
 *    /   \/    /_________  ___ ____ __ ______  /    /   ______  ______
 *   /        / /  ___ \  \/  //___// //     / /    /   /  ___ \/  ___ \
 *  /        \ /  _____/\    //   //   __   / /    /___/  _____/  _____/
 * /____/\____\\_____/   \__//___//___/ /__/ /________/\_____/ \_____/
 * </pre>
 * 
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2009-11-21)
 * @version 0.0.2 (2010-03-06) more test cases including the one testing proxy object created by javassist are added.
 * @version 0.0.3 (2010-05-10) test case for testing enum type fields is added.
 */
public class JsonStathamInActionTest
{
  private static final List<String> streetList = Arrays.asList("ABC Street", "90/120 Swanston St");
  private static final List<String> suburbList = Arrays.asList("", "Test Suburb");
  private static final List<String> cityList = Arrays.asList("Sydney", "Melbourne");
  private static final List<String> stateList = Arrays.asList("NSW", "VIC");
  private static final List<String> postcodeList = Arrays.asList("2000", "3000");
  private static final String[] SOME_STRING_VALUE_ARRAY = { "111", "222", "aaa", "bbb", "ccc" };

  private static final Answer<JsonObject> ANSWER_FOR_NEW_JSON_OBJECT_CONVERTIBLE = new Answer<JsonObject>() {
    @Override
    public JsonObject answer(@SuppressWarnings("unused") final InvocationOnMock invocation) throws Throwable
    {
      return OrderedJsonObject.newJsonObject(new LinkedHashMap<String, Object>());
    }
  };

  private static final Answer<JsonObject> ANSWER_FOR_NEW_JSON_OBJECT_CONVERTIBLE_WITH_JSON_STRING =
    new Answer<JsonObject>() {
      @Override
      public JsonObject answer(final InvocationOnMock invocation) throws Throwable
      {
        try
        {
          return OrderedJsonObject.newJsonObject((String) invocation.getArguments()[0]);
        }
        catch (final Exception e)
        {
          throw new JsonStathamException(e);
        }
      }

    };

  private static final Answer<JsonObject> ANSWER_FOR_NULL_JSON_OBJECT_CONVERTIBLE = new Answer<JsonObject>() {

    @Override
    public JsonObject answer(@SuppressWarnings("unused") final InvocationOnMock invocation) throws Throwable
    {
      return new JsonObject() {
        @Override
        public String[] getNames()
        {
          throw new JsonStathamException("The getNames method in NullJsonObjectConvertible cannot be used.");
        }

        @Override
        public int fieldLength()
        {
          return 0;
        }

        /* @formatter:off */
          @Override
          public boolean containsName(@SuppressWarnings("unused") final String name) { return false; }
          /* @formatter:on */

        @Override
        public <T> T get(@SuppressWarnings("unused") final String name)
        {
          throw new JsonStathamException("The get method in NullJsonObjectConvertible cannot be used.");
        }

        @Override
        public Object getActualObject()
        {
          return AbstractJsonObject.NULL_JSON_OBJECT;
        }

        @Override
        public <T> JsonObject put(@SuppressWarnings("unused") final String name,
            @SuppressWarnings("unused") final T value) throws JsonStathamException
        {
          throw new JsonStathamException("The put method in NullJsonObjectConvertible cannot used.");
        }

        @Override
        public boolean isNull()
        {
          return true;
        }

        @Override
        public String toString()
        {
          return AbstractJsonObject.NULL_JSON_OBJECT.toString();
        }

        @Override
        public Map<String, Object> copyToMap()
        {
          return AbstractJsonObject.NULL_JSON_OBJECT.copyToMap();
        }

        @Override
        public boolean isEmpty()
        {
          return true;
        }

        @Override
        public boolean isNotEmpty()
        {
          return false;
        }

        @Override
        public boolean isJsonObject()
        {
          return true;
        }

        @Override
        public boolean isJsonArray()
        {
          return false;
        }

        @Override
        public Class<?> getActualType()
        {
          return getActualObject().getClass();
        }
      };
    }
  };

  private static final Answer<JsonArray> ANSWER_FOR_JSON_ARRAY_CONVERTIBLE = new Answer<JsonArray>() {

    @Override
    public JsonArray answer(@SuppressWarnings("unused") final InvocationOnMock invocation) throws Throwable
    {
      return JsonArrayWithOrderedJsonObject.newJsonArray();
    }
  };

  private static final Answer<JsonArray> ANSWER_FOR_JSON_ARRAY_CONVERTIBLE_WITH_JSON_STRING = new Answer<JsonArray>() {
    @Override
    public JsonArray answer(final InvocationOnMock invocation) throws Throwable
    {
      return JsonArrayWithOrderedJsonObject.newJsonArray((String) invocation.getArguments()[0]);
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
    final JsonObjectCreator jsonObjectCreator = mock(JsonObjectCreator.class);
    when(jsonObjectCreator.newJsonObjectConvertible()).thenAnswer(ANSWER_FOR_NEW_JSON_OBJECT_CONVERTIBLE);
    when(jsonObjectCreator.newJsonObjectConvertible(anyString())).thenAnswer(
        ANSWER_FOR_NEW_JSON_OBJECT_CONVERTIBLE_WITH_JSON_STRING);
    when(jsonObjectCreator.nullJsonObjectConvertible()).thenAnswer(ANSWER_FOR_NULL_JSON_OBJECT_CONVERTIBLE);

    final JsonArrayCreator jsonArrayCreator = mock(JsonArrayCreator.class);
    when(jsonArrayCreator.newJsonArrayConvertible()).thenAnswer(ANSWER_FOR_JSON_ARRAY_CONVERTIBLE);
    when(jsonArrayCreator.newJsonArrayConvertible(anyString())).thenAnswer(
        ANSWER_FOR_JSON_ARRAY_CONVERTIBLE_WITH_JSON_STRING);

    final ReflectionJavaToJsonConverter javaToJsonConverter =
      new ReflectionJavaToJsonConverter(jsonObjectCreator, jsonArrayCreator,
          new KnownDataStructureTypeProcessorDecider(), new KnownObjectReferenceTypeProcessorDecider(),
          new OneProcessorForKnownTypeDecider());

    final ReflectionJsonToJavaConverter jsonToJavaConverter =
      new ReflectionJsonToJavaConverter(DefaultJsonToJavaConfig.builder(jsonObjectCreator, jsonArrayCreator)
          .build());

    jsonStatham = new JsonStathamInAction(javaToJsonConverter, jsonToJavaConverter);
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

    /* test convertIntoJson */
    final String result1_1 = jsonStatham.convertIntoJson(null);
    System.out.println(result1_1);
    assertThat(result1_1).isEqualTo(expected.toString());
  }

  @Test
  public void testJsonHavingNullValue()
  {
    System.out.println("\nJsonStathamInActionTest.testJsonHavingNullValue()");
    @SuppressWarnings("hiding")
    @Json
    class TestPojo
    {
      @JsonField
      private Object object = null;
    }
    final String expected2 = "{\"object\":null}";
    System.out.println("expected:\n" + expected2);
    System.out.println("actual: ");
    final String result2 = jsonStatham.convertIntoJson(new TestPojo());
    System.out.println(result2);
    assertEquals(expected2.toString(), result2);

  }

  private String getAddressArrayString()
  {
    final StringBuilder stringBuilder = new StringBuilder("[");
    for (final Address address : addressList)
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
    System.out.println("\nJsonStathamInActionTest.testArray()");

    final String expectedIntArray = "[1,2,3,4,5,8,23,56]";
    System.out.println("\nexpected:\n" + expectedIntArray);
    System.out.println("actual: ");
    final String resultIntArray = jsonStatham.convertIntoJson(new int[] { 1, 2, 3, 4, 5, 8, 23, 56 });
    System.out.println(resultIntArray);
    assertThat(resultIntArray).isEqualTo(expectedIntArray);

    final String expectedDoubleArray = "[1.2,2.6,3.3,4.8,5.234,8.567,23.48754,56.0547]";
    System.out.println("\nexpected:\n" + expectedDoubleArray);
    System.out.println("actual: ");
    final String resultDoubleArray =
      jsonStatham.convertIntoJson(new double[] { 1.2, 2.6, 3.3, 4.8, 5.234, 8.567, 23.48754, 56.0547 });
    System.out.println(resultDoubleArray);
    assertThat(resultDoubleArray).isEqualTo(expectedDoubleArray);

    final String expectedBooleanArray = "[true,false,false,true,false,true,false,true,true]";
    System.out.println("\nexpected:\n" + expectedBooleanArray);
    System.out.println("actual: ");
    final String resultBooleanArray =
      jsonStatham.convertIntoJson(new boolean[] { true, false, false, true, false, true, false, true, true });
    System.out.println(resultBooleanArray);
    assertThat(resultBooleanArray).isEqualTo(expectedBooleanArray);
  }

  @Test
  public void testArrayHavingPojo()
  {
    System.out.println("\nJsonStathamInActionTest.testArrayHavingPojo()");
    final String expected = getAddressArrayString();
    System.out.println("expected:\n" + expected);
    System.out.println("actual: ");
    final String result = jsonStatham.convertIntoJson(addressList.toArray(new Address[addressList.size()]));
    System.out.println(result);
    assertEquals(expected, result);
  }

  /**
   * Test method for {@link org.elixirian.jsonstatham.core.JsonStathamInAction#convertIntoJson(java.lang.Object)} with
   * List as the parameter object.
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
    for (final Entry<String, Address> entry : addressMap.entrySet())
    {
      final Address address = entry.getValue();
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
    final Map<String, Object> nestedMap = new HashMap<String, Object>();
    nestedMap.put("test1", addressMap);
    nestedMap.put("test2", addressMap);
    System.out.println("actual: ");
    final String result = jsonStatham.convertIntoJson(nestedMap);
    System.out.println(result);
    assertEquals(expected, result);
  }

  /**
   * Test method for {@link org.elixirian.jsonstatham.core.JsonStathamInAction#convertIntoJson(java.lang.Object)}.
   */
  @Test
  public void testAddress()
  {
    System.out.println("\nReflectionJsonStathamTest.testAddress()");

    final String expected =
      "{\"street\":\"" + streetList.get(0) + "\",\"suburb\":\"" + suburbList.get(0) + "\",\"city\":\""
          + cityList.get(0) + "\",\"state\":\"" + stateList.get(0) + "\",\"postcode\":\"" + postcodeList.get(0) + "\"}";
    System.out.println("expected:\n" + expected);
    System.out.println("actual: ");
    final String result = jsonStatham.convertIntoJson(address);
    System.out.println(result);
    assertEquals(expected, result);
  }

  /**
   * Test method for {@link org.elixirian.jsonstatham.core.JsonStathamInAction#convertIntoJson(java.lang.Object)}.
   */
  @Test
  public void testNestedJsonObject()
  {
    final long id = 1;
    final String name = "jsonObject";
    final NestedJsonObject jsonObject = new NestedJsonObject();
    jsonObject.setPrimaryKey(Long.valueOf(id));
    jsonObject.setName(name);
    jsonObject.setAddress(address);
    jsonObject.setIntNumber(Integer.MAX_VALUE);
    jsonObject.setDoubleNumber(Double.MAX_VALUE);

    final String expected =
      "{\"id\":" + id + ",\"name\":\"" + name + "\",\"address\":{\"street\":\"" + streetList.get(0)
          + "\",\"suburb\":\"" + suburbList.get(0) + "\",\"city\":\"" + cityList.get(0) + "\",\"state\":\""
          + stateList.get(0) + "\",\"postcode\":\"" + postcodeList.get(0)
          + "\"},\"intNumber\":2147483647,\"doubleNumber\":1.7976931348623157E308}";
    System.out.println("\nReflectionJsonStathamTest.testNestedJsonObject()");
    System.out.println("expected:\n" + expected);
    System.out.println("actual: ");
    final String result = jsonStatham.convertIntoJson(jsonObject);
    System.out.println(result);
    assertThat(result).isEqualTo(expected);

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
    final String result2 = jsonStatham.convertIntoJson(jsonObject);
    System.out.println(result2);
    assertThat(result2).isEqualTo(expected2);

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
    final String result3 = jsonStatham.convertIntoJson(jsonObject);
    System.out.println(result3);
    assertThat(result3).isEqualTo(expected3);

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
    final String result4 = jsonStatham.convertIntoJson(jsonObject);
    System.out.println(result4);
    assertThat(result4).isEqualTo(expected4);

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
    final String result5 = jsonStatham.convertIntoJson(jsonObject);
    System.out.println(result5);
    assertThat(result5).isEqualTo(expected5);
  }

  @Test(expected = JsonStathamException.class)
  public void testJsonObjectWithDuplicateKeys() throws IOException
  {
    System.out.println("\nReflectionJsonStathamTest.testJsonObjectWithDuplicateKeys()");
    final JsonObjectWithDuplicateKeys jsonObjectWithDuplicateKeys = new JsonObjectWithDuplicateKeys();
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
    catch (final JsonStathamException e)
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
    assertThat(result).isEqualTo(expected);
  }

  @Test
  public void testComplexJsonObjectWithMethodUse()
  {
    final ComplexJsonObjectWithValueAccessor jsonObject = new ComplexJsonObjectWithValueAccessor();
    jsonObject.setPrimaryKey(Long.valueOf(1));
    jsonObject.setName("Kevin");
    jsonObject.setAddress(address);
    final Date date = new Date();
    final Calendar calendar = Calendar.getInstance();
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
    final String result = jsonStatham.convertIntoJson(jsonObject);
    System.out.println(result);
    assertThat(result).isEqualTo(expected);
  }

  @Test
  public void testComplexJsonObjectWithValueAccessorWithoutItsName()
  {
    final ComplexJsonObjectWithValueAccessorWithoutItsName jsonObject =
      new ComplexJsonObjectWithValueAccessorWithoutItsName();
    jsonObject.setPrimaryKey(Long.valueOf(1));
    jsonObject.setName("Kevin");
    jsonObject.setRegistered(true);
    jsonObject.setEnabled(false);
    jsonObject.setAddress(address);
    final Date date = new Date();
    final Calendar calendar = Calendar.getInstance();
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
    final String result = jsonStatham.convertIntoJson(jsonObject);
    System.out.println(result);
    assertThat(result).isEqualTo(expected);
  }

  private String getExpectedJsonArray(final String name, final String value, final String setName)
  {
    final StringBuilder stringBuilder = new StringBuilder("{\"").append(name)
        .append("\":\"")
        .append(value)
        .append("\",\"")
        .append(setName)
        .append("\":[");
    for (final String element : SOME_STRING_VALUE_ARRAY)
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

  private <V extends Object, T extends Collection<V>> T initialiseCollectionWithStringValues(final T t,
      final V... values)
  {
    for (final V value : values)
    {
      t.add(value);
    }
    return t;
  }

  @Test
  public void testJsonObjectContainingCollection()
  {
    final String nameValue = "testJsonWithCollection";
    final Collection<String> collection =
      initialiseCollectionWithStringValues(new ArrayList<String>(), SOME_STRING_VALUE_ARRAY);

    final JsonObjectContainingCollection jsonObjectContainingCollection =
      new JsonObjectContainingCollection(nameValue, collection);
    final String expected = getExpectedJsonArray("name", nameValue, "valueCollection");
    System.out.println("\nReflectionJsonStathamTest.testJsonObjectContainingCollection()");
    System.out.println("expected:\n" + expected);
    System.out.println("actual: ");
    final String result = jsonStatham.convertIntoJson(jsonObjectContainingCollection);
    System.out.println(result);
    assertThat(result).isEqualTo(expected);
  }

  @Test
  public void testJsonObjectContainingList()
  {
    final String nameValue = "testJsonWithList";
    final List<String> list = initialiseCollectionWithStringValues(new ArrayList<String>(), SOME_STRING_VALUE_ARRAY);

    final JsonObjectContainingList jsonObjectContainingList = new JsonObjectContainingList(nameValue, list);
    final String expected = getExpectedJsonArray("name", nameValue, "valueList");
    System.out.println("\nReflectionJsonStathamTest.testJsonObjectContainingList()");
    System.out.println("expected:\n" + expected);
    System.out.println("actual: ");
    final String result = jsonStatham.convertIntoJson(jsonObjectContainingList);
    System.out.println(result);
    assertThat(result).isEqualTo(expected);
  }

  @Test
  public void testJsonObjectContainingSet()
  {
    final String nameValue = "testJsonWithSet";
    final Set<String> set = initialiseCollectionWithStringValues(new LinkedHashSet<String>(), SOME_STRING_VALUE_ARRAY);

    final JsonObjectContainingSet jsonObjectContainingSet = new JsonObjectContainingSet(nameValue, set);
    final String expected = getExpectedJsonArray("name", nameValue, "valueSet");
    System.out.println("\nReflectionJsonStathamTest.testJsonObjectContainingSet()");
    System.out.println("expected:\n" + expected);
    System.out.println("actual: ");
    final String result = jsonStatham.convertIntoJson(jsonObjectContainingSet);
    System.out.println(result);
    assertThat(result).isEqualTo(expected);
  }

  @Test
  public void testJsonObjectContainingMapEntrySetSet()
  {
    final String nameValue = "testJsonObjectContainingMapEntrySetSet";

    final JsonObjectContainingMapEntrySet jsonObjectContainingSet =
      new JsonObjectContainingMapEntrySet(nameValue, addressMap.entrySet());

    final StringBuilder stringBuilder =
      new StringBuilder("{\"name\":\"testJsonObjectContainingMapEntrySetSet\",\"valueMapEntrySet\":[");
    for (final Entry<String, Address> entry : addressMap.entrySet())
    {
      final Address address = entry.getValue();
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
    assertThat(result).isEqualTo(expected);
  }

  @Test
  public void testJsonObjectContainingIterator()
  {
    final String nameValue = "testJsonObjectContainingIterator";
    final Collection<String> collection =
      initialiseCollectionWithStringValues(new ArrayList<String>(), SOME_STRING_VALUE_ARRAY);

    final JsonObjectContainingIterator jsonObjectContainingCollection =
      new JsonObjectContainingIterator(nameValue, collection.iterator());
    final String expected = getExpectedJsonArray("name", nameValue, "valueIterator");
    System.out.println("\nReflectionJsonStathamTest.testJsonObjectContainingIterator()");
    System.out.println("expected:\n" + expected);
    System.out.println("actual: ");
    final String result = jsonStatham.convertIntoJson(jsonObjectContainingCollection);
    System.out.println(result);
    assertThat(result).isEqualTo(expected);
  }

  @Test
  public void testJsonObjectContainingIterable()
  {
    final String nameValue = "testJsonObjectContainingIterable";
    final Iterable<String> iterable = new Iterable<String>() {
      @Override
      public Iterator<String> iterator()
      {
        return initialiseCollectionWithStringValues(new ArrayList<String>(), SOME_STRING_VALUE_ARRAY).iterator();
      }
    };

    final JsonObjectContainingIterable jsonObjectContainingCollection =
      new JsonObjectContainingIterable(nameValue, iterable);
    final String expected = getExpectedJsonArray("name", nameValue, "valueIterable");
    System.out.println("\nReflectionJsonStathamTest.testJsonObjectContainingIterator()");
    System.out.println("expected:\n" + expected);
    System.out.println("actual: ");
    final String result = jsonStatham.convertIntoJson(jsonObjectContainingCollection);
    System.out.println(result);
    assertThat(result).isEqualTo(expected);
  }

  @Test
  public void testJsonObjectWithInterfaceInheritance()
  {
    final String name = "Kevin Lee";
    final int number = 99;
    final String email = "kevinlee@test.test";
    final SomeInterface jsonObject = new SomeImplementingClass(name, number, email);
    System.out.println("\nReflectionJsonStathamTest.testJsonObjectWithInterfaceInheritance()");
    final String expected = "{\"name\":\"" + name + "\",\"number\":" + number + ",\"email\":\"" + email + "\"}";
    System.out.println("expected:\n" + expected);
    System.out.println("actual: ");
    final String result = jsonStatham.convertIntoJson(jsonObject);
    System.out.println(result);
    assertThat(result).isEqualTo(expected);
  }

  @Test
  public void testJsonObjectWithImplementationInheritance()
  {
    final String name = "Kevin";
    final int number = 5;
    final String email = "kevin@test.test";
    final SubClass jsonObject = new SubClass(name, number, email);
    System.out.println("\nReflectionJsonStathamTest.testJsonObjectWithImplementationInheritance()");
    final String expected = "{\"name\":\"" + name + "\",\"number\":" + number + ",\"email\":\"" + email + "\"}";
    System.out.println("expected:\n" + expected);
    System.out.println("actual: ");
    final String result = jsonStatham.convertIntoJson(jsonObject);
    System.out.println(result);
    assertThat(result).isEqualTo(expected);
  }

  @Test
  public void testJsonObjectWithDoubleImplementationInheritanceAndNoOwnFieldsInSecondSubClass()
  {
    final String name = "Kevin";
    final int number = 11;
    final String email = "kevin@test.blahblah";
    final SubClass jsonObject = new SecondSubClassWithoutOwnFields(name, number, email);
    System.out.println("\nReflectionJsonStathamTest.testJsonObjectWithDoubleImplementationInheritanceAndNoOwnFieldsInSecondSubClass()");
    final String expected = "{\"name\":\"" + name + "\",\"number\":" + number + ",\"email\":\"" + email + "\"}";
    System.out.println("expected:\n" + expected);
    System.out.println("actual: ");
    final String result = jsonStatham.convertIntoJson(jsonObject);
    System.out.println(result);
    assertThat(result).isEqualTo(expected);
  }

  @Test
  public void testJsonObjectWithDoubleImplementationInheritanceAndOwnFieldsInSecondSubClass()
  {
    final String name = "Mr. Lee";
    final int number = 999;
    final String email = "kevin@another.email";
    final String comment = "Blah blah";
    final SecondSubClassWithOwnFields jsonObject =
      new SecondSubClassWithOwnFields(name, number, email, address, comment);
    System.out.println("\nReflectionJsonStathamTest.testJsonObjectWithDoubleImplementationInheritanceAndOwnFieldsInSecondSubClass()");
    final String expected =
      "{\"name\":\"" + name + "\",\"number\":" + number + ",\"email\":\"" + email + "\"," + "\"address\":"
          + "{\"street\":\"" + address.getStreet() + "\",\"suburb\":\"" + address.getSuburb() + "\",\"city\":\""
          + address.getCity() + "\",\"state\":\"" + address.getState() + "\",\"postcode\":\"" + address.getPostcode()
          + "\"},\"comment\":\"" + comment + "\"}";
    System.out.println("expected:\n" + expected);
    System.out.println("actual: ");
    final String result = jsonStatham.convertIntoJson(jsonObject);
    System.out.println(result);
    assertThat(result).isEqualTo(expected);
  }

  @Test
  public void testJsonObjectWithImplementationInheritanceWithNoJsonObjectSuperClass()
  {
    final String name = "Kevin";
    final int number = 5;
    final String email = "kevin@test.test";
    final SubClassWithNoJsonObjectSuperClass jsonObject = new SubClassWithNoJsonObjectSuperClass(name, number, email);
    System.out.println("\nReflectionJsonStathamTest.testJsonObjectWithImplementationInheritanceWithNoJsonObjectSuperClass()");
    final String expected = "{\"email\":\"" + email + "\"}";
    System.out.println("expected:\n" + expected);
    System.out.println("actual: ");
    final String result = jsonStatham.convertIntoJson(jsonObject);
    System.out.println(result);
    assertThat(result).isEqualTo(expected);
  }

  @Test
  public void testJsonObjectWithImplementationInheritanceWithValueAccessor()
  {
    final String name = "Kevin";
    final int number = 5;
    final String email = "kevin@test.test";
    final SubClassWithValueAccessor jsonObject = new SubClassWithValueAccessor(name, number, email);
    System.out.println("\nReflectionJsonStathamTest.testJsonObjectWithImplementationInheritanceWithValueAccessor()");
    final String expected =
      "{\"name\":\"My name is " + name + "\",\"number\":\"The number is " + number
          + "\",\"email\":\"My email address is " + email + "\"}";
    System.out.println("expected:\n" + expected);
    System.out.println("actual: ");
    final String result = jsonStatham.convertIntoJson(jsonObject);
    System.out.println(result);
    assertThat(result).isEqualTo(expected);
  }

  @Test
  public void testJsonObjectWithImplementationInheritanceWithValueAccessorWithoutItsName()
  {
    final String name = "Kevin";
    final int number = 5;
    final String email = "kevin@test.test";
    final SubClassWithValueAccessorWithoutItsName jsonObject =
      new SubClassWithValueAccessorWithoutItsName(name, number, email);
    System.out.println("\nReflectionJsonStathamTest.testJsonObjectWithImplementationInheritanceWithValueAccessorWithoutItsName()");
    final String expected =
      "{\"name\":\"My name is " + name + "\",\"number\":\"The number is " + number
          + "\",\"email\":\"My email address is " + email + "\"}";
    System.out.println("expected:\n" + expected);
    System.out.println("actual: ");
    final String result = jsonStatham.convertIntoJson(jsonObject);
    System.out.println(result);
    assertThat(result).isEqualTo(expected);
  }

  @Test
  public void testJsonObjectWithImplementationInheritanceWithValueAccessorWithAbstractMethod()
  {
    final String name = "Kevin";
    final int number = 5;
    final String email = "kevin@test.test";
    final SubClassWithValueAccessorWithAbstractMethod jsonObject =
      new SubClassWithValueAccessorWithAbstractMethod(name, number, email);
    System.out.println("\nReflectionJsonStathamTest.testJsonObjectWithImplementationInheritanceWithValueAccessorWithAbstractMethod()");
    final String expected =
      "{\"name\":\"My name is nobody.\",\"number\":\"The number is 100.\",\"email\":\"My email address is " + email
          + "\"}";
    System.out.println("expected:\n" + expected);
    System.out.println("actual: ");
    final String result = jsonStatham.convertIntoJson(jsonObject);
    System.out.println(result);
    assertThat(result).isEqualTo(expected);
  }

  @Test
  public void testJsonObjectWithImplementationInheritanceWithValueAccessorWithOverriddenMethod()
  {
    final String name = "Kevin";
    final int number = 5;
    final String email = "kevin@test.test";
    final SubClassWithValueAccessorWithOverriddenMethod jsonObject =
      new SubClassWithValueAccessorWithOverriddenMethod(name, number, email);
    System.out.println("\nReflectionJsonStathamTest.testJsonObjectWithImplementationInheritanceWithValueAccessorWithOverriddenMethod()");
    final String expected =
      "{\"name\":\"My name is " + name + "\",\"number\":\"The number is " + number
          + "\",\"email\":\"My email address is " + email + "\"}";
    System.out.println("expected:\n" + expected);
    System.out.println("actual: ");
    final String result = jsonStatham.convertIntoJson(jsonObject);
    System.out.println(result);
    assertThat(result).isEqualTo(expected);
  }

  @Test
  public void testProxiedJsonObjectPojo() throws IllegalArgumentException, NoSuchMethodException,
      InstantiationException, IllegalAccessException, InvocationTargetException
  {
    final long id = 999L;
    final String name = "ProxiedPojo";
    final JsonObjectPojo jsonObjectPojo =
      JsonObjectPojoProxyFactory.newJsonObjectPojo(new JsonObjectPojoImpl(null, null, null), Long.valueOf(id), name,
          addressList);

    System.out.println("\nReflectionJsonStathamTest.testProxiedJsonObjectPojo()");
    final String expected =
      "{\"id\":" + id + ",\"name\":\"" + name + "\",\"addresses\":" + getAddressArrayString() + "}";
    System.out.println("expected:\n" + expected);
    System.out.println("actual: ");
    final String result = jsonStatham.convertIntoJson(jsonObjectPojo);
    System.out.println(result);
    assertThat(result).isEqualTo(expected);
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
    final NestedJsonObjectWithValueAccessor nestedJsonObjectWithValueAccessor =
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
    final String result = jsonStatham.convertIntoJson(nestedJsonObjectWithValueAccessor);
    System.out.println(result);
    assertThat(result).isEqualTo(expected);
  }

  @Test
  public void testJsonObjectContainingEnums()
  {
    System.out.println("\nReflectionJsonStathamTest.testJsonObjectContainingEnums()");
    String expected =
      "{\"name\":\"" + "Kevin" + "\",\"number\":" + 1 + ",\"passed\":" + true + ",\"role\":\"" + "SYSTEM_ADMIN"
          + "\",\"access\":[]}";
    System.out.println("expected:\n" + expected);
    System.out.println("actual: ");
    String result = jsonStatham.convertIntoJson(new JsonObjectContainingEnums("Kevin", 1, true, Role.SYSTEM_ADMIN));
    System.out.println(result);
    assertThat(result).isEqualTo(expected);

    expected =
      "{\"name\":\"" + "Kevin" + "\",\"number\":" + 1 + ",\"passed\":" + true + ",\"role\":\"" + "MEMBER"
          + "\",\"access\":[\"BLOG\",\"EMAIL\"]}";
    System.out.println("expected:\n" + expected);
    System.out.println("actual: ");
    result =
      jsonStatham.convertIntoJson(new JsonObjectContainingEnums("Kevin", 1, true, Role.MEMBER, Access.BLOG,
          Access.EMAIL));
    System.out.println(result);
    assertThat(result).isEqualTo(expected);

    expected =
      "{\"name\":\"" + "Kevin" + "\",\"number\":" + 1 + ",\"passed\":" + true + ",\"role\":\"" + "MEMBER"
          + "\",\"access\":[\"BLOG\",\"WIKI\",\"EMAIL\",\"TWITTER\"]}";
    System.out.println("expected:\n" + expected);
    System.out.println("actual: ");
    result =
      jsonStatham.convertIntoJson(new JsonObjectContainingEnums("Kevin", 1, true, Role.MEMBER, Access.BLOG,
          Access.WIKI, Access.EMAIL, Access.TWITTER));
    System.out.println(result);
    assertThat(result).isEqualTo(expected);
  }

  // JSON2Java
  @Test(expected = JsonStathamException.class)
  public final void testConvertFromJsonWithIllegalJson()
  {
    System.out.println("\nJsonStathamInActionTest.testConvertFromJsonWithIllegalJson()");
    jsonStatham.convertFromJson(Object.class, "{\"some\",\"value\",\"This is not JSON\"}");
  }

  @Test(expected = JsonStathamException.class)
  public void testJson2JavaUnknownType()
  {
    System.out.println("\nJsonStathamInActionTest.testJson2JavaUnknownType()");
    class UnknownType
    {
    }
    jsonStatham.convertFromJson(UnknownType.class, "{}");
  }

  @Test
  public void testJson2JavaNull()
  {
    System.out.println("\nJsonStathamInActionTest.testJson2JavaNull()");
    final String json = "null";
    System.out.println("json:\n" + json);
    System.out.println("java: ");

    final Object result = jsonStatham.convertFromJson((Class<Object>) null, json);
    System.out.println(result);
    assertThat(result).isNull();
  }

  @Test(expected = JsonStathamException.class)
  public void testLocalJsonClass() throws Exception
  {
    System.out.println("\nJsonStathamInActionTest.testLocalJsonClass().TestPojo.testLocalJsonClass()");
    @SuppressWarnings("hiding")
    @Json
    class TestPojo
    {
      @JsonField
      private Object object = null;

      @Override
      public int hashCode()
      {
        return hash(object);
      }

      @Override
      public boolean equals(final Object testPojo)
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
        return equal(this.object, that.object);
      }
    }
    final String json = "{\"object\":null}";
    System.out.println("json:\n" + json);
    System.out.println("java: ");
    try
    {
      jsonStatham.convertFromJson(TestPojo.class, json);
    }
    catch (final Exception e)
    {
      System.out.println("exceptionMessage: " + e.getMessage());
      throw e;
    }
  }

  @Json
  private static class TestPojo
  {
    @JsonField
    private Object object = null;

    @Override
    public int hashCode()
    {
      return hash(object);
    }

    @Override
    public boolean equals(final Object testPojo)
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
      return equal(this.object, that.object);
    }

    @Override
    public String toString()
    {
      return toStringBuilder(this).add("object", object)
          .toString();
    }
  }

  @Test
  public void testJson2JavaJsonHavingNullValue()
  {
    System.out.println("\nJsonStathamInActionTest.testJson2JavaJsonHavingNullValue()");
    final String json = "{\"object\":null}";
    System.out.println("json:\n" + json);
    System.out.println("java: ");
    final TestPojo result = jsonStatham.convertFromJson(TestPojo.class, json);
    System.out.println(result);
    assertThat(result).isEqualTo(new TestPojo());
    assertTrue(new TestPojo().equals(result));
    assertEquals(new TestPojo().hashCode(), result.hashCode());

  }

  private String toString(final Object object)
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
  public void testJson2JavaArray()
  {
    System.out.println("\nJsonStathamInActionTest.testJson2JavaArray()");

    final String intArrayJson = "[1,2,3,4,5,8,23,56]";
    final int[] intArray = new int[] { 1, 2, 3, 4, 5, 8, 23, 56 };
    System.out.println("\njson:\n" + intArrayJson);
    System.out.println("java: ");
    final int[] resultIntArray = jsonStatham.convertFromJson(int[].class, intArrayJson);
    System.out.println(toString(resultIntArray));
    assertThat(resultIntArray).isEqualTo(intArray);

    final String doubleArrayJson = "[1.2,2.6,3.3,4.8,5.234,8.567,23.48754,56.0547]";
    final double[] doubleArray = new double[] { 1.2, 2.6, 3.3, 4.8, 5.234, 8.567, 23.48754, 56.0547 };
    System.out.println("\njson:\n" + doubleArrayJson);
    System.out.println("java: ");
    final double[] resultDoubleArray = jsonStatham.convertFromJson(double[].class, doubleArrayJson);
    System.out.println(toString(resultDoubleArray));
    assertThat(resultDoubleArray).isEqualTo(doubleArray);

    final String booleanArrayJson = "[true,false,false,true,false,true,false,true,true]";
    final boolean[] booleanArray = new boolean[] { true, false, false, true, false, true, false, true, true };
    System.out.println("\njson:\n" + booleanArrayJson);
    System.out.println("java: ");
    final boolean[] resultBooleanArray = jsonStatham.convertFromJson(boolean[].class, booleanArrayJson);
    System.out.println(toString(resultBooleanArray));
    assertThat(resultBooleanArray).isEqualTo(booleanArray);
  }

  @Test
  public void testJson2JavaArrayHavingPojo()
  {
    System.out.println("\nJsonStathamInActionTest.testJson2JavaArrayHavingPojo()");
    final String json = getAddressArrayString();
    System.out.println("json:\n" + json);
    System.out.println("java: ");
    final Address[] addresses = jsonStatham.convertFromJson(Address[].class, json);
    System.out.println(toString(addresses));
    assertThat(addresses).isEqualTo(addressList.toArray(new Address[addressList.size()]));
  }

  @Test
  public void testJson2JavaList()
  {
    System.out.println("\nJsonStathamInActionTest.testJson2JavaList()");
    final String json = getAddressArrayString();
    System.out.println("json:\n" + json);
    System.out.println("java: ");

    final List<Address> result = jsonStatham.convertFromJson(new TypeHolder<List<Address>>() {}, json);
    System.out.println(result);
    assertThat(result).isEqualTo(addressList);
  }

  @Test
  public void testJson2JavaMap()
  {
    System.out.println("\nJsonStathamInActionTest.testJson2JavaMap()");
    final String json = getAddressMapString();
    System.out.println("json:\n" + json);
    System.out.println("java: ");

    final Map<String, Address> result = jsonStatham.convertFromJson(new TypeHolder<Map<String, Address>>() {}, json);
    System.out.println(result);
    assertThat(result).isEqualTo(addressMap);
  }

  @Test
  public void testJson2JavaNestedMap()
  {
    System.out.println("\nJsonStathamInActionTest.testJson2JavaNestedMap()");
    final String json = "{\"test1\":" + getAddressMapString() + ",\"test2\":" + getAddressMapString() + "}";
    System.out.println("json: \n" + json);
    final Map<String, Map<String, Address>> nestedMap = new HashMap<String, Map<String, Address>>();
    nestedMap.put("test1", addressMap);
    nestedMap.put("test2", addressMap);
    System.out.println("java: ");

    final Map<String, Map<String, Address>> result =
      jsonStatham.convertFromJson(new TypeHolder<Map<String, Map<String, Address>>>() {}, json);
    System.out.println(result);
    assertThat(result).isEqualTo(nestedMap);
  }

  @Test
  public void testJson2JavaAddress()
  {
    System.out.println("\nJsonStathamInActionTest.testJson2JavaAddress()");

    final String json =
      "{\"street\":\"" + streetList.get(0) + "\",\"suburb\":\"" + suburbList.get(0) + "\",\"city\":\""
          + cityList.get(0) + "\",\"state\":\"" + stateList.get(0) + "\",\"postcode\":\"" + postcodeList.get(0) + "\"}";
    System.out.println("json:\n" + json);
    System.out.println("java: ");
    final Address result = jsonStatham.convertFromJson(Address.class, json);
    System.out.println(result);
    assertThat(result).isEqualTo(address);
  }

  @Test
  public void testJson2JavaNestedJsonObject()
  {
    System.out.println("\nJsonStathamInActionTest.testJson2JavaNestedJsonObject()");
    final long id = 1;
    final String name = "jsonObject";
    final NestedJsonObject jsonObject = new NestedJsonObject();
    jsonObject.setPrimaryKey(Long.valueOf(id));
    jsonObject.setName(name);
    jsonObject.setAddress(address);
    jsonObject.setIntNumber(Integer.MAX_VALUE);
    jsonObject.setDoubleNumber(Double.MAX_VALUE);

    final String json =
      "{\"id\":" + id + ",\"name\":\"" + name + "\",\"address\":{\"street\":\"" + streetList.get(0)
          + "\",\"suburb\":\"" + suburbList.get(0) + "\",\"city\":\"" + cityList.get(0) + "\",\"state\":\""
          + stateList.get(0) + "\",\"postcode\":\"" + postcodeList.get(0) + "\"},\"intNumber\":" + Integer.MAX_VALUE
          + ",\"doubleNumber\":" + Double.MAX_VALUE + "}";
    System.out.println("json:\n" + json);
    System.out.println("java: ");
    final NestedJsonObject result = jsonStatham.convertFromJson(NestedJsonObject.class, json);
    System.out.println(result);
    assertThat(result).isEqualTo(jsonObject);

    final Long id2 = Long.valueOf(id + 100);
    final String name2 = name + "4Testing";
    jsonObject.setPrimaryKey(id2);
    jsonObject.setName(name2);
    jsonObject.setAddress(new Address(streetList.get(1), suburbList.get(1), cityList.get(1), stateList.get(1),
        postcodeList.get(1)));
    jsonObject.setIntNumber(Integer.MIN_VALUE);
    jsonObject.setDoubleNumber(Double.MIN_VALUE);

    final String json2 =
      "{\"id\":" + id2 + ",\"name\":\"" + name2 + "\",\"address\":{\"street\":\"" + streetList.get(1)
          + "\",\"suburb\":\"" + suburbList.get(1) + "\",\"city\":\"" + cityList.get(1) + "\",\"state\":\""
          + stateList.get(1) + "\",\"postcode\":\"" + postcodeList.get(1) + "\"},\"intNumber\":" + Integer.MIN_VALUE
          + ",\"doubleNumber\":" + Double.MIN_VALUE + "}";
    System.out.println("\nReflectionJsonStathamTest.testNestedJsonObject()");
    System.out.println("json:\n" + json2);
    System.out.println("java: ");
    final NestedJsonObject result2 = jsonStatham.convertFromJson(NestedJsonObject.class, json2);
    System.out.println(result2);
    assertThat(result2).isEqualTo(jsonObject);

    final Long id3 = Long.valueOf(id + 100);
    final String name3 = name + "4Testing";
    jsonObject.setPrimaryKey(id3);
    jsonObject.setName(name3);
    jsonObject.setAddress(new Address(streetList.get(0), suburbList.get(0), cityList.get(0), stateList.get(0),
        postcodeList.get(0)));
    jsonObject.setIntNumber(Integer.MAX_VALUE >>> 1);
    jsonObject.setDoubleNumber(1234.1000D);

    final String json3 =
      "{\"id\":" + id3 + ",\"name\":\"" + name3 + "\",\"address\":{\"street\":\"" + streetList.get(0)
          + "\",\"suburb\":\"" + suburbList.get(0) + "\",\"city\":\"" + cityList.get(0) + "\",\"state\":\""
          + stateList.get(0) + "\",\"postcode\":\"" + postcodeList.get(0) + "\"},\"intNumber\":"
          + (Integer.MAX_VALUE >>> 1) + ",\"doubleNumber\":1234.1}";
    System.out.println("\nReflectionJsonStathamTest.testNestedJsonObject()");
    System.out.println("json:\n" + json3);
    System.out.println("java: ");
    final NestedJsonObject result3 = jsonStatham.convertFromJson(NestedJsonObject.class, json3);
    System.out.println(result3);
    assertThat(result3).isEqualTo(jsonObject);

    jsonObject.setPrimaryKey(Long.valueOf(id));
    jsonObject.setName(name);
    jsonObject.setAddress(address);
    jsonObject.setIntNumber(Integer.MAX_VALUE);
    jsonObject.setDoubleNumber(1234.0D);

    final String json4 =
      "{\"id\":" + id + ",\"name\":\"" + name + "\",\"address\":{\"street\":\"" + streetList.get(0)
          + "\",\"suburb\":\"" + suburbList.get(0) + "\",\"city\":\"" + cityList.get(0) + "\",\"state\":\""
          + stateList.get(0) + "\",\"postcode\":\"" + postcodeList.get(0) + "\"},\"intNumber\":" + Integer.MAX_VALUE
          + ",\"doubleNumber\":1234}";
    System.out.println("\nReflectionJsonStathamTest.testNestedJsonObject()");
    System.out.println("json:\n" + json4);
    System.out.println("java: ");
    final NestedJsonObject result4 = jsonStatham.convertFromJson(NestedJsonObject.class, json4);
    System.out.println(result4);
    assertThat(result4).isEqualTo(jsonObject);

    jsonObject.setPrimaryKey(Long.valueOf(id));
    jsonObject.setName(name);
    jsonObject.setAddress(address);
    jsonObject.setIntNumber(Integer.MAX_VALUE);
    jsonObject.setDoubleNumber(123456789.1234D);

    final String json5 =
      "{\"id\":" + id + ",\"name\":\"" + name + "\",\"address\":{\"street\":\"" + streetList.get(0)
          + "\",\"suburb\":\"" + suburbList.get(0) + "\",\"city\":\"" + cityList.get(0) + "\",\"state\":\""
          + stateList.get(0) + "\",\"postcode\":\"" + postcodeList.get(0) + "\"},\"intNumber\":" + Integer.MAX_VALUE
          + ",\"doubleNumber\":" + 123456789.1234D + "}";
    System.out.println("\nReflectionJsonStathamTest.testNestedJsonObject()");
    System.out.println("json:\n" + json5);
    System.out.println("java: ");
    final NestedJsonObject result5 = jsonStatham.convertFromJson(NestedJsonObject.class, json5);
    System.out.println(result5);
    assertThat(result5).isEqualTo(jsonObject);
  }

  @Test(expected = JsonStathamException.class)
  public void testJson2JavaJsonObjectWithDuplicateKeys()
  {
    System.out.println("\nJsonStathamInActionTest.testJson2JavaJsonObjectWithDuplicateKeys()");
    final JsonObjectWithDuplicateKeys jsonObjectWithDuplicateKeys = new JsonObjectWithDuplicateKeys();
    jsonObjectWithDuplicateKeys.setUsername("kevinlee");
    jsonObjectWithDuplicateKeys.setName("Kevin");
    jsonObjectWithDuplicateKeys.setFullName("Kevin Lee");
    jsonObjectWithDuplicateKeys.setEmail("kevin@test.test");

    System.out.println("result: ");
    JsonObjectWithDuplicateKeys result = null;
    try
    {
      result = jsonStatham.convertFromJson(JsonObjectWithDuplicateKeys.class, "{}");
    }
    catch (final JsonStathamException e)
    {
      System.out.println(e.getMessage());
      throw e;
    }
    System.out.println(result);
  }

  @Test
  public void testJson2JavaJsonObjectWithoutFieldName()
  {
    System.out.println("\nJsonStathamInActionTest.testJson2JavaJsonObjectWithoutFieldName()");
    final int id = 5;
    final String name = "Kevin Lee";
    final String address = "123 ABC Street";
    final JsonObjectWithoutFieldName jsonObjectWithoutFieldName = new JsonObjectWithoutFieldName(id, name, address);
    final String json = "{\"id\":" + id + ",\"name\":\"" + name + "\",\"address\":\"" + address + "\"}";
    System.out.println("json:\n" + json);
    System.out.println("java: ");
    final JsonObjectWithoutFieldName result = jsonStatham.convertFromJson(JsonObjectWithoutFieldName.class, json);
    System.out.println(result);
    assertThat(result).isEqualTo(jsonObjectWithoutFieldName);
  }

  @SuppressWarnings("deprecation")
  @Test
  public void testJson2JavaComplexJsonObjectWithMethodUse() throws JsonStathamException, IllegalArgumentException,
      InstantiationException, IllegalAccessException, InvocationTargetException
  {
    System.out.println("\nJsonStathamInActionTest.testJson2JavaComplexJsonObjectWithMethodUse()");
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
          + address.getSuburb() + "\",\"city\":\"" + address.getCity() + "\",\"state\":\"" + address.getState()
          + "\",\"postcode\":\"" + address.getPostcode() + "\"}," + "\"date\":" + jsonObject.getDate()
              .getTime() + "," + "\"dateWithValueAccessor\":" + jsonObject.getDateWithValueAccessor()
              .getTime() + ",\"calendar\":" + jsonObject.getCalendar()
              .getTimeInMillis() + ",\"calendarWithValueAccessor\":" + jsonObject.getCalendarWithValueAccessor()
              .getTimeInMillis() + "}";
    System.out.println("json:\n" + json);
    System.out.println("java: ");
    final ComplexJsonObjectWithValueAccessor result =
      jsonStatham.convertFromJson(ComplexJsonObjectWithValueAccessor.class, json);
    System.out.println(result);
    assertThat(result).isEqualTo(jsonObject);
  }

  @Test
  public void testJson2JavaComplexJsonObjectWithValueAccessorWithoutItsName()
  {
    System.out.println("\nJsonStathamInActionTest.testJson2JavaComplexJsonObjectWithValueAccessorWithoutItsName()");
    final ComplexJsonObjectWithValueAccessorWithoutItsName jsonObject =
      new ComplexJsonObjectWithValueAccessorWithoutItsName();
    jsonObject.setPrimaryKey(Long.valueOf(1));
    jsonObject.setName("Kevin");
    jsonObject.setRegistered(true);
    jsonObject.setEnabled(false);
    jsonObject.setAddress(address);
    @SuppressWarnings("deprecation")
    final Date date = new Date(2010, 9, 27);
    final Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.getTimeInMillis();
    jsonObject.setDate(date);
    jsonObject.setDateWithValueAccessor(date);
    jsonObject.setCalendar(calendar);
    jsonObject.setCalendarWithValueAccessor(calendar);

    final String json =
      "{\"id\":1,\"name\":\"Kevin\",\"registered\":true,\"enabled\":false,\"address\":{\"street\":\""
          + address.getStreet() + "\",\"suburb\":\"" + address.getSuburb() + "\",\"city\":\"" + address.getCity()
          + "\",\"state\":\"" + address.getState() + "\",\"postcode\":\"" + address.getPostcode() + "\"},"
          + "\"date\":" + jsonObject.getDate()
              .getTime() + "," + "\"dateWithValueAccessor\":" + jsonObject.dateWithValueAccessor()
              .getTime() + ",\"calendar\":" + jsonObject.getCalendar()
              .getTimeInMillis() + ",\"calendarWithValueAccessor\":" + jsonObject.calendarWithValueAccessor()
              .getTimeInMillis() + "}";
    System.out.println("json:\n" + json);
    System.out.println("java: ");
    final ComplexJsonObjectWithValueAccessorWithoutItsName result =
      jsonStatham.convertFromJson(ComplexJsonObjectWithValueAccessorWithoutItsName.class, json);
    System.out.println(result);
    assertThat(result).isEqualTo(jsonObject);
  }

  @Test
  public void testJson2JavaJsonObjectContainingCollection()
  {
    System.out.println("\nJsonStathamInActionTest.testJson2JavaJsonObjectContainingCollection()");
    final String nameValue = "testJsonWithCollection";
    final Collection<String> collection =
      initialiseCollectionWithStringValues(new ArrayList<String>(), SOME_STRING_VALUE_ARRAY);

    final JsonObjectContainingCollection jsonObjectContainingCollection =
      new JsonObjectContainingCollection(nameValue, collection);
    final String json = getExpectedJsonArray("name", nameValue, "valueCollection");
    System.out.println("json:\n" + json);
    System.out.println("java: ");
    final JsonObjectContainingCollection result =
      jsonStatham.convertFromJson(JsonObjectContainingCollection.class, json);
    System.out.println(result);
    assertThat(result).isEqualTo(jsonObjectContainingCollection);
  }

  @Test
  public void testJson2JavaJsonObjectContainingList()
  {
    System.out.println("\nJsonStathamInActionTest.testJson2JavaJsonObjectContainingList()");
    final String nameValue = "testJsonWithList";
    final List<String> list = initialiseCollectionWithStringValues(new ArrayList<String>(), SOME_STRING_VALUE_ARRAY);

    final JsonObjectContainingList jsonObjectContainingList = new JsonObjectContainingList(nameValue, list);
    final String json = getExpectedJsonArray("name", nameValue, "valueList");
    System.out.println("json:\n" + json);
    System.out.println("java: ");
    final JsonObjectContainingList result = jsonStatham.convertFromJson(JsonObjectContainingList.class, json);
    System.out.println(result);
    assertThat(result).isEqualTo(jsonObjectContainingList);
  }

  @Test
  public void testJson2JavaJsonObjectContainingSet()
  {
    System.out.println("\nJsonStathamInActionTest.testJson2JavaJsonObjectContainingSet()");
    final String nameValue = "testJsonWithSet";
    final Set<String> set = initialiseCollectionWithStringValues(new HashSet<String>(), SOME_STRING_VALUE_ARRAY);

    final JsonObjectContainingSet jsonObjectContainingSet = new JsonObjectContainingSet(nameValue, set);
    final String json = getExpectedJsonArray("name", nameValue, "valueSet");
    System.out.println("json:\n" + json);
    System.out.println("java: ");
    final JsonObjectContainingSet result = jsonStatham.convertFromJson(JsonObjectContainingSet.class, json);
    System.out.println(result);
    assertThat(result).isEqualTo(jsonObjectContainingSet);
  }

  @Test
  public void testJson2JavaJsonObjectContainingMapEntrySetSet()
  {
    System.out.println("\nJsonStathamInActionTest.testJson2JavaJsonObjectContainingMapEntrySetSet()");
    final String nameValue = "testJsonObjectContainingMapEntrySetSet";

    final JsonObjectContainingMapEntrySet jsonObjectContainingSet =
      new JsonObjectContainingMapEntrySet(nameValue, addressMap.entrySet());

    final StringBuilder stringBuilder =
      new StringBuilder("{\"name\":\"testJsonObjectContainingMapEntrySetSet\",\"valueMapEntrySet\":[");
    for (final Entry<String, Address> entry : addressMap.entrySet())
    {
      final Address address = entry.getValue();
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
      jsonStatham.convertFromJson(JsonObjectContainingMapEntrySet.class, json);
    System.out.println(result);
    assertThat(result).isEqualTo(jsonObjectContainingSet);

    final String json2 = "{\"name\":\"testJsonObjectContainingMapEntrySetSet\",\"valueMapEntrySet\":[]}";
    final JsonObjectContainingMapEntrySet jsonObjectContainingSet2 =
      new JsonObjectContainingMapEntrySet(nameValue, new HashSet<Entry<String, Address>>());
    System.out.println("json:\n" + json2);
    System.out.println("java: ");
    final JsonObjectContainingMapEntrySet result2 =
      jsonStatham.convertFromJson(JsonObjectContainingMapEntrySet.class, json2);
    System.out.println(result2);
    assertThat(result2).isEqualTo(jsonObjectContainingSet2);

  }

  @SuppressWarnings("boxing")
  @Test
  public final void testJson2JavaJsonPojoHavingMap()
  {
    System.out.println("\nJsonStathamInActionTest.testJson2JavaJsonPojoHavingMap()");
    final Map<String, Long> map = new HashMap<String, Long>();
    map.put("Kevin", 1L);
    map.put("Lee", 2L);
    map.put("Kevin Lee", 3L);
    final JsonPojoHavingMap jsonPojoHavingMap = new JsonPojoHavingMap("Kevin", map);
    final String json = "{\"stringToLongMap\":{\"Kevin Lee\":3,\"Lee\":2,\"Kevin\":1},\"name\":\"Kevin\"}";
    final JsonPojoHavingMap result = jsonStatham.convertFromJson(JsonPojoHavingMap.class, json);
    assertThat(result).isEqualTo(jsonPojoHavingMap);
  }

  @Test
  public void testJson2JavaJsonObjectContainingIterator()
  {
    System.out.println("\nJsonStathamInActionTest.testJson2JavaJsonObjectContainingIterator()");
    final String nameValue = "testJsonObjectContainingIterator";
    final Collection<String> collection =
      initialiseCollectionWithStringValues(new ArrayList<String>(), SOME_STRING_VALUE_ARRAY);

    final JsonObjectContainingIterator jsonObjectContainingCollection =
      new JsonObjectContainingIterator(nameValue, collection.iterator());
    final String json = getExpectedJsonArray("name", nameValue, "valueIterator");
    System.out.println("json:\n" + json);
    System.out.println("java: ");
    final JsonObjectContainingIterator result = jsonStatham.convertFromJson(JsonObjectContainingIterator.class, json);
    System.out.println(result);
    assertThat(result).isEqualTo(jsonObjectContainingCollection);

  }

  @Test
  public void testJson2JavaJsonObjectContainingIterable()
  {
    System.out.println("\nJsonStathamInActionTest.testJson2JavaJsonObjectContainingIterable()");
    final String nameValue = "testJsonObjectContainingIterable";
    final Iterable<String> iterable = new Iterable<String>() {
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
    final JsonObjectContainingIterable result = jsonStatham.convertFromJson(JsonObjectContainingIterable.class, json);
    System.out.println(result);
    assertThat(result).isEqualTo(jsonObjectContainingCollection);
  }

  @Test
  public void testJsonObjectContainingNestedGenericTypes()
  {
    System.out.println("\nJsonStathamInActionTest.testJsonObjectContainingNestedGenericTypes()");
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
      jsonStatham.convertFromJson(JsonObjectHavingNestedGenericTypes.class, json);
    System.out.println(result);
    assertThat(result).isEqualTo(jsonObjectHavingNestedGenericTypes);
  }

  @Test
  public void testJson2JavaJsonObjectWithInterfaceInheritance()
  {
    System.out.println("\nJsonStathamInActionTest.testJson2JavaJsonObjectWithInterfaceInheritance()");
    final String name = "Kevin Lee";
    final int number = 99;
    final String email = "kevinlee@test.test";
    final SomeInterface jsonObject = new SomeImplementingClass(name, number, email);
    final String json = "{\"name\":\"" + name + "\",\"number\":" + number + ",\"email\":\"" + email + "\"}";
    System.out.println("json:\n" + json);
    System.out.println("java: ");
    final SomeInterface result = jsonStatham.convertFromJson(SomeImplementingClass.class, json);
    System.out.println(result);
    assertThat(result).isEqualTo(jsonObject);
  }

  @Test
  public void testJson2JavaJsonObjectWithImplementationInheritance()
  {
    System.out.println("\nJsonStathamInActionTest.testJson2JavaJsonObjectWithImplementationInheritance()");
    final String name = "Kevin";
    final int number = 5;
    final String email = "kevin@test.test";
    final SubClass jsonObject = new SubClass(name, number, email);
    final String json = "{\"name\":\"" + name + "\",\"number\":" + number + ",\"email\":\"" + email + "\"}";
    System.out.println("json:\n" + json);
    System.out.println("java: ");
    final SubClass result = jsonStatham.convertFromJson(SubClass.class, json);
    System.out.println(result);
    assertThat(result).isEqualTo(jsonObject);
  }

  @Test
  public void testJson2JavaJsonObjectWithDoubleImplementationInheritanceAndNoOwnFieldsInSecondSubClass()
  {
    System.out.println("\nJsonStathamInActionTest.testJson2JavaJsonObjectWithDoubleImplementationInheritanceAndNoOwnFieldsInSecondSubClass()");
    final String name = "Kevin";
    final int number = 11;
    final String email = "kevin@test.blahblah";
    final SubClass jsonObject = new SecondSubClassWithoutOwnFields(name, number, email);
    final String json = "{\"name\":\"" + name + "\",\"number\":" + number + ",\"email\":\"" + email + "\"}";
    System.out.println("json:\n" + json);
    System.out.println("java: ");
    final SubClass result = jsonStatham.convertFromJson(SubClass.class, json);
    System.out.println(result);
    assertThat(result).isEqualTo(jsonObject);
  }

  @Test
  public void testJson2JavaJsonObjectWithDoubleImplementationInheritanceAndOwnFieldsInSecondSubClass()
  {
    System.out.println("\nJsonStathamInActionTest.testJson2JavaJsonObjectWithDoubleImplementationInheritanceAndOwnFieldsInSecondSubClass()");
    final String name = "Mr. Lee";
    final int number = 999;
    final String email = "kevin@another.email";
    final String comment = "Blah blah";
    final SecondSubClassWithOwnFields jsonObject =
      new SecondSubClassWithOwnFields(name, number, email, address, comment);
    final String json =
      "{\"name\":\"" + name + "\",\"number\":" + number + ",\"email\":\"" + email + "\"," + "\"address\":"
          + "{\"street\":\"" + address.getStreet() + "\",\"suburb\":\"" + address.getSuburb() + "\",\"city\":\""
          + address.getCity() + "\",\"state\":\"" + address.getState() + "\",\"postcode\":\"" + address.getPostcode()
          + "\"},\"comment\":\"" + comment + "\"}";
    System.out.println("json:\n" + json);
    System.out.println("java: ");
    final SecondSubClassWithOwnFields result = jsonStatham.convertFromJson(SecondSubClassWithOwnFields.class, json);
    System.out.println(result);
    assertThat(result).isEqualTo(jsonObject);
  }

  @Test
  public void testJson2JavaJsonObjectWithImplementationInheritanceWithNoJsonObjectSuperClass()
  {
    System.out.println("\nJsonStathamInActionTest.testJson2JavaJsonObjectWithImplementationInheritanceWithNoJsonObjectSuperClass()");
    final String email = "kevin@test.test";
    final SubClassWithNoJsonObjectSuperClass jsonObject = new SubClassWithNoJsonObjectSuperClass(null, 0, email);
    final String json = "{\"email\":\"" + email + "\"}";
    System.out.println("json:\n" + json);
    System.out.println("java: ");
    final SubClassWithNoJsonObjectSuperClass result =
      jsonStatham.convertFromJson(SubClassWithNoJsonObjectSuperClass.class, json);
    System.out.println(result);
    assertThat(result).isEqualTo(jsonObject);
  }

  @Test
  public void testJson2JavaJsonObjectWithImplementationInheritanceWithValueAccessor()
  {
    System.out.println("\nJsonStathamInActionTest.testJson2JavaJsonObjectWithImplementationInheritanceWithValueAccessor()");
    final String name = "Kevin";
    final int number = 5;
    final String email = "kevin@test.test";
    final SubClassWithValueAccessor jsonObject = new SubClassWithValueAccessor(name, number, email);
    final String json = "{\"name\":\"" + name + "\",\"number\":" + number + ",\"email\":\"" + email + "\"}";
    System.out.println("json:\n" + json);
    System.out.println("java: ");
    final SubClassWithValueAccessor result = jsonStatham.convertFromJson(SubClassWithValueAccessor.class, json);
    System.out.println(result);
    assertThat(result).isEqualTo(jsonObject);
  }

  @Test
  public void testJson2JavaJsonObjectWithImplementationInheritanceWithValueAccessorWithoutItsName()
  {
    System.out.println("\nJsonStathamInActionTest.testJson2JavaJsonObjectWithImplementationInheritanceWithValueAccessorWithoutItsName()");
    final String name = "Kevin";
    final int number = 5;
    final String email = "kevin@test.test";
    final SubClassWithValueAccessorWithoutItsName jsonObject =
      new SubClassWithValueAccessorWithoutItsName(name, number, email);
    final String json = "{\"name\":\"" + name + "\",\"number\":" + number + ",\"email\":\"" + email + "\"}";
    System.out.println("json:\n" + json);
    System.out.println("java: ");
    final SubClassWithValueAccessorWithoutItsName result =
      jsonStatham.convertFromJson(SubClassWithValueAccessorWithoutItsName.class, json);
    System.out.println(result);
    assertThat(result).isEqualTo(jsonObject);
  }

  @Test
  public void testJson2JavaJsonObjectWithImplementationInheritanceWithValueAccessorWithAbstractMethod()
  {
    System.out.println("\nJsonStathamInActionTest.testJson2JavaJsonObjectWithImplementationInheritanceWithValueAccessorWithAbstractMethod()");
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
      jsonStatham.convertFromJson(SubClassWithValueAccessorWithAbstractMethod.class, json);
    System.out.println(result);
    assertThat(result).isEqualTo(jsonObject);
  }

  @Test
  public void testJson2JavaJsonObjectWithImplementationInheritanceWithValueAccessorWithOverriddenMethod()
  {
    System.out.println("\nJsonStathamInActionTest.testJson2JavaJsonObjectWithImplementationInheritanceWithValueAccessorWithOverriddenMethod()");
    final String name = "Kevin";
    final int number = 5;
    final String email = "kevin@test.test";
    final SubClassWithValueAccessorWithOverriddenMethod jsonObject =
      new SubClassWithValueAccessorWithOverriddenMethod(name, number, email);
    final String json = "{\"name\":\"" + name + "\",\"number\":" + number + ",\"email\":\"" + email + "\"}";
    System.out.println("json:\n" + json);
    System.out.println("java: ");
    final SubClassWithValueAccessorWithOverriddenMethod result =
      jsonStatham.convertFromJson(SubClassWithValueAccessorWithOverriddenMethod.class, json);
    System.out.println(result);
    assertThat(result).isEqualTo(jsonObject);
  }

  @Test
  public void testJson2JavaJsonObjectContainingEnums()
  {
    System.out.println("\nJsonStathamInActionTest.testJson2JavaJsonObjectContainingEnums()");

    String json =
      "{\"name\":\"" + "Kevin" + "\",\"number\":" + 1 + ",\"passed\":" + true + ",\"role\":\"" + "SYSTEM_ADMIN"
          + "\",\"access\":[]}";
    System.out.println("json:\n" + json);
    System.out.println("java: ");
    JsonObjectContainingEnums result = jsonStatham.convertFromJson(JsonObjectContainingEnums.class, json);
    System.out.println(result);
    assertThat(result).isEqualTo(new JsonObjectContainingEnums("Kevin", 1, true, Role.SYSTEM_ADMIN));

    json =
      "{\"name\":\"" + "Kevin" + "\",\"number\":" + 1 + ",\"passed\":" + true + ",\"role\":\"" + "MEMBER"
          + "\",\"access\":[\"BLOG\",\"EMAIL\"]}";
    System.out.println("json:\n" + json);
    System.out.println("java: ");
    result = jsonStatham.convertFromJson(JsonObjectContainingEnums.class, json);
    System.out.println(result);
    assertThat(result).isEqualTo(
        new JsonObjectContainingEnums("Kevin", 1, true, Role.MEMBER, Access.BLOG, Access.EMAIL));

    json =
      "{\"name\":\"" + "Kevin" + "\",\"number\":" + 1 + ",\"passed\":" + true + ",\"role\":\"" + "MEMBER"
          + "\",\"access\":[\"BLOG\",\"WIKI\",\"EMAIL\",\"TWITTER\"]}";
    System.out.println("json:\n" + json);
    System.out.println("java: ");
    result = jsonStatham.convertFromJson(JsonObjectContainingEnums.class, json);
    System.out.println(result);
    assertThat(result).isEqualTo(
        new JsonObjectContainingEnums("Kevin", 1, true, Role.MEMBER, Access.BLOG, Access.WIKI, Access.EMAIL,
            Access.TWITTER));
  }

  @Test
  public void testWithMultipleSelectionItemIntoJson()
  {
    System.out.println("JsonStathamInActionTest.testWithMultipleSelectionItem()");
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
    final String result = jsonStatham.convertIntoJson(itemDefinition);
    System.out.println(result);

    /* then */
    assertThat(result).isEqualTo(expected);

    /* given */
    final JsonStatham jsonStatham2 = ReflectionJsonStathams.newReflectionJsonStathamInAction();
    System.out.println();
    System.out.println("expected:\n" + expected);

    /* when */
    System.out.println("actual: ");
    final String result2 = jsonStatham2.convertIntoJson(itemDefinition);
    System.out.println(result2);

    /* then */
    assertThat(result2).isEqualTo(expected);
  }

  @Test
  public void testWithMultipleSelectionItemFromJson()
  {
    System.out.println("JsonStathamInActionTest.testWithMultipleSelectionItemFromJson()");

    /* given */
    final ItemDefinition expected =
      new MultipleSelectionItem("Global Warming", "In your opinion, global warming...", Arrays.asList(new Option("A",
          "is just a fad."), new Option("B", "already started to affect our lives."), new Option("C",
          "will not have any impact on our lives in the next 10 years."), new Option("D",
          "is really a problem for the next generation."), new Option("E",
          "will not have any effect for at least 100 years.")));
    System.out.println("expected:\n" + expected);

    final String itemDefinition =
      "{\"name\":\"Global Warming\",\"instructions\":\"In your opinion, global warming...\",\"options\":[{\"code\":\"A\",\"text\":\"is just a fad.\"},{\"code\":\"B\",\"text\":\"already started to affect our lives.\"},{\"code\":\"C\",\"text\":\"will not have any impact on our lives in the next 10 years.\"},{\"code\":\"D\",\"text\":\"is really a problem for the next generation.\"},{\"code\":\"E\",\"text\":\"will not have any effect for at least 100 years.\"}]}";

    /* when */
    System.out.println("actual: ");
    final ItemDefinition result = jsonStatham.convertFromJson(MultipleSelectionItem.class, itemDefinition);
    System.out.println(result);

    /* then */
    assertThat(result).isEqualTo(expected);

    /* given */
    final JsonStatham jsonStatham2 = ReflectionJsonStathams.newReflectionJsonStathamInAction();
    System.out.println();
    System.out.println("expected:\n" + expected);

    /* when */
    System.out.println("actual: ");
    final ItemDefinition result2 = jsonStatham2.convertFromJson(MultipleSelectionItem.class, itemDefinition);
    System.out.println(result2);

    /* then */
    assertThat(result2).isEqualTo(expected);
  }

  @Test
  public void testItemConfig() throws ArrayIndexOutOfBoundsException, IllegalArgumentException, InstantiationException,
      IllegalAccessException, InvocationTargetException
  {
    /* given */
    final ItemConfig expected = ItemConfig.NULL_ITEM_CONFIG;
    System.out.println("expected:\n" + expected);

    final String itemConfig =
      "{\"idAutomated\":null,\"optionsRandomised\":null,\"optionCodesShown\":null,\"correctAnswers\":[],\"optional\":false}";

    /* when */
    System.out.println("actual: ");
    final ItemConfig result = jsonStatham.convertFromJson(ItemConfig.class, itemConfig);
    System.out.println(result);

    /* then */
    assertThat(result).isEqualTo(expected);
  }

  @Test
  public void testConvertIntoJsonConvertible()
  {
    System.out.println("\nJsonStathamInActionTest.testConvertIntoJsonConvertible() {");
    /* given */
    final String jsonObjectString =
      "{\"first\":{\"abc\":\"1234\"},\"second\":{\"z\":\"yx\"},\"third\":{\"a\":\"aaa\"}}";
    final String jsonArrayString =
      "["
          + "{\"name\":\"test\",\"params\":{\"first\":{\"abc\":\"1234\"},\"second\":{\"z\":\"yx\"},\"third\":{\"a\":\"aaa\"}}},"
          + "{\"name\":\"test\",\"params\":{\"first\":{\"abc\":\"1234\"},\"second\":{\"z\":\"yx\"},\"third\":{\"a\":\"aaa\"}}},"
          + "{\"name\":\"test\",\"params\":{\"first\":{\"abc\":\"1234\"},\"second\":{\"z\":\"yx\"},\"third\":{\"a\":\"aaa\"}}}"
          + "]";
    final String json =
      "{\"name\":\"test\",\"jsonObject\":" + jsonObjectString + ",\"jsonArray\":" + jsonArrayString + "}";
    System.out.println("json:\n" + json);

    final JsonObject jsonObject = OrderedJsonObject.newJsonObject(jsonObjectString);
    final JsonArray jsonArray = JsonArrayWithOrderedJsonObject.newJsonArray(jsonArrayString);

    final JsonObject expected = OrderedJsonObject.newJsonObject(json);
    final ObjectHavingJsonObjectAndJsonArray target =
      new ObjectHavingJsonObjectAndJsonArray("test", jsonObject, jsonArray);
    System.out.println("expected:\n" + expected);

    /* when */
    System.out.println("actual:");
    final JsonObject actual = jsonStatham.convertIntoJsonConvertible(target);
    System.out.println(actual);

    /* then */
    assertThat(actual).isEqualTo(expected);
    System.out.println("} JsonStathamInActionTest.testConvertIntoJsonConvertible()");
  }

  @Test
  public void testConvertIntoJsonConvertibleObject()
  {
    /* given */
    final String jsonObjectString =
      "{\"first\":{\"abc\":\"1234\"},\"second\":{\"z\":\"yx\"},\"third\":{\"a\":\"aaa\"}}";
    System.out.println("json:\n" + jsonObjectString);

    final JsonObject expected = OrderedJsonObject.newJsonObject(jsonObjectString);
    System.out.println("expected:\n" + expected);

    /* when */
    System.out.println("actual:");
    final JsonObject actual =
      jsonStatham.convertIntoJsonConvertible(new ParamObject(new StringAndObjectPair("abc", "1234"),
          new StringAndObjectPair("z", "yx"), new StringAndObjectPair("a", "aaa")));
    System.out.println(actual);

    /* then */
    assertThat(actual).isEqualTo(expected);
  }

  @Json
  private static class TestJsonObject
  {
    @JsonField
    String name;

    @JsonField
    ParamObject params;

    public TestJsonObject(final String name, final ParamObject params)
    {
      this.name = name;
      this.params = params;
    }

    @Override
    public int hashCode()
    {
      return hash(name, params);
    }

    @Override
    public boolean equals(final Object testJsonObject)
    {
      if (this == testJsonObject)
        return true;
      final TestJsonObject that = castIfInstanceOf(TestJsonObject.class, testJsonObject);
      return null != testJsonObject && (equal(this.name, that.name) && equal(this.params, that.params));
    }

    @Override
    public String toString()
    {
      /* @formatter:off */
			return toStringBuilder(this)
					.add("name", name)
					.add("params", params)
					.toString();
			/* @formatter:on */
    }
  }

  @Json
  private static class ParamObject
  {
    @JsonField
    JsonNameValuePair<String, Object> first;
    @JsonField
    JsonNameValuePair<String, Object> second;
    @JsonField
    JsonNameValuePair<String, Object> third;

    public ParamObject(final JsonNameValuePair<String, Object> first, final JsonNameValuePair<String, Object> second,
        final JsonNameValuePair<String, Object> third)
    {
      this.first = first;
      this.second = second;
      this.third = third;
    }

    @Override
    public int hashCode()
    {
      return hash(first, second, third);
    }

    @Override
    public boolean equals(final Object paramObject)
    {
      if (this == paramObject)
        return true;
      final ParamObject that = castIfInstanceOf(ParamObject.class, paramObject);
      return null != that
          && (equal(this.first, that.first) && equal(this.second, that.second) && equal(this.third, that.third));
    }

    @Override
    public String toString()
    {
      /* @formatter:off */
			return toStringBuilder(this)
					.add("first", first)
					.add("second", second)
					.add("third", third)
					.toString();
			/* @formatter:on */
    }
  }

  private static class StringAndObjectPair extends ImmutableJsonNameValuePair<Object>
  {
    public StringAndObjectPair(final String first, final Object second)
    {
      super(first, second);
    }
  }

  @Test
  public void testConvertIntoJsonConvertibleArray()
  {
    /* given */
    final String[] names = { "test1", "test2", "test3" };
    final String[] params1_1 = { "number1", "number2", "number3" };
    @SuppressWarnings("boxing")
    final Object[] params1_2 = { 1234, 123, 12 };
    final String[] params2_1 = { "value1", "value2", "value3" };
    final Object[] params2_2 = { "yxA", "yxB", "yxC" };
    final String[] params3_1 = { "test1", "test2", "test3" };
    final Object[] params3_2 = { "aaa", "bbb", "ccc" };
    final String jsonArrayString =
      "[" + "{\"name\":\"" + names[0] + "\",\"params\":{\"first\":{\"" + params1_1[0] + "\":" + params1_2[0]
          + "},\"second\":{\"" + params2_1[0] + "\":\"" + params2_2[0] + "\"},\"third\":{\"" + params3_1[0] + "\":\""
          + params3_2[0] + "\"}}}," + "{\"name\":\"" + names[1] + "\",\"params\":{\"first\":{\"" + params1_1[1] + "\":"
          + params1_2[1] + "},\"second\":{\"" + params2_1[1] + "\":\"" + params2_2[1] + "\"},\"third\":{\""
          + params3_1[1] + "\":\"" + params3_2[1] + "\"}}}," + "{\"name\":\"" + names[2]
          + "\",\"params\":{\"first\":{\"" + params1_1[2] + "\":" + params1_2[2] + "},\"second\":{\"" + params2_1[2]
          + "\":\"" + params2_2[2] + "\"},\"third\":{\"" + params3_1[2] + "\":\"" + params3_2[2] + "\"}}}" + "]";
    System.out.println("json:\n" + jsonArrayString);

    final JsonArray expected = JsonArrayWithOrderedJsonObject.newJsonArray(jsonArrayString);
    System.out.println("expected:\n" + expected);

    /* @formatter:off */
		final List<TestJsonObject> list = Arrays.asList(
				new TestJsonObject(
						names[0],
						new ParamObject(
								new StringAndObjectPair(params1_1[0], params1_2[0]),
								new StringAndObjectPair(params2_1[0], params2_2[0]),
								new StringAndObjectPair(params3_1[0], params3_2[0]))),
				new TestJsonObject(
						names[1],
						new ParamObject(
								new StringAndObjectPair(params1_1[1], params1_2[1]),
								new StringAndObjectPair(params2_1[1], params2_2[1]),
								new StringAndObjectPair(params3_1[1], params3_2[1]))),
				new TestJsonObject(
						names[2],
						new ParamObject(
								new StringAndObjectPair(params1_1[2], params1_2[2]),
								new StringAndObjectPair(params2_1[2], params2_2[2]),
								new StringAndObjectPair(params3_1[2], params3_2[2]))));
		/* @formatter:on */

    /* when */
    System.out.println("actual:");
    final JsonArray actual = jsonStatham.convertIntoJsonConvertible(list);
    System.out.println(actual);

    /* then */
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  public void testConvertFromJsonConvertible()
  {
    /* given */
    final String jsonObjectString =
      "{\"first\":{\"abc\":\"1234\"},\"second\":{\"z\":\"yx\"},\"third\":{\"a\":\"aaa\"}}";
    final String jsonArrayString =
      "["
          + "{\"name\":\"test\",\"params\":{\"first\":{\"abc\":\"1234\"},\"second\":{\"z\":\"yx\"},\"third\":{\"a\":\"aaa\"}}},"
          + "{\"name\":\"test\",\"params\":{\"first\":{\"abc\":\"1234\"},\"second\":{\"z\":\"yx\"},\"third\":{\"a\":\"aaa\"}}},"
          + "{\"name\":\"test\",\"params\":{\"first\":{\"abc\":\"1234\"},\"second\":{\"z\":\"yx\"},\"third\":{\"a\":\"aaa\"}}}"
          + "]";
    final String json =
      "{\"name\":\"test\",\"jsonObject\":" + jsonObjectString + ",\"jsonArray\":" + jsonArrayString + "}";
    System.out.println("json:\n" + json);

    final JsonObject jsonObject = OrderedJsonObject.newJsonObject(jsonObjectString);
    final JsonArray jsonArray = JsonArrayWithOrderedJsonObject.newJsonArray(jsonArrayString);

    final ObjectHavingJsonObjectAndJsonArray expected =
      new ObjectHavingJsonObjectAndJsonArray("test", jsonObject, jsonArray);

    final JsonObject target = OrderedJsonObject.newJsonObject(json);
    System.out.println("expected:\n" + expected);

    /* when */
    System.out.println("actual:");
    final ObjectHavingJsonObjectAndJsonArray actual =
      jsonStatham.convertFromJsonConvertible(ObjectHavingJsonObjectAndJsonArray.class, target);
    System.out.println(actual);

    /* then */
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  public void testConvertFromJsonConvertibleWithJsonArray()
  {
    /* given */
    final String[] names = { "test1", "test2", "test3" };
    final String[] params1_1 = { "number1", "number2", "number3" };
    @SuppressWarnings("boxing")
    final Object[] params1_2 = { 1234, 123, 12 };
    final String[] params2_1 = { "value1", "value2", "value3" };
    final Object[] params2_2 = { "yxA", "yxB", "yxC" };
    final String[] params3_1 = { "test1", "test2", "test3" };
    final Object[] params3_2 = { "aaa", "bbb", "ccc" };
    final String jsonArrayString =
      "[" + "{\"name\":\"" + names[0] + "\",\"params\":{\"first\":{\"" + params1_1[0] + "\":" + params1_2[0]
          + "},\"second\":{\"" + params2_1[0] + "\":\"" + params2_2[0] + "\"},\"third\":{\"" + params3_1[0] + "\":\""
          + params3_2[0] + "\"}}}," + "{\"name\":\"" + names[1] + "\",\"params\":{\"first\":{\"" + params1_1[1] + "\":"
          + params1_2[1] + "},\"second\":{\"" + params2_1[1] + "\":\"" + params2_2[1] + "\"},\"third\":{\""
          + params3_1[1] + "\":\"" + params3_2[1] + "\"}}}," + "{\"name\":\"" + names[2]
          + "\",\"params\":{\"first\":{\"" + params1_1[2] + "\":" + params1_2[2] + "},\"second\":{\"" + params2_1[2]
          + "\":\"" + params2_2[2] + "\"},\"third\":{\"" + params3_1[2] + "\":\"" + params3_2[2] + "\"}}}" + "]";
    System.out.println("json:\n" + jsonArrayString);

    final JsonArray jsonArray = JsonArrayWithOrderedJsonObject.newJsonArray(jsonArrayString);

    /* @formatter:off */
		final TestJsonObject[] expected = {
				new TestJsonObject(
						names[0],
						new ParamObject(
								new StringAndObjectPair(params1_1[0], params1_2[0]),
								new StringAndObjectPair(params2_1[0], params2_2[0]),
								new StringAndObjectPair(params3_1[0], params3_2[0]))),
				new TestJsonObject(
						names[1],
						new ParamObject(
								new StringAndObjectPair(params1_1[1], params1_2[1]),
								new StringAndObjectPair(params2_1[1], params2_2[1]),
								new StringAndObjectPair(params3_1[1], params3_2[1]))),
				new TestJsonObject(
						names[2],
						new ParamObject(
								new StringAndObjectPair(params1_1[2], params1_2[2]),
								new StringAndObjectPair(params2_1[2], params2_2[2]),
								new StringAndObjectPair(params3_1[2], params3_2[2])))};
		/* @formatter:on */
    System.out.println("expected:\n" + toStringOf(expected));

    /* when */
    System.out.println("actual:");
    final TestJsonObject[] actual = jsonStatham.convertFromJsonConvertible(TestJsonObject[].class, jsonArray);
    System.out.println(toStringOf(actual));

    /* then */
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  public void testConvertFromJsonConvertibleWithTypeHolder()
  {
    /* given */
    final String[] names = { "test1", "test2", "test3" };
    final String[] params1_1 = { "number1", "number2", "number3" };
    @SuppressWarnings("boxing")
    final Object[] params1_2 = { 1234, 123, 12 };
    final String[] params2_1 = { "value1", "value2", "value3" };
    final Object[] params2_2 = { "yxA", "yxB", "yxC" };
    final String[] params3_1 = { "test1", "test2", "test3" };
    final Object[] params3_2 = { "aaa", "bbb", "ccc" };
    final String jsonArrayString =
      "[" + "{\"name\":\"" + names[0] + "\",\"params\":{\"first\":{\"" + params1_1[0] + "\":" + params1_2[0]
          + "},\"second\":{\"" + params2_1[0] + "\":\"" + params2_2[0] + "\"},\"third\":{\"" + params3_1[0] + "\":\""
          + params3_2[0] + "\"}}}," + "{\"name\":\"" + names[1] + "\",\"params\":{\"first\":{\"" + params1_1[1] + "\":"
          + params1_2[1] + "},\"second\":{\"" + params2_1[1] + "\":\"" + params2_2[1] + "\"},\"third\":{\""
          + params3_1[1] + "\":\"" + params3_2[1] + "\"}}}," + "{\"name\":\"" + names[2]
          + "\",\"params\":{\"first\":{\"" + params1_1[2] + "\":" + params1_2[2] + "},\"second\":{\"" + params2_1[2]
          + "\":\"" + params2_2[2] + "\"},\"third\":{\"" + params3_1[2] + "\":\"" + params3_2[2] + "\"}}}" + "]";
    System.out.println("json:\n" + jsonArrayString);

    final JsonArray jsonArray = JsonArrayWithOrderedJsonObject.newJsonArray(jsonArrayString);

    /* @formatter:off */
		final List<TestJsonObject> expected = Arrays.asList(
				new TestJsonObject(
						names[0],
						new ParamObject(
								new StringAndObjectPair(params1_1[0], params1_2[0]),
								new StringAndObjectPair(params2_1[0], params2_2[0]),
								new StringAndObjectPair(params3_1[0], params3_2[0]))),
				new TestJsonObject(
						names[1],
						new ParamObject(
								new StringAndObjectPair(params1_1[1], params1_2[1]),
								new StringAndObjectPair(params2_1[1], params2_2[1]),
								new StringAndObjectPair(params3_1[1], params3_2[1]))),
				new TestJsonObject(
						names[2],
						new ParamObject(
								new StringAndObjectPair(params1_1[2], params1_2[2]),
								new StringAndObjectPair(params2_1[2], params2_2[2]),
								new StringAndObjectPair(params3_1[2], params3_2[2]))));
		/* @formatter:on */

    System.out.println("expected:\n" + toStringOf(expected));

    /* when */
    System.out.println("actual:");
    final List<TestJsonObject> actual =
      jsonStatham.convertFromJsonConvertible(new TypeHolder<List<TestJsonObject>>() {}, jsonArray);
    System.out.println(toStringOf(actual));

    /* then */
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  public final void testConvertJsonStringIntoJsonConvertible()
  {
    /* given */
    final String jsonObjectString =
      "{\"first\":{\"abc\":\"1234\"},\"second\":{\"z\":\"yx\"},\"third\":{\"a\":\"aaa\"}}";
    final String jsonArrayString =
      "["
          + "{\"name\":\"test\",\"params\":{\"first\":{\"abc\":\"1234\"},\"second\":{\"z\":\"yx\"},\"third\":{\"a\":\"aaa\"}}},"
          + "{\"name\":\"test\",\"params\":{\"first\":{\"abc\":\"1234\"},\"second\":{\"z\":\"yx\"},\"third\":{\"a\":\"aaa\"}}},"
          + "{\"name\":\"test\",\"params\":{\"first\":{\"abc\":\"1234\"},\"second\":{\"z\":\"yx\"},\"third\":{\"a\":\"aaa\"}}}"
          + "]";
    final String json =
      "{\"name\":\"test\",\"jsonObject\":" + jsonObjectString + ",\"jsonArray\":" + jsonArrayString + "}";
    System.out.println("json:\n" + json);

    final JsonObject jsonObject = OrderedJsonObject.newJsonObject(jsonObjectString);
    final JsonArray jsonArray = JsonArrayWithOrderedJsonObject.newJsonArray(jsonArrayString);

    final JsonObject expected = OrderedJsonObject.newJsonObject();
    expected.put("name", "test");
    expected.put("jsonObject", jsonObject);
    expected.put("jsonArray", jsonArray);

    System.out.println("expected:\n" + expected);

    /* when */
    System.out.println("actual:");
    final JsonConvertible actual = jsonStatham.convertJsonStringIntoJsonConvertible(json);
    System.out.println(actual);

    /* then */
    assertThat(actual).isEqualTo(expected);
    assertThat(actual.isJsonObject()).isTrue();
    assertThat(actual.isJsonArray()).isFalse();
  }

  @Test
  public final void testConvertJsonStringIntoJsonConvertibleWithJsonArray()
  {
    /* given */
    final String json =
      "["
          + "{\"name\":\"test\",\"params\":{\"first\":{\"abc\":\"1234\"},\"second\":{\"z\":\"yx\"},\"third\":{\"a\":\"aaa\"}}},"
          + "{\"name\":\"test\",\"params\":{\"first\":{\"abc\":\"1234\"},\"second\":{\"z\":\"yx\"},\"third\":{\"a\":\"aaa\"}}},"
          + "{\"name\":\"test\",\"params\":{\"first\":{\"abc\":\"1234\"},\"second\":{\"z\":\"yx\"},\"third\":{\"a\":\"aaa\"}}}"
          + "]";
    System.out.println("json:\n" + json);

    final JsonArray expected = JsonArrayWithOrderedJsonObject.newJsonArray(json);
    System.out.println("expected:\n" + expected);

    /* when */
    System.out.println("actual:");
    final JsonConvertible actual = jsonStatham.convertJsonStringIntoJsonConvertible(json);
    System.out.println(actual);

    /* then */
    assertThat(actual).isEqualTo(expected);
    assertThat(actual.isJsonObject()).isFalse();
    assertThat(actual.isJsonArray()).isTrue();
  }
}

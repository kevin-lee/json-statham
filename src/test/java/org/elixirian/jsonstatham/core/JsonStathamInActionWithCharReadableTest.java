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
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.elixirian.jsonstatham.core.convertible.AbstractJsonObject;
import org.elixirian.jsonstatham.core.convertible.JsonArray;
import org.elixirian.jsonstatham.core.convertible.JsonArrayCreator;
import org.elixirian.jsonstatham.core.convertible.JsonArrayWithOrderedJsonObject;
import org.elixirian.jsonstatham.core.convertible.JsonArrayWithOrderedJsonObjectCreator;
import org.elixirian.jsonstatham.core.convertible.JsonObject;
import org.elixirian.jsonstatham.core.convertible.JsonObjectCreator;
import org.elixirian.jsonstatham.core.convertible.JsonScanner;
import org.elixirian.jsonstatham.core.convertible.JsonScannerCreator;
import org.elixirian.jsonstatham.core.convertible.JsonScannerForOrderedJsonObject;
import org.elixirian.jsonstatham.core.convertible.OrderedJsonObject;
import org.elixirian.jsonstatham.core.convertible.OrderedJsonObjectCreator;
import org.elixirian.jsonstatham.core.reflect.ReflectionJsonStathams;
import org.elixirian.jsonstatham.core.reflect.java2json.KnownDataStructureTypeProcessorDecider;
import org.elixirian.jsonstatham.core.reflect.java2json.KnownObjectReferenceTypeProcessorDecider;
import org.elixirian.jsonstatham.core.reflect.java2json.OneProcessorForKnownTypeDecider;
import org.elixirian.jsonstatham.core.reflect.java2json.ReflectionJavaToJsonConverter;
import org.elixirian.jsonstatham.core.reflect.json2java.DefaultJsonToJavaConfig;
import org.elixirian.jsonstatham.core.reflect.json2java.ReflectionJsonToJavaConverter;
import org.elixirian.jsonstatham.exception.JsonStathamException;
import org.elixirian.jsonstatham.json.Address;
import org.elixirian.jsonstatham.json.PersonJson;
import org.elixirian.jsonstatham.type.CharReadable;
import org.elixirian.jsonstatham.type.CharReadableFromInputStream;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
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
@RunWith(MockitoJUnitRunner.class)
public class JsonStathamInActionWithCharReadableTest
{
  private static final List<String> streetList = Arrays.asList("ABC Street", "90/120 Swanston St", "1 AAA St",
      "AAA 123 RD", "111 ABCDEF LN");
  private static final List<String> suburbList = Arrays.asList("", "Test Suburb", "Another", "Blah", "LAST");
  private static final List<String> cityList = Arrays.asList("Sydney", "Melbourne", "Brisbane", "Sydney", "Melbourne");
  private static final List<String> stateList = Arrays.asList("NSW", "VIC", "QLD", "NSW", "VIC");
  private static final List<String> postcodeList = Arrays.asList("2000", "3000", "4000", "2000", "3000");

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

  private JsonStatham jsonStatham;

  @Mock
  private JsonScannerCreator jsonScannerCreator;

  @Mock
  private JsonObjectCreator jsonObjectCreator;

  @Mock
  private JsonArrayCreator jsonArrayCreator;

  /**
   * @throws java.lang.Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception
  {
    System.out.println("###  JsonStathamInActionWithCharReadableTest starts ###");
  }

  /**
   * @throws java.lang.Exception
   */
  @AfterClass
  public static void tearDownAfterClass() throws Exception
  {
    System.out.println("\n### JsonStathamInActionWithCharReadableTest ends ###");
  }

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception
  {
    when(jsonScannerCreator.newJsonScanner(any(CharReadable.class))).then(new Answer<JsonScanner>() {

      @Override
      public JsonScanner answer(final InvocationOnMock invocation) throws Throwable
      {
        final CharReadable charReadable = (CharReadable) invocation.getArguments()[0];
        return new JsonScannerForOrderedJsonObject(charReadable);
      }
    });

    when(jsonObjectCreator.newJsonObjectConvertible()).then(ANSWER_FOR_NEW_JSON_OBJECT_CONVERTIBLE);

    when(jsonObjectCreator.newJsonObjectConvertible(any(JsonScanner.class))).then(new Answer<JsonObject>() {

      @Override
      public JsonObject answer(final InvocationOnMock invocation) throws Throwable
      {
        final JsonScanner jsonScanner = (JsonScanner) invocation.getArguments()[0];
        return new OrderedJsonObjectCreator().newJsonObjectConvertible(jsonScanner);
      }
    });

    when(jsonObjectCreator.newJsonObjectConvertible(anyString())).then(
        ANSWER_FOR_NEW_JSON_OBJECT_CONVERTIBLE_WITH_JSON_STRING);
    when(jsonObjectCreator.nullJsonObjectConvertible()).then(ANSWER_FOR_NULL_JSON_OBJECT_CONVERTIBLE);

    when(jsonArrayCreator.newJsonArrayConvertible()).then(ANSWER_FOR_JSON_ARRAY_CONVERTIBLE);
    when(jsonArrayCreator.newJsonArrayConvertible(any(JsonScanner.class))).then(new Answer<JsonArray>() {
      @Override
      public JsonArray answer(final InvocationOnMock invocation) throws Throwable
      {
        final JsonScanner jsonScanner = (JsonScanner) invocation.getArguments()[0];
        return new JsonArrayWithOrderedJsonObjectCreator().newJsonArrayConvertible(jsonScanner);
      }
    });
    when(jsonArrayCreator.newJsonArrayConvertible(anyString())).then(ANSWER_FOR_JSON_ARRAY_CONVERTIBLE_WITH_JSON_STRING);

    final ReflectionJavaToJsonConverter javaToJsonConverter =
      new ReflectionJavaToJsonConverter(jsonObjectCreator, jsonArrayCreator,
          new KnownDataStructureTypeProcessorDecider(), new KnownObjectReferenceTypeProcessorDecider(),
          new OneProcessorForKnownTypeDecider());

    final ReflectionJsonToJavaConverter jsonToJavaConverter =
      new ReflectionJsonToJavaConverter(DefaultJsonToJavaConfig.builder(jsonScannerCreator, jsonObjectCreator,
          jsonArrayCreator)
          .build());

    jsonStatham = new JsonStathamInAction(javaToJsonConverter, jsonToJavaConverter);
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
  public void testWithInputStream()
  {
    /* given */
    final InputStream inputStream = getClass().getResourceAsStream("/person.json");
    final PersonJson expected =
      new PersonJson(1L, "Kevin", "kevin@some-email.com", true, new Address(streetList.get(0), suburbList.get(0),
          cityList.get(0), stateList.get(0), postcodeList.get(0)));

    /* when */
    final PersonJson actual = jsonStatham.convertFromJson(PersonJson.class, inputStream);

    /* then */
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  public void testWithInputStream2()
  {
    /* given */
    final InputStream inputStream = getClass().getResourceAsStream("/people.json");
    /* @formatter:off */
    int i = 0;
    final List<PersonJson> expected =
      Arrays.asList(
          new PersonJson(1L, "Kevin", "kevin@some-email.com", true,
                         new Address(streetList.get(i),
                                     suburbList.get(i),
                                     cityList.get(i),
                                     stateList.get(i),
                                     postcodeList.get(i++))),
          new PersonJson(2L, "Tom", "tom@another-email.com", false,
                         new Address(streetList.get(i),
                                     suburbList.get(i),
                                     cityList.get(i),
                                     stateList.get(i),
                                     postcodeList.get(i++))),
          new PersonJson(3L, "John", "john.doe@test-email.com", true,
                         new Address(streetList.get(i),
                                     suburbList.get(i),
                                     cityList.get(i),
                                     stateList.get(i),
                                     postcodeList.get(i++))),
          new PersonJson(4L, "Mark", "mk@aaaa.bbb.com", true,
                         new Address(streetList.get(i),
                                     suburbList.get(i),
                                     cityList.get(i),
                                     stateList.get(i),
                                     postcodeList.get(i++))),
          new PersonJson(5L, "Sean", "sean@abcde.com", false,
                         new Address(streetList.get(i),
                                     suburbList.get(i),
                                     cityList.get(i),
                                     stateList.get(i),
                                     postcodeList.get(i++))));
    /* @formatter:on */

    /* when */
    final List<PersonJson> actual = Arrays.asList(jsonStatham.convertFromJson(PersonJson[].class, inputStream));

    /* then */
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  public void testWithInputStream3()
  {
    /* given */
    final InputStream inputStream = getClass().getResourceAsStream("/person.json");
    final PersonJson expected =
      new PersonJson(1L, "Kevin", "kevin@some-email.com", true, new Address(streetList.get(0), suburbList.get(0),
          cityList.get(0), stateList.get(0), postcodeList.get(0)));

    final JsonStatham jsonStatham = ReflectionJsonStathams.newReflectionJsonStathamInAction();

    /* when */
    final PersonJson actual = jsonStatham.convertFromJson(PersonJson.class, inputStream);

    /* then */
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  public void testWithInputStream4()
  {
    /* given */
    final InputStream inputStream = getClass().getResourceAsStream("/people.json");
    /* @formatter:off */
    int i = 0;
    final List<PersonJson> expected =
      Arrays.asList(
          new PersonJson(1L, "Kevin", "kevin@some-email.com", true,
                         new Address(streetList.get(i),
                                     suburbList.get(i),
                                     cityList.get(i),
                                     stateList.get(i),
                                     postcodeList.get(i++))),
          new PersonJson(2L, "Tom", "tom@another-email.com", false,
                         new Address(streetList.get(i),
                                     suburbList.get(i),
                                     cityList.get(i),
                                     stateList.get(i),
                                     postcodeList.get(i++))),
          new PersonJson(3L, "John", "john.doe@test-email.com", true,
                         new Address(streetList.get(i),
                                     suburbList.get(i),
                                     cityList.get(i),
                                     stateList.get(i),
                                     postcodeList.get(i++))),
          new PersonJson(4L, "Mark", "mk@aaaa.bbb.com", true,
                         new Address(streetList.get(i),
                                     suburbList.get(i),
                                     cityList.get(i),
                                     stateList.get(i),
                                     postcodeList.get(i++))),
          new PersonJson(5L, "Sean", "sean@abcde.com", false,
                         new Address(streetList.get(i),
                                     suburbList.get(i),
                                     cityList.get(i),
                                     stateList.get(i),
                                     postcodeList.get(i++))));
    /* @formatter:on */
    final JsonStatham jsonStatham = ReflectionJsonStathams.newReflectionJsonStathamInAction();

    /* when */
    final List<PersonJson> actual = Arrays.asList(jsonStatham.convertFromJson(PersonJson[].class, inputStream));

    /* then */
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  public void testWithReader()
  {
    /* given */
    final InputStream inputStream = getClass().getResourceAsStream("/person.json");
    final PersonJson expected =
      new PersonJson(1L, "Kevin", "kevin@some-email.com", true, new Address(streetList.get(0), suburbList.get(0),
          cityList.get(0), stateList.get(0), postcodeList.get(0)));

    /* when */
    final PersonJson actual = jsonStatham.convertFromJson(PersonJson.class, new InputStreamReader(inputStream));

    /* then */
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  public void testWithReader2()
  {
    /* given */
    final InputStream inputStream = getClass().getResourceAsStream("/people.json");
    /* @formatter:off */
    int i = 0;
    final List<PersonJson> expected =
      Arrays.asList(
          new PersonJson(1L, "Kevin", "kevin@some-email.com", true,
                         new Address(streetList.get(i),
                                     suburbList.get(i),
                                     cityList.get(i),
                                     stateList.get(i),
                                     postcodeList.get(i++))),
          new PersonJson(2L, "Tom", "tom@another-email.com", false,
                         new Address(streetList.get(i),
                                     suburbList.get(i),
                                     cityList.get(i),
                                     stateList.get(i),
                                     postcodeList.get(i++))),
          new PersonJson(3L, "John", "john.doe@test-email.com", true,
                         new Address(streetList.get(i),
                                     suburbList.get(i),
                                     cityList.get(i),
                                     stateList.get(i),
                                     postcodeList.get(i++))),
          new PersonJson(4L, "Mark", "mk@aaaa.bbb.com", true,
                         new Address(streetList.get(i),
                                     suburbList.get(i),
                                     cityList.get(i),
                                     stateList.get(i),
                                     postcodeList.get(i++))),
          new PersonJson(5L, "Sean", "sean@abcde.com", false,
                         new Address(streetList.get(i),
                                     suburbList.get(i),
                                     cityList.get(i),
                                     stateList.get(i),
                                     postcodeList.get(i++))));
    /* @formatter:on */

    /* when */
    final List<PersonJson> actual =
      Arrays.asList(jsonStatham.convertFromJson(PersonJson[].class, new InputStreamReader(inputStream)));

    /* then */
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  public void testWithReader3()
  {
    /* given */
    final InputStream inputStream = getClass().getResourceAsStream("/person.json");
    final PersonJson expected =
      new PersonJson(1L, "Kevin", "kevin@some-email.com", true, new Address(streetList.get(0), suburbList.get(0),
          cityList.get(0), stateList.get(0), postcodeList.get(0)));
    final JsonStatham jsonStatham = ReflectionJsonStathams.newReflectionJsonStathamInAction();

    /* when */
    final PersonJson actual = jsonStatham.convertFromJson(PersonJson.class, new InputStreamReader(inputStream));

    /* then */
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  public void testWithReader4()
  {
    /* given */
    final InputStream inputStream = getClass().getResourceAsStream("/people.json");
    /* @formatter:off */
    int i = 0;
    final List<PersonJson> expected =
      Arrays.asList(
          new PersonJson(1L, "Kevin", "kevin@some-email.com", true,
                         new Address(streetList.get(i),
                                     suburbList.get(i),
                                     cityList.get(i),
                                     stateList.get(i),
                                     postcodeList.get(i++))),
          new PersonJson(2L, "Tom", "tom@another-email.com", false,
                         new Address(streetList.get(i),
                                     suburbList.get(i),
                                     cityList.get(i),
                                     stateList.get(i),
                                     postcodeList.get(i++))),
          new PersonJson(3L, "John", "john.doe@test-email.com", true,
                         new Address(streetList.get(i),
                                     suburbList.get(i),
                                     cityList.get(i),
                                     stateList.get(i),
                                     postcodeList.get(i++))),
          new PersonJson(4L, "Mark", "mk@aaaa.bbb.com", true,
                         new Address(streetList.get(i),
                                     suburbList.get(i),
                                     cityList.get(i),
                                     stateList.get(i),
                                     postcodeList.get(i++))),
          new PersonJson(5L, "Sean", "sean@abcde.com", false,
                         new Address(streetList.get(i),
                                     suburbList.get(i),
                                     cityList.get(i),
                                     stateList.get(i),
                                     postcodeList.get(i++))));
    /* @formatter:on */
    final JsonStatham jsonStatham = ReflectionJsonStathams.newReflectionJsonStathamInAction();

    /* when */
    final List<PersonJson> actual =
      Arrays.asList(jsonStatham.convertFromJson(PersonJson[].class, new InputStreamReader(inputStream)));

    /* then */
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  public void testWithCharReadable()
  {
    /* given */
    final InputStream inputStream = getClass().getResourceAsStream("/person.json");
    final PersonJson expected =
      new PersonJson(1L, "Kevin", "kevin@some-email.com", true, new Address(streetList.get(0), suburbList.get(0),
          cityList.get(0), stateList.get(0), postcodeList.get(0)));

    /* when */
    final PersonJson actual =
      jsonStatham.convertFromJson(PersonJson.class, new CharReadableFromInputStream(inputStream));

    /* then */
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  public void testWithCharReadable2()
  {
    /* given */
    final InputStream inputStream = getClass().getResourceAsStream("/people.json");
    /* @formatter:off */
    int i = 0;
    final List<PersonJson> expected =
      Arrays.asList(
          new PersonJson(1L, "Kevin", "kevin@some-email.com", true,
                         new Address(streetList.get(i),
                                     suburbList.get(i),
                                     cityList.get(i),
                                     stateList.get(i),
                                     postcodeList.get(i++))),
          new PersonJson(2L, "Tom", "tom@another-email.com", false,
                         new Address(streetList.get(i),
                                     suburbList.get(i),
                                     cityList.get(i),
                                     stateList.get(i),
                                     postcodeList.get(i++))),
          new PersonJson(3L, "John", "john.doe@test-email.com", true,
                         new Address(streetList.get(i),
                                     suburbList.get(i),
                                     cityList.get(i),
                                     stateList.get(i),
                                     postcodeList.get(i++))),
          new PersonJson(4L, "Mark", "mk@aaaa.bbb.com", true,
                         new Address(streetList.get(i),
                                     suburbList.get(i),
                                     cityList.get(i),
                                     stateList.get(i),
                                     postcodeList.get(i++))),
          new PersonJson(5L, "Sean", "sean@abcde.com", false,
                         new Address(streetList.get(i),
                                     suburbList.get(i),
                                     cityList.get(i),
                                     stateList.get(i),
                                     postcodeList.get(i++))));
    /* @formatter:on */

    /* when */
    final List<PersonJson> actual =
      Arrays.asList(jsonStatham.convertFromJson(PersonJson[].class, new CharReadableFromInputStream(inputStream)));

    /* then */
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  public void testWithCharReadable3()
  {
    /* given */
    final InputStream inputStream = getClass().getResourceAsStream("/person.json");
    final PersonJson expected =
      new PersonJson(1L, "Kevin", "kevin@some-email.com", true, new Address(streetList.get(0), suburbList.get(0),
          cityList.get(0), stateList.get(0), postcodeList.get(0)));
    final JsonStatham jsonStatham = ReflectionJsonStathams.newReflectionJsonStathamInAction();

    /* when */
    final PersonJson actual =
      jsonStatham.convertFromJson(PersonJson.class, new CharReadableFromInputStream(inputStream));

    /* then */
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  public void testWithCharReadable4()
  {
    /* given */
    final InputStream inputStream = getClass().getResourceAsStream("/people.json");
    /* @formatter:off */
    int i = 0;
    final List<PersonJson> expected =
      Arrays.asList(
          new PersonJson(1L, "Kevin", "kevin@some-email.com", true,
                         new Address(streetList.get(i),
                                     suburbList.get(i),
                                     cityList.get(i),
                                     stateList.get(i),
                                     postcodeList.get(i++))),
          new PersonJson(2L, "Tom", "tom@another-email.com", false,
                         new Address(streetList.get(i),
                                     suburbList.get(i),
                                     cityList.get(i),
                                     stateList.get(i),
                                     postcodeList.get(i++))),
          new PersonJson(3L, "John", "john.doe@test-email.com", true,
                         new Address(streetList.get(i),
                                     suburbList.get(i),
                                     cityList.get(i),
                                     stateList.get(i),
                                     postcodeList.get(i++))),
          new PersonJson(4L, "Mark", "mk@aaaa.bbb.com", true,
                         new Address(streetList.get(i),
                                     suburbList.get(i),
                                     cityList.get(i),
                                     stateList.get(i),
                                     postcodeList.get(i++))),
          new PersonJson(5L, "Sean", "sean@abcde.com", false,
                         new Address(streetList.get(i),
                                     suburbList.get(i),
                                     cityList.get(i),
                                     stateList.get(i),
                                     postcodeList.get(i++))));
    /* @formatter:on */
    final JsonStatham jsonStatham = ReflectionJsonStathams.newReflectionJsonStathamInAction();

    /* when */
    final List<PersonJson> actual =
      Arrays.asList(jsonStatham.convertFromJson(PersonJson[].class, new CharReadableFromInputStream(inputStream)));

    /* then */
    assertThat(actual).isEqualTo(expected);
  }
}

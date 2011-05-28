/**
 * 
 */
package org.elixirian.jsonstatham.core.reflect.java2json;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableSet;
import java.util.TreeSet;

import org.elixirian.jsonstatham.core.KnownTypeProcessorWithReflectionJavaToJsonConverter;
import org.elixirian.jsonstatham.core.convertible.JsonArrayConvertible;
import org.elixirian.jsonstatham.core.convertible.JsonObjectConvertible;
import org.elixirian.jsonstatham.core.convertible.OrgJsonJsonArrayConvertibleCreator;
import org.elixirian.jsonstatham.core.convertible.OrgJsonOrderedJsonObjectConvertibleCreator;
import org.elixirian.jsonstatham.core.reflect.java2json.KnownDataStructureTypeProcessorDecider;
import org.elixirian.jsonstatham.core.reflect.java2json.KnownObjectReferenceTypeProcessorDecider;
import org.elixirian.jsonstatham.core.reflect.java2json.OneProcessorForKnownTypeDecider;
import org.elixirian.jsonstatham.core.reflect.java2json.ReflectionJavaToJsonConverter;
import org.elixirian.jsonstatham.exception.JsonStathamException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;


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
 * @version 0.0.1 (2010-06-11)
 */
public class KnownDataStructureTypeProcessorDeciderTest
{
  private static String[] strings = { null, "Kevin", "Lee", "test", "string" };
  private static int[] ints = { Integer.MIN_VALUE, Integer.MAX_VALUE, 0, -1, 1 };
  private static Integer[] integers = { Integer.valueOf(Integer.MIN_VALUE), Integer.valueOf(Integer.MAX_VALUE),
      Integer.valueOf(0), Integer.valueOf(-1), Integer.valueOf(1) };
  private static Collection<String> collection1 = Arrays.asList(strings);
  private static Collection<Integer> collection2 = Arrays.asList(integers);
  private static Iterable<String> iterable1 = collection1;
  private static Iterable<Integer> iterable2 = collection2;
  private static Iterator<String> iterator1 = collection1.iterator();
  private static Iterator<Integer> iterator2 = collection2.iterator();
  private static Map<Long, String> map = new HashMap<Long, String>();

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
    strings = new String[] { null, "Kevin", "Lee", "test", "string" };
    ints = new int[] { Integer.MIN_VALUE, Integer.MAX_VALUE, 0, -1, 1 };
    integers =
      new Integer[] { Integer.valueOf(Integer.MIN_VALUE), Integer.valueOf(Integer.MAX_VALUE), Integer.valueOf(0),
          Integer.valueOf(-1), Integer.valueOf(1) };
    collection1 = Arrays.asList(strings);
    collection2 = Arrays.asList(integers);
    iterable1 = collection1;
    iterable2 = collection2;
    iterator1 = collection1.iterator();
    iterator2 = collection2.iterator();
    map = new HashMap<Long, String>();
    map.put(Long.valueOf(1L), "Kevin");
    map.put(Long.valueOf(2L), "Lee");
    map.put(Long.valueOf(3L), "Tom");
    map.put(Long.valueOf(4L), "Peter");
    map.put(Long.valueOf(5L), "Steve");
  }

  /**
   * @throws java.lang.Exception
   */
  @After
  public void tearDown() throws Exception
  {
  }

  /**
   * Test method for
   * {@link org.elixirian.jsonstatham.core.reflect.java2json.KnownDataStructureTypeProcessorDecider#KnownDataStructureTypeProcessorDecider()}
   * .
   * 
   * @throws IllegalAccessException
   * @throws JsonStathamException
   * @throws IllegalArgumentException
   */
  @Test
  public final void testKnownDataStructureTypeProcessorDecider() throws IllegalArgumentException, JsonStathamException,
      IllegalAccessException
  {
    final KnownDataStructureTypeProcessorDecider knownDataStructureTypeProcessorDecider =
      new KnownDataStructureTypeProcessorDecider();

    final KnownObjectReferenceTypeProcessorDecider knownObjectReferenceTypeProcessorDecider =
      mock(KnownObjectReferenceTypeProcessorDecider.class);
    when(knownObjectReferenceTypeProcessorDecider.decide(Mockito.any(Class.class))).thenReturn(null);

    final OneProcessorForKnownTypeDecider oneProcessorForKnownTypeDecider = mock(OneProcessorForKnownTypeDecider.class);
    when(oneProcessorForKnownTypeDecider.decide(Mockito.any(Class.class))).thenReturn(
        new KnownTypeProcessorWithReflectionJavaToJsonConverter() {
          @Override
          public <T> Object process(
              @SuppressWarnings("unused") ReflectionJavaToJsonConverter reflectionJavaToJsonConverter,
              Class<T> valueType, Object source) throws IllegalArgumentException, IllegalAccessException,
              JsonStathamException
          {
            return source;
          }
        });

    // final JsonStathamInAction jsonStatham =
    // new JsonStathamInAction(new ReflectionJavaToJsonConverter(new OrgJsonOrderedJsonObjectConvertibleCreator(),
    // new OrgJsonJsonArrayConvertibleCreator(), knownDataStructureTypeProcessorDecider,
    // knownObjectReferenceTypeProcessorDecider, oneProcessorForKnownTypeDecider), new
    // ReflectionJsonToJavaConverter());

    assertThat(knownDataStructureTypeProcessorDecider.decide(strings.getClass()), is(not(nullValue())));
    assertThat(knownDataStructureTypeProcessorDecider.decide(ints.getClass()), is(not(nullValue())));
    assertThat(knownDataStructureTypeProcessorDecider.decide(integers.getClass()), is(not(nullValue())));

    assertThat(knownDataStructureTypeProcessorDecider.decide(collection1.getClass()), is(not(nullValue())));
    assertThat(knownDataStructureTypeProcessorDecider.decide(collection2.getClass()), is(not(nullValue())));
    assertThat(knownDataStructureTypeProcessorDecider.decide(iterable1.getClass()), is(not(nullValue())));
    assertThat(knownDataStructureTypeProcessorDecider.decide(iterable2.getClass()), is(not(nullValue())));
    assertThat(knownDataStructureTypeProcessorDecider.decide(iterator1.getClass()), is(not(nullValue())));
    assertThat(knownDataStructureTypeProcessorDecider.decide(iterator2.getClass()), is(not(nullValue())));
    assertThat(knownDataStructureTypeProcessorDecider.decide(map.getClass()), is(not(nullValue())));
  }

  /**
   * Test method for
   * {@link org.elixirian.jsonstatham.core.reflect.java2json.KnownDataStructureTypeProcessorDecider#KnownDataStructureTypeProcessorDecider(java.util.Map)}
   * .
   * 
   * @throws IllegalAccessException
   * @throws JsonStathamException
   * @throws IllegalArgumentException
   */
  @Test
  public final void testKnownDataStructureTypeProcessorDeciderMapOfClassOfQKnownTypeProcessor()
      throws IllegalArgumentException, JsonStathamException, IllegalAccessException
  {
    final Map<Class<?>, KnownTypeProcessorWithReflectionJavaToJsonConverter> knownDataStructuresProcessorMap =
      new HashMap<Class<?>, KnownTypeProcessorWithReflectionJavaToJsonConverter>();
    knownDataStructuresProcessorMap.put(NavigableSet.class, new KnownTypeProcessorWithReflectionJavaToJsonConverter() {
      @SuppressWarnings("unchecked")
      @Override
      public <T> Object process(
          @SuppressWarnings("unused") ReflectionJavaToJsonConverter reflectionJavaToJsonConverter, Class<T> valueType,
          Object source) throws IllegalArgumentException, IllegalAccessException, JsonStathamException
      {
        JsonArrayConvertible jsonArrayConvertible = reflectionJavaToJsonConverter.newJsonArrayConvertible();
        for (Object each : (NavigableSet<Object>) source)
        {
          jsonArrayConvertible.put(each);
        }
        return jsonArrayConvertible;
      }
    });
    final KnownDataStructureTypeProcessorDecider knownDataStructureTypeProcessorDecider =
      new KnownDataStructureTypeProcessorDecider(knownDataStructuresProcessorMap);

    final KnownObjectReferenceTypeProcessorDecider knownObjectReferenceTypeProcessorDecider =
      mock(KnownObjectReferenceTypeProcessorDecider.class);
    when(knownObjectReferenceTypeProcessorDecider.decide(Mockito.any(Class.class))).thenReturn(null);

    final OneProcessorForKnownTypeDecider oneProcessorForKnownTypeDecider = mock(OneProcessorForKnownTypeDecider.class);
    when(oneProcessorForKnownTypeDecider.decide(Mockito.any(Class.class))).thenReturn(
        new KnownTypeProcessorWithReflectionJavaToJsonConverter() {
          @Override
          public <T> Object process(
              @SuppressWarnings("unused") ReflectionJavaToJsonConverter reflectionJavaToJsonConverter,
              Class<T> valueType, Object source) throws IllegalArgumentException, IllegalAccessException,
              JsonStathamException
          {
            return source;
          }
        });

    final ReflectionJavaToJsonConverter reflectionJavaToJsonConverter =
      new ReflectionJavaToJsonConverter(new OrgJsonOrderedJsonObjectConvertibleCreator(),
          new OrgJsonJsonArrayConvertibleCreator(), knownDataStructureTypeProcessorDecider,
          knownObjectReferenceTypeProcessorDecider, oneProcessorForKnownTypeDecider);
    // final JsonStathamInAction jsonStatham = new JsonStathamInAction(reflectionJavaToJsonConverter, new
    // ReflectionJsonToJavaConverter());

    final NavigableSet<String> testSet = new TreeSet<String>();
    testSet.add("Hello");
    testSet.add("Kevin");
    testSet.add("Lee");

    JsonArrayConvertible jsonArrayConvertible = reflectionJavaToJsonConverter.newJsonArrayConvertible();
    for (String each : testSet)
    {
      jsonArrayConvertible.put(reflectionJavaToJsonConverter.createJsonValue(each));
    }
    assertThat(knownDataStructureTypeProcessorDecider.decide(testSet.getClass())
        .process(reflectionJavaToJsonConverter, testSet.getClass(), testSet)
        .toString(), equalTo(jsonArrayConvertible.toString()));

    assertThat(knownDataStructureTypeProcessorDecider.decide(strings.getClass()), is(nullValue()));
    assertThat(knownDataStructureTypeProcessorDecider.decide(ints.getClass()), is(nullValue()));
    assertThat(knownDataStructureTypeProcessorDecider.decide(integers.getClass()), is(nullValue()));

    assertThat(knownDataStructureTypeProcessorDecider.decide(collection1.getClass()), is(nullValue()));
    assertThat(knownDataStructureTypeProcessorDecider.decide(collection2.getClass()), is(nullValue()));
    assertThat(knownDataStructureTypeProcessorDecider.decide(iterable1.getClass()), is(nullValue()));
    assertThat(knownDataStructureTypeProcessorDecider.decide(iterable2.getClass()), is(nullValue()));
    assertThat(knownDataStructureTypeProcessorDecider.decide(iterator1.getClass()), is(nullValue()));
    assertThat(knownDataStructureTypeProcessorDecider.decide(iterator2.getClass()), is(nullValue()));
    assertThat(knownDataStructureTypeProcessorDecider.decide(map.getClass()), is(nullValue()));
  }

  /**
   * Test method for
   * {@link org.elixirian.jsonstatham.core.reflect.java2json.KnownDataStructureTypeProcessorDecider#decide(java.lang.Class)}
   * .
   * 
   * @throws IllegalAccessException
   * @throws JsonStathamException
   * @throws IllegalArgumentException
   */
  @Test
  public final void testDecide() throws IllegalArgumentException, JsonStathamException, IllegalAccessException
  {
    final KnownDataStructureTypeProcessorDecider knownDataStructureTypeProcessorDecider =
      new KnownDataStructureTypeProcessorDecider();

    final KnownObjectReferenceTypeProcessorDecider knownObjectReferenceTypeProcessorDecider =
      mock(KnownObjectReferenceTypeProcessorDecider.class);
    when(knownObjectReferenceTypeProcessorDecider.decide(Mockito.any(Class.class))).thenReturn(null);

    final OneProcessorForKnownTypeDecider oneProcessorForKnownTypeDecider = mock(OneProcessorForKnownTypeDecider.class);
    when(oneProcessorForKnownTypeDecider.decide(Mockito.any(Class.class))).thenReturn(
        new KnownTypeProcessorWithReflectionJavaToJsonConverter() {
          @Override
          public <T> Object process(
              @SuppressWarnings("unused") ReflectionJavaToJsonConverter reflectionJavaToJsonConverter,
              Class<T> valueType, Object source) throws IllegalArgumentException, IllegalAccessException,
              JsonStathamException
          {
            return source;
          }
        });

    final ReflectionJavaToJsonConverter reflectionJavaToJsonConverter =
      new ReflectionJavaToJsonConverter(new OrgJsonOrderedJsonObjectConvertibleCreator(),
          new OrgJsonJsonArrayConvertibleCreator(), knownDataStructureTypeProcessorDecider,
          knownObjectReferenceTypeProcessorDecider, oneProcessorForKnownTypeDecider);
    // final JsonStathamInAction jsonStatham =
    // new JsonStathamInAction(reflectionJavaToJsonConverter, new ReflectionJsonToJavaConverter());

    JsonArrayConvertible jsonArrayConvertible = reflectionJavaToJsonConverter.newJsonArrayConvertible();
    for (String each : strings)
    {
      jsonArrayConvertible.put(reflectionJavaToJsonConverter.createJsonValue(each));
    }
    assertThat(knownDataStructureTypeProcessorDecider.decide(strings.getClass())
        .process(reflectionJavaToJsonConverter, strings.getClass(), strings)
        .toString(), equalTo(jsonArrayConvertible.toString()));

    jsonArrayConvertible = reflectionJavaToJsonConverter.newJsonArrayConvertible();
    for (int each : ints)
    {
      jsonArrayConvertible.put(reflectionJavaToJsonConverter.createJsonValue(each));
    }
    assertThat(knownDataStructureTypeProcessorDecider.decide(ints.getClass())
        .process(reflectionJavaToJsonConverter, ints.getClass(), ints)
        .toString(), equalTo(jsonArrayConvertible.toString()));

    jsonArrayConvertible = reflectionJavaToJsonConverter.newJsonArrayConvertible();
    for (Integer each : integers)
    {
      jsonArrayConvertible.put(reflectionJavaToJsonConverter.createJsonValue(each));
    }
    assertThat(knownDataStructureTypeProcessorDecider.decide(integers.getClass())
        .process(reflectionJavaToJsonConverter, integers.getClass(), integers)
        .toString(), equalTo(jsonArrayConvertible.toString()));

    jsonArrayConvertible = reflectionJavaToJsonConverter.newJsonArrayConvertible();
    for (String each : collection1)
    {
      jsonArrayConvertible.put(reflectionJavaToJsonConverter.createJsonValue(each));
    }
    assertThat(knownDataStructureTypeProcessorDecider.decide(collection1.getClass())
        .process(reflectionJavaToJsonConverter, collection1.getClass(), collection1)
        .toString(), equalTo(jsonArrayConvertible.toString()));

    jsonArrayConvertible = reflectionJavaToJsonConverter.newJsonArrayConvertible();
    for (Integer each : collection2)
    {
      jsonArrayConvertible.put(reflectionJavaToJsonConverter.createJsonValue(each));
    }
    assertThat(knownDataStructureTypeProcessorDecider.decide(collection2.getClass())
        .process(reflectionJavaToJsonConverter, collection2.getClass(), collection2)
        .toString(), equalTo(jsonArrayConvertible.toString()));

    jsonArrayConvertible = reflectionJavaToJsonConverter.newJsonArrayConvertible();
    for (String each : iterable1)
    {
      jsonArrayConvertible.put(reflectionJavaToJsonConverter.createJsonValue(each));
    }
    assertThat(knownDataStructureTypeProcessorDecider.decide(iterable1.getClass())
        .process(reflectionJavaToJsonConverter, iterable1.getClass(), iterable1)
        .toString(), equalTo(jsonArrayConvertible.toString()));

    jsonArrayConvertible = reflectionJavaToJsonConverter.newJsonArrayConvertible();
    for (Integer each : iterable2)
    {
      jsonArrayConvertible.put(reflectionJavaToJsonConverter.createJsonValue(each));
    }
    assertThat(knownDataStructureTypeProcessorDecider.decide(iterable2.getClass())
        .process(reflectionJavaToJsonConverter, iterable2.getClass(), iterable2)
        .toString(), equalTo(jsonArrayConvertible.toString()));

    jsonArrayConvertible = reflectionJavaToJsonConverter.newJsonArrayConvertible();
    for (Iterator<String> it = iterable1.iterator(); it.hasNext();)
    {
      jsonArrayConvertible.put(reflectionJavaToJsonConverter.createJsonValue(it.next()));
    }
    assertThat(knownDataStructureTypeProcessorDecider.decide(iterator1.getClass())
        .process(reflectionJavaToJsonConverter, iterable1.getClass(), iterator1)
        .toString(), equalTo(jsonArrayConvertible.toString()));

    jsonArrayConvertible = reflectionJavaToJsonConverter.newJsonArrayConvertible();
    for (Iterator<Integer> it = iterable2.iterator(); it.hasNext();)
    {
      jsonArrayConvertible.put(reflectionJavaToJsonConverter.createJsonValue(it.next()));
    }
    assertThat(knownDataStructureTypeProcessorDecider.decide(iterator2.getClass())
        .process(reflectionJavaToJsonConverter, iterator2.getClass(), iterator2)
        .toString(), equalTo(jsonArrayConvertible.toString()));

    final JsonObjectConvertible jsonObjectConvertible = reflectionJavaToJsonConverter.newJsonObjectConvertible();
    for (Entry<Long, String> entry : map.entrySet())
    {
      jsonObjectConvertible.put(String.valueOf(entry.getKey()), entry.getValue());
    }
    assertThat(knownDataStructureTypeProcessorDecider.decide(map.getClass())
        .process(reflectionJavaToJsonConverter, map.getClass(), map)
        .toString(), equalTo(jsonObjectConvertible.toString()));
  }
}

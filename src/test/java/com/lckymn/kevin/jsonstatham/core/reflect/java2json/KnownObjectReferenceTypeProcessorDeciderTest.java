/**
 * 
 */
package com.lckymn.kevin.jsonstatham.core.reflect.java2json;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;
import org.mockito.Mockito;

import com.lckymn.kevin.jsonstatham.core.KnownTypeProcessorDeciderForJavaToJson;
import com.lckymn.kevin.jsonstatham.core.KnownTypeProcessorWithReflectionJavaToJsonConverter;
import com.lckymn.kevin.jsonstatham.core.convertible.OrgJsonJsonArrayConvertibleCreator;
import com.lckymn.kevin.jsonstatham.core.convertible.OrgJsonOrderedJsonObjectConvertibleCreator;
import com.lckymn.kevin.jsonstatham.exception.JsonStathamException;

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
public class KnownObjectReferenceTypeProcessorDeciderTest
{
  private static final Date DATE = new Date();
  private static final Calendar CALENDAR = Calendar.getInstance();
  private static final Map<String, String> MAP = new HashMap<String, String>();

  /**
   * Test method for
   * {@link com.lckymn.kevin.jsonstatham.core.reflect.java2json.KnownObjectReferenceTypeProcessorDecider#KnownObjectReferenceTypeProcessorDecider()}
   * .
   */
  @Test
  public final void testKnownObjectReferenceTypeProcessorDecider()
  {
    final KnownTypeProcessorDeciderForJavaToJson knownTypeProcessorDeciderForJavaToJson =
      new KnownObjectReferenceTypeProcessorDecider();
    assertThat(knownTypeProcessorDeciderForJavaToJson.decide(DATE.getClass()), is(not(nullValue())));
    assertThat(knownTypeProcessorDeciderForJavaToJson.decide(CALENDAR.getClass()), is(not(nullValue())));

    for (Entry<String, String> entry : MAP.entrySet())
    {
      assertThat(knownTypeProcessorDeciderForJavaToJson.decide(entry.getClass()), is(not(nullValue())));
    }

    assertThat(knownTypeProcessorDeciderForJavaToJson.decide(new ArrayList<String>().getClass()), is(nullValue()));
  }

  /**
   * Test method for
   * {@link com.lckymn.kevin.jsonstatham.core.reflect.java2json.KnownObjectReferenceTypeProcessorDecider#KnownObjectReferenceTypeProcessorDecider(java.util.Map)}
   * .
   * 
   * @throws IllegalAccessException
   * @throws JsonStathamException
   * @throws IllegalArgumentException
   */
  @Test
  public final void testKnownObjectReferenceTypeProcessorDeciderMapOfClassOfQKnownTypeProcessor()
      throws IllegalArgumentException, JsonStathamException, IllegalAccessException
  {
    class TestClass
    {
      private final Long id;
      private final String name;

      public TestClass(Long id, String name)
      {
        this.id = id;
        this.name = name;
      }
    }

    final TestClass testClass = new TestClass(Long.valueOf(999L), "Kevin");

    final Map<Class<?>, KnownTypeProcessorWithReflectionJavaToJsonConverter> map =
      new HashMap<Class<?>, KnownTypeProcessorWithReflectionJavaToJsonConverter>();
    map.put(TestClass.class, new KnownTypeProcessorWithReflectionJavaToJsonConverter() {
      @Override
      public <T> Object process(
          @SuppressWarnings("unused") ReflectionJavaToJsonConverter reflectionJavaToJsonConverter,
          @SuppressWarnings("unused") final Class<T> valueType, Object value) throws IllegalArgumentException,
          IllegalAccessException, JsonStathamException
      {
        final TestClass testClassObject = (TestClass) value;
        return "id: " + testClassObject.id + " | name: " + testClassObject.name;
      }
    });
    final KnownTypeProcessorDeciderForJavaToJson knownTypeProcessorDeciderForJavaToJson =
      new KnownObjectReferenceTypeProcessorDecider(map);

    assertThat(knownTypeProcessorDeciderForJavaToJson.decide(testClass.getClass()), is(not(nullValue())));
    assertThat(knownTypeProcessorDeciderForJavaToJson.decide(testClass.getClass())
        .process(null, testClass.getClass(), testClass),
        equalTo((Object) ("id: " + testClass.id + " | name: " + testClass.name)));

    assertThat(knownTypeProcessorDeciderForJavaToJson.decide(DATE.getClass()), is(nullValue()));
    assertThat(knownTypeProcessorDeciderForJavaToJson.decide(CALENDAR.getClass()), is(nullValue()));

    for (Entry<String, String> entry : MAP.entrySet())
    {
      assertThat(knownTypeProcessorDeciderForJavaToJson.decide(entry.getClass()), is(nullValue()));
    }
  }

  /**
   * Test method for
   * {@link com.lckymn.kevin.jsonstatham.core.reflect.java2json.KnownObjectReferenceTypeProcessorDecider#decide(java.lang.Class)}
   * .
   * 
   * @throws IllegalAccessException
   * @throws JsonStathamException
   * @throws IllegalArgumentException
   */
  @SuppressWarnings("unchecked")
  @Test
  public final void testDecide() throws IllegalArgumentException, JsonStathamException, IllegalAccessException
  {
    final KnownDataStructureTypeProcessorDecider knownDataStructureTypeProcessorDecider =
      mock(KnownDataStructureTypeProcessorDecider.class);
    when(knownDataStructureTypeProcessorDecider.decide(Mockito.any(Class.class))).thenReturn(null);
    final OneProcessorForKnownTypeDecider oneProcessorForKnownTypeDecider = mock(OneProcessorForKnownTypeDecider.class);
    when(oneProcessorForKnownTypeDecider.decide(String.class)).thenReturn(
        new KnownTypeProcessorWithReflectionJavaToJsonConverter() {
          @Override
          public <T> Object process(
              @SuppressWarnings("unused") ReflectionJavaToJsonConverter reflectionJavaToJsonConverter,
              @SuppressWarnings("unused") final Class<T> valueType, Object value) throws IllegalArgumentException,
              IllegalAccessException, JsonStathamException
          {
            return value;
          }
        });

    final ReflectionJavaToJsonConverter reflectionJavaToJsonConverter =
      new ReflectionJavaToJsonConverter(new OrgJsonOrderedJsonObjectConvertibleCreator(),
          new OrgJsonJsonArrayConvertibleCreator(), knownDataStructureTypeProcessorDecider,
          new KnownObjectReferenceTypeProcessorDecider(), oneProcessorForKnownTypeDecider);
    // final JsonStathamInAction jsonStathamInAction =
    // new JsonStathamInAction(reflectionJavaToJsonConverter, new ReflectionJsonToJavaConverter());
    final KnownTypeProcessorDeciderForJavaToJson knownTypeProcessorDeciderForJavaToJson =
      new KnownObjectReferenceTypeProcessorDecider();

    assertThat(knownTypeProcessorDeciderForJavaToJson.decide(DATE.getClass())
        .process(reflectionJavaToJsonConverter, DATE.getClass(), DATE),
        equalTo(reflectionJavaToJsonConverter.createJsonValue(DATE.toString())));

    assertThat(knownTypeProcessorDeciderForJavaToJson.decide(CALENDAR.getClass())
        .process(reflectionJavaToJsonConverter, CALENDAR.getClass(), CALENDAR),
        equalTo(reflectionJavaToJsonConverter.createJsonValue(CALENDAR.getTime()
            .toString())));

    for (Entry<String, String> entry : MAP.entrySet())
    {
      assertThat(knownTypeProcessorDeciderForJavaToJson.decide(entry.getClass())
          .process(reflectionJavaToJsonConverter, entry.getClass(), entry)
          .toString(), equalTo(reflectionJavaToJsonConverter.newJsonObjectConvertible()
          .put(entry.getKey(), reflectionJavaToJsonConverter.createJsonValue(entry.getValue()))
          .toString()));
    }

    assertThat(knownTypeProcessorDeciderForJavaToJson.decide(new ArrayList<String>().getClass()), is(nullValue()));
  }
}

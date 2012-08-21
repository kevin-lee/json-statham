/**
 *
 */
package org.elixirian.jsonstatham.core.reflect;

import static org.elixirian.kommonlee.test.CommonTestHelper.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;

import org.elixirian.jsonstatham.core.JsonStathamInAction;
import org.elixirian.jsonstatham.core.KnownTypeProcessorDeciderForJavaToJson;
import org.elixirian.jsonstatham.core.convertible.JsonArrayCreator;
import org.elixirian.jsonstatham.core.convertible.JsonArrayWithOrderedJsonObjectCreator;
import org.elixirian.jsonstatham.core.convertible.JsonArrayWithUnorderedJsonObjectCreator;
import org.elixirian.jsonstatham.core.convertible.JsonObjectCreator;
import org.elixirian.jsonstatham.core.convertible.OrderedJsonObjectCreator;
import org.elixirian.jsonstatham.core.convertible.UnorderedJsonObjectCreator;
import org.elixirian.jsonstatham.core.reflect.java2json.KnownDataStructureTypeProcessorDecider;
import org.elixirian.jsonstatham.core.reflect.java2json.KnownObjectReferenceTypeProcessorDecider;
import org.elixirian.jsonstatham.core.reflect.java2json.OneProcessorForKnownTypeDecider;
import org.elixirian.jsonstatham.core.reflect.java2json.ReflectionJavaToJsonConverter;
import org.elixirian.jsonstatham.core.reflect.json2java.DefaultJsonToJavaConfig;
import org.elixirian.jsonstatham.core.reflect.json2java.ReflectionJsonToJavaConverter;
import org.elixirian.kommonlee.test.CommonTestHelper.Accessibility;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
 * @version 0.0.1 (2010-06-14)
 */
public class ReflectionJsonStathamsTest
{

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
  }

  /**
   * @throws java.lang.Exception
   */
  @After
  public void tearDown() throws Exception
  {
  }

  @Test(expected = IllegalAccessException.class)
  public void testReflectionJsonStathams() throws Exception
  {
    testNotAccessibleConstructor(ReflectionJsonStathams.class, this, Accessibility.PRIVATE, classArrayOf(),
        objectArrayOf());
  }

  /**
   * Test method for
   * {@link org.elixirian.jsonstatham.core.reflect.ReflectionJsonStathams#newJsonStathamInAction(org.elixirian.jsonstatham.core.convertible.JsonObjectCreator, org.elixirian.jsonstatham.core.convertible.JsonArrayCreator, org.elixirian.jsonstatham.core.reflect.java2json.KnownDataStructureTypeProcessorDecider, org.elixirian.jsonstatham.core.reflect.java2json.KnownObjectReferenceTypeProcessorDecider, org.elixirian.jsonstatham.core.reflect.java2json.OneProcessorForKnownTypeDecider)}
   * .
   */
  @Test
  public final void testNewJsonStathamInActionJsonObjectConvertibleCreatorJsonArrayConvertibleCreatorKnownDataStructureTypeProcessorDeciderKnownObjectReferenceTypeProcessorDeciderOneProcessorForKnownTypeDecider()
  {
    final JsonObjectCreator jsonObjectCreator = mock(JsonObjectCreator.class);
    final JsonArrayCreator jsonArrayCreator = mock(JsonArrayCreator.class);
    final KnownDataStructureTypeProcessorDecider knownDataStructureTypeProcessorDecider =
      mock(KnownDataStructureTypeProcessorDecider.class);
    final KnownObjectReferenceTypeProcessorDecider knownObjectReferenceTypeProcessorDecider =
      mock(KnownObjectReferenceTypeProcessorDecider.class);
    final OneProcessorForKnownTypeDecider oneProcessorForKnownTypeDecider = mock(OneProcessorForKnownTypeDecider.class);

    final ReflectionJavaToJsonConverter reflectionJavaToJsonConverter =
      new ReflectionJavaToJsonConverter(jsonObjectCreator, jsonArrayCreator,
          knownDataStructureTypeProcessorDecider, knownObjectReferenceTypeProcessorDecider,
          oneProcessorForKnownTypeDecider);
    final ReflectionJsonToJavaConverter reflectionJsonToJavaConverter =
      new ReflectionJsonToJavaConverter(DefaultJsonToJavaConfig.builder(jsonObjectCreator,
          jsonArrayCreator)
          .build());

    final JsonStathamInAction jsonStathamInAction =
      ReflectionJsonStathams.newJsonStathamInAction(reflectionJavaToJsonConverter, reflectionJsonToJavaConverter);

    assertThat(jsonStathamInAction.getJavaToJsonConverter(), is(instanceOf(ReflectionJavaToJsonConverter.class)));
    assertThat(jsonStathamInAction.getJsonToJavaConverter(), is(instanceOf(ReflectionJsonToJavaConverter.class)));

    final ReflectionJavaToJsonConverter reflectionJavaToJsonConverterFromJsonStathamInAction =
      (ReflectionJavaToJsonConverter) jsonStathamInAction.getJavaToJsonConverter();

    // TODO uncomment when ReflectionJsonToJavaConverter is done.
    // final ReflectionJsonToJavaConverter reflectionJsonToJavaConverterFromJsonStathamInAction =
    // (ReflectionJsonToJavaConverter) jsonStathamInAction.getJsonToJavaConverter();

    assertThat(reflectionJavaToJsonConverterFromJsonStathamInAction.getJsonObjectConvertibleCreator(),
        equalTo(jsonObjectCreator));
    assertThat(reflectionJavaToJsonConverterFromJsonStathamInAction.getJsonArrayConvertibleCreator(),
        equalTo(jsonArrayCreator));
    assertThat(reflectionJavaToJsonConverterFromJsonStathamInAction.getKnownDataStructureTypeProcessorDecider(),
        equalTo(knownDataStructureTypeProcessorDecider));
    assertTrue(Arrays.deepEquals(new KnownTypeProcessorDeciderForJavaToJson[] { knownDataStructureTypeProcessorDecider,
        knownObjectReferenceTypeProcessorDecider, oneProcessorForKnownTypeDecider },
        reflectionJavaToJsonConverterFromJsonStathamInAction.getKnownTypeProcessorDeciders()));
  }

  /**
   * Test method for
   * {@link org.elixirian.jsonstatham.core.reflect.ReflectionJsonStathams#newReflectionJsonStathamInAction()}.
   */
  @Test
  public final void testNewJsonStathamInAction()
  {
    final JsonStathamInAction jsonStathamInAction = ReflectionJsonStathams.newReflectionJsonStathamInAction();

    assertThat(jsonStathamInAction.getJavaToJsonConverter(), is(instanceOf(ReflectionJavaToJsonConverter.class)));
    assertThat(jsonStathamInAction.getJsonToJavaConverter(), is(instanceOf(ReflectionJsonToJavaConverter.class)));

    final ReflectionJavaToJsonConverter reflectionJavaToJsonConverterFromJsonStathamInAction =
      (ReflectionJavaToJsonConverter) jsonStathamInAction.getJavaToJsonConverter();

    // TODO uncomment when ReflectionJsonToJavaConverter is done.
    // final ReflectionJsonToJavaConverter reflectionJsonToJavaConverterFromJsonStathamInAction =
    // (ReflectionJsonToJavaConverter) jsonStathamInAction.getJsonToJavaConverter();

    // TODO remove it after testing.
    // assertThat(reflectionJavaToJsonConverterFromJsonStathamInAction.getJsonObjectConvertibleCreator(),
    // is((instanceOf(OrgJsonOrderedJsonObjectCreator.class))));
    assertThat(reflectionJavaToJsonConverterFromJsonStathamInAction.getJsonObjectConvertibleCreator(),
        is((instanceOf(OrderedJsonObjectCreator.class))));

    // TODO remove it after testing.
    // assertThat(reflectionJavaToJsonConverterFromJsonStathamInAction.getJsonArrayConvertibleCreator(),
    // is(instanceOf(OrgJsonJsonArrayCreator.class)));
    assertThat(reflectionJavaToJsonConverterFromJsonStathamInAction.getJsonArrayConvertibleCreator(),
        is(instanceOf(JsonArrayWithOrderedJsonObjectCreator.class)));

    assertThat(reflectionJavaToJsonConverterFromJsonStathamInAction.getKnownDataStructureTypeProcessorDecider(),
        is(instanceOf(KnownDataStructureTypeProcessorDecider.class)));

    final KnownTypeProcessorDeciderForJavaToJson[] knownTypeProcessorDeciders =
      reflectionJavaToJsonConverterFromJsonStathamInAction.getKnownTypeProcessorDeciders();
    assertEquals(3, knownTypeProcessorDeciders.length);
    assertThat(knownTypeProcessorDeciders[0], is(instanceOf(KnownDataStructureTypeProcessorDecider.class)));
    assertThat(knownTypeProcessorDeciders[1], is(instanceOf(KnownObjectReferenceTypeProcessorDecider.class)));
    assertThat(knownTypeProcessorDeciders[2], is(instanceOf(OneProcessorForKnownTypeDecider.class)));

  }

  /**
   * Test method for
   * {@link org.elixirian.jsonstatham.core.reflect.ReflectionJsonStathams#newUnorderedReflectionJsonStathamInAction()} .
   */
  @Test
  public final void testNewUnorderedReflectionJsonStathamInAction()
  {
    final JsonStathamInAction jsonStathamInAction = ReflectionJsonStathams.newUnorderedReflectionJsonStathamInAction();

    assertThat(jsonStathamInAction.getJavaToJsonConverter(), is(instanceOf(ReflectionJavaToJsonConverter.class)));
    assertThat(jsonStathamInAction.getJsonToJavaConverter(), is(instanceOf(ReflectionJsonToJavaConverter.class)));

    final ReflectionJavaToJsonConverter reflectionJavaToJsonConverterFromJsonStathamInAction =
      (ReflectionJavaToJsonConverter) jsonStathamInAction.getJavaToJsonConverter();

    // TODO uncomment when ReflectionJsonToJavaConverter is done.
    // final ReflectionJsonToJavaConverter reflectionJsonToJavaConverterFromJsonStathamInAction =
    // (ReflectionJsonToJavaConverter) jsonStathamInAction.getJsonToJavaConverter();

    // TODO remove it after testing.
    // assertThat(reflectionJavaToJsonConverterFromJsonStathamInAction.getJsonObjectConvertibleCreator(),
    // is((instanceOf(OrgJsonUnorderedJsonObjectCreator.class))));
    assertThat(reflectionJavaToJsonConverterFromJsonStathamInAction.getJsonObjectConvertibleCreator(),
        is((instanceOf(UnorderedJsonObjectCreator.class))));

    // TODO remove it after testing.
    // assertThat(reflectionJavaToJsonConverterFromJsonStathamInAction.getJsonArrayConvertibleCreator(),
    // is(instanceOf(OrgJsonJsonArrayCreator.class)));
    assertThat(reflectionJavaToJsonConverterFromJsonStathamInAction.getJsonArrayConvertibleCreator(),
        is(instanceOf(JsonArrayWithUnorderedJsonObjectCreator.class)));

    assertThat(reflectionJavaToJsonConverterFromJsonStathamInAction.getKnownDataStructureTypeProcessorDecider(),
        is(instanceOf(KnownDataStructureTypeProcessorDecider.class)));

    final KnownTypeProcessorDeciderForJavaToJson[] knownTypeProcessorDeciders =
      reflectionJavaToJsonConverterFromJsonStathamInAction.getKnownTypeProcessorDeciders();
    assertEquals(3, knownTypeProcessorDeciders.length);
    assertThat(knownTypeProcessorDeciders[0], is(instanceOf(KnownDataStructureTypeProcessorDecider.class)));
    assertThat(knownTypeProcessorDeciders[1], is(instanceOf(KnownObjectReferenceTypeProcessorDecider.class)));
    assertThat(knownTypeProcessorDeciders[2], is(instanceOf(OneProcessorForKnownTypeDecider.class)));

  }

}

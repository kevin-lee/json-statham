/**
 *
 */
package org.elixirian.jsonstatham.core.convertible;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Field;

import org.json.JSONArray;
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
 * @version 0.0.1 (2010-06-03)
 */
public class OrgJsonJsonArrayConvertibleCreatorTest
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

  /**
   * Test method for
   * {@link org.elixirian.jsonstatham.core.convertible.OrgJsonJsonArrayConvertibleCreator#newJsonArrayConvertible()}.
   *
   * @throws NoSuchFieldException
   * @throws SecurityException
   * @throws IllegalAccessException
   * @throws IllegalArgumentException
   */
  @Test
  public final void testNewJsonArrayConvertible() throws SecurityException, NoSuchFieldException,
      IllegalArgumentException, IllegalAccessException
  {
    final JsonArrayConvertibleCreator jsonArrayConvertibleCreator = new JsonArrayWithOrderedJsonObjectCreator();
    final JsonArray jsonArray = jsonArrayConvertibleCreator.newJsonArrayConvertible();

//    final Field jsonObjectField = jsonArray.getClass()
//        .getDeclaredField("jsonArray");
//    jsonObjectField.setAccessible(true);
//    final Object jsonObject = jsonObjectField.get(jsonArray);
    assertThat(jsonArray, notNullValue());
    assertThat(jsonArray, is(instanceOf(JsonArray.class)));
//    assertSame(jsonObject.getClass(), JSONArray.class);
    assertSame(jsonArray.getClass(), JsonArrayWithOrderedJsonObject.class);
  }

}

/**
 * 
 */
package org.elixirian.jsonstatham.core.convertible;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.lang.reflect.Field;

import org.elixirian.jsonstatham.core.convertible.JsonArrayConvertible;
import org.elixirian.jsonstatham.core.convertible.JsonArrayConvertibleCreator;
import org.elixirian.jsonstatham.core.convertible.OrgJsonJsonArrayConvertibleCreator;
import org.json.JSONArray;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


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
    final JsonArrayConvertibleCreator jsonArrayConvertibleCreator = new OrgJsonJsonArrayConvertibleCreator();
    final JsonArrayConvertible jsonArrayConvertible = jsonArrayConvertibleCreator.newJsonArrayConvertible();

    final Field jsonObjectField = jsonArrayConvertible.getClass()
        .getDeclaredField("jsonArray");
    jsonObjectField.setAccessible(true);
    Object jsonObject = jsonObjectField.get(jsonArrayConvertible);
    assertThat(jsonObject, notNullValue());
    assertThat(jsonObject, is(instanceOf(JSONArray.class)));
    assertSame(jsonObject.getClass(), JSONArray.class);
  }

}

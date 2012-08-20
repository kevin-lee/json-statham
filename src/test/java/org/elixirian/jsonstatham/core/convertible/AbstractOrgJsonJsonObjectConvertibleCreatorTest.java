/**
 * 
 */
package org.elixirian.jsonstatham.core.convertible;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.elixirian.jsonstatham.core.convertible.AbstractOrgJsonJsonObjectConvertibleCreator;
import org.elixirian.jsonstatham.core.convertible.JsonObject;
import org.elixirian.jsonstatham.core.convertible.OrgJsonJsonObjectConvertible;
import org.elixirian.jsonstatham.exception.JsonStathamException;
import org.json.JSONObject;
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
public class AbstractOrgJsonJsonObjectConvertibleCreatorTest
{

  /**
   * Test method for
   * {@link org.elixirian.jsonstatham.core.convertible.AbstractOrgJsonJsonObjectConvertibleCreator#newJsonObjectConvertible()}
   * .
   */
  @Test
  public final void testNewJsonObjectConvertible()
  {
    final JsonObject jsonObject = new OrgJsonJsonObjectConvertible(new JSONObject());
    final AbstractOrgJsonJsonObjectConvertibleCreator orgJsonJsonObjectConvertibleCreator =
      new AbstractOrgJsonJsonObjectConvertibleCreator() {

        @Override
        public JsonObject newJsonObjectConvertible()
        {
          return jsonObject;
        }
      };

    assertThat(orgJsonJsonObjectConvertibleCreator.newJsonObjectConvertible(), is(jsonObject));
    assertThat(orgJsonJsonObjectConvertibleCreator.newJsonObjectConvertible(), equalTo(jsonObject));
  }

  /**
   * Test method for
   * {@link org.elixirian.jsonstatham.core.convertible.AbstractOrgJsonJsonObjectConvertibleCreator#nullJsonObjectConvertible()}
   * .
   */
  @Test
  public final void testNullJsonObjectConvertible()
  {
    final AbstractOrgJsonJsonObjectConvertibleCreator orgJsonJsonObjectConvertibleCreator =
      new AbstractOrgJsonJsonObjectConvertibleCreator() {

        @Override
        public JsonObject newJsonObjectConvertible()
        {
          return null;
        }
      };

    assertThat(orgJsonJsonObjectConvertibleCreator.nullJsonObjectConvertible(),
        is(AbstractOrgJsonJsonObjectConvertibleCreator.NULL_JSON_OBJECT_CONVERTIBLE));
    assertThat(orgJsonJsonObjectConvertibleCreator.nullJsonObjectConvertible(),
        equalTo(AbstractOrgJsonJsonObjectConvertibleCreator.NULL_JSON_OBJECT_CONVERTIBLE));
  }

  @Test(expected = JsonStathamException.class)
  public void testAbstractOrgJsonJsonObjectConvertibleCreator$NULL_JSON_OBJECT_CONVERTIBLE() throws Exception
  {
    AbstractOrgJsonJsonObjectConvertibleCreator.NULL_JSON_OBJECT_CONVERTIBLE.put(null, null);
  }
}

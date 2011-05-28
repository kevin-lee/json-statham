/**
 * 
 */
package org.elixirian.jsonstatham.core.convertible;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.elixirian.jsonstatham.core.convertible.AbstractOrgJsonJsonObjectConvertibleCreator;
import org.elixirian.jsonstatham.core.convertible.JsonObjectConvertible;
import org.elixirian.jsonstatham.core.convertible.OrgJsonJsonObjectConvertible;
import org.elixirian.jsonstatham.exception.JsonStathamException;
import org.json.JSONObject;
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
    final JsonObjectConvertible jsonObjectConvertible = new OrgJsonJsonObjectConvertible(new JSONObject());
    final AbstractOrgJsonJsonObjectConvertibleCreator orgJsonJsonObjectConvertibleCreator =
      new AbstractOrgJsonJsonObjectConvertibleCreator() {

        @Override
        public JsonObjectConvertible newJsonObjectConvertible()
        {
          return jsonObjectConvertible;
        }
      };

    assertThat(orgJsonJsonObjectConvertibleCreator.newJsonObjectConvertible(), is(jsonObjectConvertible));
    assertThat(orgJsonJsonObjectConvertibleCreator.newJsonObjectConvertible(), equalTo(jsonObjectConvertible));
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
        public JsonObjectConvertible newJsonObjectConvertible()
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

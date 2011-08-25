/**
 * 
 */
package org.elixirian.jsonstatham.json.json2java;

import static org.elixirian.kommonlee.util.Objects.*;

import java.util.List;

import org.elixirian.jsonstatham.annotation.JsonField;
import org.elixirian.jsonstatham.annotation.JsonObject;
import org.elixirian.jsonstatham.json.Address;


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
 * @version 0.0.1 (2010-10-08)
 */
@JsonObject
public class JsonObjectHavingNestedGenericTypes
{
  @JsonField
  private List<List<List<Address>>> listOfListOfListOfAddress;

  public JsonObjectHavingNestedGenericTypes(List<List<List<Address>>> listOfListOfListOfAddress)
  {
    this.listOfListOfListOfAddress = listOfListOfListOfAddress;
  }

  @Override
  public int hashCode()
  {
    return hash(listOfListOfListOfAddress);
  }

  @Override
  public boolean equals(Object jsonObjectHavingNestedGenericTypesObject)
  {
    if (identical(this, jsonObjectHavingNestedGenericTypesObject))
    {
      return true;
    }
    final JsonObjectHavingNestedGenericTypes that =
      castIfInstanceOf(JsonObjectHavingNestedGenericTypes.class, jsonObjectHavingNestedGenericTypesObject);
    return isNotNull(that) && equal(this.listOfListOfListOfAddress, that.listOfListOfListOfAddress);
  }

  @Override
  public String toString()
  {
    return toStringBuilder(this).add("listOfListOfListOfAddress", listOfListOfListOfAddress)
        .toString();
  }
}

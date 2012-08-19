/**
 * 
 */
package org.elixirian.jsonstatham.json.json2java;

import static org.elixirian.kommonlee.util.Objects.*;

import java.util.LinkedHashMap;

import org.elixirian.jsonstatham.annotation.JsonConstructor;
import org.elixirian.jsonstatham.annotation.JsonField;
import org.elixirian.jsonstatham.annotation.Json;
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
 * @version 0.0.1 (2010-10-27)
 */
@Json
public class JsonObjectWithMapImplementation
{
  @JsonField(name = "address")
  private final LinkedHashMap<String, Address> addressLinkedHashMap;

  @JsonConstructor
  public JsonObjectWithMapImplementation(LinkedHashMap<String, Address> addressLinkedHashMap)
  {
    this.addressLinkedHashMap = addressLinkedHashMap;
  }

  public LinkedHashMap<String, Address> getAddressLinkedHashMap()
  {
    return addressLinkedHashMap;
  }

  @Override
  public int hashCode()
  {
    return hash(addressLinkedHashMap);
  }

  @Override
  public boolean equals(Object jsonObjectWithSetImplementation)
  {
    if (identical(this, jsonObjectWithSetImplementation))
    {
      return true;
    }
    final JsonObjectWithMapImplementation that =
      castIfInstanceOf(JsonObjectWithMapImplementation.class, jsonObjectWithSetImplementation);
    return isNotNull(that) && equal(this.addressLinkedHashMap, that.getAddressLinkedHashMap());
  }

  @Override
  public String toString()
  {
    return toStringBuilder(this).add("addresses", addressLinkedHashMap)
        .toString();
  }
}

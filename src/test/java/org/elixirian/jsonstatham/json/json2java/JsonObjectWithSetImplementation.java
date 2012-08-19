/**
 * 
 */
package org.elixirian.jsonstatham.json.json2java;

import static org.elixirian.kommonlee.util.Objects.*;

import java.util.LinkedHashSet;

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
public class JsonObjectWithSetImplementation
{
  @JsonField(name = "address")
  private final LinkedHashSet<Address> addressLinkedHashSet;

  @JsonConstructor
  public JsonObjectWithSetImplementation(LinkedHashSet<Address> addressLinkedHashSet)
  {
    this.addressLinkedHashSet = addressLinkedHashSet;
  }

  public LinkedHashSet<Address> getAddressLinkedHashSet()
  {
    return addressLinkedHashSet;
  }

  @Override
  public int hashCode()
  {
    return hash(addressLinkedHashSet);
  }

  @Override
  public boolean equals(Object jsonObjectWithSetImplementation)
  {
    if (identical(this, jsonObjectWithSetImplementation))
    {
      return true;
    }
    final JsonObjectWithSetImplementation that =
      castIfInstanceOf(JsonObjectWithSetImplementation.class, jsonObjectWithSetImplementation);
    return isNotNull(that) && equal(this.addressLinkedHashSet, that.getAddressLinkedHashSet());
  }

  @Override
  public String toString()
  {
    return toStringBuilder(this).add("addresses", addressLinkedHashSet)
        .toString();
  }
}

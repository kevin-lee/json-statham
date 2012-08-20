/**
 * 
 */
package org.elixirian.jsonstatham.json.json2java;

import static org.elixirian.kommonlee.util.Objects.*;

import java.util.LinkedList;

import org.elixirian.jsonstatham.annotation.JsonConstructor;
import org.elixirian.jsonstatham.annotation.JsonField;
import org.elixirian.jsonstatham.annotation.Json;
import org.elixirian.jsonstatham.json.Address;


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
 * @version 0.0.1 (2010-10-27)
 */
@Json
public class JsonObjectWithListImplementation
{
  @JsonField(name = "address")
  private final LinkedList<Address> addressLinkedList;

  @JsonConstructor
  public JsonObjectWithListImplementation(LinkedList<Address> addressLinkedList)
  {
    this.addressLinkedList = addressLinkedList;
  }

  public LinkedList<Address> getAddressLinkedList()
  {
    return addressLinkedList;
  }

  @Override
  public int hashCode()
  {
    return hash(addressLinkedList);
  }

  @Override
  public boolean equals(Object JsonObjectWithSetImplementation)
  {
    if (identical(this, JsonObjectWithSetImplementation))
    {
      return true;
    }
    final JsonObjectWithListImplementation that =
      castIfInstanceOf(JsonObjectWithListImplementation.class, JsonObjectWithSetImplementation);
    return isNotNull(that) && equal(this.addressLinkedList, that.getAddressLinkedList());
  }

  @Override
  public String toString()
  {
    return toStringBuilder(this).add("addresses", addressLinkedList)
        .toString();
  }
}

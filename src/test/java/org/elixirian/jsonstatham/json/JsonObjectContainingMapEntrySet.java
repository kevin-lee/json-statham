/**
 * 
 */
package org.elixirian.jsonstatham.json;

import static org.elixirian.kommonlee.util.Conditional.*;
import static org.elixirian.kommonlee.util.Objects.*;

import java.util.Map.Entry;
import java.util.Set;

import org.elixirian.jsonstatham.annotation.JsonField;
import org.elixirian.jsonstatham.annotation.Json;


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
 * @version 0.0.1 (2010-02-03)
 */
@Json
public class JsonObjectContainingMapEntrySet
{
  @JsonField(name = "name")
  private final String name;

  @JsonField(name = "valueMapEntrySet")
  private final Set<Entry<String, Address>> mapEntrySet;

  public JsonObjectContainingMapEntrySet(String name, Set<Entry<String, Address>> mapEntrySet)
  {
    this.name = name;
    this.mapEntrySet = mapEntrySet;
  }

  @Override
  public int hashCode()
  {
    return hash(name, mapEntrySet);
  }

  @Override
  public boolean equals(Object jsonObjectContainingMapEntrySet)
  {
    if (identical(this, jsonObjectContainingMapEntrySet))
    {
      return true;
    }
    final JsonObjectContainingMapEntrySet that =
      castIfInstanceOf(JsonObjectContainingMapEntrySet.class, jsonObjectContainingMapEntrySet);
    /* @formatter:off */
		return isNotNull(that) && 
						and(equal(this.name, that.name), 
								equal(this.mapEntrySet, that.mapEntrySet));
		/* @formatter:on */
  }

  @Override
  public String toString()
  {
    return toStringBuilder(this).add("name", name)
        .add("valueMapEntrySet", mapEntrySet)
        .toString();
  }
}

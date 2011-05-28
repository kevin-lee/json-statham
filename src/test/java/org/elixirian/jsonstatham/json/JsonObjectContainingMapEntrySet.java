/**
 * 
 */
package org.elixirian.jsonstatham.json;

import static org.elixirian.common.util.Conditional.*;
import static org.elixirian.common.util.Objects.*;

import java.util.Map.Entry;
import java.util.Set;

import org.elixirian.jsonstatham.annotation.JsonField;
import org.elixirian.jsonstatham.annotation.JsonObject;


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
 * @version 0.0.1 (2010-02-03)
 */
@JsonObject
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

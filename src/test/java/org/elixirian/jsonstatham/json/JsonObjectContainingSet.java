/**
 * 
 */
package org.elixirian.jsonstatham.json;

import static org.elixirian.kommonlee.util.Conditional.*;
import static org.elixirian.kommonlee.util.Objects.*;

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
public class JsonObjectContainingSet
{
  @JsonField(name = "name")
  private final String name;

  @JsonField(name = "valueSet")
  private final Set<String> set;

  public JsonObjectContainingSet(String name, Set<String> set)
  {
    this.name = name;
    this.set = set;
  }

  @Override
  public int hashCode()
  {
    return hash(name, set);
  }

  @Override
  public boolean equals(Object jsonObjectContainingSet)
  {
    if (identical(this, jsonObjectContainingSet))
    {
      return true;
    }
    final JsonObjectContainingSet that = castIfInstanceOf(JsonObjectContainingSet.class, jsonObjectContainingSet);
    /* @formatter:off */
		return isNotNull(that) && 
						and(equal(this.name, that.name), 
								equal(this.set, that.set));
		/* @formatter:on */
  }

  @Override
  public String toString()
  {
    return toStringBuilder(this).add("name", name)
        .add("valueSet", set)
        .toString();
  }
}

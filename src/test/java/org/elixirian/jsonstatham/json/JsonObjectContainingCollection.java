/**
 * 
 */
package org.elixirian.jsonstatham.json;

import static org.elixirian.kommonlee.util.Conditional.*;
import static org.elixirian.kommonlee.util.Objects.*;

import java.util.Collection;

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
public class JsonObjectContainingCollection
{
  @JsonField(name = "name")
  private final String name;

  @JsonField(name = "valueCollection")
  private final Collection<String> collection;

  public JsonObjectContainingCollection(String name, Collection<String> collection)
  {
    this.name = name;
    this.collection = collection;
  }

  @Override
  public int hashCode()
  {
    return hash(name, collection);
  }

  @Override
  public boolean equals(Object jsonObjectContainingCollection)
  {
    if (identical(this, jsonObjectContainingCollection))
    {
      return true;
    }
    final JsonObjectContainingCollection that =
      castIfInstanceOf(JsonObjectContainingCollection.class, jsonObjectContainingCollection);
    /* @formatter:off */
		return isNotNull(that) && 
				and(equal(this.name, that.name), 
					equal(this.collection, that.collection));
		/* @formatter:on */
  }

  @Override
  public String toString()
  {
    return toStringBuilder(this).add("name", name)
        .add("valueCollection", collection)
        .toString();
  }
}

package org.elixirian.jsonstatham.json;

import static org.elixirian.kommonlee.util.Conditional.*;
import static org.elixirian.kommonlee.util.Objects.*;

import org.elixirian.jsonstatham.annotation.JsonField;
import org.elixirian.jsonstatham.annotation.Json;


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
 * @version 0.0.1 (2010-02-12)
 */
@Json
public class JsonObjectWithoutFieldName
{
  @JsonField
  private final long id;

  @JsonField
  private final String name;

  @JsonField
  private final String address;

  public JsonObjectWithoutFieldName(long id, String name, String address)
  {
    this.id = id;
    this.name = name;
    this.address = address;
  }

  @Override
  public int hashCode()
  {
    return hashObjects(hash(id), name, address);
  }

  @Override
  public boolean equals(Object jsonObjectWithoutFieldName)
  {
    if (identical(this, jsonObjectWithoutFieldName))
    {
      return true;
    }
    final JsonObjectWithoutFieldName that =
      castIfInstanceOf(JsonObjectWithoutFieldName.class, jsonObjectWithoutFieldName);
    /* @formatter:off */
		return isNotNull(that) && 
						and(equal(this.id, that.id), 
								equal(this.name, that.name), 
								equal(this.address, that.address));
		/* @formatter:on */
  }

  @Override
  public String toString()
  {
    return toStringBuilder(this).add("id", id)
        .add("name", name)
        .add("address", address)
        .toString();
  }
}

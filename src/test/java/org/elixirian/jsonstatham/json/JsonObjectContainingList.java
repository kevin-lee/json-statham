/**
 * 
 */
package org.elixirian.jsonstatham.json;

import static org.elixirian.kommonlee.util.Conditional.*;
import static org.elixirian.kommonlee.util.Objects.*;

import java.util.List;

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
public class JsonObjectContainingList
{
  @JsonField(name = "name")
  private final String name;

  @JsonField(name = "valueList")
  private final List<String> list;

  public JsonObjectContainingList(String name, List<String> list)
  {
    this.name = name;
    this.list = list;
  }

  @Override
  public int hashCode()
  {
    return hash(name, list);
  }

  @Override
  public boolean equals(Object jsonObjectContainingList)
  {
    if (identical(this, jsonObjectContainingList))
    {
      return true;
    }
    final JsonObjectContainingList that = castIfInstanceOf(JsonObjectContainingList.class, jsonObjectContainingList);
    /* @formatter:off */
		return isNotNull(that) && 
						and(equal(this.name, that.name), 
								equal(this.list, that.list));
		/* @formatter:on */
  }
}

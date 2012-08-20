/**
 * 
 */
package org.elixirian.jsonstatham.json.json2java;

import static org.elixirian.kommonlee.util.Conditional.*;
import static org.elixirian.kommonlee.util.Objects.*;

import java.util.Map;

import org.elixirian.jsonstatham.annotation.JsonConstructor;
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
 * @version 0.01 (2009-11-28)
 */
@Json
public class JsonObjectWithJsonConstructorWithSomeNotMatchingParams
{
  private final String name;

  @JsonField(name = "uri")
  private final String uri;

  @JsonField(name = "params")
  private final Map<String, String> parameterMap;

  @JsonConstructor
  public JsonObjectWithJsonConstructorWithSomeNotMatchingParams(String name, String uri,
      Map<String, String> parameterMap)
  {
    this.name = name;
    this.uri = uri;
    this.parameterMap = parameterMap;
  }

  public String getName()
  {
    return name;
  }

  public String getUri()
  {
    return uri;
  }

  public Map<String, String> getParameterMap()
  {
    return parameterMap;
  }

  @Override
  public int hashCode()
  {
    return hash(name, uri, parameterMap);
  }

  @Override
  public boolean equals(Object address)
  {
    if (identical(this, address))
    {
      return true;
    }
    final JsonObjectWithJsonConstructorWithSomeNotMatchingParams that =
      castIfInstanceOf(JsonObjectWithJsonConstructorWithSomeNotMatchingParams.class, address);
    /* @formatter:off */
		return isNotNull(that) && 
						and(equal(this.name, that.getName()), 
								equal(this.uri, that.getUri()),
								equal(this.parameterMap, that.getParameterMap()));
		/* @formatter:on */
  }

  @Override
  public String toString()
  {
    return toStringBuilder(this).add("name", name)
        .add("uri", uri)
        .add("parameterMap", parameterMap)
        .toString();
  }
}

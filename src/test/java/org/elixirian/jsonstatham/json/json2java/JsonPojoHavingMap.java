package org.elixirian.jsonstatham.json.json2java;

import static org.elixirian.kommonlee.util.Objects.*;

import java.util.Map;

import org.elixirian.jsonstatham.annotation.Json;
import org.elixirian.jsonstatham.annotation.JsonField;


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
 * @version 0.0.1 (2010-10-07)
 */
@Json
public class JsonPojoHavingMap
{
  @JsonField
  private final String name;

  @JsonField
  private final Map<String, Long> stringToLongMap;

  public JsonPojoHavingMap(final String name, final Map<String, Long> stringToLongMap)
  {
    this.name = name;
    this.stringToLongMap = stringToLongMap;
  }

  public String getName()
  {
    return name;
  }

  public Map<String, Long> getStringToLongMap()
  {
    return stringToLongMap;
  }

  @Override
  public int hashCode()
  {
    return hash(name, stringToLongMap);
  }

  @Override
  public boolean equals(final Object jsonPojoHavingMap)
  {
    if (identical(this, jsonPojoHavingMap))
    {
      return true;
    }
    final JsonPojoHavingMap that = castIfInstanceOf(JsonPojoHavingMap.class, jsonPojoHavingMap);
    /* @formatter:off */
    return isNotNull(that) &&
        (equal(this.name, that.name) &&
         equal(this.stringToLongMap, that.stringToLongMap));
    /* @formatter:on */
  }

  @Override
  public String toString()
  {
    return toStringBuilder(this).add("name", name)
        .add("stringToLongMap", stringToLongMap)
        .toString();
  }
}

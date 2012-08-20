package org.elixirian.jsonstatham.json.json2java;

import static org.elixirian.kommonlee.util.Conditional.*;
import static org.elixirian.kommonlee.util.Objects.*;

import java.util.Map;

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
 * @version 0.0.1 (2010-10-07)
 */
@Json
public class JsonPojoHavingMap
{
  @JsonField
  private final String name;

  @JsonField
  private final Map<String, Long> stringToLongMap;

  public JsonPojoHavingMap(String name, Map<String, Long> stringToLongMap)
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
  public boolean equals(Object jsonPojoHavingMap)
  {
    if (identical(this, jsonPojoHavingMap))
    {
      return true;
    }
    final JsonPojoHavingMap that = castIfInstanceOf(JsonPojoHavingMap.class, jsonPojoHavingMap);
    return isNotNull(that) && and(equal(this.name, that.name), equal(this.stringToLongMap, that.stringToLongMap));
  }

  @Override
  public String toString()
  {
    return toStringBuilder(this).add("name", name)
        .add("stringToLongMap", stringToLongMap)
        .toString();
  }
}

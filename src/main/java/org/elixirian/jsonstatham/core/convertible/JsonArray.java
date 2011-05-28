/**
 * 
 */
package org.elixirian.jsonstatham.core.convertible;

import static org.elixirian.jsonstatham.core.util.JsonUtil.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

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
 * @version 0.0.1 (2010-12-25)
 */
public class JsonArray implements JsonArrayConvertible
{
  private final List<Object> list;

  private JsonArray()
  {
    this.list = new ArrayList<Object>();
  }

  private JsonArray(final Collection<?> elements)
  {
    this.list = new ArrayList<Object>(elements);
  }

  @Override
  public Object get(final int index)
  {
    return list.get(index);
  }

  @Override
  public <T> JsonArrayConvertible put(final T value)
  {
    list.add(value);
    return this;
  }

  @Override
  public int length()
  {
    return list.size();
  }

  @Override
  public Object getActualObject()
  {
    return this;
  }

  @Override
  public String toString()
  {
    final StringBuilder stringBuilder = new StringBuilder("[");
    final Iterator<Object> iterator = list.iterator();

    if (iterator.hasNext())
      stringBuilder.append(toStringValue(iterator.next()));

    while (iterator.hasNext())
    {
      stringBuilder.append(',')
          .append(toStringValue(iterator.next()));
    }
    return stringBuilder.append(']')
        .toString();
  }

  public static JsonArray newJsonArray()
  {
    return new JsonArray();
  }

  public static JsonArray newJsonArray(final Collection<?> elements)
  {
    return new JsonArray(elements);
  }
}

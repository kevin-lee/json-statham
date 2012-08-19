/**
 * 
 */
package org.elixirian.jsonstatham.json;

import static org.elixirian.kommonlee.util.Conditional.*;
import static org.elixirian.kommonlee.util.Objects.*;

import java.util.Iterator;

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
 * @version 0.0.1 (2010-02-03)
 */
@Json
public class JsonObjectContainingIterator
{
  @JsonField(name = "name")
  private final String name;

  @JsonField(name = "valueIterator")
  private final Iterator<String> iterator;

  public JsonObjectContainingIterator(String name, Iterator<String> iterator)
  {
    this.name = name;
    this.iterator = iterator;
  }

  public String getName()
  {
    return name;
  }

  public Iterator<String> getIterator()
  {
    return iterator;
  }

  @Override
  public int hashCode()
  {
    return hash(name, iterator);
  }

  @Override
  public boolean equals(Object jsonObjectContainingIterator)
  {
    if (identical(this, jsonObjectContainingIterator))
    {
      return true;
    }
    final JsonObjectContainingIterator that =
      castIfInstanceOf(JsonObjectContainingIterator.class, jsonObjectContainingIterator);
    /* @formatter:off */
		return isNotNull(that) && 
						and(equal(this.name, that.name), 
								iteratorEquals(this.iterator, that.iterator));
		/* @formatter:on */
  }

  private boolean iteratorEquals(Iterator<String> iterator1, Iterator<String> iterator2)
  {
    if (iterator1.hasNext() != iterator2.hasNext())
    {
      return false;
    }
    int count1 = 0;
    int count2 = 0;
    while (iterator1.hasNext())
    {
      count1++;
      final String value = iterator1.next();
      if (iterator2.hasNext())
      {
        count2++;
        final String value2 = iterator2.next();
        if (value != value2)
        {
          if (null != value && !value.equals(value2))
          {
            return false;
          }
        }
      }
    }
    return count1 == count2;
  }
}

/**
 * 
 */
package org.elixirian.jsonstatham.json;

import static org.elixirian.kommonlee.util.Conditional.*;
import static org.elixirian.kommonlee.util.Objects.*;

import org.elixirian.jsonstatham.annotation.JsonField;
import org.elixirian.jsonstatham.annotation.Json;
import org.elixirian.jsonstatham.annotation.ValueAccessor;


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
 * @version 0.01 (2009-11-28)
 */
@Json
public class NestedJsonObjectWithValueAccessor
{
  @ValueAccessor
  @JsonField(name = "id")
  private Long primaryKey;

  @ValueAccessor
  @JsonField(name = "name")
  private String name;

  @ValueAccessor
  @JsonField(name = "parent")
  private NestedJsonObjectWithValueAccessor parent;

  public NestedJsonObjectWithValueAccessor(Long primaryKey, String name, NestedJsonObjectWithValueAccessor parent)
  {
    this.primaryKey = primaryKey;
    this.name = name;
    this.parent = parent;
  }

  public Long getPrimaryKey()
  {
    return primaryKey;
  }

  public void setPrimaryKey(Long primaryKey)
  {
    this.primaryKey = primaryKey;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public NestedJsonObjectWithValueAccessor getParent()
  {
    return parent;
  }

  public void setParent(NestedJsonObjectWithValueAccessor parent)
  {
    this.parent = parent;
  }

  @Override
  public int hashCode()
  {
    return hash(primaryKey, name, parent);
  }

  @Override
  public boolean equals(Object nestedJsonObjectWithValueAccessor)
  {
    if (identical(this, nestedJsonObjectWithValueAccessor))
    {
      return true;
    }
    final NestedJsonObjectWithValueAccessor that =
      castIfInstanceOf(NestedJsonObjectWithValueAccessor.class, nestedJsonObjectWithValueAccessor);
    /* @formatter:off */
		return isNotNull(that) && 
						and(equal(this.primaryKey, that.getPrimaryKey()), 
								equal(this.name, that.getName()),
								equal(this.parent, that.getParent()));
		/* @formatter:on */
  }
}

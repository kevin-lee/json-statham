/**
 * 
 */
package com.lckymn.kevin.jsonstatham.json;

import static org.elixirian.common.util.Conditional.*;
import static org.elixirian.common.util.Objects.*;

import java.util.Iterator;
import java.util.Set;

import com.lckymn.kevin.jsonstatham.annotation.JsonField;
import com.lckymn.kevin.jsonstatham.annotation.JsonObject;
import com.lckymn.kevin.jsonstatham.annotation.ValueAccessor;

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
 * @version 0.0.1 (2010-03-06)
 */
@JsonObject
public class JsonObjectPojoImpl implements JsonObjectPojo
{
  @ValueAccessor
  @JsonField
  private Long id;

  @ValueAccessor
  @JsonField
  private String name;

  @ValueAccessor(name = "getAddresses")
  @JsonField(name = "addresses")
  private Set<Address> addressSet;

  public JsonObjectPojoImpl(Long id, String name, Set<Address> addressSet)
  {
    this.id = id;
    this.name = name;
    this.addressSet = addressSet;
  }

  @Override
  public Long getId()
  {
    return id;
  }

  @Override
  public String getName()
  {
    return name;
  }

  @Override
  public Iterator<Address> getAddresses()
  {
    return addressSet.iterator();
  }

  @Override
  public Set<Address> getAddressSet()
  {
    return addressSet;
  }

  @Override
  public int hashCode()
  {
    return hash(id, name, addressSet);
  }

  @Override
  public boolean equals(Object jsonObjectPojoImpl)
  {
    if (identical(this, jsonObjectPojoImpl))
    {
      return true;
    }
    final JsonObjectPojoImpl that = castIfInstanceOf(JsonObjectPojoImpl.class, jsonObjectPojoImpl);
    /* @formatter:off */
		return isNotNull(that)	&& 
						and(equal(this.id, that.getId()), 
								equal(this.name, that.getName()),
								equal(this.addressSet, that.getAddressSet()));
		/* @formatter:on */
  }

  @Override
  public String toString()
  {
    return toStringBuilder(this).add("id", id)
        .add("name", name)
        .add("addresses", addressSet)
        .toString();
  }
}

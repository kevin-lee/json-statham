/**
 *
 */
package org.elixirian.jsonstatham.json.json2java;

import static org.elixirian.kommonlee.util.Objects.*;

import org.elixirian.jsonstatham.annotation.Json;
import org.elixirian.jsonstatham.annotation.JsonField;
import org.elixirian.jsonstatham.core.convertible.JsonConvertible;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2012-10-23)
 */
@Json
public class ObjectContainingJsonConvertible
{
  @JsonField
  private final String id;

  @JsonField
  private final JsonConvertible jsonConvertible;

  public ObjectContainingJsonConvertible(final String id, final JsonConvertible jsonConvertible)
  {
    this.id = id;
    this.jsonConvertible = jsonConvertible;
  }

  public String getFsi()
  {
    return id;
  }

  public <T extends JsonConvertible> T getJsonConvertible()
  {
    @SuppressWarnings("unchecked")
    final T castedValues = (T) jsonConvertible;
    return castedValues;
  }

  @Override
  public int hashCode()
  {
    return hash(id, jsonConvertible);
  }

  @Override
  public boolean equals(final Object objectContainingJsonConvertible)
  {
    if (this == objectContainingJsonConvertible)
    {
      return true;
    }
    final ObjectContainingJsonConvertible that =
      castIfInstanceOf(ObjectContainingJsonConvertible.class, objectContainingJsonConvertible);
    /* @formatter:off */
    return null != that &&
        (equal(this.id, that.getFsi()) &&
         equal(this.jsonConvertible, that.getJsonConvertible()));
    /* @formatter:on */
  }

  @Override
  public String toString()
  {
    /* @formatter:off */
    return toStringBuilder(this)
            .add("id",              id)
            .add("jsonConvertible", jsonConvertible)
          .toString();
    /* @formatter:on */
  }
}

/**
 *
 */
package org.elixirian.jsonstatham.json.json2java;

import static org.elixirian.kommonlee.util.Objects.*;
import static org.elixirian.kommonlee.util.collect.Lists.*;

import java.util.List;

import org.elixirian.jsonstatham.annotation.Json;
import org.elixirian.jsonstatham.annotation.JsonConstructor;
import org.elixirian.jsonstatham.annotation.JsonField;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2012-12-09)
 */
@Json
public class JsonWithNotOrderedConstructorParams
{
  @JsonField(name = "selectedServices")
  private Long[] selectedServiceIdList;

  @JsonField
  private String note;

  @JsonConstructor
  public JsonWithNotOrderedConstructorParams(final String note, final Long... selectedServiceIdList)
  {
    this.selectedServiceIdList = selectedServiceIdList;
    this.note = note;
  }

  public Long[] getSelectedServiceIdList()
  {
    return selectedServiceIdList;
  }

  public String getNote()
  {
    return note;
  }

  @Override
  public int hashCode()
  {
    return hash(selectedServiceIdList, note);
  }

  @Override
  public boolean equals(final Object jsonWithNotOrderedConstructorParams)
  {
    if (this == jsonWithNotOrderedConstructorParams)
    {
      return true;
    }
    final JsonWithNotOrderedConstructorParams that =
      castIfInstanceOf(JsonWithNotOrderedConstructorParams.class, jsonWithNotOrderedConstructorParams);
    /* @formatter:off */
    return null != that &&
            (deepEqual(this.selectedServiceIdList, that.getSelectedServiceIdList()) &&
             equal(this.note, that.getNote()));
    /* @formatter:on */
  }

  @Override
  public String toString()
  {
    /* @formatter:off */
    return toStringBuilder(this)
            .add("selectedServiceIdList", selectedServiceIdList)
            .add("note",                  note)
          .toString();
    /* @formatter:on */
  }
}

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
public class JsonWithDifferentConstructorParamTypeAndName
{
  @JsonField(name = "selectedServices")
  private List<Long> selectedServiceIdList;

  @JsonField
  private String note;

  @JsonConstructor
  public JsonWithDifferentConstructorParamTypeAndName(final Long[] selectedServices, final String note)
  {
    this.selectedServiceIdList = newArrayList(selectedServices);
    this.note = note;
  }

  public List<Long> getSelectedServiceIdList()
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
    final JsonWithDifferentConstructorParamTypeAndName that =
      castIfInstanceOf(JsonWithDifferentConstructorParamTypeAndName.class, jsonWithNotOrderedConstructorParams);
    /* @formatter:off */
    return null != that &&
            (equal(this.selectedServiceIdList, that.getSelectedServiceIdList()) &&
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

/**
 *
 */
package org.elixirian.jsonstatham.json.json2java;

import static org.elixirian.kommonlee.util.Objects.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.elixirian.jsonstatham.annotation.Json;
import org.elixirian.jsonstatham.annotation.JsonConstructor;
import org.elixirian.jsonstatham.annotation.JsonField;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2012-12-09)
 */
@Json
public class JsonWithDifferentConstructorParamType3
{
  @JsonField
  private final Long id;

  @JsonField
  private final Date date;

  @JsonField
  private final String note;

  @JsonConstructor
  public JsonWithDifferentConstructorParamType3(final Long id, final String date, final String note)
      throws ParseException
  {
    this.id = id;
    this.date = new SimpleDateFormat("yyyy-MM-dd").parse(date);
    this.note = note;
  }

  public Long getId()
  {
    return id;
  }

  public Date getDate()
  {
    return date;
  }

  public String getNote()
  {
    return note;
  }

  @Override
  public int hashCode()
  {
    return hash(id, date, note);
  }

  @Override
  public boolean equals(final Object jsonWithNotOrderedConstructorParams)
  {
    if (this == jsonWithNotOrderedConstructorParams)
    {
      return true;
    }
    final JsonWithDifferentConstructorParamType3 that =
      castIfInstanceOf(JsonWithDifferentConstructorParamType3.class, jsonWithNotOrderedConstructorParams);
    /* @formatter:off */
    return null != that &&
            (equal(this.id, that.getId()) &&
             equal(this.date, that.getDate()) &&
             equal(this.note, that.getNote()));
    /* @formatter:on */
  }

  @Override
  public String toString()
  {
    /* @formatter:off */
    return toStringBuilder(this)
            .add("id",   id)
            .add("date", date)
            .add("note", note)
          .toString();
    /* @formatter:on */
  }
}

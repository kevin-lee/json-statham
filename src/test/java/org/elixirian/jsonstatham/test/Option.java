/**
 * 
 */
package org.elixirian.jsonstatham.test;

import static org.elixirian.kommonlee.util.Objects.*;

import org.elixirian.jsonstatham.annotation.JsonField;
import org.elixirian.jsonstatham.annotation.JsonObject;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-09-07)
 */
@JsonObject
public class Option
{
  @JsonField
  private final String code;

  @JsonField
  private final String text;

  public Option(String code, String text)
  {
    this.code = code;
    this.text = text;
  }

  /**
   * @return the code
   */
  public String getCode()
  {
    return code;
  }

  /**
   * @return the text
   */
  public String getText()
  {
    return text;
  }

  @Override
  public String toString()
  {
    return toStringBuilder(this).add("code", code)
        .add("text", text)
        .toString();
  }

  @Override
  public int hashCode()
  {
    return hash(hash(code), text);
  }

  @Override
  public boolean equals(Object option)
  {
    if (identical(this, option))
      return true;

    final Option that = castIfInstanceOf(Option.class, option);
    return isNotNull(that) && (equal(this.code, that.getCode()) && equal(this.text, that.getText()));
  }
}

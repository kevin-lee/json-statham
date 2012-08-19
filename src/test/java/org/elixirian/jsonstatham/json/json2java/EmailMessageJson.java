/**
 * 
 */
package org.elixirian.jsonstatham.json.json2java;

import static org.elixirian.kommonlee.util.Objects.*;

import org.elixirian.jsonstatham.annotation.JsonConstructor;
import org.elixirian.jsonstatham.annotation.JsonField;
import org.elixirian.jsonstatham.annotation.Json;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2012-02-07)
 */
@Json
public class EmailMessageJson
{
  @JsonField
  public final String from;

  @JsonField
  public final String to;

  @JsonField
  public final String subject;

  @JsonField
  public final String content;

  @JsonConstructor
  public EmailMessageJson(final String from, final String subject, final String content)
  {
    this(from, null, subject, content);
    // System.out.println("\n[DEBUG] LAST IS THE FIRST\n----------------------------------\n@JsonConstructor\n"
    // + "public EmailMessageJson(final String from, final String subject, final String content)");
  }

  @JsonConstructor
  public EmailMessageJson(final String from, final String to, final String subject, final String content)
  {
    this.from = from;
    this.to = to;
    this.subject = subject;
    this.content = content;
    // System.out.println("\n[DEBUG] LAST IS THE FIRST\n----------------------------------\n@JsonConstructor\n"
    // + "public EmailMessageJson(final String from, final String to, final String subject, final String content)");
  }

  public EmailMessageJson(final String from, final String to)
  {
    this(from, to, null, null);
    // System.out.println("\n[DEBUG] LAST IS THE FIRST\n----------------------------------\npublic EmailMessageJson(final String from, final String to)");
  }

  public EmailMessageJson(final String from)
  {
    this(from, null, null, null);
    // System.out.println("\n[DEBUG] LAST IS THE FIRST\n----------------------------------\npublic EmailMessageJson(final String from)");
  }

  @Override
  public int hashCode()
  {
    return hash(from, to, subject, content);
  }

  @Override
  public boolean equals(final Object emailMessageJson)
  {
    if (this == emailMessageJson)
    {
      return true;
    }
    final EmailMessageJson that = castIfInstanceOf(EmailMessageJson.class, emailMessageJson);

    return isNotNull(that)
        && (equal(this.from, that.from) && equal(this.to, that.to) && equal(this.subject, that.subject) && equal(
            this.content, that.content));
  }

  @Override
  public String toString()
  {
    /* @formatter:off */
    return toStringBuilder(this)
        .add("from", from)
        .add("to", to)
        .add("subject", subject)
        .add("content", content)
        .toString();
    /* @formatter:on */

  }
}

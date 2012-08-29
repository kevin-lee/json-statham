/**
 * This project is licensed under the Apache License, Version 2.0
 * if the following condition is met:
 * (otherwise it cannot be used by anyone but the author, Kevin, only)
 *
 * The original KommonLee project is owned by Lee, Seong Hyun (Kevin).
 *
 * -What does it mean to you?
 * Nothing, unless you want to take the ownership of
 * "the original project" (not yours or forked & modified one).
 * You are free to use it for both non-commercial and commercial projects
 * and free to modify it as the Apache License allows.
 *
 * -So why is this condition necessary?
 * It is only to protect the original project (See the case of Java).
 *
 *
 * Copyright 2009 Lee, Seong Hyun (Kevin)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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

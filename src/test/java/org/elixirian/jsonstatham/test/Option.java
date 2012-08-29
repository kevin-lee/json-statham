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
package org.elixirian.jsonstatham.test;

import static org.elixirian.kommonlee.util.Objects.*;

import org.elixirian.jsonstatham.annotation.JsonField;
import org.elixirian.jsonstatham.annotation.Json;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-09-07)
 */
@Json
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

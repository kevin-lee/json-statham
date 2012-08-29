/**
 * This project is licensed under the Apache License, Version 2.0
 * if the following condition is met:
 * (otherwise it cannot be used by anyone but the author, Kevin, only)
 *
 * The original JSON Statham project is owned by Lee, Seong Hyun (Kevin).
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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.elixirian.jsonstatham.annotation.JsonField;
import org.elixirian.jsonstatham.annotation.Json;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-09-07)
 */
@Json
public abstract class AbstractItemDefinition implements ItemDefinition
{
  protected static final List<Option> EMPTY_IMMUTABLE_OPTION_LIST =
    Collections.unmodifiableList(Arrays.<Option> asList());

  @JsonField
  private final String name;

  @JsonField
  private final String instructions;

  public AbstractItemDefinition(String name, String instructions)
  {
    this.name = name;
    this.instructions = instructions;
  }

  @Override
  public String getName()
  {
    return name;
  }

  @Override
  public String getInstructions()
  {
    return instructions;
  }

  @Override
  public String toString()
  {
    return toStringBuilder(this).add("name", name)
        .add("instructions", instructions)
        .toString();
  }

  @Override
  public int hashCode()
  {
    return hash(hash(name), instructions);
  }

  @Override
  public boolean equals(Object abstractItemDefinition)
  {
    if (identical(this, abstractItemDefinition))
      return true;

    final AbstractItemDefinition that = castIfInstanceOf(AbstractItemDefinition.class, abstractItemDefinition);
    return isNotNull(that) && (equal(this.name, that.getName()) && equal(this.instructions, that.instructions));
  }
}

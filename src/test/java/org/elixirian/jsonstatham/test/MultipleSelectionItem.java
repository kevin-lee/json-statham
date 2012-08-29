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

import java.util.List;

import org.elixirian.jsonstatham.annotation.JsonField;
import org.elixirian.jsonstatham.annotation.Json;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-09-07)
 */
@Json
public class MultipleSelectionItem extends AbstractItemDefinition
{
  private static final MultipleSelectionItem EMPTY_MULTIPLE_SELECTION_ITEM = new MultipleSelectionItem("", "",
      EMPTY_IMMUTABLE_OPTION_LIST);

  @JsonField
  private final List<Option> options;

  public MultipleSelectionItem(String name, String instructions, List<Option> options)
  {
    super(name, instructions);
    this.options = options;
  }

  /**
   * @return the List containing options.
   */
  @Override
  public List<Option> getOptions()
  {
    return options;
  }

  @Override
  public String toString()
  {
    return toStringBuilder(this).add("super", super.toString())
        .add("options", options)
        .toString();
  }

  public static final MultipleSelectionItem emptyMultipleSelectionItem()
  {
    return EMPTY_MULTIPLE_SELECTION_ITEM;
  }

  @Override
  public int hashCode()
  {
    return hash(super.hashCode(), options);
  }

  @Override
  public boolean equals(Object multipleSelectionItem)
  {
    if (!super.equals(multipleSelectionItem))
      return false;

    if (identical(this, multipleSelectionItem))
      return true;

    final MultipleSelectionItem that = castIfInstanceOf(MultipleSelectionItem.class, multipleSelectionItem);
    return isNotNull(that) && (equal(this.options, that.getOptions()));
  }
}

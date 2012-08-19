/**
 * 
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

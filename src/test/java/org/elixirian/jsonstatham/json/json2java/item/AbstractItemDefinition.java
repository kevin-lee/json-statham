package org.elixirian.jsonstatham.json.json2java.item;

import static org.elixirian.kommonlee.util.Objects.*;

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
  @JsonField
  private final String name;

  @JsonField
  private final String instructions;

  @JsonField(name = "questionVersion")
  private final ItemVersion itemVersion;

  public AbstractItemDefinition(final String name, final String instructions, final ItemVersion itemVersion)
  {
    this.name = name;
    this.instructions = instructions;
    this.itemVersion = itemVersion;
  }

  @Override
  public ItemVersion getItemVersion()
  {
    return itemVersion;
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

  /**
   * returns {@link ItemDefinitions#EMPTY_IMMUTABLE_OPTION_LIST}.
   * 
   * @return {@link ItemDefinitions#EMPTY_IMMUTABLE_OPTION_LIST}
   */
  @Override
  public List<Option> getOptions()
  {
    return ItemDefinitions.EMPTY_IMMUTABLE_OPTION_LIST;
  }

  /**
   * Returns 0.
   * 
   * @return 0
   */
  @Override
  public int sizeOfOptions()
  {
    return 0;
  }

  @Override
  public int hashCode()
  {
    return hash(name, instructions, itemVersion);
  }

  @Override
  public boolean equals(final Object itemDefinition)
  {
    if (this == itemDefinition)
    {
      return true;
    }
    final ItemDefinition that = castIfInstanceOf(ItemDefinition.class, itemDefinition);
    return isNotNull(that)
        && (equal(this.name, that.getName()) && equal(this.instructions, that.getInstructions()) && equal(
            this.itemVersion, that.getItemVersion()));
  }

  @Override
  public String toString()
  {
    /* @formatter:off */
		return toStringBuilder(this)
				.add("name", name)
				.add("instructions", instructions)
				.add("itemVersion", itemVersion)
				.toString();
		/* @formatter:on */
  }
}

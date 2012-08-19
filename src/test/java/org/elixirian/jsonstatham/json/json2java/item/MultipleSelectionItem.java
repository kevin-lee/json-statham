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
public class MultipleSelectionItem extends AbstractItemDefinition implements ItemDefinition
{
  private static final MultipleSelectionItem EMPTY_MULTIPLE_SELECTION_MULTI_SELECT_ITEM = new MultipleSelectionItem("",
      "", ItemVersion.MULTI_SELECT, ItemDefinitions.EMPTY_IMMUTABLE_OPTION_LIST);
  private static final MultipleSelectionItem EMPTY_MULTIPLE_SELECTION_SINGLE_SELECT_ITEM = new MultipleSelectionItem(
      "", "", ItemVersion.SINGLE_SELECT, ItemDefinitions.EMPTY_IMMUTABLE_OPTION_LIST);

  @JsonField
  private final List<Option> options;

  public MultipleSelectionItem(final String name, final String instructions, final ItemVersion itemVersion,
      final List<Option> options)
  {
    super(name, instructions, itemVersion);
    this.options = options;
  }

  /**
   * Returns the List containing options.
   * 
   * @return the List containing options.
   */
  @Override
  public List<Option> getOptions()
  {
    return options;
  }

  /**
   * Returns the number of {@link Option} in this {@link MultipleSelectionItem}.
   * 
   * @return The number of {@link Option} in this {@link MultipleSelectionItem}.
   */
  @Override
  public int sizeOfOptions()
  {
    return options.size();
  }

  @Override
  public int hashCode()
  {
    return hash(super.hashCode(), options);
  }

  @Override
  public boolean equals(final Object itemDefinition)
  {
    if (super.equals(itemDefinition))
    {
      return equal(this.options, ((ItemDefinition) itemDefinition).getOptions());
    }
    return false;
  }

  @Override
  public String toString()
  {
    /* @formatter:off */
		return toStringBuilder(this)
				.add("super",
						 super.toString())
				.add("options", options)
				.toString();
		/* @formatter:on */
  }

  public static final MultipleSelectionItem emptyMultipleSelectionMultiSelectItem()
  {
    return EMPTY_MULTIPLE_SELECTION_MULTI_SELECT_ITEM;
  }

  public static final MultipleSelectionItem emptyMultipleSelectionSingleSelectItem()
  {
    return EMPTY_MULTIPLE_SELECTION_SINGLE_SELECT_ITEM;
  }

  @Override
  public boolean isMultipleChoice()
  {
    return isMultipleChoice0();
  }

  private boolean isMultipleChoice0()
  {
    return true;
  }

  @Override
  public boolean isNotMultipleChoice()
  {
    return !isMultipleChoice0();
  }
}

/**
 * 
 */
package org.elixirian.jsonstatham.test;

import static org.elixirian.common.util.Objects.*;

import java.util.List;

import org.elixirian.jsonstatham.annotation.JsonField;
import org.elixirian.jsonstatham.annotation.JsonObject;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-09-07)
 */
@JsonObject
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

package org.elixirian.jsonstatham.json.json2java.item;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-09-07)
 */
public class FreeInputItem extends AbstractItemDefinition implements ItemDefinition
{
  private static final FreeInputItem EMPTY_FREE_INPUT_TEXT_ITEM = new FreeInputItem("", "", ItemVersion.TEXT);
  private static final FreeInputItem EMPTY_FREE_INPUT_NUMBER_ITEM = new FreeInputItem("", "", ItemVersion.NUMBER);
  private static final FreeInputItem EMPTY_FREE_INPUT_SLIDE_ITEM = new FreeInputItem("", "", ItemVersion.SLIDE);

  public FreeInputItem(final String name, final String instructions, final ItemVersion itemVersion)
  {
    super(name, instructions, itemVersion);
  }

  public static FreeInputItem emptyFreeInputTextItem()
  {
    return EMPTY_FREE_INPUT_TEXT_ITEM;
  }

  public static FreeInputItem emptyFreeInputNumberItem()
  {
    return EMPTY_FREE_INPUT_NUMBER_ITEM;
  }

  public static FreeInputItem emptyFreeInputSlideItem()
  {
    return EMPTY_FREE_INPUT_SLIDE_ITEM;
  }

  @Override
  public boolean isMultipleChoice()
  {
    return isMultipleChoice0();
  }

  private boolean isMultipleChoice0()
  {
    return false;
  }

  @Override
  public boolean isNotMultipleChoice()
  {
    return !isMultipleChoice0();
  }
}

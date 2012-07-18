package org.elixirian.jsonstatham.json.json2java.item;
/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-08-17)
 */
public enum ItemVersion
{
	NUMBER, TEXT, SINGLE_SELECT, MULTI_SELECT, SLIDE;

	/**
	 * {@link ItemVersion#MULTI_SELECT}
	 */
	public static ItemVersion DEFAULT_ITEM_VERSION = MULTI_SELECT;

	public static ItemVersion[] EMPTY_ITEM_VERSIONS = new ItemVersion[0];

	public static boolean isMultipleChoice(final ItemVersion itemVersion)
	{
		return SINGLE_SELECT == itemVersion | MULTI_SELECT == itemVersion;
	}
}

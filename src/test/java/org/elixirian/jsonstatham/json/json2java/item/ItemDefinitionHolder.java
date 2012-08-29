/**
 *
 */
package org.elixirian.jsonstatham.json.json2java.item;

import static org.elixirian.kommonlee.util.Objects.*;

import org.elixirian.jsonstatham.annotation.JsonConstructor;
import org.elixirian.jsonstatham.annotation.JsonField;
import org.elixirian.jsonstatham.annotation.Json;

/**
 * <pre>
 *     ___  _____                                _____
 *    /   \/    /_________  ___ ____ __ ______  /    /   ______  ______
 *   /        / /  ___ \  \/  //___// //     / /    /   /  ___ \/  ___ \
 *  /        \ /  _____/\    //   //   __   / /    /___/  _____/  _____/
 * /____/\____\\_____/   \__//___//___/ /__/ /________/\_____/ \_____/
 * </pre>
 *
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2012-07-18)
 */
@Json
public class ItemDefinitionHolder
{
	@JsonField(name = "definition")
	private final ItemDefinition itemDefinition;

	@JsonConstructor
	public ItemDefinitionHolder(final ItemDefinition itemDefinition)
	{
		this.itemDefinition = itemDefinition;
	}

	public ItemDefinition getItemDefinition()
	{
		return itemDefinition;
	}

	@Override
	public int hashCode()
	{
		return hash(itemDefinition);
	}

	@Override
	public boolean equals(final Object itemDefinitionHolder)
	{
		if (this == itemDefinitionHolder)
		{
			return true;
		}
		final ItemDefinitionHolder that = castIfInstanceOf(ItemDefinitionHolder.class, itemDefinitionHolder);
		return null != that && (equal(this.itemDefinition, that.getItemDefinition()));
	}

	@Override
	public String toString()
	{
		/* @formatter:off */
    return toStringBuilder(this)
        .add("itemDefinition", itemDefinition)
        .toString();
    /* @formatter:on */
	}
}

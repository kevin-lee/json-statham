package org.elixirian.jsonstatham.json.json2java.item;

import static org.elixirian.jsonstatham.json.json2java.item.ItemVersion.*;
import static org.elixirian.kommonlee.util.MessageFormatter.*;
import static org.elixirian.kommonlee.util.collect.Lists.*;

import java.util.Collections;
import java.util.List;

import org.elixirian.kommonlee.type.GenericBuilder;
import org.elixirian.kommonlee.util.CommonConstants;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-09-07)
 */
public final class ItemDefinitions
{
	public static final List<Option> EMPTY_IMMUTABLE_OPTION_LIST = Collections.emptyList();

	public static class MultipleSelectionItemBuilder implements GenericBuilder<MultipleSelectionItem>
	{
		String name;
		String instructions;
		ItemVersion itemVersion;
		List<Option> options;

		public MultipleSelectionItemBuilder()
		{
			this.options = newArrayList();
		}

		public MultipleSelectionItemBuilder name(final String name)
		{
			this.name = name;
			return this;
		}

		public MultipleSelectionItemBuilder instructions(final String instructions)
		{
			this.instructions = instructions;
			return this;
		}

		public MultipleSelectionItemBuilder itemVersion(final ItemVersion itemVersion)
		{
			this.itemVersion = itemVersion;
			return this;
		}

		public MultipleSelectionItemBuilder addOption(final String code, final String text)
		{
			this.options.add(new Option(code, text));
			return this;
		}

		public MultipleSelectionItemBuilder addOption(final Option option)
		{
			this.options.add(option);
			return this;
		}

		public MultipleSelectionItemBuilder addAllOptions(final List<Option> options)
		{
			this.options.addAll(options);
			return this;
		}

		@Override
		public MultipleSelectionItem build()
		{
			return new MultipleSelectionItem(name, instructions, itemVersion, options);
		}

	}

	public static MultipleSelectionItemBuilder multipleSelectionItemBuilder()
	{
		return new MultipleSelectionItemBuilder();
	}

	public static class FreeInputItemBuilder implements GenericBuilder<FreeInputItem>
	{
		String name;
		String instructions;
		ItemVersion itemVersion;

		public FreeInputItemBuilder name(final String name)
		{
			this.name = name;
			return this;
		}

		public FreeInputItemBuilder instructions(final String instructions)
		{
			this.instructions = instructions;
			return this;
		}

		public FreeInputItemBuilder itemVersion(final ItemVersion itemVersion)
		{
			this.itemVersion = itemVersion;
			return this;
		}

		@Override
		public FreeInputItem build()
		{
			return new FreeInputItem(name, instructions, itemVersion);
		}

	}

	public static FreeInputItemBuilder freeInputItemBuilder()
	{
		return new FreeInputItemBuilder();
	}

	private ItemDefinitions()
	{
		throw new IllegalStateException(getClass().getName() + CommonConstants.CANNOT_BE_INSTANTIATED);
	}

	public static Class<? extends ItemDefinition> getItemDefinitionClassByItemVersion(final ItemVersion itemVersion)
	{
		/* @formatter:off */
		return (SINGLE_SELECT == itemVersion || MULTI_SELECT == itemVersion) ?
				MultipleSelectionItem.class :
				FreeInputItem.class;
		/* @formatter:on */
	}

	public static ItemDefinition newItemDefinition(final ItemVersion itemVersion, final String name,
			final String instructions, final List<Option> options)
	{
		if (SINGLE_SELECT == itemVersion || MULTI_SELECT == itemVersion)
		{
			return new MultipleSelectionItem(name, instructions, itemVersion, newArrayList(options));
		}
		return new FreeInputItem(name, instructions, itemVersion);
	}

	public static ItemDefinition getEmptyItemDefinition(final ItemVersion itemVersion)
	{
		ItemDefinition itemDefinition;
		switch (itemVersion)
		{
			case MULTI_SELECT:
				itemDefinition = MultipleSelectionItem.emptyMultipleSelectionMultiSelectItem();
				break;
			case SINGLE_SELECT:
				itemDefinition = MultipleSelectionItem.emptyMultipleSelectionSingleSelectItem();
				break;
			case NUMBER:
				itemDefinition = FreeInputItem.emptyFreeInputNumberItem();
				break;
			case TEXT:
				itemDefinition = FreeInputItem.emptyFreeInputTextItem();
				break;
			case SLIDE:
				itemDefinition = FreeInputItem.emptyFreeInputSlideItem();
				break;
			default:
				throw new IllegalArgumentException(format("It must not happen!!! [itemVersion: %s]", itemVersion));
		}
		return itemDefinition;
	}
}

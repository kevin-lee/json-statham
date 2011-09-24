/**
 *
 */
package org.elixirian.jsonstatham.test;

import static java.lang.Boolean.*;
import static org.elixirian.kommonlee.util.Objects.*;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.elixirian.jsonstatham.annotation.JsonConstructor;
import org.elixirian.jsonstatham.annotation.JsonField;
import org.elixirian.jsonstatham.annotation.JsonObject;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2011-09-09)
 */
@JsonObject
public class ItemConfig implements Serializable
{
  private static final long serialVersionUID = 1L;

  public static final List<String> EMPTY_CORRECT_ANSWERS = Collections.emptyList();

  private static class NullItemConfig extends ItemConfig
  {
    private static final long serialVersionUID = 1L;

    NullItemConfig()
    {
      super(false, null, null, null, EMPTY_CORRECT_ANSWERS);
    }

    @Override
    public boolean isNull()
    {
      return true;
    }
  }

  public static final ItemConfig NULL_ITEM_CONFIG = new NullItemConfig();

  public static final ItemConfig DEFAULT_MULTIPLE_CHOICE_CONFIG = ItemConfig.newInstance(false, true, false, true);

  @JsonField
  private final boolean optional;

  @JsonField
  private final Boolean idAutomated;

  @JsonField
  private final Boolean optionsRandomised;

  @JsonField
  private final Boolean optionCodesShown;

  @JsonField
  private final List<String> correctAnswers;

  @JsonConstructor
  public ItemConfig(final boolean optional, final Boolean idAutomated, final Boolean optionsRandomised,
      final Boolean optionCodesShown, final List<String> correctAnswers)
  {
    this.optional = optional;
    this.idAutomated = idAutomated;
    this.optionsRandomised = optionsRandomised;
    this.optionCodesShown = optionCodesShown;
    /* @formatter:off */
    this.correctAnswers = null == correctAnswers || correctAnswers.isEmpty() ?
                            EMPTY_CORRECT_ANSWERS :
                            Collections.unmodifiableList(correctAnswers);
    /* @formatter:on */
  }

  public static ItemConfig newInstance(final boolean optional, final boolean idAutomated,
      final boolean optionsRandomised, final boolean optionCodesShown, final String... correctAnswers)
  {
    /* @formatter:off */
		return new ItemConfig(optional,
													 valueOf(idAutomated),
													 valueOf(optionsRandomised),
													 valueOf(optionCodesShown),
													 Arrays.asList(correctAnswers));
		/* @formatter:on */
  }

  public boolean isOptional()
  {
    return optional;
  }

  public Boolean isIdAutomated()
  {
    return idAutomated;
  }

  public Boolean isOptionsRandomised()
  {
    return optionsRandomised;
  }

  public Boolean isOptionCodesShown()
  {
    return optionCodesShown;
  }

  public List<String> getCorrectAnswers()
  {
    return correctAnswers;
  }

  public boolean isNull()
  {
    /* @formatter:off */
		return this == NULL_ITEM_CONFIG ||
						(!optional &&
						 null == idAutomated &&
						 null == optionsRandomised &&
						 null == optionCodesShown &&
						 correctAnswers.isEmpty());
		/* @formatter:on */
  }

  @Override
  public int hashCode()
  {
    return hashObjects(hash(optional), idAutomated, optionsRandomised, optionCodesShown, correctAnswers);
  }

  @Override
  public boolean equals(final Object itemState)
  {
    if (this == itemState)
    {
      return true;
    }
    final ItemConfig that = castIfInstanceOf(ItemConfig.class, itemState);
    /* @formatter:off */
		return isNotNull(that) &&
							(equal(this.optional, that.isOptional()) &&
							 equal(this.idAutomated, that.isIdAutomated()) &&
							 equal(this.optionsRandomised, that.isOptionsRandomised()) &&
							 equal(this.optionCodesShown, that.isOptionCodesShown()) &&
							 equal(this.correctAnswers, that.getCorrectAnswers()));
		/* @formatter:on */
  }

  @Override
  public String toString()
  {
    /* @formatter:off */
		return toStringBuilder(this)
				.add("optional", optional)
				.add("idAutomated", idAutomated)
				.add("optionsRandomised", optionsRandomised)
				.add("optionCodesShown", optionCodesShown)
				.add("correctAnswers", correctAnswers)
				.toString();
		/* @formatter:on */
  }
}

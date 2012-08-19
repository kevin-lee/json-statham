/**
 *
 */
package org.elixirian.jsonstatham.test;

import static java.lang.Boolean.*;
import static org.elixirian.kommonlee.collect.Lists.*;
import static org.elixirian.kommonlee.util.Objects.*;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.elixirian.jsonstatham.annotation.JsonConstructor;
import org.elixirian.jsonstatham.annotation.JsonField;
import org.elixirian.jsonstatham.annotation.Json;
import org.elixirian.kommonlee.type.GenericBuilder;
import org.elixirian.kommonlee.util.Objects;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2011-09-09)
 */
@Json
public class ItemConfig implements Serializable
{
  private static final long serialVersionUID = 1L;

  public static final List<CorrectAnswer> EMPTY_CORRECT_ANSWERS = Collections.emptyList();

  private static class NullItemConfig extends ItemConfig
  {
    private static final long serialVersionUID = 1L;

    NullItemConfig()
    {
      super(false, null, null, null, false, null, EMPTY_CORRECT_ANSWERS);
    }

    @Override
    public boolean isNull()
    {
      return true;
    }
  }

  public static final ItemConfig NULL_ITEM_CONFIG = new NullItemConfig();

  /* @formatter:off */
  public static final ItemConfig DEFAULT_MULTIPLE_CHOICE_CONFIG = ItemConfig.builder()
                                                                                .mandatory()
                                                                                .automateId()
                                                                                .unrandomizeOptions()
                                                                                .showOptionCodes()
                                                                                .disallowOtherAnswer()
                                                                              .build();
  /* @formatter:on */

  @JsonField
  private final boolean optional;

  @JsonField
  private final Boolean idAutomated;

  @JsonField
  private final Boolean optionsRandomised;

  @JsonField
  private final Boolean optionCodesShown;

  @JsonField
  private final boolean otherAnswerEnabled;

  @JsonField
  private final String otherAnswerLabel;

  @JsonField
  private final List<CorrectAnswer> correctAnswers;

  public static class Builder implements GenericBuilder<ItemConfig>
  {
    boolean optional;

    Boolean idAutomated;

    Boolean optionsRandomised;

    Boolean optionCodesShown;

    boolean otherAnswerEnabled;

    String otherAnswerLabel;

    List<CorrectAnswer> correctAnswers;

    public Builder()
    {
    }

    public Builder(final ItemConfig itemConfig)
    {
      this.optional = itemConfig.isOptional();
      this.idAutomated = itemConfig.isIdAutomated();
      this.optionsRandomised = itemConfig.isOptionsRandomised();
      this.optionCodesShown = itemConfig.isOptionCodesShown();
      this.correctAnswers = newArrayList(itemConfig.getCorrectAnswers());
      this.otherAnswerEnabled = itemConfig.isOtherAnswerEnabled();
      this.otherAnswerLabel = itemConfig.getOtherAnswerLabel();
    }

    public Builder optional()
    {
      this.optional = true;
      return this;
    }

    public Builder mandatory()
    {
      this.optional = false;
      return this;
    }

    public Builder automateId()
    {
      this.idAutomated = TRUE;
      return this;
    }

    public Builder unautomateId()
    {
      this.idAutomated = FALSE;
      return this;
    }

    public Builder randomizeOptions()
    {
      this.optionsRandomised = TRUE;
      return this;
    }

    public Builder unrandomizeOptions()
    {
      this.optionsRandomised = FALSE;
      return this;
    }

    public Builder showOptionCodes()
    {
      this.optionCodesShown = TRUE;
      return this;
    }

    public Builder hideOptionCodes()
    {
      this.optionCodesShown = FALSE;
      return this;
    }

    public Builder addCorrectAnswer(final CorrectAnswer correctAnswer)
    {
      this.correctAnswers.add(correctAnswer);
      return this;
    }

    public Builder addAllCorrectAnswers(final CorrectAnswer... correctAnswers)
    {
      this.correctAnswers.addAll(Arrays.asList(correctAnswers));
      return this;
    }

    public Builder allowOtherAnswer()
    {
      this.otherAnswerEnabled = true;
      return this;
    }

    public Builder disallowOtherAnswer()
    {
      this.otherAnswerEnabled = true;
      return this;
    }

    public Builder otherAnswerLabel(final String otherAnswerLabel)
    {
      this.otherAnswerLabel = otherAnswerLabel;
      return this;
    }

    @Override
    public ItemConfig build()
    {
      return new ItemConfig(this);
    }
  }

  @JsonConstructor
  ItemConfig(final boolean optional, final Boolean idAutomated, final Boolean optionsRandomised,
      final Boolean optionCodesShown, final boolean otherAnswerEnabled, final String otherAnswerLabel,
      final List<CorrectAnswer> correctAnswers)
  {
    this.optional = optional;
    this.idAutomated = idAutomated;
    this.optionsRandomised = optionsRandomised;
    this.optionCodesShown = optionCodesShown;
    this.otherAnswerEnabled = otherAnswerEnabled;
    this.otherAnswerLabel = otherAnswerLabel;
    /* @formatter:off */
    this.correctAnswers = null == correctAnswers || correctAnswers.isEmpty() ?
        EMPTY_CORRECT_ANSWERS :
          Collections.unmodifiableList(correctAnswers);
    /* @formatter:on */
  }

  public static ItemConfig newInstance(final boolean optional, final boolean idAutomated,
      final boolean optionsRandomised, final boolean optionCodesShown, final boolean otherAnswerEnabled,
      final String otherAnswerLabel, final CorrectAnswer... correctAnswers)
  {
    /* @formatter:off */
    return new ItemConfig(optional,
                           valueOf(idAutomated),
                           valueOf(optionsRandomised),
                           valueOf(optionCodesShown),
                           otherAnswerEnabled,
                           otherAnswerLabel,
                           Arrays.asList(correctAnswers));
    /* @formatter:on */
  }

  private ItemConfig(final Builder builder)
  {
    this(builder.optional, builder.idAutomated, builder.optionsRandomised, builder.optionCodesShown,
        builder.otherAnswerEnabled, builder.otherAnswerLabel, builder.correctAnswers);
  }

  public static Builder builder()
  {
    return new Builder();
  }

  public static Builder builder(final ItemConfig itemConfig)
  {
    return new Builder(itemConfig);
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

  public boolean isOtherAnswerEnabled()
  {
    return otherAnswerEnabled;
  }

  public String getOtherAnswerLabel()
  {
    return otherAnswerLabel;
  }

  public List<CorrectAnswer> getCorrectAnswers()
  {
    return correctAnswers;
  }

  public boolean isNull()
  {
    return isNull0();
  }

  private boolean isNull0()
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

  public boolean isNotNull()
  {
    return !isNull0();
  }

  @Override
  public int hashCode()
  {
    return hashObjects(
        hash(hashObjects(hash(optional), idAutomated, optionsRandomised, optionCodesShown), otherAnswerEnabled),
        otherAnswerLabel, correctAnswers);
  }

  @Override
  public boolean equals(final Object itemConfig)
  {
    if (this == itemConfig)
    {
      return true;
    }
    final ItemConfig that = castIfInstanceOf(ItemConfig.class, itemConfig);
    /* @formatter:off */
    return Objects.isNotNull(that) &&
              (equal(this.optional, that.isOptional()) &&
               equal(this.idAutomated, that.isIdAutomated()) &&
               equal(this.optionsRandomised, that.isOptionsRandomised()) &&
               equal(this.optionCodesShown, that.isOptionCodesShown()) &&
               equal(this.otherAnswerEnabled, that.isOtherAnswerEnabled()) &&
               equal(this.otherAnswerLabel, that.getOtherAnswerLabel()) &&
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
        .add("otherAnswerEnabled", otherAnswerEnabled)
        .add("otherAnswerLabel", otherAnswerLabel)
        .add("correctAnswers", correctAnswers)
        .toString();
    /* @formatter:on */
  }
}

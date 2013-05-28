/**
 *
 */
package org.elixirian.jsonstatham.json.json2java.interaction;

import static org.elixirian.kommonlee.util.Objects.*;
import static org.elixirian.kommonlee.util.Strings.*;
import static org.elixirian.kommonlee.util.collect.Sets.*;

import java.util.Collection;
import java.util.Collections;
import java.util.NavigableSet;
import java.util.SortedSet;

import org.elixirian.jsonstatham.annotation.Json;
import org.elixirian.jsonstatham.annotation.JsonField;
import org.elixirian.kommonlee.type.GenericBuilder;
import org.elixirian.kommonlee.util.Objects;
import org.elixirian.kommonlee.util.collect.Sets;

import org.elixirian.kommonlee.functional.Functions;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2012-04-24)
 */
@Json
public class InteractionConfig
{
  public static final SortedSet<Integer> EMPTY_INTEGER_SORTED_SET =
    Collections.unmodifiableSortedSet(Sets.<Integer> newTreeSet());

  public static final String NAME_FOR_PUBLIC = "properties";

  @JsonField
  private final String submitMessage;

  @JsonField
  private final String submitRedirectUrl;

  @JsonField
  private final SortedSet<Integer> pageBreaks;

  @JsonField
  private final boolean displayResultsOnSubmit;

  public static class NullInteractionConfig extends InteractionConfig
  {
    public NullInteractionConfig()
    {
      /* @formatter:off */
			super(builder()
						.submitMessage(null)
						.submitRedirectUrl(null)
						.noPageBreaks()
						.doNotDisplayResultsOnSubmit());
			/* @formatter:on */
    }

  }

  public static final InteractionConfig NULL_INTERACTION_CONFIG = new NullInteractionConfig();

  public static class Builder implements GenericBuilder<InteractionConfig>
  {
    String submitMessage;

    String submitRedirectUrl;

    NavigableSet<Integer> pageBreakSet;

    boolean displayResultsOnSubmit;

    public Builder()
    {
      pageBreakSet = newTreeSet(Functions.INTEGER_ASCENDING_ORDER);
    }

    public Builder submitMessage(final String submitMessage)
    {
      this.submitMessage = submitMessage;
      return this;
    }

    public Builder submitRedirectUrl(final String submitRedirectUrl)
    {
      this.submitRedirectUrl = submitRedirectUrl;
      return this;
    }

    public Builder addPageBreak(final int pageBreak)
    {
      pageBreakSet.add(Integer.valueOf(pageBreak));
      return this;
    }

    public Builder addAllPageBreaks(final int... pageBreaks)
    {
      for (final int pageBreak : pageBreaks)
      {
        pageBreakSet.add(Integer.valueOf(pageBreak));
      }
      return this;
    }

    public Builder setPageBreaks(final int... pageBreaks)
    {
      pageBreakSet.clear();
      addAllPageBreaks(pageBreaks);
      return this;
    }

    public Builder noPageBreaks()
    {
      pageBreakSet.clear();
      return this;
    }

    public Builder displayResultsOnSubmit()
    {
      this.displayResultsOnSubmit = true;
      return this;
    }

    public Builder doNotDisplayResultsOnSubmit()
    {
      this.displayResultsOnSubmit = false;
      return this;
    }

    @Override
    public InteractionConfig build()
    {
      return new InteractionConfig(this);
    }
  }

  public static Builder builder()
  {
    return new Builder();
  }

  private InteractionConfig(final Builder builder)
  {
    this(builder.submitMessage, builder.submitRedirectUrl, builder.pageBreakSet, builder.displayResultsOnSubmit);
  }

  public InteractionConfig(final String submitMessage, final String submitRedirectUrl,
      final Collection<Integer> pageBreaks, final boolean displayResultsOnSubmit)
  {
    this.submitMessage = submitMessage;
    /* @formatter:off */
    this.submitRedirectUrl = null == submitRedirectUrl ?
                                null :
                                submitRedirectUrl.trim();
    /* @formatter:on */

    if (null == pageBreaks || pageBreaks.isEmpty())
    {
      this.pageBreaks = EMPTY_INTEGER_SORTED_SET;
    }
    else
    {
      final SortedSet<Integer> pageBreakSet = newTreeSet(Functions.INTEGER_ASCENDING_ORDER, pageBreaks);
      this.pageBreaks = Collections.unmodifiableSortedSet(pageBreakSet);
    }
    this.displayResultsOnSubmit = displayResultsOnSubmit;
  }

  public String getSubmitMessage()
  {
    return submitMessage;
  }

  public String getSubmitRedirectUrl()
  {
    return submitRedirectUrl;
  }

  public String getSubmitRedirectUrlForUse()
  {
    final String trimmedUrl = nullSafeTrim(submitRedirectUrl);
    /* @formatter:off */
    return trimmedUrl.isEmpty() ? "" :
              trimmedUrl.contains("://") ?
                  trimmedUrl :
                  "http://" + trimmedUrl;
    /* @formatter:on */
  }

  public SortedSet<Integer> getPageBreaks()
  {
    // return getCopyOfPageBreaks();
    return pageBreaks;
  }

  // public int[] getCopyOfPageBreaks()
  // {
  // final int length = pageBreaks.length;
  // final int[] copyOfPageBreaks = new int[length];
  // if (0 < length)
  // {
  // System.arraycopy(pageBreaks, 0, copyOfPageBreaks, 0, length);
  // }
  // return copyOfPageBreaks;
  // }

  public boolean shouldDisplayResultsOnSubmit()
  {
    return displayResultsOnSubmit;
  }

  public boolean isNull()
  {
    return isNull0();
  }

  private boolean isNull0()
  {
    return NULL_INTERACTION_CONFIG.equals(this);
  }

  public boolean isNotNull()
  {
    return !isNull0();
  }

  @Override
  public int hashCode()
  {
    /* @formatter:off */
    return hash(submitMessage,
                 submitRedirectUrl,
                 pageBreaks,
                 Boolean.valueOf(displayResultsOnSubmit));
    /* @formatter:on */
  }

  @Override
  public boolean equals(final Object interactionConfig)
  {
    if (this == interactionConfig)
    {
      return true;
    }
    final InteractionConfig that = castIfInstanceOf(InteractionConfig.class, interactionConfig);
    /* @formatter:off */
		return Objects.isNotNull(that) &&
						(equal(this.submitMessage, that.getSubmitMessage()) &&
						 equal(this.submitRedirectUrl, that.getSubmitRedirectUrl()) &&
						 equal(this.pageBreaks, that.getPageBreaks()) &&
						 equal(this.displayResultsOnSubmit, that.shouldDisplayResultsOnSubmit()));
		/* @formatter:on */
  }

  @Override
  public String toString()
  {
    /* @formatter:off */
		return toStringBuilder(this)
				.add("submitMessage", submitMessage)
				.add("submitRedirectUrl", submitRedirectUrl)
				.add("pageBreaks", pageBreaks)
				.add("displayResultsOnSubmit", displayResultsOnSubmit)
				.toString();
		/* @formatter:on */
  }

}

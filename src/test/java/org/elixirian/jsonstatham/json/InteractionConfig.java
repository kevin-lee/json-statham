package org.elixirian.jsonstatham.json;

import static org.elixirian.kommonlee.collect.Sets.*;
import static org.elixirian.kommonlee.util.Objects.*;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.NavigableSet;
import java.util.SortedSet;

import org.elixirian.jsonstatham.annotation.JsonField;
import org.elixirian.jsonstatham.annotation.JsonObject;
import org.elixirian.kommonlee.collect.Sets;
import org.elixirian.kommonlee.type.GenericBuilder;
import org.elixirian.kommonlee.util.Objects;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2012-04-24)
 */
@JsonObject
public class InteractionConfig
{
  public static final SortedSet<Integer> EMPTY_INTEGER_SORTED_SET =
    Collections.unmodifiableSortedSet(Sets.<Integer> newTreeSet());

  private static final Comparator<Integer> INTEGER_ASCENDING_ODER_COMPARATOR = new Comparator<Integer>() {
    @Override
    public int compare(final Integer integer1, final Integer integer2)
    {
      notNull(integer1);
      notNull(integer2);
      return integer1.compareTo(integer2);
    }
  };

  @JsonField
  private final String submitMessage;

  @JsonField
  private final SortedSet<Integer> pageBreaks;

  public static class NullInteractionConfig extends InteractionConfig
  {

    public NullInteractionConfig()
    {
      /* @formatter:off */
      super(builder()
            .submitMessage(null)
            .noPageBreaks());
      /* @formatter:on */
    }

  }

  public static final InteractionConfig NULL_INTERACTION_CONFIG = new NullInteractionConfig();

  public static class Builder implements GenericBuilder<InteractionConfig>
  {
    String submitMessage;

    NavigableSet<Integer> pageBreakSet;

    public Builder()
    {
      pageBreakSet = newTreeSet(INTEGER_ASCENDING_ODER_COMPARATOR);
    }

    public Builder submitMessage(final String submitMessage)
    {
      this.submitMessage = submitMessage;
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
    this.submitMessage = builder.submitMessage;
    final NavigableSet<Integer> pageBreakSetFromBuilder = builder.pageBreakSet;
    /* @formatter:off */
    final SortedSet<Integer> pageBreakSet =
      null == pageBreakSetFromBuilder || pageBreakSetFromBuilder.isEmpty() ?
          EMPTY_INTEGER_SORTED_SET :
          Collections.unmodifiableSortedSet(newTreeSet(pageBreakSetFromBuilder));
    /* @formatter:on */
    this.pageBreaks = pageBreakSet;
  }

  // public InteractionConfig(final String submitMessage, final int[] pageBreaks)
  public InteractionConfig(final String submitMessage, final Collection<Integer> pageBreaks)
  {
    this.submitMessage = submitMessage;
    // if (null == pageBreaks || 0 == pageBreaks.length)
    if (null == pageBreaks || pageBreaks.isEmpty())
    {
      this.pageBreaks = EMPTY_INTEGER_SORTED_SET;
    }
    else
    {
      final SortedSet<Integer> pageBreakSet = newTreeSet(INTEGER_ASCENDING_ODER_COMPARATOR);
      // for (final int i : pageBreaks)
      for (final Integer integer : pageBreaks)
      {
        // @SuppressWarnings("boxing")
        // final Integer integer = i;
        pageBreakSet.add(integer);
      }
      this.pageBreaks = Collections.unmodifiableSortedSet(pageBreakSet);
    }
  }

  public String getSubmitMessage()
  {
    return submitMessage;
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
    return hash(hash(submitMessage), pageBreaks);
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
             equal(this.pageBreaks, that.getPageBreaks()));
    /* @formatter:on */
  }

  @Override
  public String toString()
  {
    /* @formatter:off */
    return toStringBuilder(this)
        .add("submitMessage", submitMessage)
        .add("pageBreaks", pageBreaks)
        .toString();
    /* @formatter:on */
  }
}

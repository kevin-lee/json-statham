/**
 *
 */
package org.elixirian.jsonstatham.test;

import static org.elixirian.kommonlee.util.Objects.*;

import org.elixirian.jsonstatham.annotation.JsonConstructor;
import org.elixirian.jsonstatham.annotation.JsonField;
import org.elixirian.jsonstatham.annotation.Json;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2012-03-22)
 */
@Json
public class CorrectAnswer
{
	@JsonField
	private final String answer;

	@JsonField
	private final boolean caseSensitive;

	@JsonConstructor
	public CorrectAnswer(final String answer, final boolean caseSensitive)
	{
		this.answer = answer;
		this.caseSensitive = caseSensitive;
	}

	public CorrectAnswer(final String answer)
	{
		this(answer, false);
	}

	public String getAnswer()
	{
		return answer;
	}

	public boolean isCaseSensitive()
	{
		return caseSensitive;
	}

	@Override
	public int hashCode()
	{
		return hash(hash(answer), caseSensitive);
	}

	@Override
	public boolean equals(final Object correctAnswer)
	{
		if (this == correctAnswer)
		{
			return true;
		}
		final CorrectAnswer that = castIfInstanceOf(CorrectAnswer.class, correctAnswer);
		/* @formatter:off */
		return isNotNull(that) &&
						(equal(this.answer, that.getAnswer()) &&
						 equal(this.caseSensitive, that.isCaseSensitive()));
		/* @formatter:on */
	}

	@Override
	public String toString()
	{
		/* @formatter:off */
		return toStringBuilder(this)
				.add("answer", answer)
				.add("caseSensitive", caseSensitive)
				.toString();
		/* @formatter:on */
	}
}

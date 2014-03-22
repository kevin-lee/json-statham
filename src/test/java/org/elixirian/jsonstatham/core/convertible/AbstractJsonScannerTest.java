/**
 * This project is licensed under the Apache License, Version 2.0
 * if the following condition is met:
 * (otherwise it cannot be used by anyone but the author, Kevin, only)
 *
 * The original JSON Statham project is owned by Lee, Seong Hyun (Kevin).
 *
 * -What does it mean to you?
 * Nothing, unless you want to take the ownership of
 * "the original project" (not yours or forked & modified one).
 * You are free to use it for both non-commercial and commercial projects
 * and free to modify it as the Apache License allows.
 *
 * -So why is this condition necessary?
 * It is only to protect the original project (See the case of Java).
 *
 *
 * Copyright 2009 Lee, Seong Hyun (Kevin)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.elixirian.jsonstatham.core.convertible;

import static org.assertj.core.api.Assertions.*;
import static org.elixirian.kommonlee.util.MessageFormatter.*;
import static org.elixirian.kommonlee.util.Objects.*;

import org.elixirian.jsonstatham.core.util.JsonUtil;
import org.elixirian.kommonlee.collect.immutable.ImmutableLists;
import org.elixirian.kommonlee.type.functional.Function1;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

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
 * @version 0.0.1 (2014-03-22)
 */
@RunWith(MockitoJUnitRunner.class)
public class AbstractJsonScannerTest
{
  private Object[] namesAndValues = { "id", 1, "name", "Kevin", "title", "Mr", "email", "kevin@some-email.com",
      "verified", true };
  private String[] onlyTexts = ImmutableLists.listOf(namesAndValues[0], namesAndValues[2], namesAndValues[3],
      namesAndValues[4], namesAndValues[5], namesAndValues[6], namesAndValues[7], namesAndValues[8])
      .map(new Function1<Object, String>() {
        @Override
        public String apply(final Object input)
        {
          return toStringOf(input);
        }
      })
      .toArray(new String[0]);
  private int i = 0;
  private final String jsonString = "{  \"" + namesAndValues[i++] + "\": " + namesAndValues[i++] + ",\t\""
      + namesAndValues[i++] + "\" : \"" + namesAndValues[i++] + "\",\n\"" + namesAndValues[i++] + "\":\""
      + namesAndValues[i++] + "\"\n,  \"" + namesAndValues[i++] + "\":\"" + namesAndValues[i++] + "\",\n\t\""
      + namesAndValues[i++] + "\":" + namesAndValues[i++] + "}";

  private final AbstractJsonScanner jsonScanner = new AbstractJsonScanner(jsonString) {

    @Override
    protected JsonObject newJsonObjectConvertible(final JsonScanner jsonScanner)
    {
      return new OrderedJsonObject(jsonScanner);
    }

    @Override
    protected AbstractJsonArray newJsonArrayConvertible(final JsonScanner jsonScanner)
    {
      return JsonArrayWithOrderedJsonObject.newJsonArray(jsonScanner);
    }
  };

  @BeforeClass
  public static void setUpBeforeClass() throws Exception
  {
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception
  {
  }

  @Before
  public void setUp() throws Exception
  {
  }

  @After
  public void tearDown() throws Exception
  {
  }

  @Test
  public final void testAbstractJsonScanner() throws Exception
  {
    /* given */
    final String expected = jsonString;
    /* when */

    final AbstractJsonScanner actual = new AbstractJsonScanner(expected) {

      @Override
      protected JsonObject newJsonObjectConvertible(final JsonScanner jsonScanner)
      {
        return new OrderedJsonObject(jsonScanner);
      }

      @Override
      protected AbstractJsonArray newJsonArrayConvertible(final JsonScanner jsonScanner)
      {
        return JsonArrayWithOrderedJsonObject.newJsonArray(jsonScanner);
      }
    };

    /* then */
    assertThat(actual.getJsonString()).isEqualTo(expected);
  }

  @Test
  public final void testGetJsonString() throws Exception
  {
    /* given */
    final String expected = jsonString;
    /* when */

    final AbstractJsonScanner actual = new AbstractJsonScanner(expected) {

      @Override
      protected JsonObject newJsonObjectConvertible(final JsonScanner jsonScanner)
      {
        return new OrderedJsonObject(jsonScanner);
      }

      @Override
      protected AbstractJsonArray newJsonArrayConvertible(final JsonScanner jsonScanner)
      {
        return JsonArrayWithOrderedJsonObject.newJsonArray(jsonScanner);
      }
    };

    /* then */
    assertThat(actual.getJsonString()).isEqualTo(expected);
  }

  @Test
  public final void testNextChar() throws Exception
  {
    /* given */
    final char[] expectedChars = jsonString.toCharArray();

    final int length = jsonString.length();
    for (int i = 0; i < length; i++)
    {
      final char expected = expectedChars[i];
      /* when */
      final char actual = jsonScanner.nextChar();
      /* then */
      assertThat(actual).isEqualTo(expected);
    }
  }

  @Test
  public final void testNextNonWhiteSpaceChar() throws Exception
  {
    /* given */
    final char[] expectedChars = jsonString.toCharArray();

    final int length = jsonString.length();
    for (int i = 0; i < length; i++)
    {
      char expected = expectedChars[i];
      while (i < length && Character.isWhitespace(expected))
      {
        i++;
        expected = expectedChars[i];
      }
      assertThat(Character.isWhitespace(expected)).isFalse();

      /* when */
      final char actual = jsonScanner.nextNonWhiteSpaceChar();
      /* then */
      assertThat(actual).isEqualTo(expected);
    }
  }

  @Test
  public final void testNextStringUntilQuoteEnded() throws Exception
  {
    /* given */
    final char[] expectedChars = jsonString.toCharArray();

    final int length = onlyTexts.length;
    for (int i = 0; i < length; i++)
    {
      final String expected = onlyTexts[i];

      char c = jsonScanner.nextChar();
      /* when */
      while (c != '"' & c != '\'')
      {
        c = jsonScanner.nextChar();
      }

      final String actual = jsonScanner.nextStringUntilQuoteEnded(c);
      /* then */
      assertThat(actual).isEqualTo(expected);
    }
  }

  @Test
  public final void testNextValue() throws Exception
  {
    /* given */
    final char[] expectedChars = jsonString.toCharArray();

    final int length = namesAndValues.length;
    for (int i = 0; i < length; i++)
    {
      final Object expected = namesAndValues[i];

      char c = jsonScanner.nextChar();
      /* when */
      while (jsonScanner.isNotEnded()
          && (c == '"' | c == '\'' | c == '{' | c == '}' | c == ',' | c == ':' | c == '[' | c == ']' | Character.isWhitespace(c)))
      {
        c = jsonScanner.nextChar();
      }
      jsonScanner.backToPrevious();

      final Object actual = jsonScanner.nextValue();
      /* then */
      assertThat(actual).isEqualTo(expected);
    }
  }

  @Test
  public final void testBackToPrevious() throws Exception
  {
    /* given */
    final char[] expectedChars = jsonString.toCharArray();

    final int length = jsonString.length();
    for (int i = 0; i < length; i++)
    {
      final char expected = expectedChars[i];
      jsonScanner.nextChar();
      /* when */
      jsonScanner.backToPrevious();
      final char actual = jsonScanner.nextChar();
      /* then */
      assertThat(actual).isEqualTo(expected);
    }
  }

  @Test
  public final void testNewJsonObjectConvertible() throws Exception
  {
    /* given */
    final JsonObject[] expected = new JsonObject[1];
    final AbstractJsonScanner jsonScanner = new AbstractJsonScanner("{}") {

      @Override
      protected JsonObject newJsonObjectConvertible(final JsonScanner jsonScanner)
      {
        expected[0] = new OrderedJsonObject(jsonScanner);
        return expected[0];
      }

      @Override
      protected AbstractJsonArray newJsonArrayConvertible(final JsonScanner jsonScanner)
      {
        throw new UnsupportedOperationException();
      }
    };

    /* when */
    final JsonObject actual = jsonScanner.newJsonObjectConvertible(jsonScanner);

    /* then */
    assertThat(actual).isEqualTo(expected[0]);
  }

  @Test
  public final void testNewJsonArrayConvertible() throws Exception
  {
    /* given */
    final AbstractJsonArray[] expected = new AbstractJsonArray[1];
    final AbstractJsonScanner jsonScanner = new AbstractJsonScanner("[]") {

      @Override
      protected JsonObject newJsonObjectConvertible(final JsonScanner jsonScanner)
      {
        throw new UnsupportedOperationException();
      }

      @Override
      protected AbstractJsonArray newJsonArrayConvertible(final JsonScanner jsonScanner)
      {
        expected[0] = JsonArrayWithOrderedJsonObject.newJsonArray(jsonScanner);
        return expected[0];
      }
    };

    /* when */
    final AbstractJsonArray actual = jsonScanner.newJsonArrayConvertible(jsonScanner);

    /* then */
    assertThat(actual).isEqualTo(expected[0]);
  }

  @Test
  public final void testGetPreviousCharInfo() throws Exception
  {
    /* given */
    final char[] expectedChars = jsonString.toCharArray();

    final int length = jsonString.length();
    int line = 1;
    int previousInLine = 0;
    char previous = 0;
    boolean ended = false;

    assertThat(jsonScanner.getPreviousCharInfo()).isEqualTo("Not started![index: 0, length: " + length + "]");

    char c = expectedChars[0];

    int i = 1;
    while (jsonScanner.isNotEnded())
    {
      if (c == '\n' | c == '\r')
      {
        line++;
        previousInLine = 0;
      }
      else
      {
        previousInLine++;
      }

      final String expected =
        format(
            "[char: '%s', index (start: 0): %s, previousPositionInLine (start: 1): %s, currentLine: %s, length: %s, ended: %s]",
            JsonUtil.toPrintable(c), i - 1, previousInLine, line, length, ended);
      previous = jsonScanner.nextChar();

      /* when */
      final String actual = jsonScanner.getPreviousCharInfo();

      /* then */
      assertThat(actual).isEqualTo(expected);

      if (i < length)
      {
        c = expectedChars[i];
      }
      else
      {
        ended = true;
        c = 0;
      }
      i++;
    }
    assertThat(jsonScanner.isEnded()).isTrue();
  }

  @Test
  public final void testToString() throws Exception
  {
    /* given */
    final String expected =
      "{length=95, index=0, previousCharInt=0, previousChar=\\u0000, previousCharRequiredAlready=false, previousPositionInLine=0, currentLine=1, ended=false}";

    /* when */
    final String actual = jsonScanner.toString();

    /* then */
    assertThat(actual).isEqualTo(expected);

    /* given */
    final String expected2 =
      "{length=95, index=1, previousCharInt=123, previousChar={, previousCharRequiredAlready=false, previousPositionInLine=1, currentLine=1, ended=false}";
    jsonScanner.nextChar();

    /* when */
    final String actual2 = jsonScanner.toString();

    /* then */
    assertThat(actual2).isEqualTo(expected2);
  }

}

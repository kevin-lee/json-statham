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
package org.elixirian.jsonstatham.core.reflect.java2json;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import org.elixirian.jsonstatham.core.KnownTypeProcessorDeciderForJavaToJson;
import org.elixirian.jsonstatham.core.KnownTypeProcessorWithReflectionJavaToJsonConverter;
import org.elixirian.jsonstatham.core.SimpleKnownTypeChecker;
import org.elixirian.jsonstatham.core.reflect.java2json.OneProcessorForKnownTypeDecider;
import org.elixirian.jsonstatham.core.reflect.java2json.ReflectionJavaToJsonConverter;
import org.elixirian.jsonstatham.exception.JsonStathamException;
import org.junit.Test;


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
 * @version 0.0.1 (2010-06-10)
 */
public class OneProcessorForKnownTypeDeciderTest
{

  private static enum TestEnum
  {
    FIRST, SECOND, THIRD;
  }

  private final int[] ints = { -999, -1, 0, 1, 999, Integer.MIN_VALUE, Integer.MAX_VALUE };
  private final Integer[] integers =
    { Integer.valueOf(-999), Integer.valueOf(-1), Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(999),
        Integer.valueOf(Integer.MIN_VALUE), Integer.valueOf(Integer.MAX_VALUE) };

  private final long[] longs = { -999L, -1L, 0L, 1L, 999L, Long.MIN_VALUE, Long.MAX_VALUE };
  private final Long[] longObjects = { Long.valueOf(-999L), Long.valueOf(-1L), Long.valueOf(0L), Long.valueOf(1L),
      Long.valueOf(999L), Long.valueOf(Long.MIN_VALUE), Long.valueOf(Long.MAX_VALUE) };

  private final BigInteger[] bigIntegers = { BigInteger.valueOf(-999L), BigInteger.valueOf(-1L),
      BigInteger.valueOf(0L), BigInteger.valueOf(1L), BigInteger.valueOf(999L), BigInteger.valueOf(Long.MIN_VALUE),
      BigInteger.valueOf(Long.MAX_VALUE) };

  private final float[] floats = { -999.55f, -1.5f, 0.0f, 1.5f, 999.55f, Float.MIN_VALUE, Float.MAX_VALUE };
  private final Float[] floatObjects = { Float.valueOf(-999.55f), Float.valueOf(-1.5f), Float.valueOf(0.0f),
      Float.valueOf(1.5f), Float.valueOf(999.55f), Float.valueOf(Float.MIN_VALUE), Float.valueOf(Float.MAX_VALUE) };

  private final double[] doubles = { -999.55D, -1.5D, 0.0D, 1.5D, 999.55D, Double.MIN_VALUE, Double.MAX_VALUE };
  private final Double[] doubleObjects =
    { Double.valueOf(-999.55D), Double.valueOf(-1.5D), Double.valueOf(0.0D), Double.valueOf(1.5D),
        Double.valueOf(999.55D), Double.valueOf(Double.MIN_VALUE), Double.valueOf(Double.MAX_VALUE) };

  private final BigDecimal[] bigDecimals = { BigDecimal.valueOf(-999.55D), BigDecimal.valueOf(-1.5D),
      BigDecimal.valueOf(0.0D), BigDecimal.valueOf(1.5D), BigDecimal.valueOf(999.55D),
      BigDecimal.valueOf(Double.MIN_VALUE), BigDecimal.valueOf(Double.MAX_VALUE) };

  private final Number[] numbers = { new Number() {
    private static final long serialVersionUID = -1L;

    @Override
    public long longValue()
    {
      return Long.MIN_VALUE;
    }

    @Override
    public int intValue()
    {
      return Integer.MIN_VALUE;
    }

    @Override
    public float floatValue()
    {
      return Float.MIN_VALUE;
    }

    @Override
    public double doubleValue()
    {
      return Double.MIN_VALUE;
    }
  }, new Number() {
    private static final long serialVersionUID = 1L;

    @Override
    public long longValue()
    {
      return Long.MAX_VALUE;
    }

    @Override
    public int intValue()
    {
      return Integer.MAX_VALUE;
    }

    @Override
    public float floatValue()
    {
      return Float.MAX_VALUE;
    }

    @Override
    public double doubleValue()
    {
      return Double.MAX_VALUE;
    }
  } };

  private final boolean[] booleans = { true, false };
  private final Boolean[] booleanObjects = { Boolean.TRUE, Boolean.FALSE };

  private final String[] strings = { "", "Hello", "Kevin", new String("test") };

  /**
   * Test method for
   * {@link org.elixirian.jsonstatham.core.reflect.java2json.OneProcessorForKnownTypeDecider#KnownBasicTypeDecider()}
   * .
   */
  @Test
  public final void testKnownBasicTypeDecider()
  {
    final KnownTypeProcessorDeciderForJavaToJson knownTypeProcessorDeciderForJavaToJson =
      new OneProcessorForKnownTypeDecider();

    assertThat(knownTypeProcessorDeciderForJavaToJson.decide(int.class), is(not(nullValue())));

    for (Integer value : integers)
    {
      assertThat(knownTypeProcessorDeciderForJavaToJson.decide(value.getClass()), is(not(nullValue())));
    }

    assertThat(knownTypeProcessorDeciderForJavaToJson.decide(long.class), is(not(nullValue())));

    for (Long value : longObjects)
    {
      assertThat(knownTypeProcessorDeciderForJavaToJson.decide(value.getClass()), is(not(nullValue())));
    }

    for (BigInteger value : bigIntegers)
    {
      assertThat(knownTypeProcessorDeciderForJavaToJson.decide(value.getClass()), is(not(nullValue())));
    }

    assertThat(knownTypeProcessorDeciderForJavaToJson.decide(float.class), is(not(nullValue())));

    for (Float value : floatObjects)
    {
      assertThat(knownTypeProcessorDeciderForJavaToJson.decide(value.getClass()), is(not(nullValue())));
    }

    assertThat(knownTypeProcessorDeciderForJavaToJson.decide(double.class), is(not(nullValue())));

    for (Double value : doubleObjects)
    {
      assertThat(knownTypeProcessorDeciderForJavaToJson.decide(value.getClass()), is(not(nullValue())));
    }

    for (BigDecimal value : bigDecimals)
    {
      assertThat(knownTypeProcessorDeciderForJavaToJson.decide(value.getClass()), is(not(nullValue())));
    }

    for (Number value : numbers)
    {
      assertThat(knownTypeProcessorDeciderForJavaToJson.decide(value.getClass()), is(not(nullValue())));
    }

    assertThat(knownTypeProcessorDeciderForJavaToJson.decide(boolean.class), is(not(nullValue())));

    for (Boolean value : booleanObjects)
    {
      assertThat(knownTypeProcessorDeciderForJavaToJson.decide(value.getClass()), is(not(nullValue())));
    }

    for (String value : strings)
    {
      assertThat(knownTypeProcessorDeciderForJavaToJson.decide(value.getClass()), is(not(nullValue())));
    }

    for (TestEnum value : TestEnum.values())
    {
      assertThat(knownTypeProcessorDeciderForJavaToJson.decide(value.getClass()), is(not(nullValue())));
    }
  }

  /**
   * Test method for
   * {@link org.elixirian.jsonstatham.core.reflect.java2json.OneProcessorForKnownTypeDecider#KnownBasicTypeDecider(org.elixirian.jsonstatham.core.KnownTypeProcessorWithReflectionJavaToJsonConverter, java.util.Set)}
   * .
   * 
   * @throws IllegalAccessException
   * @throws JsonStathamException
   * @throws IllegalArgumentException
   */
  @Test
  public final void testKnownBasicTypeDeciderKnownTypeProcessorSetOfClassOfQ() throws IllegalArgumentException,
      JsonStathamException, IllegalAccessException
  {
    final Set<Class<?>> set1 = new HashSet<Class<?>>();
    set1.add(Date.class);
    final Set<Class<?>> set2 = new HashSet<Class<?>>();
    set2.add(Calendar.class);
    final KnownTypeProcessorDeciderForJavaToJson knownTypeProcessorDeciderForJavaToJson =
      new OneProcessorForKnownTypeDecider(new KnownTypeProcessorWithReflectionJavaToJsonConverter() {

        @Override
        public <T> Object process(
            @SuppressWarnings("unused") final ReflectionJavaToJsonConverter reflectionJavaToJsonConverter,
            final Class<T> valueType, @SuppressWarnings("unused") final Object value) throws IllegalArgumentException,
            IllegalAccessException, JsonStathamException
        {
          return Boolean.TRUE;
        }
      }, set1, set2, new SimpleKnownTypeChecker[0]);

    assertThat(knownTypeProcessorDeciderForJavaToJson.decide(Date.class), is(not(nullValue())));
    assertThat(knownTypeProcessorDeciderForJavaToJson.decide(Date.class)
        .process(null, null, null), is((Object) Boolean.TRUE));

    assertThat(knownTypeProcessorDeciderForJavaToJson.decide(Calendar.class), is(not(nullValue())));
    assertThat(knownTypeProcessorDeciderForJavaToJson.decide(Calendar.class)
        .process(null, null, null), is((Object) Boolean.TRUE));

    assertThat(knownTypeProcessorDeciderForJavaToJson.decide(GregorianCalendar.class), is(not(nullValue())));
    assertThat(knownTypeProcessorDeciderForJavaToJson.decide(GregorianCalendar.class)
        .process(null, null, null), is((Object) Boolean.TRUE));

    assertThat(knownTypeProcessorDeciderForJavaToJson.decide(int.class), is(nullValue()));

    for (Integer value : integers)
    {
      assertThat(knownTypeProcessorDeciderForJavaToJson.decide(value.getClass()), is(nullValue()));
    }

    assertThat(knownTypeProcessorDeciderForJavaToJson.decide(long.class), is(nullValue()));

    for (Long value : longObjects)
    {
      assertThat(knownTypeProcessorDeciderForJavaToJson.decide(value.getClass()), is(nullValue()));
    }

    for (BigInteger value : bigIntegers)
    {
      assertThat(knownTypeProcessorDeciderForJavaToJson.decide(value.getClass()), is(nullValue()));
    }

    assertThat(knownTypeProcessorDeciderForJavaToJson.decide(float.class), is(nullValue()));

    for (Float value : floatObjects)
    {
      assertThat(knownTypeProcessorDeciderForJavaToJson.decide(value.getClass()), is(nullValue()));
    }

    assertThat(knownTypeProcessorDeciderForJavaToJson.decide(double.class), is(nullValue()));

    for (Double value : doubleObjects)
    {
      assertThat(knownTypeProcessorDeciderForJavaToJson.decide(value.getClass()), is(nullValue()));
    }

    for (BigDecimal value : bigDecimals)
    {
      assertThat(knownTypeProcessorDeciderForJavaToJson.decide(value.getClass()), is(nullValue()));
    }

    for (Number value : numbers)
    {
      assertThat(knownTypeProcessorDeciderForJavaToJson.decide(value.getClass()), is(nullValue()));
    }

    assertThat(knownTypeProcessorDeciderForJavaToJson.decide(boolean.class), is(nullValue()));

    for (Boolean value : booleanObjects)
    {
      assertThat(knownTypeProcessorDeciderForJavaToJson.decide(value.getClass()), is(nullValue()));
    }

    for (String value : strings)
    {
      assertThat(knownTypeProcessorDeciderForJavaToJson.decide(value.getClass()), is(nullValue()));
    }

    for (TestEnum value : TestEnum.values())
    {
      assertThat(knownTypeProcessorDeciderForJavaToJson.decide(value.getClass()), is(nullValue()));
    }
  }

  /**
   * Test method for
   * {@link org.elixirian.jsonstatham.core.reflect.java2json.OneProcessorForKnownTypeDecider#decide(java.lang.Class)}
   * .
   * 
   * @throws IllegalAccessException
   * @throws JsonStathamException
   * @throws IllegalArgumentException
   */
  @SuppressWarnings("boxing")
  @Test
  public final void testDecide() throws IllegalArgumentException, JsonStathamException, IllegalAccessException
  {
    final KnownTypeProcessorDeciderForJavaToJson knownTypeProcessorDeciderForJavaToJson =
      new OneProcessorForKnownTypeDecider();

    for (int value : ints)
    {
      assertThat(knownTypeProcessorDeciderForJavaToJson.decide(int.class)
          .process(null, int.class, value), equalTo((Object) value));
    }

    for (Integer value : integers)
    {
      assertThat(knownTypeProcessorDeciderForJavaToJson.decide(value.getClass())
          .process(null, value.getClass(), value), equalTo((Object) value));
    }

    for (long value : longs)
    {
      assertThat(knownTypeProcessorDeciderForJavaToJson.decide(long.class)
          .process(null, long.class, value), equalTo((Object) value));
    }

    for (Long value : longObjects)
    {
      assertThat(knownTypeProcessorDeciderForJavaToJson.decide(value.getClass())
          .process(null, Long.class, value), equalTo((Object) value));
    }

    for (BigInteger value : bigIntegers)
    {
      assertThat(knownTypeProcessorDeciderForJavaToJson.decide(value.getClass())
          .process(null, value.getClass(), value), equalTo((Object) value));
    }

    for (float value : floats)
    {
      assertThat(knownTypeProcessorDeciderForJavaToJson.decide(float.class)
          .process(null, float.class, value), equalTo((Object) value));
    }

    for (Float value : floatObjects)
    {
      assertThat(knownTypeProcessorDeciderForJavaToJson.decide(value.getClass())
          .process(null, value.getClass(), value), equalTo((Object) value));
    }

    for (double value : doubles)
    {
      assertThat(knownTypeProcessorDeciderForJavaToJson.decide(double.class)
          .process(null, double.class, value), equalTo((Object) value));
    }

    for (Double value : doubleObjects)
    {
      assertThat(knownTypeProcessorDeciderForJavaToJson.decide(value.getClass())
          .process(null, value.getClass(), value), equalTo((Object) value));
    }

    for (BigDecimal value : bigDecimals)
    {
      assertThat(knownTypeProcessorDeciderForJavaToJson.decide(value.getClass())
          .process(null, value.getClass(), value), equalTo((Object) value));
    }

    for (Number value : numbers)
    {
      assertThat(knownTypeProcessorDeciderForJavaToJson.decide(value.getClass())
          .process(null, value.getClass(), value), equalTo((Object) value));
    }

    for (boolean value : booleans)
    {
      assertThat(knownTypeProcessorDeciderForJavaToJson.decide(boolean.class)
          .process(null, boolean.class, value), equalTo((Object) value));
    }

    for (Boolean value : booleanObjects)
    {
      assertThat(knownTypeProcessorDeciderForJavaToJson.decide(value.getClass())
          .process(null, value.getClass(), value), equalTo((Object) value));
    }

    for (String value : strings)
    {
      assertThat(knownTypeProcessorDeciderForJavaToJson.decide(value.getClass())
          .process(null, value.getClass(), value), equalTo((Object) value));
    }

    for (TestEnum value : TestEnum.values())
    {
      assertThat(knownTypeProcessorDeciderForJavaToJson.decide(value.getClass())
          .process(null, value.getClass(), value), equalTo((Object) value));
    }
  }

}

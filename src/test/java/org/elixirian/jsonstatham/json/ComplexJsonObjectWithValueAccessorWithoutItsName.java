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
package org.elixirian.jsonstatham.json;

import static org.elixirian.kommonlee.util.Conditional.*;
import static org.elixirian.kommonlee.util.Objects.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.elixirian.jsonstatham.annotation.JsonField;
import org.elixirian.jsonstatham.annotation.Json;
import org.elixirian.jsonstatham.annotation.ValueAccessor;

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
 * @version 0.0.1 (2010-03-14)
 */
@Json
public final class ComplexJsonObjectWithValueAccessorWithoutItsName
{
  @JsonField(name = "id")
  private Long primaryKey;

  @JsonField(name = "name")
  private String name;

  @ValueAccessor
  @JsonField(name = "registered")
  private boolean registered;

  @ValueAccessor
  @JsonField
  private boolean enabled;

  @JsonField(name = "address")
  private Address address;

  @JsonField(name = "date")
  private Date date;

  @ValueAccessor
  @JsonField(name = "dateWithValueAccessor")
  private Date dateWithValueAccessor;

  @JsonField(name = "calendar")
  private Calendar calendar;

  @ValueAccessor
  @JsonField(name = "calendarWithValueAccessor")
  private Calendar calendarWithValueAccessor;

  private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

  /**
   * @return the primaryKey
   */
  public Long getPrimaryKey()
  {
    return primaryKey;
  }

  /**
   * @param primaryKey
   *          the primaryKey to set
   */
  public void setPrimaryKey(final Long primaryKey)
  {
    this.primaryKey = primaryKey;
  }

  /**
   * @return the name
   */
  public String getName()
  {
    return name;
  }

  /**
   * @param name
   *          the name to set
   */
  public void setName(final String name)
  {
    this.name = name;
  }

  public boolean isRegistered()
  {
    return registered;
  }

  public void setRegistered(final boolean registered)
  {
    this.registered = registered;
  }

  public boolean getEnabled()
  {
    return enabled;
  }

  public void setEnabled(final boolean enabled)
  {
    this.enabled = enabled;
  }

  /**
   * @return the address
   */
  public Address getAddress()
  {
    return address;
  }

  /**
   * @param address
   *          the address to set
   */
  public void setAddress(final Address address)
  {
    this.address = address;
  }

  /**
   * @return the date
   */
  public Date getDate()
  {
    return date;
  }

  /**
   * @param date
   *          the date to set
   */
  public void setDate(final Date date)
  {
    this.date = date;
  }

  public Date dateWithValueAccessor()
  {
    return dateWithValueAccessor;
  }

  /**
   * @return the dateWithValueAccessor
   */
  public String getDateWithValueAccessor()
  {
    synchronized (simpleDateFormat)
    {
      return simpleDateFormat.format(dateWithValueAccessor);
    }
  }

  /**
   * @param dateWithValueAccessor
   *          the dateWithValueAccessor to set
   */
  public void setDateWithValueAccessor(final Date dateWithValueAccessor)
  {
    this.dateWithValueAccessor = dateWithValueAccessor;
  }

  /**
   * @return the calendar
   */
  public Calendar getCalendar()
  {
    return calendar;
  }

  /**
   * @param calendar
   *          the calendar to set
   */
  public void setCalendar(final Calendar calendar)
  {
    this.calendar = calendar;
  }

  public Calendar calendarWithValueAccessor()
  {
    return calendarWithValueAccessor;
  }

  /**
   * @return the calendarWithValueAccessor
   */
  public String getCalendarWithValueAccessor()
  {
    return simpleDateFormat.format(calendarWithValueAccessor.getTime());
  }

  /**
   * @param calendarWithValueAccessor
   *          the calendarWithValueAccessor to set
   */
  public void setCalendarWithValueAccessor(final Calendar calendarWithValueAccessor)
  {
    this.calendarWithValueAccessor = calendarWithValueAccessor;
  }

  @Override
  public int hashCode()
  {
    return hash(primaryKey, name, registered, enabled, address, date, dateWithValueAccessor, calendar,
        calendarWithValueAccessor);
  }

  @Override
  public boolean equals(final Object complexJsonObjectWithValueAccessorWithoutItsName)
  {
    if (identical(this, complexJsonObjectWithValueAccessorWithoutItsName))
    {
      return true;
    }
    final ComplexJsonObjectWithValueAccessorWithoutItsName that =
      castIfInstanceOf(ComplexJsonObjectWithValueAccessorWithoutItsName.class,
          complexJsonObjectWithValueAccessorWithoutItsName);
    /* @formatter:off */
		return isNotNull(that) && 
						and(equal(this.primaryKey, that.getPrimaryKey()),
								equal(this.name, that.getName()), 
								equal(this.registered, that.isRegistered()), 
								equal(this.enabled, that.getEnabled()), 
								equal(this.address, that.getAddress()), 
								equal(this.date, that.date), 
								equal(this.dateWithValueAccessor, that.dateWithValueAccessor), 
								equal(this.calendar, that.calendar), 
								equal(this.calendarWithValueAccessor, that.calendarWithValueAccessor));
		/* @formatter:on */
  }

  @Override
  public String toString()
  {
    return toStringBuilder(this).add("id", primaryKey)
        .add("name", name)
        .add("registered", registered)
        .add("enabled", enabled)
        .add("address", address)
        .add("date", date)
        .add("dateWithValueAccessor", dateWithValueAccessor)
        .add("calendar", calendar)
        .add("calendarWithValueAccessor", calendarWithValueAccessor)
        .toString();
  }
}

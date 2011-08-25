/**
 * 
 */
package org.elixirian.jsonstatham.json;

import static org.elixirian.kommonlee.util.Conditional.*;
import static org.elixirian.kommonlee.util.Objects.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.elixirian.jsonstatham.annotation.JsonField;
import org.elixirian.jsonstatham.annotation.JsonObject;
import org.elixirian.jsonstatham.annotation.ValueAccessor;


/**
 * <pre>
 *     ___  _____  __________  ___________ _____  ____
 *    /   \/    / /      \   \/   /_    _//     \/   /
 *   /        /  /    ___/\      / /   / /          /
 *  /        \  /    ___/  \    /_/   /_/          /
 * /____/\____\/_______/    \__//______/___/\_____/
 * </pre>
 * 
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2009-12-20)
 */
@JsonObject
public final class ComplexJsonObjectWithValueAccessor
{
  @JsonField(name = "id")
  private Long primaryKey;

  @JsonField(name = "name")
  private String name;

  @JsonField(name = "address")
  private Address address;

  @JsonField(name = "date")
  private Date date;

  @JsonField(name = "dateWithValueAccessor")
  @ValueAccessor(name = "getDateString")
  private Date dateWithValueAccessor;

  @JsonField(name = "calendar")
  private Calendar calendar;

  @JsonField(name = "calendarWithValueAccessor")
  @ValueAccessor(name = "getCalendarString")
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
  public void setPrimaryKey(Long primaryKey)
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
  public void setName(String name)
  {
    this.name = name;
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
  public void setAddress(Address address)
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
  public void setDate(Date date)
  {
    this.date = date;
  }

  /**
   * @return the dateWithValueAccessor
   */
  public Date getDateWithValueAccessor()
  {
    return dateWithValueAccessor;
  }

  /**
   * @param dateWithValueAccessor
   *          the dateWithValueAccessor to set
   */
  public void setDateWithValueAccessor(Date dateWithValueAccessor)
  {
    this.dateWithValueAccessor = dateWithValueAccessor;
  }

  public String getDateString()
  {
    synchronized (simpleDateFormat)
    {
      return simpleDateFormat.format(dateWithValueAccessor);
    }
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
  public void setCalendar(Calendar calendar)
  {
    this.calendar = calendar;
  }

  /**
   * @return the calendarWithValueAccessor
   */
  public Calendar getCalendarWithValueAccessor()
  {
    return calendarWithValueAccessor;
  }

  /**
   * @param calendarWithValueAccessor
   *          the calendarWithValueAccessor to set
   */
  public void setCalendarWithValueAccessor(Calendar calendarWithValueAccessor)
  {
    this.calendarWithValueAccessor = calendarWithValueAccessor;
  }

  public String getCalendarString()
  {
    return simpleDateFormat.format(calendarWithValueAccessor.getTime());
  }

  @Override
  public int hashCode()
  {
    return hash(primaryKey, name, address, date, dateWithValueAccessor, calendar, calendarWithValueAccessor);
  }

  @Override
  public boolean equals(Object complexJsonObjectWithValueAccessor)
  {
    if (identical(this, complexJsonObjectWithValueAccessor))
    {
      return true;
    }
    final ComplexJsonObjectWithValueAccessor that =
      castIfInstanceOf(ComplexJsonObjectWithValueAccessor.class, complexJsonObjectWithValueAccessor);
    /* @formatter:off */
		return isNotNull(that) && 
						and(equal(this.primaryKey, that.getPrimaryKey()), 
								equal(this.name, that.getName()), 
								equal(this.address, that.getAddress()), 
								equal(this.date, that.getDate()), 
								equal(this.dateWithValueAccessor, that.getDateWithValueAccessor()),
								equal(this.calendar, that.getCalendar()),
								equal(this.calendarWithValueAccessor, that.getCalendarWithValueAccessor()));
		/* @formatter:on */
  }

  @Override
  public String toString()
  {
    return toStringBuilder(this).add("id", primaryKey)
        .add("name", name)
        .add("address", address)
        .add("date", date)
        .add("dateWithValueAccessor", dateWithValueAccessor)
        .add("calendar", calendar)
        .add("calendarWithValueAccessor", calendarWithValueAccessor)
        .toString();
  }
}

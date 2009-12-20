/**
 * 
 */
package com.lckymn.kevin.jsonstatham;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.lckymn.kevin.jsonstatham.annotation.JsonField;
import com.lckymn.kevin.jsonstatham.annotation.JsonObject;
import com.lckymn.kevin.jsonstatham.annotation.ValueAccessor;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.01 (2009-12-20)
 */
@JsonObject
public final class ComplexJsonObjectWithMethodUse
{
	@JsonField(name = "id")
	private Long primaryKey;

	@JsonField(name = "name")
	private String name;

	@JsonField(name = "address")
	private Address address;

	@JsonField(name = "dateWithoutValueAccessor")
	private Date dateWithoutValueAccessor;

	@JsonField(name = "date")
	@ValueAccessor(name = "getDateString")
	private Date date;

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
	 *            the primaryKey to set
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
	 *            the name to set
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
	 *            the address to set
	 */
	public void setAddress(Address address)
	{
		this.address = address;
	}

	/**
	 * @return the dateWithoutValueAccessor
	 */
	public Date getDateWithoutValueAccessor()
	{
		return dateWithoutValueAccessor;
	}

	/**
	 * @param dateWithoutValueAccessor
	 *            the dateWithoutValueAccessor to set
	 */
	public void setDateWithoutValueAccessor(Date dateWithoutValueAccessor)
	{
		this.dateWithoutValueAccessor = dateWithoutValueAccessor;
	}

	public String getDateString()
	{
		synchronized (simpleDateFormat)
		{
			return simpleDateFormat.format(date);
		}
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
	 *            the date to set
	 */
	public void setDate(Date date)
	{
		this.date = date;
	}
}

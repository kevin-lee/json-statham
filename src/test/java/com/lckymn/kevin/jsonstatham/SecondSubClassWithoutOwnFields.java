/**
 * 
 */
package com.lckymn.kevin.jsonstatham;

import com.lckymn.kevin.jsonstatham.annotation.JsonObject;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.01 (2009-12-22)
 */
@JsonObject
public class SecondSubClassWithoutOwnFields extends SubClass
{
	public SecondSubClassWithoutOwnFields(String name, int number, String email)
	{
		super(name, number, email);
	}
}

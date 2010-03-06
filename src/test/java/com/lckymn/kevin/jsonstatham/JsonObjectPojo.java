/**
 * 
 */
package com.lckymn.kevin.jsonstatham;

import java.util.Iterator;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-03-06)
 */
public interface JsonObjectPojo
{
	Long getId();
	
	String getName();
	
	Iterator<Address> getAddresses();
}

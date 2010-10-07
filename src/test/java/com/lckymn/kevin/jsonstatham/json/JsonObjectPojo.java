/**
 * 
 */
package com.lckymn.kevin.jsonstatham.json;

import java.util.Iterator;
import java.util.Set;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-03-06)
 */
public interface JsonObjectPojo
{
	Long getId();
	
	String getName();
	
	Iterator<Address> getAddresses();
	
	Set<Address> getAddressSet();
}

/**
 * 
 */
package org.elixirian.jsonstatham.json;

import java.util.Iterator;
import java.util.Set;

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
 * @version 0.0.1 (2010-03-06)
 */
public interface JsonObjectPojo
{
  Long getId();

  String getName();

  Iterator<Address> getAddresses();

  Set<Address> getAddressSet();
}

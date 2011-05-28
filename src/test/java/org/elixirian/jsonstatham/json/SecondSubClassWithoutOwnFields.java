/**
 * 
 */
package org.elixirian.jsonstatham.json;

import org.elixirian.jsonstatham.annotation.JsonObject;

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

/**
 * 
 */
package org.elixirian.jsonstatham.json;

import org.elixirian.jsonstatham.annotation.Json;

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
 * @version 0.01 (2009-12-22)
 */
@Json
public class SecondSubClassWithoutOwnFields extends SubClass
{
  public SecondSubClassWithoutOwnFields(String name, int number, String email)
  {
    super(name, number, email);
  }
}

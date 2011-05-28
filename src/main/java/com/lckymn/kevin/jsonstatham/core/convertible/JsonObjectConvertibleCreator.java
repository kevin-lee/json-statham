/**
 * 
 */
package com.lckymn.kevin.jsonstatham.core.convertible;

import com.lckymn.kevin.jsonstatham.exception.JsonStathamException;

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
 * @version 0.0.1 (2010-02-03)
 */
public interface JsonObjectConvertibleCreator
{
  JsonObjectConvertible newJsonObjectConvertible();

  JsonObjectConvertible nullJsonObjectConvertible();

  JsonObjectConvertible newJsonObjectConvertible(String jsonString) throws JsonStathamException;
}

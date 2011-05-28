/**
 * 
 */
package org.elixirian.jsonstatham.core;

import java.lang.reflect.Type;

import org.elixirian.jsonstatham.exception.JsonStathamException;


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
 * @version 0.0.1 (2010-06-10)
 */
public interface KnownTypeProcessorForJsonToJava<P, VT extends Type>
{
  <T> Object process(P processor, VT valueType, Object value) throws IllegalArgumentException, IllegalAccessException,
      JsonStathamException;
}
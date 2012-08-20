/**
 * 
 */
package org.elixirian.jsonstatham.core;

import java.lang.reflect.Type;

import org.elixirian.jsonstatham.exception.JsonStathamException;


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
 * @version 0.0.1 (2010-06-10)
 */
public interface KnownTypeProcessorForJsonToJava<P, VT extends Type>
{
  <T> Object process(P processor, VT valueType, Object value) throws IllegalArgumentException, IllegalAccessException,
      JsonStathamException;
}
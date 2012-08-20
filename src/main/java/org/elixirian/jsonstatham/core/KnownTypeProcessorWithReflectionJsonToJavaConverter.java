/**
 * 
 */
package org.elixirian.jsonstatham.core;

import java.lang.reflect.Type;

import org.elixirian.jsonstatham.core.reflect.json2java.ReflectionJsonToJavaConverter;
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
public interface KnownTypeProcessorWithReflectionJsonToJavaConverter<VT extends Type> extends
    KnownTypeProcessorForJsonToJava<ReflectionJsonToJavaConverter, VT>
{
  @Override
  <T> Object process(ReflectionJsonToJavaConverter reflectionJsonToJavaConverter, VT valueType, Object value)
      throws IllegalArgumentException, IllegalAccessException, JsonStathamException;
}
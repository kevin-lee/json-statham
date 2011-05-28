/**
 * 
 */
package org.elixirian.jsonstatham.core;

import java.lang.reflect.Type;

import org.elixirian.jsonstatham.core.reflect.json2java.ReflectionJsonToJavaConverter;
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
public interface KnownTypeProcessorWithReflectionJsonToJavaConverter<VT extends Type> extends
    KnownTypeProcessorForJsonToJava<ReflectionJsonToJavaConverter, VT>
{
  @Override
  <T> Object process(ReflectionJsonToJavaConverter reflectionJsonToJavaConverter, VT valueType, Object value)
      throws IllegalArgumentException, IllegalAccessException, JsonStathamException;
}
/**
 * 
 */
package org.elixirian.jsonstatham.core;

import java.lang.reflect.InvocationTargetException;

import org.elixirian.jsonstatham.exception.JsonStathamException;
import org.elixirian.kommonlee.reflect.TypeHolder;

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
 * @version 0.0.1 (2010-09-08)
 */
public interface JsonToJavaConverter
{
  <T> T convertFromJson(Class<T> targetClass, String jsonString) throws JsonStathamException, IllegalArgumentException,
      InstantiationException, IllegalAccessException, InvocationTargetException;

  <T> T convertFromJson(TypeHolder<T> typeHolder, String jsonString) throws JsonStathamException,
      IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException;
}

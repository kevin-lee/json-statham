/**
 * 
 */
package com.lckymn.kevin.jsonstatham.core;

import java.lang.reflect.InvocationTargetException;

import org.elixirian.common.reflect.TypeHolder;

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
 * @version 0.0.1 (2010-09-08)
 */
public interface JsonToJavaConverter
{
  <T> T convertFromJson(Class<T> targetClass, String jsonString) throws JsonStathamException, IllegalArgumentException,
      InstantiationException, IllegalAccessException, InvocationTargetException;

  <T> T convertFromJson(TypeHolder<T> typeHolder, String jsonString) throws JsonStathamException,
      IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException;
}

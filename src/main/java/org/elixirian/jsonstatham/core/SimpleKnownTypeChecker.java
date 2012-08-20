package org.elixirian.jsonstatham.core;

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
 * @version 0.0.2 (2010-09-16) moved from
 *          {@link org.elixirian.jsonstatham.core.reflect.java2json.OneProcessorForKnownTypeDecider}.
 */
public interface SimpleKnownTypeChecker
{
  boolean isKnown(Class<?> type);
}
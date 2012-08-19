/**
 * 
 */
package org.elixirian.jsonstatham.core;

import java.lang.reflect.Type;

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
public interface KnownTypeProcessorWithReflectionJsonToJavaConverterDeciderForJsonToJava<VT extends Type> extends
    KnownTypeProcessorDeciderForJsonToJava<KnownTypeProcessorWithReflectionJsonToJavaConverter<VT>, VT>
{
  @Override
  KnownTypeProcessorWithReflectionJsonToJavaConverter<VT> decide(VT type);
}

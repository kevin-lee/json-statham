/**
 * 
 */
package org.elixirian.jsonstatham.core;

import java.lang.reflect.Type;

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
public interface KnownTypeProcessorWithReflectionJsonToJavaConverterDeciderForJsonToJava<VT extends Type> extends
    KnownTypeProcessorDeciderForJsonToJava<KnownTypeProcessorWithReflectionJsonToJavaConverter<VT>, VT>
{
  @Override
  KnownTypeProcessorWithReflectionJsonToJavaConverter<VT> decide(VT type);
}

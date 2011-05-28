/**
 * 
 */
package org.elixirian.jsonstatham.core;

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
public interface KnownTypeProcessorDeciderForJavaToJson extends
    KnownTypeProcessorDecider<KnownTypeProcessorWithReflectionJavaToJsonConverter>
{
  @Override
  <T> KnownTypeProcessorWithReflectionJavaToJsonConverter decide(Class<T> type);
}

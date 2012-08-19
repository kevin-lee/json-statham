/**
 * 
 */
package org.elixirian.jsonstatham.core;

import java.util.Collection;
import java.util.Map;

import org.elixirian.jsonstatham.annotation.Json;
import org.elixirian.jsonstatham.exception.JsonStathamException;
import org.elixirian.kommonlee.reflect.TypeHolder;

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
 * @version 0.0.1 (2009-11-21)
 * @version 0.1.0 (2010-09-08) {@link #convertFromJson(Class, String)} is added.
 * @version 0.1.0 (2010-10-09) {@link #convertFromJson(TypeHolder, String)} is added.
 */
public interface JsonStatham
{
  /**
   * Returns String value containing JSON text. It turns the given target object which must be annotated with
   * {@link Json} into JSON text. If the target object is not annotated with {@link Json}, it throws
   * {@link IllegalStateException}.
   * 
   * @param target
   *          the target object.
   * @return String value which contains JSON text created based on the given target object.
   * @throws JsonStathamException
   *           TODO: finish it!
   */
  String convertIntoJson(Object target) throws JsonStathamException;

  /**
   * Returns an object of the given type. It extracts all the data from the given JSON String then creates the given
   * type object based on the data.
   * 
   * @param <T>
   *          the type of the object to be created.
   * @param type
   *          the given type.
   * @param json
   *          the given JSON String used to create the given type object.
   * @return T type object containing the data extracted from the JSON String.
   * @throws JsonStathamException
   *           TODO: finish it!
   */
  <T> T convertFromJson(Class<T> type, String json) throws JsonStathamException;

  /**
   * Returns an object of the type which the given {@link TypeHolder} contains. It extracts all the data from the given
   * JSON String then creates the given type object based on the data. This method is useful to create array,
   * {@link Collection} and {@link Map} objects directly from JSON.
   * 
   * @param <T>
   *          the type of the object to be created.
   * @param typeHolder
   *          the {@link TypeHolder} object containing the type to be created.
   * @param jsonString
   *          the given JSON String used to create the given type object.
   * @return T type object containing the data extracted from the JSON String.
   * @throws JsonStathamException
   */
  <T> T convertFromJson(final TypeHolder<T> typeHolder, final String jsonString) throws JsonStathamException;
}

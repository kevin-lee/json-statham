/**
 *
 */
package org.elixirian.jsonstatham.core.convertible;

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
 * @version 0.0.1 (2010-02-03)
 */
public interface JsonObjectCreator
{
	JsonObject newJsonObjectConvertible();

	JsonObject nullJsonObjectConvertible();

	JsonObject newJsonObjectConvertible(String jsonString) throws JsonStathamException;
}

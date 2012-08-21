/**
 * 
 */
package org.elixirian.jsonstatham.core.reflect.json2java;

import java.util.List;

import org.elixirian.jsonstatham.core.KnownTypeProcessorWithReflectionJsonToJavaConverterDeciderForJsonToJava;
import org.elixirian.jsonstatham.core.convertible.JsonArrayCreator;
import org.elixirian.jsonstatham.core.convertible.JsonObjectCreator;

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
 * @version 0.0.1 (2012-07-18)
 */
public interface JsonToJavaConfig
{
	JsonObjectCreator getJsonObjectConvertibleCreator();

	JsonArrayCreator getJsonArrayConvertibleCreator();

	List<KnownTypeProcessorWithReflectionJsonToJavaConverterDeciderForJsonToJava<Class<?>>> getKnownTypeProcessorWithReflectionJsonToJavaConverterDeciderForJsonToJavaList();
}

/**
 * 
 */
package com.lckymn.kevin.jsonstatham.core.reflect;

import static com.lckymn.kevin.common.util.MessageFormatter.*;
import static com.lckymn.kevin.common.util.Strings.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

import com.lckymn.kevin.common.validation.AssertIt;
import com.lckymn.kevin.jsonstatham.annotation.JsonField;
import com.lckymn.kevin.jsonstatham.annotation.JsonObject;
import com.lckymn.kevin.jsonstatham.annotation.ValueAccessor;
import com.lckymn.kevin.jsonstatham.core.JsonArrayConvertible;
import com.lckymn.kevin.jsonstatham.core.JsonArrayConvertibleCreator;
import com.lckymn.kevin.jsonstatham.core.JsonConvertible;
import com.lckymn.kevin.jsonstatham.core.JsonObjectConvertible;
import com.lckymn.kevin.jsonstatham.core.JsonObjectConvertibleCreator;
import com.lckymn.kevin.jsonstatham.core.JsonStatham;
import com.lckymn.kevin.jsonstatham.core.KnownTypeProcessor;
import com.lckymn.kevin.jsonstatham.core.KnownTypeProcessorDecider;
import com.lckymn.kevin.jsonstatham.exception.JsonStathamException;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2009-11-21)
 * @version 0.0.2 (2009-12-07) It is refactored.
 * @version 0.0.3 (2009-12-07) It is redesigned.
 * @version 0.0.4 (2009-12-12) It can handle array, List and Map.
 * @version 0.0.5 (2009-12-20)
 *          <p>
 *          It can handle duplicate {@link JsonField} names. => It throws an exception.
 *          </p>
 *          <p>
 *          It can also handle {@link java.util.Date} type value annotated with {@link JsonField}. => It uses the toString() method of the
 *          value object, or if the field is also annotated with {@link ValueAccessor} annotation, it uses the method specified with the
 *          {@link ValueAccessor} annotation in order to get the value.
 *          </p>
 * @version 0.0.6 (2010-02-03) {@link JsonObjectConvertibleCreator} is added to create a new {@link org.json.JSONObject}.
 * @version 0.0.7 (2010-02-12) The name is changed from NonIndentedJsonStatham to ReflectionJsonStatham. When the JsonObject is converted
 *          into JSON, if any fields annotated with @JsonField without the 'name' element explicitly set, it will use the actual field names
 *          as the JsonField names.
 * @version 0.0.8 (2010-03-02) refactoring...
 * @version 0.0.9 (2010-03-06)
 *          <ul>
 *          <li>It can process {@link java.util.Iterator}, {@link java.lang.Iterable} and {@link java.util.Map.Entry}.</li>
 *          <li>If there is no explicit @ValueAccessor name, it uses the getter name that is get + the field name (e.g. field name: name =>
 *          getName / field name: id => getId).</li>
 *          <li>It can handle proxied objects created by javassist.</li>
 *          <li>It ignores any super classes of the given JSON object if the classes are not annotated with the {@link JsonObject}
 *          annotation.</li>
 *          </ul>
 * @version 0.0.10 (2010-03-07) It does not throw an exception when the given JSON object has a proxied object created by javassist as a
 *          field value. Instead it tries to find any JSON objects from its super classes.
 * @version 0.0.11 (2010-03-14) If the {@link ValueAccessor} without its name explicitly set is used on a field and the field type is
 *          <code>boolean</code> or {@link Boolean}, it tries to get the value by calling isField() method that is "is" + the field name
 *          instead of "get" + the field name.
 * @version 0.0.12 (2010-04-20) refactoring...
 * @version 0.0.13 (2010-05-10) It can handle enum type fields (it uses enumType.toString() method to use the returned String as the value
 *          of the field).
 * @version 0.0.14 (2010-06-02) The following types are not used anymore.
 *          <p>
 *          {@link org.json.JSONObject} and {@link org.json.JSONArray}
 *          <p>
 *          These are replaced by {@link JsonObjectConvertible} and {@link JsonArrayConvertible} respectively.
 * @version 0.0.15 (2010-06-10) known types are injectable (more extensible design).
 * @version 0.0.16 (2010-06-14) refactoring...
 */
public class ReflectionJsonStatham implements JsonStatham
{

	private static final Class<?>[] EMPTY_CLASS_ARRAY = new Class<?>[0];
	private static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];

	private final JsonObjectConvertibleCreator jsonObjectConvertibleCreator;

	private final JsonArrayConvertibleCreator jsonArrayConvertibleCreator;

	private final KnownDataStructureTypeProcessorDecider knownDataStructureTypeProcessorDecider;
	private final KnownTypeProcessorDecider[] knownTypeProcessorDeciders;

	public ReflectionJsonStatham(JsonObjectConvertibleCreator jsonObjectConvertibleCreator,
			JsonArrayConvertibleCreator jsonArrayConvertibleCreator,
			KnownDataStructureTypeProcessorDecider knownDataStructureTypeProcessorDecider,
			KnownObjectReferenceTypeProcessorDecider knownObjectReferenceTypeProcessorDecider,
			OneProcessorForKnownTypeDecider oneProcessorForKnownTypeDecider)
	{
		this.jsonObjectConvertibleCreator = jsonObjectConvertibleCreator;
		this.jsonArrayConvertibleCreator = jsonArrayConvertibleCreator;
		this.knownDataStructureTypeProcessorDecider = knownDataStructureTypeProcessorDecider;
		this.knownTypeProcessorDeciders =
			new KnownTypeProcessorDecider[] { knownDataStructureTypeProcessorDecider, knownObjectReferenceTypeProcessorDecider,
					oneProcessorForKnownTypeDecider };
	}

	/**/

	JsonObjectConvertibleCreator getJsonObjectConvertibleCreator()
	{
		return jsonObjectConvertibleCreator;
	}

	JsonArrayConvertibleCreator getJsonArrayConvertibleCreator()
	{
		return jsonArrayConvertibleCreator;
	}

	KnownDataStructureTypeProcessorDecider getKnownDataStructureTypeProcessorDecider()
	{
		return knownDataStructureTypeProcessorDecider;
	}

	KnownTypeProcessorDecider[] getKnownTypeProcessorDeciders()
	{
		return knownTypeProcessorDeciders;
	}

	/**/

	public JsonObjectConvertible newJsonObjectConvertible()
	{
		return jsonObjectConvertibleCreator.newJsonObjectConvertible();
	}

	public JsonObjectConvertible nullJsonObjectConvertible()
	{
		return jsonObjectConvertibleCreator.nullJsonObjectConvertible();
	}

	public JsonArrayConvertible newJsonArrayConvertible()
	{
		return jsonArrayConvertibleCreator.newJsonArrayConvertible();
	}

	/**
	 * Creates a JSON object which is {@link JsonObjectConvertible} containing all the fields annotated with the {@link JsonField}
	 * annotation. The value of the given sourceObject must not be a null reference.
	 * 
	 * @param sourceObject
	 *            the given source object to be converted into {@link JsonObjectConvertible}.
	 * @return The {@link JsonObjectConvertible} object created based on the given sourceObject.
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws JsonStathamException
	 */
	private Object createJsonObject(final Object sourceObject) throws IllegalArgumentException, IllegalAccessException,
			JsonStathamException
	{
		Class<?> sourceClass = sourceObject.getClass();

		final Deque<Class<?>> classStack = new ArrayDeque<Class<?>>();
		while (!Object.class.equals(sourceClass))
		{
			if (sourceClass.isAnnotationPresent(JsonObject.class))
			{
				/* add if the class is annotated with @JsonObject. Otherwise ignore it as it is not a JSON Object */
				classStack.push(sourceClass);
			}
			sourceClass = sourceClass.getSuperclass();
		}

		AssertIt.isFalse(classStack.isEmpty(), "The target object is not a JSON object. " + "It must be annotated with %s.\n"
				+ "[class: %s]\n[object: %s]", JsonObject.class.getName(), sourceClass, sourceObject);

		final Set<String> fieldNameSet = new HashSet<String>();
		final JsonObjectConvertible jsonObjectConvertible = newJsonObjectConvertible();
		for (Class<?> eachClass : classStack)
		{
			extractJsonFields(sourceObject, eachClass, fieldNameSet, jsonObjectConvertible);
		}
		return jsonObjectConvertible;
	}

	private void extractJsonFields(final Object source, final Class<?> sourceClass, final Set<String> fieldNameSet,
			final JsonObjectConvertible jsonObjectConvertible) throws IllegalAccessException, JsonStathamException
	{
		for (Field field : sourceClass.getDeclaredFields())
		{
			if (!field.isAnnotationPresent(JsonField.class))
			{
				/* not JsonField so check next one. */
				continue;
			}
			field.setAccessible(true);

			/* get field name from the @JsonField annotation */
			String jsonFieldName = field.getAnnotation(JsonField.class)
					.name();

			if (isEmpty(jsonFieldName))
			{
				/* no field name is set in the @JsonField annotation so use the actual field name for the JsonObject field. */
				jsonFieldName = field.getName();
			}

			if (fieldNameSet.contains(jsonFieldName))
			{
				/* [ERROR] duplicate field names found */
				throw new JsonStathamException(format(
						"Json filed name must be unique. [JsonField name: %s] in [field: %s] is already used in another field.",
						jsonFieldName, field));
			}
			fieldNameSet.add(jsonFieldName);

			Object fieldValue = null;

			if (field.isAnnotationPresent(ValueAccessor.class))
			{
				String valueAccessorName = field.getAnnotation(ValueAccessor.class)
						.name();

				final boolean hasNoAccessorName = isEmpty(valueAccessorName);
				boolean isFieldBoolean = false;
				if (hasNoAccessorName)
				{
					/*
					 * no explicit ValueAccessor name is set so use the getter name that is get + the field name (e.g. field name: name =>
					 * getName / field name: id => getId). If the field type is boolean or Boolean, it is is + the field name (e.g. field:
					 * boolean assigned => isAssigned).
					 */
					final Class<?> fieldType = field.getType();
					final String fieldName = field.getName();
					isFieldBoolean = boolean.class.equals(fieldType) || Boolean.class.equals(fieldType);
					valueAccessorName =
						(isFieldBoolean ? "is" : "get") + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
				}

				try
				{
					final Method method = sourceClass.getDeclaredMethod(valueAccessorName, EMPTY_CLASS_ARRAY);
					method.setAccessible(true);
					fieldValue = method.invoke(source, EMPTY_OBJECT_ARRAY);
				}
				catch (SecurityException e)
				{
					throw new JsonStathamException(e);
				}
				catch (NoSuchMethodException e)
				{
					if (hasNoAccessorName && isFieldBoolean)
					{
						final String anotherValueAccessorName = "get" + valueAccessorName.substring(2);
						try
						{
							final Method method = sourceClass.getDeclaredMethod(anotherValueAccessorName, EMPTY_CLASS_ARRAY);
							method.setAccessible(true);
							fieldValue = method.invoke(source, EMPTY_OBJECT_ARRAY);
						}
						catch (Exception e1)
						{
							/* throw the original exception. */
							throw new JsonStathamException(format("The given ValueAccessor method that is [%s] is not found."
									+ " An additional attempt with the method name, [%s] failed as well with the exception [%s].",
									valueAccessorName, anotherValueAccessorName, e1), e);
						}
					}
					else
					{
						throw new JsonStathamException(format("The given ValueAccessor method that is [%s] is not found.",
								valueAccessorName), e);
					}
				}
				catch (InvocationTargetException e)
				{
					throw new JsonStathamException(format("Value accessor invocation failed.\n"
							+ "It might be caused by any error happened in the given value accessor method or "
							+ "The given ValueAccessor method [%s] is not a proper value accessor for the JsonField [name: %s].",
							valueAccessorName, jsonFieldName), e);
				}
			}
			else
			{
				fieldValue = field.get(source);
			}
			jsonObjectConvertible.put(jsonFieldName, createJsonValue(fieldValue));
		}
	}

	/**
	 * Converts the given value into {@link JsonConvertible} object. It can be either {@link JsonObjectConvertible} or
	 * {@link JsonArrayConvertible}.
	 * 
	 * @param value
	 *            the given target object to be converted to {@link JsonConvertible} which is either {@link JsonObjectConvertible} or
	 *            {@link JsonArrayConvertible}.
	 * @return {@link JsonConvertible} converted from the given value object. It can be either {@link JsonObjectConvertible} or
	 *         {@link JsonArrayConvertible}. If the given value is the null reference, it returns the object created by
	 *         {@link #nullJsonObjectConvertible()}.
	 * @throws JsonStathamException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public Object createJsonValue(final Object value) throws IllegalArgumentException, IllegalAccessException, JsonStathamException
	{
		if (null == value)
		{
			return nullJsonObjectConvertible();
		}

		final Class<?> type = value.getClass();

		for (KnownTypeProcessorDecider knowTypeProcessorDecider : knownTypeProcessorDeciders)
		{
			final KnownTypeProcessor knownTypeProcessor = knowTypeProcessorDecider.decide(type);
			if (null != knownTypeProcessor)
			{
				return knownTypeProcessor.process(this, value);
			}
		}

		return createJsonObject(value);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lckymn.kevin.jsonstatham.core.JsonStatham#convertToJson(java.lang.Object)
	 */
	@Override
	public String convertIntoJson(Object source) throws JsonStathamException
	{
		try
		{
			if (null == source)
			{
				return nullJsonObjectConvertible().toString();
			}

			final KnownTypeProcessor knownTypeProcessor = knownDataStructureTypeProcessorDecider.decide(source.getClass());
			if (null != knownTypeProcessor)
			{
				return knownTypeProcessor.process(this, source)
						.toString();
			}

			return createJsonObject(source).toString();
		}
		catch (IllegalArgumentException e)
		{
			throw new JsonStathamException(format(
					"Wrong object [object: %s] is passed or it has illegal fields with the @JsonField annotation", source), e);
		}
		catch (IllegalAccessException e)
		{
			throw new JsonStathamException(e);
		}
		catch (JsonStathamException e)
		{
			throw e;
		}
	}
}
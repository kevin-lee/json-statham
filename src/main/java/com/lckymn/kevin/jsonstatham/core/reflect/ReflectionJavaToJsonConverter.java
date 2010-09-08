/**
 * 
 */
package com.lckymn.kevin.jsonstatham.core.reflect;

import static com.lckymn.kevin.common.util.MessageFormatter.*;
import static com.lckymn.kevin.common.util.Strings.*;
import static com.lckymn.kevin.common.validation.Assertions.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

import com.lckymn.kevin.jsonstatham.annotation.JsonField;
import com.lckymn.kevin.jsonstatham.annotation.JsonObject;
import com.lckymn.kevin.jsonstatham.annotation.ValueAccessor;
import com.lckymn.kevin.jsonstatham.core.JavaToJsonConverter;
import com.lckymn.kevin.jsonstatham.core.KnownTypeProcessor;
import com.lckymn.kevin.jsonstatham.core.KnownTypeProcessorDecider;
import com.lckymn.kevin.jsonstatham.core.convertible.JsonArrayConvertible;
import com.lckymn.kevin.jsonstatham.core.convertible.JsonArrayConvertibleCreator;
import com.lckymn.kevin.jsonstatham.core.convertible.JsonConvertible;
import com.lckymn.kevin.jsonstatham.core.convertible.JsonObjectConvertible;
import com.lckymn.kevin.jsonstatham.core.convertible.JsonObjectConvertibleCreator;
import com.lckymn.kevin.jsonstatham.exception.JsonStathamException;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-09-8)
 */
public class ReflectionJavaToJsonConverter implements JavaToJsonConverter
{
	private static final Class<?>[] EMPTY_CLASS_ARRAY = new Class<?>[0];
	private static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];

	private final JsonObjectConvertibleCreator jsonObjectConvertibleCreator;

	private final JsonArrayConvertibleCreator jsonArrayConvertibleCreator;

	private final KnownDataStructureTypeProcessorDecider knownDataStructureTypeProcessorDecider;
	private final KnownTypeProcessorDecider[] knownTypeProcessorDeciders;

	public ReflectionJavaToJsonConverter(JsonObjectConvertibleCreator jsonObjectConvertibleCreator,
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

		assertFalse(classStack.isEmpty(), "The target object is not a JSON object. " + "It must be annotated with %s.\n"
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
	
	/* (non-Javadoc)
	 * @see com.lckymn.kevin.jsonstatham.core.JavaToJsonConverter#convertIntoJson(java.lang.Object)
	 */
	@Override
	public String convertIntoJson(Object source) throws IllegalArgumentException, JsonStathamException, IllegalAccessException
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

}

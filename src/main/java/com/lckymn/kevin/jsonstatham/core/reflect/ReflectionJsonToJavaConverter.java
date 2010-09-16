/**
 * 
 */
package com.lckymn.kevin.jsonstatham.core.reflect;

import static com.lckymn.kevin.common.util.MessageFormatter.*;
import static com.lckymn.kevin.common.util.Strings.*;
import static com.lckymn.kevin.common.validation.Assertions.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.lckymn.kevin.common.asm.analysis.AsmMethodAndConstructorAnalyser;
import com.lckymn.kevin.common.asm.analysis.ConstructorAnalyser;
import com.lckymn.kevin.common.type.Pair;
import com.lckymn.kevin.common.util.Strings;
import com.lckymn.kevin.jsonstatham.annotation.JsonField;
import com.lckymn.kevin.jsonstatham.annotation.JsonObject;
import com.lckymn.kevin.jsonstatham.core.JsonToJavaConverter;
import com.lckymn.kevin.jsonstatham.core.KnownTypeProcessorWithReflectionJsonToJavaConverter;
import com.lckymn.kevin.jsonstatham.core.SimpleKnownTypeChecker;
import com.lckymn.kevin.jsonstatham.core.convertible.JsonArrayConvertible;
import com.lckymn.kevin.jsonstatham.core.convertible.JsonArrayConvertibleCreator;
import com.lckymn.kevin.jsonstatham.core.convertible.JsonObjectConvertible;
import com.lckymn.kevin.jsonstatham.core.convertible.JsonObjectConvertibleCreator;
import com.lckymn.kevin.jsonstatham.exception.JsonStathamException;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-09-08)
 */
public class ReflectionJsonToJavaConverter implements JsonToJavaConverter
{
	private static final Class<?>[] EMPTY_CLASS_ARRAY = new Class<?>[0];
	private static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];

	private final ConstructorAnalyser constructorAnalyser = new AsmMethodAndConstructorAnalyser();

	private final JsonObjectConvertibleCreator jsonObjectConvertibleCreator;
	private final JsonArrayConvertibleCreator jsonArrayConvertibleCreator;

	public ReflectionJsonToJavaConverter(JsonObjectConvertibleCreator jsonObjectConvertibleCreator,
			JsonArrayConvertibleCreator jsonArrayConvertibleCreator)
	{
		this.jsonObjectConvertibleCreator = jsonObjectConvertibleCreator;
		this.jsonArrayConvertibleCreator = jsonArrayConvertibleCreator;
	}

	private <T> Deque<Class<?>> extractAllClassesWithAnnotation(final Class<T> type,
			final Class<? extends Annotation> annotationClass)
	{
		Class<?> sourceClass = type;

		final Deque<Class<?>> classDeque = new ArrayDeque<Class<?>>();
		while (!Object.class.equals(sourceClass))
		{
			if (sourceClass.isAnnotationPresent(annotationClass))
			{
				/* add if the class is annotated with @JsonObject. Otherwise ignore it as it is not a JSON Object */
				classDeque.push(sourceClass);
			}
			sourceClass = sourceClass.getSuperclass();
		}
		return classDeque;
	}

	// private void <T> extractJsonObject(final Class<T> type)
	// {
	// Class<?> sourceClass = type;
	//
	// final Deque<Class<?>> classStack = new ArrayDeque<Class<?>>();
	// while (!Object.class.equals(sourceClass))
	// {
	// if (sourceClass.isAnnotationPresent(JsonObject.class))
	// {
	// /* add if the class is annotated with @JsonObject. Otherwise ignore it as it is not a JSON Object */
	// classStack.push(sourceClass);
	// }
	// sourceClass = sourceClass.getSuperclass();
	// }
	//
	// assertFalse(classStack.isEmpty(), "The target object is not a JSON object. "
	// + "It must be annotated with %s.\n" + "[class: %s]\n[object: %s]", JsonObject.class.getName(),
	// sourceClass, type);
	//
	// final Set<String> fieldNameSet = new HashSet<String>();
	// final JsonObjectConvertible jsonObjectConvertible = newJsonObjectConvertible();
	// for (Class<?> eachClass : classStack)
	// {
	// extractJsonFieldNames(type, eachClass, fieldNameSet, jsonObjectConvertible);
	// }
	// // return jsonObjectConvertible;
	// }

	private static class FieldNameAndFieldNameToTypePair implements Pair<Set<String>, Map<String, Class<?>>>
	{
		private final Set<String> nameSet;
		private final Map<String, Class<?>> nameToTypeMap;

		public FieldNameAndFieldNameToTypePair(final Set<String> nameSet, final Map<String, Class<?>> nameToTypeMap)
		{
			this.nameSet = nameSet;
			this.nameToTypeMap = nameToTypeMap;
		}

		@Override
		public Set<String> getLeft()
		{
			return nameSet;
		}

		@Override
		public Map<String, Class<?>> getRight()
		{
			return nameToTypeMap;
		}
	}

	private Pair<Set<String>, Map<String, Class<?>>> extractJsonFieldNames(final Class<?> sourceClass,
			Pair<Set<String>, Map<String, Class<?>>> fieldNameAndNameToTypePair)
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
				/*
				 * no field name is set in the @JsonField annotation so use the actual field name for the JsonObject
				 * field.
				 */
				jsonFieldName = field.getName();
			}

			if (fieldNameAndNameToTypePair.getLeft()
					.contains(jsonFieldName))
			{
				/* [ERROR] duplicate field names found */
				throw new JsonStathamException(
						format("Json filed name must be unique. [JsonField name: %s] in [field: %s] is already used in another field.",
								jsonFieldName, field));
			}
			fieldNameAndNameToTypePair.getLeft()
					.add(jsonFieldName);
			fieldNameAndNameToTypePair.getRight()
					.put(jsonFieldName, field.getType());
		}
		return fieldNameAndNameToTypePair;
	}

	@SuppressWarnings("unchecked")
	private <T> T convertFromJsonObject(final Class<T> targetClass, final String jsonString)
			throws IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException
	{
		final JsonObjectConvertible jsonObjectConvertible =
			jsonObjectConvertibleCreator.newJsonObjectConvertible(jsonString);

		final Deque<Class<?>> classDeque = extractAllClassesWithAnnotation(targetClass, JsonObject.class);
		assertFalse(classDeque.isEmpty(), "The given type is not a JSON object type. "
				+ "It must be annotated with %s.\n" + "[class: %s]", JsonObject.class.getName(), targetClass);
		final Pair<Set<String>, Map<String, Class<?>>> fieldNameAndNameToTypePair =
			new FieldNameAndFieldNameToTypePair(new LinkedHashSet<String>(), new LinkedHashMap<String, Class<?>>());
		for (Class<?> eachClass : classDeque)
		{
			extractJsonFieldNames(eachClass, fieldNameAndNameToTypePair);
		}

		final Entry<Constructor<?>, String[]> constructorEntry =
			findMatchingConstructor(constructorAnalyser.findConstructorsWithParameterNames(targetClass),
					fieldNameAndNameToTypePair);

		final Map<String, Class<?>> fieldNameToTypeMap = fieldNameAndNameToTypePair.getRight();
		final List<Object> argList = new ArrayList<Object>();
		for (String fieldName : constructorEntry.getValue())
		{
			Class<?> fieldType = fieldNameToTypeMap.get(fieldName);
			argList.add(getValueFromJsonObjectValue(fieldType, jsonObjectConvertible.get(fieldName)));
		}
		final Constructor<?> constructor = constructorEntry.getKey();
		return (T) constructor.newInstance(argList.toArray());
	}

	private static class JsonToJavaOneProcessorForKnownTypeDecider
	{
		public static final KnownTypeProcessorWithReflectionJsonToJavaConverter DEFAULT_KNOWN_TYPE_PROCESSOR;
		public static final Set<Class<?>> DAFAULT_KNOWN_BASIC_TYPE_SET;
		public static final Set<Class<?>> DAFAULT_KNOWN_EXTENSIBLE_BASIC_TYPE_SET;
		public static final SimpleKnownTypeChecker[] DAFAULT_SIMPLE_TYPE_CHECKERS;

		static
		{
			DEFAULT_KNOWN_TYPE_PROCESSOR = new KnownTypeProcessorWithReflectionJsonToJavaConverter()
			{
				@Override
				public Object process(
						@SuppressWarnings("unused") ReflectionJsonToJavaConverter reflectionJsonToJavaConverter,
						Object source) throws IllegalArgumentException, IllegalAccessException, JsonStathamException
				{
					return source;
				}
			};

			Set<Class<?>> tempSet = new HashSet<Class<?>>();
			tempSet.add(Integer.TYPE);
			tempSet.add(Integer.class);
			tempSet.add(Long.TYPE);
			tempSet.add(Long.class);
			tempSet.add(BigInteger.class);
			tempSet.add(Float.TYPE);
			tempSet.add(Float.class);
			tempSet.add(Double.TYPE);
			tempSet.add(Double.class);
			tempSet.add(BigDecimal.class);
			tempSet.add(Boolean.TYPE);
			tempSet.add(Boolean.class);
			tempSet.add(String.class);
			DAFAULT_KNOWN_BASIC_TYPE_SET = Collections.unmodifiableSet(tempSet);

			tempSet = new HashSet<Class<?>>();
			tempSet.add(Number.class);
			DAFAULT_KNOWN_EXTENSIBLE_BASIC_TYPE_SET = Collections.unmodifiableSet(tempSet);

			DAFAULT_SIMPLE_TYPE_CHECKERS = new SimpleKnownTypeChecker[] { new SimpleKnownTypeChecker()
			{

				@Override
				public boolean isKnown(Class<?> type)
				{
					return type.isPrimitive();
				}
			}, new SimpleKnownTypeChecker()
			{

				@Override
				public boolean isKnown(Class<?> type)
				{
					return type.isEnum();
				}
			} };
		}

	}

	private static final Map<Class<?>, KnownTypeProcessorWithReflectionJsonToJavaConverter> KNOWN_BASIC_TYPE_PROCESSOR_MAP =
		new HashMap<Class<?>, KnownTypeProcessorWithReflectionJsonToJavaConverter>();

	static
	{
	}

	private Object getValueFromJsonObjectValue(final Class<?> fieldType, final Object fieldValue)
	{
		if (int.class.equals(fieldType))
		{

		}
		if (String.class.equals(fieldType))
		{
			return fieldValue;
		}

		// TODO Auto-generated function stub
		throw new UnsupportedOperationException();
	}

	public Entry<Constructor<?>, String[]> findMatchingConstructor(Map<Constructor<?>, String[]> constructorMap,
			Pair<Set<String>, Map<String, Class<?>>> fieldNameAndNameToTypePair)
	{
		final Set<String> fieldNameSet = fieldNameAndNameToTypePair.getLeft();
		for (Entry<Constructor<?>, String[]> entry : constructorMap.entrySet())
		{
			final String[] paramNames = entry.getValue();
			final int fieldSize = fieldNameSet.size();
			if (fieldSize == paramNames.length)
			{
				int count = 0;
				for (String paramName : paramNames)
				{
					if (fieldNameSet.contains(paramName))
						count++;
				}
				if (fieldSize == count)
				{
					count = 0;
					final Class<?>[] paramTypes = entry.getKey()
							.getParameterTypes();
					final Map<String, Class<?>> fieldNameToTypeMap = fieldNameAndNameToTypePair.getRight();
					for (int i = 0, size = paramNames.length; i < size; i++)
					{
						final String paramName = paramNames[i];
						if (paramTypes[i].equals(fieldNameToTypeMap.get(paramName)))
							count++;
					}
					if (fieldSize == count)
						return entry;
				}
			}
		}
		return null;
	}

	private <T> T convertFromJsonArray(final Class<T> targetClass, final String jsonString)
	{
		final JsonArrayConvertible jsonArrayConvertible =
			jsonArrayConvertibleCreator.newJsonArrayConvertible(jsonString);
		final int length = jsonArrayConvertible.length();
		if (targetClass.isArray())
		{
			final Object array = Array.newInstance(targetClass.getComponentType(), length);
			for (int i = 0; i < length; i++)
			{
				Array.set(array, i, jsonArrayConvertible.get(i));
			}
			return (T) array;
		}
		else if (Collection.class.isAssignableFrom(targetClass))
		{
			final List<Object> list = new ArrayList<Object>();
			for (int i = 0; i < length; i++)
			{
				list.add(jsonArrayConvertible.get(i));
			}
			return (T) list;
		}
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T convertFromJson(final Class<T> targetClass, final String jsonString) throws JsonStathamException,
			IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException
	{
		final String trimedJson = Strings.nullSafeTrim(jsonString);
		if ('{' == trimedJson.charAt(0))
		{
			return convertFromJsonObject(targetClass, jsonString);
		}
		else if ('[' == trimedJson.charAt(0))
		{
			return convertFromJsonArray(targetClass, jsonString);
		}
		else if ("null".equals(trimedJson))
		{
			return null;
		}
		else
		{
			throw new JsonStathamException();
		}
	}
}

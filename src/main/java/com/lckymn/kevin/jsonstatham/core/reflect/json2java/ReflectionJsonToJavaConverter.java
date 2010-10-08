/**
 * 
 */
package com.lckymn.kevin.jsonstatham.core.reflect.json2java;

import static com.lckymn.kevin.common.reflect.Classes.*;
import static com.lckymn.kevin.common.util.MessageFormatter.*;
import static com.lckymn.kevin.common.util.Strings.*;
import static com.lckymn.kevin.common.validation.Assertions.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.lckymn.kevin.common.asm.analysis.AsmMethodAndConstructorAnalyser;
import com.lckymn.kevin.common.asm.analysis.ConstructorAnalyser;
import com.lckymn.kevin.common.reflect.Generics;
import com.lckymn.kevin.common.reflect.TypeHolder;
import com.lckymn.kevin.common.type.Pair;
import com.lckymn.kevin.common.util.Objects;
import com.lckymn.kevin.jsonstatham.annotation.JsonConstructor;
import com.lckymn.kevin.jsonstatham.annotation.JsonField;
import com.lckymn.kevin.jsonstatham.annotation.JsonObject;
import com.lckymn.kevin.jsonstatham.core.JsonToJavaConverter;
import com.lckymn.kevin.jsonstatham.core.KnownTypeProcessorWithReflectionJsonToJavaConverter;
import com.lckymn.kevin.jsonstatham.core.convertible.JsonArrayConvertible;
import com.lckymn.kevin.jsonstatham.core.convertible.JsonArrayConvertibleCreator;
import com.lckymn.kevin.jsonstatham.core.convertible.JsonObjectConvertible;
import com.lckymn.kevin.jsonstatham.core.convertible.JsonObjectConvertibleCreator;
import com.lckymn.kevin.jsonstatham.core.convertible.OrgJsonJsonObjectConvertible;
import com.lckymn.kevin.jsonstatham.exception.JsonStathamException;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-09-08)
 */
public class ReflectionJsonToJavaConverter implements JsonToJavaConverter
{
	private final ConstructorAnalyser constructorAnalyser = new AsmMethodAndConstructorAnalyser();

	private final JsonObjectConvertibleCreator jsonObjectConvertibleCreator;
	private final JsonArrayConvertibleCreator jsonArrayConvertibleCreator;

	private final JsonToJavaOneProcessorForKnownTypeDecider jsonToJavaOneProcessorForKnownTypeDecider =
		new JsonToJavaOneProcessorForKnownTypeDecider();

	private final JsonToJavaKnownDataStructureTypeProcessorDecider jsonToJavaKnownDataStructureTypeProcessorDecider =
		new JsonToJavaKnownDataStructureTypeProcessorDecider();

	private final JsonToJavaKnownObjectTypeProcessorDecider jsonToJavaKnownObjectTypeProcessorDecider =
		new JsonToJavaKnownObjectTypeProcessorDecider();

	public ReflectionJsonToJavaConverter(JsonObjectConvertibleCreator jsonObjectConvertibleCreator,
			JsonArrayConvertibleCreator jsonArrayConvertibleCreator)
	{
		this.jsonObjectConvertibleCreator = jsonObjectConvertibleCreator;
		this.jsonArrayConvertibleCreator = jsonArrayConvertibleCreator;
	}

	public JsonObjectConvertibleCreator getJsonObjectConvertibleCreator()
	{
		return jsonObjectConvertibleCreator;
	}

	public JsonArrayConvertibleCreator getJsonArrayConvertibleCreator()
	{
		return jsonArrayConvertibleCreator;
	}

	private static class JsonFieldNameAndFieldPair implements Pair<String, Field>
	{
		private final String jsonFieldName;
		private final Field field;

		public JsonFieldNameAndFieldPair(String jsonFieldName, Field field)
		{
			this.jsonFieldName = jsonFieldName;
			this.field = field;
		}

		@Override
		public String getLeft()
		{
			return jsonFieldName;
		}

		@Override
		public Field getRight()
		{
			return field;
		}

		@Override
		public String toString()
		{
			return Objects.toStringBuilder(this)
					.add("jsonFieldName", jsonFieldName)
					.add("field", field.getName())
					.toString();
		}
	}

	private static class JsonFieldName2FieldNFieldName2JsonFieldNameAndFieldPairMapsPair implements
			Pair<Map<String, Field>, Map<String, JsonFieldNameAndFieldPair>>
	{
		private final Map<String, Field> jsonFieldNameToFieldMap;
		private final Map<String, JsonFieldNameAndFieldPair> fieldNameToJsonFieldNameAndFieldPairMap;

		public JsonFieldName2FieldNFieldName2JsonFieldNameAndFieldPairMapsPair(
				Map<String, Field> jsonFieldNameToFieldMap,
				Map<String, JsonFieldNameAndFieldPair> fieldNameToJsonFieldNameAndFieldPairMap)
		{
			this.jsonFieldNameToFieldMap = jsonFieldNameToFieldMap;
			this.fieldNameToJsonFieldNameAndFieldPairMap = fieldNameToJsonFieldNameAndFieldPairMap;
		}

		@Override
		public Map<String, Field> getLeft()
		{
			return jsonFieldNameToFieldMap;
		}

		@Override
		public Map<String, JsonFieldNameAndFieldPair> getRight()
		{
			return fieldNameToJsonFieldNameAndFieldPairMap;
		}

		@Override
		public String toString()
		{
			return Objects.toStringBuilder(this)
					.add("jsonFieldNameToFieldMap", jsonFieldNameToFieldMap)
					.add("fieldNameToJsonFieldNameAndFieldPairMap", fieldNameToJsonFieldNameAndFieldPairMap)
					.toString();
		}
	}

	private void extractJsonFieldNames(final Class<?> sourceClass,
			JsonFieldName2FieldNFieldName2JsonFieldNameAndFieldPairMapsPair jsonFieldNameToFieldNameAndFieldPairMap)
	{
		for (Field field : sourceClass.getDeclaredFields())
		{
			if (!field.isAnnotationPresent(JsonField.class))
			{
				/* not JsonField so check next one. */
				continue;
			}
			field.setAccessible(true);

			final String fieldName = field.getName();
			/* get field name from the @JsonField annotation */
			String jsonFieldName = field.getAnnotation(JsonField.class)
					.name();

			if (isEmpty(jsonFieldName))
			{
				/*
				 * no field name is set in the @JsonField annotation so use the actual field name for the JsonObject
				 * field.
				 */
				jsonFieldName = fieldName;
			}

			if (jsonFieldNameToFieldNameAndFieldPairMap.getLeft()
					.containsKey(jsonFieldName))
			{
				/* [ERROR] duplicate field names found */
				throw new JsonStathamException(
						format("Json filed name must be unique. [JsonField name: %s] in [field: %s] is already used in another field.",
								jsonFieldName, field));
			}

			jsonFieldNameToFieldNameAndFieldPairMap.getLeft()
					.put(jsonFieldName, field);
			jsonFieldNameToFieldNameAndFieldPairMap.getRight()
					.put(fieldName, new JsonFieldNameAndFieldPair(jsonFieldName, field));
		}
	}

	private <T, A extends Annotation> Map<Constructor<T>, String[]> extractAllConstructorsWithAnnotations(
			Map<Constructor<T>, String[]> constructorMap, Class<? extends A>... annotations)
	{
		final Map<Constructor<T>, String[]> map = new HashMap<Constructor<T>, String[]>();
		for (final Entry<Constructor<T>, String[]> entry : constructorMap.entrySet())
		{
			final Constructor<T> constructor = entry.getKey();
			for (final Class<? extends A> annotation : annotations)
			{
				if (constructor.isAnnotationPresent(annotation))
				{
					map.put(constructor, entry.getValue());
					break;
				}
			}
		}
		return map;
	}

	public <T> T createFromJsonObject(final Class<T> targetClass, final JsonObjectConvertible jsonObjectConvertible)
			throws IllegalArgumentException, IllegalAccessException
	{
		try
		{
			return createFromJsonObject0(targetClass, jsonObjectConvertible);
		}
		catch (IllegalArgumentException e)
		{
			throw e;
		}
		catch (IllegalAccessException e)
		{
			throw e;
		}
		catch (InstantiationException e)
		{
			throw new JsonStathamException(e);
		}
		catch (InvocationTargetException e)
		{
			throw new JsonStathamException(e);
		}
	}

	private static class ConstructorAndParamsPair<T, R> implements Pair<Constructor<T>, R>
	{
		private final Constructor<T> constructor;
		private final R params;

		public ConstructorAndParamsPair(Constructor<T> constructor, R paramNames)
		{
			this.constructor = constructor;
			this.params = paramNames;
		}

		@Override
		public Constructor<T> getLeft()
		{
			return constructor;
		}

		@Override
		public R getRight()
		{
			return params;
		}
	}

	private <T> T createFromJsonObject0(final Class<T> targetClass, final JsonObjectConvertible jsonObjectConvertible)
			throws IllegalArgumentException, IllegalAccessException, InstantiationException, InvocationTargetException
	{
		final List<Class<?>> classList =
			extractClssesWithAnnotationsInSuperToSubOrder(targetClass, Object.class, true, JsonObject.class);

		assertFalse(classList.isEmpty(), "The given type is not a JSON object type. "
				+ "It must be annotated with %s.\n" + "[class: %s]", JsonObject.class.getName(), targetClass);

		final JsonFieldName2FieldNFieldName2JsonFieldNameAndFieldPairMapsPair jsonFieldName2FieldNFieldName2JsonFieldNameAndFieldPairMapsPair =
			new JsonFieldName2FieldNFieldName2JsonFieldNameAndFieldPairMapsPair(new LinkedHashMap<String, Field>(),
					new LinkedHashMap<String, JsonFieldNameAndFieldPair>());
		for (final Class<?> eachClass : classList)
		{
			extractJsonFieldNames(eachClass, jsonFieldName2FieldNFieldName2JsonFieldNameAndFieldPairMapsPair);
		}

		final Map<Constructor<T>, String[]> constructorMap =
			constructorAnalyser.findConstructorsWithParameterNames(targetClass);

		@SuppressWarnings("unchecked")
		final Map<Constructor<T>, String[]> constructorMapWithJsonConstructorAnnotation =
			extractAllConstructorsWithAnnotations(constructorMap, JsonConstructor.class);

		if (!constructorMapWithJsonConstructorAnnotation.isEmpty())
		{
			for (final Constructor<T> constructor : constructorMapWithJsonConstructorAnnotation.keySet())
			{
				/* remove all the constructors with the annotation from the constructor map. */
				constructorMap.remove(constructor);
			}
			/* not empty so use it first! */
			// matching with all the json field
			final Pair<Constructor<T>, String[]> constructorEntry =
				findMatchingConstructor(constructorMapWithJsonConstructorAnnotation,
						jsonFieldName2FieldNFieldName2JsonFieldNameAndFieldPairMapsPair);

			// if constructorEntry is null try with any available constructors.
			final Constructor<T> constructor = constructorEntry.getLeft();
			if (null != constructor)
			{
				// final Map<String, Field> fieldNameToFieldMap = fieldNameAndNameToFieldPair.getRight();
				// final List<Object> argList = new ArrayList<Object>();
				// for (String fieldName : constructorEntry.getValue())
				// {
				// final Field field = fieldNameToFieldMap.get(fieldName);
				// argList.add(getFieldValue(field, field.getType(), jsonObjectConvertible.get(fieldName)));
				// }
				final Map<String, JsonFieldNameAndFieldPair> fieldNameToFieldMap =
					jsonFieldName2FieldNFieldName2JsonFieldNameAndFieldPairMapsPair.getRight();
				final List<Object> argList = new ArrayList<Object>();
				for (String fieldName : constructorEntry.getRight())
				{
					final JsonFieldNameAndFieldPair jsonFieldNameAndFieldPair = fieldNameToFieldMap.get(fieldName);
					final Field field = jsonFieldNameAndFieldPair.getRight();
					argList.add(resolveFieldValue(field, field.getType(),
							jsonObjectConvertible.get(jsonFieldNameAndFieldPair.getLeft())));
				}
				// final Constructor<T>
				return constructor.newInstance(argList.toArray());
			}

			return null;
		}

		/* matching with all the json field */
		final Pair<Constructor<T>, String[]> constructorEntry =
			findMatchingConstructor(constructorMap, jsonFieldName2FieldNFieldName2JsonFieldNameAndFieldPairMapsPair);

		if (null != constructorEntry)
		{/* if constructorEntry is null try with any available constructors. */
			final Constructor<T> constructor = constructorEntry.getLeft();
			if (null != constructor)
			{
				final Map<String, JsonFieldNameAndFieldPair> fieldNameToFieldMap =
					jsonFieldName2FieldNFieldName2JsonFieldNameAndFieldPairMapsPair.getRight();
				final List<Object> argList = new ArrayList<Object>();
				for (String fieldName : constructorEntry.getRight())
				{
					final JsonFieldNameAndFieldPair jsonFieldNameAndFieldPair = fieldNameToFieldMap.get(fieldName);
					final Field field = jsonFieldNameAndFieldPair.getRight();
					final Object arg =
						resolveFieldValue(field, field.getType(),
								jsonObjectConvertible.get(jsonFieldNameAndFieldPair.getLeft()));
					System.out.println("arg: " + arg);
					argList.add(arg);
				}
				// System.out.println(jsonObjectConvertible.toString());
				// System.out.println("argList: " + argList);
				return constructor.newInstance(argList.toArray());
			}
		}

		// TODO finish it!
		// find constructor with minimum matching params.
		final Pair<Constructor<T>, List<Object>> constructorToParamsPair =
			findMatchingConstructorWithMinimumParams(constructorMap,
					jsonFieldName2FieldNFieldName2JsonFieldNameAndFieldPairMapsPair, jsonObjectConvertible);

		if (null != constructorToParamsPair)
		{
			return constructorToParamsPair.getLeft()
					.newInstance(constructorToParamsPair.getRight()
							.toArray());
		}

		// no arg constructor
		final Constructor<T> constructor = findConstructor(targetClass, EMPTY_CLASS_ARRAY);
		if (null != constructor)
		{
			// use it
			constructor.setAccessible(true);
			T t = constructor.newInstance();
			// set the values;
			for (final Entry<String, Field> fieldEntry : jsonFieldName2FieldNFieldName2JsonFieldNameAndFieldPairMapsPair.getLeft()
					.entrySet())
			{
				final Field field = fieldEntry.getValue();
				field.setAccessible(true);
				System.out.println("field: " + field + " | type: " + field.getType() + " | object: "
						+ jsonObjectConvertible.get(fieldEntry.getKey()));
				field.set(t, resolveFieldValue(field, field.getType(), jsonObjectConvertible.get(fieldEntry.getKey())));
			}
			return t;
		}

		// TODO get the constructor with the minimum number of paramters and use it.

		throw new JsonStathamException(format(
				"The target JSON class [class: %s] cannot be instantiated with the given JSON [json: %s].",
				targetClass.getName(), jsonObjectConvertible));
	}

	private <T> T convertFromJsonObject(final Class<T> targetClass, final String jsonString)
			throws IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException
	{
		final JsonObjectConvertible jsonObjectConvertible =
			jsonObjectConvertibleCreator.newJsonObjectConvertible(jsonString);
		return createFromJsonObject(targetClass, jsonObjectConvertible);
	}

	private <T> Object resolveFieldValue(Field field, Class<T> fieldType, Object value) throws JsonStathamException,
			IllegalArgumentException, IllegalAccessException
	{
		final Type genericType = field.getGenericType();
		if (genericType instanceof Class)
		{
			return resolveTypeAndValue(fieldType, value);
		}
		return resolveGenericTypeAndValue(genericType, fieldType, value);
	}

	private <T> Object resolveTypeAndValue(final Class<T> valueType, final Object value) throws JsonStathamException,
			IllegalArgumentException, IllegalAccessException
	{
		if (null == value || jsonObjectConvertibleCreator.nullJsonObjectConvertible()
				.getActualObject()
				.equals(value))
		{
			return null;
		}

		System.out.println("valueType: " + valueType + " | value: " + value);
		final KnownTypeProcessorWithReflectionJsonToJavaConverter<Class<?>> knownTypeProcessorWithReflectionJsonToJavaConverter =
			jsonToJavaOneProcessorForKnownTypeDecider.decide(valueType);
		if (null != knownTypeProcessorWithReflectionJsonToJavaConverter)
		{
			return knownTypeProcessorWithReflectionJsonToJavaConverter.process(this, valueType, value);
		}

		final KnownTypeProcessorWithReflectionJsonToJavaConverter<Type> knownTypeProcessorWithReflectionJsonToJavaConverter2 =
			jsonToJavaKnownDataStructureTypeProcessorDecider.decide(valueType);
		if (null != knownTypeProcessorWithReflectionJsonToJavaConverter2)
		{
			return knownTypeProcessorWithReflectionJsonToJavaConverter2.process(this, valueType, value);
		}

		final KnownTypeProcessorWithReflectionJsonToJavaConverter<Class<?>> knownTypeProcessorWithReflectionJsonToJavaConverter3 =
			jsonToJavaKnownObjectTypeProcessorDecider.decide(valueType);
		if (null != knownTypeProcessorWithReflectionJsonToJavaConverter3)
		{
			return knownTypeProcessorWithReflectionJsonToJavaConverter3.process(this, valueType, value);
		}

		final KnownTypeProcessorWithReflectionJsonToJavaConverter<Class<?>> knownTypeProcessorWithReflectionJsonToJavaConverter4 =
			jsonToJavaKnownObjectTypeProcessorDecider.decide(value.getClass());
		if (null != knownTypeProcessorWithReflectionJsonToJavaConverter4)
		{
			return knownTypeProcessorWithReflectionJsonToJavaConverter4.process(this, valueType, value);
		}

		// if (Date.class.isAssignableFrom(valueType))
		// {
		// if (long.class.equals(value.getClass()) || Long.class.equals(value.getClass()))
		// {
		// return new Date(((Long) value).longValue());
		// }
		// }
		// if (Calendar.class.isAssignableFrom(valueType))
		// {
		// if (long.class.equals(value.getClass()) || Long.class.equals(value.getClass()))
		// {
		// final Calendar calendar = Calendar.getInstance();
		// calendar.setTimeInMillis(((Long) value).longValue());
		// return calendar;
		// }
		// }
		// if (JSONArray.class.isAssignableFrom(value.getClass()))
		// {
		// System.out.println("It's JSONArray1111111");
		// return createFromJsonArray(valueType, new OrgJsonJsonArrayConvertible((JSONArray) value));
		// }
		// if (JSONObject.class.isAssignableFrom(value.getClass()))
		// {
		// return createFromJsonObject(valueType, new OrgJsonJsonObjectConvertible((JSONObject) value));
		// }
		throw new JsonStathamException(format("Unknown type [class: %s][object: %s]", valueType, value));
	}

	private <T> Object resolveGenericTypeAndValue(final Type genericType, final Class<T> valueType, final Object value)
			throws IllegalAccessException
	{
		System.out.println(format("genericType: %s, valueType: %s, value: %s", genericType, valueType, value));
		final KnownTypeProcessorWithReflectionJsonToJavaConverter<Type> knownTypeProcessorWithReflectionJsonToJavaConverter2 =
			jsonToJavaKnownDataStructureTypeProcessorDecider.decide(genericType);
		if (null != knownTypeProcessorWithReflectionJsonToJavaConverter2)
		{
			return knownTypeProcessorWithReflectionJsonToJavaConverter2.process(this, genericType, value);
		}

		// if (Collection.class.isAssignableFrom(valueType))
		// {
		// System.out.println("!!!!!!!!!!!!!!!!!!!!!!Collection valueType: " + valueType);
		// final Type elementType = Generics.getGenericInfo(genericType);
		// if (elementType instanceof Class)
		// {
		// final Class<?> elementTypeClass = (Class<?>) elementType;
		// @SuppressWarnings({ "unchecked", "rawtypes" })
		// Collection<?> collection =
		// createCollectionWithValues((Class<Collection>) valueType, elementTypeClass, value);
		// return collection;
		// }
		// // newArrayList(elementType)
		// }
		// if (Iterable.class.isAssignableFrom(valueType))
		// {
		// System.out.println("[Iterable]");
		// final Type elementType = Generics.getGenericInfo(genericType);
		// if (elementType instanceof Class)
		// {
		// Class<?> elementTypeClass = (Class<?>) elementType;
		// @SuppressWarnings("unchecked")
		// Collection<?> collection = createCollectionWithValues(List.class, elementTypeClass, value);
		// return collection;
		// }
		// }
		// if (Iterator.class.isAssignableFrom(valueType))
		// {
		// System.out.println("[Iterator]");
		// final Type elementType = Generics.getGenericInfo(genericType);
		// if (elementType instanceof Class)
		// {
		// Class<?> elementTypeClass = (Class<?>) elementType;
		// @SuppressWarnings("unchecked")
		// final Collection<?> collection = createCollectionWithValues(List.class, elementTypeClass, value);
		// return collection.iterator();
		// }
		// }
		// if (Map.class.isAssignableFrom(valueType))
		// {
		// Class<?> genericValueType = Generics.extractFromParameterizedType(genericType, 1);
		// System.out.println("valueType: " + genericValueType);
		// return createHashMapWithKeysAndValues(genericValueType, value);
		// }
		throw new JsonStathamException(format("Unknown type [class: %s][object: %s]", valueType, value));
	}

	public <V> Map<String, Object> createHashMapWithKeysAndValues(Class<V> valueType, Object value)
			throws IllegalAccessException
	{
		JSONObject jsonObject = null;
		if (JsonObjectConvertible.class.isAssignableFrom(value.getClass()))
		{
			jsonObject = (JSONObject) ((JsonObjectConvertible) value).getActualObject();
		}
		if (JSONObject.class.isAssignableFrom(value.getClass()))
		{
			jsonObject = (JSONObject) value;
		}
		if (null != jsonObject)
		{
			final Map<String, Object> map = new HashMap<String, Object>();
			for (String name : JSONObject.getNames(jsonObject))
			{
				try
				{
					System.out.println("name: " + name + " | valueType: " + valueType + " | value: "
							+ jsonObject.get(name));
					map.put(name, resolveElement(valueType, jsonObject.get(name)));
				}
				catch (JSONException e)
				{
					e.printStackTrace();
					throw new JsonStathamException(e);
				}
			}
			return map;
		}
		// TODO Auto-generated function stub
		throw new JsonStathamException();
	}

	public <E, C extends Collection<E>> Collection<E> createCollectionWithValues(Class<C> collectionClass,
			Class<E> elementType, Object value) throws IllegalAccessException
	{
		System.out.println("ReflectionJsonToJavaConverter.createCollectionWithValues()");
		System.out.println("value: " + value);
		final Collection<E> collection = newCollection(collectionClass);
		JSONArray jsonArray = null;
		if (JsonArrayConvertible.class.isAssignableFrom(value.getClass()))
		{
			jsonArray = (JSONArray) ((JsonArrayConvertible) value).getActualObject();
		}
		if (JSONArray.class.isAssignableFrom(value.getClass()))
		{
			jsonArray = (JSONArray) value;
		}
		if (null != jsonArray)
		{
			System.out.println("size: " + jsonArray.length());

			for (int i = 0, size = jsonArray.length(); i < size; i++)
			{
				try
				{
					System.out.println("elementType: " + elementType + " | " + jsonArray.get(i));
					collection.add(elementType.cast(resolveElement(elementType, jsonArray.get(i))));
				}
				catch (JSONException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new JsonStathamException(e);
				}
			}
		}
		return collection;

	}

	private <E, C extends Collection<E>> Collection<E> newCollection(Class<C> collectionClass)
	{
		System.out.println("collectionClass: " + collectionClass);
		Collection<E> collection = null;
		if (List.class.isAssignableFrom(collectionClass))
		{
			collection = new ArrayList<E>();
			System.out.println("ArrayList is created.");
		}
		else if (Set.class.isAssignableFrom(collectionClass))
		{
			collection = new HashSet<E>();
			System.out.println("HashSet is created.");
		}
		else
		{
			collection = new ArrayList<E>();
			System.out.println("ArrayList is created.");
		}
		return collection;
	}

	public <T> ConstructorAndParamsPair<T, String[]> findMatchingConstructor(
			Map<Constructor<T>, String[]> constructorMap,
			JsonFieldName2FieldNFieldName2JsonFieldNameAndFieldPairMapsPair jsonFieldNameToFieldNameAndFieldPairMap)
	{
		// final Set<String> fieldNameSet = fieldNameAndFieldNameToFieldPair.getLeft();
		final Map<String, JsonFieldNameAndFieldPair> fieldNameToFieldMap =
			jsonFieldNameToFieldNameAndFieldPairMap.getRight();
		final int fieldSize = fieldNameToFieldMap.size();
		for (Entry<Constructor<T>, String[]> entry : constructorMap.entrySet())
		{
			final String[] paramNames = entry.getValue();
			// final int fieldSize = fieldNameSet.size();
			if (fieldSize == paramNames.length)
			{
				int count = 0;
				for (final String paramName : paramNames)
				{
					// if (fieldNameSet.contains(paramName))
					if (fieldNameToFieldMap.containsKey(paramName))
						count++;
				}
				if (fieldSize == count)
				{
					count = 0;
					final Class<?>[] paramTypes = entry.getKey()
							.getParameterTypes();

					for (int i = 0; i < fieldSize; i++)
					{
						final String paramName = paramNames[i];
						if (paramTypes[i].equals(fieldNameToFieldMap.get(paramName)
								.getRight()
								.getType()))
							count++;
					}
					if (fieldSize == count)
						return new ConstructorAndParamsPair<T, String[]>(entry.getKey(), entry.getValue());
				}
			}
		}
		return null;
	}

	public <T> Pair<Constructor<T>, List<Object>> findMatchingConstructorWithMinimumParams(
			final Map<Constructor<T>, String[]> constructorMap,
			final JsonFieldName2FieldNFieldName2JsonFieldNameAndFieldPairMapsPair jsonFieldName2FieldNFieldName2JsonFieldNameAndFieldPairMapsPair,
			final JsonObjectConvertible jsonObjectConvertible) throws JsonStathamException, IllegalArgumentException,
			IllegalAccessException
	{
		final List<Object> paramValues = new ArrayList<Object>();
		// final Set<String> fieldNameSet = fieldNameAndFieldNameToFieldPair.getLeft();
		final Map<String, JsonFieldNameAndFieldPair> fieldNameToJsonFieldNameAndFieldPairMap =
			jsonFieldName2FieldNFieldName2JsonFieldNameAndFieldPairMapsPair.getRight();

		System.out.println("fieldNameToJsonFieldNameAndFieldPairMap: " + fieldNameToJsonFieldNameAndFieldPairMap);

		final int fieldSize = fieldNameToJsonFieldNameAndFieldPairMap.size();
		int max = 0;
		Entry<Constructor<T>, String[]> foundConstructor = null;
		for (Entry<Constructor<T>, String[]> entry : constructorMap.entrySet())
		{
			final String[] paramNames = entry.getValue();
			// final int fieldSize = fieldNameSet.size();
			int allFieldCount = 0;
			int matchingFieldCount = 0;
			for (final String paramName : paramNames)
			{
				// if (fieldNameSet.contains(paramName))
				if (fieldNameToJsonFieldNameAndFieldPairMap.containsKey(paramName))
				{
					matchingFieldCount++;
				}
				allFieldCount++;
			}
			System.out.println("fieldSize: " + fieldSize + " | count: " + allFieldCount + " | max: " + max);
			if (fieldSize == allFieldCount && fieldSize == matchingFieldCount)
			{
				System.out.println("yeah1");
				final Class<?>[] paramTypes = entry.getKey()
						.getParameterTypes();

				for (int i = 0; i < fieldSize; i++)
				{
					final String paramName = paramNames[i];
					System.out.println("paramName: " + paramName);
					final JsonFieldNameAndFieldPair jsonFieldNameAndFieldPair =
						fieldNameToJsonFieldNameAndFieldPairMap.get(paramName);
					final Field field = jsonFieldNameAndFieldPair.getRight();
					if (paramTypes[i].equals(field.getType()))
					{
						paramValues.add(resolveFieldValue(field, field.getType(),
								jsonObjectConvertible.get(jsonFieldNameAndFieldPair.getLeft())));
					}
				}
				return new ConstructorAndParamsPair<T, List<Object>>(entry.getKey(), paramValues);
			}
			else if (fieldSize < allFieldCount && max < allFieldCount)
			{
				System.out.println("yeah2");
				max = allFieldCount;
				foundConstructor = entry;
			}
		}

		if (null != foundConstructor)
		{
			System.out.println("yeah3");
			// TODO finish it!
			final Constructor<T> constructor = foundConstructor.getKey();
			final Class<?>[] paramTypes = constructor.getParameterTypes();
			final String[] paramNames = foundConstructor.getValue();

			for (int i = 0, size = paramTypes.length; i < size; i++)
			{
				final String paramName = paramNames[i];
				final JsonFieldNameAndFieldPair jsonFieldNameAndFieldPair =
					fieldNameToJsonFieldNameAndFieldPairMap.get(paramName);
				if (null == jsonFieldNameAndFieldPair)
				{
					paramValues.add(getDefaultValue(paramTypes[i]));
				}
				else
				{
					final Field field = jsonFieldNameAndFieldPair.getRight();
					if (paramTypes[i].equals(field.getType()))
					{
						paramValues.add(resolveFieldValue(field, field.getType(),
								jsonObjectConvertible.get(jsonFieldNameAndFieldPair.getLeft())));
					}
					else
					{
						paramValues.add(getDefaultValue(paramTypes[i]));
					}
				}
			}
			return new ConstructorAndParamsPair<T, List<Object>>(constructor, paramValues);
		}
		return null;
	}

	private <T> Object getDefaultValue(Class<T> typeClass)
	{
		if (typeClass.isPrimitive())
		{
			if (Integer.TYPE.equals(typeClass))
			{
				return Integer.valueOf(0);
			}
			else if (Long.TYPE.equals(typeClass))
			{
				return Long.valueOf(0);
			}
			else if (Double.TYPE.equals(typeClass))
			{
				return Double.valueOf(0);
			}
			else if (Boolean.TYPE.equals(typeClass))
			{
				return Boolean.FALSE;
			}
			else if (Byte.TYPE.equals(typeClass))
			{
				return Byte.valueOf((byte) 0);
			}
			else if (Short.TYPE.equals(typeClass))
			{
				return Short.valueOf((short) 0);
			}
			else if (Float.TYPE.equals(typeClass))
			{
				return Float.valueOf(0);
			}
			else
			{
				throw new IllegalArgumentException(format("Unknown type: [class: %s]", typeClass));
			}
		}
		return null;
	}

	public <E> Object resolveElement(Class<E> componentType, Object element) throws IllegalArgumentException,
			JsonStathamException, IllegalAccessException
	{
		System.out.println("componentType: " + componentType);
		System.out.println("element: " + element);
		final Class<?> elementType = element.getClass();
		final KnownTypeProcessorWithReflectionJsonToJavaConverter<Class<?>> knownTypeProcessorWithReflectionJsonToJavaConverter =
			jsonToJavaOneProcessorForKnownTypeDecider.decide(elementType);
		if (null != knownTypeProcessorWithReflectionJsonToJavaConverter)
		{
			return knownTypeProcessorWithReflectionJsonToJavaConverter.process(this, componentType, element);
		}
		if (JSONObject.class.isAssignableFrom(elementType))
		{
			return createFromJsonObject(componentType, new OrgJsonJsonObjectConvertible((JSONObject) element));
		}

		throw new UnsupportedOperationException();
	}

	private <T> T convertFromJsonArray(final Class<T> targetClass, final String jsonString)
			throws ArrayIndexOutOfBoundsException, IllegalArgumentException, InstantiationException,
			IllegalAccessException, InvocationTargetException
	{
		return createFromJsonArray(targetClass, jsonArrayConvertibleCreator.newJsonArrayConvertible(jsonString));
	}

	public <T, E> T createFromJsonArray(final Class<T> targetClass, final JsonArrayConvertible jsonArrayConvertible)
			throws IllegalArgumentException, JsonStathamException, IllegalAccessException
	{
		final int length = jsonArrayConvertible.length();
		if (targetClass.isArray())
		{
			final Class<?> componentType = targetClass.getComponentType();
			System.out.println("componentType: " + componentType);
			final Object array = Array.newInstance(componentType, length);
			for (int i = 0; i < length; i++)
			{
				Array.set(array, i, resolveElement(componentType, jsonArrayConvertible.get(i)));
			}
			@SuppressWarnings("unchecked")
			T t = (T) array;
			return t;
		}
		else if (Collection.class.isAssignableFrom(targetClass))
		{
			final List<Object> list = new ArrayList<Object>();
			for (int i = 0; i < length; i++)
			{
				list.add(resolveElement(Object.class, jsonArrayConvertible.get(i)));
			}
			@SuppressWarnings("unchecked")
			T t = (T) list;
			return t;
		}
		throw new JsonStathamException(format("Unknown type [class: %s] [JsonArrayConvertible: %s]", targetClass,
				jsonArrayConvertible));
	}

	@Override
	public <T> T convertFromJson(final Class<T> targetClass, final String jsonString)
			throws ArrayIndexOutOfBoundsException, IllegalArgumentException, InstantiationException,
			IllegalAccessException, InvocationTargetException
	{
		final String trimedJson = nullSafeTrim(jsonString);
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

	@Override
	public <T> T convertFromJson(final TypeHolder<T> typeHolder, final String jsonString) throws JsonStathamException,
			IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException
	{
		if (ParameterizedType.class.isAssignableFrom(typeHolder.getType()
				.getClass()))
		{
			final ParameterizedType parameterizedType = (ParameterizedType) typeHolder.getType();
			final Type type = parameterizedType.getRawType();
			if (Collection.class.isAssignableFrom((Class<?>) type))
			{
				final Class<Object> elementType = Generics.getGenericInfo(parameterizedType);

				@SuppressWarnings("unchecked")
				T t =
					(T) createCollectionWithValues((Class<Collection<Object>>) type, elementType,
							jsonArrayConvertibleCreator.newJsonArrayConvertible(jsonString));
				return t;
			}
			if (Map.class.isAssignableFrom((Class<?>) type))
			{
				final Class<?> valueType = Generics.extractFromParameterizedType(parameterizedType, 1);

				@SuppressWarnings("unchecked")
				T t =
					(T) createHashMapWithKeysAndValues(valueType,
							jsonObjectConvertibleCreator.newJsonObjectConvertible(jsonString));
				return t;
			}
		}
		throw new JsonStathamException();
	}
}

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
				/* It is annotated with @JsonConstructor so it should be used even if it is a private constructor. */
				constructor.setAccessible(true);
				return constructor.newInstance(argList.toArray());
			}
			/*
			 * No constructors annotated with @JsonConstructor are usable with the given parameters so try with the
			 * other ones which means do nothing here in this if block
			 */
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
					argList.add(arg);
				}
				return constructor.newInstance(argList.toArray());
			}
		}

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
				field.set(t, resolveFieldValue(field, field.getType(), jsonObjectConvertible.get(fieldEntry.getKey())));
			}
			return t;
		}

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
		return resolveTypeAndValue(field.getGenericType(), fieldType, value);
	}

	private <T> Object resolveTypeAndValue(final Type type, final Class<T> typeClass, final Object value)
			throws JsonStathamException, IllegalArgumentException, IllegalAccessException
	{
		if (type instanceof Class)
		{
			return resolveTypeAndValue(typeClass, value);
		}
		return resolveGenericTypeAndValue(type, typeClass, value);
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

		throw new JsonStathamException(format("Unknown type [class: %s][object: %s]", valueType, value));
	}

	private <T> Object resolveGenericTypeAndValue(final Type genericType, final Class<T> valueType, final Object value)
			throws IllegalAccessException
	{
		final KnownTypeProcessorWithReflectionJsonToJavaConverter<Type> knownTypeProcessorWithReflectionJsonToJavaConverter2 =
			jsonToJavaKnownDataStructureTypeProcessorDecider.decide(genericType);
		if (null != knownTypeProcessorWithReflectionJsonToJavaConverter2)
		{
			return knownTypeProcessorWithReflectionJsonToJavaConverter2.process(this, genericType, value);
		}
		throw new JsonStathamException(format("Unknown type [class: %s][object: %s]", valueType, value));
	}

	public <V> Map<String, Object> createHashMapWithKeysAndValues(final Type type, final Object value)
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
			if (type instanceof ParameterizedType)
			{
				final Map<String, Object> map = new HashMap<String, Object>();
				for (String name : JSONObject.getNames(jsonObject))
				{
					try
					{
						map.put(name, resolveTypeAndValue(type, null, jsonObject.get(name)));
					}
					catch (JSONException e)
					{
						e.printStackTrace();
						throw new JsonStathamException(e);
					}
				}
				return map;

			}

			if (type instanceof Class)
			{
				final Map<String, Object> map = new HashMap<String, Object>();
				for (String name : JSONObject.getNames(jsonObject))
				{
					try
					{
						final Class<?> typeClass = (Class<?>) type;
						map.put(name, resolveElement(typeClass, jsonObject.get(name)));
					}
					catch (JSONException e)
					{
						e.printStackTrace();
						throw new JsonStathamException(e);
					}
				}
				return map;
			}
		}
		throw new JsonStathamException(format("Unknown type: [class: %s][value: %s]", type, value));
	}

	public <E, C extends Collection<E>> Collection<?> createCollectionWithValues(Type type, Class<C> collectionClass,
			Object value) throws IllegalAccessException
	{
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
			if (type instanceof ParameterizedType)
			{
				final ParameterizedType parameterizedType = (ParameterizedType) type;
				final Collection<E> collection = newCollection(collectionClass);
				for (int i = 0, size = jsonArray.length(); i < size; i++)
				{
					try
					{
						final Type rawType = parameterizedType.getRawType();
						@SuppressWarnings("unchecked")
						final Class<E> rawClass = (Class<E>) rawType;
						collection.add(rawClass.cast(resolveTypeAndValue(type, null, jsonArray.get(i))));
					}
					catch (JSONException e)
					{
						throw new JsonStathamException(e);
					}
				}
				return collection;
			}
			if (type instanceof Class)
			{
				final Collection<E> collection = newCollection(collectionClass);

				for (int i = 0, size = jsonArray.length(); i < size; i++)
				{
					try
					{
						@SuppressWarnings("unchecked")
						final Class<E> elementType = (Class<E>) type;
						collection.add(elementType.cast(resolveElement(elementType, jsonArray.get(i))));
					}
					catch (JSONException e)
					{
						throw new JsonStathamException(e);
					}
				}
				return collection;
			}
		}
		return newCollection(collectionClass);

	}

	private <E, C extends Collection<E>> Collection<E> newCollection(Class<C> collectionClass)
	{
		Collection<E> collection = null;
		if (List.class.isAssignableFrom(collectionClass))
		{
			collection = new ArrayList<E>();
		}
		else if (Set.class.isAssignableFrom(collectionClass))
		{
			collection = new HashSet<E>();
		}
		else
		{
			collection = new ArrayList<E>();
		}
		return collection;
	}

	public <T> ConstructorAndParamsPair<T, String[]> findMatchingConstructor(
			Map<Constructor<T>, String[]> constructorMap,
			JsonFieldName2FieldNFieldName2JsonFieldNameAndFieldPairMapsPair jsonFieldNameToFieldNameAndFieldPairMap)
	{
		final Map<String, JsonFieldNameAndFieldPair> fieldNameToFieldMap =
			jsonFieldNameToFieldNameAndFieldPairMap.getRight();
		final int fieldSize = fieldNameToFieldMap.size();
		for (Entry<Constructor<T>, String[]> entry : constructorMap.entrySet())
		{
			final String[] paramNames = entry.getValue();
			if (fieldSize == paramNames.length)
			{
				int count = 0;
				for (final String paramName : paramNames)
				{
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
		final Map<String, JsonFieldNameAndFieldPair> fieldNameToJsonFieldNameAndFieldPairMap =
			jsonFieldName2FieldNFieldName2JsonFieldNameAndFieldPairMapsPair.getRight();

		final int fieldSize = fieldNameToJsonFieldNameAndFieldPairMap.size();
		int max = 0;
		Entry<Constructor<T>, String[]> foundConstructor = null;
		for (Entry<Constructor<T>, String[]> entry : constructorMap.entrySet())
		{
			final String[] paramNames = entry.getValue();
			int allFieldCount = 0;
			int matchingFieldCount = 0;
			for (final String paramName : paramNames)
			{
				if (fieldNameToJsonFieldNameAndFieldPairMap.containsKey(paramName))
				{
					matchingFieldCount++;
				}
				allFieldCount++;
			}
			if (fieldSize == allFieldCount && fieldSize == matchingFieldCount)
			{
				final Class<?>[] paramTypes = entry.getKey()
						.getParameterTypes();

				for (int i = 0; i < fieldSize; i++)
				{
					final String paramName = paramNames[i];
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
				max = allFieldCount;
				foundConstructor = entry;
			}
		}

		if (null != foundConstructor)
		{
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
			final Object array = Array.newInstance(componentType, length);
			for (int i = 0; i < length; i++)
			{
				Array.set(array, i, resolveElement(componentType, jsonArrayConvertible.get(i)));
			}
			@SuppressWarnings("unchecked")
			final T t = (T) array;
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
			final T t = (T) list;
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
			final Class<?> typeClass = (Class<?>) parameterizedType.getRawType();
			if (Collection.class.isAssignableFrom(typeClass))
			{
				@SuppressWarnings("unchecked")
				final T t =
					(T) createCollectionWithValues(parameterizedType.getActualTypeArguments()[0],
							(Class<Collection<T>>) typeClass,
							jsonArrayConvertibleCreator.newJsonArrayConvertible(jsonString));
				return t;
			}
			if (Map.class.isAssignableFrom(typeClass))
			{
				@SuppressWarnings("unchecked")
				final T t =
					(T) createHashMapWithKeysAndValues(parameterizedType.getActualTypeArguments()[1],
							jsonObjectConvertibleCreator.newJsonObjectConvertible(jsonString));
				return t;
			}
		}
		throw new JsonStathamException(format("Unknown type: [TypeHolder: %s][JSON: %s]", typeHolder, jsonString));
	}
}

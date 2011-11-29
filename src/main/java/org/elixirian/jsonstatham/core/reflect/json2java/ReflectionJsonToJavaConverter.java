/**
 *
 */
package org.elixirian.jsonstatham.core.reflect.json2java;

import static org.elixirian.kommonlee.collect.Lists.*;
import static org.elixirian.kommonlee.reflect.Classes.*;
import static org.elixirian.kommonlee.reflect.Primitives.*;
import static org.elixirian.kommonlee.util.MessageFormatter.*;
import static org.elixirian.kommonlee.util.Objects.*;
import static org.elixirian.kommonlee.util.Strings.*;
import static org.elixirian.kommonlee.validation.Assertions.*;

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

import org.elixirian.jsonstatham.annotation.JsonConstructor;
import org.elixirian.jsonstatham.annotation.JsonField;
import org.elixirian.jsonstatham.annotation.JsonObject;
import org.elixirian.jsonstatham.core.JsonToJavaConverter;
import org.elixirian.jsonstatham.core.KnownTypeProcessorWithReflectionJsonToJavaConverter;
import org.elixirian.jsonstatham.core.convertible.JsonArrayConvertible;
import org.elixirian.jsonstatham.core.convertible.JsonArrayConvertibleCreator;
import org.elixirian.jsonstatham.core.convertible.JsonObjectConvertible;
import org.elixirian.jsonstatham.core.convertible.JsonObjectConvertibleCreator;
import org.elixirian.jsonstatham.core.convertible.OrgJsonJsonArrayConvertible;
import org.elixirian.jsonstatham.core.convertible.OrgJsonJsonObjectConvertible;
import org.elixirian.jsonstatham.exception.JsonStathamException;
import org.elixirian.kommonlee.asm.analysis.AsmMethodAndConstructorAnalyser;
import org.elixirian.kommonlee.asm.analysis.ConstructorAnalyser;
import org.elixirian.kommonlee.reflect.TypeHolder;
import org.elixirian.kommonlee.type.Pair;
import org.json.JSONArray;
import org.json.JSONObject;

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
 * @version 0.0.1 (2010-09-08)
 * @version 0.0.2 (2010-12-23) refactored...
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

  public ReflectionJsonToJavaConverter(final JsonObjectConvertibleCreator jsonObjectConvertibleCreator,
      final JsonArrayConvertibleCreator jsonArrayConvertibleCreator)
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
    final String jsonFieldName;
    final Field field;

    public JsonFieldNameAndFieldPair(final String jsonFieldName, final Field field)
    {
      this.jsonFieldName = jsonFieldName;
      this.field = field;
    }

    @Override
    public String getFirst()
    {
      return jsonFieldName;
    }

    @Override
    public Field getSecond()
    {
      return field;
    }

    @Override
    public String toString()
    {
      return toStringBuilder(this).add("jsonFieldName", jsonFieldName)
          .add("field", field.getName())
          .toString();
    }
  }

  private static class JsonFieldName2FieldNFieldName2JsonFieldNameAndFieldPairMapsPair implements
      Pair<Map<String, Field>, Map<String, JsonFieldNameAndFieldPair>>
  {
    final Map<String, Field> jsonFieldNameToFieldMap;
    final Map<String, JsonFieldNameAndFieldPair> fieldNameToJsonFieldNameAndFieldPairMap;

    public JsonFieldName2FieldNFieldName2JsonFieldNameAndFieldPairMapsPair(
        final Map<String, Field> jsonFieldNameToFieldMap,
        final Map<String, JsonFieldNameAndFieldPair> fieldNameToJsonFieldNameAndFieldPairMap)
    {
      this.jsonFieldNameToFieldMap = jsonFieldNameToFieldMap;
      this.fieldNameToJsonFieldNameAndFieldPairMap = fieldNameToJsonFieldNameAndFieldPairMap;
    }

    @Override
    public Map<String, Field> getFirst()
    {
      return jsonFieldNameToFieldMap;
    }

    @Override
    public Map<String, JsonFieldNameAndFieldPair> getSecond()
    {
      return fieldNameToJsonFieldNameAndFieldPairMap;
    }

    @Override
    public String toString()
    {
      /* @formatter:off */
			return toStringBuilder(this)
    					.add("jsonFieldNameToFieldMap", jsonFieldNameToFieldMap)
    					.add("fieldNameToJsonFieldNameAndFieldPairMap", fieldNameToJsonFieldNameAndFieldPairMap)
    					.toString();
			/* @formatter:on */
    }
  }

  private void extractJsonFieldNames(final Class<?> sourceClass,
      final JsonFieldName2FieldNFieldName2JsonFieldNameAndFieldPairMapsPair jsonFieldNameToFieldNameAndFieldPairMap)
  {
    for (final Field field : sourceClass.getDeclaredFields())
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
         * no field name is set in the @JsonField annotation so use the actual field name for the JsonObject field.
         */
        jsonFieldName = fieldName;
      }

      if (jsonFieldNameToFieldNameAndFieldPairMap.jsonFieldNameToFieldMap.containsKey(jsonFieldName))
      {
        /* [ERROR] duplicate field names found */
        throw new JsonStathamException(format(
            "Json filed name must be unique. [JsonField name: %s] in [field: %s] is already used in another field.",
            jsonFieldName, field));
      }

      jsonFieldNameToFieldNameAndFieldPairMap.jsonFieldNameToFieldMap.put(jsonFieldName, field);
      jsonFieldNameToFieldNameAndFieldPairMap.fieldNameToJsonFieldNameAndFieldPairMap.put(fieldName,
          new JsonFieldNameAndFieldPair(jsonFieldName, field));
    }
  }

  private <T, A extends Annotation> Map<Constructor<T>, String[]> extractAllConstructorsWithAnnotations(
      final Map<Constructor<T>, String[]> constructorMap, final Class<? extends A>... annotations)
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
      final KnownTypeProcessorWithReflectionJsonToJavaConverter<Type> knownTypeProcessorWithReflectionJsonToJavaConverter =
        jsonToJavaKnownDataStructureTypeProcessorDecider.decide(targetClass);
      if (null != knownTypeProcessorWithReflectionJsonToJavaConverter)
      {
        @SuppressWarnings("unchecked")
        final T result =
          (T) knownTypeProcessorWithReflectionJsonToJavaConverter.process(this, targetClass, jsonObjectConvertible);
        return result;
      }
      return createFromJsonObject0(targetClass, jsonObjectConvertible);
    }
    catch (final IllegalArgumentException e)
    {
      throw e;
    }
    catch (final IllegalAccessException e)
    {
      throw e;
    }
    catch (final InstantiationException e)
    {
      throw new JsonStathamException(e);
    }
    catch (final InvocationTargetException e)
    {
      throw new JsonStathamException(e);
    }
  }

  private static class ConstructorAndParamsPair<T, P> implements Pair<Constructor<T>, P>
  {
    final Constructor<T> constructor;
    final P params;

    public ConstructorAndParamsPair(final Constructor<T> constructor, final P paramNames)
    {
      this.constructor = constructor;
      this.params = paramNames;
    }

    @Override
    public Constructor<T> getFirst()
    {
      return constructor;
    }

    @Override
    public P getSecond()
    {
      return params;
    }
  }

  private <T> T createFromJsonObject0(final Class<T> targetClass, final JsonObjectConvertible jsonObjectConvertible)
      throws IllegalArgumentException, IllegalAccessException, InstantiationException, InvocationTargetException
  {
    final List<Class<?>> classList =
      extractClassesWithAnnotationsInSuperToSubOrder(targetClass, Object.class, true, JsonObject.class);

    assertFalse(classList.isEmpty(), "The given type is not a JSON object type. " + "It must be annotated with %s.\n"
        + "[class: %s]", JsonObject.class.getName(), targetClass);

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
      /*
       * not empty so use it first!
       */
      for (final Constructor<T> constructor : constructorMapWithJsonConstructorAnnotation.keySet())
      {
        /* remove all the constructors with the annotation from the constructor map. */
        constructorMap.remove(constructor);
      }

      /*
       * First, find the constructor with all the parameters matched with all the JSON field.
       */
      final ConstructorAndParamsPair<T, String[]> constructorEntry =
        findMatchingConstructor(constructorMapWithJsonConstructorAnnotation,
            jsonFieldName2FieldNFieldName2JsonFieldNameAndFieldPairMapsPair);
      if (null == constructorEntry || null == constructorEntry.constructor)
      {
        /*
         * if there is no matching one, try to find the one annotated with @JsonConstructor having the fewest number of
         * non-matching parameters and the greatest number of matching parameters.
         */
        final ConstructorAndParamsPair<T, List<Object>> constructorToParamsPair =
          findConstructorWithMaxMatchingMinNonMatchingParams(constructorMapWithJsonConstructorAnnotation,
              jsonFieldName2FieldNFieldName2JsonFieldNameAndFieldPairMapsPair, jsonObjectConvertible);
        if (null != constructorToParamsPair)
        {
          return constructorToParamsPair.constructor.newInstance(constructorToParamsPair.params.toArray());
        }
      }
      else
      {
        final Constructor<T> constructor = constructorEntry.getFirst();
        final Map<String, JsonFieldNameAndFieldPair> fieldNameToFieldMap =
          jsonFieldName2FieldNFieldName2JsonFieldNameAndFieldPairMapsPair.getSecond();
        final List<Object> argList = newArrayList();
        for (final String fieldName : constructorEntry.getSecond())
        {
          final JsonFieldNameAndFieldPair jsonFieldNameAndFieldPair = fieldNameToFieldMap.get(fieldName);
          final Field field = jsonFieldNameAndFieldPair.field;
          argList.add(resolveFieldValue(field, field.getType(),
              jsonObjectConvertible.get(jsonFieldNameAndFieldPair.getFirst())));
        }
        /* It is annotated with @JsonConstructor so it should be used even if it is a private constructor. */
        constructor.setAccessible(true);
        return constructor.newInstance(argList.toArray());
      }
      /*
       * No constructors annotated with @JsonConstructor are usable with the given parameters so try with the other ones
       * which means do nothing here in this if block
       */
    }

    /* matching with all the json field */
    final Pair<Constructor<T>, String[]> constructorEntry =
      findMatchingConstructor(constructorMap, jsonFieldName2FieldNFieldName2JsonFieldNameAndFieldPairMapsPair);

    if (null != constructorEntry)
    {/* if constructorEntry is null try with any available constructors. */
      final Constructor<T> constructor = constructorEntry.getFirst();
      if (null != constructor)
      {
        final Map<String, JsonFieldNameAndFieldPair> fieldNameToFieldMap =
          jsonFieldName2FieldNFieldName2JsonFieldNameAndFieldPairMapsPair.getSecond();
        final List<Object> argList = newArrayList();
        for (final String fieldName : constructorEntry.getSecond())
        {
          final JsonFieldNameAndFieldPair jsonFieldNameAndFieldPair = fieldNameToFieldMap.get(fieldName);
          final Field field = jsonFieldNameAndFieldPair.field;
          final Object arg =
            resolveFieldValue(field, field.getType(), jsonObjectConvertible.get(jsonFieldNameAndFieldPair.getFirst()));
          argList.add(arg);
        }
        return constructor.newInstance(argList.toArray());
      }
    }

    // find constructor with minimum matching params.
    final Pair<Constructor<T>, List<Object>> constructorToParamsPair =
      findConstructorWithMaxMatchingMinNonMatchingParams(constructorMap,
          jsonFieldName2FieldNFieldName2JsonFieldNameAndFieldPairMapsPair, jsonObjectConvertible);

    if (null != constructorToParamsPair)
    {
      return constructorToParamsPair.getFirst()
          .newInstance(constructorToParamsPair.getSecond()
              .toArray());
    }

    // no arg constructor
    final Constructor<T> constructor = findConstructor(targetClass, EMPTY_CLASS_ARRAY);
    if (null != constructor)
    {
      // use it
      constructor.setAccessible(true);
      final T t = constructor.newInstance();
      // set the values;
      for (final Entry<String, Field> fieldEntry : jsonFieldName2FieldNFieldName2JsonFieldNameAndFieldPairMapsPair.getFirst()
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

  private <T> Object resolveFieldValue(final Field field, final Class<T> fieldType, final Object value)
      throws JsonStathamException, IllegalArgumentException, IllegalAccessException
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

  public <V, M extends Map<String, Object>> Map<String, Object> createHashMapWithKeysAndValues(final Class<M> mapType,
      final Type valueType, final Object value) throws IllegalAccessException
  {
    /* @formatter:off */
		final JsonObjectConvertible jsonObject =
			JsonObjectConvertible.class.isAssignableFrom(value.getClass()) ?
				/* JsonObjectConvertible */
				(JsonObjectConvertible) value :
			JSONObject.class.isAssignableFrom(value.getClass()) ?
				/* JSONObject */
				new OrgJsonJsonObjectConvertible((JSONObject) value) :
				/* unknown */
				null;
		/* @formatter:on */

    if (null != jsonObject)
    {
      if (valueType instanceof ParameterizedType)
      {
        final Map<String, Object> map = newMap(mapType);
        for (final String name : jsonObject.getNames())
        {
          map.put(name, resolveTypeAndValue(valueType, null, jsonObject.get(name)));
        }
        return map;
      }

      if (valueType instanceof Class)
      {
        final Map<String, Object> map = newMap(mapType);
        for (final String name : jsonObject.getNames())
        {
          final Class<?> typeClass = (Class<?>) valueType;
          map.put(name, resolveElement(typeClass, jsonObject.get(name)));
        }
        return map;
      }
    }
    throw new JsonStathamException(format("Unknown type: [class: %s][value: %s]", valueType, value));
  }

  private <E, M extends Map<String, E>> Map<String, E> newMap(final Class<M> mapClass)
  {
    try
    {
      if (Map.class.isAssignableFrom(mapClass))
      {
        return mapClass.isInterface() ? new HashMap<String, E>() : mapClass.newInstance();
      }

    }
    catch (final Exception e)
    {
      throw new JsonStathamException(format("The given collectionClass [class: %s] cannot be instantiated.", mapClass),
          e);
    }
    return new HashMap<String, E>();
  }

  public <E, C extends Collection<E>> Collection<?> createCollectionWithValues(final Class<C> collectionClass,
      final Type valueType, final Object value) throws IllegalAccessException
  {
    /* @formatter:off */
		final JsonArrayConvertible jsonArray =
			/* JsonArrayConvertible. */
			JsonArrayConvertible.class.isAssignableFrom(value.getClass()) ?
			(JsonArrayConvertible) value :

			/* JSONArray. */
			JSONArray.class.isAssignableFrom(value.getClass()) ?
			new OrgJsonJsonArrayConvertible((JSONArray) value) :

			/* Neither JsonArrayConvertible nor JSONArray  */
			null;
		/* @formatter:on */

    if (null != jsonArray)
    {
      if (valueType instanceof ParameterizedType)
      {
        final ParameterizedType parameterizedType = (ParameterizedType) valueType;
        final Collection<E> collection = newCollection(collectionClass);
        for (int i = 0, size = jsonArray.length(); i < size; i++)
        {
          final Type rawType = parameterizedType.getRawType();
          @SuppressWarnings("unchecked")
          final Class<E> rawClass = (Class<E>) rawType;
          collection.add(rawClass.cast(resolveTypeAndValue(valueType, null, jsonArray.get(i))));
        }
        return collection;
      }
      if (valueType instanceof Class)
      {
        final Collection<E> collection = newCollection(collectionClass);

        for (int i = 0, size = jsonArray.length(); i < size; i++)
        {
          @SuppressWarnings("unchecked")
          final Class<E> elementType = (Class<E>) valueType;
          collection.add(elementType.cast(resolveElement(elementType, jsonArray.get(i))));
        }
        return collection;
      }
    }
    return newCollection(collectionClass);

  }

  private <E, C extends Collection<E>> Collection<E> newCollection(final Class<C> collectionClass)
  {
    try
    {
      if (List.class.isAssignableFrom(collectionClass))
      {
        return collectionClass.isInterface() ? new ArrayList<E>() : collectionClass.newInstance();
      }

      if (Set.class.isAssignableFrom(collectionClass))
      {
        return collectionClass.isInterface() ? new HashSet<E>() : collectionClass.newInstance();
      }
    }
    catch (final Exception e)
    {
      throw new JsonStathamException(format("The given collectionClass [class: %s] cannot be instantiated.",
          collectionClass), e);
    }
    return newArrayList();
  }

  public <T> ConstructorAndParamsPair<T, String[]> findMatchingConstructor(
      final Map<Constructor<T>, String[]> constructorMap,
      final JsonFieldName2FieldNFieldName2JsonFieldNameAndFieldPairMapsPair jsonFieldNameToFieldNameAndFieldPairMap)
  {
    final Map<String, JsonFieldNameAndFieldPair> fieldNameToFieldMap =
      jsonFieldNameToFieldNameAndFieldPairMap.fieldNameToJsonFieldNameAndFieldPairMap;
    final int fieldSize = fieldNameToFieldMap.size();
    for (final Entry<Constructor<T>, String[]> entry : constructorMap.entrySet())
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
                .getSecond()
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

  public <T> ConstructorAndParamsPair<T, List<Object>> findConstructorWithMaxMatchingMinNonMatchingParams(
      final Map<Constructor<T>, String[]> constructorMap,
      final JsonFieldName2FieldNFieldName2JsonFieldNameAndFieldPairMapsPair jsonFieldName2FieldNFieldName2JsonFieldNameAndFieldPairMapsPair,
      final JsonObjectConvertible jsonObjectConvertible) throws JsonStathamException, IllegalArgumentException,
      IllegalAccessException
  {
    final List<Object> paramValues = newArrayList();
    final Map<String, JsonFieldNameAndFieldPair> fieldNameToJsonFieldNameAndFieldPairMap =
      jsonFieldName2FieldNFieldName2JsonFieldNameAndFieldPairMapsPair.fieldNameToJsonFieldNameAndFieldPairMap;

    final int fieldSize = fieldNameToJsonFieldNameAndFieldPairMap.size();
    int max = 0;
    Entry<Constructor<T>, String[]> foundConstructor = null;
    for (final Entry<Constructor<T>, String[]> entry : constructorMap.entrySet())
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
          final Field field = jsonFieldNameAndFieldPair.field;
          if (paramTypes[i].equals(field.getType()))
          {
            paramValues.add(resolveFieldValue(field, field.getType(),
                jsonObjectConvertible.get(jsonFieldNameAndFieldPair.getFirst())));
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
          final Field field = jsonFieldNameAndFieldPair.field;
          if (paramTypes[i].equals(field.getType()))
          {
            paramValues.add(resolveFieldValue(field, field.getType(),
                jsonObjectConvertible.get(jsonFieldNameAndFieldPair.jsonFieldName)));
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

  /**
   * <p>
   * <a href="http://java.sun.com/docs/books/jls/second_edition/html/typesValues.doc.html#96595">4.5.5 Initial Values of
   * Variables</a>
   * </p>
   * <ul>
   * <li>Each class variable, instance variable, or array component is initialized with a default value when it is
   * created (§15.9, §15.10):
   * <ul>
   * <li>For type byte, the default value is zero, that is, the value of (byte)0.</li>
   * <li>For type short, the default value is zero, that is, the value of (short)0.</li>
   * <li>For type int, the default value is zero, that is, 0.</li>
   * <li>For type long, the default value is zero, that is, 0L.</li>
   * <li>For type float, the default value is positive zero, that is, 0.0f.</li>
   * <li>For type double, the default value is positive zero, that is, 0.0d.</li>
   * <li>For type char, the default value is the null character, that is, '\u0000'.</li>
   * <li>For type boolean, the default value is false.</li>
   * <li>For all reference types (§4.3), the default value is null.</li>
   * </ul>
   * </li>
   * </ul>
   */
  private <T> Object getDefaultValue(final Class<T> typeClass)
  {
    if (typeClass.isPrimitive())
    {
      return getPrimitiveDefaultValueObject(typeClass);
    }
    return null;
  }

  public <E> Object resolveElement(final Class<E> componentType, final Object element) throws IllegalArgumentException,
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
      throws ArrayIndexOutOfBoundsException, IllegalArgumentException, InstantiationException, IllegalAccessException,
      InvocationTargetException
  {
    return createFromJsonArray(targetClass, jsonArrayConvertibleCreator.newJsonArrayConvertible(jsonString));
  }

  public <T, E> T createFromJsonArray(final Class<T> targetClass, final JsonArrayConvertible jsonArrayConvertible)
      throws IllegalArgumentException, JsonStathamException, IllegalAccessException, InstantiationException
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
      // @SuppressWarnings("unchecked")
      // final List<Object> list = (List<Object>) (targetClass.isInterface() ? new ArrayList<Object>() :
      // targetClass.newInstance());
      final List<Object> list = newArrayList();
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
  public <T> T convertFromJson(final Class<T> targetClass, final String json) throws ArrayIndexOutOfBoundsException,
      IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException
  {
    final String jsonString = toStringOf(json).trim();
    if ('{' == jsonString.charAt(0))
    {
      return convertFromJsonObject(targetClass, jsonString);
    }
    else if ('[' == jsonString.charAt(0))
    {
      return convertFromJsonArray(targetClass, jsonString);
    }
    else if ("null".equals(jsonString))
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
          (T) createCollectionWithValues((Class<Collection<T>>) typeClass,
              parameterizedType.getActualTypeArguments()[0],
              jsonArrayConvertibleCreator.newJsonArrayConvertible(jsonString));
        return t;
      }
      if (Map.class.isAssignableFrom(typeClass))
      {
        @SuppressWarnings("unchecked")
        final T t =
          (T) createHashMapWithKeysAndValues((Class<Map<String, Object>>) typeClass,
              parameterizedType.getActualTypeArguments()[1],
              jsonObjectConvertibleCreator.newJsonObjectConvertible(jsonString));
        return t;
      }
    }
    throw new JsonStathamException(format("Unknown type: [TypeHolder: %s][JSON: %s]", typeHolder, jsonString));
  }
}

/**
 * 
 */
package com.lckymn.kevin.jsonstatham.core.reflect;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.lckymn.kevin.jsonstatham.core.KnownTypeProcessorDeciderForJavaToJson;
import com.lckymn.kevin.jsonstatham.core.KnownTypeProcessorWithReflectionJavaToJsonConverter;
import com.lckymn.kevin.jsonstatham.core.SimpleKnownTypeChecker;
import com.lckymn.kevin.jsonstatham.exception.JsonStathamException;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-06-10)
 */
public class OneProcessorForKnownTypeDecider implements KnownTypeProcessorDeciderForJavaToJson
{
	public static final KnownTypeProcessorWithReflectionJavaToJsonConverter DEFAULT_KNOWN_TYPE_PROCESSOR;
	public static final Set<Class<?>> DAFAULT_KNOWN_BASIC_TYPE_SET;
	public static final Set<Class<?>> DAFAULT_KNOWN_EXTENSIBLE_BASIC_TYPE_SET;
	public static final SimpleKnownTypeChecker[] DAFAULT_SIMPLE_TYPE_CHECKERS;

	static
	{
		DEFAULT_KNOWN_TYPE_PROCESSOR = new KnownTypeProcessorWithReflectionJavaToJsonConverter()
		{
			@Override
			public Object process(
					@SuppressWarnings("unused") ReflectionJavaToJsonConverter reflectionJavaToJsonConverter,
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

	private final KnownTypeProcessorWithReflectionJavaToJsonConverter knownTypeProcessorWithReflectionJavaToJsonConverter;
	private final Set<Class<?>> knownBasicTypeSet;
	private final Set<Class<?>> knownExtensibleBasicTypeSet;
	private final SimpleKnownTypeChecker[] simpleKnownTypeCheckers;

	public OneProcessorForKnownTypeDecider()
	{
		this.knownTypeProcessorWithReflectionJavaToJsonConverter = DEFAULT_KNOWN_TYPE_PROCESSOR;
		this.knownBasicTypeSet = DAFAULT_KNOWN_BASIC_TYPE_SET;
		this.simpleKnownTypeCheckers = DAFAULT_SIMPLE_TYPE_CHECKERS;
		this.knownExtensibleBasicTypeSet = DAFAULT_KNOWN_EXTENSIBLE_BASIC_TYPE_SET;
	}

	public OneProcessorForKnownTypeDecider(
			KnownTypeProcessorWithReflectionJavaToJsonConverter knownTypeProcessorWithReflectionJavaToJsonConverter,
			Set<Class<?>> knownBasicTypeSet, Set<Class<?>> knownExtensibleBasicTypeSet,
			SimpleKnownTypeChecker... simpleTypeCheckers)
	{
		this.knownTypeProcessorWithReflectionJavaToJsonConverter = knownTypeProcessorWithReflectionJavaToJsonConverter;
		this.knownBasicTypeSet = knownBasicTypeSet;
		this.knownExtensibleBasicTypeSet = knownExtensibleBasicTypeSet;
		this.simpleKnownTypeCheckers = simpleTypeCheckers;
	}

	/*
	 * (non-Javadoc)
	 * @see com.lckymn.kevin.jsonstatham.core.KnownTypeProcessorDeciderForJavaToJson#getKnownTypeProcessor(java.lang.Object)
	 */
	@Override
	public KnownTypeProcessorWithReflectionJavaToJsonConverter decide(Class<?> type)
	{
		for (SimpleKnownTypeChecker simpleKnownTypeChecker : simpleKnownTypeCheckers)
		{
			if (simpleKnownTypeChecker.isKnown(type))
			{
				return knownTypeProcessorWithReflectionJavaToJsonConverter;
			}
		}
		if (knownBasicTypeSet.contains(type))
		{
			return knownTypeProcessorWithReflectionJavaToJsonConverter;
		}

		for (Class<?> knownType : knownExtensibleBasicTypeSet)
		{
			if (knownType.isAssignableFrom(type))
			{
				return knownTypeProcessorWithReflectionJavaToJsonConverter;
			}
		}
		return null;
	}

}

/**
 * 
 */
package com.lckymn.kevin.jsonstatham.core.reflect;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.lckymn.kevin.jsonstatham.core.KnownTypeProcessor;
import com.lckymn.kevin.jsonstatham.core.KnownTypeProcessorDecider;
import com.lckymn.kevin.jsonstatham.exception.JsonStathamException;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-06-10)
 */
public class OneProcessorForKnownTypeDecider implements KnownTypeProcessorDecider
{
	public interface SimpleTypeChecker
	{
		boolean isKnown(Class<?> type);
	}

	public static final KnownTypeProcessor DEFAULT_KNOWN_TYPE_PROCESSOR;
	public static final Set<Class<?>> DAFAULT_KNOWN_BASIC_TYPE_SET;
	public static final Set<Class<?>> DAFAULT_KNOWN_EXTENSIBLE_BASIC_TYPE_SET;
	public static final SimpleTypeChecker[] DAFAULT_SIMPLE_TYPE_CHECKERS;

	static
	{
		DEFAULT_KNOWN_TYPE_PROCESSOR = new KnownTypeProcessor()
		{
			@Override
			public Object process(@SuppressWarnings("unused") ReflectionJavaToJsonConverter reflectionJavaToJsonConverter, Object source)
					throws IllegalArgumentException, IllegalAccessException, JsonStathamException
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

		DAFAULT_SIMPLE_TYPE_CHECKERS = new SimpleTypeChecker[] { new SimpleTypeChecker()
		{

			@Override
			public boolean isKnown(Class<?> type)
			{
				return type.isPrimitive();
			}
		}, new SimpleTypeChecker()
		{

			@Override
			public boolean isKnown(Class<?> type)
			{
				return type.isEnum();
			}
		} };
	}

	private final KnownTypeProcessor knownTypeProcessor;
	private final Set<Class<?>> knownBasicTypeSet;
	private final Set<Class<?>> knownExtensibleBasicTypeSet;
	private final SimpleTypeChecker[] simpleTypeCheckers;

	public OneProcessorForKnownTypeDecider()
	{
		this.knownTypeProcessor = DEFAULT_KNOWN_TYPE_PROCESSOR;
		this.knownBasicTypeSet = DAFAULT_KNOWN_BASIC_TYPE_SET;
		this.simpleTypeCheckers = DAFAULT_SIMPLE_TYPE_CHECKERS;
		this.knownExtensibleBasicTypeSet = DAFAULT_KNOWN_EXTENSIBLE_BASIC_TYPE_SET;
	}

	public OneProcessorForKnownTypeDecider(KnownTypeProcessor knownTypeProcessor, Set<Class<?>> knownBasicTypeSet,
			Set<Class<?>> knownExtensibleBasicTypeSet, SimpleTypeChecker... simpleTypeCheckers)
	{
		this.knownTypeProcessor = knownTypeProcessor;
		this.knownBasicTypeSet = knownBasicTypeSet;
		this.knownExtensibleBasicTypeSet = knownExtensibleBasicTypeSet;
		this.simpleTypeCheckers = simpleTypeCheckers;
	}

	/*
	 * (non-Javadoc)
	 * @see com.lckymn.kevin.jsonstatham.core.KnownTypeProcessorDecider#getKnownTypeProcessor(java.lang.Object)
	 */
	@Override
	public KnownTypeProcessor decide(Class<?> type)
	{
		for (SimpleTypeChecker simpleTypeChecker : simpleTypeCheckers)
		{
			if (simpleTypeChecker.isKnown(type))
			{
				return knownTypeProcessor;
			}
		}
		if (knownBasicTypeSet.contains(type))
		{
			return knownTypeProcessor;
		}

		for (Class<?> knownType : knownExtensibleBasicTypeSet)
		{
			if (knownType.isAssignableFrom(type))
			{
				return knownTypeProcessor;
			}
		}
		return null;
	}

}

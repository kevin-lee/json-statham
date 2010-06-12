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
public class KnownBasicTypeDecider implements KnownTypeProcessorDecider
{
	public interface KnownBasicTypeChecker
	{
		boolean isKnown(Class<?> type);
	}

	public static final KnownTypeProcessor DEFAULT_KNOWN_TYPE_PROCESSOR;
	public static final Set<Class<?>> DAFAULT_KNOWN_BASIC_TYPE_SET;
	public static final KnownBasicTypeChecker[] DAFAULT_KNOWN_BASIC_TYPE_CHECKERS;

	static
	{
		DEFAULT_KNOWN_TYPE_PROCESSOR = new KnownTypeProcessor()
		{
			@Override
			public Object process(@SuppressWarnings("unused") ReflectionJsonStatham jsonStatham, Object source)
					throws IllegalArgumentException, IllegalAccessException, JsonStathamException
			{
				return source;
			}
		};

		final Set<Class<?>> tempSet = new HashSet<Class<?>>();
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
		tempSet.add(Number.class);
		tempSet.add(Boolean.TYPE);
		tempSet.add(Boolean.class);
		tempSet.add(String.class);
		DAFAULT_KNOWN_BASIC_TYPE_SET = Collections.unmodifiableSet(tempSet);

		DAFAULT_KNOWN_BASIC_TYPE_CHECKERS = new KnownBasicTypeChecker[] { new KnownBasicTypeChecker()
		{

			@Override
			public boolean isKnown(Class<?> type)
			{
				return type.isPrimitive();
			}
		}, new KnownBasicTypeChecker()
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
	private final KnownBasicTypeChecker[] knownBasicTypeCheckers;

	public KnownBasicTypeDecider()
	{
		knownTypeProcessor = DEFAULT_KNOWN_TYPE_PROCESSOR;
		knownBasicTypeSet = DAFAULT_KNOWN_BASIC_TYPE_SET;
		knownBasicTypeCheckers = DAFAULT_KNOWN_BASIC_TYPE_CHECKERS;
	}

	public KnownBasicTypeDecider(KnownTypeProcessor knownTypeProcessor, Set<Class<?>> knownBasicTypeSet,
			KnownBasicTypeChecker... knownBasicTypeCheckers)
	{
		this.knownTypeProcessor = knownTypeProcessor;
		this.knownBasicTypeSet = knownBasicTypeSet;
		this.knownBasicTypeCheckers = knownBasicTypeCheckers;
	}

	/*
	 * (non-Javadoc)
	 * @see com.lckymn.kevin.jsonstatham.core.KnownTypeProcessorDecider#getKnownTypeProcessor(java.lang.Object)
	 */
	@Override
	public KnownTypeProcessor decide(Class<?> type)
	{
		for (KnownBasicTypeChecker knownBasicTypeChecker : knownBasicTypeCheckers)
		{
			if (knownBasicTypeChecker.isKnown(type))
			{
				return knownTypeProcessor;
			}
		}
		for (Class<?> knownType : knownBasicTypeSet)
		{
			if (knownType.isAssignableFrom(type))
			{
				return knownTypeProcessor;
			}
		}
		return null;
	}

}

/**
 * 
 */
package com.lckymn.kevin.jsonstatham.core.reflect;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;
import org.mockito.Mockito;

import com.lckymn.kevin.jsonstatham.core.KnownTypeProcessor;
import com.lckymn.kevin.jsonstatham.core.KnownTypeProcessorDecider;
import com.lckymn.kevin.jsonstatham.core.convertible.OrgJsonJsonArrayConvertibleCreator;
import com.lckymn.kevin.jsonstatham.core.convertible.OrgJsonOrderedJsonObjectConvertibleCreator;
import com.lckymn.kevin.jsonstatham.core.reflect.KnownDataStructureTypeProcessorDecider;
import com.lckymn.kevin.jsonstatham.core.reflect.KnownObjectReferenceTypeProcessorDecider;
import com.lckymn.kevin.jsonstatham.core.reflect.OneProcessorForKnownTypeDecider;
import com.lckymn.kevin.jsonstatham.core.reflect.ReflectionJavaToJsonConverter;
import com.lckymn.kevin.jsonstatham.exception.JsonStathamException;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-06-11)
 */
public class KnownObjectReferenceTypeProcessorDeciderTest
{
	private static final Date DATE = new Date();
	private static final Calendar CALENDAR = Calendar.getInstance();
	private static final Map<String, String> MAP = new HashMap<String, String>();

	/**
	 * Test method for
	 * {@link com.lckymn.kevin.jsonstatham.core.reflect.KnownObjectReferenceTypeProcessorDecider#KnownObjectReferenceTypeProcessorDecider()}.
	 */
	@Test
	public final void testKnownObjectReferenceTypeProcessorDecider()
	{
		final KnownTypeProcessorDecider knownTypeProcessorDecider = new KnownObjectReferenceTypeProcessorDecider();
		assertThat(knownTypeProcessorDecider.decide(DATE.getClass()), is(not(nullValue())));
		assertThat(knownTypeProcessorDecider.decide(CALENDAR.getClass()), is(not(nullValue())));

		for (Entry<String, String> entry : MAP.entrySet())
		{
			assertThat(knownTypeProcessorDecider.decide(entry.getClass()), is(not(nullValue())));
		}

		assertThat(knownTypeProcessorDecider.decide(new ArrayList<String>().getClass()), is(nullValue()));
	}

	/**
	 * Test method for
	 * {@link com.lckymn.kevin.jsonstatham.core.reflect.KnownObjectReferenceTypeProcessorDecider#KnownObjectReferenceTypeProcessorDecider(java.util.Map)}
	 * .
	 * 
	 * @throws IllegalAccessException
	 * @throws JsonStathamException
	 * @throws IllegalArgumentException
	 */
	@Test
	public final void testKnownObjectReferenceTypeProcessorDeciderMapOfClassOfQKnownTypeProcessor() throws IllegalArgumentException,
			JsonStathamException, IllegalAccessException
	{
		class TestClass
		{
			private final Long id;
			private final String name;

			public TestClass(Long id, String name)
			{
				this.id = id;
				this.name = name;
			}
		}

		final TestClass testClass = new TestClass(Long.valueOf(999L), "Kevin");

		final Map<Class<?>, KnownTypeProcessor> map = new HashMap<Class<?>, KnownTypeProcessor>();
		map.put(TestClass.class, new KnownTypeProcessor()
		{
			@Override
			public Object process(@SuppressWarnings("unused") ReflectionJavaToJsonConverter reflectionJavaToJsonConverter, Object source)
					throws IllegalArgumentException, IllegalAccessException, JsonStathamException
			{
				final TestClass testClassObject = (TestClass) source;
				return "id: " + testClassObject.id + " | name: " + testClassObject.name;
			}
		});
		final KnownTypeProcessorDecider knownTypeProcessorDecider = new KnownObjectReferenceTypeProcessorDecider(map);

		assertThat(knownTypeProcessorDecider.decide(testClass.getClass()), is(not(nullValue())));
		assertThat(knownTypeProcessorDecider.decide(testClass.getClass())
				.process(null, testClass), equalTo((Object) ("id: " + testClass.id + " | name: " + testClass.name)));

		assertThat(knownTypeProcessorDecider.decide(DATE.getClass()), is(nullValue()));
		assertThat(knownTypeProcessorDecider.decide(CALENDAR.getClass()), is(nullValue()));

		for (Entry<String, String> entry : MAP.entrySet())
		{
			assertThat(knownTypeProcessorDecider.decide(entry.getClass()), is(nullValue()));
		}
	}

	/**
	 * Test method for {@link com.lckymn.kevin.jsonstatham.core.reflect.KnownObjectReferenceTypeProcessorDecider#decide(java.lang.Class)}.
	 * 
	 * @throws IllegalAccessException
	 * @throws JsonStathamException
	 * @throws IllegalArgumentException
	 */
	@Test
	public final void testDecide() throws IllegalArgumentException, JsonStathamException, IllegalAccessException
	{
		final KnownDataStructureTypeProcessorDecider knownDataStructureTypeProcessorDecider =
			mock(KnownDataStructureTypeProcessorDecider.class);
		when(knownDataStructureTypeProcessorDecider.decide(Mockito.any(Class.class))).thenReturn(null);
		final OneProcessorForKnownTypeDecider oneProcessorForKnownTypeDecider = mock(OneProcessorForKnownTypeDecider.class);
		when(oneProcessorForKnownTypeDecider.decide(String.class)).thenReturn(new KnownTypeProcessor()
		{
			@Override
			public Object process(@SuppressWarnings("unused") ReflectionJavaToJsonConverter reflectionJavaToJsonConverter, Object source)
					throws IllegalArgumentException, IllegalAccessException, JsonStathamException
			{
				return source;
			}
		});

		final ReflectionJavaToJsonConverter reflectionJavaToJsonConverter =
			new ReflectionJavaToJsonConverter(new OrgJsonOrderedJsonObjectConvertibleCreator(), new OrgJsonJsonArrayConvertibleCreator(),
					knownDataStructureTypeProcessorDecider, new KnownObjectReferenceTypeProcessorDecider(), oneProcessorForKnownTypeDecider);
		// final JsonStathamInAction jsonStathamInAction =
		// new JsonStathamInAction(reflectionJavaToJsonConverter, new ReflectionJsonToJavaConverter());
		final KnownTypeProcessorDecider knownTypeProcessorDecider = new KnownObjectReferenceTypeProcessorDecider();

		assertThat(knownTypeProcessorDecider.decide(DATE.getClass())
				.process(reflectionJavaToJsonConverter, DATE), equalTo(reflectionJavaToJsonConverter.createJsonValue(DATE.toString())));

		assertThat(knownTypeProcessorDecider.decide(CALENDAR.getClass())
				.process(reflectionJavaToJsonConverter, CALENDAR), equalTo(reflectionJavaToJsonConverter.createJsonValue(CALENDAR.getTime()
				.toString())));

		for (Entry<String, String> entry : MAP.entrySet())
		{
			assertThat(knownTypeProcessorDecider.decide(entry.getClass())
					.process(reflectionJavaToJsonConverter, entry)
					.toString(), equalTo(reflectionJavaToJsonConverter.newJsonObjectConvertible()
					.put(entry.getKey(), reflectionJavaToJsonConverter.createJsonValue(entry.getValue()))
					.toString()));
		}

		assertThat(knownTypeProcessorDecider.decide(new ArrayList<String>().getClass()), is(nullValue()));
	}
}

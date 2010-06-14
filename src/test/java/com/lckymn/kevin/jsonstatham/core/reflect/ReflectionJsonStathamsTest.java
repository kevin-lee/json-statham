/**
 * 
 */
package com.lckymn.kevin.jsonstatham.core.reflect;

import static com.lckymn.kevin.common.test.CommonTestHelper.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.lckymn.kevin.jsonstatham.core.JsonArrayConvertibleCreator;
import com.lckymn.kevin.jsonstatham.core.JsonObjectConvertibleCreator;
import com.lckymn.kevin.jsonstatham.core.KnownTypeProcessorDecider;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-06-14)
 */
public class ReflectionJsonStathamsTest
{

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception
	{
	}

	@Test(expected = IllegalStateException.class)
	public void testReflectionJsonStathams() throws Exception
	{
		testNotAccessibleConstructor(ReflectionJsonStathams.class, EMPTY_CLASS_ARRAY, EMPTY_OBJECT_ARRAY);
	}

	/**
	 * Test method for
	 * {@link com.lckymn.kevin.jsonstatham.core.reflect.ReflectionJsonStathams#newReflectionJsonStatham(com.lckymn.kevin.jsonstatham.core.JsonObjectConvertibleCreator, com.lckymn.kevin.jsonstatham.core.JsonArrayConvertibleCreator, com.lckymn.kevin.jsonstatham.core.reflect.KnownDataStructureTypeProcessorDecider, com.lckymn.kevin.jsonstatham.core.reflect.KnownObjectReferenceTypeProcessorDecider, com.lckymn.kevin.jsonstatham.core.reflect.OneProcessorForKnownTypeDecider)}
	 * .
	 */
	@Test
	public final void testNewReflectionJsonStathamJsonObjectConvertibleCreatorJsonArrayConvertibleCreatorKnownDataStructureTypeProcessorDeciderKnownObjectReferenceTypeProcessorDeciderOneProcessorForKnownTypeDecider()
	{
		final JsonObjectConvertibleCreator jsonObjectConvertibleCreator = mock(JsonObjectConvertibleCreator.class);
		final JsonArrayConvertibleCreator jsonArrayConvertibleCreator = mock(JsonArrayConvertibleCreator.class);
		final KnownDataStructureTypeProcessorDecider knownDataStructureTypeProcessorDecider =
			mock(KnownDataStructureTypeProcessorDecider.class);
		final KnownObjectReferenceTypeProcessorDecider knownObjectReferenceTypeProcessorDecider =
			mock(KnownObjectReferenceTypeProcessorDecider.class);
		final OneProcessorForKnownTypeDecider oneProcessorForKnownTypeDecider = mock(OneProcessorForKnownTypeDecider.class);

		final ReflectionJsonStatham reflectionJsonStatham =
			ReflectionJsonStathams.newReflectionJsonStatham(jsonObjectConvertibleCreator, jsonArrayConvertibleCreator,
					knownDataStructureTypeProcessorDecider, knownObjectReferenceTypeProcessorDecider, oneProcessorForKnownTypeDecider);
		assertThat(reflectionJsonStatham.getJsonObjectConvertibleCreator(), equalTo(jsonObjectConvertibleCreator));
		assertThat(reflectionJsonStatham.getJsonArrayConvertibleCreator(), equalTo(jsonArrayConvertibleCreator));
		assertThat(reflectionJsonStatham.getKnownDataStructureTypeProcessorDecider(), equalTo(knownDataStructureTypeProcessorDecider));
		assertTrue(Arrays.deepEquals(new KnownTypeProcessorDecider[] { knownDataStructureTypeProcessorDecider,
				knownObjectReferenceTypeProcessorDecider, oneProcessorForKnownTypeDecider },
				reflectionJsonStatham.getKnownTypeProcessorDeciders()));
	}

	/**
	 * Test method for {@link com.lckymn.kevin.jsonstatham.core.reflect.ReflectionJsonStathams#newReflectionJsonStatham()}.
	 */
	@Test
	public final void testNewReflectionJsonStatham()
	{
		final ReflectionJsonStatham reflectionJsonStatham = ReflectionJsonStathams.newReflectionJsonStatham();
		assertThat(reflectionJsonStatham.getJsonObjectConvertibleCreator(),
				is((instanceOf(OrgJsonOrderedJsonObjectConvertibleCreator.class))));
		assertThat(reflectionJsonStatham.getJsonArrayConvertibleCreator(), is(instanceOf(OrgJsonJsonArrayConvertibleCreator.class)));
		assertThat(reflectionJsonStatham.getKnownDataStructureTypeProcessorDecider(),
				is(instanceOf(KnownDataStructureTypeProcessorDecider.class)));

		final KnownTypeProcessorDecider[] knownTypeProcessorDeciders = reflectionJsonStatham.getKnownTypeProcessorDeciders();
		assertEquals(3, knownTypeProcessorDeciders.length);
		assertThat(knownTypeProcessorDeciders[0], is(instanceOf(KnownDataStructureTypeProcessorDecider.class)));
		assertThat(knownTypeProcessorDeciders[1], is(instanceOf(KnownObjectReferenceTypeProcessorDecider.class)));
		assertThat(knownTypeProcessorDeciders[2], is(instanceOf(OneProcessorForKnownTypeDecider.class)));

	}

	/**
	 * Test method for {@link com.lckymn.kevin.jsonstatham.core.reflect.ReflectionJsonStathams#newUnorderedReflectionJsonStatham()}.
	 */
	@Test
	public final void testNewUnorderedReflectionJsonStatham()
	{
		final ReflectionJsonStatham reflectionJsonStatham = ReflectionJsonStathams.newUnorderedReflectionJsonStatham();
		assertThat(reflectionJsonStatham.getJsonObjectConvertibleCreator(),
				is((instanceOf(OrgJsonUnorderedJsonObjectConvertibleCreator.class))));
		assertThat(reflectionJsonStatham.getJsonArrayConvertibleCreator(), is(instanceOf(OrgJsonJsonArrayConvertibleCreator.class)));
		assertThat(reflectionJsonStatham.getKnownDataStructureTypeProcessorDecider(),
				is(instanceOf(KnownDataStructureTypeProcessorDecider.class)));

		final KnownTypeProcessorDecider[] knownTypeProcessorDeciders = reflectionJsonStatham.getKnownTypeProcessorDeciders();
		assertEquals(3, knownTypeProcessorDeciders.length);
		assertThat(knownTypeProcessorDeciders[0], is(instanceOf(KnownDataStructureTypeProcessorDecider.class)));
		assertThat(knownTypeProcessorDeciders[1], is(instanceOf(KnownObjectReferenceTypeProcessorDecider.class)));
		assertThat(knownTypeProcessorDeciders[2], is(instanceOf(OneProcessorForKnownTypeDecider.class)));

	}

}

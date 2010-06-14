/**
 * 
 */
package com.lckymn.kevin.jsonstatham.core.reflect;

import com.lckymn.kevin.common.util.CommonConstants;
import com.lckymn.kevin.jsonstatham.core.JsonArrayConvertibleCreator;
import com.lckymn.kevin.jsonstatham.core.JsonObjectConvertibleCreator;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-06-12)
 */
public final class ReflectionJsonStathams
{
	private ReflectionJsonStathams()
	{
		throw new IllegalStateException(getClass() + CommonConstants.CANNOT_BE_INSTANTIATED);
	}

	public static ReflectionJsonStatham newReflectionJsonStatham(final JsonObjectConvertibleCreator jsonObjectConvertibleCreator,
			final JsonArrayConvertibleCreator jsonArrayConvertibleCreator,
			final KnownDataStructureTypeProcessorDecider knownDataStructureTypeProcessorDecider,
			final KnownObjectReferenceTypeProcessorDecider knownObjectReferenceTypeProcessorDecider,
			final OneProcessorForKnownTypeDecider oneProcessorForKnownTypeDecider)
	{
		return new ReflectionJsonStatham(jsonObjectConvertibleCreator, jsonArrayConvertibleCreator, knownDataStructureTypeProcessorDecider,
				knownObjectReferenceTypeProcessorDecider, oneProcessorForKnownTypeDecider);
	}

	public static ReflectionJsonStatham newReflectionJsonStatham(final JsonObjectConvertibleCreator jsonObjectConvertibleCreator,
			final JsonArrayConvertibleCreator jsonArrayConvertibleCreator)
	{
		return newReflectionJsonStatham(jsonObjectConvertibleCreator, jsonArrayConvertibleCreator,
				new KnownDataStructureTypeProcessorDecider(), new KnownObjectReferenceTypeProcessorDecider(), new OneProcessorForKnownTypeDecider());
	}

	public static ReflectionJsonStatham newReflectionJsonStatham(
			final KnownDataStructureTypeProcessorDecider knownDataStructureTypeProcessorDecider,
			final KnownObjectReferenceTypeProcessorDecider knownObjectReferenceTypeProcessorDecider,
			final OneProcessorForKnownTypeDecider oneProcessorForKnownTypeDecider)
	{
		return newReflectionJsonStatham(new OrgJsonOrderedJsonObjectConvertibleCreator(), new OrgJsonJsonArrayConvertibleCreator(),
				knownDataStructureTypeProcessorDecider, knownObjectReferenceTypeProcessorDecider, oneProcessorForKnownTypeDecider);
	}

	/**
	 * Returns ReflectionJsonStatham object created with OrgJsonOrderedJsonObjectConvertibleCreator, OrgJsonJsonArrayConvertibleCreator,
	 * KnownDataStructureTypeProcessorDecider, KnownObjectReferenceTypeProcessorDecider and OneProcessorForKnownTypeDecider.
	 * 
	 * @return ReflectionJsonStatham object created with OrgJsonOrderedJsonObjectConvertibleCreator, OrgJsonJsonArrayConvertibleCreator,
	 *         KnownDataStructureTypeProcessorDecider, KnownObjectReferenceTypeProcessorDecider and OneProcessorForKnownTypeDecider.
	 */
	public static ReflectionJsonStatham newReflectionJsonStatham()
	{
		return newReflectionJsonStatham(new OrgJsonOrderedJsonObjectConvertibleCreator(), new OrgJsonJsonArrayConvertibleCreator(),
				new KnownDataStructureTypeProcessorDecider(), new KnownObjectReferenceTypeProcessorDecider(), new OneProcessorForKnownTypeDecider());
	}

	public static ReflectionJsonStatham newUnorderedReflectionJsonStatham()
	{
		return newReflectionJsonStatham(new OrgJsonUnorderedJsonObjectConvertibleCreator(), new OrgJsonJsonArrayConvertibleCreator(),
				new KnownDataStructureTypeProcessorDecider(), new KnownObjectReferenceTypeProcessorDecider(), new OneProcessorForKnownTypeDecider());
	}
}

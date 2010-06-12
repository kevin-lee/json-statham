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
			final KnownBasicTypeDecider knownBasicTypeDecider)
	{
		return new ReflectionJsonStatham(jsonObjectConvertibleCreator, jsonArrayConvertibleCreator, knownDataStructureTypeProcessorDecider,
				knownObjectReferenceTypeProcessorDecider, knownBasicTypeDecider);
	}

	public static ReflectionJsonStatham newReflectionJsonStatham(final JsonObjectConvertibleCreator jsonObjectConvertibleCreator,
			final JsonArrayConvertibleCreator jsonArrayConvertibleCreator)
	{
		return newReflectionJsonStatham(jsonObjectConvertibleCreator, jsonArrayConvertibleCreator,
				new KnownDataStructureTypeProcessorDecider(), new KnownObjectReferenceTypeProcessorDecider(), new KnownBasicTypeDecider());
	}

	public static ReflectionJsonStatham newReflectionJsonStatham(
			final KnownDataStructureTypeProcessorDecider knownDataStructureTypeProcessorDecider,
			final KnownObjectReferenceTypeProcessorDecider knownObjectReferenceTypeProcessorDecider,
			final KnownBasicTypeDecider knownBasicTypeDecider)
	{
		return newReflectionJsonStatham(new OrgJsonOrderedJsonObjectConvertibleCreator(), new OrgJsonJsonArrayConvertibleCreator(),
				knownDataStructureTypeProcessorDecider, knownObjectReferenceTypeProcessorDecider, knownBasicTypeDecider);
	}

	/**
	 * Returns ReflectionJsonStatham object created with OrgJsonOrderedJsonObjectConvertibleCreator, OrgJsonJsonArrayConvertibleCreator,
	 * KnownDataStructureTypeProcessorDecider, KnownObjectReferenceTypeProcessorDecider and KnownBasicTypeDecider.
	 * 
	 * @return ReflectionJsonStatham object created with OrgJsonOrderedJsonObjectConvertibleCreator, OrgJsonJsonArrayConvertibleCreator,
	 *         KnownDataStructureTypeProcessorDecider, KnownObjectReferenceTypeProcessorDecider and KnownBasicTypeDecider.
	 */
	public static ReflectionJsonStatham newReflectionJsonStatham()
	{
		return newReflectionJsonStatham(new OrgJsonOrderedJsonObjectConvertibleCreator(), new OrgJsonJsonArrayConvertibleCreator(),
				new KnownDataStructureTypeProcessorDecider(), new KnownObjectReferenceTypeProcessorDecider(), new KnownBasicTypeDecider());
	}

	public static ReflectionJsonStatham newUnorderedReflectionJsonStatham()
	{
		return newReflectionJsonStatham(new OrgJsonUnorderedJsonObjectConvertibleCreator(), new OrgJsonJsonArrayConvertibleCreator(),
				new KnownDataStructureTypeProcessorDecider(), new KnownObjectReferenceTypeProcessorDecider(), new KnownBasicTypeDecider());
	}
}

/**
 * 
 */
package com.lckymn.kevin.jsonstatham.core.reflect;

import com.lckymn.kevin.jsonstatham.core.JsonStathamInAction;
import com.lckymn.kevin.jsonstatham.core.convertible.OrderedJsonObjectCreator;
import com.lckymn.kevin.jsonstatham.core.convertible.OrgJsonJsonArrayConvertibleCreator;
import com.lckymn.kevin.jsonstatham.core.convertible.UnorderedJsonObjectCreator;
import com.lckymn.kevin.jsonstatham.core.reflect.java2json.KnownDataStructureTypeProcessorDecider;
import com.lckymn.kevin.jsonstatham.core.reflect.java2json.KnownObjectReferenceTypeProcessorDecider;
import com.lckymn.kevin.jsonstatham.core.reflect.java2json.OneProcessorForKnownTypeDecider;
import com.lckymn.kevin.jsonstatham.core.reflect.java2json.ReflectionJavaToJsonConverter;
import com.lckymn.kevin.jsonstatham.core.reflect.json2java.ReflectionJsonToJavaConverter;

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
 * @version 0.0.1 (2010-06-12)
 */
public final class ReflectionJsonStathams
{
  private ReflectionJsonStathams()
  {
  }

  public static JsonStathamInAction newJsonStathamInAction(
      final ReflectionJavaToJsonConverter reflectionJavaToJsonConverter,
      final ReflectionJsonToJavaConverter reflectionJsonToJavaConverter)
  {
    return new JsonStathamInAction(reflectionJavaToJsonConverter, reflectionJsonToJavaConverter);
  }

  /**
   * Returns JsonStathamInAction object created with ReflectionJavaToJsonConverter and ReflectionJsonToJavaConverter.
   * The ReflectionJavaToJsonConverter object is created with OrgJsonOrderedJsonObjectConvertibleCreator,
   * OrgJsonJsonArrayConvertibleCreator, KnownDataStructureTypeProcessorDecider,
   * KnownObjectReferenceTypeProcessorDecider and OneProcessorForKnownTypeDecider.
   * 
   * @return JsonStathamInAction object created with ReflectionJavaToJsonConverter and ReflectionJsonToJavaConverter.
   */
  public static JsonStathamInAction newReflectionJsonStathamInAction()
  {
    // final OrgJsonOrderedJsonObjectConvertibleCreator orderedJsonObjectConvertibleCreator =
    // new OrgJsonOrderedJsonObjectConvertibleCreator();
    // final OrgJsonJsonArrayConvertibleCreator orgJsonJsonArrayConvertibleCreator =
    // new OrgJsonJsonArrayConvertibleCreator();
    final OrderedJsonObjectCreator orderedJsonObjectCreator = new OrderedJsonObjectCreator();
    final OrgJsonJsonArrayConvertibleCreator orgJsonJsonArrayConvertibleCreator =
      new OrgJsonJsonArrayConvertibleCreator();
    return newJsonStathamInAction(new ReflectionJavaToJsonConverter(orderedJsonObjectCreator,
        orgJsonJsonArrayConvertibleCreator, new KnownDataStructureTypeProcessorDecider(),
        new KnownObjectReferenceTypeProcessorDecider(), new OneProcessorForKnownTypeDecider()),
        new ReflectionJsonToJavaConverter(orderedJsonObjectCreator, orgJsonJsonArrayConvertibleCreator));
  }

  public static JsonStathamInAction newUnorderedReflectionJsonStathamInAction()
  {
    // final OrgJsonUnorderedJsonObjectConvertibleCreator orgJsonUnorderedJsonObjectConvertibleCreator =
    // new OrgJsonUnorderedJsonObjectConvertibleCreator();
    // final OrgJsonJsonArrayConvertibleCreator orgJsonJsonArrayConvertibleCreator =
    // new OrgJsonJsonArrayConvertibleCreator();
    final UnorderedJsonObjectCreator unorderedJsonObjectCreator = new UnorderedJsonObjectCreator();
    final OrgJsonJsonArrayConvertibleCreator orgJsonJsonArrayConvertibleCreator =
      new OrgJsonJsonArrayConvertibleCreator();
    return newJsonStathamInAction(new ReflectionJavaToJsonConverter(unorderedJsonObjectCreator,
        orgJsonJsonArrayConvertibleCreator, new KnownDataStructureTypeProcessorDecider(),
        new KnownObjectReferenceTypeProcessorDecider(), new OneProcessorForKnownTypeDecider()),
        new ReflectionJsonToJavaConverter(unorderedJsonObjectCreator, orgJsonJsonArrayConvertibleCreator));
  }
}

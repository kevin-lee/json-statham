/**
 * 
 */
package org.elixirian.jsonstatham.test;

import java.util.List;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-09-07)
 */
public interface ItemDefinition
{
  String getName();

  String getInstructions();

  List<Option> getOptions();
}

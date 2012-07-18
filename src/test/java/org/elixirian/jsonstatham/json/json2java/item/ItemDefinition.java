package org.elixirian.jsonstatham.json.json2java.item;
import java.util.List;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-09-07)
 */
public interface ItemDefinition
{
	ItemVersion getItemVersion();

	String getName();

	String getInstructions();

	List<Option> getOptions();

	int sizeOfOptions();

	boolean isMultipleChoice();

	boolean isNotMultipleChoice();
}

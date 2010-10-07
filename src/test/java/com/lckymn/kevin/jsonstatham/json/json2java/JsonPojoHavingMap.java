package com.lckymn.kevin.jsonstatham.json.json2java;

import java.util.Map;

import com.lckymn.kevin.common.util.Objects;
import com.lckymn.kevin.jsonstatham.annotation.JsonField;
import com.lckymn.kevin.jsonstatham.annotation.JsonObject;

@JsonObject
public class JsonPojoHavingMap
{
	@JsonField
	private final String name;

	@JsonField
	private final Map<String, Long> stringToLongMap;

	public JsonPojoHavingMap(String name, Map<String, Long> stringToLongMap)
	{
		this.name = name;
		this.stringToLongMap = stringToLongMap;
	}

	public String getName()
	{
		return name;
	}

	public Map<String, Long> getStringToLongMap()
	{
		return stringToLongMap;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(name, stringToLongMap);
	}

	@Override
	public boolean equals(Object jsonPojoHavingMap)
	{
		if (this == jsonPojoHavingMap)
		{
			return true;
		}
		if (!(jsonPojoHavingMap instanceof JsonPojoHavingMap))
		{
			return false;
		}
		final JsonPojoHavingMap that = (JsonPojoHavingMap) jsonPojoHavingMap;
		return Objects.equals(this.name, that.name) && Objects.equals(this.stringToLongMap, that.stringToLongMap);
	}

	@Override
	public String toString()
	{
		return Objects.toStringBuilder(this)
				.add("name", name)
				.add("stringToLongMap", stringToLongMap)
				.toString();
	}
}

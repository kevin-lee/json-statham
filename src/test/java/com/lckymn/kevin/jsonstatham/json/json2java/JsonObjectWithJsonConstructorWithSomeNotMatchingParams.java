/**
 * 
 */
package com.lckymn.kevin.jsonstatham.json.json2java;

import java.util.Map;

import com.lckymn.kevin.common.util.Objects;
import com.lckymn.kevin.jsonstatham.annotation.JsonConstructor;
import com.lckymn.kevin.jsonstatham.annotation.JsonField;
import com.lckymn.kevin.jsonstatham.annotation.JsonObject;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.01 (2009-11-28)
 */
@JsonObject
public class JsonObjectWithJsonConstructorWithSomeNotMatchingParams
{
	private final String name;

	@JsonField(name = "uri")
	private final String uri;

	@JsonField(name = "params")
	private final Map<String, String> parameterMap;

	@JsonConstructor
	public JsonObjectWithJsonConstructorWithSomeNotMatchingParams(String name, String uri,
			Map<String, String> parameterMap)
	{
		this.name = name;
		this.uri = uri;
		this.parameterMap = parameterMap;
	}

	public String getName()
	{
		return name;
	}

	public String getUri()
	{
		return uri;
	}

	public Map<String, String> getParameterMap()
	{
		return parameterMap;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(name, uri, parameterMap);
	}

	@Override
	public boolean equals(Object address)
	{
		if (this == address)
		{
			return true;
		}
		if (!(address instanceof JsonObjectWithJsonConstructorWithSomeNotMatchingParams))
		{
			return false;
		}
		final JsonObjectWithJsonConstructorWithSomeNotMatchingParams that =
			(JsonObjectWithJsonConstructorWithSomeNotMatchingParams) address;
		return Objects.equals(this.name, that.getName()) && Objects.equals(this.uri, that.getUri())
				&& Objects.equals(this.parameterMap, that.getParameterMap());
	}

	@Override
	public String toString()
	{
		return Objects.toStringBuilder(this)
				.add("name", name)
				.add("uri", uri)
				.add("parameterMap", parameterMap)
				.toString();
	}
}

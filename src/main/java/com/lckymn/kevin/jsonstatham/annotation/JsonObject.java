package com.lckymn.kevin.jsonstatham.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * It indicates that an instance of any classes, annotated with this annotation, is eligible to be used as the target object of the
 * {@link com.lckymn.kevin.jsonstatham.core.JsonStatham JsonStatham}'s methods which convert Java object to JSON.
 * 
 * @author Lee, SeongHyun (Kevin)
 * @version 0.01 (2009-11-21)
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonObject
{
}

/**
 * 
 */
package com.lckymn.kevin.jsonstatham.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.01 (2009-11-21)
 */
@Target( { ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonField
{
	String name() default "";
}

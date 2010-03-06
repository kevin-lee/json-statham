/**
 * 
 */
package com.lckymn.kevin.jsonstatham.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.01 (2009-12-20)
 */
@Documented
@Target( { ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValueAccessor
{
	String name() default "";
}

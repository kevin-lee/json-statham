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
 * @version 0.0.1 (2010-09-18)
 */
@Documented
@Target({ ElementType.CONSTRUCTOR })
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonConstructor
{
}

package vn.topica.itlab4.jdbc.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * This class represents an annotation named Table.
 *
 * @author AnhLT14 (anhlt14@topica.edu.vn)
 */
@Target(TYPE) 
@Retention(RUNTIME)
public @interface Table {
	String name() default "";
}

package vn.topica.itlab4.jdbc.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * This class represents an annotation named Column.
 *
 * @author AnhLT14 (anhlt14@topica.edu.vn)
 */
@Target({ METHOD, FIELD })
@Retention(RUNTIME)
public @interface Column {
	String name() default "";
}

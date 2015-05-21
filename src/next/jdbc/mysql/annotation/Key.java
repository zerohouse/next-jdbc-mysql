package next.jdbc.mysql.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Primary Key필드입니다. <br>
 * AUTO_INCREMENT 지정가능합니다. 
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Key {
	boolean AUTO_INCREMENT() default false;
}

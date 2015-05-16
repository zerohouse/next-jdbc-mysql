package next.jdbc.mysql.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 해당하는 칼럼의 설정을 지정합니다.
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {

	String value() default "";

	boolean NULL() default false;

	boolean hasDefaultValue() default true;

	String DEFAULT() default "";

	String DATA_TYPE() default "";
	
	String[] function() default "";

}

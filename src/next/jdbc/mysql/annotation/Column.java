package next.jdbc.mysql.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 해당하는 칼럼의 설정을 지정합니다.
 * 
 * value : 칼럼이름<br>
 * DATA_TYPE : SQL data Type<br>
 * function : mysql function( ex: index, unique... )
 * NULL : NULL값 허용 여부<br>
 * DEFAULT : NULL값 허용 여부<br>
 * NULL : NULL값 허용 여부<br>
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

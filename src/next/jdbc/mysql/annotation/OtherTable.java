package next.jdbc.mysql.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 조인 SQL실행시 다른 칼럼의 필드명을 지정합니다.<br>
 * 인서트, 업데이트등의 작업은 무시합니다. 
 * 
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface OtherTable {

	String COLUMN_NAME() default "";
}

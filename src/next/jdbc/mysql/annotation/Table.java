package next.jdbc.mysql.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 테이블 설정을 지정합니다.<br>
 * value : table명<br>
 * 
 * columnPrefix : 각 칼럼명 앞에 해당 문자열을 붙입니다.<br>
 * columnSuffix : 각 칼럼명 뒤에 해당 문자열을 붙입니다.<br>
 *                $table으로 테이블명을 사용할 수 있습니다. (ex: columnPrefix = "$table_")<br>
 * 
 * createQuery : 테이블 Create Query를 하드코딩합니다.<br>
 * 
 * neverDrop : true로 설정하면, TableMaker에서 drop, reset작업시 테이블을 드롭하지 않습니다.
 * 
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Table {

	boolean neverDrop() default false;

	String value() default "";

	String tableSuffix() default "";

	String createQuery() default "";

	String columnPrefix() default "";

	String columnSuffix() default "";

}

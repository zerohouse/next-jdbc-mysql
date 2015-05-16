package next.jdbc.mysql.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * 해당 필드의 value가 Regex와 맞지 않으면, Insert, update 작업을 수행하지 않고<br>
 * RegexNotMatchedException을 Throw합니다.
 * 
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface RequiredRegex {
	String value();
}

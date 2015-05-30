package next.jdbc.mysql.exception;

/**
 * 필드의 Regex와 맞지 않는 데이터를 넣으려 했을때 발생합니다.
 *
 */
public class RegexNotMatchedException extends RuntimeException {

	public RegexNotMatchedException(String message) {
		super(message);
	}

	private static final long serialVersionUID = 5327993556995210476L;

}

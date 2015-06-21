package next.jdbc.mysql.query.support.exception;

public class FieldNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 8417636222757366271L;

	private static final String m = "%s field는 없습니다. \n [fieldName], [ClassName].[FieldName]으로 작성해야 합니다.";

	public FieldNotFoundException(String message) {
		super(String.format(m, message));
	}

}

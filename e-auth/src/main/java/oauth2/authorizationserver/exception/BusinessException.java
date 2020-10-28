package oauth2.authorizationserver.exception;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BusinessException(String message) {
		super(message);
	}
	
	public BusinessException(Exception e) {
		super(e);
	}
	
	public BusinessException(String message, Exception e) {
		super(message + ": " + e.getMessage(), e);
	}
	
}

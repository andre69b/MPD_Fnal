package Exception;

public class MyRuntimeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MyRuntimeException() {
		super();
	}

	public MyRuntimeException(String s) {
		super(s);
	}

	public MyRuntimeException(String s, Throwable throwable) {
		super(s, throwable);
	}

	public MyRuntimeException(Throwable throwable) {
		super(throwable);
	}
}

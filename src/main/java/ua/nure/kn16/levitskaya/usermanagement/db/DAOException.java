package ua.nure.kn16.levitskaya.usermanagement.db;

public class DAOException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2587172538669325935L;

	public DAOException() {
	}

	public DAOException(String arg) {
		super(arg);

	}

	public DAOException(Throwable arg) {
		super(arg);
	}

	public DAOException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public DAOException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}
}

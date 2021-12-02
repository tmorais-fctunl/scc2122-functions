package scc.exceptions;

public class InexistentEntityException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InexistentEntityException() {
		super();
	}

	public InexistentEntityException(String message) {
		super(message);
	}
}

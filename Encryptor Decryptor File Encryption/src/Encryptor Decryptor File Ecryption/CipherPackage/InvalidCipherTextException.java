/**
 * 
 */
package CipherPackage;

/**
 * @author Amol Soneji
 *
 */
public class InvalidCipherTextException extends Exception 
{

	/**
	 * 
	 */
	public InvalidCipherTextException() 
	{
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public InvalidCipherTextException(String message) 
	{
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public InvalidCipherTextException(Throwable cause) 
	{
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public InvalidCipherTextException(String message, Throwable cause) 
	{
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public InvalidCipherTextException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) 
	{
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}

/**
*This class is a subclass of class Exception.  This exception is invoked when a manual conversion rate has been entered
*in as a value that is equal to or less than 0.  Currently in this program, only convertCurrency can invoke
*this type of exception.  
*<br>
*
*@author  Amol Soneji
*@version 1.0, 19 May 2021
*/

public class InvalidConversionRateException extends Exception
{
  public InvalidConversionRateException()
  {
    //Empty constructor.  
  }
  
  public InvalidConversionRateException(String message)
  {
    super(message);
  }
  
  public InvalidConversionRateException(Throwable cause)
  {
    super(cause);
  }
  
  public InvalidConversionRateException(String message, Throwable cause)
  {
    super(message, cause);
  }
  
  public InvalidConversionRateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
  {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}

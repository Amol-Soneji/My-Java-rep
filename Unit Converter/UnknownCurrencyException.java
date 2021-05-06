/**
*This class is a subclass of class Exception.  This exception is invoked when an unknown currency has been entered
*in as either the source currency or the end currency.  Currently in this program, only conversionData could invoke
*this type of exception.  
*<br>
*
*@author  Amol Soneji
*@version 1.0, 04 May 2021
*/

public class UnknownCurrencyException extends Exception
{
  public UnknownCurrencyException()
  {
    //Empty constructor.  
  }
  
  public UnknownCurrencyException(String message)
  {
    super(message);
  }
  
  public UnknownCurrencyException(Throwable cause)
  {
    super(cause);
  }
  
  public UnkownCurrencyException(String message, Throwable cause)
  {
    super(message, cause);
  }
  
  public UnknownCurrencyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
  {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}

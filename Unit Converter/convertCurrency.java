/**
*This class converts currency of one contry, to the currency of another country.  This class utilizes an the getCurrencyInfo class, to get the latest market exchange rate.  
*<br>
*
*@author  Amol Soneji
*@version 1.0, 01 Jan 2021  ,  Happry New Year!!!
*/



public class convertCurrecny
{
  private static byte inputCurrency;
  private static byte outputCurrency;
  private static double input;
  private static double rate;
  
  /**
  *This method takes in the parameters and calls methods required to carry out the calculation of conversion.  It then returns the double data type value represented by output.  
  */
  
  public static double convertCurrency(byte inpCurrency, byte outCurrency, double inp)
  {
    inputCurrency = inpCurrency;
    outputCurrency = outCurrency;
    input = inp;
    if((inputCurrency == outputCurrency) || (input == 0))
      return input;
    getRate();
    return calculateConversion();
  }
  
  /**
  *This method calls a method from the conversionData class to get the market conversion rate.  
  */
  
  private static void getRate()
  {
    rate = conversionData.getRate(inputCurrency, outputCurrency);
  }
  
  /**
  *This method is responsible for calculating the conversion of one currency to another.  
  */
  private static double calculateConversion()
  {
    return (input / rate);
  }

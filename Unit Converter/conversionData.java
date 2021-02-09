/**
*This class gets currency exchange rate data between two countries.  This class is called by the convertCurrncy class.  
*<br>
*
*@author  Amol Soneji
*@version 1.0, 06 Jan 2021
*/

private static double manualRate;

/**
*The following static class is only called if a manual static conversion rate is to be set.  
*/
public static void conversionData(double manRate)
{
  manualRate = manRate;
}

/**
*The following class gets the currency echange rate.  
*/
public static double getRate(byte inputCurrency, byte outputCurrency)
{
  if(inputCurrency == -1)
    return manualRate;
  else
  {
    
  }
}

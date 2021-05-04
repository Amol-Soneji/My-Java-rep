/**
*This class gets currency exchange rate data between two countries.  This class is called by the convertCurrncy class.  
*<br>
*
*@author  Amol Soneji
*@version 1.0, 06 Jan 2021
*/

import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class conversionData
{
  //Private fields in the class.  
  private static double rate;
  private static String srcCurrencyName;
  private static String endCurrencyName;
  private static double manualRate;

  //Public fields in the class.  
  public static boolean manual;

  /**
  *The following constructor is only called if a manual static conversion rate is to be set.  
  */
  public conversionData(double manualRate)
  {
    conversionData.manualRate = manRate;
    manual = true;
  }

  /**
  *The following constructor is used when trying to get the actual conversion rate value.  
  */
  public conversionData(String srcCurrencyName, String endCurrencyName)
  {
    conversionData.srcCurrencyName = srcCurrencyName;
    conversionData.endCurrencyName = endCurrencyName;
    manual = false;
  }

  /**
  *The following method returns the currency echange rate.  
  */
  public static double returnRate()
  {
    if(manual)
      return manualRate;
    else
    {
      getData();
      return rate;
    }
  }
  
  /**
  *The following class gets the currency rate data.  
  */
  private static void getData()
  {
    
  }
    
}

/**
*This class converts measurements of volume, from one volume measurement to another volume measurement.  This class can also convert from US customary untis to SI units, and vice versa as well.  
*This class also implements the measurementTypeConversion interface.  
*<br>
*
*@author  Amol Soneji
*@version 1.0, 6 Oct 2020
*/



public class convertVolumeMeasurement implements measurementTypeConversion
{
  private static byte[] inputType;
  private static byte[] outputType;
  private static double input;
  private static double output;
  
  /**
  *This method takes in the parameters and automatically calls the methods for calculating the conversion.  It then returns the double data type value represented by output.  
  */
  public static double measurementConvert(byte[] inpType, byte[] outType, double inp)
  {
    inputType = inpType;
    outputType = outType;
    input = inp;
    if(inputType[0] == 0)//Check if US units
    {
      if(outputType[0] == 0)//Check if output is also requested in US units.
      {
        if(inputType[1] == outputType[1])//Same US unit measurements.  The result will always be the same (ex. input in cubic feet and output in cubic feet as well).  
        {
          output = input;
          return output;
        }
        else
        {
          usOnlyConvert();
          return output;
        }
      }
      else//Output is being requested in SI units.  
      {
        usToSiConvert();
        return output;
      }
    }
    else//Input is in SI units.  
    {
      if(outputType[0] == 0)//Check if output is being requested in US units.  
      {
        siToUsConvert();
        return output;
      }
      else//Ouput is being requested in SI units as well.  
      {
        if(inputType[1] == outputType[1])//Same SI unit measurements.  The result will always be the same (ex. input in cubic cm and output in cubic cm as well).  
        {
          output = input;
          return output;
        }
        else
        {
          siOnlyConvert();
          return output;
        }
      }
    }
  }
  
  /**
  *The following method is for converting between one US volume measurement to another US volume measurement.  This method does not return a value as it ustilizes the output field variable.  
  */
  private static void usOnlyConvert()
  {
    
  }
  
  /**
  *The following method is for converting from a US volume measurement to a SI unit volume measurement.  This method does not return a value as it utilizes the output field variable.  
  */
  private static void usToSiConvert()
  {
    
  }
  
  /**
  *The following method is for conerting between one SI unit volume measurement to another SI unit volume measurement.  
  *This method does not return a value as it utilizes the output field variable.  
  */
  private static void siOnlyConvert()
  {
    
  }
  
  /**
  *The following method is for converting from a SI unit volume measurement to a US volume measurement.  This method does not return a value as it utilizes the output field variable.  
  */
  private static void siToUsConvert()
  {
    
  }
}

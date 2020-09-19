/**
*This class converts measurements of length, from one length measurement to another length measurement.  This class can also convert from US customary untis to SI units, and vice versa as well.  
*This class also implements the measurementTypeConversion interface.  
*<br>
*
*@author  Amol Soneji
*@version 1.0, 19 Sept 2020
*/



public class convertLengthMeasurement implements measurementTypeConversion
{
  private static byte[] inputType;
  private static byte[] outputType;
  private static double input;
  private static double ouput;
  
  /**
  *This method takes in the parameters and automatically calls the methods for calculating the conversion.  It then returns the double data type value represented by output.  
  */
  public static double measurementConvert(byte[] inpType, byte[] outType, double inp)
  {
    inputType = inpType;
    outputType = outType;
    input = inp;
  }
}

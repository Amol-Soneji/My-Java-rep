/**
*This class is is responsible for converting the measurement from one unit to another.  
*<br>
*
*@author  Amol Soneji
*@version 1.0, 18 Sept 2020
*/


public class converter
{
  private static byte measurementType;
  private static byte[] inputType;
  private static byte[] outputType;
  private static double input;
  private static double output;
  
  /**
  *The following method is used to set fields, as makeing this convert class into an object does not make sense.  
  */
  public static void setFields(byte mesType, byte[] inpType, byte[] outType, double inp, double out)
  {
    mesurementType = mesType;
    inputType = inpType;
    outputType = outType;
    input = inp;
    output = out;
  }
}

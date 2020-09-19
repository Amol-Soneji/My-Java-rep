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
  
  /**
  *The following method is used to do the conversion.  It will use non-child subclasses to do the conversion.  However, those classes will be implemnting the 
  *measurementTypeConversion interface.  
  */
  public static void convert()
  {
    if(measurementType == 1)
      output = convertLengthMeasurement.measurementConvert(inputType, outputType, input);
    else if(measurementType == 2)
      output = convertAreaMeasurement.measurementConvert(inputType, outputType, input);
    else if(measurementType == 3)
      output = convertVolumeMeasurement.measurementConvert(inputType, outputType, input);
    else if(measurementType == 4)
      output = convertLiquidMeasurement.measurementConvert(inputType, outputType, input);
    else
      output = convertWeightMeasurement.measurementConvert(inputType, outputType, input);
  }
  
  /**
  *The following method returns the result of the conversion.  
  */
  public static double getResult()
  {
    return output;
  }
}

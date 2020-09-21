/**
*This class is is responsible for converting the measurement from one unit to another.  
*<br>
*
*@author  Amol Soneji
*@version 1.0, 18 Sept 2020
*/


public class converter
{
  private static byte measurementType;//The type of measurement we are dealing with (ex. Liquid, Volume, Length).  
  private static byte[] inputType;//An array holding the input type, US or metric, inch, cm, mm, km, or mile.  
  private static byte[] outputType;//An array holding the desired output type to convert to.  
  private static double input;//The input measurement value.  
  private static double output;//The output measurement value from the result of conversion calculation.  
  
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

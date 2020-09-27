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
        if(inputType[1] == outputType[1])//Same US unit measurements.  The result will always be the same (ex. input in inches and output in inches as well).  
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
        if(inputType[1] == outputType[1])//Same SI unit measurements.  The result will always be the same (ex. input in cm and output in cm as well).  
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
  *The following method is for converting between one US length measurement to another US length measurement.  This method does not return a value as it ustilizes the output field variable.  
  */
  private static void usOnlyConvert()
  {
    if(inputType[1] == 1)//Inches
    {
      if(outputType[1] == 2)
        output = input / 12;//12 inches in a feet.  
      else if(outputType[1] == 3)
        output = input / 36;//36 inches in a yard.  
      else
        output = input / 63360;//63,360 inches in a mile.  
    }
    else if(inputType[1] == 2)//Feet
    {
      if(outputType[1] == 1)
        output = input * 12;//A feet has 12 inches.  
      else if(outputType[3] == 2)
        output = input / 3;//3 feet in a yard.  
      else
        output = input / 5280;//5,280 feet in a mile.  
    }
    else if(inputType[1] == 3)//Yards
    {
      if(outputType[1] == 1)
        output = input * 36;//A yard has 36 inches.  
      else if(outputType[1] == 2)
        output = input * 3;//A yard has 3 feet.  
      else
        output = input / 1760;//1,760 yards in a mile.  
    }
    else//Miles
    {
      if(outputType[1] == 1)
        output = input * 63360;//A mile has 63,360 inches.  
      else if(outputType[1] == 2)
        output = input * 5280;//A mile has 5,280 feet.  
      else
        output = input * 1760;//A mile has 1,760 yards.  
    }
  }
  
  /**
  *The following method is for converting from a US length measurement to a SI unit length measurement.  This method does not return a value as it utilizes the output field variable.  
  */
  private static void usToSiConvert()
  {
    if(inputType[1] == 1)//Inches
    {
      if(outputType[1] == 1)
        output = input * 25.4;//1 inch = 25.4 millimeters
      else if(outputType[1] == 2)
        output = input * 2.54;//inch to centimeter
      else if(outputType[1] == 3)
        output = input * 0.254;//inch to decimeter
      else if(outputType[1] == 4)
        output = input * 0.0254;//inch to meter
      else if(outputType[1] == 5)
        output = input * 0.00254;//inch to decameter
      else if(outputType[1] == 6)
        output = input * 0.000254;//inch to hectometer
      else
        output = input * 0.0000254;//inch to kilometer
    }
    else if(inputType[1] == 2)//Feet
    {
      if(outputType[1] == 1)
        output = input * 304.8;//1 feet = 304.8 millimeters
      else if(outputType[1] == 2)
        output = input * 30.48;//feet to centimeter
      else if(outputType[1] == 3)
        output = input * 3.048;//feet to decimeter
      else if(outputType[1] == 4)
        output = input * 0.3048;//feet to meter
      else if(outputType[1] == 5)
        output = input * 0.03048;//feet to decameter
      else if(outputType[1] == 6)
        output = input * 0.003048;//feet to hectometer
      else
        output = input * 0.0003048;//feet to meter
    }
    else if(inputType[1] == 3)//Yards
    {
      if(outputType[1] == 1)
        output = input * 914.4;//1 yard = 914.4 millimeters
      else if(outputType[1] == 2)
        output = input * 91.44;//yard to centimeter
      else if(outputType[1] == 3)
        output = input * 9.144;//yard to decimeter
      else if(outputType[1] == 4)
        output = input * 0.9144;//yard to meter
      else if(outputType[1] == 5)
        output = input * 0.09144;//yard to decameter
      else if(outputType[1] == 6)
        output = input * 0.009144;//yard to hectometer
      else
        output = input * 0.0009144;//yard to kilometer
    }
    else//Miles
    {
      if(outputType[1] == 1)
        output = input * 1609344;//1 mile = 1,609,344 millimeters
      else if(outputType[1] == 2)
        output = input * 160934.4;//mile to centimeter
      else if(outputType[1] == 3)
        output = input * 16093.44;//mile to decimeter
      else if(outputType[1] == 4)
        output = input * 1609.344;//mile to meter
      else if(outputType[1] == 5)
        output = input * 160.9344;//mile to decameter
      else if(outputType[1] == 6)
        output = input * 16.09344;//mile to hectometer
      else
        output = input * 1.609344;//mile to kilometer
    }
  }
  
  /**
  *The following method is for conerting between one SI unit length measurement to another SI unit length measurement.  
  *This method does not return a value as it utilizes the output field variable.  
  */
  private static void siOnlyConvert()
  {
    if(inputType[1] == 1)//Millimeter
    {
      if(outputType[1] == 2)
        output = input / 10;//10 millimeters in a centimeter.  
      else if(outputType[1] == 3)
        output = input / 100;//100 millimeters in a decimeter.  
      else if(outputType[1] == 4)
        output = input / 1000;//1,000 millimeters in a meter.  
      else if(outputType[1] == 5)
        output = input / 10000;//10,000 millimeters in a decameter.  
      else if(outputType[1] == 6)
        output = input / 100000;//100,000 millimeters in a hectometer.  
      else
        output = input / 1000000;//1,000,000 millimeters in a kilometer.  
    }
    else if(inputType[1] == 2)//Centimeter
    {
      if(outputType[1] == 1)
        output = input * 10;//A centimeter has 10 millimeters.  
      else if(outputType[1] == 3)
        output = input / 10;//10 centimeters in a decimeter.  
      else if(outputType[1] == 4)
        output = input / 100;//100 centimeters in a meter.  
      else if(outputType[1] == 5)
        output = input / 1000;//1,000 centimeters in a decameter.  
      else if(outputType[1] == 6)
        output = input / 10000;//10,000 centimeters in a hectometer.  
      else
        output = input / 100000;//100,000 centimeters in a kilometer.  
    }
    else if(inputType[1] == 3)//Decimeter
    {
      if(outputType[1] == 1)
        output = input * 100;//A decimeter has 100 millimeters.  
      else if(outputType[1] == 2)
        output = input * 10;//A decimeter has 10 centimeters.  
      else if(outputType[1] == 4)
        output = input / 10;//10 decimeters in a meter.  
      else if(outputType[1] == 5)
        output = input / 100;//100 decimeters in a decameter.  
      else if(outputType[1] == 6)
        output = input / 1000;//1,000 decimeters in a hectometer.  
      else
        output = input / 10000;//10,000 decimeters in a kilometer.  
    }
    else if(inputType[1] == 4)//Meter
    {
      if(outputType[1] == 1)
        output = input * 1000;//A meter has 1,000 millimeters.  
      else if(outputType[1] == 2)
        output = input * 100;//A meter has 100 centimeters.  
      else if(outputType[1] == 3)
        output = input * 10;//A meter has 10 decimeters.  
      else if(outputType[1] == 5)
        output = input / 10;//10 meters in a decameter.  
      else if(outputType[1] == 6)
        output = input / 100;//100 meters in a hectometer.  
      else
        output = input / 1000;//1,000 meters in a kilometer.  
    }
    else if(inputType[1] == 5)//Decameter
    {
      if(outputType[1] == 1)
        output = input * 10000;//A decameter has 10,000 millimeters.  
      else if(outputType[1] == 2)
        output = input * 1000;//A decameter has 1,000 centimeters.  
      else if(outputType[1] == 3)
        output = input * 100;//A decameter has 100 decimeters.  
      else if(outputType[1] == 4)
        output = input * 10;//A decameter has 10 meters.  
      else if(outputType[1] == 6)
        output = input / 10;//10 decameters in a hectometer.  
      else
        output = input / 100;//100 decameters in a kilometer.  
    }
    else if(inputType[1] == 6)//Hectometer
    {
      if(outputType[1] == 1)
        output = input * 100000;//A hectometer has 100,000 millimeters.  
      else if(outputType[1] == 2)
        output = input * 10000;//A hectometer has 10,000 centimeters.  
      else if(outputType[1] == 3)
        output = input * 1000;//A hectometer has 1,000 decimeters.  
      else if(outputType[1] == 4)
        output = input * 100;//A hectometer has 100 meters.  
      else if(outputType[1] == 5)
        output = input * 10;//A hectometer has 10 decameters.  
      else
        output = input / 10;//10 hectometers in a kilometer.  
    }
    else//Kilometer
    {
      if(outputType[1] == 1)
        output = input * 1000000;//A kilometer has 1,000,000 millimeters.  
      else if(outputType[1] == 2)
        output = input * 100000;//A kilometer has 100,000 centimeters.  
      else if(outputType[1] == 3)
        output = input * 10000;//A kilometer has 10,000 decimeters.  
      else if(outputType[1] == 4)
        output = input * 1000;//A kilometer has 1,000 meters.  
      else if(outputType[1] == 5)
        output = input * 100;//A kilometer has 100 decameters.  
      else if(outputType[1] == 6)
        output = input * 10;//A kilometer has 10 hectometers.  
    }
  }
  
  /**
  *The following method is for converting from a SI unit length measurement to a US length measurement.  This method does not return a value as it utilizes the output field variable.  
  */
  private static void siToUsConvert()
  {
    if(inputType[1] == 1)//Milimeter
    {
      if(outputType[1] == 1)
        output = input / 25.4;//millimeter to inches
      else if(outputType[1] == 2)
        output = input / 304.8;//millimeter to feet
      else if(outputType[1] == 3)
        output = input / 914.4;//millimeter to yards
      else
        output = input / 1609344;//millimeter to miles
    }
    else if(inputType[1] == 2)//Centimeter
    {
      if(outputType[1] == 1)
        output = input / 2.54;//centimeter to inches
      else if(outputType[1] == 2)
        output = input / 30.48;//centimeter to feet
      else if(outputType[1] == 3)
        output = input / 91.44;//centimeter to yards
      else
        output = input / 160934.4;//centimeter to miles
    }
    else if(inputType[1] == 3)//Decimeter
    {
      if(outputType[1] == 1)
        output = input / 0.254;//decimeter to inches
      else if(outputType[1] == 2)
        output = input / 3.048;//decimeter to feet
      else if(outputType[1] == 3)
        output = input / 9.144;//decimeter to yards
      else
        output = input / 16093.44;//decimeter to miles
    }
    else if(inputType[1] == 4)//Meter
    {
      if(outputType[1] == 1)
        output = input / 0.0254;//meter to inches
      else if(outputType[1] == 2)
        output = input / 0.3048;//meter to feet
      else if(outputType[1] == 3)
        output = input / 0.9144;//meter to yards
      else
        output = input / 1609.344;//meter to miles
    }
    else if(inputType[1] == 5)//Decameter
    {
      if(outputType[1] == 1)
        output = input / 0.00254;//decameter to inches
      else if(outputType[1] == 2)
        output = input / 0.03048;//decameter to feet
      else if(outputType[1] == 3)
        output = input / 0.09144;//decameter to yards
      else
        output = input / 160.9344;//decameter to miles
    }
    else if(inputType[1] == 6)//Hectometer
    {
      if(outputType[1] == 1)
        output = input / 0.000254;//hectometer to inches
      else if(outputType[1] == 2)
        output = input / 0.003048;//hectometer to feet
      else if(outputType[1] == 3)
        output = input / 0.009144;//hectometer to yards
      else
        output = input / 16.09344;//hectometer to miles
    }
    else//Kilometer
    {
      if(outputType[1] == 1)
        output = input / 0.0000254;//kilometer to inches
      else if(outputType[1] == 2)
        output = input / 0.0003048;//kilometer to feet
      else if(outputType[1] == 3)
        output = input / 0.0009144;//kilometer to yards
      else
        output = input / 1.609344;//kilometer to miles
    }
  }
}

/**
*This class converts measurements of temprature, from one temprature measurement to another temprature measurement.  This class can also convert from US customary untis to SI units, and vice versa as well.  
*This class also implements the measurementTypeConversion interface.  
*<br>
*
*@author  Amol Soneji
*@version 1.0, 6 Oct 2020
*/



public class convertTempratureMeasurement implements measurementTypeConversion
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
        if(inputType[1] == outputType[1])//Same US unit measurements.  The result will always be the same (ex. input in fahrenheit and output in fahrenheit as well).  
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
        if(inputType[1] == outputType[1])//Same SI unit measurements.  The result will always be the same (ex. input in celcius and output in celcius as well).  
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
  *The following method is for converting between one US temprature measurement to another US temprature measurement.  This method does not return a value as it ustilizes the output field variable.  
  */
  private static void usOnlyConvert()
  {
    if(inputType[1] == 1)//We are converting to Rankine
      output = input + 459.67;
    else//We are converting to Fahrenheit
      output = input - 459.67;
  }
  
  /**
  *The following method is for converting from a US temprature measurement to a SI unit temprature measurement.  This method does not return a value as it utilizes the output field variable.  
  */
  private static void usToSiConvert()
  {
    if(inputType[1] == 1)//Fahrenheit
    {
      if(outputType[1] == 1)//To Celcius
        output = (input - 32) * (5 / 9);
      else//To Kelvin
        output = (input + 459.67) * (5 / 9);
    }
    else//Rankine
    {
      if(outputType[1] == 1)//To Celcius
        output = (input - 491.67) * (5 / 9);
      else//To Kelvin
        output = input * (5 / 9);
    }
  }
  
  /**
  *The following method is for conerting between one SI unit temprature measurement to another SI unit temprature measurement.  
  *This method does not return a value as it utilizes the output field variable.  
  */
  private static void siOnlyConvert()
  {
    if(inputType[1] == 1)//We are converting to Kelvin
      output = input + 273.15;
    else//We are converting to Celcius
      output = input - 273.15;
  }
  
  /**
  *The following method is for converting from a SI unit temprature measurement to a US temprature measurement.  This method does not return a value as it utilizes the output field variable.  
  */
  private static void siToUsConvert()
  {
    if(inputType[1] == 1)//Celcius
    {
      if(outputType[1] == 1)//To Fahrenheit
        output = (input * (9 / 5)) + 32;
      else//To Rankine
        output = (input + 273.15) * (9 / 5);
    }
    else//Kelvine
    {
      if(outputType[1] == 1)//To Fahrenheit
        output = (input * (9 / 5)) - 459.67;
      else//To Rankine
        output = input * (9 / 5);
    }
  }
}

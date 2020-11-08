/**
*This class converts measurements of weight, from one weight measurement to another weight measurement.  This class can also convert from US customary untis to SI units, and vice versa as well.  
*This class also implements the measurementTypeConversion interface.  
*<br>
*
*@author  Amol Soneji
*@version 1.0, 6 Oct 2020
*/



public class convertWeightMeasurement implements measurementTypeConversion
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
        if(inputType[1] == outputType[1])//Same US unit measurements.  The result will always be the same (ex. input pounds and output in pounds as well).  
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
        if(inputType[1] == outputType[1])//Same SI unit measurements.  The result will always be the same (ex. input in grams and output in grams as well).  
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
  *The following method is for converting between one US weight measurement to another US weight measurement.  This method does not return a value as it ustilizes the output field variable.  
  */
  private static void usOnlyConvert()
  {
    if(inputType[1] == 1)//Ounces
    {
      if(outputType[1] == 2)//To Pounds
        
      else//To Tons
        
    }
    else if(inputType[1] == 2)//Pounds
    {
      if(outputType[1] == 1)//To Ounces
        
      else//To Tons
        
    }
    else//Tons
    {
      if(outputType[1] == 1)//To Ounces
        
      else//To Pounds
        
    }
  }
  
  /**
  *The following method is for converting from a US weight measurement to a SI unit weight measurement.  This method does not return a value as it utilizes the output field variable.  
  */
  private static void usToSiConvert()
  {
    if(inputType[1] == 1)//Ounces
    {
      if(outputType[1] == 1)//To Picograms
        
      else if(outputType[1] == 2)//To Nanograms
        
      else if(outputType[1] == 3)//To Micrograms
        
      else if(outputType[1] == 4)//To Milligrams
        
      else if(outputType[1] == 5)//To Grams
        
      else if(outputType[1] == 6)//To Kilograms
        
      else//To Tonnes
        
    }
    else if(inputType[1] == 2)//Pounds
    {
      if(outputType[1] == 1)//To Picograms
        
      else if(outputType[1] == 2)//To Nanograms
        
      else if(outputType[1] == 3)//To Micrograms
        
      else if(outputType[1] == 4)//To Milligrams
        
      else if(outputType[1] == 5)//To Grams
        
      else if(outputType[1] == 6)//To Kilograms
        
      else//To Tonnes
        
    }
    else//Tons
    {
      if(outputType[1] == 1)//To Picograms
        
      else if(outputType[1] == 2)//To Nanograms
        
      else if(outputType[1] == 3)//To Micrograms
        
      else if(outputType[1] == 4)//To Milligrams
        
      else if(outputType[1] == 5)//To Grams
        
      else if(outputType[1] == 6)//To Kilograms
        
      else//To Tonnes
        
    }
  }
  
  /**
  *The following method is for conerting between one SI unit weight measurement to another SI unit weight measurement.  
  *This method does not return a value as it utilizes the output field variable.  
  */
  private static void siOnlyConvert()
  {
    if(inputType[1] == 1)//Picograms
    {
      if(outputType[1] == 2)//To Nanograms
        
      else if(outputType[1] == 3)//To Micrograms
        
      else if(outputType[1] == 4)//To Milligrams
        
      else if(outputType[1] == 5)//To Grams
        
      else if(outputType[1] == 6)//To Kilograms
        
      else//To Tonnes
        
    }
    else if(inputType[1] == 2)//Nanograms
    {
      if(outputType[1] == 1)//To Picograms
        
      else if(outputType[1] == 3)//To Micrograms
        
      else if(outputType[1] == 4)//To Milligrams
        
      else if(outputType[1] == 5)//To Grams
        
      else if(outputType[1] == 6)//To Kilograms
        
      else//To Tonnes
        
    }
    else if(inputType[1] == 3)//Micrograms
    {
      if(outputType[1] == 1)//To Picograms
        
      else if(outputType[1] == 2)//To Nanograms
        
      else if(outputType[1] == 4)//To Milligrams
        
      else if(outputType[1] == 5)//To Grams
        
      else if(outputType[1] == 6)//To Kilograms
        
      else//To Tonnes
        
    }
    else if(inputType[1] == 4)//Milligrams
    {
      if(outputType[1] == 1)//To Picograms
        
      else if(outputType[1] == 2)//To Nanograms
        
      else if(outputType[1] == 3)//To Micrograms
        
      else if(outputType[1] == 5)//To Grams
        
      else if(outputType[1] == 6)//To Kilograms
        
      else//To Tonnes
        
    }
    else if(inputType[1] == 5)//Grams
    {
      if(outputType[1] == 1)//To Picograms
        
      else if(outputType[1] == 2)//To Nanograms
        
      else if(outputType[1] == 3)//To Micrograms
        
      else if(outputType[1] == 4)//To Milligrams
        
      else if(outputType[1] == 6)//To Kilograms
        
      else//To Tonnes
        
    }
    else if(inputType[1] == 6)//Kilograms
    {
      if(outputType[1] == 1)//To Picograms
        
      else if(outputType[1] == 2)//To Nanograms
        
      else if(outputType[1] == 3)//To Micrograms
        
      else if(outputType[1] == 4)//To Milligrams
        
      else if(outputType[1] == 5)//To Grams
        
      else//To Tonnes
        
    }
    else//Tonnes
    {
      if(outputType[1] == 1)//To Picograms
        
      else if(outputType[1] == 2)//To Nanograms
        
      else if(outputType[1] == 3)//To Micrograms
        
      else if(outputType[1] == 4)//To Milligrams
        
      else if(outputType[1] == 5)//To Grams
        
      else//To Kilograms
        
    }
  }
  
  /**
  *The following method is for converting from a SI unit weight measurement to a US weight measurement.  This method does not return a value as it utilizes the output field variable.  
  */
  private static void siToUsConvert()
  {
    if(inputType[1] == 1)//Picograms
    {
      if(outputType[1] == 1)//To Ounces
        
      else if(outputType[1] == 2)//To Pounds
        
      else//To Tons
        
    }
    else if(inputType[1] == 2)//Nanograms
    {
      if(outputType[1] == 1)//To Ounces
        
      else if(outputType[1] == 2)//To Pounds
        
      else//To Tons
        
    }
    else if(inputType[1] == 3)//Micrograms
    {
      if(outputType[1] == 1)//To Ounces
        
      else if(outputType[1] == 2)//To Pounds
        
      else//To Tons
        
    }
    else if(inputType[1] == 4)//Milligrams
    {
      if(outputType[1] == 1)//To Ounces
        
      else if(outputType[1] == 2)//To Pounds
        
      else//To Tons
        
    }
    else if(inputType[1] == 5)//Grams
    {
      if(outputType[1] == 1)//To Ounces
        
      else if(outputType[1] == 2)//To Pounds
        
      else//To Tons
        
    }
    else if(inputType[1] == 6)//Kilograms
    {
      if(outputType[1] == 1)//To Ounces
        
      else if(outputType[1] == 2)//To Pounds
        
      else//To Tons
        
    }
    else//Tonnes
    {
      if(outputType[1] == 1)//To Ounces
        
      else if(outputType[1] == 2)//To Pounds
        
      else//To Tons
        
    }
  }
}

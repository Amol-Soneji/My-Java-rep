/**
*This class converts measurements of liquids, from one liquid measurement to another liquid measurement.  This class can also convert from US customary untis to SI units, and vice versa as well.  
*This class also implements the measurementTypeConversion interface.  
*<br>
*
*@author  Amol Soneji
*@version 1.0, 6 Oct 2020
*/



public class convertLiquidMeasurement implements measurementTypeConversion
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
        if(inputType[1] == outputType[1])//Same US unit measurements.  The result will always be the same (ex. input in cups and output in cups as well).  
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
        if(inputType[1] == outputType[1])//Same SI unit measurements.  The result will always be the same (ex. input in cups and output in cups as well).  
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
  *The following method is for converting between one US liquid measurement to another US liquid measurement.  This method does not return a value as it ustilizes the output field variable.  
  */
  private static void usOnlyConvert()
  {
    if(inputType[1] == 1)//Teaspoons
    {
      if(outputType[1] == 2)//To tablespoons
        
      else if(outputType[1] == 3)//To fluid ounces
        
      else if(outputType[1] == 4)//To cups
        
      else if(outputType[1] == 5)//To pints
        
      else if(outputType[1] == 6)//To quarts
        
      else if(outputType[1] == 7)//To gallons
        
      else if(outputType[1] == 8)//To barrels (oil barrels)
        
      else//To hogsheads
        
    }
    else if(inputType[1] == 2)//Tablespoons
    {
      if(outputType[1] == 1)//To teaspoons
        
      else if(outputType[1] == 3)//To fluid ounces
        
      else if(outputType[1] == 4)//To cups
        
      else if(outputType[1] == 5)//To pints
        
      else if(outputType[1] == 6)//To quarts
        
      else if(outputType[1] == 7)//To gallons
        
      else if(outputType[1] == 8)//To barrels (oil barrels)
        
      else//To hogsheads
        
    }
    else if(inputType[1] == 3)//Fluid ounces
    {
      if(outputType[1] == 1)//To teaspoons
        
      else if(outputType[1] == 2)//To tablespoons
        
      else if(outputType[1] == 4)//To cups
        
      else if(outputType[1] == 5)//To pints
        
      else if(outputType[1] == 6)//To quarts
        
      else if(outputType[1] == 7)//To gallons
        
      else if(outputType[1] == 8)//To barrels (oil barrels)
        
      else//To hogsheads
        
    }
    else if(inputType[1] == 4)//Cups
    {
      if(outputType[1] == 1)//To teaspoons
        
      else if(outputType[1] == 2)//To tablespoons
        
      else if(outputType[1] == 3)//To fluid ounces
        
      else if(outputType[1] == 5)//To pints
        
      else if(outputType[1] == 6)//To quarts
        
      else if(outputType[1] == 7)//To gallons
        
      else if(outputType[1] == 8)//To barrels (oil barrels)
        
      else//To hogsheads
        
    }
    else if(inputType[1] == 5)//Pints
    {
      if(outputType[1] == 1)//To teaspoons
        
      else if(outputType[1] == 2)//To tablespoons
        
      else if(outputType[1] == 3)//To fluid ounces
        
      else if(outputType[1] == 4)//To cups
        
      else if(outputType[1] == 6)//To quarts
        
      else if(outputType[1] == 7)//To gallons
        
      else if(outputType[1] == 8)//To barrels (oil barrels)
        
      else//To hogsheads
        
    }
    else if(inputType[1] == 6)//Quarts
    {
      if(outputType[1] == 1)//To teaspoons
        
      else if(outputType[1] == 2)//To tablespoons
        
      else if(outputType[1] == 3)//To fluid ounces
        
      else if(outputType[1] == 4)//To cups
        
      else if(outputType[1] == 5)//To pints
        
      else if(outputType[1] == 7)//To gallons
        
      else if(outputType[1] == 8)//To barrels (oil barrels)
        
      else//To hogsheads
        
    }
    else if(inputType[1] == 7)//Gallons
    {
      if(outputType[1] == 1)//To teaspoons
        
      else if(outputType[1] == 2)//To tablespoons
        
      else if(outputType[1] == 3)//To fluid ounces
        
      else if(outputType[1] == 4)//To cups
        
      else if(outputType[1] == 5)//To pints
        
      else if(outputType[1] == 6)//To quarts
        
      else if(outputType[1] == 8)//To barrels (oil barrels)
        
      else//To hogsheads
        
    }
    else if(inputType[1] == 8)//Barrels (Oil barrels)
    {
      if(outputType[1] == 1)//To teaspoons
        
      else if(outputType[1] == 2)//To tablespoons
        
      else if(outputType[1] == 3)//To fluid ounces
        
      else if(outputType[1] == 4)//To cups
        
      else if(outputType[1] == 5)//To pints
        
      else if(outputType[1] == 6)//To quarts
        
      else if(outputType[1] == 7)//To gallons
        
      else//To hogsheads
        
    }
    else//Hogsheads
    {
      if(outputType[1] == 1)//To teaspoons
        
      else if(outputType[1] == 2)//To tablespoons
        
      else if(outputType[1] == 3)//To fluid ounces
        
      else if(outputType[1] == 4)//To cups
        
      else if(outputType[1] == 5)//To pints
        
      else if(outputType[1] == 6)//To quarts
        
      else if(outputType[1] == 7)//To gallons
        
      else//To barrels (oil barrels)
        
    }
  }
  
  /**
  *The following method is for converting from a US liquid measurement to a SI liquid measurement.  This method does not return a value as it utilizes the output field variable.  
  */
  private static void usToSiConvert()
  {
    if(inputType[1] == 1)//Teaspoons
    {
      
    }
    else if(inputType[1] == 2)//Tablespoons
    {
      
    }
    else if(inputType[1] == 3)//Fluid ounces
    {
      
    }
    else if(inputType[1] == 4)//Cups
    {
      
    }
    else if(inputType[1] == 5)//Pints
    {
      
    }
    else if(inputType[1] == 6)//Quarts
    {
      
    }
    else if(inputType[1] == 7)//Gallons
    {
      
    }
    else if(inputType[1] == 8)//Barrels (Oil barrels)
    {
      
    }
    else//Hogsheads
    {
      
    }
  }
  
  /**
  *The following method is for conerting between one SI unit liquid measurement to another SI unit liquid measurement.  
  *This method does not return a value as it utilizes the output field variable.  
  */
  private static void siOnlyConvert()
  {
    if(inputType[1] == 1)//Microlitres
    {
      if(outputType[1] == 2)//To millilitres
        
      else if(outputType[1] == 3)//To centilitres
        
      else if(outputType[1] == 4)//To litres
        
      else if(outputType[1] == 5)//To kilolitres
        
      else//To megalitres
        
    }
    else if(inputType[1] == 2)//Millilitres
    {
      if(outputType[1] == 1)//To microlitres
        
      else if(outputType[1] == 3)//To centilitres
        
      else if(outputType[1] == 4)//To litres
        
      else if(outputType[1] == 5)//To kilolitres
        
      else//To megalitres
        
    }
    else if(inputType[1] == 3)//Centilitres
    {
      if(outputType[1] == 1)//To microlitres
        
      else if(outputType[1] == 2)//To millilitres
        
      else if(outputType[1] == 4)//To litres
        
      else if(outputType[1] == 5)//To kilolitres
        
      else//To megalitres
        
    }
    else if(inputType[1] == 4)//Litres
    {
      if(outputType[1] == 1)//To microlitres
        
      else if(outputType[1] == 2)//To millilitres
        
      else if(outputType[1] == 3)//To centilitres
        
      else if(outputType[1] == 5)//To kilolitres
        
      else//To megalitres
        
    }
    else if(inputType[1] == 5)//Kilolitres
    {
      if(outputType[1] == 1)//To microlitres
        
      else if(outputType[1] == 2)//To millilitres
        
      else if(outputType[1] == 3)//To centilitres
        
      else if(outputType[1] == 4)//To litres
        
      else//To megalitres
        
    }
    else//Megalitres
    {
      if(outputType[1] == 1)//To microlitres
        
      else if(outputType[1] == 2)//To millilitres
        
      else if(outputType[1] == 3)//To centilitres
        
      else if(outputType[1] == 4)//To litres
        
      else//To kilolitres
        
    }
  }
  
  /**
  *The following method is for converting from a SI unit liquid measurement to a US liquid measurement.  This method does not return a value as it utilizes the output field variable.  
  */
  private static void siToUsConvert()
  {
    
  }
}

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
      if(outputType[1] == 2)//To Tablespoons
        output = input / 3;
      else if(outputType[1] == 3)//To Fluid Ounces
        output = input / 6;
      else if(outputType[1] == 4)//To Cups
        output = input / 48;
      else if(outputType[1] == 5)//To Pints
        output = input / 96;
      else if(outputType[1] == 6)//To Quarts
        output = input / 192;
      else if(outputType[1] == 7)//To Gallons
        output = input / 768;
      else if(outputType[1] == 8)//To Barrels (Oil Barrels)
        output = input / 32256;
      else//To Hogsheads
        output = input / 48384;
    }
    else if(inputType[1] == 2)//Tablespoons
    {
      if(outputType[1] == 1)//To Teaspoons
        output = input * 3;
      else if(outputType[1] == 3)//To Fluid Ounces
        output = input / 2;
      else if(outputType[1] == 4)//To Cups
        output = input / 16;
      else if(outputType[1] == 5)//To Pints
        output = input / 32;
      else if(outputType[1] == 6)//To Quarts
        output = input / 64;
      else if(outputType[1] == 7)//To Gallons
        output = input / 256;
      else if(outputType[1] == 8)//To Barrels (Oil Barrels)
        output = input / 10752;
      else//To Hogsheads
        output = input / 16128;
    }
    else if(inputType[1] == 3)//Fluid ounces
    {
      if(outputType[1] == 1)//To Teaspoons
        output = input * 6;
      else if(outputType[1] == 2)//To Tablespoons
        output = input * 2;
      else if(outputType[1] == 4)//To Cups
        output = input / 8;
      else if(outputType[1] == 5)//To Pints
        output = input / 16;
      else if(outputType[1] == 6)//To Quarts
        output = input / 32;
      else if(outputType[1] == 7)//To Gallons
        output = input / 128;
      else if(outputType[1] == 8)//To Barrels (Oil Barrels)
        output = input / 5376;
      else//To Hogsheads
        output = input / 8064;
    }
    else if(inputType[1] == 4)//Cups
    {
      if(outputType[1] == 1)//To Teaspoons
        output = input * 48;
      else if(outputType[1] == 2)//To Tablespoons
        output = input * 16;
      else if(outputType[1] == 3)//To Fluid Ounces
        output = input * 8;
      else if(outputType[1] == 5)//To Pints
        output = input / 2;
      else if(outputType[1] == 6)//To Quarts
        output = input / 4;
      else if(outputType[1] == 7)//To Gallons
        output = input / 16;
      else if(outputType[1] == 8)//To Barrels (Oil Barrels)
        output = input / 672;
      else//To Hogsheads
        output = input / 1008;
    }
    else if(inputType[1] == 5)//Pints
    {
      if(outputType[1] == 1)//To Teaspoons
        output = input * 96;
      else if(outputType[1] == 2)//To Tablespoons
        output = input * 32;
      else if(outputType[1] == 3)//To Fluid Ounces
        output = input * 16;
      else if(outputType[1] == 4)//To Cups
        output = input * 2;
      else if(outputType[1] == 6)//To Quarts
        output = input / 2;
      else if(outputType[1] == 7)//To Gallons
        output = input / 8;
      else if(outputType[1] == 8)//To Barrels (Oil Barrels)
        output = input / 336;
      else//To Hogsheads
        output = input / 504;
    }
    else if(inputType[1] == 6)//Quarts
    {
      if(outputType[1] == 1)//To Teaspoons
        output = input * 192;
      else if(outputType[1] == 2)//To Tablespoons
        output = input * 64;
      else if(outputType[1] == 3)//To Fluid Ounces
        output = input * 32;
      else if(outputType[1] == 4)//To Cups
        output = input * 4;
      else if(outputType[1] == 5)//To Pints
        output = input * 2;
      else if(outputType[1] == 7)//To Gallons
        output = input / 4;
      else if(outputType[1] == 8)//To Barrels (Oil Barrels)
        output = input / 168;
      else//To Hogsheads
        output = input / 252;
    }
    else if(inputType[1] == 7)//Gallons
    {
      if(outputType[1] == 1)//To Teaspoons
        output = input * 768;
      else if(outputType[1] == 2)//To Tablespoons
        output = input * 256;
      else if(outputType[1] == 3)//To Fluid Ounces
        output = input * 128;
      else if(outputType[1] == 4)//To Cups
        output = input * 16;
      else if(outputType[1] == 5)//To Pints
        output = input * 8;
      else if(outputType[1] == 6)//To Quarts
        output = input * 4;
      else if(outputType[1] == 8)//To Barrels (Oil Barrels)
        output = input / 42;
      else//To Hogsheads
        output = input / 63;
    }
    else if(inputType[1] == 8)//Barrels (Oil barrels)
    {
      if(outputType[1] == 1)//To Teaspoons
        output = input * 32256;
      else if(outputType[1] == 2)//To Tablespoons
        output = input * 10752;
      else if(outputType[1] == 3)//To Fluid Ounces
        output = input * 5376;
      else if(outputType[1] == 4)//To Cups
        output = input * 672;
      else if(outputType[1] == 5)//To Pints
        output = input * 336;
      else if(outputType[1] == 6)//To Quarts
        output = input * 168;
      else if(outputType[1] == 7)//To Gallons
        output = input * 42;
      else//To Hogsheads
        output = input / (2 / 3);//Factor of 21.  
    }
    else//Hogsheads
    {
      if(outputType[1] == 1)//To Teaspoons
        output = input * 48384;
      else if(outputType[1] == 2)//To Tablespoons
        output = input * 16128;
      else if(outputType[1] == 3)//To Fluid Ounces
        output = input * 8064;
      else if(outputType[1] == 4)//To Cups
        output = input * 1008;
      else if(outputType[1] == 5)//To Pints
        output = input * 504;
      else if(outputType[1] == 6)//To Quarts
        output = input * 252;
      else if(outputType[1] == 7)//To Gallons
        output = input * 63;
      else//To Barrels (Oil Barrels)
        output = input * (2 / 3);//Factor of 21.  
    }
  }
  
  /**
  *The following method is for converting from a US liquid measurement to a SI liquid measurement.  This method does not return a value as it utilizes the output field variable.  
  */
  private static void usToSiConvert()
  {
    if(inputType[1] == 1)//Teaspoons
    {
      if(outputType[1] == 1)//To Microlitres
        
      else if(outputType[1] == 2)//To Millilitres
        
      else if(outputType[1] == 3)//To Centilitres
        
      else if(outputType[1] == 4)//To Litres
        
      else if(outputType[1] == 5)//To Kilolitres
        
      else//To Megalitres
        
    }
    else if(inputType[1] == 2)//Tablespoons
    {
      if(outputType[1] == 1)//To Microlitres
        
      else if(outputType[1] == 2)//To Millilitres
        
      else if(outputType[1] == 3)//To Centilitres
        
      else if(outputType[1] == 4)//To Litres
        
      else if(outputType[1] == 5)//To Kilolitres
        
      else//To Megalitres
        
    }
    else if(inputType[1] == 3)//Fluid ounces
    {
      if(outputType[1] == 1)//To Microlitres
        
      else if(outputType[1] == 2)//To Millilitres
        
      else if(outputType[1] == 3)//To Centilitres
        
      else if(outputType[1] == 4)//To Litres
        
      else if(outputType[1] == 5)//To Kilolitres
        
      else//To Megalitres
        
    }
    else if(inputType[1] == 4)//Cups
    {
      if(outputType[1] == 1)//To Microlitres
        
      else if(outputType[1] == 2)//To Millilitres
        
      else if(outputType[1] == 3)//To Centilitres
        
      else if(outputType[1] == 4)//To Litres
        
      else if(outputType[1] == 5)//To Kilolitres
        
      else//To Megalitres
        
    }
    else if(inputType[1] == 5)//Pints
    {
      if(outputType[1] == 1)//To Microlitres
        
      else if(outputType[1] == 2)//To Millilitres
        
      else if(outputType[1] == 3)//To Centilitres
        
      else if(outputType[1] == 4)//To Litres
        
      else if(outputType[1] == 5)//To Kilolitres
        
      else//To Megalitres
        
    }
    else if(inputType[1] == 6)//Quarts
    {
      if(outputType[1] == 1)//To Microlitres
        
      else if(outputType[1] == 2)//To Millilitres
        
      else if(outputType[1] == 3)//To Centilitres
        
      else if(outputType[1] == 4)//To Litres
        
      else if(outputType[1] == 5)//To Kilolitres
        
      else//To Megalitres
        
    }
    else if(inputType[1] == 7)//Gallons
    {
      if(outputType[1] == 1)//To Microlitres
        
      else if(outputType[1] == 2)//To Millilitres
        
      else if(outputType[1] == 3)//To Centilitres
        
      else if(outputType[1] == 4)//To Litres
        
      else if(outputType[1] == 5)//To Kilolitres
        
      else//To Megalitres
        
    }
    else if(inputType[1] == 8)//Barrels (Oil barrels)
    {
      if(outputType[1] == 1)//To Microlitres
        
      else if(outputType[1] == 2)//To Millilitres
        
      else if(outputType[1] == 3)//To Centilitres
        
      else if(outputType[1] == 4)//To Litres
        
      else if(outputType[1] == 5)//To Kilolitres
        
      else//To Megalitres
        
    }
    else//Hogsheads
    {
      if(outputType[1] == 1)//To Microlitres
        
      else if(outputType[1] == 2)//To Millilitres
        
      else if(outputType[1] == 3)//To Centilitres
        
      else if(outputType[1] == 4)//To Litres
        
      else if(outputType[1] == 5)//To Kilolitres
        
      else//To Megalitres
        
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
      if(outputType[1] == 2)//To Millilitres
        output = input / 1000;
      else if(outputType[1] == 3)//To Centilitres
        output = input / 10000;
      else if(outputType[1] == 4)//To Litres
        output = input / 1000000;
      else if(outputType[1] == 5)//To Kilolitres
        output = input / 1000000000;
      else//To Megalitres
        output = input / 1000000000000;
    }
    else if(inputType[1] == 2)//Millilitres
    {
      if(outputType[1] == 1)//To Microlitres
        output = input * 1000;
      else if(outputType[1] == 3)//To Centilitres
        output = input / 10;
      else if(outputType[1] == 4)//To Litres
        output = input / 1000;
      else if(outputType[1] == 5)//To Kilolitres
        output = input / 1000000;
      else//To Megalitres
        output = input / 1000000000;
    }
    else if(inputType[1] == 3)//Centilitres
    {
      if(outputType[1] == 1)//To Microlitres
        output = input * 10000;
      else if(outputType[1] == 2)//To Millilitres
        output = input * 10;
      else if(outputType[1] == 4)//To Litres
        output = input / 100;
      else if(outputType[1] == 5)//To Kilolitres
        output = input / 100000;
      else//To Megalitres
        output = input / 100000000;
    }
    else if(inputType[1] == 4)//Litres
    {
      if(outputType[1] == 1)//To Microlitres
        output = input * 1000000;
      else if(outputType[1] == 2)//To Millilitres
        output = input * 1000;
      else if(outputType[1] == 3)//To Centilitres
        output = input * 100;
      else if(outputType[1] == 5)//To Kilolitres
        output = input / 1000;
      else//To Megalitres
        output = input / 1000000;
    }
    else if(inputType[1] == 5)//Kilolitres
    {
      if(outputType[1] == 1)//To Microlitres
        output = input * 1000000000;
      else if(outputType[1] == 2)//To Millilitres
        output = input * 1000000;
      else if(outputType[1] == 3)//To Centilitres
        output = input * 100000;
      else if(outputType[1] == 4)//To Litres
        output = input * 1000;
      else//To Megalitres
        output = input / 1000;
    }
    else//Megalitres
    {
      if(outputType[1] == 1)//To Microlitres
        output = input * 1000000000000;
      else if(outputType[1] == 2)//To Millilitres
        output = input * 1000000000;
      else if(outputType[1] == 3)//To Centilitres
        output = input * 100000000;
      else if(outputType[1] == 4)//To Litres
        output = input * 1000000;
      else//To Kilolitres
        output = input * 1000;
    }
  }
  
  /**
  *The following method is for converting from a SI unit liquid measurement to a US liquid measurement.  This method does not return a value as it utilizes the output field variable.  
  */
  private static void siToUsConvert()
  {
    if(inputType[1] == 1)//Microlitres
    {
      if(outputType[1] == 1)//To Teaspoons
        
      else if(outputType[1] == 2)//To Tablespoons
        
      else if(outputType[1] == 3)//To Fluid Ounces
        
      else if(outputType[1] == 4)//To Cups
        
      else if(outputType[1] == 5)//To Pints
        
      else if(outputType[1] == 6)//To Quarts
        
      else if(outputType[1] == 7)//To Gallons
        
      else if(outputType[1] == 8)//To Barrels (Oil Barrels)
        
      else//To Hogsheads
        
    }
    else if(inputType[1] == 2)//Millilitres
    {
      if(outputType[1] == 1)//To Teaspoons
        
      else if(outputType[1] == 2)//To Tablespoons
        
      else if(outputType[1] == 3)//To Fluid Ounces
        
      else if(outputType[1] == 4)//To Cups
        
      else if(outputType[1] == 5)//To Pints
        
      else if(outputType[1] == 6)//To Quarts
        
      else if(outputType[1] == 7)//To Gallons
        
      else if(outputType[1] == 8)//To Barrels (Oil Barrels)
        
      else//To Hogsheads
        
    }
    else if(inputType[1] == 3)//Centilitres
    {
      if(outputType[1] == 1)//To Teaspoons
        
      else if(outputType[1] == 2)//To Tablespoons
        
      else if(outputType[1] == 3)//To Fluid Ounces
        
      else if(outputType[1] == 4)//To Cups
        
      else if(outputType[1] == 5)//To Pints
        
      else if(outputType[1] == 6)//To Quarts
        
      else if(outputType[1] == 7)//To Gallons
        
      else if(outputType[1] == 8)//To Barrels (Oil Barrels)
        
      else//To Hogsheads
        
    }
    else if(inputType[1] == 4)//Litres
    {
      if(outputType[1] == 1)//To Teaspoons
        
      else if(outputType[1] == 2)//To Tablespoons
        
      else if(outputType[1] == 3)//To Fluid Ounces
        
      else if(outputType[1] == 4)//To Cups
        
      else if(outputType[1] == 5)//To Pints
        
      else if(outputType[1] == 6)//To Quarts
        
      else if(outputType[1] == 7)//To Gallons
        
      else if(outputType[1] == 8)//To Barrels (Oil Barrels)
        
      else//To Hogsheads
        
    }
    else if(inputType[1] == 5)//Kilolitres
    {
      if(outputType[1] == 1)//To Teaspoons
        
      else if(outputType[1] == 2)//To Tablespoons
        
      else if(outputType[1] == 3)//To Fluid Ounces
        
      else if(outputType[1] == 4)//To Cups
        
      else if(outputType[1] == 5)//To Pints
        
      else if(outputType[1] == 6)//To Quarts
        
      else if(outputType[1] == 7)//To Gallons
        
      else if(outputType[1] == 8)//To Barrels (Oil Barrels)
        
      else//To Hogsheads
        
    }
    else//Megalitres
    {
      if(outputType[1] == 1)//To Teaspoons
        
      else if(outputType[1] == 2)//To Tablespoons
        
      else if(outputType[1] == 3)//To Fluid Ounces
        
      else if(outputType[1] == 4)//To Cups
        
      else if(outputType[1] == 5)//To Pints
        
      else if(outputType[1] == 6)//To Quarts
        
      else if(outputType[1] == 7)//To Gallons
        
      else if(outputType[1] == 8)//To Barrels (Oil Barrels)
        
      else//To Hogsheads
        
    }
  }
}

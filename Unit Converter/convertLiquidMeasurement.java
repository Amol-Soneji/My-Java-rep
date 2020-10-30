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
        output = input * 4928.92159;
      else if(outputType[1] == 2)//To Millilitres
        output = input * 4.92892159;
      else if(outputType[1] == 3)//To Centilitres
        output = input * 0.49289216;
      else if(outputType[1] == 4)//To Litres
        output = input * 0.00492892;
      else if(outputType[1] == 5)//To Kilolitres
        output = input * 0.00000493;
      else//To Megalitres
        output = input * 0.000000005;
    }
    else if(inputType[1] == 2)//Tablespoons
    {
      if(outputType[1] == 1)//To Microlitres
        output = input * 14786.7648;
      else if(outputType[1] == 2)//To Millilitres
        output = input * 14.7867648;
      else if(outputType[1] == 3)//To Centilitres
        output = input * 1.47867648;
      else if(outputType[1] == 4)//To Litres
        output = input * 0.01478676;
      else if(outputType[1] == 5)//To Kilolitres
        output = input * 0.00001479;
      else//To Megalitres
        output = input * 0.000000015;
    }
    else if(inputType[1] == 3)//Fluid ounces
    {
      if(outputType[1] == 1)//To Microlitres
        output = input * 29573.5295;
      else if(outputType[1] == 2)//To Millilitres
        output = input * 29.5735295;
      else if(outputType[1] == 3)//To Centilitres
        output = input * 2.95735295;
      else if(outputType[1] == 4)//To Litres
        output = input * 0.02957353;
      else if(outputType[1] == 5)//To Kilolitres
        output = input * 0.00002957;
      else//To Megalitres
        output = input * 0.00000003;
    }
    else if(inputType[1] == 4)//Cups
    {
      if(outputType[1] == 1)//To Microlitres
        output = input * 236588.236;
      else if(outputType[1] == 2)//To Millilitres
        output = input * 236.588236;
      else if(outputType[1] == 3)//To Centilitres
        output = input * 23.6588236;
      else if(outputType[1] == 4)//To Litres
        output = input * 0.23658824;
      else if(outputType[1] == 5)//To Kilolitres
        output = input * 0.00023659;
      else//To Megalitres
        output = input * 0.00000024;
    }
    else if(inputType[1] == 5)//Pints
    {
      if(outputType[1] == 1)//To Microlitres
        output = input * 473176.473;
      else if(outputType[1] == 2)//To Millilitres
        output = input * 473.176473;
      else if(outputType[1] == 3)//To Centilitres
        output = input * 47.3176473;
      else if(outputType[1] == 4)//To Litres
        output = input * 0.47317647;
      else if(outputType[1] == 5)//To Kilolitres
        output = input * 0.00047318;
      else//To Megalitres
        output = input * 0.00000047;
    }
    else if(inputType[1] == 6)//Quarts
    {
      if(outputType[1] == 1)//To Microlitres
        output = input * 946352.945;
      else if(outputType[1] == 2)//To Millilitres
        output = input * 946.352945;
      else if(outputType[1] == 3)//To Centilitres
        output = input * 94.6352945;
      else if(outputType[1] == 4)//To Litres
        output = input * 0.94635295;
      else if(outputType[1] == 5)//To Kilolitres
        output = input * 0.00094635;
      else//To Megalitres
        output = input * 0.00000095;
    }
    else if(inputType[1] == 7)//Gallons
    {
      if(outputType[1] == 1)//To Microlitres
        output = input * 3785411.78;
      else if(outputType[1] == 2)//To Millilitres
        output = input * 3785.41178;
      else if(outputType[1] == 3)//To Centilitres
        output = input * 378.541178;
      else if(outputType[1] == 4)//To Litres
        output = input * 3.78541178;
      else if(outputType[1] == 5)//To Kilolitres
        output = input * 0.00378541;
      else//To Megalitres
        output = input * 0.00000379;
    }
    else if(inputType[1] == 8)//Barrels (Oil barrels)
    {
      if(outputType[1] == 1)//To Microlitres
        output = input * 158987294.928;
      else if(outputType[1] == 2)//To Millilitres
        output = input * 158987.294928;
      else if(outputType[1] == 3)//To Centilitres
        output = input * 15898.7294928;
      else if(outputType[1] == 4)//To Litres
        output = input * 158.987294928;
      else if(outputType[1] == 5)//To Kilolitres
        output = input * 0.15898729493;
      else//To Megalitres
        output = input * 0.0001589873;
    }
    else//Hogsheads
    {
      if(outputType[1] == 1)//To Microlitres
        output = input * 238480942.39;
      else if(outputType[1] == 2)//To Millilitres
        output = input * 238480.94239;
      else if(outputType[1] == 3)//To Centilitres
        output = input * 23848.094239;
      else if(outputType[1] == 4)//To Litres
        output = input * 238.48094239;
      else if(outputType[1] == 5)//To Kilolitres
        output = input * 0.2384809424;
      else//To Megalitres
        output = input * 0.0002384809;
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
        output = input / 1000000000000l;
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
        output = input * 1000000000000l;
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
        output = input / 4928.92159;
      else if(outputType[1] == 2)//To Tablespoons
        output = input / 14786.7648;
      else if(outputType[1] == 3)//To Fluid Ounces
        output = input / 29573.5295;
      else if(outputType[1] == 4)//To Cups
        output = input / 236588.236;
      else if(outputType[1] == 5)//To Pints
        output = input / 473176.473;
      else if(outputType[1] == 6)//To Quarts
        output = input / 946352.945;
      else if(outputType[1] == 7)//To Gallons
        output = input / 3785411.78;
      else if(outputType[1] == 8)//To Barrels (Oil Barrels)
        output = input / 158987294.928;
      else//To Hogsheads
        output = input / 238480942.39;
    }
    else if(inputType[1] == 2)//Millilitres
    {
      if(outputType[1] == 1)//To Teaspoons
        output = input / 4.92892159;
      else if(outputType[1] == 2)//To Tablespoons
        output = input / 14.7867648;
      else if(outputType[1] == 3)//To Fluid Ounces
        output = input / 29.5735295;
      else if(outputType[1] == 4)//To Cups
        output = input / 236.588236;
      else if(outputType[1] == 5)//To Pints
        output = input / 473.176473;
      else if(outputType[1] == 6)//To Quarts
        output = input / 946.352945;
      else if(outputType[1] == 7)//To Gallons
        output = input / 3785.41178;
      else if(outputType[1] == 8)//To Barrels (Oil Barrels)
        output = input / 158987.294928;
      else//To Hogsheads
        output = input / 238480.94239;
    }
    else if(inputType[1] == 3)//Centilitres
    {
      if(outputType[1] == 1)//To Teaspoons
        output = input / 0.49289216;
      else if(outputType[1] == 2)//To Tablespoons
        output = input / 1.47867648;
      else if(outputType[1] == 3)//To Fluid Ounces
        output = input / 2.95735295;
      else if(outputType[1] == 4)//To Cups
        output = input / 23.6588236;
      else if(outputType[1] == 5)//To Pints
        output = input / 47.3176473;
      else if(outputType[1] == 6)//To Quarts
        output = input / 94.6352945;
      else if(outputType[1] == 7)//To Gallons
        output = input / 378.541178;
      else if(outputType[1] == 8)//To Barrels (Oil Barrels)
        output = input / 15898.7294928;
      else//To Hogsheads
        output = input / 23848.094239;
    }
    else if(inputType[1] == 4)//Litres
    {
      if(outputType[1] == 1)//To Teaspoons
        output = input / 0.00492892;
      else if(outputType[1] == 2)//To Tablespoons
        output = input / 0.01478676;
      else if(outputType[1] == 3)//To Fluid Ounces
        output = input / 0.02957353;
      else if(outputType[1] == 4)//To Cups
        output = input / 0.23658824;
      else if(outputType[1] == 5)//To Pints
        output = input / 0.47317647;
      else if(outputType[1] == 6)//To Quarts
        output = input / 0.94635295;
      else if(outputType[1] == 7)//To Gallons
        output = input / 3.78541178;
      else if(outputType[1] == 8)//To Barrels (Oil Barrels)
        output = input / 158.987294928;
      else//To Hogsheads
        output = input / 238.48094239;
    }
    else if(inputType[1] == 5)//Kilolitres
    {
      if(outputType[1] == 1)//To Teaspoons
        output = input / 0.00000493;
      else if(outputType[1] == 2)//To Tablespoons
        output = input / 0.00001479;
      else if(outputType[1] == 3)//To Fluid Ounces
        output = input / 0.00002957;
      else if(outputType[1] == 4)//To Cups
        output = input / 0.00023659;
      else if(outputType[1] == 5)//To Pints
        output = input / 0.00047318;
      else if(outputType[1] == 6)//To Quarts
        output = input / 0.00094635;
      else if(outputType[1] == 7)//To Gallons
        output = input / 0.00378541;
      else if(outputType[1] == 8)//To Barrels (Oil Barrels)
        output = input / 0.15898729493;
      else//To Hogsheads
        output = input / 0.2384809424;
    }
    else//Megalitres
    {
      if(outputType[1] == 1)//To Teaspoons
        output = input / 0.000000005;
      else if(outputType[1] == 2)//To Tablespoons
        output = input / 0.000000015;
      else if(outputType[1] == 3)//To Fluid Ounces
        output = input / 0.00000003;
      else if(outputType[1] == 4)//To Cups
        output = input / 0.00000024;
      else if(outputType[1] == 5)//To Pints
        output = input / 0.00000047;
      else if(outputType[1] == 6)//To Quarts
        output = input / 0.00000095;
      else if(outputType[1] == 7)//To Gallons
        output = input / 0.00000379;
      else if(outputType[1] == 8)//To Barrels (Oil Barrels)
        output = input / 0.0001589873;
      else//To Hogsheads
        output = input / 0.0002384809;
    }
  }
}

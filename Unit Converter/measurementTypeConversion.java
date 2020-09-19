/**
*This is an interface for the classes that will do the computational work of converting from one measurement to another of the same measurement type.  
*<br>
*
*@author  Amol Soneji
*@version 1.0, 19 Sept 2020
*/

public interface measurementTypeConverter
{
  //The method in the implementing classes that will get called by the converter class.  
  public static double measurementConvert(byte[] inpType, byte[] outType, double inp);
}

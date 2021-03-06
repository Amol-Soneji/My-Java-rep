import java.io.*;
import java.util.Scanner;

/**
*This class reads the file and searches for the number of instances the letters being searched for appear.  
*<br>
*
*@author  Amol Soneji
*@version 1.1,  14 Sept 2020
*/



public class countInFile
{
  private static String fileName;//This will store the name of the file.  The field name can take a full file path as a name.  
  private static Character[] charsToSearch;//The characters to search for.  The array length is not set on purpouse as "charsToSearch" will be assigned an array.  
  private static long numbInstances = 0;//Will be used to count the number of instances.  
  
  /**
  *The following method is used to set the field values.  A constructor is not being used as it does not make sense to turn this class into an Object.  
  */
  public static void setFields(String name, Character[] letters)
  {
    fileName = name;
    charsToSearch = letters;
  }
  
  /**
  *The following method is used to count and find the number of instances the letters to search for appear in a text file.  
  */
  protected static void doCount()
  {
    try
    {
      File inputFile = new File(fileName);
      Scanner searchScan = new Scanner(inputFile);
      while(searchScan.hasNextLine())
      {
        String textLine = searchScan.nextLine();
        for(int i = 0; i < textLine.length(); i++)
        {
          for(int j = 0; j < charsToSearch.length; j++)
          {
            if(textLine.charAt(i) == charsToSearch[j])
              numbInstances++;
          }
        }
      }
      searchScan.close();
    }
    catch(FileNotFoundException err)
    {
      err.printStackTrace();
    }
    catch(IOException err)
    {
      err.printStackTrace();
    }
  }
  
  /**
  *The following method returns the count of the number of instances the letters to search for, occured in the text file.  
  */
  protected static long getNumbInstances()
  {
    return numbInstances;
  }
}

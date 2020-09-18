import java.util.Scanner;

/**
*This is the main class for the Find Letters java program.  In this class the user will be prompted to enter some inputs.  After which the main class will utilize the 
*countInFile class to search, count, and retreive the number of times in total the provided letters or characters were found.  The main class then displays the result.  
*
*<br>
*The first input will be used to determine how many letters or characters (max 3) to search for in a text file.  
*
*The next inputs are the letters to search for.  
*
*Afterwards the next input is where the user enters the name of the file, if the compiles java class files are located in the same directory as the file that is to
*be used.  Or, the user will enter the full directory path to the file to be used.  
*
*<br>
*The program will then utilize and get the results from the countInFile class, and display the results.  
*<br>
*
*@author  Amol Soneji
*@version 1.0,  15 Sept 2020
*/


public class Main
{
  public static void main(String[] args)
  {
    Scanner userInput = new Scanner(System.in);//Create the Scanner inststance.  This will be used for user input in this program.  
    boolean validInput = false;
    System.out.println("As stated in the README.md file of the java program, this program counts the number of times a specified group of" 
                       + System.lineSeparator()
                       + "letters or characters (max 3) are found in a text file.  ");
    System.out.println("Enter the number of letters or chacters to search for.  (Max 3)  :  ");
    int numberOfLetters = 0;
    while(!validInput)//Check to see if the input that the user enters is valid.  
    {
      try
      {
        numberOfLetters = Integer.parseInt(userInput.nextLine());
        if((numberOfLetters < 1) || (numberOfLetters > 3))
          System.out.println("Invalid input.  Please try again.  :  ");
        else
          validInput = true;
      }
      catch(Exception someException)//An input that was not a number or something else.  
      {
        System.out.println("Invalid input.  Please try again.  :  ");
      }
    }
    Character[] charsToSearch = new Character[numberOfLetters];
    System.out.println("Please enter the first letter or character that is to be searched for.  :  ");
    charsToSearch[0] = (userInput.nextLine()).charAt(0);
    for(int i = 1; i < numberOfLetters; i++)//Get the letter or chateracters to be searched for.  
    {
      validInput = false;
      System.out.println("Please enter the next letter or character that is to be searched for.  :  ");
      Character letter;
      while(!validInput)
      {
        letter = (userInput.nextLine()).charAt(0);
        for(int j = 0; j < i; j++)//Check to see if the same letter or character is already being searched for.  
        {
          if(charsToSearch[j] == letter)
            System.out.println("That letter or character has allready been entered to be searched for.  Please enter a different letter or character.  :  ");
          else if((charsToSearch[j] != letter) && (j == (i - 1)))
            validInput = true;
          else
            //Do nothing.  
        }
      }
      charsToSearch[i] = letter;
    }
    System.out.println("Now that you have entered the letters, it is time to enter the file to be looked at in the search.  "
                       + System.lineSeparator()
                       + "If the file that you want to search is located in the same directory as the Java class FilePath for this program, just enter the name of the file.  "
                       + System.lineSeparator()
                       + "Otherwise enter the full directory path of the file you want to use.  :  ");
    String fileName = userInput.nextLine();
    
    //Set the field variables in the countInFile class by sening fileName string and charsToSearch character array, as parameters.  
    countInFile.setFields(fileName, charsToSearch);
    //Do the counting of the number of instances.  
    countInFile.doCount();
    //Get the results of the count.  
    int result = countInFile.getNumbInstances();
    
    //Display the results of the overall total of all instances that the letters in the charsToSearch character array appeared.  
    System.out.println("The total number of times any one of the " + numberOfLetters + " appeared is " + result + ".  ");
    System.out.println("Thank you for running this program.  ");
    System.exit(0);
  }
}

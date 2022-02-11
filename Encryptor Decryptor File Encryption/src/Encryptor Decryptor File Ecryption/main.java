import CipherKeys.*;
import CipherPackage.*;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class main
{
	private static final String VERSION_NUMBER = "0.9";
	private static CipherKeyStorage dbAccess;
	
	public static void main(String[] args)
	{
		Scanner userInput = new Scanner(System.in);
		System.out.println("Encryptor Decryptor File Encryption, Version : " + VERSION_NUMBER);
		boolean defaultExists = false;
		while(true)
		{
			if(!defaultExists)
			{
				try
				{
					File dbFile = new File("Keys.db");
					Scanner fileTest = new Scanner(dbFile);
					defaultExists = true;
					fileTest.close();
				}
				catch(FileNotFoundException e)
				{
					defaultExists = false;
				}
			}
			int input = 0;
			if(defaultExists)
			{
				while((input < 1) || ((input > 2) && (input < 4)) || (input > 4))
				{
					System.out.println("Enter 1 to use the deafult database that exists.  Or enter 2 to use your "
									   + "own database that is compatible with this program (see README.md).  "
									   + "Enter 5 to exit the program.  :  ");
					input = userInput.nextInt();
				}
			}
			else
			{
				while((input < 3) || (input > 4))
				{
					System.out.println("Enter 3 to create a default database to use. Or enter 2 to use your own "
									   + "database the is compatible with this program (see README.md).  "
									   + "Enter 4 to exit the program.  :  ");
					input = userInput.nextInt();
				}
			}
			
			if(input == 1)
			{
				dbAccess = new CipherKeyStorage();
				int toDo = 0;
				while(toDo != 5)
				{
					toDo = askAction(1);
				}
			}
			else if(input == 2)
			{
				String ignore = userInput.nextLine(); //Flush the next line character that may remain in Scanner.  
				System.out.println("Enter the file path with the name of the database file.  :  ");
				String thePath = userInput.nextLine();
				dbAccess = new CipherKeyStorage(thePath);
				toDo = askAction(1);
			}
			else if(input == 3)
			{
				System.out.println("Creating and setting to use the default database.  ");
				dbAccess = new CipherKeyStorage();
				System.out.println("Default database created successfully.  ");
				int toDo = 0;
				while(toDo != 5)
				{
					toDo = askAction(2);
				}
			}
			else
				break;
		}
		System.out.println("Closing the program.  ");
		userInput.close();
		System.exit(0);
	}
	
	private static int askAction(int availOptionRefferer)
	{
		int inputSelection = 0;
		Scanner userInput = new Scanner(System.in);
		if(availOptionRefferer == 1) //There are keys for files in db.  
		{
			while((inputSelection < 1) || (inputSelection > 5))
			{
				System.out.println("Enter 1 to decrypt a file.  Enter 2 to encrypt a file.  Enter 3 to remove "
								   + "a key.  Enter 4 to enter a key manually.  Note that this program does "
								   + "not yet allow the simulatanious use of two different key databases.  "
								   + "To use another key database enter 5. :  ");
				inputSelection = userInput.nextInt();
			}
		}
		else //No keys in db
		{
			while(((inputSelection < 2) || (inputSelection == 3)) || (inputSelection > 5))
			{
				System.out.println("Enter 2 to encrypt a file.  Enter 4 to enter a key manually.  Note that "
								   + "this program does not yet allow the simulatanious use of two "
								   + "different key databases.  To use another key database enter 5.  :  ");
				inputSelection = userInput.nextInt();
			}
		}
		userInput.close();
		return inputSelection;
	}
	
	private static boolean encryptDecrypt(int option, int keyType)
	{
		
	}
	
	private static String readFile(String fileName)
	{
		
	}
	
	private static boolean writeFile(String fileName, String contents)
	{
		

	}
}

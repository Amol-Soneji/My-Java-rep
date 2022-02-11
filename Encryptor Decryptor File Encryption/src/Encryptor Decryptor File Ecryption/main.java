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
			}
			else if(input == 2)
			{
				
			}
			else if(input == 3)
			{
				
			}
			else
				break;
		}
	}
	
	private static int askAction(int availOptionRefferer)
	{
		
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

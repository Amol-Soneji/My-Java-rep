import CipherKeys.*;
import CipherPackage.*;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Base64;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class main
{
	private static final String VERSION_NUMBER = "0.9";
	private static CipherKeyStorage dbAccess = null;
	
	public static void main(String[] args)
	{
		Scanner userInput = new Scanner(System.in);
		System.out.println("Encryptor Decryptor File Encryption, Version : " + VERSION_NUMBER);
		boolean defaultExists = false;
		while(true)
		{
			if(!defaultExists) //Check to see if the default db exists.  
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
			if(defaultExists) //Options for using an existent default db.  
			{
				while((input < 1) || ((input > 2) && (input < 4)) || (input > 4))
				{
					System.out.println("Enter 1 to use the deafult database that exists.  Or enter 2 to use your \n"
									   + "own database that is compatible with this program (see README.md).  \n"
									   + "Enter 5 to exit the program.  :  ");
					input = userInput.nextInt();
				}
			}
			else //Less options available for a newly created default db.  
			{
				while((input < 3) || (input > 4))
				{
					System.out.println("Enter 3 to create a default database to use. Or enter 2 to use your own \n"
									   + "database the is compatible with this program (see README.md).  \n"
									   + "Enter 4 to exit the program.  :  ");
					input = userInput.nextInt();
				}
			}
			while(input != 4)
			{
				int toDo = 0;
				if(input == 1)
				{
					if(defaultExists && (dbAccess == null)) //We are starting of as 1 and not switched from 3 to 1.  
						dbAccess = new CipherKeyStorage();
					toDo = askAction(1);
				}
				else if(input == 2)
				{
					if(dbAccess == null) //Currently no db is in use.  
					{
						String ignore = userInput.nextLine(); //Flush the next line character that may remain in Scanner.  
						System.out.println("Enter the file path with the name of the database file.  :  ");
						String thePath = userInput.nextLine();
						dbAccess = new CipherKeyStorage(thePath);
					}
					toDo = askAction(1);
				}
				else
				{
					System.out.println("Creating and setting to use the default database.  ");
					dbAccess = new CipherKeyStorage();
					System.out.println("Default database created successfully.  ");
					toDo = askAction(2);
				}
				if(toDo == 1) //Decrypt
				{
					int keyType = 0;
					while((keyType < 1) || (keyType > 7))
					{
						System.out.println("Enter 1 if the file was encrypted using AES-GCM.  Enter 2 if the file \n"
										   + "was encrypted using AES-CBC.  Enter 3 if the file was encrypted using \n"
										   + "Affine cipher.  Enter 4 if the file was encrypted using Caeser \n"
										   + "cipher.  Enter 5 if the file was encrypted using One Time Pad.  Enter \n"
										   + "6 if the file was encrypted using Rail Fence cipher.   Enter 7 if the \n"
										   + "file was encrypted using Vigenere cipher.  :  ");
						keyType = userInput.nextInt();
					}
					if(encryptDecrypt(2, keyType))
						System.out.println("Decryption done successfuly.  ");
				}
				else if(toDo == 2) //Encrypt
				{
					int keyType = 0;
					while((keyType < 1) || (keyType > 7))
					{
						System.out.println("Enter 1 if you want to encrypt the file using AES-GCM.  Enter 2 if you \n"
										   + "want to encrypt the file using AES-CBC.  Enter 3 if you want to encrypt \n"
										   + "the file using Affine cipher.  Enter 4 if you want to encrypt the file \n"
										   + "using Caeser cipher.  Enter 5 if you want to encrypt the file using \n"
										   + "One Time Pad.  Enter 6 if you want to encrypt the file using Rail Fence \n"
										   + "cipher.  Enter 7 if you want to encrypt the file using Vigenere cipher.  :  ");
						keyType = userInput.nextInt();
					}
					if(encryptDecrypt(1, keyType))
						System.out.println("Encryption done successfuly.  ");
				}
				else if(toDo == 3) //Delete
				{
					int keyType = 0;
					while((keyType < 1) || (keyType > 7))
					{
						System.out.println("Enter 1 if you want to delete a key that is of type AES-GCM.  Enter 2 if \n"
										   + "you want to delete a key that is of type AES-CBC.  Enter 3 if you want \n"
										   + "to delete a key that is of type Affine.  Enter 4 if you want to delete \n"
										   + "a key that is of type Caeser.  Enter 5 if you want to delete a key that \n"
										   + "is of type One Time Pad.  Enter 6 if you want to delete a key that is \n"
										   + "of type Rail Fence.  Enter 7 if you want to delete a key that is of type \n"
										   + "Vigenere.  :  ");
						keyType = userInput.nextInt();
					}
					while(userInput.hasNextLine())
					{
						String toIgnore = userInput.nextLine(); //Flush any /n characters that may remain from next ints.  
					}
					System.out.println("Enter the name of the file (with path if file is not located in the same \n"
									   + "directory as the program).  :  ");
					dbAccess.removeKey(userInput.nextLine(), keyType);
				}
				else if(toDo == 4) //Add key
				{
					int keyType = 0;
					while((keyType < 1) || (keyType > 7))
					{
						System.out.println("Enter 1 if the key you want to add is an AES-GCM key.  Enter 2 if the key \n"
										   + "you want to add is an AES-CBC key.  Enter 3 if the key you want to add \n"
										   + "is an Affine key.  Enter 4 if the key you want to add is an Caeser key.  \n"
										   + "Enter 5 if the key you want to add is an One Time Pad key.  Enter 6 if \n"
										   + "the key you want to add is an Rail Fence key.  Enter 7 if the key you \n"
										   + "want to add is an Vigenere key.  :  ");
						keyType = userInput.nextInt();
					}
					if(addKey(keyType))
						System.out.println("Key added to db successfuly.  ");
				}
				else //Close db
					break;
				if((input == 3) && ((toDo == 2) || (toDo == 4))) //Something has been added to the newly created default db.  
				{
					input = 1; //More options now available.  
					defaultExists = true;
				}
			}
			if(input == 4)
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
				System.out.println("Enter 1 to decrypt a file.  Enter 2 to encrypt a file.  Enter 3 to remove \n"
								   + "a key.  Enter 4 to enter a key manually.  Note that this program does \n"
								   + "not yet allow the simulatanious use of two different key databases.  \n"
								   + "To use another key database enter 5. :  ");
				inputSelection = userInput.nextInt();
			}
		}
		else //No keys in db
		{
			while(((inputSelection < 2) || (inputSelection == 3)) || (inputSelection > 5))
			{
				System.out.println("Enter 2 to encrypt a file.  Enter 4 to enter a key manually.  Note that \n"
								   + "this program does not yet allow the simulatanious use of two \n"
								   + "different key databases.  To use another key database enter 5.  :  ");
				inputSelection = userInput.nextInt();
			}
		}
		userInput.close();
		return inputSelection;
	}
	
	private static boolean addKey(int keyType)
	{
		Scanner userInput = new Scanner(System.in);
		InheritableKey theKey = null;
		if(keyType == 1) //Create AES-GCM key.  
		{
			System.out.println("Enter the authentication tag length.  :  ");
			int authTagLength = userInput.nextInt();
			while(userInput.hasNextLine()) //Flush any /n characters that may remain from next ints.  
			{
				String clearNewLines = userInput.nextLine();
			}
			System.out.println("Enter the Base-64 string of the initialization vector.  :  ");
			byte[] initVect = Base64.getDecoder().decode(userInput.nextLine());
			System.out.println("Enter the Base-64 string of the secret key.  :  ");
			byte[] secKeyBytes = Base64.getDecoder().decode(userInput.nextLine());
			SecretKey secKey = new SecretKeySpec(secKeyBytes, 0, secKeyBytes.length, "AES");
			theKey = new BlockKey(secKey, initVect, authTagLength);
			theKey.setComponents();
		}
		else if(keyType == 2) //Create AES-CBC key.  
		{
			while(userInput.hasNextLine()) //Flush any /n characters that may remain from next ints.  
			{
				String clearNewLines = userInput.nextLine();
			}
			System.out.println("Enter the Base-64 string of the initialization vector.  :  ");
		}
		else if(keyType == 3) //Create Affine key.  
		{
			
		}
		else if(keyType == 4) //Create Caeser key.  
		{
			
		}
		else if(keyType == 5) //Create One Time Pad key.  
		{
			
		}
		else if(keyType == 6) //Create Rail Fence key.  
		{
			
		}
		else //Create Vigenere key.  
		{
			
		}
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

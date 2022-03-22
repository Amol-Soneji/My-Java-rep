import CipherKeys.*;
import CipherPackage.*;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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
			String clearNewLines = userInput.nextLine(); //Flush any /n characters that may remain from next ints
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
			System.out.println("Enter the Base-64 string of the initialization vector.  :  ");
			byte[] initVect = Base64.getDecoder().decode(userInput.nextLine());
			System.out.println("Enter the Base-64 string of the secret key.  :  ");
			byte[] secKeyBytes = Base64.getDecoder().decode(userInput.nextLine());
			SecretKey secKey = new SecretKeySpec(secKeyBytes, 0, secKeyBytes.length, "AES");
			theKey = new BlockKey(secKey, initVect);
			theKey.setComponents();
		}
		else if(keyType == 3) //Create Affine key.  
		{
			System.out.println("Enter the Affine key value.  :  ");
			int affKey = userInput.nextInt();
			System.out.println("Enter the Affine decreyption key value.  :  ");
			int affDecKey = userInput.nextInt();
			System.out.println("Enter the arbitrary x value.  :  ");
			int arbX = userInput.nextInt();
			theKey = new SubstitutionKey(affKey, affDecKey, arbX);
			theKey.setComponents();
		}
		else if(keyType == 4) //Create Caeser key.  
		{
			System.out.println("Enter the Caeser key value.  :  ");
			int keyVal = userInput.nextInt();
			theKey = new SubstitutionKey(keyVal);
			theKey.setComponents();
		}
		else if(keyType == 5) //Create One Time Pad key.  
		{
			System.out.println("Enter the Base-64 string value of the One Time Pad key.  :  ");
			byte[] keyBytes = Base64.getDecoder().decode(userInput.nextLine());
			System.out.println("Enter the text length.  :  ");
			int textLen = userInput.nextInt();
			theKey = new OneTimePadKey(keyBytes, textLen);
			theKey.setComponents();
		}
		else if(keyType == 6) //Create Rail Fence key.  
		{
			System.out.println("Enter the key value of the Rail Fence key.  :  ");
			int keyVal = userInput.nextInt();
			int usePunctInt = 0;
			while((usePunctInt < 1) || (usePunctInt > 2))
			{
				System.out.println("Enter 1 if the key is to be used with files that have punctuation or other \n"
							   	   + "characters.  Enter 2 if otherwise.  :  ");
				usePunctInt = userInput.nextInt();
			}
			boolean usePunct;
			if(usePunctInt == 1)
				usePunct = true;
			else
				usePunct = false;
			theKey = new TranspositionKey(keyVal, usePunct);
			theKey.setComponents();
		}
		else //Create Vigenere key.  
		{
			System.out.println("Enter the key string for the Vigenere key.  :  ");
			String keyVal = userInput.nextLine();
			int usePunctInt = 0;
			while((usePunctInt < 1) || (usePunctInt > 2))
			{
				System.out.println("Enter 1 if the key is to be used with files that have punctuation or other \n"
								   + "characters.  Enter 2 if otherwise.  :  ");
				usePunctInt = userInput.nextInt();
			}
			boolean usePunct;
			if(usePunctInt == 1)
				usePunct = true;
			else
				usePunct = false;
			theKey = new PolyalphabeticKey(keyVal, usePunct);
			theKey.setComponents();
		}
		while(userInput.hasNextLine())
		{
			String clearNewLines = userInput.nextLine(); //Flush any /n characters that may remain from next ints
		}
		System.out.println("Enter the name/path of the file that this key is to be associated with.  :  ");
		String docName = userInput.nextLine();
		dbAccess.addKey(theKey, keyType, docName);
		return true;
	}
	
	private static boolean encryptDecrypt(int option, int keyType)
	{
		Scanner userInput = new Scanner(System.in);
		if(option == 1)
			System.out.println("Enter the name of the file/path to be encrypted.  :  ");
		else
			System.out.println("Enter the name of the file/path to be decrypted.  :  ");
		String inputFileName = userInput.nextLine();
		try
		{
			//First try to see if file exists.  
			File testExists = new File(inputFileName);
			if(!testExists.isFile())
				return false;
			if(option == 1)
			{
				String encryptedFileName = inputFileName.substring(0, inputFileName.indexOf(".")) 
										   + "Encrypted" + inputFileName.substring(inputFileName.indexOf("."));
				int outputType = 0, usePunctuation = 0;
				if(((keyType > 2) && (keyType < 5)) || (keyType > 5))
				{
					Scanner outputTypeInput = new Scanner(System.in);
					while((outputType < 1) || (outputType > 2))
					{
						System.out.println("Enter 1 if you would like a normal encryption process.  Enter \n"
									   	   + "2 if you would like the output cipher text in char-code \n"
									   	   + "integers.  This method is more reliable as it can be used \n"
									   	   + "to write non-printable characters by numeric char-codes.  \n"
									   	   + "Do note however that this method will lead to a much larger \n"
									   	   + "encrypted file.  :  ");
						outputType = outputTypeInput.nextInt();
					}
				}
				if(keyType > 5)
				{
					Scanner usePunctuationInput = new Scanner(System.in);
					while((usePunctuation < 1) || (usePunctuation > 2))
					{
						System.out.println("Enter 1 if you would like to keep punctuations during encryption \n"
										   + "and decryption.  Enter 2 if you don't want to.  :  ");
						usePunctuation = usePunctuationInput.nextInt();
					}
				}
				System.out.println("Creating new key for file to be encrypted.  ");
				switch(keyType)
				{
					case 1: //Create an encrypted file using AES-GCM.  
					{
						BlockKey theKey = new BlockKey(true);
						System.out.println("Reading content of file to be encrypted.  ");
						BlockCipher theCipher = new AESCipher(readFile(inputFileName), theKey, true);
						System.out.println("Encrypting.  Please wait.  ");
						String cipherText = theCipher.compute();
						System.out.println("Creating new encrypted file.  ");
						if(!writeFile(encryptedFileName, cipherText))
							throw new Exception();
						System.out.println("Created new encrypted file.  Now adding key to database.  ");
						InheritableKey keyToAdd = theKey;
						keyToAdd.setComponents();
						dbAccess.addKey(keyToAdd, keyType, encryptedFileName);
					}
						break;
					case 2: //Create an encrypted file using AES-CBC
					{
						BlockKey theKey = new BlockKey(false);
						System.out.println("Reading content of the file to be encrypted.  ");
						BlockCipher theCipher = new AESCipher(readFile(inputFileName), theKey, true);
						System.out.println("Encrypting.  Please wait.  ");
						String cipherText = theCipher.compute();
						System.out.println("Creating new encrypted file.  ");
						if(!writeFile(encryptedFileName, cipherText))
							throw new Exception();
						System.out.println("Created new encrypted file.  Now adding key to database.  ");
						InheritableKey keyToAdd = theKey;
						keyToAdd.setComponents();
						dbAccess.addKey(keyToAdd, keyType, encryptedFileName);
					}
						break;
					case 3: //Create an encrypted file using Affine
					{
						SubstitutionKey theKey = new SubstitutionKey(true);
						System.out.println("Reading content of the file to be encrypted.  ");
						SubstitutionCipher theCipher = new AffineCipher(readFile(inputFileName), theKey, true);
						System.out.println("Encrypting.  Please wait.  ");
						String cipherText = "";
						if(outputType == 1)
							cipherText = theCipher.compute(true);
						else
							cipherText = theCipher.compute(false);
						System.out.println("Creating new encrypted file.  ");
						if(!writeFile(encryptedFileName, cipherText))
							throw new Exception();
						System.out.println("Created new encrypted file.  Now adding key to database.  ");
						InheritableKey keyToAdd = theKey;
						keyToAdd.setComponents();
						dbAccess.addKey(keyToAdd, keyType, encryptedFileName);
					}
						break;
					case 4: //Create an encrypted file using Caeser
					{
						SubstitutionKey theKey = new SubstitutionKey(false);
						System.out.println("Reading the content of the file to be encrypted.  ");
						System.out.println(theKey.getKeyVal());
						SubstitutionCipher theCipher = new CaeserCipher(readFile(inputFileName), theKey, true);
						System.out.println("Encrypting.  Please wait.  ");
						String cipherText = "";
						if(outputType == 1)
							cipherText = theCipher.compute(true);
						else
							cipherText = theCipher.compute(false);
						System.out.println("Creating new encrypted file.  ");
						if(!writeFile(encryptedFileName, cipherText))
							throw new Exception();
						System.out.println("Created new encrypted file.  Now adding key to database.  ");
						InheritableKey keyToAdd = theKey;
						keyToAdd.setComponents();
						dbAccess.addKey(keyToAdd, keyType, encryptedFileName);
					}
						break;
					case 5: //Create an encrypted file using One Time Pad
					{
						System.out.println("Reading the contents of the file to be encrypted.  ");
						String fileContents = readFile(inputFileName);
						int contentLength = fileContents.length();
						OneTimePadKey theKey = new OneTimePadKey(contentLength);
						OneTimePadCipher theCipher = new OneTimePadCipher(fileContents, theKey, true);
						System.out.println("Encrypting.  Please wait.  ");
						String cipherText = theCipher.compute(true);
						System.out.println("Creating new encrypted file.  ");
						if(!writeFile(encryptedFileName, cipherText))
							throw new Exception();
						System.out.println("Created new encrypted file.  Now adding key to database.  ");
						InheritableKey keyToAdd = theKey;
						keyToAdd.setComponents();
						dbAccess.addKey(keyToAdd, keyType, encryptedFileName);
					}
						break;
					case 6: //Create an encrypted file using Rail Fence
					{
						TranspositionKey theKey = null;
						if(usePunctuation == 1)
							theKey = new TranspositionKey(true);
						else
							theKey = new TranspositionKey(false);
						System.out.println("Reading the contents of the file to be encrypted.  ");
						RailFenceCipher theCipher = new RailFenceCipher(readFile(inputFileName), theKey, true);
						System.out.println("Encrypting.  Please wait.  ");
						String cipherText = "";
						if(outputType == 1)
							cipherText = theCipher.compute(true);
						else
							cipherText = theCipher.compute(false);
						System.out.println("Creating new encrypted file.  ");
						if(!writeFile(encryptedFileName, cipherText))
							throw new Exception();
						System.out.println("Created new encrypted file.  Now adding key to database.  ");
						InheritableKey keyToAdd = theKey;
						keyToAdd.setComponents();
						dbAccess.addKey(keyToAdd, keyType, encryptedFileName);
					}
						break;
					case 7: //Create an encrypted file using Vigenere
					{
						PolyalphabeticKey theKey = null;
						if(usePunctuation == 1)
							theKey = new PolyalphabeticKey(true);
						else
							theKey = new PolyalphabeticKey(false);
						System.out.println("Reading the contents of the file to be encrypted.  ");
						VigenereCipher theCipher = new VigenereCipher(readFile(inputFileName), theKey, true);
						System.out.println("Encrypting.  Please wait.  ");
						String cipherText = "";
						if(usePunctuation == 1)
							cipherText = theCipher.compute(true);
						else
							cipherText = theCipher.compute(false);
						System.out.println("Creating new encrypted file.  ");
						if(!writeFile(encryptedFileName, cipherText))
							throw new Exception();
						System.out.println("Created new encrypted file.  Now adding key to database.  ");
						InheritableKey keyToAdd = theKey;
						keyToAdd.setComponents();
						dbAccess.addKey(keyToAdd, keyType, encryptedFileName);
					}
						break;
				}
			}
			else
			{
				String decryptedFileName = inputFileName.substring(0, inputFileName.indexOf("Encrypted"))
										   + inputFileName.substring(inputFileName.indexOf("."));
				int encryptedTextTypeInt = 0;
				if(((keyType > 2) && (keyType < 5)) || (keyType > 5))
				{
					Scanner textTypeInput = new Scanner(System.in);
					while((encryptedTextTypeInt < 1) || (encryptedTextTypeInt > 2))
					{
						System.out.println("Enter 1 if the cipher text is in normal Base-64 format.  Else enter 2 if the \n"
									   	   + "cipher text is in char-code format.  ");
						encryptedTextTypeInt = textTypeInput.nextInt();
					}
				}
				boolean encryptedTextType = true;
				if(encryptedTextTypeInt == 2)
					encryptedTextType = false;
				System.out.println("Getting key to decrypt file.  ");
				switch(keyType)
				{
					case 1: //Decrypt using AES-GCM.  
					{
						BlockKey theKey = new BlockKey(dbAccess.getKey(inputFileName, keyType).getComponents());
						System.out.println("Reading contents of encrypted file.  ");
						AESCipher theCipher = new AESCipher(readFile(inputFileName), theKey, false);
						System.out.println("Decrypting the encrypted file contents.  ");
						String plainText = theCipher.compute();
						System.out.println("Creating the plain text file.  ");
						writeFile(decryptedFileName, plainText);
					}
						break;
					case 2: //Decrypt using AES-CBC.  
					{
						BlockKey theKey = new BlockKey(dbAccess.getKey(inputFileName, keyType).getComponents());
						System.out.println("Reading contents of the encrypted file.  ");
						AESCipher theCipher = new AESCipher(readFile(inputFileName), theKey, false);
						System.out.println("Decrypting the encrypted file contents.  ");
						String plainText = theCipher.compute();
						System.out.println("Creating the plain text file.  ");
						writeFile(decryptedFileName, plainText);
					}
						break;
					case 3: //Decrypt using Affine.  
					{
						SubstitutionKey theKey = new SubstitutionKey(dbAccess.getKey(inputFileName, keyType).getComponents());
						System.out.println("Reading contents of the encrypted file.  ");
						AffineCipher theCipher = new AffineCipher(readFile(inputFileName), theKey, false);
						System.out.println("Decrypting the encrypted file contents.  ");
						String plainText = theCipher.compute(encryptedTextType);
						System.out.println("Creating the plain text file.  ");
						writeFile(decryptedFileName, plainText);
					}
						break;
					case 4: //Decrypt using Caeser.  
					{
						SubstitutionKey theKey = new SubstitutionKey(dbAccess.getKey(inputFileName, keyType).getComponents());
						System.out.println("Reading contents of the encrypted file.  ");
						CaeserCipher theCipher = new CaeserCipher(readFile(inputFileName), theKey, false);
						System.out.println("Decrypting the encrypted file contents.  ");
						String plainText = theCipher.compute(encryptedTextType);
						System.out.println("Creating the plain text file.  ");
						writeFile(decryptedFileName, plainText);
					}
						break;
					case 5: //Decrypt using One Time Pad.  
					{
						OneTimePadKey theKey = new OneTimePadKey(dbAccess.getKey(inputFileName, keyType).getComponents());
						System.out.println("Reading contents of the encrypted file.  ");
						OneTimePadCipher theCipher = new OneTimePadCipher(readFile(inputFileName), theKey, false);
						System.out.println("Decrypting the encrypted file contents.  ");
						String plainText = theCipher.compute(true);
						System.out.println("Creating the plain text file.  ");
						writeFile(decryptedFileName, plainText);
					}
						break;
					case 6: //Decrypt using Rail Fence.  
					{
						TranspositionKey theKey = new TranspositionKey(dbAccess.getKey(inputFileName, keyType).getComponents());
						System.out.println("Reading contents of the encrypted file.  ");
						RailFenceCipher theCipher = new RailFenceCipher(readFile(inputFileName), theKey, false);
						System.out.println("Decrypting the encrypted file contents.  ");
						String plainText = theCipher.compute(encryptedTextType);
						System.out.println("Creating the plain text file.  ");
						writeFile(decryptedFileName, plainText);
					}
						break;
					case 7: //Decrypt using Vigenere.  
					{
						PolyalphabeticKey theKey = new PolyalphabeticKey(dbAccess.getKey(inputFileName, keyType).getComponents());
						System.out.println("Reading contents of the encrypted file.  ");
						VigenereCipher theCipher = new VigenereCipher(readFile(inputFileName), theKey, false);
						System.out.println("Decrypting the encrypted file contents.  ");
						String plainText = theCipher.compute(encryptedTextType);
						System.out.println("Creating the plain text file.  ");
						writeFile(decryptedFileName, plainText);
					}
						break;
				}
			}
		}
		//Catch anything else.  
		catch(Exception someException)
		{
			someException.printStackTrace();
			System.out.print("There was an internal error with the program, and thus the requested \n"
							 + "action might not have been completed.  \n");
			return false;
		}
		return true;
	}
	
	private static String readFile(String fileName)
	{
		String fileContents = "";
		try
		{
			File fileToRead = new File(fileName);
			Scanner fileReader = new Scanner(fileToRead, "UTF-16");
			while(fileReader.hasNextLine())
			{
				fileContents = fileContents + fileReader.nextLine() + "\n";
			}
			fileReader.close();
		}
		catch(IllegalArgumentException e)
		{
			e.printStackTrace();
			System.out.println("There has been an error in reading the file.  \n"
							   + "Please run this program on a computer that supports UTF-16 encoding.  "
							   + "Exiting in 10 seconds.  ");
			for(int i = 0; i < 10000; i++)
			{
				//Do noting.  
			}
			System.exit(1);
		}
		catch(Exception someException)
		{
			someException.printStackTrace();
			System.out.println("There was an internal error in the program.  Exiting in 10 seconds.  ");
			for(int i = 0; i < 10000; i++)
			{
				//Do nothing.  
			}
			System.exit(1);
		}
		return fileContents;
	}
	
	private static boolean writeFile(String fileName, String contents)
	{
		try
		{
			File newFile = new File(fileName);
			PrintWriter fileWriter = new PrintWriter(newFile, "UTF-16");
			fileWriter.print(contents);
			fileWriter.close();
			return true;
		}
		catch(SecurityException e)
		{
			e.printStackTrace();
			System.out.println("There has been an error in creating the encrypted file.  \n"
							   + "The program has insuffecient privleges to create the file.  ");
			return false;
		}
		catch(UnsupportedEncodingException e)
		{
			e.printStackTrace();
			System.out.println("There has been an error in creating the encrypted file.  \n"
							   + "Please run this program on a computer that supports UTF-16 encoding.  ");
			return false;
		}
		catch(Exception someException)
		{
			someException.printStackTrace();
			System.out.print("There was an internal error with the program, and thus the program will \n"
							 + "close in 10 seconds.  \n");
			for(int i = 0; i < 10000; i++)
			{
				//Do nothing
			}
			dbAccess.closeDB();
			System.exit(0);
		}
		return false;
	}
}

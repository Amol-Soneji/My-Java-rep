/**
 * 
 */
package CipherPackage;

import java.util.stream.IntStream;
import java.io.UnsupportedEncodingException;
import CipherKeys.TranspositionKey;

/**
 * @author Amol Soneji
 *
 */
public class RailFenceCipher extends TranspositionCipher 
{
	
	private String[] cipherTextBuilder;
	private String[] plainTextBuilder;
	private String plainText;
	private byte[] cipherText;
	private boolean mode;
	private int textLength;
	private int requiredLengthsCheck;

	/**
	 * 
	 */
	public RailFenceCipher(String text, TranspositionKey key, boolean mode) 
	{
		this.mode = mode;
		if(mode) // True for encrypt.  
		{
			super.setKey(key);
			if(super.getPunct())  //Keep punctuation in result.  
			{
				plainText = text;
				textLength = text.length();
			}
			else  //Don't keep punctuation in result.  
			{
				IntStream textInts = text.codePoints();
				int[] intTextArray = textInts.toArray();
				for(int index = 0; index < text.length(); index++) 
				{
					if(Character.isLetter(intTextArray[index])) // Check if not punctuation.  
						plainText = plainText + String.copyValueOf(Character.toChars(intTextArray[index]));
				}
				textLength = plainText.length();
			}
			cipherTextBuilder = new String[key.getKeyVal()];
		}
		else  //Decrypt
		{
			try
			{
				cipherText = text.getBytes("UTF-16");
			}
			catch(UnsupportedEncodingException e)
			{
				e.printStackTrace();
				System.out.println("Please use a computer that supports UTF-16 to use this program.  ");
				System.out.println("Program exiting in 10 seconds.  ");
				for(int i = 0; i < 10000; i++);
				{
					//Do nothing.  
				}
				System.exit(1);
			}
			super.setKey(key);
			textLength=text.length();
			plainTextBuilder = new String[key.getKeyVal()];
		}
	}
	
	public RailFenceCipher(byte[] text, TranspositionKey key, boolean mode) 
	{
		this.mode = mode;
		super.setKey(key);
		if(mode) // True for encrypt.  
			System.out.println("Program does not currently support making cipher text via byte input for Rail Fence Cipher.  ");
		else  //Decrypt
		{
			cipherText = text;
			textLength=text.length;
			plainTextBuilder = new String[key.getKeyVal()];
		}
	}

	@Override
	public byte[] compute(boolean resultType) throws InvalidCipherTextException
	{
		if(mode) // True means encryption mode.  
		{
			try
			{
				if(resultType) // True means to return a cipher text of characters.  
					return encrypt().getBytes("UTF-16");
				else // Return a String of integers, where each integer represents the code point for each of the Cipher text characters, with the exception of /n special character.  
					return encryptCharCodes().getBytes("UTF-16");
			}
			catch(UnsupportedEncodingException e)
			{
				e.printStackTrace();
				System.out.println("Please use a computer that supports UTF-16 to use this program.  ");
				System.out.println("Program exiting in 10 seconds.  ");
				for(int i = 0; i < 10000; i++);
				{
					//Do nothing.  
				}
				System.exit(1);
			}
			byte[] nullBytes = null; //Added because, 
			return nullBytes; //compiler complains since it thinks that nothing will get returned and not acknowledge the exit() if exception is caught.  
		}
		else // False means decryption mode.  
		{
			if(resultType) // True means that the result of decryption does not involve converting code points.  
				return decrypt();
			else // The decryption process will require converting code points of cipher text characters back to characters before decryption.  
			{
				textLength =  cipherText.length; //cipherText.split(" ").length;
				return decryptCharCodes();
			}
		}
	}

	@Override
	protected String encrypt() 
	{
		int currentRail = 0;
		boolean direction = false; // False indicates the next rail will be bellow where as true means a rail above.  
		int index = 0;
		while(index < textLength) 
		{
			for(int rail = 0; (rail < super.getKey().getKeyVal()) && (index < textLength); rail++) 
			{
				cipherTextBuilder[rail] = cipherTextBuilder[rail] + plainText.substring(index, index + 1);
				currentRail = rail;
				direction = false;
				index++;
			}
			for(int rail = super.getKey().getKeyVal() - 2; (rail > 0) && (index < textLength); rail--) 
			{
				cipherTextBuilder[rail] = cipherTextBuilder[rail] + plainText.substring(index, index + 1);
				currentRail = rail;
				direction = true;
				index++;
			}
		}
		while(currentRail < super.getKey().getKeyVal() - 1) // Padding is needed to make the length a multiple of 2*(rails-1).  
		{
			while((currentRail < super.getKey().getKeyVal()) && (!direction)) 
			{
				cipherTextBuilder[currentRail + 1] = cipherTextBuilder[currentRail + 1] + " "; // Use blank space as padding.  
				currentRail++;
			}
			while(!(currentRail < super.getKey().getKeyVal() - 1) && (currentRail > 0) && direction) 
			{
				cipherTextBuilder[currentRail - 1] = cipherTextBuilder[currentRail - 1] + " "; // Use blank space as padding.  
				currentRail--;
				if(currentRail == 0)
					direction = false;
			}
		}
		String cipherString = "";
		for(int rail = 0; rail < super.getKey().getKeyVal(); rail++) 
		{
			cipherString = cipherString + cipherTextBuilder[rail];
		}
		return cipherString;
	}

	@Override
	protected String encryptCharCodes() 
	{
		int nextRailPointer = 0;
		boolean direction = false;
		int index = 0;
		while(index < textLength - 1) 
		{
			for(int rail = 0; (rail < super.getKey().getKeyVal()) && (index < textLength - 1); rail++) 
			{
				cipherTextBuilder[rail] = cipherTextBuilder[rail] + String.valueOf(plainText.codePointAt(index)) + " ";
				if(rail == super.getKey().getKeyVal() - 1) // This is for determining which rail the next character will be in.  
				{
					nextRailPointer = rail - 1;
					direction = true;
				}
				else 
				{
					nextRailPointer = rail + 1;
					direction = false;
				}
				index++;
			}
			for(int rail = super.getKey().getKeyVal() - 2; rail > 0; rail--) 
			{
				cipherTextBuilder[rail] = cipherTextBuilder[rail] + String.valueOf(plainText.codePointAt(index)) + " ";
				nextRailPointer = rail - 1;
				index++;
			}
		}
		cipherTextBuilder[nextRailPointer] = cipherTextBuilder[nextRailPointer] + String.valueOf(plainText.codePointAt(textLength - 1));
		if((nextRailPointer == 0) || (!direction && (nextRailPointer != super.getKey().getKeyVal() - 1))) // Convert nextRailPointer into currentRailPointer
		{
			nextRailPointer++;
			direction = false;
		}
		else if((nextRailPointer != super.getKey().getKeyVal() - 1) && (direction && (nextRailPointer < 0))) 
			nextRailPointer--;
		else
			nextRailPointer = nextRailPointer; // Do nothing.  
		int currentRail = nextRailPointer;
		while(currentRail < super.getKey().getKeyVal() - 1)  // Padding is needed to make length a multiple of 2*(rail-1).  
		{
			while((currentRail < super.getKey().getKeyVal()) && !(direction)) 
			{
				cipherTextBuilder[currentRail] = cipherTextBuilder[currentRail] + String.valueOf(" ".codePointAt(0));
				currentRail++;
			}
			while(!(currentRail < super.getKey().getKeyVal() - 1) && (currentRail > 0) && direction) 
			{
				cipherTextBuilder[currentRail] = cipherTextBuilder[currentRail] + String.valueOf(" ".codePointAt(0));
				currentRail--;
				if(currentRail == 0)
					direction = false;
			}
		}
		String cipherString = "";
		for(int rail = 0; rail < super.getKey().getKeyVal(); rail++) 
		{
			cipherString = cipherString + cipherTextBuilder[rail];
		}
		return cipherString;
	}

	@Override
	protected byte[] decrypt() throws InvalidCipherTextException
	{
		try
		{
			String cipherString = new String(cipherText, "UTF-16");
			textLength = cipherString.length();
			if((textLength % (2 * (super.getKey().getKeyVal() - 1))) != 0)
				throw new InvalidCipherTextException("Ciphertext length is not a multiple of 2(key-1).  ");
			int firstAndLastRailLength = textLength / (2 * (super.getKey().getKeyVal() - 1));
			requiredLengthsCheck = 2 * firstAndLastRailLength;
			plainTextBuilder[0] = cipherString.substring(0, firstAndLastRailLength);
			int pointer = firstAndLastRailLength;
			for(int index = 1; index < super.getKey().getKeyVal() - 1; index++) 
			{
				plainTextBuilder[index] = cipherString.substring(pointer, pointer + requiredLengthsCheck);
				pointer = pointer + requiredLengthsCheck;
			}
			plainTextBuilder[super.getKey().getKeyVal() - 1] = cipherString.substring(pointer);
			boolean direction = false; // False for next rail being in the rail bellow, and True for next rail being above.  
			int topRailPointer = 0;
			int midPointer = 0;
			int bottomRailPointer = 0;
			int index = 0;
			int nextRailPointer = 0;
			while(index < textLength) 
			{
				while((nextRailPointer < super.getKey().getKeyVal()) && !direction) 
				{
					if(nextRailPointer == 0) 
					{
						plainText = plainText + plainTextBuilder[nextRailPointer].substring(topRailPointer, topRailPointer + 1);
						topRailPointer++;
						nextRailPointer++;
					}
					else if(nextRailPointer == (super.getKey().getKeyVal() - 1))
					{
						plainText = plainText + plainTextBuilder[nextRailPointer].substring(bottomRailPointer, bottomRailPointer + 1);
						midPointer++; // midPointer can now be incremented, as this iteration of mid rails has already been completed.  
						bottomRailPointer++;
						nextRailPointer--;
						direction = true;
					}
					else
					{
						plainText = plainText + plainTextBuilder[nextRailPointer].substring(midPointer, midPointer + 1);
						nextRailPointer++;
					}
					index++;
				}
				while((nextRailPointer > 0) && direction && (index < textLength)) // The index conditional is because the code can be able to go into this while loop even if the last character has been done.  
				{
					if(nextRailPointer == 1)
					{
						plainText = plainText + plainTextBuilder[nextRailPointer].substring(midPointer, midPointer + 1);
						midPointer++; // midPointer can now be incremented, as this was the last iteration of mid rails, in this direction.  
						nextRailPointer--;
						direction = false;
					}
					else
					{
						plainText = plainText + plainTextBuilder[nextRailPointer].substring(midPointer, midPointer + 1);
						nextRailPointer--;
					}
					index++;
				}
			}
			return plainText.getBytes("UTF-16");
		}
		catch(UnsupportedEncodingException e)
		{
			e.printStackTrace();
			System.out.println("Please use a computer that supports UTF-16 to use this program.  ");
			System.out.println("Program exiting in 10 seconds.  ");
			for(int i = 0; i < 10000; i++);
			{
				//Do nothing.  
			}
			System.exit(1);
		}
		byte[] nullBytes = null; //Added because, 
		return nullBytes; //compiler complains since it thinks nothing is being returned.  
	}

	@Override
	protected byte[] decryptCharCodes() throws InvalidCipherTextException
	{
		if((textLength % (2 * (super.getKey().getKeyVal() - 1))) != 0)
			throw new InvalidCipherTextException("Ciphertext length is not a multiple of 2(key-1).  ");
		String[] cipherArray = null;
		try
		{
			String cipherString = new String(cipherText, "UTF-16");
			cipherArray = cipherString.split(" ");
		}
		catch(UnsupportedEncodingException e)
		{
			e.printStackTrace();
			System.out.println("Please use a computer that supports UTF-16 to use this program.  ");
			System.out.println("Program exiting in 10 seconds.  ");
			for(int i = 0; i < 10000; i++);
			{
				//Do nothing.  
			}
			System.exit(1);
		}
		textLength = cipherArray.length;
		int firstAndLastRailLength = textLength / (2 * (super.getKey().getKeyVal() - 1));
		requiredLengthsCheck = 2 * firstAndLastRailLength;
		int cipherTextPositionPointer = 0;
		for(int rail = 0; rail < super.getKey().getKeyVal(); rail++)
		{
			while((rail == 0) && (cipherTextPositionPointer < firstAndLastRailLength))
			{
				plainTextBuilder[rail] = plainTextBuilder[rail] + Character.toString(Integer.parseInt(cipherArray[cipherTextPositionPointer]));
				cipherTextPositionPointer++;
			}
			while(((rail != 0) && (rail < super.getKey().getKeyVal() - 1)) && (cipherTextPositionPointer < (firstAndLastRailLength + (rail * requiredLengthsCheck))))
			{
				plainTextBuilder[rail] = plainTextBuilder[rail] + Character.toString(Integer.parseInt(cipherArray[cipherTextPositionPointer]));
				cipherTextPositionPointer++;
			}
			while((rail == super.getKey().getKeyVal() - 1) && (cipherTextPositionPointer < textLength))
			{
				plainTextBuilder[rail] = plainTextBuilder[rail] + Character.toString(Integer.parseInt(cipherArray[cipherTextPositionPointer]));
				cipherTextPositionPointer++;
			}
		}
		int firstRailPointer = 0;
		int midRailPointer = 0;
		int lastRailPointer = 0;
		int rail = 0;
		boolean direction = false; // False indicates the next rail to go to will be the rail bellow, while True indicates the next rail is above.  
		int index = 0;
		while(index < textLength)
		{
			while((!direction) && (rail < super.getKey().getKeyVal()))
			{
				if(rail == 0)
				{
					plainText = plainText + plainTextBuilder[rail].substring(firstRailPointer, firstRailPointer + 1);
					firstRailPointer++;
					rail++;
					index++;
				}
				else if(rail == super.getKey().getKeyVal() - 1)
				{
					plainText = plainText + plainTextBuilder[rail].substring(lastRailPointer, lastRailPointer + 1);
					midRailPointer++; // Now increment midRailPointer, as the current iteration of middle rails has been completed.  
					lastRailPointer++;
					rail--;
					index++;
					direction = true;
				}
				else
				{
					plainText = plainText + plainTextBuilder[rail].substring(midRailPointer, midRailPointer + 1);
					rail++;
					index++;
				}
			}
			while(direction && (rail > 0) && (index < textLength)) // The reason for the index condition is because without it, this while loop can get activated even after all the characters have been done.  
			{
				if(rail > 1)
				{
					plainText = plainText + plainTextBuilder[rail].substring(midRailPointer, midRailPointer + 1);
					rail--;
					index++;
				}
				else
				{
					plainText = plainText + plainTextBuilder[rail].substring(midRailPointer, midRailPointer + 1);
					midRailPointer++; // Increment midRailPointer, as the current iteration of the middle rails has been completed.  
					rail--;
					index++;
					direction = false;
				}
			}
		}
		try
		{
			return plainText.getBytes("UTF-16");
		}
		catch(UnsupportedEncodingException e)
		{
			e.printStackTrace();
			System.out.println("Please use a computer that supports UTF-16 to use this program.  ");
			System.out.println("Program exiting in 10 seconds.  ");
			for(int i = 0; i < 10000; i++);
			{
				//Do nothing.  
			}
			System.exit(1);
		}
		byte[] nullByteArray = null; //Added because, 
		return nullByteArray; //compiler thinks nothing is being returned.  
	}
	
	//private boolean checkIfValid(String rail, int railSize) throws InvalidCipherTextException
	//{
		//
	//}
}

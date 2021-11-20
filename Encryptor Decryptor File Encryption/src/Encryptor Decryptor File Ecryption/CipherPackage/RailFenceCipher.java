/**
 * 
 */
package CipherPackage;

import java.util.stream.IntStream;
import java.util.ArrayList;
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
	private String cipherText;
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
			cipherText = text;
			super.setKey(key);
			textLength=text.length();
			plainTextBuilder = new String[key.getKeyVal()];
		}
	}

	@Override
	public String compute(boolean resultType) throws InvalidCipherTextException
	{
		if(mode) // True means encryption mode.  
		{
			if(resultType) // True means to return a cipher text of characters.  
				return encrypt();
			else // Return a String of integers, where each integer represents the code point for each of the Cipher text characters, with the exception of /n special character.  
				return encryptCharCodes();
		}
		else // False means decrption mode.  
		{
			if(resultType) // True means that the result of decryption does not involve converting code points.  
				return decrypt();
			else // The decryption process will require converting code points of cipher text characters back to characters before decryption.  
			{
				textLength = cipherText.split(" ").length;
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
		for(int rail = 0; rail < super.getKey().getKeyVal(); rail++) 
		{
			cipherText = cipherText + cipherTextBuilder[rail];
		}
		return cipherText;
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
				cipherTextBuilder[rail] = cipherTextBuilder[rail] + String.valueOf(cipherText.codePointAt(index)) + " ";
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
				cipherTextBuilder[rail] = cipherTextBuilder[rail] + String.valueOf(cipherText.codePointAt(index)) + " ";
				nextRailPointer = rail - 1;
				index++;
			}
		}
		cipherTextBuilder[nextRailPointer] = cipherTextBuilder[nextRailPointer] + String.valueOf(cipherText.codePointAt(textLength - 1));
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
		for(int rail = 0; rail < super.getKey().getKeyVal(); rail++) 
		{
			cipherText = cipherText + cipherTextBuilder[rail];
		}
		return cipherText;
	}

	@Override
	protected String decrypt() throws InvalidCipherTextException
	{
		if((textLength % (2 * (super.getKey().getKeyVal() - 1))) != 0)
			throw new InvalidCipherTextException("Ciphertext length is not a multiple of 2(key-1).  ");
		int firstAndLastRailLength = textLength / (2 * (super.getKey().getKeyVal() - 1));
		requiredLengthsCheck = 2 * firstAndLastRailLength;
		plainTextBuilder[0] = cipherText.substring(0, firstAndLastRailLength);
		int pointer = firstAndLastRailLength;
		for(int index = 1; index < super.getKey().getKeyVal() - 1; index++) 
		{
			plainTextBuilder[index] = cipherText.substring(pointer, pointer + requiredLengthsCheck);
			pointer = pointer + requiredLengthsCheck;
		}
		plainTextBuilder[super.getKey().getKeyVal() - 1] = cipherText.substring(pointer);
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
		return plainText;
	}

	@Override
	protected String decryptCharCodes() throws InvalidCipherTextException
	{
		if((textLength % (2 * (super.getKey().getKeyVal() - 1))) != 0)
			throw new InvalidCipherTextException("Ciphertext length is not a multiple of 2(key-1).  ");
		String[] cipherArray = cipherText.split(" ");
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
		return plainText;
	}
	
	//private boolean checkIfValid(String rail, int railSize) throws InvalidCipherTextException
	//{
		//
	//}
}

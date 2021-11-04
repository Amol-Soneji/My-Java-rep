/**
 * 
 */
package CipherPackage;

import java.util.stream.IntStream;
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
		if(mode) { // True for encrypt.  
			super.setKey(key);
			if(super.getPunct())  {  //Keep punctuation in result.  
				plainText = text;
				textLength = text.length();
			}
			else  {  //Don't keep punctuation in result.  
				IntStream textInts = text.codePoints();
				int[] intTextArray = textInts.toArray();
				for(int index = 0; index < text.length(); index++) {
					if(Character.isLetter(intTextArray[index])) // Check if not punctuation.  
						plainText = plainText + String.copyValueOf(Character.toChars(intTextArray[index]));
				}
				textLength = plainText.length();
			}
			cipherTextBuilder = new String[key.getKey()];
		}
		else {  //Decrypt
			cipherText = text;
			super.setKey(key);
			textLength = text.length();
			plainTextBuilder = new String[key.getKey()];
		}
	}

	@Override
	public String compute(boolean resultType) throws InvalidCipherTextException
	{
		if(mode) { // True means encryption mode.  
			if(resultType) // True means to return a cipher text of characters.  
				return encrypt();
			else // Return a String of integers, where each integer represents the code point for each of the Cipher text characters, with the exception of /n special character.  
				return encryptCharCodes();
		}
		else { // False means decrption mode.  
			if(resultType) // True means that the result of decryption does not involve converting code points.  
				return decrypt();
			else // The decryption process will require converting code points of cipher text characters back to characters before decryption.  
				return decryptCharCodes();
		}
	}

	@Override
	protected String encrypt() 
	{
		
	}

	@Override
	protected String encryptCharCodes() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String decrypt() throws InvalidCipherTextException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String decryptCharCodes() throws InvalidCipherTextException
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	private boolean checkIfValid(String rail, int railSize) throws InvalidCipherTextException
	{
		
	}
}
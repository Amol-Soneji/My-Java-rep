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
			cipherTextBuilder = new String[key.getKeyVal()];
		}
		else {  //Decrypt
			cipherText = text;
			super.setKey(key);
			textLength = text.length();
			plainTextBuilder = new String[key.getKeyVal()];
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
		int currentRail = 0;
		boolean direction = false; // False indicates the next rail will be bellow where as true means a rail above.  
		int index = 0;
		while(index < textLength) {
			for(int rail = 0; (rail < super.getKey().getKeyVal()) && (index < textLength); rail++) {
				cipherTextBuilder[rail] = cipherTextBuilder[rail] + plainText.substring(index, index + 1);
				currentRail = rail;
				direction = false;
				index++;
			}
			for(int rail = super.getKey().getKeyVal() - 2; (rail > 0) && (index < textLength); rail--) {
				cipherTextBuilder[rail] = cipherTextBuilder[rail] + plainText.substring(index, index + 1);
				currentRail = rail;
				direction = true;
				index++;
			}
		}
		while(currentRail < super.getKey().getKeyVal() - 1) { // Padding is needed to make the length a multiple of 2*(rails-1).  
			while((currentRail < super.getKey().getKeyVal()) && (!direction)) {
				cipherTextBuilder[currentRail + 1] = cipherTextBuilder[currentRail + 1] + " "; // Use blank space as padding.  
				currentRail++;
			}
			while(!(currentRail < super.getKey().getKeyVal() - 1) && (currentRail > 0) && direction) {
				cipherTextBuilder[currentRail - 1] = cipherTextBuilder[currentRail - 1] + " "; // Use blank space as padding.  
				currentRail--;
				if(currentRail == 0)
					direction = false;
			}
		}
		for(int rail = 0; rail < super.getKey().getKeyVal(); rail++) {
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
		while(index < textLength - 1) {
			for(int rail = 0; (rail < super.getKey().getKeyVal()) && (index < textLength - 1); rail++) {
				cipherTextBuilder[rail] = cipherTextBuilder[rail] + String.valueOf(cipherText.codePointAt(index)) + " ";
				if(rail == super.getKey().getKeyVal() - 1) { // This is for determining which rail the next character will be in.  
					nextRailPointer = rail - 1;
					direction = true;
				}
				else {
					nextRailPointer = rail + 1;
					direction = false;
				}
				index++;
			}
			for(int rail = super.getKey().getKeyVal() - 2; rail > 0; rail--) {
				cipherTextBuilder[rail] = cipherTextBuilder[rail] + String.valueOf(cipherText.codePointAt(index)) + " ";
				nextRailPointer = rail - 1;
				index++;
			}
		}
		cipherTextBuilder[nextRailPointer] = cipherTextBuilder[nextRailPointer] + String.valueOf(cipherText.codePointAt(textLength - 1));
		if((nextRailPointer == 0) || (!direction && (nextRailPointer != super.getKey().getKeyVal() - 1))) { // Convert nextRailPointer into currentRailPointer
			nextRailPointer++;
			direction = false;
		}
		else if((nextRailPointer != super.getKey().getKeyVal() - 1) && (direction && (nextRailPointer < 0))) 
			nextRailPointer--;
		else
			nextRailPointer = nextRailPointer; // Do nothing.  
		int currentRail = nextRailPointer;
		while(currentRail < super.getKey().getKeyVal() - 1) { // Padding is needed to make length a multiple of 2*(rail-1).  
			while((currentRail < super.getKey().getKeyVal()) && !(direction)) {
				cipherTextBuilder[currentRail] = cipherTextBuilder[currentRail] + String.valueOf(" ".codePointAt(0));
				currentRail++;
			}
			while(!(currentRail < super.getKey().getKeyVal() - 1) && (currentRail > 0) && direction) {
				cipherTextBuilder[currentRail] = cipherTextBuilder[currentRail] + String.valueOf(" ".codePointAt(0));
				currentRail--;
				if(currentRail == 0)
					direction = false;
			}
		}
		for(int rail = 0; rail < super.getKey().getKeyVal(); rail++) {
			cipherText = cipherText + cipherTextBuilder[rail];
		}
		return cipherText;
	}

	@Override
	protected String decrypt() throws InvalidCipherTextException
	{
		if((textLength % (2 * (super.getKey().getKeyVal() - 1))) != 0)
			throw new InvalidCipherTextException("Ciphertext length is not a multiple of 2(key-1).  ");
		String[] cipherGrid = new String[super.getKey().getKeyVal()];
		int firstAndLastRailLength = textLength / (2 * (super.getKey().getKeyVal() - 1));
		requiredLengthsCheck = 2 * firstAndLastRailLength;
		cipherGrid[0] = cipherText.substring(0, firstAndLastRailLength);
		int pointer = firstAndLastRailLength;
		for(int index = 1; index < super.getKey().getKeyVal() - 1; index++) {
			cipherGrid[index] = cipherText.substring(pointer, pointer + requiredLengthsCheck);
			pointer = pointer + requiredLengthsCheck;
		}
		cipherGrid[super.getKey().getKeyVal() - 1] = cipherText.substring(pointer);
		for(int index = 0; index < textLength; index++) {
			int railPointer = 0;
			while(railPointer < super.getKey().getKeyVal()) {
				plainTextBuilder[index = ]
			}
		}
	}

	@Override
	protected String decryptCharCodes() throws InvalidCipherTextException
	{
		
	}
	
	//private boolean checkIfValid(String rail, int railSize) throws InvalidCipherTextException
	//{
		//
	//}
}

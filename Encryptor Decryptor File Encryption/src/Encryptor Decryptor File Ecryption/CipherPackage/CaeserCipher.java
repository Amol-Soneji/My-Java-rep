/**
 * 
 */
package CipherPackage;

import CipherKeys.SubstitutionKey;

/**
 * @author Amol Soneji
 *
 */
public class CaeserCipher extends SubstitutionCipher 
{
	
	private String cipherTextBuilder;
	private String plainTextBuilder;
	private String cipherText;
	private String plainText;
	private boolean mode;

	/**
	 * 
	 */
	public CaeserCipher(String text, SubstitutionKey key, boolean mode) 
	{
		this.mode = mode;
		if(mode) //Encryption
			this.plainText = text;
		else //Decryption
			this.cipherText = text;
		super.setKey(key);
	}

	@Override
	public String compute(boolean resultType) 
	{
		if(resultType){ //Return the actual en/decrypted string and not a char code literal string.  
			if(mode)
				return encrypt();
			else
				return decrypt();
		}
		else { //Return a char code literal string of the encrypted string or return a decrypted String no longer showing char codes.  
			if(mode)
				return encryptCharCodes();
			else
				return decryptCharCodes();
		}
	}

	@Override
	protected String encrypt() 
	{
		for(int index = 0; index < plainText.length(); index++) {
			int charValue = plainText.codePointAt(index);
			charValue = (charValue + super.getKey().getKeyVal()) % 1112063;
			cipherTextBuilder = cipherTextBuilder + String.copyValueOf(Character.toChars(charValue));
		}
		return cipherTextBuilder;
	}

	@Override
	protected String encryptCharCodes() 
	{
		for(int index = 0; index < plainText.length(); index++) {
			int charValue = plainText.codePointAt(index);
			charValue = (charValue + super.getKey().getKeyVal()) % 1112063;
			if(index != (plainText.length() - 1))
				cipherTextBuilder = String.valueOf(charValue) + " ";
			else
				cipherTextBuilder = String.valueOf(charValue);
		}
		return cipherTextBuilder;
	}

	@Override
	protected String decrypt() 
	{
		for(int index = 0; index < cipherText.length(); index++) {
			int charValue = cipherText.codePointAt(index);
			charValue = (charValue - super.getKey().getKeyVal()) % 1112063;
			plainTextBuilder = plainTextBuilder + String.copyValueOf(Character.toChars(charValue));
		}
		return plainTextBuilder;
	}

	@Override
	protected String decryptCharCodes() 
	{
		String[] codes = plainText.split(" ");
		for(int index = 0; index < codes.length; index++) {
			int charValue = Integer.parseInt(codes[index]);
			charValue = (charValue - super.getKey().getKeyVal()) % 1112063;
			plainTextBuilder = plainTextBuilder + String.copyValueOf(Character.toChars(charValue));
		}
		return plainTextBuilder;
	}

}

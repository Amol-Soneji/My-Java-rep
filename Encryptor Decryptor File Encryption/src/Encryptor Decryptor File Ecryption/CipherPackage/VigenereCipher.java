/**
 * 
 */
package CipherPackage;

import CipherKeys.PolyalphabeticKey;

/**
 * @author Amol Soneji
 *
 */
public class VigenereCipher extends PolyalphabeticCipher 
{
	
	private String cipherTextBuilder;
	private String plainTextBuilder;
	private String cipherText;
	private String plainText;
	private boolean mode;

	/**
	 * 
	 */
	public VigenereCipher(String text, PolyalphabeticKey key, boolean mode) 
	{
		if(mode)//Encryption to be done.  
			plainText = text;
		else
			cipherText = text;
		this.mode = mode;
		super.setKey(key);
	}

	@Override
	public String compute(boolean resultType) 
	{
		if(mode) 
		{
			if(resultType) //Return as a String of encrypted chars.
				return encrypt();
			else //Return as a String of char codes of chars of encrypted String.  
				return encryptCharCodes();
		}
		else 
		{
			if(resultType) //Return plaintext with input being String of encrypted chars.  
				return decrypt();
			else //Return plaintext with input being String of char codes of chars of encrypted String.  
				return decryptCharCodes();
		}
	}

	@Override
	protected String encrypt() 
	{
		for(int index = 0; index < plainText.length(); index++) 
		{
			int charCode = plainText.codePointAt(index);
			int keyCharCode = super.getKey().getKeyVal().codePointAt(index);
			charCode = (charCode + keyCharCode) % 1112063;
			cipherTextBuilder = cipherTextBuilder + String.copyValueOf(Character.toChars(charCode));
		}
		return cipherTextBuilder;
	}

	@Override
	protected String encryptCharCodes() 
	{
		for(int index = 0; index < plainText.length(); index++) 
		{
			int charCode = plainText.codePointAt(index);
			int keyCharCode = super.getKey().getKeyVal().codePointAt(index);
			charCode = (charCode + keyCharCode) % 1112063;
			if(index == (plainText.length() - 1))
				cipherTextBuilder = cipherTextBuilder + String.valueOf(charCode);
			else
				cipherTextBuilder = cipherTextBuilder + String.valueOf(charCode) + " ";
		}
		return cipherTextBuilder;
	}

	@Override
	protected String decrypt() 
	{
		for(int index = 0; index < cipherText.length(); index++) 
		{
			int charCode = cipherText.codePointAt(index);
			int keyCharCode = super.getKey().getKeyVal().codePointAt(index);
			charCode = (charCode - keyCharCode) % 1112063;
			plainTextBuilder = plainTextBuilder + String.copyValueOf(Character.toChars(charCode));
		}
		return plainTextBuilder;
	}

	@Override
	protected String decryptCharCodes() 
	{
		String[] charCodeStringArray = cipherText.split(" ");
		for(int index = 0; index < charCodeStringArray.length; index++) 
		{
			int charCode = Integer.parseInt(charCodeStringArray[index]);
			int keyCharCode = super.getKey().getKeyVal().codePointAt(index);
			charCode = (charCode - keyCharCode) % 1112063;
			plainTextBuilder = plainTextBuilder + String.copyValueOf(Character.toChars(charCode));
		}
		return plainTextBuilder;
	}

}

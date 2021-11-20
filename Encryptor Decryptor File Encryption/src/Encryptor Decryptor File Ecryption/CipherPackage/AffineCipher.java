/**
 * 
 */
package CipherPackage;

import CipherKeys.SubstitutionKey;

/**
 * @author Amol Soneji
 *
 */
public class AffineCipher extends SubstitutionCipher 
{

	private String cipherTextBuilder;
	private String plainTextBuilder;
	private String cipherText;
	private String plainText;
	private boolean mode;
	
	/**
	 * 
	 */
	public AffineCipher(String text, SubstitutionKey key, boolean mode) 
	{
		this.mode = mode;
		if(mode)
			plainText = text;
		else
			cipherText = text;
		super.setKey(key);
	}

	@Override
	public String compute(boolean resultType) 
	{
		if(mode) 
		{
			if(resultType)
				return encrypt();
			else
				return encryptCharCodes();
		}
		else 
		{
			if(resultType)
				return decrypt();
			else
				return decryptCharCodes();
		}
	}

	@Override
	protected String encrypt() 
	{
		for(int index = 0; index < plainText.length(); index++) 
		{
			int charCode = plainText.codePointAt(index);
			charCode = ((super.getKey().getKeyVal() * charCode) + super.getKey().getArbitraryB()) % 1112063;
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
			charCode = ((super.getKey().getKeyVal() * charCode) + super.getKey().getArbitraryB()) % 1112063;
			if(index == (plainText.length() + 1))
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
			charCode = (super.getKey().getAffineDecKey() * (charCode - super.getKey().getArbitraryB())) % 1112063;
			plainTextBuilder = plainTextBuilder + Character.toString(charCode);
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
			charCode = (super.getKey().getAffineDecKey() * (charCode - super.getKey().getArbitraryB())) % 1112063;
			plainTextBuilder = plainTextBuilder + Character.toString(charCode);
		}
		return plainTextBuilder;
	}

}

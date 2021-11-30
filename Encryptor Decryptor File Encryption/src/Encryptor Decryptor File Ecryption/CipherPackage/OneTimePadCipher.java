/**
 * 
 */
package CipherPackage;

import CipherKeys.OneTimePadKey;

/**
 * @author Amol Soneji
 *
 */
public class OneTimePadCipher 
{
	
	private String cipherTextBuilder;
	private String plainTextBuilder;
	private String cipherText;
	private String plainText;
	private boolean mode;
	private OneTimePadKey key;

	/**
	 * 
	 */
	public OneTimePadCipher(String text, OneTimePadKey key, boolean mode)
	{
		this.mode = mode;
		if(mode) // Encrypt
			plainText = text;
		else // Decrypt
			cipherText = text;
		this.key = key;
	}
	
	public void clear()
	{
		cipherText = "";
		plainText = "";
		key = null;
	}
	
	public String compute(boolean inputType)
	{
		if(mode)
		{
			if(inputType) // Is not a string of bits.  
			{
				cipherText = 
			}
		}
	}
}

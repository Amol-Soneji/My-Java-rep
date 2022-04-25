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
	
	private byte[] cipherText;
	private byte[] plainText;
	private boolean mode;
	private OneTimePadKey key;

	/**
	 * 
	 */
	public OneTimePadCipher(byte[] text, OneTimePadKey key, boolean mode)
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
		cipherText = null;
		plainText = null;
		key = null;
	}
	
	public byte[] compute(boolean inputType)
	{
		if(mode)
			return encrypt();
		else
			return decrypt();
	}
	
	protected byte[] encrypt()
	{
		cipherText = new byte[key.getTextLength()];
		for(int index = 0; index < plainText.length; index++)
		{
			cipherText[index] = (byte)(key.getKey()[index] ^ plainText[index]);
		}
		return cipherText;
	}
	
	protected byte[] decrypt()
	{
		plainText = new byte[key.getTextLength()];
		for(int index = 0; index < cipherText.length; index++)
		{
			plainText[index] = (byte)(key.getKey()[index] ^ cipherText[index]);
		}
		return plainText;
	}
}

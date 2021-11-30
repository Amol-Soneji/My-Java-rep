/**
 * 
 */
package CipherKeys;

import java.security.SecureRandom;

/**
 * @author Amol Soneji
 *
 */
public class OneTimePadKey 
{
	private int textLength; // Text length in terms of number of number of bytes.  
	private byte[] key;
	
	public OneTimePadKey(int textLength)
	{
		this.textLength = textLength;
		createKey();
	}
	
	public OneTimePadKey(byte[] key, int textLength)
	{
		this.key = key;
		this.textLength = textLength;
	}
	
	private void createKey()
	{
		SecureRandom random = new SecureRandom();
		key = new byte[textLength];
		random.nextBytes(key);
	}
	
	public byte[] getKey()
	{
		return key;
	}
	
	public int getTextLength()
	{
		return textLength;
	}
}

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
	
	public OneTimePadKey(String key, int textLength)
	{
		this.key = key;
		this.textLength = textLength;
	}
	
	private void createKey()
	{
		SecureRandom random = new SecureRandom();
		String 
		String keyBuilder = "";
		for(int index = 0; index < textLength; index++) 
		{
			keyBuilder = keyBuilder + Character.toChars(random.);
		}
	}
	
	public String getKey()
	{
		return key;
	}
	
	public int getTextLength()
	{
		return textLength;
	}
}

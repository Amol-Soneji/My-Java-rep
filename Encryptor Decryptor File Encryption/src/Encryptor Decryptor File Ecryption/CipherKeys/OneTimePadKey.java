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
	private int textLength;
	private String key;
	
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
		char[] builder = new char[textLength];
		for(int index = 0; index < textLength; index++) 
		{
			char[] holding = Character.toChars(random.nextInt());
			builder[index] = holding[0];
		}
		key = new String(builder);
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

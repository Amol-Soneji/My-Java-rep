/**
 * 
 */
package CipherKeys;

import java.security.SecureRandom;
import java.nio.ByteBuffer;

/**
 * @author Amol Soneji
 *
 */
public class OneTimePadKey extends InheritableKey
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
	
	@Override
	protected void setComponents() 
	{
		ByteBuffer firstArg = ByteBuffer.allocate(4);
		ByteBuffer secondArg = ByteBuffer.allocate(key.length);
		super.keyComponents.add(firstArg.putInt(textLength));
		super.keyComponents.add(secondArg.put(key));
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

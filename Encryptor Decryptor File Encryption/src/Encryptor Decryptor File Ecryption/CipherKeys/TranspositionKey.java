/**
 * 
 */
package CipherKeys;

import java.security.SecureRandom;
import java.nio.ByteBuffer;
import java.util.BitSet;

/**
 * @author Amol Soneji
 *
 */
public class TranspositionKey extends InheritableKey
{

	private int key;
	private boolean usePunct;
	
	/**
	 * 
	 */
	public TranspositionKey() 
	{
		usePunct = true; //Default not user set value for usePunct will be true.  
		createKey();
	}
	
	public TranspositionKey(boolean usePunct)
	{
		this.usePunct = usePunct;
		createKey();
	}
	
	public TranspositionKey(int key, boolean usePunct)
	{
		this.key = key;
		this.usePunct = usePunct;
	}
	
	private void createKey()//Padding will be used in another part of the program in case that L is not a multiple of the key.  
	{
		SecureRandom random = new SecureRandom();
		int val = random.nextInt(128);
		while(val < 10) //10 or larger is better.  
		{
			val = random.nextInt(128);
		}
		key = val;
	}
	
	@Override
	protected void setComponents() 
	{
			ByteBuffer firstArg = ByteBuffer.allocate(4);
			ByteBuffer secondArg = ByteBuffer.allocate(1);
			BitSet boolInBits = new BitSet();
			if(usePunct)
				boolInBits.set(0);
			byte[] boolInBytes = boolInBits.toByteArray();
			super.keyComponents.add(firstArg.putInt(key));
			super.keyComponents.add(secondArg.put(boolInBytes));
	}
	
	public int getKeyVal()
	{
		return key;
	}
	
	public boolean getUsePunct()
	{
		return usePunct;
	}

	
}

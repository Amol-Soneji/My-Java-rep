/**
 * 
 */
package CipherKeys;

import java.security.SecureRandom;
import java.nio.ByteBuffer;
import java.nio.ReadOnlyBufferException;
import java.util.BitSet;
import java.util.ArrayList;

/**
 * @author Amol Soneji
 *
 */
public class TranspositionKey extends InheritableKey
{

	private int key;
	private boolean usePunct;
	private ArrayList<ByteBuffer> components = null;
	
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
	
	public TranspositionKey(ArrayList<ByteBuffer> components) //Called by CipherKeyStorage for use in returning a InheritableKey and main.  
	{
		try
		{
			components.forEach((n) -> n.rewind());
			key = components.get(0).getInt();
			if(components.get(1).hasArray())
			{
				byte[] boolInBytes = components.get(1).array();
				BitSet boolInBits = BitSet.valueOf(boolInBytes);
				usePunct = boolInBits.get(0);
			}
			else
				throw new Exception();
			this.components.addAll(components);
		}
		catch(ReadOnlyBufferException e)
		{
			e.printStackTrace();
			System.out.println("There was an internal problem with the program.  ");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Some contents of the key were not stored in the database.  ");
		}
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
	public void setComponents() 
	{
		if(components == null)
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
		else
			super.keyComponents.addAll(components); //Adds all to InheritableKey. 
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

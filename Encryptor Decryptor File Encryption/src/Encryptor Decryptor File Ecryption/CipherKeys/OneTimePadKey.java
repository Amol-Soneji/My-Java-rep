/**
 * 
 */
package CipherKeys;

import java.security.SecureRandom;
import java.nio.ByteBuffer;
import java.nio.ReadOnlyBufferException;
import java.nio.BufferUnderflowException;
import java.util.ArrayList;

/**
 * @author Amol Soneji
 *
 */
public class OneTimePadKey extends InheritableKey
{
	private int textLength; // Text length in terms of number of number of bytes.  
	private byte[] key;
	private ArrayList<ByteBuffer> components = null;
	
	public OneTimePadKey(int textLength)
	{
		this.textLength = textLength;
		createKey();
	}
	
	public OneTimePadKey(ArrayList<ByteBuffer> components) //Called by CipherKeyStorage for use in returning a InheritableKey and also used by main.  
	{
		components.forEach((n) -> n.rewind());
		try
		{
			textLength = components.get(0).getInt();
			if(components.get(1).hasArray())
				key = components.get(1).array();
			else
				throw new Exception();
			this.components.addAll(components);
		}
		catch(ReadOnlyBufferException e)
		{
			e.printStackTrace();
			System.out.println("There was an internal problem in the program.  ");
		}
		catch(BufferUnderflowException e)
		{
			e.printStackTrace();
			System.out.println("There was an error in how some parts of the key was stored in the database.  ");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Some contents of the key were not stored in the database.  ");
		}
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
	public void setComponents() 
	{
		if(components == null)
		{
			ByteBuffer firstArg = ByteBuffer.allocate(4);
			ByteBuffer secondArg = ByteBuffer.allocate(key.length);
			super.keyComponents.add(firstArg.putInt(textLength));
			super.keyComponents.add(secondArg.put(key));
		}
		else
			super.keyComponents.addAll(components); //Adds all to InheritableKey.  
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

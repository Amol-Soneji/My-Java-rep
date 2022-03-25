/**
 * 
 */
package CipherKeys;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.nio.ByteBuffer;
import java.nio.ReadOnlyBufferException;
import java.nio.BufferUnderflowException;
import java.util.ArrayList;

/**
 * @author Amol Soneji
 *
 */
public class BlockKey extends InheritableKey
{

	private SecretKey key; //The key that will be used by block ciphers.  
	private byte[] IV; //Initialization Vector.  
	private int authenticationTagLength; //Length of the authentication tag used in AES-GCM.  
	private boolean enDecryptionMethod; //Mode of key, true AES-GCM, false AES-CBC.  
	private ArrayList<ByteBuffer> components = null;
	
	/**
	 * 
	 */
	public BlockKey(boolean enDecryptionMethod) 
	{
		this.enDecryptionMethod = enDecryptionMethod;
		createKey();
	}
	
	public BlockKey(ArrayList<ByteBuffer> components) //Called by CipherKeyStorage for use in returning a InheritableKey.  
	{
		try
		{
			this.components = new ArrayList<ByteBuffer>();
			components.forEach((n) -> n.rewind());
			if(components.size() == 3)
			{
				enDecryptionMethod = true;
				if((components.get(0).hasArray()) && (components.get(1).hasArray()))
				{
					byte[] keyBytes = components.get(0).array();
					System.out.println(keyBytes[0]);
					System.out.println(keyBytes[keyBytes.length - 1]);
					key = new SecretKeySpec(keyBytes, "AES");//0, keyBytes.length, "AES");
					IV = components.get(1).array();
					authenticationTagLength = components.get(2).getInt();
				}
				else
					throw new Exception();
			}
			else
			{
				enDecryptionMethod = false;
				if((components.get(0).hasArray()) && (components.get(1).hasArray()))
				{
					byte[] keyBytes = components.get(0).array();
					key = new SecretKeySpec(keyBytes, 0, keyBytes.length, "AES");
					IV = components.get(1).array();
				}
			}
			this.components.addAll(components);
		}
		catch(ReadOnlyBufferException e)
		{
			e.printStackTrace();
			System.out.println("There was an internal problem with the program.  ");
		}
		catch(BufferUnderflowException e)
		{
			e.printStackTrace();
			System.out.println("There is a malformat with the key in the database.  ");
		}
		catch(NullPointerException e)
		{
			e.printStackTrace();
			System.out.println("Internal problem with the program, but not the database.  ");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Some contents of the key were not stored in the database.  ");
		}
		
	}
	
	public BlockKey(SecretKey key, byte[] IV, int authenticataionTagLength)
	{
		this.key = key;
		this.IV = IV;
		this.authenticationTagLength = authenticataionTagLength;
		enDecryptionMethod = true; //True means GCM mode.  
	}
	
	public BlockKey(SecretKey key, byte[] IV)
	{
		this.key = key;
		this.IV = IV;
		enDecryptionMethod = false; //False means CBC mode.  
	}
	
	private void createKey()
	{
		try
		{
			KeyGenerator keyGen = KeyGenerator.getInstance("AES");
			keyGen.init(128);
			key = keyGen.generateKey();
			SecureRandom random = new SecureRandom();
			if(enDecryptionMethod) 
			{
				IV = new byte[12]; //IV in GCM is 12 bytes.  
				random.nextBytes(IV);
				authenticationTagLength = 128; //Tag length is 128 bits.  
			}
			else 
			{
				IV = new byte[16]; //IV in CBC for AES 128-bit is 16 bytes.
				random.nextBytes(IV);
			}
		}
		catch(Exception someException)
		{
			System.out.println("There was an error in the program.  This device does not support AES.  ");
			System.out.println("Please use a different device to use AES.  ");
			System.out.println("Program will close in a few seconds.  ");
			for(int i = 0; i < 100000; i++) 
			{
				//Do nothing.  
			}
			System.exit(1);
		}
	}
	
	@Override
	public void setComponents() 
	{
		if(components == null)
		{
			ByteBuffer firstArg = ByteBuffer.allocate(16);
			if(enDecryptionMethod)
			{
				ByteBuffer secondArg = ByteBuffer.allocate(IV.length);
				ByteBuffer thirdArg = ByteBuffer.allocate(4);
				System.out.println(key.getEncoded()[0]);
				System.out.println(key.getEncoded()[key.getEncoded().length - 1]);
				System.out.println("size " + key.getEncoded().length);
				super.keyComponents.add(firstArg.put(key.getEncoded()));
				super.keyComponents.add(secondArg.put(IV));
				super.keyComponents.add(thirdArg.putInt(authenticationTagLength));
			}
			else
			{
				ByteBuffer secondArg = ByteBuffer.allocate(IV.length);
				super.keyComponents.add(firstArg.put(key.getEncoded()));
				super.keyComponents.add(secondArg.put(IV));
			}
		}
		else
			super.keyComponents.addAll(components); //Adds all to InheritableKey. 
	}
	
	public SecretKey getKey()
	{
		return key;
	}
	
	public byte[] getIV()
	{
		return IV;
	}
	
	public int getAuthenticationTagLength()
	{
		return authenticationTagLength;
	}
	
	public boolean getEnDecryptionMethod()
	{
		return enDecryptionMethod;
	}

	

}

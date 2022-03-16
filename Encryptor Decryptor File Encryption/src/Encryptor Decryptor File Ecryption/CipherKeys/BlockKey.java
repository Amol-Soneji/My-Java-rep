/**
 * 
 */
package CipherKeys;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.SecureRandom;
import java.nio.ByteBuffer;
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
		if(components.size() == 3)
			enDecryptionMethod = true;
		else
			enDecryptionMethod = false;
		this.components.addAll(components);
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
				authenticationTagLength = 16;
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

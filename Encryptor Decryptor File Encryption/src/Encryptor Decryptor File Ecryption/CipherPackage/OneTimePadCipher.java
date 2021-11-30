/**
 * 
 */
package CipherPackage;

import java.io.UnsupportedEncodingException;

import CipherKeys.OneTimePadKey;

/**
 * @author Amol Soneji
 *
 */
public class OneTimePadCipher 
{
	
	private byte[] cipherBytes;
	private byte[] plainBytes;
	private String cipherText;
	private String plainText;
	private boolean mode;
	private OneTimePadKey key;

	/**
	 * 
	 */
	public OneTimePadCipher(String text, OneTimePadKey key, boolean mode)
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
		cipherText = "";
		plainText = "";
		key = null;
	}
	
	public String compute(boolean inputType)
	{
		if(mode)
		{
			if(inputType) // Is not a String of bytes, is a literal plaintext.  
			{
				try 
				{
					plainBytes = plainText.getBytes("UTF-16");
				} 
				catch (UnsupportedEncodingException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("Error could not interpret text.  ");
					System.out.println("Fatal program error, programm will close in 10 seconds.  ");
					for(int i = 0; i < 100000; i++)
					{
						i = i; // Do nothing.  
					}
					System.exit(1);
				}
			}
			else // Is a String of bytes, where each byte is separated by a space.  
			{
				String[] stringBytes = plainText.split(" ");
				for(int index = 0; index < stringBytes.length; index++)
				{
					plainBytes[index] = Byte.parseByte(stringBytes[index]);
				}
			}
			return encrypt();
		}
		else
		{
			try
			{
				cipherBytes = cipherText.getBytes("UTF-16");
			}
			catch(UnsupportedEncodingException e)
			{
				e.printStackTrace();
				System.out.println("Error could not interpret text.  ");
				System.out.println("Fatal program error, programm will close in 10 seconds.  ");
				for(int i = 0; i < 100000; i++)
				{
					i = i; // Do nothing.  
				}
				System.exit(1);
			}
			return decrypt();
		}
	}
	
	protected String encrypt()
	{
		cipherBytes = new byte[key.getTextLength()];
		for(int index = 0; index < plainBytes.length; index++)
		{
			cipherBytes[index] = (byte)(key.getKey()[index] ^ plainBytes[index]);
		}
		try 
		{
			cipherText = new String(cipherBytes, "UTF-16");
		} 
		catch (UnsupportedEncodingException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error could not interpret text.  ");
			System.out.println("Fatal program error, programm will close in 10 seconds.  ");
			for(int i = 0; i < 100000; i++)
			{
				i = i; // Do nothing.  
			}
			System.exit(1);
		}
		return cipherText;
	}
	
	protected String decrypt()
	{
		plainBytes = new byte[key.getTextLength()];
		for(int index = 0; index < cipherBytes.length; index++)
		{
			plainBytes[index] = (byte)(key.getKey()[index] ^ cipherBytes[index]);
		}
		try
		{
			plainText = new String(cipherBytes, "UTF-16");
		}
		catch(UnsupportedEncodingException e)
		{
			e.printStackTrace();
			System.out.println("Error could not interpret text.  ");
			System.out.println("Fatal program error, programm will close in 10 seconds.  ");
			for(int i = 0; i < 100000; i++)
			{
				i = i; // Do nothing.  
			}
			System.exit(1);
		}
		return plainText;
	}
}

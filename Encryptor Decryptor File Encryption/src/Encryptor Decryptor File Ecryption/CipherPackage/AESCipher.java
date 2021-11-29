/**
 * 
 */
package CipherPackage;

import java.io.UnsupportedEncodingException;

import CipherKeys.BlockKey;

/**
 * @author Amol Soneji
 *
 */
public class AESCipher extends BlockCipher 
{
	
	private String cipherTextBuilder;
	private String plainTextBuilder;
	private byte[] rawCipherText;
	private String plainText;
	private boolean EnDecryptionMethod;
	private boolean mode;

	/**
	 * 
	 */
	public AESCipher(String text, BlockKey key, boolean mode) 
	{
		this.mode = mode;
		if(!mode) // Decryption.  
		{
			try 
			{
				rawCipherText = text.getBytes("UTF-16");
			} 
			catch (UnsupportedEncodingException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Error could not interpret text.  ");
			}
		}
		else
			plainText = text;
		super.setKey(key);
		EnDecryptionMethod = key.getEnDecryptionMethod();
	}

	@Override
	public String compute() throws Exception 
	{
		if(mode) // Encryption
		{
			return encrypt();
		}
		else
		{
			return decrypt();
		}
	}

	@Override
	protected String encrypt() throws Exception 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String decrypt() throws Exception 
	{
		// TODO Auto-generated method stub
		return null;
	}

}

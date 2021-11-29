/**
 * 
 */
package CipherPackage;

import java.io.UnsupportedEncodingException;

import CipherKeys.BlockKey;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.GCMParameterSpec;

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
				System.out.println("Fatal program error, programm will close in 10 seconds.  ");
				for(int i = 0; i < 100000; i++)
				{
					i = i; // Do nothing.  
				}
				System.exit(1);
			}
		}
		else
			plainText = text;
		super.setBlockKey(key);
		EnDecryptionMethod = key.getEnDecryptionMethod();
	}

	@Override
	public String compute() throws Exception 
	{
		if(mode) // Encryption
			return encrypt();
		else
			return decrypt();
	}

	@Override
	protected String encrypt() throws Exception 
	{
		if(EnDecryptionMethod) // AES-GCM
		{
			Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
			try
			{
				SecretKeySpec keySpec = new SecretKeySpec(super.getBlockKey().getKey().getEncoded(), "AES");
				GCMParameterSpec parameterSpec = new GCMParameterSpec(super.getBlockKey().getAuthenticationTagLength(), super.getBlockKey().getIV());
			}
			catch(IllegalArgumentException e)
			{
				e.printStackTrace();
				System.out.println("There was an internal error in the program.  ");
				throw new Exception();
			}
		}
		else // AES-CBC
		{
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			try
			{
				SecretKeySpec keySpec = new SecretKeySpec(super.getBlockKey().getKey().getEncoded(), "AES");
				
			}
			catch(IllegalArgumentException e)
			{
				e.printStackTrace();
				System.out.println("There was an internal error in the program.  ");
				throw new Exception();
			}
		}
	}

	@Override
	protected String decrypt() throws Exception 
	{
		// TODO Auto-generated method stub
		return null;
	}

}

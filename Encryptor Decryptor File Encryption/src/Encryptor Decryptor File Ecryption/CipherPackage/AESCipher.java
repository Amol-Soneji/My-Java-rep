/**
 * 
 */
package CipherPackage;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import CipherKeys.BlockKey;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * @author Amol Soneji
 *
 */
public class AESCipher extends BlockCipher 
{
	
	private byte[] rawCipherText = {0};
	private byte[] rawPlainText = {0};
	private String plainText;
	private String cipherText;
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
		{
			try
			{
				rawPlainText = text.getBytes("UTF-16");
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
		}
		super.setBlockKey(key);
		EnDecryptionMethod = key.getEnDecryptionMethod();
	}

	@Override
	public byte[] compute() throws Exception 
	{
		if(mode) // Encryption
			return encrypt();
		else
			return decrypt();
	}

	@Override
	protected byte[] encrypt() throws Exception 
	{
		if(EnDecryptionMethod) // AES-GCM
		{
			Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
			try
			{
				GCMParameterSpec parameterSpec = new GCMParameterSpec(getBlockKey().getAuthenticationTagLength(), getBlockKey().getIV());
				cipher.init(Cipher.ENCRYPT_MODE, getBlockKey().getKey(), parameterSpec);
				rawCipherText = cipher.doFinal(rawPlainText);
			}
			catch(InvalidKeyException e)
			{
				e.printStackTrace();
				System.out.println("There was an issue with the key that was provided, plase make sure the correct \n"
								 + "key was supplied.  ");
				throw new Exception();
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
				SecretKeySpec keySpec = new SecretKeySpec(getBlockKey().getKey().getEncoded(), "AES");
				IvParameterSpec ivSpec = new IvParameterSpec(getBlockKey().getIV());
				cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
				rawCipherText = cipher.doFinal(rawPlainText);
			}
			catch(InvalidKeyException e)
			{
				e.printStackTrace();
				System.out.println("There was an issue with the key that was provided, please make sure the correct \n"
								 + "key was supplied.  ");
				throw new Exception();
			}
			catch(IllegalArgumentException e)
			{
				e.printStackTrace();
				System.out.println("There was an internal error in the program.  ");
				throw new Exception();
			}
		}
		return rawCipherText;
	}

	@Override
	protected byte[] decrypt() throws Exception 
	{
		if(EnDecryptionMethod) // AES-GCM
		{
			System.out.println("This is not getting skipped.  ");
			Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
			try
			{
				GCMParameterSpec parameterSpec = new GCMParameterSpec(getBlockKey().getAuthenticationTagLength(), getBlockKey().getIV());
				System.out.println("size " + getBlockKey().getKey().getEncoded().length);
				System.out.println(getBlockKey().getIV()[0] + "   stored version  " +  getBlockKey().getIV()[11]);
				cipher.init(Cipher.DECRYPT_MODE, getBlockKey().getKey(), parameterSpec);
			}
			catch(InvalidKeyException e)
			{
				e.printStackTrace();
				System.out.println("There was an issue with the key that was provided, plase make sure the correct \n"
								 + "key was supplied.  ");
				throw new Exception();
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
				System.out.println("This is not the correct place right now, testing still on GCM.  ");
				SecretKeySpec keySpec = new SecretKeySpec(getBlockKey().getKey().getEncoded(), "AES");
				IvParameterSpec ivSpec = new IvParameterSpec(getBlockKey().getIV());
				cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
				rawPlainText = cipher.doFinal(rawCipherText);
			}
			catch(InvalidKeyException e)
			{
				e.printStackTrace();
				System.out.println("There was an issue with the key that was provided, please make sure the correct \n"
								 + "key was supplied.  ");
				throw new Exception();
			}
			catch(IllegalArgumentException e)
			{
				e.printStackTrace();
				System.out.println("There was an internal error in the program.  ");
				throw new Exception();
			}
		}
		System.out.println(rawPlainText.length);
		return rawPlainText;
	}

}

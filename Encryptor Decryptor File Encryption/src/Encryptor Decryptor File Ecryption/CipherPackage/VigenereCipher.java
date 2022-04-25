/**
 * 
 */
package CipherPackage;

import java.io.UnsupportedEncodingException;
import CipherKeys.PolyalphabeticKey;

/**
 * @author Amol Soneji
 *
 */
public class VigenereCipher extends PolyalphabeticCipher 
{
	
	private String cipherTextBuilder;
	private String plainTextBuilder;
	private String cipherText;
	private byte[] cipherBytes = null;
	private String plainText;
	private boolean mode;

	/**
	 * 
	 */
	public VigenereCipher(String text, PolyalphabeticKey key, boolean mode) 
	{
		if(mode)//Encryption to be done.  
			plainText = text;
		else
			cipherText = text;
		this.mode = mode;
		super.setKey(key);
	}
	
	public VigenereCipher(byte[] text, PolyalphabeticKey key, boolean mode) 
	{
		if(mode)//Encryption to be done.  
		{
			try
			{
				plainText = new String(text, "UTF-16");
			}
			catch(UnsupportedEncodingException e)
			{
				e.printStackTrace();
				System.out.println("Please use a computer that supports UTF-16 to use this program.  ");
				System.out.println("Program exiting in 10 seconds.  ");
				for(int i = 0; i < 10000; i++);
				{
					//Do nothing.  
				}
				System.exit(1);
			}
		}
		else
			cipherBytes = text;
		this.mode = mode;
		super.setKey(key);
	}

	@Override
	public byte[] compute(boolean resultType) 
	{
		try
		{
			if(mode) 
			{
				if(resultType) //Return as a String of encrypted chars.
					return encrypt().getBytes("UTF-16");
				else //Return as a String of char codes of chars of encrypted String.  
					return encryptCharCodes().getBytes("UTF-16");
			}
			else 
			{
				if(resultType) //Return plaintext with input being String of encrypted chars.  
					return decrypt().getBytes("UTF-16");
				else //Return plaintext with input being String of char codes of chars of encrypted String.  
					return decryptCharCodes().getBytes("UTF-16");
			}
		}
		catch(UnsupportedEncodingException e)
		{
			e.printStackTrace();
			System.out.println("Please use a computer that supports UTF-16 to use this program.  ");
			System.out.println("Program exiting in 10 seconds.  ");
			for(int i = 0; i < 10000; i++);
			{
				//Do nothing.  
			}
			System.exit(1);
		}
		return cipherBytes; //Unreachable, but the compiler thinks that it is possible to get out of the Try/Catch.  
	}

	@Override
	protected String encrypt() 
	{
		for(int index = 0; index < plainText.length(); index++) 
		{
			int charCode = plainText.codePointAt(index);
			int keyCharCode = super.getKey().getKeyVal().codePointAt(index);
			charCode = (charCode + keyCharCode) % 1112063;
			cipherTextBuilder = cipherTextBuilder + String.copyValueOf(Character.toChars(charCode));
		}
		return cipherTextBuilder;
	}

	@Override
	protected String encryptCharCodes() 
	{
		for(int index = 0; index < plainText.length(); index++) 
		{
			int charCode = plainText.codePointAt(index);
			int keyCharCode = super.getKey().getKeyVal().codePointAt(index);
			charCode = (charCode + keyCharCode) % 1112063;
			if(index == (plainText.length() - 1))
				cipherTextBuilder = cipherTextBuilder + String.valueOf(charCode);
			else
				cipherTextBuilder = cipherTextBuilder + String.valueOf(charCode) + " ";
		}
		return cipherTextBuilder;
	}

	@Override
	protected String decrypt() 
	{
		if(cipherBytes == null)
		{
			for(int index = 0; index < cipherText.length(); index++) 
			{
				int charCode = cipherText.codePointAt(index);
				int keyCharCode = super.getKey().getKeyVal().codePointAt(index);
				charCode = (charCode - keyCharCode) % 1112063;
				plainTextBuilder = plainTextBuilder + Character.toString(charCode);
			}
		}
		else
		{
			try
			{
				String UTFString = new String(cipherBytes, "UTF-16");
				for(int index = 0; index < UTFString.length(); index++)
				{
					int charCode = UTFString.codePointAt(index);
					int keyCharCode = super.getKey().getKeyVal().codePointAt(index);
					charCode = (charCode - keyCharCode) % 1112063;
					plainTextBuilder = plainTextBuilder + Character.toString(charCode);
				}
			}
			catch(UnsupportedEncodingException e)
			{
				e.printStackTrace();
				System.out.println("Please use a computer that supports UTF-16 to use this program.  ");
				System.out.println("Program exiting in 10 seconds.  ");
				for(int i = 0; i < 10000; i++);
				{
					//Do nothing.  
				}
				System.exit(1);
			}
		}
		return plainTextBuilder;
	}

	@Override
	protected String decryptCharCodes() 
	{
		String[] charCodeStringArray = cipherText.split(" ");
		for(int index = 0; index < charCodeStringArray.length; index++) 
		{
			int charCode = Integer.parseInt(charCodeStringArray[index]);
			int keyCharCode = super.getKey().getKeyVal().codePointAt(index);
			charCode = (charCode - keyCharCode) % 1112063;
			plainTextBuilder = plainTextBuilder + Character.toString(charCode);
		}
		return plainTextBuilder;
	}

}

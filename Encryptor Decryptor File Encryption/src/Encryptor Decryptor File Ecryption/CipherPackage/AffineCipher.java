/**
 * 
 */
package CipherPackage;

import CipherKeys.SubstitutionKey;
import java.io.UnsupportedEncodingException;

/**
 * @author Amol Soneji
 *
 */
public class AffineCipher extends SubstitutionCipher 
{

	private String cipherTextBuilder;
	private String plainTextBuilder;
	private String cipherText;
	private String plainText;
	private boolean mode;
	
	/**
	 * 
	 */
	public AffineCipher(String text, SubstitutionKey key, boolean mode) 
	{
		this.mode = mode;
		if(mode)
			plainText = text;
		else
			cipherText = text;
		super.setKey(key);
	}
	
	public AffineCipher(byte[] text, SubstitutionKey key, boolean mode)
	{
		this.mode = mode;
		try 
		{
			if(mode)
				plainText = new String(text, "UTF-16");
			else
				cipherText = new String(text, "UTF-16");
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
		super.setKey(key);
	}

	@Override
	public byte[] compute(boolean resultType) 
	{
		byte[] toReturn = null;
		try
		{
			if(mode) 
			{
				if(resultType)
					toReturn = encrypt().getBytes("UTF-16");
				else
					toReturn = encryptCharCodes().getBytes("UTF-16");
			}
			else 
			{
				if(resultType)
					toReturn = decrypt().getBytes("UTF-16");
				else
					toReturn = decryptCharCodes().getBytes("UTF-16");
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
		return toReturn;
	}
	@Override
	protected String encrypt() 
	{
		for(int index = 0; index < plainText.length(); index++) 
		{
			int charCode = plainText.codePointAt(index);
			charCode = ((super.getKey().getKeyVal() * charCode) + super.getKey().getArbitraryX()) % 1112063;
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
			charCode = ((super.getKey().getKeyVal() * charCode) + super.getKey().getArbitraryX()) % 1112063;
			if(index == (plainText.length() + 1))
				cipherTextBuilder = cipherTextBuilder + String.valueOf(charCode);
			else
				cipherTextBuilder = cipherTextBuilder + String.valueOf(charCode) + " ";
		}
		return cipherTextBuilder;
	}

	@Override
	protected String decrypt() 
	{
		for(int index = 0; index < cipherText.length(); index++) 
		{
			int charCode = cipherText.codePointAt(index);
			charCode = (super.getKey().getAffineDecKey() * (charCode - super.getKey().getArbitraryX())) % 1112063;
			plainTextBuilder = plainTextBuilder + Character.toString(charCode);
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
			charCode = (super.getKey().getAffineDecKey() * (charCode - super.getKey().getArbitraryX())) % 1112063;
			plainTextBuilder = plainTextBuilder + Character.toString(charCode);
		}
		return plainTextBuilder;
	}

}

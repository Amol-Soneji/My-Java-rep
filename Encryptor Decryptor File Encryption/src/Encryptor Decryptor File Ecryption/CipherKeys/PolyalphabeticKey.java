package CipherKeys;

import java.security.SecureRandom;
import java.util.ArrayList; //Array list for storing char array, since when using full UTF-16, there are characters that may use more than one char for once character.
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.BitSet; //Usefull when converting a boolean to a byte for ByteBuffer.  

/**
 * @author Amol Soneji
 *
 */

public class PolyalphabeticKey extends InheritableKey
{
	private String key;
	private boolean usePunct;

	public PolyalphabeticKey() 
	{
		usePunct = true; //No arg constructor will set usePunct to a default of true.  
		createKey();
	}
	
	public PolyalphabeticKey(boolean usePunct)
	{
		this.usePunct = usePunct;
		createKey();
	}
	
	public PolyalphabeticKey(String key, boolean usePunct)
	{
		this.key = key;
		this.usePunct = usePunct;
	}
	
	private void createKey()
	{
		SecureRandom random = new SecureRandom();
		if(usePunct) 
		{
			ArrayList<char[]> charList = new ArrayList<char[]>();
			for(int index = 0; index < 10; index++) //Key length is 10 if created via this program.  
			{
				charList.add(Character.toChars(random.nextInt(1112063)));
			}
			for(int index = 0; index < 10; index++) 
			{
				key = key + String.valueOf(charList.get(index));
			}
		}
		else //Only capital and lower case letters.  
		{
			char[][] charList = new char[10][1];
			for(int index = 0; index < 10; index++) 
			{
				int val = random.nextInt(122) - 65;
				while((val > 25) && (val < 32)) 
				{
					val = random.nextInt(122) - 65;
				}
				charList[index] = Character.toChars(val + 65);
			}
			for(int index = 0; index < 10; index++) 
			{
				key = key + String.valueOf(charList[index]);
			}
		}
	}
	
	@Override
	protected void setComponents() 
	{
			ByteBuffer firstArg = ByteBuffer.allocate(key.length());
			ByteBuffer secondArg = ByteBuffer.allocate(1);
			BitSet boolInBits = new BitSet();
			if(usePunct)
				boolInBits.set(0);
			byte[] boolInBytes = boolInBits.toByteArray();
			try
			{
				super.keyComponents.add(firstArg.put(key.getBytes("UTF-16")));
			}
			catch(UnsupportedEncodingException e)
			{
				System.out.println("There was an error.  Please run the program in a different");
				System.out.println("computer.  Program will close in 10 seconds.  ");
				for(int i = 0; i < 10000; i++)
				{
					i = i;  
				}
				System.exit(0);
			}
			super.keyComponents.add(secondArg.put(boolInBytes[0]));
	}
	
	public String getKeyVal()
	{
		return key;
	}
	
	public boolean getUsePunct()
	{
		return usePunct;
	}

	
}

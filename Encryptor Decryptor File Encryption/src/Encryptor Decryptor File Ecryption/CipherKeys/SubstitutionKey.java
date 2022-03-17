/**/
package CipherKeys;

import java.security.SecureRandom;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.BufferUnderflowException;
import java.util.ArrayList;

/**
 * @author Amol Soneji
 *
 */
public class SubstitutionKey extends InheritableKey
{
	private int key;
	private int affineDecKey;
	private int arbitraryX;
	private boolean booleanMode;
	private ArrayList<ByteBuffer> components = null;
	
	public SubstitutionKey(boolean booleanMode) throws KeyCreationException
	{
		this.booleanMode = booleanMode;
		createKey();
	}
	
	public SubstitutionKey(int key)
	{
		this.key = key;
		booleanMode = false;
	}
	
	public SubstitutionKey(ArrayList<ByteBuffer> components) //Called by CipherKeyStorage for use in returning a InheritableKey, and used by main.  
	{
		try
		{
			components.forEach((n) -> n.rewind());
			if(components.size() == 3)
			{
				booleanMode = true;
				key = components.get(0).getInt();
				affineDecKey = components.get(1).getInt();
				arbitraryX = components.get(2).getInt();
			}
			else
			{
				booleanMode = false;
				key = components.get(0).getInt();
			}
			this.components.addAll(components);
		}
		catch(BufferUnderflowException e)
		{
			e.printStackTrace();
			System.out.println("There is a malformat with the key in the database.  ");
		}
	}
	
	public SubstitutionKey(int key, int affineDecKey, int arbitraryX)
	{
		this.key = key;
		this.affineDecKey = affineDecKey;
		this.arbitraryX = arbitraryX;
		booleanMode = true;
	}
	
	private void createKey() throws KeyCreationException
	{
		SecureRandom random = new SecureRandom();
		if(booleanMode) 
		{
			int randomInteger = -1;
			while((randomInteger > 1112063) || (randomInteger < 2) || (gcd(randomInteger, 1112063) != 1)) 
			{
				randomInteger = random.nextInt(2097152);
			}
			key = randomInteger;
			System.out.println(key);
			try
			{
				affineDecKey = (BigInteger.valueOf(key).modInverse(BigInteger.valueOf(1112063))).intValue();
			}
			catch(ArithmeticException e)
			{
				System.out.println("Internal program error.  ");
				throw new KeyCreationException();
			}
			randomInteger = -1;
			while((randomInteger > 1112063) || (randomInteger < 2) || (randomInteger == key) || (randomInteger == affineDecKey)) 
			{
				randomInteger = random.nextInt(2097152);
			}
			arbitraryX = randomInteger;
		}
		else 
		{
			key = random.nextInt();
		}
	}
	
	@Override
	public void setComponents() 
	{
		if(components == null)
		{
			ByteBuffer firstArg = ByteBuffer.allocate(4);
			if(booleanMode)
			{
				ByteBuffer secondArg = ByteBuffer.allocate(4);
				ByteBuffer thirdArg = ByteBuffer.allocate(4);
				System.out.println(key);
				super.keyComponents.add(firstArg.putInt(key));
				super.keyComponents.add(secondArg.putInt(affineDecKey));
				super.keyComponents.add(thirdArg.putInt(arbitraryX));
			}
			else
				super.keyComponents.add(firstArg.putInt(key));
		}
		else
			super.keyComponents.addAll(components); //Adds all to InheritableKey.  
	}
	
	public int getKeyVal()
	{
		return key;
	}
	
	public int getAffineDecKey()
	{
		return affineDecKey;
	}
	
	public int getArbitraryX()
	{
		return arbitraryX;
	}
	
	private int gcd(int a, int b)
	{
		if(b == 0)
			return a;
		return gcd(b, a % b);
	}

	
}

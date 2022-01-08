/**/
package CipherKeys;

import java.security.SecureRandom;
import java.math.BigInteger;
import java.nio.ByteBuffer;

/**
 * @author Amol Soneji
 *
 */
public class SubstitutionKey extends InheritableKey
{
	private int key;
	private int affineDecKey;
	private int arbitraryB;
	private boolean booleanMode;
	
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
	
	public SubstitutionKey(int key, int affineDecKey, int arbitraryB)
	{
		this.key = key;
		this.affineDecKey = affineDecKey;
		this.arbitraryB = arbitraryB;
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
			arbitraryB = randomInteger;
		}
		else 
		{
			key = random.nextInt();
		}
	}
	
	@Override
	protected void setComponents() 
	{
		ByteBuffer firstArg = ByteBuffer.allocate(4);
		if(booleanMode)
		{
			ByteBuffer secondArg = ByteBuffer.allocate(4);
			ByteBuffer thirdArg = ByteBuffer.allocate(4);
			super.keyComponents.add(firstArg.putInt(key));
			super.keyComponents.add(secondArg.putInt(affineDecKey));
			super.keyComponents.add(thirdArg.putInt(arbitraryB));
		}
		else
			super.keyComponents.add(firstArg.putInt(key));
	}
	
	public int getKeyVal()
	{
		return key;
	}
	
	public int getAffineDecKey()
	{
		return affineDecKey;
	}
	
	public int getArbitraryB()
	{
		return arbitraryB;
	}
	
	private int gcd(int a, int b)
	{
		if(b == 0)
			return a;
		return gcd(b, a % b);
	}

	
}

/**
 * 
 */
package CipherKeys;

import java.security.SecureRandom;

/**
 * @author eon74
 *
 */
public class SubstitutionKey 
{
	private int key;
	private int affineDecKey;
	private int arbitraryB;
	private boolean booleanMode;
	
	public SubstitutionKey(boolean booleanMode)
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
	
	private void createKey()
	{
		SecureRandom random = new SecureRandom();
		if(booleanMode) {
			int randomInteger = -1;
			while((randomInteger > 1112064) && (randomInteger < 2) && (gcd(randomInteger, 1112064) != 1)) {
				randomInteger = random.nextInt(2097152);
			}
			key = randomInteger;
			
		}
		else {
			key = random.nextInt();
		}
	}
	
	private int gcd(int a, int b)
	{
		if(b == 0)
			return a;
		return gcd(b, a % b);
	}
}

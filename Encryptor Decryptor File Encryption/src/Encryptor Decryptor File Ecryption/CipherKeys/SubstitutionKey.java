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
			
		}
		else {
			key = random.nextInt();
		}
	}
}

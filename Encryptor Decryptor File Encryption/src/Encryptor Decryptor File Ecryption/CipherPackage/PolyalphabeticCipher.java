/**
 * 
 */
package CipherPackage;

import CipherKeys.PolyalphabeticKey;

/**
 * @author Amol Soneji
 *
 */
public abstract class PolyalphabeticCipher 
{
	
	private PolyalphabeticKey key;

	/**
	 * 
	 */
	public PolyalphabeticCipher() 
	{
		//Blank constructor.  
	}
	
	public void clear()
	{
		key = null;
	}
	
	public abstract byte[] compute(boolean resultType);
	
	protected void setKey(PolyalphabeticKey key)
	{
		this.key = key;
	}
	
	protected PolyalphabeticKey getKey()
	{
		return key;
	}
	
	protected abstract String encrypt();
	
	protected abstract String encryptCharCodes();

	protected abstract String decrypt();
	
	protected abstract String decryptCharCodes();
}

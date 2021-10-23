/**
 * 
 */
package CipherPackage;

import CipherKeys.SubstitutionKey;

/**
 * @author Amol Soneji
 *
 */
public abstract class SubstitutionCipher 
{
	private SubstitutionKey key;

	/**
	 * 
	 */
	public SubstitutionCipher() 
	{
		// TODO Auto-generated constructor stub
	}
	
	public void clear()
	{
		key = null;
	}
	
	public abstract String compute(boolean resultType);
	
	protected void setKey(SubstitutionKey key)
	{
		this.key = key;
	}
	
	protected SubstitutionKey getKey()
	{
		return key;
	}
	
	protected abstract String encrypt();
	
	protected abstract String encryptCharCodes();
	
	protected abstract String decrypt();
	
	protected abstract String decryptCharCodes();

}

/**
 * 
 */
package CipherPackage;

import CipherKeys.TranspositionKey;

/**
 * @author Amol Soneji
 *
 */
public abstract class TranspositionCipher 
{
	
	private TranspositionKey key;
	
	/**
	 * 
	 */
	public TranspositionCipher() 
	{
		// TODO Auto-generated constructor stub
	}
	
	public void clear()
	{
		key = null;
	}
	
	public abstract String compute(boolean resultType);
	
	protected void setKey(TranspositionKey key)
	{
		this.key = key;
	}
	
	protected TranspositionKey getKey()
	{
		return key;
	}
	
	protected boolean getPunct()
	{
		return key.getUsePunct();
	}
	
	protected abstract String encrypt();
	
	protected abstract String encryptCharCodes();
	
	protected abstract String decrypt();
	
	protected abstract String decryptCharCodes();
}

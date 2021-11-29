/**
 * 
 */
package CipherPackage;

import CipherKeys.BlockKey;

/**
 * @author Amol Soneji
 *
 */
public abstract class BlockCipher 
{
	
	private BlockKey key;

	/**
	 * 
	 */
	public BlockCipher() 
	{
		// TODO Auto-generated constructor stub
	}
	
	public void clear()
	{
		key = null;
	}
	
	public abstract String compute(boolean resultType) throws Exception;
	
	protected void setKey(BlockKey key)
	{
		this.key = key;
	}
	
	protected BlockKey getKey()
	{
		return key;
	}
	
	protected abstract String encrypt() throws Exception; // Char code version of the method is not added, for condensing purpouses.  
	
	protected abstract String decrypt() throws Exception;
	
	protected abstract String decryptCharCodes() throws Exception;
}

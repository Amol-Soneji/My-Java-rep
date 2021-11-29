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
	
	public abstract String compute() throws Exception;
	
	protected void setBlockKey(BlockKey key)
	{
		this.key = key;
	}
	
	protected BlockKey getBlockKey()
	{
		return key;
	}
	
	protected abstract String encrypt() throws Exception; // Char code version of the method is not added, for condensing purpouses.  
	
	protected abstract String decrypt() throws Exception;
	
}

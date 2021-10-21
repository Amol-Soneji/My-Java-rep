/**
 * 
 */
package CipherKeys;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.SecureRandom;

/**
 * @author Amol Soneji
 *
 */
public class BlockKey {

	private SecretKey key;
	private byte[] IV;
	private int authenticationTagLength;
	private boolean enDecryptionMethod;
	
	/**
	 * 
	 */
	public BlockKey(boolean enDecryptionMethod) 
	{
		this.enDecryptionMethod = enDecryptionMethod;
		createKey();
	}
	
	public BlockKey(SecretKey key, byte[] IV, int authenticataionTagLength)
	{
		this.key = key;
		this.IV = IV;
		this.authenticationTagLength = authenticationTagLength;
		enDecryptionMethod = true; //True means GCM mode.  
	}
	
	public BlockKey(SecretKey key, byte[] IV)
	{
		this.key = key;
		this.IV = IV;
		enDecryptionMethod = false; //False means CBC mode.  
	}
	
	

}

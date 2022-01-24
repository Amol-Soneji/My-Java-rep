/**
 * 
 */
package CipherKeys;

import java.util.ArrayList;
import java.nio.ByteBuffer;

/**
 * @author Amol Soneji
 *
 */
public abstract class InheritableKey 
{
	protected ArrayList<ByteBuffer> keyComponents; //Although byte[] could be used, but not everything can be listed as a byte[], ex: int.
	
	public InheritableKey()
	{
		//Do nothing.  
	}
	
	public ArrayList<ByteBuffer> getComponents()
	{
		return this.keyComponents;
	}
	
	public abstract void setComponents();
}

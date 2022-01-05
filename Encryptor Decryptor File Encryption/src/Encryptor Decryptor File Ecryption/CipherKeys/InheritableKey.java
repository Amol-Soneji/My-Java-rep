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
	protected ArrayList<ByteBuffer> keyComponents;
	
	public InheritableKey()
	{
		//Do nothing.  
	}
	
	public ArrayList<ByteBuffer> getComponents()
	{
		return this.keyComponents;
	}
	
	protected abstract void setComponents();
}

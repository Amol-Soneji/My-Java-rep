package CipherKeys;

import java.security.SecureRandom;
import java.util.ArrayList; //Array list for storing char array, since when using full UTF-16, there are characters that may use more than one char for once character.  

/*
 * 
 */

public class PolyalphabeticKey 
{
	private String key;
	private boolean usePunct;

	public PolyalphabeticKey() 
	{
		usePunct = true; //No arg constructor will set usePunct to a default of true.  
		createKey();
	}
	
	public PolyalphabeticKey(boolean usePunct)
	{
		this.usePunct = usePunct;
		createKey();
	}
	
	public PolyalphabeticKey(String key, boolean usePunct)
	{
		this.key = key;
		this.usePunct = usePunct;
	}
	
	private void createKey()
	{
		SecureRandom random = new SecureRandom();
		if(usePunct) {
			ArrayList<char[]> charList = new ArrayList<char[]>();
			for(int index = 0; index < 10; index++) {//Key length is 10 if created via this program.  
				charList.add(Character.toChars(random.nextInt(1112064)));
			}
			for(int index = 0; index < 10; index++) {
				key = key + String.valueOf(charList.get(index));
			}
		}
		else {
			
		}
	}
}

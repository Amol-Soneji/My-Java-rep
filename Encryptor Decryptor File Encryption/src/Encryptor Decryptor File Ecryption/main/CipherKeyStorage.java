/**
 * 
 */
package main;

import CipherKeys.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Amol Soneji
 *
 */
public class CipherKeyStorage 
{
	private static final String FILE_PATH = "Keys.db";
	private String dbName;
	private Connection conn;
	
	public CipherKeyStorage(String dbName)
	{
		this.dbName = dbName;
		try
		{
			this.conn = DriverManager.getConnection("jdbc:sqlite:" + FILE_PATH);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			System.out.println("There was an internal error with the program.  \n"
							  + "The program will exit in 10 seconds.  ");
			for(int i = 0; i < 10000; i++)
			{
				i = i; //Do nothing.  
			}
			System.exit(1);
		}
	}
}

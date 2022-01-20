/**
 * 
 */
package main;

import CipherKeys.InheritableKey;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.sql.Blob;

/**
 * @author Amol Soneji
 *
 */
public class CipherKeyStorage 
{
	private static final String FILE_PATH = "Keys.db"; //Default used by program.  
	private String dbName; //Used for user provided databases or user created databases.  
	private Connection conn;
	
	public CipherKeyStorage()
	{
		try 
		{
			this.conn = DriverManager.getConnection("jdbc:sqlite:" + FILE_PATH);
		} 
		catch (SQLException e) 
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
	
	public CipherKeyStorage(String dbName)
	{
		this.dbName = dbName;
		try
		{
			boolean[] tblsExistant = new boolean[7];
			this.conn = DriverManager.getConnection("jdbc:sqlite:" + dbName);
			Statement stmt = conn.createStatement();
			ResultSet rSet = stmt.executeQuery("SHOW TABLES"); //Check to see if any of the tables needed 
			//by this program are not in the database yet.  
			while(rSet.next())
			{
				String tblName = rSet.getString(1);
				if(tblName.equalsIgnoreCase("AES-GCM"))
					tblsExistant[0] = true;
				else if(tblName.equalsIgnoreCase("AES-CBC"))
					tblsExistant[1] = true;
				else if(tblName.equalsIgnoreCase("Affine"))
					tblsExistant[2] = true;
				else if(tblName.equalsIgnoreCase("Caeser"))
					tblsExistant[3] = true;
				else if(tblName.equalsIgnoreCase("OneTimePad"))
					tblsExistant[4] = true;
				else if(tblName.equalsIgnoreCase("RailFence"))
					tblsExistant[5] = true;
				else if(tblName.equalsIgnoreCase("Vigenere"))
					tblsExistant[6] = true;
				else
					tblName = tblName; //Do nothing, user defined table.  
			}
			//Now create any of the missing tables if needed.  
			if(!(tblsExistant[0]))
			{
				stmt.executeUpdate("CREATE TABLE AES-GCM ("
								   + "document_name varchar(255) NOT NULL, "
								   + "key BLOB NOT NULL, "
								   + "iv BLOB NOT NULL, "
								   + "auth_tag_len integer NOT NULL, "
								   + "PRIMARY KEY (document_name))");
			}
			if(!(tblsExistant[1]))
			{
				stmt.executeUpdate("CREATE TABLE AES-CBC ("
								   + "document_name varchar(255) NOT NULL, "
								   + "key BLOB NOT NULL, "
								   + "iv BLOB NOT NULL, "
								   + "PRIMARY KEY (document_name))");
			}
			if(!(tblsExistant[2]))
			{
				stmt.executeUpdate("CREATE TABLE Affine ("
								   + "document_name varchar(255) NOT NULL, "
								   + "key integer NOT NULL, "
								   + "affine_dec_key integer NOT NULL, "
								   + "arbitrary_b integer NOT NULL, "
								   + "PRIMARY KEY (document_name))");
			}
			if(!(tblsExistant[3]))
			{
				stmt.executeUpdate("CREATE TABLE Caeser ("
								   + "document_name varchar(255) NOT NULL, "
								   + "key integer NOT NULL, "
								   + "PRIMARY KEY (document_name))");
			}
			if(!(tblsExistant[4]))
			{
				stmt.executeUpdate("CREATE TABLE OneTimePad ("
								   + "document_name varchar(255) NOT NULL, "
								   + "text_len integer NOT NULL, "
								   + "key BLOB NOT NULL, "
								   + "PRIMARY KEY (document_name))");
			}
			if(!(tblsExistant[5]))
			{
				stmt.executeUpdate("CREATE TABLE RailFence ("
								   + "document_name varchar(255) NOT NULL, "
								   + "key integer NOT NULL, "
								   + "use_punct BLOB NOT NULL, "
								   + "PRIMARY KEY (document_name))");
			}
			if(!(tblsExistant[6]))
			{
				stmt.executeUpdate("CREATE TABLE Vigenere ("
								   + "document_name varchar(255) NOT NULL, "
								   + "key varchar(255) NOT NULL, "
								   + "use_punct BLOB NOT NULL, "
								   + "PRIMARY KEY (document_name))");
			}
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
	
	protected void addKey(InheritableKey key, int keyType, String docName)
	{
		ArrayList<ByteBuffer> components = key.getComponents();
		if(keyType == 1)
		{
			
		}
		else if(keyType == 2)
		{
			
		}
		else if(keyType == 3)
		{
			
		}
		else if(keyType == 4)
		{
			
		}
		else if(keyType == 5)
		{
			
		}
		else if(keyType == 6)
		{
			
		}
		else
		{
			
		}
	}
}

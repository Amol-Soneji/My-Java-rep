/**
 * 
 */
package main;

import CipherKeys.InheritableKey;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
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
			rSet.close();
			stmt.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			try 
			{
				this.conn.close(); //Release resource.  
			} 
			catch (SQLException e1) 
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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
		components.forEach((n) -> n.rewind());
		try 
		{
			if(keyType == 1)
			{
				Blob keyVal = conn.createBlob();
				Blob IV = conn.createBlob();
				keyVal.setBytes(1, components.get(0).array());
				IV.setBytes(1, components.get(1).array());
				int authTagLength = components.get(2).getInt();
				String stmt = "INSERT INTO AES-GCM "
							  + "values(?, ?, ?, ?)";
				PreparedStatement pStmt = conn.prepareStatement(stmt);
				pStmt.setString(1, docName);
				pStmt.setBlob(2, keyVal);
				pStmt.setBlob(3, IV);
				pStmt.setInt(4, authTagLength);
				pStmt.executeUpdate();
				pStmt.close();
			}
			else if(keyType == 2)
			{
				Blob keyVal = conn.createBlob();
				Blob IV = conn.createBlob();
				keyVal.setBytes(1, components.get(0).array());
				IV.setBytes(1, components.get(1).array());
				String stmt = "INSERT INTO AES-CBC "
							  + "values(?, ?, ?)";
				PreparedStatement pStmt = conn.prepareStatement(stmt);
				pStmt.setString(1, docName);
				pStmt.setBlob(2, keyVal);
				pStmt.setBlob(3, IV);
				pStmt.executeUpdate();
				pStmt.close();
			}
			else if(keyType == 3)
			{
				int keyVal = components.get(0).getInt();
				int affineDecKey = components.get(1).getInt();
				int arbitraryB = components.get(2).getInt();
				Statement stmt = conn.createStatement();
				stmt.executeUpdate("INSERT INTO Affine "
								   + "values("+ docName + ", " + keyVal + ", " + affineDecKey + ", " + arbitraryB + ")");
				stmt.close();
			}
			else if(keyType == 4)
			{
				int keyVal = components.get(0).getInt();
				Statement stmt = conn.createStatement();
				stmt.executeUpdate("INSERT INTO Caeser "
								   + "values(" + docName + ", " + keyVal + ")");
				stmt.close();
			}
			else if(keyType == 5)
			{
				int textLength = components.get(0).getInt();
				Blob keyVal = conn.createBlob();
				keyVal.setBytes(1, components.get(1).array());
				String stmt = "INSERT INTO OneTimePad "
							  + "values(?, ?, ?)";
				PreparedStatement pStmt = conn.prepareStatement(stmt);
				pStmt.setString(1, docName);
				pStmt.setInt(2, textLength);
				pStmt.setBlob(3, keyVal);
				pStmt.executeUpdate();
				pStmt.close();
			}
			else if(keyType == 6)
			{
				int keyVal = components.get(0).getInt();
				Blob usePunct = conn.createBlob();
				usePunct.setBytes(1, components.get(1).array());
				String stmt = "INSERT INTO RailFence "
							  + "values(?, ?, ?)";
				PreparedStatement pStmt = conn.prepareStatement(stmt);
				pStmt.setString(1, docName);
				pStmt.setInt(2, keyVal);
				pStmt.setBlob(3, usePunct);
				pStmt.executeUpdate();
				pStmt.close();
			}
			else
			{
				String keyVal = new String(components.get(0).array(), "UTF-16");
				Blob usePunct = conn.createBlob();
				usePunct.setBytes(1, components.get(1).array());
				String stmt = "INSERT INTO Vigenere "
							  + "values(?, ?, ?)";
				PreparedStatement pStmt = conn.prepareStatement(stmt);
				pStmt.setString(1, docName);
				pStmt.setString(2, keyVal);
				pStmt.setBlob(3, usePunct);
				pStmt.executeUpdate();
				pStmt.close();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("There was an internal program error in adding the key to the database.  \n"
							   + "copy and save the following information as the key in ByteBuffer \n"
							   + "format.  ");
			if(keyType == 1)
			{
				System.out.println("key: " + components.get(0).toString() + "\n"
								   + "IV: " + components.get(1).toString() + "\n"
								   + "authTagLength: " + components.get(2).toString());
			}
			else if(keyType == 2)
			{
				System.out.println("key: " + components.get(0).toString() + "\n"
								   + "IV: " + components.get(1).toString());
			}
			else if(keyType == 3)
			{
				System.out.println("key: " + components.get(0).toString() + "\n"
								   + "affineDecKey: " + components.get(1).toString() + "\n"
								   + "arbitraryB: " + components.get(2).toString());
			}
			else if(keyType == 4)
			{
				System.out.println("key: " + components.get(0).toString());
			}
			else if(keyType == 5)
			{
				System.out.println("key: " + components.get(1).toString() + "\n"
								   + "textLength: " + components.get(0).toString());
			}
			else if(keyType == 6)
			{
				System.out.println("key: " + components.get(0).toString() + "\n"
								   + "usePunct: " + components.get(1).toString());
			}
			else
			{
				System.out.println("key: " + components.get(0).toString() + "\n"
								   + "usePunct: " + components.get(1).toString());
			}
			for(int i = 0; i < 600000; i++)
			{
				i = i; //Wait for 60 seconds to let person copy info to another place.  
			}
			try
			{
				conn.close();
			}
			catch(SQLException eTwo)
			{
				eTwo.printStackTrace();
			}
			System.exit(1);
		}
	}
}
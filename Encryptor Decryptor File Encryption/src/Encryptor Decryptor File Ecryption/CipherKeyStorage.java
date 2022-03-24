/**
 * 
 */


import CipherKeys.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.sql.Blob;
import java.sql.SQLFeatureNotSupportedException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author Amol Soneji
 *
 */
public class CipherKeyStorage 
{
	private static final String FILE_PATH = "Keys.db"; //Default used by program.  
	private String dbName; //Used for user provided databases or user created databases.  
	private Connection conn;
	private boolean blobSupported;
	
	public CipherKeyStorage()
	{
		try 
		{
			this.conn = DriverManager.getConnection("jdbc:sqlite:" + FILE_PATH);
			checkDBStats();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			System.out.println("There was an internal error with the program.  \n"
							  + "The program will exit in 10 seconds.  ");
			for(int i = 0; i < 10000; i++)
			{
				//Do nothing.  
			}
			System.exit(1);
		}
	}
	
	public CipherKeyStorage(String dbName)
	{
		this.dbName = dbName;
		try
		{
			this.conn = DriverManager.getConnection("jdbc:sqlite:" + dbName);
			checkDBStats();
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
				//Do nothing.  
			}
			System.exit(1);
		}
	}
	
	protected void addKey(InheritableKey key, int keyType, String docName)
	{
		docName = "\'" + docName + "\'";
		ArrayList<ByteBuffer> components = key.getComponents();
		components.forEach((n) -> n.rewind());
		try 
		{
			if(keyType == 1)
			{
				if(blobSupported)
				{
					Blob keyVal = conn.createBlob();
					Blob IV = conn.createBlob();
					keyVal.setBytes(1, components.get(0).array());
					IV.setBytes(1, components.get(1).array());
					int authTagLength = components.get(2).getInt();
					String stmt = "INSERT INTO AES_GCM "
							  	  + "values(?, ?, ?, ?)";
					PreparedStatement pStmt = conn.prepareStatement(stmt);
					pStmt.setString(1, docName);
					pStmt.setBlob(2, keyVal);
					pStmt.setBlob(3, IV);
					pStmt.setInt(4, authTagLength);
					pStmt.executeUpdate();
					pStmt.close();
				}
				else
				{
					String keyValFile = docName.substring(0, docName.indexOf("\'")) + "0\'";
					String IVFile = docName.substring(0, docName.indexOf("\'")) + "1\'";
					int authTagLength = components.get(2).getInt();
					Statement stmt = conn.createStatement();
					stmt.executeUpdate("INSERT INTO AES_GCM "
									   + "values(" + docName + ", " + keyValFile + ", " + IVFile + ", " + authTagLength + ")");
					stmt.close();
					if(!createKeyFile(components.get(0), keyValFile) || !createKeyFile(components.get(1), IVFile))
						throw new Exception();
				}
			}
			else if(keyType == 2)
			{
				if(blobSupported)
				{
					Blob keyVal = conn.createBlob();
					Blob IV = conn.createBlob();
					keyVal.setBytes(1, components.get(0).array());
					IV.setBytes(1, components.get(1).array());
					String stmt = "INSERT INTO AES_CBC "
							  	  + "values(?, ?, ?)";
					PreparedStatement pStmt = conn.prepareStatement(stmt);
					pStmt.setString(1, docName);
					pStmt.setBlob(2, keyVal);
					pStmt.setBlob(3, IV);
					pStmt.executeUpdate();
					pStmt.close();
				}
				else
				{
					String keyValFile = docName.substring(0, docName.indexOf("\'")) + "0\'";
					String IVFile = docName.substring(0, docName.indexOf("\'")) + "1\'";
					Statement stmt = conn.createStatement();
					stmt.executeUpdate("INSERT INTO AES_CBC "
									   + "values(" + docName +", " + keyValFile + ", " + IVFile + ")");
					stmt.close();
					if(!createKeyFile(components.get(0), keyValFile) || !createKeyFile(components.get(1), IVFile))
						throw new Exception();
				}
			}
			else if(keyType == 3)
			{
				int keyVal = components.get(0).getInt();
				int affineDecKey = components.get(1).getInt();
				int arbitraryX = components.get(2).getInt();
				Statement stmt = conn.createStatement();
				stmt.executeUpdate("INSERT INTO Affine "
								   + "values("+ docName + ", " + keyVal + ", " + affineDecKey + ", " + arbitraryX + ")");
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
				if(blobSupported)
				{
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
				else
				{
					String keyValFile = docName.substring(0, docName.indexOf("\'")) + "1\'";
					Statement stmt = conn.createStatement();
					stmt.executeUpdate("INSERT INTO OneTimePad "
									   + "values(" + docName + "," + textLength + ", " + keyValFile + ")");
					stmt.close();
					if(!createKeyFile(components.get(1), keyValFile))
						throw new Exception();
				}
			}
			else if(keyType == 6)
			{
				int keyVal = components.get(0).getInt();
				if(blobSupported)
				{
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
					String usePunctFile = docName.substring(0, docName.indexOf("\'")) + "1\'";
					Statement stmt = conn.createStatement();
					stmt.executeUpdate("INSERT INTO RailFence "
									   + "values(" + docName + ", " + keyVal + ", " + usePunctFile + ")");
					stmt.close();
					if(!createKeyFile(components.get(1), usePunctFile))
						throw new Exception();
				}
			}
			else
			{
				String keyVal = new String(components.get(0).array(), "UTF-16");
				keyVal = "\'" + keyVal + "\'"; //SQLite syntax format for inserting a string value.  
				if(blobSupported)
				{
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
				else
				{
					String usePunctFile = docName.substring(0, docName.indexOf("\'")) + "1\'";
					Statement stmt = conn.createStatement();
					stmt.executeUpdate("INSERT INTO Vigenere "
									   + "values(" + docName + ", " + keyVal + ", " + usePunctFile + ")");
					stmt.close();
					if(!createKeyFile(components.get(1), usePunctFile))
						throw new Exception();
				}
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
								   + "arbitraryX: " + components.get(2).toString());
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
				//Wait for 60 seconds to let person copy info to another place.  
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
	
	protected InheritableKey getKey(String docName, int keyType)
	{
		try
		{
			ArrayList<ByteBuffer> byteBuffArr = new ArrayList<ByteBuffer>();
			Statement stmt = conn.createStatement();
			if(keyType == 1)
			{
				
			    ByteBuffer firstComp = ByteBuffer.allocate(16);
				ByteBuffer thirdComp = ByteBuffer.allocate(4);
				ResultSet rSet = stmt.executeQuery("SELECT * FROM AES_GCM "
								   + "WHERE document_name = " + docName);
				if(!rSet.next())
					return null;
				String toIgnore = rSet.getString(1); //Doc name.  
				if(blobSupported)
				{
					Blob keyVal = rSet.getBlob(2);
					Blob IVVal = rSet.getBlob(3);
					int authLenVal = rSet.getInt(4);
					byte[] IVbytes = IVVal.getBytes(1, (int)IVVal.length());
					ByteBuffer secondComp = ByteBuffer.allocate(IVbytes.length);
					byte[] keyBytes = keyVal.getBytes(1, (int)keyVal.length());
					byteBuffArr.add(firstComp.put(keyBytes));
					byteBuffArr.add(secondComp.put(IVbytes));
					byteBuffArr.add(thirdComp.putInt(authLenVal));
				}
				else
				{
					firstComp = readKeyFile(rSet.getString(1)).rewind();
					ByteBuffer IVVal = readKeyFile(rSet.getString(3)).rewind();
					int authLenVal = rSet.getInt(4);
					byteBuffArr.add(firstComp);
					byteBuffArr.add(IVVal);
					byteBuffArr.add(thirdComp.putInt(authLenVal));
				}
				InheritableKey toReturn = new BlockKey(byteBuffArr);
				toReturn.setComponents();
				rSet.close();
				stmt.close();
				return toReturn;
			}
			else if(keyType == 2)
			{
				ByteBuffer firstComp = ByteBuffer.allocate(16);
				ResultSet rSet = stmt.executeQuery("SELECT * FROM AES_CBC "
								   + "WHERE document_name = " + docName);
				if(!rSet.next())
					return null;
				String toIgnore = rSet.getString(1); //Doc name;
				if(blobSupported)
				{
					Blob keyVal = rSet.getBlob(2);
					Blob IVVal = rSet.getBlob(3);
					byte[] IVbytes = IVVal.getBytes(1, (int)IVVal.length());
					ByteBuffer secondComp = ByteBuffer.allocate(IVbytes.length);
					byte[] keyBytes = keyVal.getBytes(1, (int)keyVal.length());
					byteBuffArr.add(firstComp.put(keyBytes));
					byteBuffArr.add(secondComp.put(IVbytes));
				}
				else
				{
					firstComp = readKeyFile(rSet.getString(1)).rewind();
					ByteBuffer IVVal = readKeyFile(rSet.getString(2)).rewind();
					byteBuffArr.add(firstComp);
					byteBuffArr.add(IVVal);
				}
				InheritableKey toReturn = new BlockKey(byteBuffArr);
				toReturn.setComponents();
				rSet.close();
				stmt.close();
				return toReturn;
			}
			else if(keyType == 3)
			{
				ByteBuffer firstComp = ByteBuffer.allocate(4);
				ByteBuffer secondComp = ByteBuffer.allocate(4);
				ByteBuffer thirdComp = ByteBuffer.allocate(4);
				ResultSet rSet = stmt.executeQuery("SELECT * FROM Affine " 
								   + "WHERE document_name = " + docName);
				if(!rSet.next())
					return null;
				String toIgnore = rSet.getString(1);
				int keyVal = rSet.getInt(2);
				int affDecKeyVal = rSet.getInt(3);
				int arbittraryXVal = rSet.getInt(4);
				byteBuffArr.add(firstComp.putInt(keyVal));
				byteBuffArr.add(secondComp.putInt(affDecKeyVal));
				byteBuffArr.add(thirdComp.putInt(arbittraryXVal));
				InheritableKey toReturn = new SubstitutionKey(byteBuffArr);
				toReturn.setComponents();
				rSet.close();
				stmt.close();
				return toReturn;
			}
			else if(keyType == 4)
			{
				ByteBuffer firstComp = ByteBuffer.allocate(4);
				ResultSet rSet = stmt.executeQuery("SELECT * FROM Caeser "  
								   + "WHERE document_name = " + docName);
				if(!rSet.next())
					return null;
				String toIgnore = rSet.getString(1);
				int keyVal = rSet.getInt(2);
				byteBuffArr.add(firstComp.putInt(keyVal));
				InheritableKey toReturn = new SubstitutionKey(byteBuffArr);
				toReturn.getComponents();
				rSet.close();
				stmt.close();
				return toReturn;
			}
			else if(keyType == 5)
			{
				ByteBuffer firstComp = ByteBuffer.allocate(4);
				ResultSet rSet = stmt.executeQuery("SELECT * FROM OneTimePad "
									+ "WHERE document_name = " + docName);
				if(!rSet.next())
					return null;
				String toIgnore = rSet.getString(1);
				int textLenVal = rSet.getInt(2);
				if(blobSupported)
				{
					Blob keyBlob = rSet.getBlob(3);
					byte[] keyBytes = keyBlob.getBytes(1, (int)keyBlob.length());
					ByteBuffer secondComp = ByteBuffer.allocate(keyBytes.length);
					byteBuffArr.add(firstComp.putInt(textLenVal));
					byteBuffArr.add(secondComp.put(keyBytes));
				}
				else
				{
					ByteBuffer keyVal = readKeyFile(rSet.getString(3)).rewind();
					byteBuffArr.add(firstComp.putInt(textLenVal));
					byteBuffArr.add(keyVal);
				}
				InheritableKey toReturn = new OneTimePadKey(byteBuffArr);
				toReturn.getComponents();
				rSet.close();
				stmt.close();
				return toReturn;
			}
			else if(keyType == 6)
			{
				ByteBuffer firstComp = ByteBuffer.allocate(4);
				ResultSet rSet = stmt.executeQuery("SELECT * FROM RailFence "
									+ "WHERE document_name = " + docName);
				if(!rSet.next())
					return null;
				String toIgnore = rSet.getString(1);
				int keyVal = rSet.getInt(2);
				if(blobSupported)
				{
					Blob usePunctVal = rSet.getBlob(3);
					byte[] usePunctBytes = usePunctVal.getBytes(1, (int)usePunctVal.length());
					ByteBuffer secondComp = ByteBuffer.allocate(usePunctBytes.length);
					byteBuffArr.add(firstComp.putInt(keyVal));
					byteBuffArr.add(secondComp.put(usePunctBytes));
				}
				else
				{
					ByteBuffer usePunctVal = readKeyFile(rSet.getString(3)).rewind();
					byteBuffArr.add(firstComp.putInt(keyVal));
					byteBuffArr.add(usePunctVal);
				}
				InheritableKey toReturn = new TranspositionKey(byteBuffArr);
				toReturn.getComponents();
				rSet.close();
				stmt.close();
				return toReturn;
			}
			else
			{
				ResultSet rSet = stmt.executeQuery("SELECT * FROM Vigenere "
									+ "WHERE document_name = " + docName);
				if(!rSet.next())
					return null;
				String toIgnore = rSet.getString(1);
				String keyVal = rSet.getString(2);
				if(blobSupported)
				{
					Blob usePunctVal = rSet.getBlob(3);
					ByteBuffer firstComp = ByteBuffer.allocate(keyVal.length());
					byte[] usePunctBytes = usePunctVal.getBytes(1, (int)usePunctVal.length());
					ByteBuffer secondComp = ByteBuffer.allocate(usePunctBytes.length);
					byteBuffArr.add(firstComp.put(keyVal.getBytes("UTF-16")));
					byteBuffArr.add(secondComp.put(usePunctBytes));
				}
				else
				{
					ByteBuffer usePunctVal = readKeyFile(rSet.getString(3)).rewind();
					ByteBuffer firstComp = ByteBuffer.allocate(keyVal.length());
					byteBuffArr.add(firstComp);
					byteBuffArr.add(usePunctVal);
				}
				InheritableKey toReturn = new PolyalphabeticKey(byteBuffArr);
				toReturn.getComponents();
				rSet.close();
				stmt.close();
				return toReturn;
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("There was an internal error in the program.  Sorry .  "
					   + "If you are trying to unencrypt a file, \n" 
					   + "and have written down the key and other info, \n"
					   + "try to use the option of manual key entry.  The \n"
					   + "program will close in 10 seconds.  ");
			for(int i = 0; i < 10000; i++)
			{
				//Do nothing.  
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
		return null; //Some compilers complain.  There does not seem to be any logic leak in this method.  
	}
	
	protected void removeKey(String docName, int keyType)
	{
		try
		{
			Statement stmt = conn.createStatement();
			if(keyType == 1)
			{
				stmt.executeUpdate("DELETE FROM AES_GCM WHERE document_name = " + docName);
				System.out.println("Key for document " + docName + "successfully deleted.  ");
			}
			else if(keyType == 2)
			{
				stmt.executeUpdate("DELETE FROM AES_CBC WHERE document_name = " + docName);
				System.out.println("Key for document " + docName + "successfully deleted.  ");
			}
			else if(keyType == 3)
			{
				stmt.executeUpdate("DELETE FROM Affine WHERE document_name = " + docName);
				System.out.println("Key for document " + docName + "successfully deleted.  ");
			}
			else if(keyType == 4)
			{
				stmt.executeUpdate("DELETE FROM Caeser WHERE document_name = " + docName);
				System.out.println("Key for document " + docName + "successfully deleted.  ");
			}
			else if(keyType == 5)
			{
				stmt.executeUpdate("DELETE FROM OneTimePad WHERE document_name = " + docName);
				System.out.println("Key for document " + docName + "successfully deleted.  ");
			}
			else if(keyType == 6)
			{
				stmt.executeUpdate("DELETE FROM RailFence WHERE document_name = " + docName);
				System.out.println("Key for document " + docName + "successfully deleted.  ");
			}
			else
			{
				stmt.executeUpdate("DELETE FROM Vigenere WHERE document_name = " + docName);
				System.out.println("Key for document " + docName + "successfully deleted.  ");
			}
			stmt.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("There was an internal error int the program.  Sorry.  \n"
							   + "Unable to remove key.  \n"
							   + "Program will close in 10 seconds.  ");
			for(int i = 0; i < 10000; i++)
			{
				//Do nothing.  
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
	
	protected void closeDB()
	{
		try
		{
			conn.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			System.out.println("There was an error closing the database.  \n"
							   + "Program will close in 10 seconds.  ");
			for(int i = 0; i < 10000; i++)
			{
				//Do nothing.  
			}
			System.exit(1);
		}
	}
	
	private void checkDBStats()
	{
		try
		{
			try
			{
				Blob test = conn.createBlob();
				blobSupported = true;
			}
			catch(SQLFeatureNotSupportedException e)
			{
				blobSupported = false;
				System.out.println("Due to limitations in the database driver, some parts of the key will be stored \n"
								   + "in file(s).  These key files will be deleted when the encrypted file is decrypted.  ");
			}
			boolean[] tblsExistant = new boolean[7];
			Statement stmt = conn.createStatement();
			ResultSet rSet = stmt.executeQuery("SELECT name FROM sqlite_master"); //Check to see if any of the tables needed 
			//by this program are not in the database yet.  
			while(rSet.next())
			{
				String tblName = rSet.getString(1);
				if(tblName.equalsIgnoreCase("AES_GCM"))
					tblsExistant[0] = true;
				else if(tblName.equalsIgnoreCase("AES_CBC"))
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
					tblName = tblName; //Do nothing, user defined table if using user defined db.  
			}
			//Now create any of the missing tables if needed.  
			if(!(tblsExistant[0]))
			{
				if(blobSupported)
				{
					stmt.executeUpdate("CREATE TABLE AES_GCM ("
								   	   + "document_name varchar(255) NOT NULL, "
								   	   + "key BLOB NOT NULL, "
								   	   + "iv BLOB NOT NULL, "
								   	   + "auth_tag_len integer NOT NULL, "
								   	   + "PRIMARY KEY (document_name))");
				}
				else
				{
					stmt.executeUpdate("CREATE TABLE AES_GCM ("
									   + "document_name varchar(255) NOT NULL, "
									   + "key_file varchar(255) NOT NULL, "
									   + "iv_file varchar(255) NOT NULL, "
									   + "auth_tag_len integer NOT NULL, "
									   + "PRIMARY KEY (document_name))");
				}
			}
			if(!(tblsExistant[1]))
			{
				if(blobSupported)
				{
					stmt.executeUpdate("CREATE TABLE AES_CBC ("
								   	   + "document_name varchar(255) NOT NULL, "
								   	   + "key BLOB NOT NULL, "
								   	   + "iv BLOB NOT NULL, "
								   	   + "PRIMARY KEY (document_name))");
				}
				else
				{
					stmt.executeUpdate("CREATE TABLE AES_CBC ("
									   + "document_name varchar(255) NOT NULL, "
									   + "key_file varchar(255) NOT NULL, "
									   + "iv varchar(255) NOT NULL, "
									   + "PRIMARY KEY (document_name))");
				}
			}
			if(!(tblsExistant[2]))
			{
				stmt.executeUpdate("CREATE TABLE Affine ("
								   + "document_name varchar(255) NOT NULL, "
								   + "key integer NOT NULL, "
								   + "affine_dec_key integer NOT NULL, "
								   + "arbitrary_x integer NOT NULL, "
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
				if(blobSupported)
				{
					stmt.executeUpdate("CREATE TABLE OneTimePad ("
								   	   + "document_name varchar(255) NOT NULL, "
								   	   + "text_len integer NOT NULL, "
								   	   + "key BLOB NOT NULL, "
								   	   + "PRIMARY KEY (document_name))");
				}
				else
				{
					stmt.executeUpdate("CREATE TABLE OneTimePad ("
									   + "document_name varchar(255) NOT NULL, "
									   + "text_len integer NOT NULL, "
									   + "key_file varchar(255) NOT NULL, "
									   + "PRIMARY KEY (document_name))");
				}
			}
			if(!(tblsExistant[5]))
			{
				if(blobSupported)
				{
					stmt.executeUpdate("CREATE TABLE RailFence ("
								   	   + "document_name varchar(255) NOT NULL, "
								   	   + "key integer NOT NULL, "
								   	   + "use_punct BLOB NOT NULL, "
								   	   + "PRIMARY KEY (document_name))");
				}
				else
				{
					stmt.executeUpdate("CREATE TABLE RailFence ("
									   + "document_name varchar(255) NOT NULL, "
									   + "key integer NOT NULL, "
									   + "use_punct_file varchar(255) NOT NULL, "
									   + "PRIMARY KEY (document_name))");
				}
			}
			if(!(tblsExistant[6]))
			{
				if(blobSupported)
				{
					stmt.executeUpdate("CREATE TABLE Vigenere ("
								   	   + "document_name varchar(255) NOT NULL, "
								   	   + "key varchar(255) NOT NULL, "
								   	   + "use_punct BLOB NOT NULL, "
								   	   + "PRIMARY KEY (document_name))");
				}
				else
				{
					stmt.executeUpdate("CREATE TABLE Vigenere ("
									   + "document_name varchar(255) NOT NULL, "
									   + "key varchar(255) NOT NULL, "
									   + "use_punct_file varchar(255) NOT NULL, "
									   + "PRIMARY KEY (document_name))");
				}
			}
			rSet.close();
			stmt.close();
			if(!conn.isClosed())
				System.out.println("Still working.  ");
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
				//Do nothing.  
			}
			System.exit(1);
		}
	}
	
	//Will be used temporarily till JDBC SQLite driver supports BLOBS.  
	private boolean createKeyFile(ByteBuffer keyContents, String keyFileName)
	{
		try
		{
			File keyComponentFile = new File(keyFileName);
			boolean existance = false;
			if(keyComponentFile.isFile())
				existance = true;
			if(existance)
				return false;
			keyContents.rewind();
			byte[] toWrite = keyContents.array();
			FileOutputStream fileCreatorStream = new FileOutputStream(keyComponentFile);
			fileCreatorStream.write(toWrite);
			fileCreatorStream.close();
			return true;
		}
		catch(SecurityException e)
		{
			e.printStackTrace();
			closeDB();
			System.out.println("This program requires read and write access inorder to work.  Please fix this issue \n"
							   + "and start the program again.  Program exiting in 10 seconds.  ");
			for(int i = 0; i < 10000; i++)
			{
				//Do nothing.  
			}
			System.exit(1);
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
			System.out.println("Internal program error.  ");
			closeDB();
			System.exit(1);
		}
		catch(IOException e)
		{
			e.printStackTrace();
			System.out.println("Error in writing the file.  ");
			closeDB();
			System.exit(1);
		}
		return false; //Never reached, but compiler complains it is required.  
	}
	
	//Will be used temporarily till JDBC SQLite driver supports BLOBS.  
	private ByteBuffer readKeyFile(String keyFileName)
	{
		ByteBuffer componentBuffer = null;
		try
		{
			File componentFile = new File(keyFileName);
			if(!componentFile.isFile())
			{
				System.out.println("Key component file " + keyFileName + "not found.  Please \n "
								   + "please try again later.  ");
				componentBuffer = ByteBuffer.allocate(1);
				componentBuffer.put((byte) -1);
			}
			FileInputStream componentStream = new FileInputStream(componentFile);
			byte[] componentContent = componentStream.readAllBytes();
			componentStream.close();
			componentBuffer = ByteBuffer.allocate(componentContent.length);
			componentBuffer.put(componentContent);
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
			System.out.println("Internal error with the program.  ");
			closeDB();
			System.exit(1);
		}
		catch(SecurityException e)
		{
			e.printStackTrace();
			closeDB();
			System.out.println("This program requires read and write access inorder to work.  Please fix this issue \n"
					   		   + "and start the program again.  Program exiting in 10 seconds.  ");
			for(int i = 0; i < 10000; i++)
			{
				//Do nothing.  
			}
			System.exit(1);
		}
		catch(IOException e)
		{
			e.printStackTrace();
			System.out.println("Error in reading the file.  ");
			closeDB();
			System.exit(1);
		}
		catch(OutOfMemoryError e)
		{
			e.printStackTrace();
			System.out.println("Not enough memory to continue running program.  ");
			closeDB();
			System.exit(1);
		}
		componentBuffer.rewind();
		return componentBuffer;
	}
}

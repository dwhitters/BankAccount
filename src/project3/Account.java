package project3;

import java.io.*;
import java.util.*;

public abstract class Account implements Serializable {
	
	//instance variables
	private static final long serialVersionUID = 1L;
	private int accountNumber;
	private String owner;
	private GregorianCalendar dateOpened;
	private double balance;
	
	public Account(int accountNumber, String owner, 
					GregorianCalendar dateOpened, double balance)
	{
		//adds general data to account
		this.accountNumber = accountNumber;
		this.owner = owner;
		this.dateOpened = dateOpened;
		this.balance = balance;
	}	
	
	public int getAccountNumber()
	{
		//returns account number
		return this.accountNumber;
	}
	
	public void setNumber(int accountNumber)
	{
		//sets account number
		this.accountNumber = accountNumber;
	}
	
	public String getOwner()
	{
		//returns owner
		return this.owner;
	}
	
	public void setOwner(String owner)
	{
		//sets owner
		this.owner = owner;
	}
	
	public void setBalance(double balance)
	{
		//sets balance
		this.balance = balance;
	}
	
	public double getBalance()
	{
		//returns balance
		return this.balance;
	}
	
	public GregorianCalendar getDateOpened()
	{
		//returns date opened
		return dateOpened;
	}
	
	public void setDateOpened(GregorianCalendar dateOpened)
	{
		//sets date opened
		this.dateOpened = dateOpened;
	}

	//returns all account data in String format
	public abstract String toString();
	
	public static ArrayList<Account> loadDatabase (String filename) 
			throws IOException{
		ArrayList<Account> accounts = null;
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		
		try{
			// Read from disk using FileInputStream
			fis = new FileInputStream(filename);
			// Read object using ObjectInputStream
			ois = new ObjectInputStream (fis);
			// Read an object
			Object obj = ois.readObject();
			if (obj instanceof ArrayList)
			{
				// Cast object to accounts as an ArrayList containing 
				// Account objects
				accounts = (ArrayList<Account>) obj;
			}
		}
		catch(Exception e){}
		finally{
			//Close both input streams
			fis.close();
			ois.close();
		}

		return accounts;
	}
	public static void saveDatabase (String filename) 
			throws IOException{
		FileOutputStream fos = null;
		ObjectOutputStream os = null;
		
		try{
			fos = new FileOutputStream(filename);
			os = new ObjectOutputStream(fos);
			os.writeObject(BankModel.accounts);		
		}
		catch(Exception e){}
		finally{
			//Close both output streams
			fos.close();
			os.close();
		}
	}
}
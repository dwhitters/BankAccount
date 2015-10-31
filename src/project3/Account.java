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
	
	public String getStrDateOpened(){
		String date = Integer.toString(
				dateOpened.get(dateOpened.MONTH)+1) + "/" + 
				Integer.toString(
						dateOpened.get(dateOpened.DAY_OF_MONTH)) + 
						"/" + Integer.toString(
								dateOpened.get(dateOpened.YEAR));
		return date;
	}
}
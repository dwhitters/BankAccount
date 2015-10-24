package project3;

import java.io.Serializable;
import java.util.GregorianCalendar;

public abstract class Account implements Serializable {
	private static final long serialVersionUID = 1L;
	private int number;
	private String owner;
	private GregorianCalendar dateOpened;
	private double balance;
	
	public int getNumber()
	{
		return this.number;
	}
	
	public void setNumber(int number)
	{
		this.number = number;
	}
	
	public String getOwner()
	{
		return this.owner;
	}
	
	public void setOwner(String owner)
	{
		this.owner = owner;
	}
	
	public void setBalance(double balance)
	{
		this.balance = balance;
	}
	
	public double getBalance()
	{
		return this.balance;
	}
	
	public abstract String toString();
}
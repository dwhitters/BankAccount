package project3;

import java.util.GregorianCalendar;

public class CheckingAccount extends Account{

	//instance variables
	private static final long serialVersionUID = 1L;
	private double monthlyFee;
	
	public CheckingAccount(int number, String owner, GregorianCalendar dateOpened, double balance, double monthlyFee)
	{
		//sends general data to Account class
		super(number, owner, dateOpened, balance);
		
		//sets CheckingAccount specific data
		this.monthlyFee = monthlyFee;
	}
	
	public double getMonthlyFee()
	{
		//returns monthly fee
		return this.monthlyFee;
	}
	
	public void setMonthlyFee(double monthlyFee)
	{
		//sets monthly fee
		this.monthlyFee = monthlyFee;
	}
	
	public String toString()
	{
		//create String with checking account information
		String accountInformation = "Account Number: " + getAccountNumber() + "     AccountOwner: " + getOwner() + "     Date Opened: " + getDateOpened() + "     Account Balance: " + getBalance() + "     Monthly Fee: " + getMonthlyFee();
		
		//return string
		return accountInformation;
	}
}

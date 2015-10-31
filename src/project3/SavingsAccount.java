package project3;

import java.util.GregorianCalendar;

public class SavingsAccount extends Account{
	
	//instance variables
	private static final long serialVersionUID = 1L;
	private double minimumBalance;
	private double interestRate;
	
	public SavingsAccount(int number, String owner, GregorianCalendar dateOpened, double balance, double minimumBalance, double interestRate)
	{
		super(number, owner, dateOpened, balance);
		this.minimumBalance = minimumBalance;
		this.interestRate = interestRate;
	}
	
	public void setMinimumBalance(double minimumBalance)
	{
		this.minimumBalance = minimumBalance;
	}
	
	public double getMinimumBalance()
	{
		return this.minimumBalance;
	}
	
	public void setInterestRate(double interestRate)
	{
		this.interestRate = interestRate;
	}
	
	public double getInterestRate()
	{
		return this.interestRate;
	}
	
	public String toString()
	{
		String output = "Account Type:\tS\tAccount Number:\t" + getAccountNumber() + 
				"\tAccountOwner:\t" + getOwner() + "\tDate Opened:\t" + 
				getStrDateOpened() + "\tAccount Balance:\t" + getBalance() +
				"\tInterest Rate:\t" + getInterestRate() +
				"\tMinimumBalance:\t" + getMinimumBalance();
		return output;
	}

}

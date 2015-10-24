package project3;

import java.util.GregorianCalendar;

public class CheckingAccount extends Account{

	private static final long serialVersionUID = 1L;
	private double monthlyFee;
	
	public CheckingAccount(int number, String owner, GregorianCalendar dateOpened, double balance, double monthlyFee)
	{
		super(number, owner, dateOpened, balance);
		this.monthlyFee = monthlyFee;
	}
	
	public double getMonthlyFee()
	{
		return this.monthlyFee;
	}
	
	public void setMonthlyFee(double monthlyFee)
	{
		this.monthlyFee = monthlyFee;
	}
	
	public String toString()
	{
		String output = "Account Number: " + getNumber() + "     AccountOwner: " + getOwner() + "     Date Opened: " + getDateOpened() + "     Account Balance: " + getBalance() + "     Monthly Fee: " + getMonthlyFee();
		return output;
	}
}

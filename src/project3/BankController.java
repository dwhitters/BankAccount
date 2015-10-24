package project3;

import java.util.GregorianCalendar;

public class BankController {
	
	public BankController()
	{
		
	}
	
	public void add(int number, String owner, GregorianCalendar dateOpened, double balance, double minimumBalance, double interestRate)
	{
		SavingsAccount addedSavingsAccount = new SavingsAccount(number, owner, dateOpened, balance, minimumBalance, interestRate);
	}
	
	public void add(int number, String owner, GregorianCalendar dateOpened, double balance, double monthlyFee)
	{
		CheckingAccount addedSavingsAccount = new CheckingAccount(number, owner, dateOpened, balance, monthlyFee);
	}
	
	public void delete()
	{
		
	}
	
	public void update()
	{
		
	}
	
	public void clear()
	{
		
	}
}

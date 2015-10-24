package project3;

import java.util.GregorianCalendar;

import javax.swing.table.AbstractTableModel;

public class BankModel extends AbstractTableModel{
	
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
	
	public Object getElementAt(int index)
	{
		return new Object();
	}
	
	public int getSize()
	{
		return 0;
	}

	public int getColumnCount() {

		return 0;
	}

	public int getRowCount() {

		return 0;
	}

	public Object getValueAt(int arg0, int arg1) {

		return null;
	}
}

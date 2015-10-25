package project3;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import javax.swing.table.AbstractTableModel;

public class BankModel extends AbstractTableModel {

	//storage for accounts
	private ArrayList<Account> accounts = new ArrayList<Account>();
	
	//format variables
	private SimpleDateFormat date = new SimpleDateFormat("dd/mm/yyyy");
	
	//table information
	private String columnNames [];

	public BankModel() {
		//create and add hard-coded accounts
		SavingsAccount savingsTest1 = new SavingsAccount(123, "Bob",
				new GregorianCalendar(), 100, .01, 5.00);
		SavingsAccount savingsTest2 = new SavingsAccount(123, "Allen",
				new GregorianCalendar(), 100, .01, 5.00);
		accounts.add(savingsTest1);
		accounts.add(savingsTest2);
		
		//set column names
		columnNames = new String []{ "Account Number", "Account Owner",
					"Date Opened", "Account Balance", "Monthly Fee",
					"Interest Rate", "Minimum Balance" };
	}
	
	public void update(int number, String owner,
			GregorianCalendar dateOpened, double balance,
			double monthlyFee, double interestRate,
			double minimumBalance, boolean isSavings, int selectedRow){
		//update data depending on account type
		if (isSavings == true) {
			//update data
			accounts.get(selectedRow).setNumber(number);
			accounts.get(selectedRow).setOwner(owner);
			accounts.get(selectedRow).setDateOpened(dateOpened);
			accounts.get(selectedRow).setBalance(balance);
			((SavingsAccount)accounts.get(selectedRow)).setInterestRate(interestRate);
			((SavingsAccount)accounts.get(selectedRow)).setMinimumBalance(minimumBalance);
		} else {
			//update data
			accounts.get(selectedRow).setNumber(number);
			accounts.get(selectedRow).setOwner(owner);
			accounts.get(selectedRow).setDateOpened(dateOpened);
			accounts.get(selectedRow).setBalance(balance);
			((CheckingAccount)accounts.get(selectedRow)).setMonthlyFee(monthlyFee);
		}
		
		//tell table to update
		this.fireTableDataChanged();
	}

	public void add(int number, String owner,
			GregorianCalendar dateOpened, double balance,
			double monthlyFee, double interestRate,
			double minimumBalance, boolean isSavings) {
		//add new data to account type
		if (isSavings == true) {
			//create savings account
			SavingsAccount addedSavingsAccount = new SavingsAccount(
					number, owner, dateOpened, balance, minimumBalance,
					interestRate);
			
			//add savings account to array
			accounts.add(addedSavingsAccount);
		} else {
			//create checking account
			CheckingAccount addedCheckingAccount = new CheckingAccount(
					number, owner, dateOpened, balance, monthlyFee);
			
			//add checking account to array
			accounts.add(addedCheckingAccount);
		}
		
		//tell table to update
		this.fireTableDataChanged();
	}

	public void delete(int selectedRow) {
		//delete defined row
		accounts.remove(selectedRow);
		
		//tell table to update
		this.fireTableDataChanged();
	}

	public Account getAccountInRow(int row) {
		//return account in defined row
		return accounts.get(row);
	}

	@Override
	public int getColumnCount() {
		//return number of columns
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		//return number of accounts
		return accounts.size();
	}

	@Override
	public String getColumnName(int column) {
		//return column name at defined column
		return columnNames[column];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		//find value to return
		if (accounts.get(rowIndex) instanceof SavingsAccount) {
			switch (columnIndex) {
			case 0:
				return accounts.get(rowIndex).getAccountNumber();
			case 1:
				return accounts.get(rowIndex).getOwner();
			case 2:
				return date.format(accounts.get(rowIndex).getDateOpened().getTime());
			case 3:
				return accounts.get(rowIndex).getBalance();
			case 4:
				return "";
			case 5:
				return ((SavingsAccount) accounts.get(rowIndex))
						.getInterestRate();
			case 6:
				return ((SavingsAccount) accounts.get(rowIndex))
						.getMinimumBalance();
			}
		} else {
			switch (columnIndex) {
			case 0:
				return accounts.get(rowIndex).getAccountNumber();
			case 1:
				return accounts.get(rowIndex).getOwner();
			case 2:
				return date.format(accounts.get(rowIndex).getDateOpened().getTime());
			case 3:
				return accounts.get(rowIndex).getBalance();
			case 4:
				return ((CheckingAccount) accounts.get(rowIndex))
						.getMonthlyFee();
			case 5:
				return "";
			case 6:
				return "";
			}
		}

		//default return
		return " ";
	}
}

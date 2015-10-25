package project3;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import javax.swing.table.AbstractTableModel;

public class BankModel extends AbstractTableModel {

	private ArrayList<Account> accounts = new ArrayList(10);

	public BankModel() {
		SavingsAccount savingsTest1 = new SavingsAccount(123, "Bob",
				new GregorianCalendar(), 100, .01, 5.00);
		SavingsAccount savingsTest2 = new SavingsAccount(123, "Allen",
				new GregorianCalendar(), 100, .01, 5.00);
		accounts.add(savingsTest1);
		accounts.add(savingsTest2);
	}

	public void add(int number, String owner,
			GregorianCalendar dateOpened, double balance,
			double monthlyFee, double interestRate,
			double minimumBalance) {

		if (monthlyFee == 0) {
			SavingsAccount addedSavingsAccount = new SavingsAccount(
					number, owner, dateOpened, balance, minimumBalance,
					interestRate);
			accounts.add(addedSavingsAccount);
		} else {
			CheckingAccount addedCheckingAccount = new CheckingAccount(
					number, owner, dateOpened, balance, monthlyFee);
			accounts.add(addedCheckingAccount);
		}
		this.fireTableDataChanged();
	}

	public void delete(int selectedRow) {
		accounts.remove(selectedRow);
		this.fireTableDataChanged();
	}

	public void update() {

	}

	@Override
	public int getColumnCount() {

		return 7;
	}

	@Override
	public int getRowCount() {

		return accounts.size();
	}

	@Override
	public String getColumnName(int column) {

		String columnNames[] = { "Account Number", "Account Owner",
				"Date Opened", "Account Balance", "Monthly Fee",
				"Interest Rate", "Minimum Balance" };
		return columnNames[column];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		Object output = new Object();
		// row is index in array list
		// column is data piece of array list object
		if (accounts.get(rowIndex) instanceof SavingsAccount) {
			switch (columnIndex) {
			case 0:
				return accounts.get(rowIndex).getAccountNumber();
			case 1:
				return accounts.get(rowIndex).getOwner();
			case 2:
				return accounts.get(rowIndex).getDateOpened();
			case 3:
				return accounts.get(rowIndex).getBalance();
			case 4:
				return " ";
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
				return accounts.get(rowIndex).getDateOpened();
			case 3:
				return accounts.get(rowIndex).getBalance();
			case 4:
				return ((CheckingAccount) accounts.get(rowIndex))
						.getMonthlyFee();
			case 5:
				return " ";
			case 6:
				return " ";
			}
		}

		return " ";
	}
}

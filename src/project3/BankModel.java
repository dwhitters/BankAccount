package project3;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import javax.swing.table.AbstractTableModel;

public class BankModel extends AbstractTableModel {

	//storage for accounts
	public static ArrayList<Account> accounts = new ArrayList<Account>();
	
	//format variables
	private SimpleDateFormat date = new SimpleDateFormat("MM/dd/yyyy");
	
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
					"Interest Rate", "Minimum Balance"};
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
	
	public void loadBinary(String filename, AbstractTableModel bankModel){
		try {
			//Sets accounts equal to the ArrayList read from the 
			// file
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
			//update table
			this.fireTableDataChanged();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveBinary(String filename){
		try {
			//Saves the arraylist of accounts to the passed filename 
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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadText(String filename, AbstractTableModel bankModel){
		try {
			int fileLength = 0;
			String lineData = "";
			String strSplit[] = new String[14];
			String date[] = new String[3];
			int iDate[] = new int[3];
			ArrayList<String> savingsData = new ArrayList<String>();
			ArrayList<String> checkingData = new ArrayList<String>();
			FileReader fr = null;
			BufferedReader br = null;
			ArrayList<Account> accounts = new ArrayList<Account>();
			try{
				fr = new FileReader(filename);
				br = new BufferedReader(fr);
				
				while((lineData = br.readLine()) != null){
					//Empty ArrayList every loop, otherwise it continues to 
					// grow
					
					strSplit = lineData.split("\t");
					if(strSplit[1].equals("S")){
						savingsData.clear();
						for(int i = 0; i < 14; i++)
							if(i%2 == 1)
								savingsData.add(strSplit[i]);
						//Split date into 3 separate strings then convert
						// to integers into another array
						date = savingsData.get(3).split("/");
						for(int i = 0; i < 3; i++)
							iDate[i] = Integer.parseInt(date[i]);
						
						SavingsAccount act = new SavingsAccount(
								Integer.parseInt(savingsData.get(1)), 
								savingsData.get(2), 
								new GregorianCalendar(iDate[2], iDate[0], 
										iDate[1]), 
								Double.parseDouble(savingsData.get(4)), 
								Double.parseDouble(savingsData.get(6)), 
								Double.parseDouble(savingsData.get(5)));
						
						accounts.add(act);
					}
					else{
						checkingData.clear();
						for(int i = 0; i < 12; i++)
							if(i%2 == 1)
								checkingData.add(strSplit[i]);
						//Split date into 3 separate strings then convert
						// to integers into another array
						date = checkingData.get(3).split("/");
						for(int i = 0; i < 3; i++)
							iDate[i] = Integer.parseInt(date[i]);
						
						CheckingAccount act = new CheckingAccount(
								Integer.parseInt(checkingData.get(1)), 
								checkingData.get(2), 
								new GregorianCalendar(iDate[2], iDate[0], 
										iDate[1]), 
								Double.parseDouble(checkingData.get(4)), 
								Double.parseDouble(checkingData.get(5)));
						
						accounts.add(act);
					}
				}		
			}
			catch(Exception e){
				System.out.println(e.getMessage());
			}
			finally{
				fr.close();
				br.close();
			}
			this.fireTableDataChanged();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveText(String filename, AbstractTableModel bankModel){
		try{
			FileWriter write = null;
			PrintWriter pw = null;
			try{
				write = new FileWriter(filename);
				pw = new PrintWriter(write);
				
				for(int i = 0; i < BankModel.accounts.size(); i++){
					if(BankModel.accounts.get(i) instanceof SavingsAccount){
						SavingsAccount act = 
								(SavingsAccount)BankModel.accounts.get(i);
						pw.println(act.toString());
					}
					else{
						CheckingAccount act = 
								(CheckingAccount)BankModel.accounts.get(i);
						pw.println(act.toString());
					}
				}
			}
			catch(Exception e){}
			finally{
				write.close();
				pw.close();
			}
		}catch(Exception e){
			int i = 0;
		}
	}
}

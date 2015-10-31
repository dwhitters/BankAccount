package project3;

import java.io.*;
import java.util.*;
import java.text.*;

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
	
	public String getStrDateOpened(){
		String date = Integer.toString(
				dateOpened.get(dateOpened.MONTH)+1) + "/" + 
				Integer.toString(
						dateOpened.get(dateOpened.DAY_OF_MONTH)) + 
						"/" + Integer.toString(
								dateOpened.get(dateOpened.YEAR));
		return date;
	}
	
	public void setDateOpened(GregorianCalendar dateOpened)
	{
		//sets date opened
		this.dateOpened = dateOpened;
	}

	//returns all account data in String format
	public abstract String toString();
	
	//Binary
	public static ArrayList<Account> loadDatabase (String filename) 
			throws IOException{
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

		return accounts;
	}
	//Binary
	public static void saveDatabase (String filename) 
			throws IOException{
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
	}
	
	public static ArrayList<Account> loadTextDatabase(String filename) 
			throws IOException{
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
		return accounts;
	}
	
	public static void saveTextDatabase(String filename) 
			throws IOException{
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
	}
}
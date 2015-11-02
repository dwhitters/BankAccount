package project3;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import javax.swing.table.AbstractTableModel;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;

public class BankModel extends AbstractTableModel {

	// storage for accounts
	public static ArrayList<Account> accounts = new ArrayList<Account>();
	//format variables
	private SimpleDateFormat date = new SimpleDateFormat("MM/dd/yyyy");
	
	//table information
	private String columnNames [];

	public BankModel() {
		// create and add hard-coded accounts
		SavingsAccount savingsTest1 = new SavingsAccount(125, "Bob",
				new GregorianCalendar(2015, 10 - 1, 25), 100, .01, 5.00);
		SavingsAccount savingsTest2 = new SavingsAccount(123, "Allen",
				new GregorianCalendar(2016, 9 - 1, 30), 100, .01, 5.00);
		accounts.add(savingsTest1);
		accounts.add(savingsTest2);

		// set column names
		columnNames = new String[] { "Account Number", "Account Owner",
				"Date Opened", "Account Balance", "Monthly Fee",
				"Interest Rate", "Minimum Balance" };
	}

	public void update(int number, String owner,
			GregorianCalendar dateOpened, double balance,
			double monthlyFee, double interestRate,
			double minimumBalance, boolean isSavings, int selectedRow) {

		// update data depending on account type
		if (isSavings == true) {
			// update data
			accounts.get(selectedRow).setNumber(number);
			accounts.get(selectedRow).setOwner(owner);
			accounts.get(selectedRow).setDateOpened(dateOpened);
			accounts.get(selectedRow).setBalance(balance);
			((SavingsAccount) accounts.get(selectedRow))
					.setInterestRate(interestRate);
			((SavingsAccount) accounts.get(selectedRow))
					.setMinimumBalance(minimumBalance);
		} else {
			// update data
			accounts.get(selectedRow).setNumber(number);
			accounts.get(selectedRow).setOwner(owner);
			accounts.get(selectedRow).setDateOpened(dateOpened);
			accounts.get(selectedRow).setBalance(balance);
			((CheckingAccount) accounts.get(selectedRow))
					.setMonthlyFee(monthlyFee);
		}

		// tell table to update
		this.fireTableDataChanged();
	}

	public int add(int number, String owner,
			GregorianCalendar dateOpened, double balance,
			double monthlyFee, double interestRate,
			double minimumBalance, boolean isSavings) {
		int errorCode = 0;

		for(int i = 0; i < accounts.size(); i ++){
			if(accounts.get(i).getAccountNumber() == number)
				errorCode = 2;
		}
		if(minimumBalance <= balance && errorCode == 0){
			// add new data to account type
			if (isSavings == true) {
				// create savings account
				SavingsAccount addedSavingsAccount = new SavingsAccount(
						number, owner, dateOpened, balance, minimumBalance,
						interestRate);
	
				// add savings account to array
				accounts.add(addedSavingsAccount);
			} else {
				// create checking account
				CheckingAccount addedCheckingAccount = new CheckingAccount(
						number, owner, dateOpened, balance, monthlyFee);
	
				// add checking account to array
				accounts.add(addedCheckingAccount);
			}
	
			// tell table to update
			this.fireTableDataChanged();
		}
		else if(minimumBalance > balance)
			errorCode = 1;
		
		return errorCode;
	}

	public void delete(int selectedRow) {

		// delete defined row
		accounts.remove(selectedRow);

		// tell table to update
		this.fireTableDataChanged();
	}

	public Account getAccountInRow(int row) {

		// return account in defined row
		return accounts.get(row);
	}

	@Override
	public int getColumnCount() {

		// return number of columns
		return columnNames.length;
	}

	@Override
	public int getRowCount() {

		// return number of accounts
		return accounts.size();
	}

	@Override
	public String getColumnName(int column) {

		// return column name at defined column
		return columnNames[column];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		// find value to return
		if (accounts.get(rowIndex) instanceof SavingsAccount) {
			switch (columnIndex) {
			case 0:
				return accounts.get(rowIndex).getAccountNumber();
			case 1:
				return accounts.get(rowIndex).getOwner();
			case 2:
				return date.format(accounts.get(rowIndex)
						.getDateOpened().getTime());
			case 3:
				return String.format("$%.2f", accounts.get(rowIndex).getBalance());
			case 4:
				return "";
			case 5:
				return String.format("%.3f%%", ((SavingsAccount) accounts.get(rowIndex)).getInterestRate());
			case 6:
				return String.format("$%.2f", ((SavingsAccount) accounts.get(rowIndex))
						.getMinimumBalance());
			}
		} else {
			switch (columnIndex) {
			case 0:
				return accounts.get(rowIndex).getAccountNumber();
			case 1:
				return accounts.get(rowIndex).getOwner();
			case 2:
				return date.format(accounts.get(rowIndex)
						.getDateOpened().getTime());
			case 3:
				return String.format("$%.2f", accounts.get(rowIndex).getBalance());
			case 4:
				return String.format("$%.2f", ((CheckingAccount) accounts.get(rowIndex))
						.getMonthlyFee());
			case 5:
				return "";
			case 6:
				return "";
			}
		}
		// default return
		return " ";
	}
	
	public void sortByNum(){
		if(accounts.size() > 1){
			Collections.sort(accounts, new AccountNumberComparator());
			this.fireTableRowsUpdated(0, accounts.size() - 1);
		}
	}
	
	public void sortByName(){
		if(accounts.size() > 1){
			Collections.sort(accounts, new AccountOwnerComparator());
			this.fireTableRowsUpdated(0, accounts.size() - 1);
		}
	}
	
	public void sortByDateOpened()
	{
		Collections.sort(accounts, new DateComparator());
		this.fireTableDataChanged();
	}

	public void loadBinary(String filename) {
		try {
			// Sets accounts equal to the ArrayList read from the
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

	public void saveBinary(String filename) {

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

	public void loadText(String filename) {
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
	
	public void saveText(String filename){
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
		}catch(Exception e){}
	}
	
	public void loadXML(String filename){
		ArrayList<Account> accounts = new ArrayList<Account>();
		try {
			File fXmlFile = new File(filename);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
					
			doc.getDocumentElement().normalize();
					
			NodeList nList = doc.getElementsByTagName("Account");

			for (int i = 0; i < nList.getLength(); i++) {

				Node nNode = nList.item(i);
						
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					
					String type = eElement.getElementsByTagName("type").item(0).getTextContent();
					int number = Integer.parseInt(eElement.getAttribute("number"));
					String date = eElement.getElementsByTagName("date").item(0).getTextContent();
					Double balance = Double.parseDouble(eElement.getElementsByTagName("balance").item(0).getTextContent());
					String owner = eElement.getElementsByTagName("owner").item(0).getTextContent();
					Double minBalance = null;
					Double interestRate = null;
					Double monthlyFee = null;
					if(type.equals("C"))
						monthlyFee = Double.parseDouble(eElement.getElementsByTagName("monthlyFee").item(0).getTextContent());
					else{
						minBalance = Double.parseDouble(eElement.getElementsByTagName("minBalance").item(0).getTextContent());
						interestRate = Double.parseDouble(eElement.getElementsByTagName("interestRate").item(0).getTextContent());
					}
					
					String date1[] = new String[3];
					date1 = date.split("/");
					
					int month = Integer
							.parseInt(date1[0]);
					
					int day = Integer
							.parseInt(date1[1]);
					int year = Integer.parseInt(
							date1[2]);
					GregorianCalendar dateOpened = new GregorianCalendar(
							year, month - 1, day);
					
					if(type.equals("S")){
						SavingsAccount act = null;
						act = new SavingsAccount(number, owner, dateOpened, balance, minBalance, interestRate);
						accounts.add(act);
					}
					else{
						CheckingAccount act = null;
						act = new CheckingAccount(number, owner, dateOpened, balance, monthlyFee);
						accounts.add(act);
					}
				}
			}
		    } catch (Exception e) {
			e.printStackTrace();
		    }
		this.accounts = accounts;
		
		//Update the table with the new account values
		this.fireTableDataChanged();
	}

	public void saveToXML(String filename){
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("catalog");
			doc.appendChild(rootElement);
			
			for(int i = 0; i < accounts.size(); i++){
				// account elements
				Element account = doc.createElement("Account");
				rootElement.appendChild(account);
	
				// set attribute to staff element
				Attr num = doc.createAttribute("number");
				num.setValue(Integer.toString(accounts.get(i).getAccountNumber()));
				account.setAttributeNode(num);
	
				if(accounts.get(i) instanceof SavingsAccount){
					SavingsAccount act = (SavingsAccount)accounts.get(i);
					
					// type elements
					Element type = doc.createElement("type");
					type.appendChild(doc.createTextNode("S"));
					account.appendChild(type);
		
					// date elements
					Element date = doc.createElement("date");
					date.appendChild(doc.createTextNode(act.getStrDateOpened()));
					account.appendChild(date);
		
					// balance elements
					Element balance = doc.createElement("balance");
					balance.appendChild(doc.createTextNode(Double.toString(act.getBalance())));
					account.appendChild(balance);
		
					// owner elements
					Element owner = doc.createElement("owner");
					owner.appendChild(doc.createTextNode(act.getAccountOwner()));
					account.appendChild(owner);
					
					// minimum balance elements
					Element minBalance = doc.createElement("minBalance");
					minBalance.appendChild(doc.createTextNode(Double.toString(act.getMinimumBalance())));
					account.appendChild(minBalance);
					
					// interest rate elements
					Element interestRate = doc.createElement("interestRate");
					interestRate.appendChild(doc.createTextNode(Double.toString(act.getInterestRate())));
					account.appendChild(interestRate);
					
					// monthly elements
					Element monthlyFee = doc.createElement("monthlyFee");
					monthlyFee.appendChild(doc.createTextNode(""));
					account.appendChild(monthlyFee);
				}
				else{
					CheckingAccount act = (CheckingAccount)accounts.get(i);
					
					// type elements
					Element type = doc.createElement("type");
					type.appendChild(doc.createTextNode("C"));
					account.appendChild(type);
		
					// date elements
					Element date = doc.createElement("date");
					date.appendChild(doc.createTextNode(act.getStrDateOpened()));
					account.appendChild(date);
		
					// balance elements
					Element balance = doc.createElement("balance");
					balance.appendChild(doc.createTextNode(Double.toString(act.getBalance())));
					account.appendChild(balance);
		
					// owner elements
					Element owner = doc.createElement("owner");
					owner.appendChild(doc.createTextNode(act.getAccountOwner()));
					account.appendChild(owner);
					
					// minimum balance elements
					Element minBalance = doc.createElement("minBalance");
					minBalance.appendChild(doc.createTextNode(""));
					account.appendChild(minBalance);
					
					// interest rate elements
					Element interestRate = doc.createElement("interestRate");
					interestRate.appendChild(doc.createTextNode(""));
					account.appendChild(interestRate);
					
					// monthly elements
					Element monthlyFee = doc.createElement("monthlyFee");
					monthlyFee.appendChild(doc.createTextNode(Double.toString(act.getMonthlyFee())));
					account.appendChild(monthlyFee);
				}
			}

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(filename + ".xml"));

			transformer.transform(source, result);

		  } catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		  } catch (TransformerException tfe) {
			tfe.printStackTrace();
		  }
	}
}

package project3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import javax.swing.table.AbstractTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;

/***********************************************************************
 * This class creates a BankModel, which is an AbstractTableModel. It
 * does all calculations, storage, and banking decisions. It pushes data
 * to BankGUI and lets BankGUI retrieve data from it. It also has
 * functionality to save and load it accounts from several file types.
 * @author Allen Sietsema
 * @author David Whitters
 * @version 1.49
 **********************************************************************/

public class BankModel extends AbstractTableModel {

	/** variable holds all created accounts */
	public ArrayList<Account> accounts = new ArrayList<Account>();

	/**
	 * variable holds the custom SimpleDateFormat used for all date
	 * values
	 */
	private SimpleDateFormat simpleDateFormat;

	/** variable holds names of JTable columns */
	private String columnNames[];

	/*******************************************************************
	 * Constructor creates the BankModel.
	 ******************************************************************/
	public BankModel() {

		// define JTable column names
		columnNames = new String[] { "Account Number", "Account Owner",
				"Date Opened", "Account Balance", "Monthly Fee",
				"Interest Rate", "Minimum Balance" };

		// initialize simpleDateFormat
		simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
	}

	/*******************************************************************
	 * Updates (edits) an existing account in the BankModel.
	 * @param number integer account number
	 * @param owner String name of account owner
	 * @param dateOpened GregorianCalendar account open date
	 * @param balance double account balance
	 * @param monthlyFee double account monthlyFee
	 * @param interestRate double account interestRate
	 * @param minimumBalance double account minimum balance
	 * @param isSavings boolean telling if user wants the account to be
	 *            a savings account or checking account
	 * @param selectedRow integer telling what account should be edited
	 ******************************************************************/
	public int update(int number, String owner,
			GregorianCalendar dateOpened, double balance,
			double monthlyFee, double interestRate,
			double minimumBalance, boolean isSavings, int selectedRow) {

		//declare variables
		int errorCode = 0;
		
		//checks for valid data
		if ((monthlyFee >= 0) && (interestRate >= 0)
				&& (minimumBalance >= 0) && (number > 0)
				&& (balance >= 0) && (balance >= minimumBalance))
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
		else {
			errorCode = 1;
		}
		
		// update table
		this.fireTableDataChanged();
		
		//return errorCode
		return errorCode;
	}

	/*******************************************************************
	 * Adds an account to accounts ArrayList.
	 * @param number integer account number
	 * @param owner String name of account owner
	 * @param dateOpened GregorianCalendar object that is account open
	 *            date
	 * @param balance double that is account balance
	 * @param monthlyFee double that is account monthly fee
	 * @param interestRate double that is account interest rate
	 * @param minimumBalance double that is account minimum balance
	 * @param isSavings boolean tells if the user selected savings or
	 *            checking account.
	 * @return errorCode integer representing error in date supplied.
	 *         Returns 1 if balance is smaller than minimum balance; 2
	 *         if there is account number already exists; 3 if there is
	 *         no owner name; 4 if open date is in future; 5 if data
	 *         contains negative numbers.
	 ******************************************************************/
	public int add(int number, String owner,
			GregorianCalendar dateOpened, double balance,
			double monthlyFee, double interestRate,
			double minimumBalance, boolean isSavings) {

		// declare and initialize variables
		int errorCode = 0;

		// check if balance is smaller than minimum balance
		if ((minimumBalance > balance) && (isSavings == false)) {
			errorCode = 1;
		}

		// check to see if account number already exists
		for (int i = 0; i < accounts.size(); i++) {
			if (accounts.get(i).getNumber() == number)
				errorCode = 2;
		}

		// check to see if account owner name is not blank
		if (owner.equals("")) {
			errorCode = 3;
		}

		// check to see if date opened is in the future
		if (dateOpened.compareTo(new GregorianCalendar()) > 0) {
			errorCode = 4;
		}

		// check to see if data contains negative values
		if (((number < 0) || (balance < 0)
				|| (interestRate < 0) || (minimumBalance < 0)) && (isSavings == true)) {
			errorCode = 5;
		}
		if (((number < 0) || (balance < 0) || (monthlyFee < 0))
				&& (isSavings == false)) {
			errorCode = 5;
		}

		// create the new account if no error in data
		if (errorCode == 0) {

			// add new data to account type
			if (isSavings == true) {

				// create savings account
				SavingsAccount addedSavingsAccount = new SavingsAccount(
						number, owner, dateOpened, balance,
						minimumBalance, interestRate);

				// add savings account to array
				accounts.add(addedSavingsAccount);
			} else {

				// create checking account
				CheckingAccount addedCheckingAccount = new CheckingAccount(
						number, owner, dateOpened, balance, monthlyFee);

				// add checking account to array
				accounts.add(addedCheckingAccount);
			}

			// update table
			this.fireTableDataChanged();
		}

		// return errorCode
		return errorCode;
	}

	/*******************************************************************
	 * Deletes an account from the accounts ArrayList.
	 * @param selectedRow integer selected account row in JTable
	 ******************************************************************/
	public void delete(int selectedRow) {

		// delete account in selected row
		accounts.remove(selectedRow);

		// update table
		this.fireTableDataChanged();
	}

	/*******************************************************************
	 * Returns account in selected row.
	 * @param selecedRow integer selected row in JTable
	 * @return account Account at parameter defined row
	 ******************************************************************/
	public Account getAccount(int selectedRow) {

		// return account in selected row
		return accounts.get(selectedRow);
	}

	/*******************************************************************
	 * Returns the number of columns in the JTable.
	 * @return numberOfColumns number of columns in JTable
	 ******************************************************************/
	@Override
	public int getColumnCount() {

		// declare variables
		int numberOfColumns = columnNames.length;

		// return number of columns
		return numberOfColumns;
	}

	/*******************************************************************
	 * Returns number of rows in JTable.
	 * @return numberOfRows integer number of rows in JTable
	 ******************************************************************/
	@Override
	public int getRowCount() {

		// declare variables
		int numberOfRows = accounts.size();

		// return numberOfRows
		return numberOfRows;
	}

	/*******************************************************************
	 * Returns the column name for a given column number in JTable.
	 * @param column integer column in JTable
	 * @return name of given column
	 ******************************************************************/
	@Override
	public String getColumnName(int column) {

		// return column name
		return columnNames[column];
	}

	/*******************************************************************
	 * Returns the data to be entered in a specific location on the
	 * JTable.
	 * @param rowIndex integer row number
	 * @param columnIndex integer column number
	 * @return Object data to be entered in JTable at a defined column
	 *         and row
	 ******************************************************************/
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		// find value to return
		if (accounts.get(rowIndex) instanceof SavingsAccount) {
			switch (columnIndex) {
			case 0:
				return accounts.get(rowIndex).getNumber();
			case 1:
				return accounts.get(rowIndex).getOwner();
			case 2:
				return simpleDateFormat.format(accounts.get(rowIndex)
						.getDateOpened().getTime());
			case 3:
				return String.format("$%.2f",
						accounts.get(rowIndex).getBalance());
			case 4:
				return "";
			case 5:
				return String
						.format("%.3f%%",
								((SavingsAccount) accounts
										.get(rowIndex))
												.getInterestRate());
			case 6:
				return String
						.format("$%.2f",
								((SavingsAccount) accounts
										.get(rowIndex))
												.getMinimumBalance());
			}
		} else {
			switch (columnIndex) {
			case 0:
				return accounts.get(rowIndex).getNumber();
			case 1:
				return accounts.get(rowIndex).getOwner();
			case 2:
				return simpleDateFormat.format(accounts.get(rowIndex)
						.getDateOpened().getTime());
			case 3:
				return String.format("$%.2f",
						accounts.get(rowIndex).getBalance());
			case 4:
				return String
						.format("$%.2f",
								((CheckingAccount) accounts
										.get(rowIndex))
												.getMonthlyFee());
			case 5:
				return "";
			case 6:
				return "";
			}
		}

		// return blank if nothing can be found
		return "";
	}

	/*******************************************************************
	 * Sorts the ArrayList accounts by account number.
	 ******************************************************************/
	public void sortByNumber() {

		// sort accounts by account number
		Collections.sort(accounts, new AccountNumberComparator());

		// update table
		this.fireTableRowsUpdated(0, accounts.size() - 1);
	}

	/*******************************************************************
	 * Sort accounts ArrayList by account name.
	 ******************************************************************/
	public void sortByName() {

		// sort accounts by account name
		Collections.sort(accounts, new AccountOwnerComparator());

		// update table
		this.fireTableRowsUpdated(0, accounts.size() - 1);
	}

	/*******************************************************************
	 * Sorts accounts ArrayList by date opened.
	 ******************************************************************/
	public void sortByDateOpened() {

		// sort accounts by date opened
		Collections.sort(accounts, new DateComparator());

		// update table
		this.fireTableDataChanged();
	}

	/*******************************************************************
	 * Loads and replaces data in account ArrayList with data from a
	 * Binary file.
	 * @param filename String name of BinaryFile to be opened
	 ******************************************************************/
	public void loadBinary(String filename) {

		try {
			// declare variables
			FileInputStream fis = null;
			ObjectInputStream ois = null;

			try {

				// read from disk using FileInputStream
				fis = new FileInputStream(filename);

				// read object using ObjectInputStream
				ois = new ObjectInputStream(fis);

				// read an object
				Object obj = ois.readObject();

				if (obj instanceof ArrayList) {

					// cast object to an account ArrayList and replace
					// accounts with new ArrayList
					this.accounts = (ArrayList<Account>) obj;

					// update table
					this.fireTableDataChanged();
				}
			} catch (Exception e) {
				System.out.println("Binary Load Error.");
			} finally {

				// close input streams
				fis.close();
				ois.close();
			}
			// update table
			this.fireTableDataChanged();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*******************************************************************
	 * Saves data from accounts ArrayList as a Binary file.
	 * @param filename String name of new file
	 ******************************************************************/
	public void saveBinary(String filename) {

		try {
			// declare variables
			FileOutputStream fos = null;
			ObjectOutputStream os = null;

			try {
				fos = new FileOutputStream(filename);
				os = new ObjectOutputStream(fos);
				os.writeObject(accounts);
			} catch (Exception e) {
				System.out.println("Binary Save Error.");
			} finally {

				// close output streams
				fos.close();
				os.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*******************************************************************
	 * Loads and replaces accounts with data from text file.
	 * @param filename String name of file to be opened
	 ******************************************************************/
	public void loadText(String filename) {

		try {
			// declare variables
			String lineData = "";
			String strSplit[] = new String[14];
			String date[] = new String[3];
			int iDate[] = new int[3];
			ArrayList<String> savingsData = new ArrayList<String>();
			ArrayList<String> checkingData = new ArrayList<String>();
			accounts.clear();
			FileReader fr = null;
			BufferedReader br = null;

			// load file
			try {
				fr = new FileReader(filename);
				br = new BufferedReader(fr);

				while ((lineData = br.readLine()) != null) {
					// Empty ArrayList every loop, otherwise it
					// continues to
					// grow

					strSplit = lineData.split("\t");
					if (strSplit[1].equals("S")) {
						savingsData.clear();
						for (int i = 0; i < 14; i++)
							if (i % 2 == 1)
								savingsData.add(strSplit[i]);
						// Split date into 3 separate strings then
						// convert
						// to integers into another array
						date = savingsData.get(3).split("/");
						for (int i = 0; i < 3; i++)
							iDate[i] = Integer.parseInt(date[i]);

						SavingsAccount act = new SavingsAccount(
								Integer.parseInt(savingsData.get(1)),
								savingsData.get(2),
								new GregorianCalendar(iDate[2],
										iDate[0] - 1, iDate[1]),
								Double.parseDouble(savingsData.get(4)),
								Double.parseDouble(savingsData.get(6)),
								Double.parseDouble(savingsData.get(5)));

						this.accounts.add(act);
					} else {
						checkingData.clear();
						for (int i = 0; i < 12; i++)
							if (i % 2 == 1)
								checkingData.add(strSplit[i]);
						// Split date into 3 separate strings then
						// convert
						// to integers into another array
						date = checkingData.get(3).split("/");
						for (int i = 0; i < 3; i++)
							iDate[i] = Integer.parseInt(date[i]);

						CheckingAccount act = new CheckingAccount(
								Integer.parseInt(checkingData.get(1)),
								checkingData.get(2),
								new GregorianCalendar(iDate[2],
										iDate[0] - 1, iDate[1]),
								Double.parseDouble(checkingData.get(4)),
								Double.parseDouble(
										checkingData.get(5)));

						this.accounts.add(act);
					}
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
				System.out.println("Error");
			} finally {
				fr.close();
				br.close();
				this.fireTableDataChanged();
			}
			this.fireTableDataChanged();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.fireTableDataChanged();
	}

	/*******************************************************************
	 * Saves account ArrayList to a text file.
	 * @param filename name of new text file
	 ******************************************************************/
	public void saveText(String filename) {

		try {
			FileWriter write = null;
			PrintWriter pw = null;
			try {
				write = new FileWriter(filename);
				pw = new PrintWriter(write);

				for (int i = 0; i < accounts.size(); i++) {
					if (accounts.get(i) instanceof SavingsAccount) {
						SavingsAccount act = (SavingsAccount) accounts
								.get(i);
						pw.println(act.toString());
					} else {
						CheckingAccount act = (CheckingAccount) accounts
								.get(i);
						pw.println(act.toString());
					}
				}
			} catch (Exception e) {
			} finally {
				write.close();
				pw.close();
			}
		} catch (Exception e) {
		}
	}

	/*******************************************************************
	 * Loads and replaces account ArrayList with data from a XML file.
	 * @param filename String name of file to be opened
	 ******************************************************************/
	public void loadXML(String filename) {

		ArrayList<Account> accounts = new ArrayList<Account>();
		try {
			File fXmlFile = new File((filename));
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("Account");

			for (int i = 0; i < nList.getLength(); i++) {

				Node nNode = nList.item(i);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;

					String type = eElement.getElementsByTagName("type")
							.item(0).getTextContent();
					int number = Integer
							.parseInt(eElement.getAttribute("number"));
					String date = eElement.getElementsByTagName("date")
							.item(0).getTextContent();
					Double balance = Double.parseDouble(
							eElement.getElementsByTagName("balance")
									.item(0).getTextContent());
					String owner = eElement
							.getElementsByTagName("owner").item(0)
							.getTextContent();
					Double minBalance = null;
					Double interestRate = null;
					Double monthlyFee = null;
					if (type.equals("C"))
						monthlyFee = Double
								.parseDouble(eElement
										.getElementsByTagName(
												"monthlyFee")
										.item(0).getTextContent());
					else {
						minBalance = Double
								.parseDouble(eElement
										.getElementsByTagName(
												"minBalance")
										.item(0).getTextContent());
						interestRate = Double
								.parseDouble(eElement
										.getElementsByTagName(
												"interestRate")
										.item(0).getTextContent());
					}

					String date1[] = new String[3];
					date1 = date.split("/");

					int month = Integer.parseInt(date1[0]);
					int day = Integer.parseInt(date1[1]);
					int year = Integer.parseInt(date1[2]);

					GregorianCalendar dateOpened = new GregorianCalendar(
							year, month - 1, day);

					if (type.equals("S")) {
						SavingsAccount act = null;
						act = new SavingsAccount(number, owner,
								dateOpened, balance, minBalance,
								interestRate);
						accounts.add(act);
					} else {
						CheckingAccount act = null;
						act = new CheckingAccount(number, owner,
								dateOpened, balance, monthlyFee);
						accounts.add(act);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.accounts = accounts;

		// Update the table with the new account values
		this.fireTableDataChanged();
	}

	/*******************************************************************
	 * Saves accounts ArrayList to an XML file.
	 * @return filename String name of new XML file
	 ******************************************************************/
	public void saveToXML(String filename) {

		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory
					.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("catalog");
			doc.appendChild(rootElement);

			for (int i = 0; i < accounts.size(); i++) {
				// account elements
				Element account = doc.createElement("Account");
				rootElement.appendChild(account);

				// set attribute to staff element
				Attr num = doc.createAttribute("number");
				num.setValue(
						Integer.toString(accounts.get(i).getNumber()));
				account.setAttributeNode(num);

				if (accounts.get(i) instanceof SavingsAccount) {
					SavingsAccount act = (SavingsAccount) accounts
							.get(i);

					// type elements
					Element type = doc.createElement("type");
					type.appendChild(doc.createTextNode("S"));
					account.appendChild(type);

					// date elements
					Element date = doc.createElement("date");
					date.appendChild(doc
							.createTextNode(act.getDateOpenedString()));
					account.appendChild(date);

					// balance elements
					Element balance = doc.createElement("balance");
					balance.appendChild(doc.createTextNode(
							Double.toString(act.getBalance())));
					account.appendChild(balance);

					// owner elements
					Element owner = doc.createElement("owner");
					owner.appendChild(
							doc.createTextNode(act.getOwner()));
					account.appendChild(owner);

					// minimum balance elements
					Element minBalance = doc
							.createElement("minBalance");
					minBalance.appendChild(doc.createTextNode(
							Double.toString(act.getMinimumBalance())));
					account.appendChild(minBalance);

					// interest rate elements
					Element interestRate = doc
							.createElement("interestRate");
					interestRate.appendChild(doc.createTextNode(
							Double.toString(act.getInterestRate())));
					account.appendChild(interestRate);

					// monthly elements
					Element monthlyFee = doc
							.createElement("monthlyFee");
					monthlyFee.appendChild(doc.createTextNode(""));
					account.appendChild(monthlyFee);
				} else {
					CheckingAccount act = (CheckingAccount) accounts
							.get(i);

					// type elements
					Element type = doc.createElement("type");
					type.appendChild(doc.createTextNode("C"));
					account.appendChild(type);

					// date elements
					Element date = doc.createElement("date");
					date.appendChild(doc
							.createTextNode(act.getDateOpenedString()));
					account.appendChild(date);

					// balance elements
					Element balance = doc.createElement("balance");
					balance.appendChild(doc.createTextNode(
							Double.toString(act.getBalance())));
					account.appendChild(balance);

					// owner elements
					Element owner = doc.createElement("owner");
					owner.appendChild(
							doc.createTextNode(act.getOwner()));
					account.appendChild(owner);

					// minimum balance elements
					Element minBalance = doc
							.createElement("minBalance");
					minBalance.appendChild(doc.createTextNode(""));
					account.appendChild(minBalance);

					// interest rate elements
					Element interestRate = doc
							.createElement("interestRate");
					interestRate.appendChild(doc.createTextNode(""));
					account.appendChild(interestRate);

					// monthly elements
					Element monthlyFee = doc
							.createElement("monthlyFee");
					monthlyFee.appendChild(doc.createTextNode(
							Double.toString(act.getMonthlyFee())));
					account.appendChild(monthlyFee);
				}
			}

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory
					.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(filename));

			transformer.transform(source, result);

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}
}

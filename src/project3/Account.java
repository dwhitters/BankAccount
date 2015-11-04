package project3;

import java.io.Serializable;
import java.util.GregorianCalendar;

/***********************************************************************
 * This class serves as a superclass for SavingsAccount and
 * CheckingAccount. It has variable corresponding to mutual variables
 * that exist in both SavingsAccount and CheckingAccount.
 * @author Allen Sietsema
 * @author David Whitters
 * @version 1.49
 **********************************************************************/

public abstract class Account implements Serializable {

	/** variable holds the serial version ID */
	private static final long serialVersionUID = 1L;

	/** variable holds the account number */
	private int number;

	/** variable holds the account name */
	private String owner;

	/** variable holds the account open date */
	private GregorianCalendar dateOpened;

	/** variable holds the account balance */
	private double balance;

	/*******************************************************************
	 * Constructor adds more mutual account data to subclass
	 * SavingsAccount or CheckingAccount data.
	 * @param number integer representing account number
	 * @param owner String defining name on account
	 * @param dateOpened GregorianCalendar object defining the opening
	 *            date of the account
	 * @param balance double defining the monitary balance of the
	 *            account
	 ******************************************************************/
	public Account(int number, String owner,
			GregorianCalendar dateOpened, double balance) {
		// defines class variables
		this.number = number;
		this.owner = owner;
		this.dateOpened = dateOpened;
		this.balance = balance;
	}

	/*******************************************************************
	 * Returns the account number.
	 * @return number integer account number
	 ******************************************************************/
	public int getNumber() {

		// returns account number
		return this.number;
	}

	/*******************************************************************
	 * Sets account number.
	 * @param number integer account number
	 ******************************************************************/
	public void setNumber(int number) {

		// sets account number
		this.number = number;
	}

	/*******************************************************************
	 * Returns account owner.
	 * @return owner String account owner
	 ******************************************************************/
	public String getOwner() {

		// returns owner
		return this.owner;
	}

	/*******************************************************************
	 * Sets account owner.
	 * @param owner String account owner
	 ******************************************************************/
	public void setOwner(String owner) {

		// sets owner
		this.owner = owner;
	}

	/*******************************************************************
	 * Sets account balance.
	 * @param balance double account balance
	 ******************************************************************/
	public void setBalance(double balance) {

		// sets balance
		this.balance = balance;
	}

	/*******************************************************************
	 * Returns account balance.
	 * @return balance double account balance
	 ******************************************************************/
	public double getBalance() {

		// returns balance
		return this.balance;
	}

	/*******************************************************************
	 * Returns account open date.
	 * @return dateOpened GregorianCalendar date account opened
	 ******************************************************************/
	public GregorianCalendar getDateOpened() {

		// returns date opened
		return dateOpened;
	}

	/*******************************************************************
	 * Sets account open date.
	 * @param dateOpened GregorianCalendar account open date
	 ******************************************************************/
	public void setDateOpened(GregorianCalendar dateOpened) {

		// sets date opened
		this.dateOpened = dateOpened;
	}

	/*******************************************************************
	 * Returns account open date.
	 * @return dateOpenedString String account date opened
	 ******************************************************************/
	public String getDateOpenedString() {

		// create String and initialize with dateOpned information
		String dateOpenedString = Integer
				.toString(dateOpened.get(dateOpened.MONTH) + 1)
				+ "/"
				+ Integer.toString(
						dateOpened.get(dateOpened.DAY_OF_MONTH))
				+ "/"
				+ Integer.toString(dateOpened.get(dateOpened.YEAR));

		// return dateOpenedString
		return dateOpenedString;
	}

	/*******************************************************************
	 * Converts account to String.
	 ******************************************************************/
	public abstract String toString();
}
package project3;

import java.util.GregorianCalendar;

/***********************************************************************
 * This class creates a checking account, and is a subclass to Account.
 * It contains addition variables, such as monthly fee.
 * @author Allen Sietsema
 * @author David Whitters
 * @version 1.49
 **********************************************************************/

public class CheckingAccount extends Account {

	/** variable holds the serial version ID */
	private static final long serialVersionUID = 1L;

	/** variable holds the monthly fee */
	private double monthlyFee;

	/*******************************************************************
	 * Constructor creates a checking account as an instance of
	 * CheckingAccount.
	 * @param number integer account number
	 * @param owner String owner name
	 * @param dateOpened GregorianCalendar object account open date
	 * @param balance double account balance
	 * @param monthlyFee double monthly fee of CheckingAccount
	 ******************************************************************/
	public CheckingAccount(int number, String owner,
			GregorianCalendar dateOpened, double balance,
			double monthlyFee) {
		// calls superclass
		super(number, owner, dateOpened, balance);

		// defines instance variable
		this.monthlyFee = monthlyFee;
	}

	/*******************************************************************
	 * Returns monthly fee of CheckingAccount.
	 * @return monthlyFee double monthly fee of checking account
	 ******************************************************************/
	public double getMonthlyFee() {

		// returns monthly fee
		return this.monthlyFee;
	}

	/*******************************************************************
	 * Sets monthly fee of CheckingAccount.
	 * @param monthlyFee double monthly fee of checking account
	 ******************************************************************/
	public void setMonthlyFee(double monthlyFee) {

		// sets monthly fee
		this.monthlyFee = monthlyFee;
	}

	/*******************************************************************
	 * Converts CheckingAccount instance into a String format.
	 * @return checkingAccountString CheckingAccount formated as a
	 *         String.
	 ******************************************************************/
	public String toString() {

		// create String and initialize with CheckingAccount information
		String checkingAccountString = "Account Type:\tC\tAccount "
				+ "Number:\t" + getNumber() + "\tAccountOwner:\t"
				+ getOwner() + "\tDate Opened:\t"
				+ getDateOpenedString() + "\tAccount Balance:\t"
				+ getBalance() + "\tMonthly Fee:\t" + getMonthlyFee();

		// return checkingAccountString
		return checkingAccountString;
	}
}
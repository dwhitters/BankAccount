package project3;

import java.util.GregorianCalendar;

/***********************************************************************
 * This class creates a savings account, and is a subclass to Account.
 * It has additional account variables, such as minimum balance and
 * interest rate.
 * @author Allen Sietsema
 * @author David Whitters
 * @version 1.49
 **********************************************************************/

public class SavingsAccount extends Account {

	/** variable holds the serial version ID */
	private static final long serialVersionUID = 1L;

	/** variable holds the minimum balance */
	private double minimumBalance;

	/** variable holds the interest rate */
	private double interestRate;

	/*******************************************************************
	 * Constructor creates a new SavingsAccount using parameter defined
	 * data.
	 * @param number integer representing account number
	 * @param owner String defining name on account
	 * @param dateOpened GregorianCalendar object defining the opening
	 *            date of the account
	 * @param balance double defining the monetary balance of the
	 *            account
	 * @param minimumBalance double defining minimum balance required
	 *            for savings account
	 * @param interestRate double defining interest rate of
	 *            SavingsAccount
	 ******************************************************************/
	public SavingsAccount(int number, String owner,
			GregorianCalendar dateOpened, double balance,
			double minimumBalance, double interestRate) {
		// calls superclass
		super(number, owner, dateOpened, balance);

		// defines SavingAccount instance variables
		this.minimumBalance = minimumBalance;
		this.interestRate = interestRate;
	}

	/*******************************************************************
	 * Sets minimum savings account balance.
	 * @param minimumBalance double defining minimum balance for savings
	 *            account
	 ******************************************************************/
	public void setMinimumBalance(double minimumBalance) {

		// sets minimum balance
		this.minimumBalance = minimumBalance;
	}

	/*******************************************************************
	 * Returns minimum balance.
	 * @return double minimum balance
	 ******************************************************************/
	public double getMinimumBalance() {

		// returns minimum balance
		return this.minimumBalance;
	}

	/*******************************************************************
	 * Sets savings account interest rate.
	 * @param interestRate double savings account interest rate
	 ******************************************************************/
	public void setInterestRate(double interestRate) {

		// sets interest rate
		this.interestRate = interestRate;
	}

	/*******************************************************************
	 * Returns savings account interest rate.
	 * @return interestRate double savings account interest rate
	 ******************************************************************/
	public double getInterestRate() {

		// returns interest rate
		return this.interestRate;
	}

	/*******************************************************************
	 * Converts savings account to a String.
	 * @return savingsAccountString instance of SavingsAccount
	 *         concatenated into a String
	 ******************************************************************/
	public String toString() {

		String savingsAccountString = "Account Type:\tS\t"
				+ "Account Number:\t"
				+ getNumber() + "\tAccountOwner:\t" + getOwner()
				+ "\tDate Opened:\t" + getDateOpenedString()
				+ "\tAccount Balance:\t" + getBalance()
				+ "\tInterest Rate:\t" + getInterestRate()
				+ "\tMinimumBalance:\t" + getMinimumBalance();
		return savingsAccountString;
	}
}

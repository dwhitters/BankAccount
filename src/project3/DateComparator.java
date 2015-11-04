package project3;

import java.util.Comparator;

public class DateComparator implements Comparator<Account> {
	@Override
	public int compare(Account account1, Account account2) {
		return account1.getDateOpened().
				compareTo(account2.getDateOpened());
	}
}
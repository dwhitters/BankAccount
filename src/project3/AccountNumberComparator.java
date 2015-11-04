package project3;

import java.util.Comparator;

public class AccountNumberComparator implements Comparator<Account>{
	@Override
	public int compare(Account arg0, Account arg1) {
		return arg0.getNumber() - arg1.getNumber();
	}
}

package project3;

import java.util.Comparator;

public class AccountOwnerComparator implements Comparator<Account>{
	@Override
	public int compare(Account o1, Account o2) {
		return o1.getAccountOwner().compareTo(o2.getAccountOwner());
	}
}

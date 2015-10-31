package project3;

import java.util.Comparator;

public class AccountNumberComparator implements Comparator<Account>{
	@Override
	public int compare(Account arg0, Account arg1) {
		int returnVal = 2;
		
		if(arg0.getAccountNumber() > arg1.getAccountNumber())
			returnVal = 1;
		
		else if(arg0.getAccountNumber() == arg1.getAccountNumber())
			returnVal = 0;
		
		else 
			returnVal = -1;
		
		return returnVal;
	}
}

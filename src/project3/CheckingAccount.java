package project3;

public abstract class CheckingAccount extends Account{
	private static final long serialVersionUID = 1L;
	private double monthlyFee;
	
	public CheckingAccount()
	{
		
	}
	
	public double getMonthlyFee()
	{
		return this.monthlyFee;
	}
	
	public void setMothlyFee()
	{
		this.monthlyFee = monthlyFee;
	}
}

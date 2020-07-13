package server;

public class UserInfo {
	private String name;
	private String hashPass;
	private double balance;
	
	
	public UserInfo(String nameIn, String passIn, String balanceIn)
	{
		name = nameIn;
		hashPass = passIn;
		balance = Double.parseDouble(balanceIn);
	}
	
	public String getName() {
		return name;
	}

	public String getPass() {
		return hashPass;
	}
	
	public double getBalance()
	{
		return balance;
	}
	
	public void setName(String nameIn) {
		name = nameIn;
	}

	public void setPass(String passIn) {
		hashPass = passIn;
	}
	
	public void setBalance(double balanceIn)
	{
		balance = balanceIn;
	}
	
	
	
	
	
	
	
}

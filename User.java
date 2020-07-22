package server;

import java.io.Serializable;
import java.util.Comparator;

public class User implements Serializable {
	private String name;
	private String hashPass;
	private int balance;
	private Hand hand;
	

	
	
	public User(String nameIn, String passIn)
	{
		name = nameIn;
		hashPass = passIn;
		balance = 10000;
		hand = new Hand();
	}
	
	
	
	public User(String nameIn, String passIn, String balanceIn)
	{
		name = nameIn;
		hashPass = passIn;
		balance = Integer.parseInt(balanceIn);
		hand = new Hand();

	}
	 public void hit(Card card)
	    {
	    	this.hand.hit(card);
	    }
	   
	public String getName() {
		return name;
	}

	public String getPass() {
		return hashPass;
	}
	
	public int getBalance()
	{
		return balance;
	}
	
	public void setName(String nameIn) {
		name = nameIn;
	}

	public void setPass(String passIn) {
		hashPass = passIn;
	}
	
	public void setBalance(int balanceIn)
	{
		balance = balanceIn;
	}
	
	public Hand getHand()
	{
		return hand;
	}
	
	public void changeBalance(int change) {
		balance = balance + change;
	}
	
	
	
	
}

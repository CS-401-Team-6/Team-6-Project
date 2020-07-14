public class User 
{
    private String username;
    private Byte[] password;
    private int bank;
    private Hand hand;
    
    public User(String usn, Byte[] pass)
    {
        this.setBank(10000);
        this.setPassword(pass);
        this.setUsername(usn);
    }
    
    public void hit(Card card)
    {
    	this.hand.hit(card);
    }
    
    public void setUsername(String usn)
    {
    	this.username = usn;
    }
    
    public void setPassword(Byte[] pass)
    {
    	this.password = pass;
    }
    
    public void setBank(int money)
    {
    	this.bank = money;
    }
    
    public void setBet(int b)
    {
    	if(b > this.bank)
    	{
    		this.hand.setBet(this.bank);
    	}
    	else if(b < 0)
    	{
    		this.hand.setBet(0);
    	}
    	else
    	{
    		this.hand.setBet(b);
    	}
    }
    
    public String getUsername()
    {
    	return this.username;
    }
    
    public Byte[] getPassword()
    {
    	return this.password;
    }
    
    public int getBank()
    {
    	return this.bank;
    }  
    
    public int getBet()
    {
    	return this.hand.getBet();
    }
    
    public Hand getHand()
    {
    	return this.hand;
    }
}
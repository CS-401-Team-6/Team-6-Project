public class User 
{
    private String username;
    private String password;
    private int bank;
    private Hand hand;
    
    public User(String usn, String pass)
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
    
    public void setPassword(String pass)
    {
    	this.password = pass;
    }
    
    public void setBank(int money)
    {
    	this.bank = money;
    }
    
    public String getUsername()
    {
    	return this.username;
    }
    
    public String getPassword()
    {
    	return this.password;
    }
    
    public int getBank()
    {
    	return this.bank;
    }  
    
    public Hand getHand()
    {
    	return this.hand;
    }
}

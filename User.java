package projectBJ;


public class User 
{
    private String username;
    private String password;
    private int bank;
    
    public User(String usn, String pass)
    {
        this.setBank(bank);
        this.setPassword(pass);
        this.setUsername(usn);
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
}
import java.util.ArrayList;

public class User 
{
    private String username;
    private String password;
    private int bank;
    
    private static ArrayList<String> UserList = new ArrayList<String>();
    private static ArrayList<User> UserInfo = new ArrayList<User>();
    
    public User(String usn, String pass)
    {
        this.setBank(100);
        this.setPassword(pass);
        this.setUsername(usn);
    }
    
    public boolean checkUsername(String usn)
    {
    	usn = usn.toUpperCase();
    	if(UserList.contains(usn))
    	{
    		return false;
    	}
    	else
    	{
    		return true;
    	}
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

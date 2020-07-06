import javax.swing.JOptionPane;

public class userLogin_GUI implements GameUserInterface {
	private User user;
	private String loginuserid;
	private String loginuserpw;
	
	
	public userLogin_GUI()
	{
		user = null;
		loginuserid = null;
		loginuserpw = null;
	}


	
	public void execute() {
		boolean correct = false;
		while(correct == false)
		{
			loginuserid = JOptionPane.showInputDialog("Enter User ID: ");
			loginuserpw = JOptionPane.showInputDialog("Enter User Password: ");
			if(loginuserid.equals(user.getUsername()) && loginuserpw.equals(user.getPassword()))
			{
				JOptionPane.showMessageDialog(null, "Login Success!");
				correct = true;
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Incorrect ID or Password...");
			}
		}
		
	}
	
	
}

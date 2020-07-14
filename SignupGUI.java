package projectBJ;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import java.awt.Color;

public class Signup {

	private JFrame frame;
	private JTextField rgusid;
	private JPasswordField rgpassw;
	private int numofcus;
	private String usid, bank;
	private String psd;
	private User[] customerarray;
	private User rguser;
	private boolean add;

	/**
	 * Launch the application.
	 */
	public static void main() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Signup window = new Signup();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Signup() {
		rguser = new User(null, null, 10000);
		usid = null;
		psd = null;
		numofcus = 0;
		bank = "1000";
		customerarray = new User[100];
		add = false;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		loadData("userdatabase.txt");
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(255, 255, 255));
		frame.setBackground(Color.BLACK);
		frame.setBounds(100, 100, 557, 405);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("New User Register");
		lblNewLabel.setBackground(Color.WHITE);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 21));
		lblNewLabel.setBounds(265, 10, 218, 121);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("User ID:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(206, 120, 109, 56);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Password:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_2.setBounds(206, 215, 82, 39);
		frame.getContentPane().add(lblNewLabel_2);
		
		rgusid = new JTextField();
		rgusid.setBounds(315, 141, 208, 19);
		frame.getContentPane().add(rgusid);
		rgusid.setColumns(10);
		
		rgpassw = new JPasswordField();
		rgpassw.setBounds(315, 227, 208, 19);
		frame.getContentPane().add(rgpassw);
		
		JButton btnNewButton = new JButton("Sign up");
		btnNewButton.setBackground(new Color(255, 240, 245));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				add = false;
				usid = rgusid.getText();
				psd = rgpassw.getText();
				rguser.setUsername(usid);
				rguser.setPassword(psd);
				add = addCustomer(usid,psd,bank);
				if(add == true)
				{
					save(rguser);
					frame.setVisible(false);
				}
				else
				{
					frame.setVisible(true);
				}
				
			}

		});
		btnNewButton.setBounds(206, 277, 327, 27);
		frame.getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon(Signup.class.getResource("/iamge/rg2.jpg")));
		lblNewLabel_3.setBounds(10, 10, 186, 358);
		frame.getContentPane().add(lblNewLabel_3);
	}
	
	public void loadData(String filename) {
		int x = 0;
		Scanner filescan = null;
		String[] strarray = new String[customerarray.length],  arrSplit = null;
		File file = new File(filename);
		try {
			
			if(file.length() > 1)
			{
				filescan = new Scanner(file);
				
				while(filescan.hasNextLine())
				{
					strarray[x] = filescan.nextLine();
					x++;
				}
				for(int i1 = 0; i1 < strarray.length; i1++)
				{
					if(strarray[i1] != null )
					{
						
						arrSplit = strarray[i1].split(",");
						for(int i2= 0; i2 < arrSplit.length; i2++)
						{	
							
							addCustomer(arrSplit[i2], arrSplit[i2+1], arrSplit[i2+2]);
							i2 += 2;
						}
					}
					
				}	
			}
			else
			{
				
				numofcus = 0;
			}
			
			
		} catch (Exception e) {
			System.out.println("File not exist.\n");
		}			
	}
	
	public boolean addCustomer(String id, String password, String bankc)
	{
		int bk = 0, oldcus = 0;
		char ch = 0;
		boolean numberFlag = false, capitalFlag = false, lowerCaseFlag = false;
		boolean newcus = true;
		User temp = new User(null, null, 0);

		
		for(int i = 0; i < numofcus; i++)
		{
			if(usid.equals(customerarray[i].getUsername()))
			{
				
				newcus = false;
				oldcus = i;
				break;
			}
		}
		if(newcus == true && numofcus < customerarray.length)
		{
			
			temp.setUsername(id);
			temp.setPassword(password);
			bk = Integer.parseInt(bankc);
			temp.setBank(bk);
			
			if(password.length() >= 8 )
			{
				
				for(int i = 0; i < password.length(); i ++)
				{
					
					 ch = password.charAt(i);
					 if( Character.isDigit(ch))
				        {
						 
				            numberFlag = true;
				        }
					 else if (Character.isUpperCase(ch)) 
				        {
						 
				            capitalFlag = true;
				        } 
					 else if (Character.isLowerCase(ch)) 
				        {
				            lowerCaseFlag = true;
				        }
					
						 
				}
				 if(numberFlag && capitalFlag && lowerCaseFlag)
				 {
					 customerarray[numofcus] = temp;
					 numofcus++;
					 return true;
				 }
				 else
				 {
					 JOptionPane.showMessageDialog(frame, "Password has to contain at least eight characters,"
								+ " which include at least one number one upper case letter and one lower case letter.");
					 return false;
				 }
			}
			else
			{
				JOptionPane.showMessageDialog(frame, "Password has to contain at least eight characters,"
						+ " which include at least one number one upper case letter and one lower case letter.");
				return false;
			}
			
		}
		else if(newcus == false )
		{
			JOptionPane.showMessageDialog(frame, "This username has been registered");
			return newcus;
			
		}
		else if(newcus == true && numofcus >= customerarray.length)
		{
			int doublesize = customerarray.length * 2;
			if(doublesize < numofcus)
			{
				doublesize *= 2;
			}
			User[] temparray = new User[doublesize];
			temp.setUsername(id);
			temp.setPassword(password);
			bk = Integer.parseInt(bankc);
			temp.setBank(bk);
			temparray = Arrays.copyOf(customerarray, doublesize);
			temparray[numofcus] = temp;
			
			if(password.length() >= 8 )
			{
				
				for(int i = 0; i < password.length(); i ++)
				{
					 ch = password.charAt(i);
					 if( Character.isDigit(ch))
				        {
				            numberFlag = true;
				        }
					 else if (Character.isUpperCase(ch)) 
				        {
				            capitalFlag = true;
				        } 
					 else if (Character.isLowerCase(ch)) 
				        {
				            lowerCaseFlag = true;
				        }
					 if(numberFlag && capitalFlag && lowerCaseFlag)
					 {
						 customerarray = temparray;
						 numofcus++;
						 return true;
					 }
					 else
					 {
						 JOptionPane.showMessageDialog(frame, "Password has to contain at least eight characters,"
									+ " which include at least one number one upper case letter and one lower case letter.");
						 return false;
					 }
						 
				}
			}
			else
			{
				JOptionPane.showMessageDialog(frame, "Password has to contain at least eight characters,"
						+ " which include at least one number one upper case letter and one lower case letter.");
				return false;
			}
		}
		return false;
	}
	
	public void save(User newuser) 
	{
		
		if(numofcus == 1) 
		{
			try {
			      FileWriter cuswriter = new FileWriter("userdatabase.txt");
			     
			    	  cuswriter.write(newuser.getUsername() + "," + newuser.getPassword() + "," + newuser.getBank() + "\n");
			     
			      cuswriter.close();
			      JOptionPane.showMessageDialog(frame, "Success!");
			    } catch (IOException e) {
			    	JOptionPane.showMessageDialog(frame, "Error 404");
			      e.printStackTrace();
			    }
		}
		else
		{
			try {
			      FileWriter cuswriter = new FileWriter("userdatabase.txt",true);
			     
			    	  cuswriter.write(newuser.getUsername() + "," + newuser.getPassword() + "," + newuser.getBank() + "\n");
			    
			      cuswriter.close();
			      JOptionPane.showMessageDialog(frame, "Success!");
			    } catch (IOException e) {
			    	JOptionPane.showMessageDialog(frame, "Error 404");
			      e.printStackTrace();
			    }
		}

	}
}

package project;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;




public class userLogin_GUI implements ActionListener {
	private User user;
	private JLabel userlabel, pwlabel, success, fail, welcomelb;
	private JButton button, rgbutton;
	private JTextField userText;
	private JPasswordField passwordText;
	JFrame frame;
	JPanel panel;
	
	public userLogin_GUI()
	{
		user = null;
		userlabel = null;
		pwlabel = null;
		success = null;
		fail = null;
		button = null;
		userText = null;
		passwordText = null;
		rgbutton = null;
		frame = null;
		panel = null;
		welcomelb = null;
	}


	
	public void login() {
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		
		frame.setSize(300,190);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		
		frame.add(panel);
		
		panel.setLayout(null);
		welcomelb = new JLabel("Welcome to Blackjack!");
		welcomelb.setBounds(10,15,140,25);
		panel.add(welcomelb);
		
		
		userlabel = new JLabel("User");
		userlabel.setBounds(10,50,80,25);
		panel.add(userlabel);
		
		userText = new JTextField();
		userText.setBounds(100,50,165,25);
		panel.add(userText);
		
		pwlabel = new JLabel("password");
		pwlabel.setBounds(10,80,80,25);
		panel.add(pwlabel);
		
		passwordText = new JPasswordField();
		passwordText.setBounds(100,80,165,25);
		panel.add(passwordText);
		
		button = new JButton("Login");
		button.setBounds(10,110,80,25);
		button.addActionListener(new userLogin_GUI());      
		panel.add(button);
		
		rgbutton = new JButton("Register");
		rgbutton.setBounds(173,110,90,25);
		button.addActionListener(new userLogin_GUI());  
		panel.add(rgbutton);
		
		
		
		
		
		frame.setVisible(true);
	}
	


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == button)
		{
			if(user.getUsername().equals(userText))
			{
				success = new JLabel("Login Success!");
				success.setBounds(10,110,300,25);
				panel.add(success);
			}
			else
			{
				fail = new JLabel("Incorrect Username or Password...");
				fail.setBounds(10,110,300,25);
				panel.add(fail);
			}
		}
		else if(e.getSource() == rgbutton)
		{
			// write to file or array...
		}
	
	}
}

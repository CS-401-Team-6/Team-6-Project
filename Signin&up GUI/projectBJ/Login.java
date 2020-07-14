package projectBJ;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Arrays;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JTextPane;
import java.awt.Color;
import javax.swing.ImageIcon;

public class Login {

	private JFrame frame;
	private JTextField username;
	private JPasswordField password;
	private User user;
	private User[] customerarray;
	private String newuserid;
	private Signup userrg;
	private byte[] psw;


	/**
	 * Launch the application.
	 */
	public static void main() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
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
	public Login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBackground(Color.WHITE);
		frame.setBounds(100, 100, 684, 316);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("User ID:");
		lblNewLabel.setBounds(360, 31, 65, 35);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Password:");
		lblNewLabel_1.setBounds(360, 109, 82, 28);
		frame.getContentPane().add(lblNewLabel_1);
		
		username = new JTextField();
		username.setBounds(360, 63, 284, 23);
		frame.getContentPane().add(username);
		username.setColumns(10);
		
		password = new JPasswordField();
		password.setBounds(360, 140, 284, 23);
		frame.getContentPane().add(password);
		
		JButton btnNewButton = new JButton("Login");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String usid = username.getText();
				String psd = password.getText();
				psw = psd.getBytes();
				
				// add client login here
				
				if(usid.equals("name") && psd.equals("aa"))
				{
					JOptionPane.showMessageDialog(frame, "Success!");
				}
				else
				{
					JOptionPane.showMessageDialog(frame, "Wrong userid or password...");
				}
			}
		});
		btnNewButton.setBounds(357, 202, 85, 21);
		frame.getContentPane().add(btnNewButton);
		
		JButton regb = new JButton("Register");
		regb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				userrg.main();
			}
		});
		regb.setBounds(559, 202, 85, 21);
		frame.getContentPane().add(regb);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		panel.setBounds(0, 0, 332, 282);
		frame.getContentPane().add(panel);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Login.class.getResource("/iamge/bj3.jpg")));
		panel.add(lblNewLabel_2);
	}
	

}

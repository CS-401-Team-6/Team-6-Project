package client;

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
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JTextPane;

import server.Message;
import server.User;

import java.awt.Color;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
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

	private ObjectOutputStream objectOutputStream;
	private ObjectInputStream objectInputStream;
	private Cipher clientCipher;
	private ConnectionMessage messageIn;


	/**
	 * Launch the application.
	 */
	public void login() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login(objectOutputStream, objectInputStream, clientCipher);
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
	public Login(ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream, Cipher clientCipher) {
		this.objectInputStream = objectInputStream;
		this.objectOutputStream = objectOutputStream;
		this.clientCipher = clientCipher;
		initialize();
	}

	
	public Login() {
		initialize();
	}
		/**
	}
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
				String usid = ""; 
				char[] psd = {};
				byte[] hash;
				byte[] encryptedHash;
				try {
					usid = username.getText();
					psd = password.getPassword();
					hash = Client.oneWayHash(usid, new String(psd));
					encryptedHash = clientCipher.doFinal(hash);
					objectOutputStream.writeObject(new ConnectionMessage("login", encryptedHash, usid));
					messageIn =  (ConnectionMessage) objectInputStream.readObject();
					if (messageIn.getType().contentEquals("login"))
						{
						if (messageIn.getText().equals("/NO USER"))
							JOptionPane.showMessageDialog(frame, "No such user name exists...");
						else if (messageIn.getText().equals("/INVALID PASS"))
							JOptionPane.showMessageDialog(frame, "Invalid password");
						else if  (messageIn.getText().equals("/SUCCESS")) {
							JOptionPane.showMessageDialog(frame, "Welcome back " + usid);
							ConsoleRun consoleRun = new ConsoleRun(objectOutputStream, objectInputStream, usid);
							consoleRun.consoleGameRun();
						}
						}
				} catch (IllegalBlockSizeException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (BadPaddingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NoSuchAlgorithmException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(357, 202, 85, 21);
		frame.getContentPane().add(btnNewButton);
		
		JButton regb = new JButton("Register");
		regb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				userrg = new Signup(objectOutputStream, objectInputStream,  clientCipher);
				userrg.signup(objectOutputStream, objectInputStream,  clientCipher  );
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
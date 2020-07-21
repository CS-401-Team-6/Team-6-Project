package projectBJ;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.event.ActionEvent;

public class serverchatbox extends JFrame {

	private JPanel contentPane;
	static JTextField msg_area;
	private JTextField msg_text;
	static ServerSocket ss;
	static Socket s;
	static DataInputStream din;
	static DataOutputStream dout;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		java.awt.EventQueue.invokeLater(new Runnable()
		{
			public void run() 
			{
				new serverchatbox().setVisible(true);
			}
		});
		
		String msgin = "";
		try 
		{
			ss = new ServerSocket(5555);
			s = ss.accept();
			din = new DataInputStream(s.getInputStream());
			dout = new DataOutputStream(s.getOutputStream());
			
			while(!msgin.equals("exit"))
			{
				msgin = din.readUTF();
				msg_area.setText(msg_area.getText().trim()+ "\n" + msgin);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	
		
	}

	/**
	 * Create the frame.
	 */
	public serverchatbox() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		msg_area = new JTextField();
		msg_area.setBounds(10, 10, 416, 198);
		contentPane.add(msg_area);
		msg_area.setColumns(10);
		
		msg_text = new JTextField();
		msg_text.setText("hello");
		msg_text.setBounds(10, 218, 286, 35);
		contentPane.add(msg_text);
		msg_text.setColumns(10);
		
		JButton send = new JButton("Send");
		send.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String msgout = "";                   // get message here
					msgout = msg_text.getText().trim();
					dout.writeUTF(msgout);
				}catch (Exception e1) {
					e1.printStackTrace();
				}
				
				
			}
		});
		send.setFont(new Font("Tahoma", Font.PLAIN, 15));
		send.setBounds(306, 218, 120, 35);
		contentPane.add(send);
	}
}

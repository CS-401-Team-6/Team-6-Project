package projectBJ;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class gametable extends JFrame {

	private JPanel contentPane;
	private JTextField amountint;
	private JTextField Dealercards;
	private JTextField P1Cards;
	private JTextField P2Cards;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {                  // connect to the other class just delete String[] args etc.
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					gametable frame = new gametable();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public gametable() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 872, 587);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel moneybagicon = new JLabel("");
		moneybagicon.setIcon(new ImageIcon(gametable.class.getResource("/iamge/money-bag.png")));
		moneybagicon.setBackground(Color.GREEN);
		moneybagicon.setBounds(10, 10, 263, 241);
		contentPane.add(moneybagicon);
		
		JLabel lblNewLabel_2 = new JLabel("Enter the amount you want to bet:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblNewLabel_2.setBounds(10, 261, 311, 36);
		contentPane.add(lblNewLabel_2);
		
		amountint = new JTextField();
		amountint.setBounds(10, 307, 263, 25);
		contentPane.add(amountint);
		amountint.setColumns(10);
		
		JButton Bet = new JButton("BET");
		Bet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				String amountText = amountint.getText();
				int amount = Integer.parseInt(amountText);
				//add action after hit "BET" here.
				
				
				
				
				
			}
		});
		Bet.setForeground(new Color(255, 255, 255));
		Bet.setBackground(new Color(51, 204, 51));
		Bet.setFont(new Font("Tahoma", Font.PLAIN, 18));
		Bet.setBounds(100, 354, 85, 25);
		contentPane.add(Bet);
		
		JButton Stand = new JButton("Stand");
		Stand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				
				
				//add action after hit "Stand" here.
				
				
				
				
				
			}
		});
		Stand.setForeground(Color.WHITE);
		Stand.setBackground(new Color(51, 204, 255));
		Stand.setFont(new Font("Tahoma", Font.PLAIN, 15));
		Stand.setBounds(301, 270, 194, 21);
		contentPane.add(Stand);
		
		JLabel OPCardsLabel = new JLabel("Opponents' cards: ");
		OPCardsLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		OPCardsLabel.setBounds(301, 10, 178, 36);
		contentPane.add(OPCardsLabel);
		
		JLabel DealerLabel = new JLabel("Dealer");
		DealerLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		DealerLabel.setBounds(301, 43, 113, 36);
		contentPane.add(DealerLabel);
		
		JLabel P1Label = new JLabel("Player1:");
		P1Label.setFont(new Font("Tahoma", Font.PLAIN, 14));
		P1Label.setBounds(301, 106, 113, 36);
		contentPane.add(P1Label);
		
		JLabel P2Label = new JLabel("Player2:");
		P2Label.setFont(new Font("Tahoma", Font.PLAIN, 14));
		P2Label.setBounds(301, 177, 113, 36);
		contentPane.add(P2Label);
		
		JButton Hit = new JButton("Hit");
		Hit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				//add action after hit "Hit" here.
				
				
				
				
				
			}
		});
		Hit.setBackground(new Color(204, 0, 51));
		Hit.setForeground(Color.WHITE);
		Hit.setFont(new Font("Tahoma", Font.PLAIN, 15));
		Hit.setBounds(599, 270, 194, 21);
		contentPane.add(Hit);
		
		Dealercards = new JTextField();
		Dealercards.setFont(new Font("Tahoma", Font.PLAIN, 13));
		Dealercards.setText("getcard");                                     // Change the card display here
		Dealercards.setBounds(301, 77, 492, 36);
		contentPane.add(Dealercards);
		Dealercards.setColumns(10);
		
		P1Cards = new JTextField();
		P1Cards.setFont(new Font("Tahoma", Font.PLAIN, 13));
		P1Cards.setText("getcard");                                         // same as above
		P1Cards.setColumns(10);
		P1Cards.setBounds(301, 139, 492, 36);
		contentPane.add(P1Cards);
		
		P2Cards = new JTextField();
		P2Cards.setFont(new Font("Tahoma", Font.PLAIN, 13));
		P2Cards.setText("getcard");                                          // same as above
		P2Cards.setColumns(10);
		P2Cards.setBounds(301, 208, 492, 36);
		contentPane.add(P2Cards);
		
		JLabel cardpng1 = new JLabel("New label");
		cardpng1.setIcon(new ImageIcon(gametable.class.getResource("/iamge/icon1.png")));  // Change card image here
		cardpng1.setBackground(new Color(255, 255, 255));
		cardpng1.setBounds(301, 324, 59, 216);
		contentPane.add(cardpng1);
		
		JLabel cardpng2 = new JLabel("New label");
		cardpng2.setIcon(new ImageIcon(gametable.class.getResource("/iamge/icon1.png"))); // Change card image here
		cardpng2.setBounds(371, 324, 59, 216);
		contentPane.add(cardpng2);
		
		JLabel cardpng3 = new JLabel("New label");
		cardpng3.setIcon(new ImageIcon(gametable.class.getResource("/iamge/icon1.png"))); // Change card image here
		cardpng3.setBounds(440, 324, 59, 216);
		contentPane.add(cardpng3);
		
		JLabel cardpng4 = new JLabel("New label");
		cardpng4.setIcon(new ImageIcon(gametable.class.getResource("/iamge/icon1.png"))); // Change card image here
		cardpng4.setBounds(509, 324, 59, 216);
		contentPane.add(cardpng4);
		
		JLabel cardpng5 = new JLabel("New label");
		cardpng5.setIcon(new ImageIcon(gametable.class.getResource("/iamge/icon1.png")));
		cardpng5.setBounds(583, 324, 59, 216);
		contentPane.add(cardpng5);
		
		JLabel cardpng6 = new JLabel("New label");
		cardpng6.setIcon(new ImageIcon(gametable.class.getResource("/iamge/icon1.png")));
		cardpng6.setBounds(651, 324, 59, 216);
		contentPane.add(cardpng6);
		
		JLabel cardpng7 = new JLabel("New label");
		cardpng7.setIcon(new ImageIcon(gametable.class.getResource("/iamge/icon1.png")));
		cardpng7.setBounds(723, 324, 59, 216);
		contentPane.add(cardpng7);
		
		JLabel cardpng8 = new JLabel("New label");
		cardpng8.setIcon(new ImageIcon(gametable.class.getResource("/iamge/icon1.png")));
		cardpng8.setBounds(792, 324, 59, 216);
		contentPane.add(cardpng8);
	}
}

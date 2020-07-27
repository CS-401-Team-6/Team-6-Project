package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

import server.Game;
import server.Message;
import server.User;

//Class to handle the Client side of a console version of blackjack
public class ConsoleRun {
	private ObjectOutputStream outstream;
	private ObjectInputStream instream;
	private String user;
	
	
	
	public ConsoleRun(ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream, String userIn) {
		outstream = objectOutputStream;
		instream = objectInputStream;
		user = userIn;
	}

	public void consoleGameRun() throws ClassNotFoundException, IOException {
		System.out.println("Joining game");
		
		Message gameMessage;
		Scanner scan = new Scanner(System.in);
		String input;
		String bet;
		boolean loggedin = true;
		boolean keepPlaying = true;
		int index = -1;
		while(loggedin) {
		keepPlaying = true;
		//Read the game message. If Message is "WAIT", will print the game state, but no following logic will apply.
		//Will then continue listening, printing new messages if they are WAIT or using logic below if not
		gameMessage = (Message) instream.readObject();
		System.out.println(gameMessage.getGame().toString());
		//If the message is "ACTIVE", this client is the active player
		if(gameMessage.getType().contentEquals("ACTIVE"))
		{
			index = gameMessage.getGame().getPlayers().indexOf(gameMessage.getGame().getActivePlayer());
			//Handle betting first
			System.out.println("Current Balance: " + gameMessage.getGame().getActivePlayer().getBalance());
			System.out.println("Place your bet:");
			bet = scan.nextLine();
			System.out.println("Betting " + bet);
			outstream.writeObject(new Message("BET","Client Sent", bet));
			gameMessage = (Message) instream.readObject();
			//After bet, recieve cards from the game state
			System.out.println("Your cards:");
			System.out.println(gameMessage.getGame().getActivePlayer().getName());
			System.out.println(gameMessage.getGame().getActivePlayer().getHand().toString());
			//Loop to handle hitting or standing. Busting or hitting 21 ends the loop.
			do{
				System.out.println("Enter H to hit or S to stand");
				input = scan.nextLine();
				if (input.contentEquals("H"))
				{
				outstream.writeObject(new Message("HIT","Client Sent", bet));
				outstream.reset();
				gameMessage = (Message) instream.readObject();

				System.out.println("Your cards:");
				System.out.println(gameMessage.getStatus());
				System.out.println(gameMessage.getGame().getActivePlayer().getHand().toString());
					if(gameMessage.getStatus().equals("BUST"))
					{
						System.out.println("BUSTED!");
						keepPlaying = false;
						outstream.reset();
						outstream.writeObject(new Message("DONE","Client Sent", bet));	
					}
					else if(gameMessage.getStatus().contentEquals("21"))
					{
						System.out.println("21!");
						keepPlaying = false;
						outstream.reset();
						outstream.writeObject(new Message("DONE","Client Sent", bet));	
					}
				}
				else if (input.equals("S")){
					System.out.println("STANDING!");
					keepPlaying = false;
					outstream.reset();
					outstream.writeObject(new Message("DONE","Client Sent", bet));	
				}	
			}while(keepPlaying);
			
		}
		//RESULTS means the player was not the last player, but the round is over. Display results.
		//Actual change of balance has already occurred server side.
		else if(gameMessage.getType().contentEquals("RESULTS") &&  index >= 0) {
			gameMessage.getGame().getPlayers().get(index).getHand().calculateScore();
			int yourScore = gameMessage.getGame().getPlayers().get(index).getHand().getScore();
			int dealScore = gameMessage.getGame().getDealer().getHand().getScore();		
			if (yourScore > 21)
				{
				System.out.println("You lose!");
				}
			else if (dealScore > 21) {
				System.out.println("You win!");
				}
			else if (dealScore > yourScore) {
				System.out.println("You lose!");
			}
			else if (yourScore > dealScore) {
				System.out.println("You win!");
			}
			else {
				System.out.println("You pushed!");
			}
		}
		//FINISH means the player was the last player and the round is over. Display results.
		//Actual change of balance has already occurred server side.
		//Only one FINISH message should be sent out, so only one RESET message should be sent back to the server.
		else if(gameMessage.getType().contentEquals("FINISH")) {
			gameMessage.getGame().getPlayers().get(index).getHand().calculateScore();
			int yourScore = gameMessage.getGame().getPlayers().get(index).getHand().getScore();
			int dealScore = gameMessage.getGame().getDealer().getHand().getScore();		
			if (yourScore > 21)
				{
				System.out.println("You lose!");
				}
			else if (dealScore > 21) {
				System.out.println("You win!");
				}
			else if (dealScore > yourScore) {
				System.out.println("You lose!");
			}
			else if (yourScore > dealScore) {
				System.out.println("You win!");
			}
			else {
				System.out.println("You pushed!");
			}
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			outstream.reset();
			outstream.writeObject(new Message("RESET", "Client Sent", "Reset"));	
		}
	}
	}


}

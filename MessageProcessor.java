package server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MessageProcessor {
	//This class will take in a message and depending on the type,
	//process will perform different actions within the server
	//A message object will be returned back
	
	public Message process(Message message, Game game) throws IOException {
		System.out.println("In Processor");
		if (message.getType().equals("HIT")) {
			//Draw a card from the deck for the player
	
			game.getActivePlayer().getHand().hit(game.getDeck().randomDrawCard());
			game.getActivePlayer().getHand().calculateScore();
			System.out.println(game.getActivePlayer().getHand().getScore());
			if(game.getActivePlayer().getHand().getScore() == 21)
			{
				message.setStatus("21");
			}
			else if(game.getActivePlayer().getHand().getScore() > 21)
			{
				message.setStatus("BUST");
				System.out.println("BUST");
			}
			else {
				System.out.println(game.getActivePlayer().getHand().getScore());
				message.setStatus("HitSuccess");
			}
			game.incrementUpdate();
		}
		else if (message.getType().equals("STAND")) {
			//Do nothing
			message.setStatus("StandSuccess");
		}
		else if (message.getType().equals("BET")) {
			//Creates a bet for the user's hand
			System.out.println("BETTING COMPUTE");
			System.out.println(message.getText());
			game.getActivePlayer().getHand().setBet(Integer.parseInt(message.getText()));
			
			message.setStatus("BetSuccess");
			game.getActivePlayer().getHand().hit(game.getDeck().randomDrawCard());
			System.out.println(game.getActivePlayer().getHand().toString());
			game.getActivePlayer().getHand().hit(game.getDeck().randomDrawCard());
			System.out.println(game.getActivePlayer().getHand().toString());
			game.incrementUpdate();
		}
		else if (message.getType().equals("DONE")) {
			message.setStatus("Done");
			System.out.println("DONE");
			//If active player is last player, and done, begin cleanup
			if(game.getActivePlayer().equals(game.getLastPlayer()))
			{
				message.setStatus("Active = last");
				game.getDealer().hit(game.getDeck().randomDrawCard());
				game.getDealer().getHand().calculateScore();
				while(game.getDealer().rules()) {
					game.getDealer().hit(game.getDeck().randomDrawCard());
					game.getDealer().getHand().calculateScore();
				}
				ArrayList<User> players = game.getPlayers();
				players.forEach(player ->  {
					player.getHand().calculateScore();
					if (player.getHand().getScore() > 21)
					{
						player.changeBalance(- player.getHand().getBet());
					}
					else if (game.getDealer().getHand().getScore() > 21) {
						player.changeBalance(player.getHand().getBet());
					}
					else if (game.getDealer().getHand().getScore()  > player.getHand().getScore()) {
						player.changeBalance(- player.getHand().getBet());
					}
					else if (game.getDealer().getHand().getScore()  < player.getHand().getScore()) {
						player.changeBalance(player.getHand().getBet());
					}	
				});
				Server.save();
				
				message.setType("FINISH");
				game.setFinishRound(true);
				game.incrementUpdate();
			}
			else {
				message.setType("WAIT");
			}
		}
		else if (message.getType().equals("RESET")) {
			System.out.println("RESETTING");
			game.newRoundSetup();
			System.out.println(game.toString());
		}
		
		
		message.setGame(game);
		return message;
	}
}

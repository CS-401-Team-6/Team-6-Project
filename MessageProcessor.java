
public class MessageProcessor {
	//This class will take in a message and depending on the type,
	//process will perform different actions within the server
	//A message object will be returned back
	
	public Message process(Message message, Game game) {
		if (message.getType().equals("HIT")) {
			//Draw a card from the deck for the player
			game.activePlayer().getHand().hit(deck.topDrawCard());
			message.setStatus("HitSuccess");
		}
		else if (message.getType().equals("STAND")) {
			//Do nothing
			message.setStatus("StandSuccess");
		}
		else if (message.getType().equals("BET")) {
			//Creates a bet for the user's hand
			game.activePlayer().setBet(Integer.parseInt(message.getText()));
			message.setStatus("BetSuccess");
		}
		else
			System.out.println("INVALID ACTION");
		
		//After message has been processed, we need to change the active user
		//If the last player is reached, we loop back to the first player and set them as active
			//Also triggers a newRound = true boolean to let players into the game
		int index = game.getPlayers().indexOf(game.activePlayer() + 1);
		if (index >= 0 && index <= 4)
			game.setActivePlayer(game.getPlayers().get(index));
		if (game.activePlayer().equals(game.getPlayers().get(game.getPlayers().size() - 1))) {
			game.setActivePlayer(game.getPlayers().get(0));
			game.setNewRound(true);
		}
		
		message.setGame(game);
		return message;
	}
}
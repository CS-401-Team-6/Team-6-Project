package project;

public class MessageProcessor {
	//This class will take in a message and depending on the type,
	//process will perform different actions within the server
	//A message object will be returned back, possibly more...
	
	//EDIT: Might change return variable to void and just send directly back using ObjectOutputStream
	//EDIT: Instead of passing in User and Deck, we can also pass in Game instead, however
			//we would have to know which player to draw the deck for.
	public Message process(Message message, User user, Deck deck) {
        // get the input stream from the connected socket
        // create an ObjectInputStream so we can read data from it.
		//EDIT: These will either be passed in as a parameter or created here.
		if (message.type.equals("HIT")) {
			//Draw a card from the deck for the player
			user.getHand().hit(deck.topDrawCard());
			message.setStatus("HitSuccess");
			//EDIT: Use ObjectOutputStream to send objects back.
		}
		else if (message.type.equals("DOUBLE DOWN")) {
			//Double the player's bet amount, draw a card and place in player's hand
			//EDIT: Double player bet amount
			user.getHand().hit(deck.topDrawCard());
			message.setStatus("DoubleDownSuccess");
			//EDIT: Use ObjectOutputStream to send objects back.
		}
		else if (message.type.equals("SPLIT")) {
			//Should already be checked if split is available back in client
			//Create a new hand by sending one of the cards over and drawing a second card for each hand
			//The betting amount for the second hand will be equal to the starting bet
			message.setStatus("SplitSuccess");
		}
		else if (message.type.equals("STAND")) {
			//Do nothing
			message.setStatus("StandSuccess");
		}
		else
			System.out.println("INVALID ACTION");
		return message;
	}
}
import client.Message;

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
		if (message.getType().equals("HIT")) {
			//Draw a card from the deck for the player
			user.hand.hit();
			message.setStatus("Success");
			//EDIT: Use ObjectOutputStream to send objects back.
		}
		else if (message.getType().equals("DOUBLE DOWN")) {
			//Double the player's bet amount, draw a card and place in player's hand
			//EDIT: Double player bet amount
			user.hand.hit();
			message.setStatus("Success");
			//EDIT: Use ObjectOutputStream to send objects back.
		}
		else if (message.getType().equals("SPLIT")) {
			//Should already be checked if split is available back in client
			//Create a new hand by sending one of the cards over and drawing a second card for each hand
			//The betting amount for the second hand will be equal to the starting bet
		}
		else if (message.getType().equals("STAND")) {
			//Do nothing
		}
		else
			System.out.println("INVALID ACTION");
		return message;
	}
}
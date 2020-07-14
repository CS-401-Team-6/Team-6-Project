import java.io.ObjectOutputStream;

public class MessageProcessor {
	//This class will take in a message and depending on the type,
	//process will perform different actions within the server
	//A message object will be returned back
	
	public Message process(Message message, User user, Deck deck) {
		if (message.getType().equals("HIT")) {
			//Draw a card from the deck for the player
			user.hand.hit();
			message.setStatus("Success");
		}
		else if (message.getType().equals("STAND")) {
			//Do nothing
			message.setStatus("Success");
		}
		else if (message.getType().equals("BET")) {
			//Creates a bet for the user's hand
			user.setBet(Integer.parseInt(message.getText());
			message.setStatus("Success");
		}
		else
			System.out.println("INVALID ACTION");
		return message;
	}
}
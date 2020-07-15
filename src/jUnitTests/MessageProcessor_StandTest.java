package jUnitTests;
import project.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class MessageProcessor_StandTest {

	@Test
	public void test() {
		//fail("Not yet implemented");
		Message message = new Message("STAND", "Waiting", "StandMessage");
		byte arr[] = new byte[] {'p', 'a', 's', 's'};
		User user = new User("username", arr);
		Hand hand = new Hand();
		user.setHand(hand);
		Deck deck = new Deck();
		MessageProcessor mp = new MessageProcessor();
		mp.process(message, user, deck);
		assertEquals("StandSuccess" ,message.getStatus());
	}

}

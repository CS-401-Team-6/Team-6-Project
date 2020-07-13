package jUnitTests;
import project.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class MessageProcessor_ProcessHitTest {

	@Test
	public void test() {
		//fail("Not yet implemented");
		Message message = new Message("HIT", "Waiting", "HitMessage");
		User user = new User("username", "password");
		Hand hand = new Hand();
		user.setHand(hand);
		Deck deck = new Deck();
		MessageProcessor mp = new MessageProcessor();
		mp.process(message, user, deck);
		assertEquals("HitSuccess" ,message.getStatus());
	}

}

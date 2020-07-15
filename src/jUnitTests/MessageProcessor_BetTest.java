package jUnitTests;

import static org.junit.Assert.*;

import org.junit.Test;

import project.Deck;
import project.Hand;
import project.Message;
import project.MessageProcessor;
import project.User;

public class MessageProcessor_BetTest {

	@Test
	public void test() {
		//fail("Not yet implemented");
		Message message = new Message("BET", "Waiting", "100");
		byte arr[] = new byte[] {'p', 'a', 's', 's'};
		User user = new User("username", arr);
		Hand hand = new Hand();
		user.setHand(hand);
		Deck deck = new Deck();
		MessageProcessor mp = new MessageProcessor();
		mp.process(message, user, deck);
		assertEquals("BetSuccess" ,message.getStatus());
	}

}

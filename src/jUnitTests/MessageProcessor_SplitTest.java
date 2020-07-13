package jUnitTests;
import project.*;
import static org.junit.Assert.*;

import org.junit.Test;

import project.Deck;
import project.Hand;
import project.Message;
import project.MessageProcessor;
import project.User;

public class MessageProcessor_SplitTest {

	@Test
	public void test() {
		//fail("Not yet implemented");
		Message message = new Message("SPLIT", "Waiting", "SplitMessage");
		User user = new User("username", "password");
		Hand hand = new Hand();
		user.setHand(hand);
		Deck deck = new Deck();
		MessageProcessor mp = new MessageProcessor();
		mp.process(message, user, deck);
		assertEquals("SplitSuccess" ,message.getStatus());
	}

}

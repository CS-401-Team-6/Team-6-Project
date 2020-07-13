package jUnitTests;
import project.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class Message_SetTextTest {

	@Test
	public void test() {
		//fail("Not yet implemented");
		Message message = new Message();
		message.setText("TextTest");
		assertEquals("TextTest", message.getText());
	}

}

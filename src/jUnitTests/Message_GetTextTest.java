package jUnitTests;
import project.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class Message_GetTextTest {

	@Test
	public void test() {
		//fail("Not yet implemented");
		Message message = new Message();
		assertEquals("Undefined", message.getText());
	}

}

package jUnitTests;
import project.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class Message_SetStatusTest {

	@Test
	public void test() {
		//fail("Not yet implemented");
		Message message = new Message();
		message.setStatus("StatusTest");
		assertEquals("StatusTest", message.getStatus());
	}

}

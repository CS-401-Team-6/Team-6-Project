package jUnitTests;
import project.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class Message_SetTypeTest {

	@Test
	public void test() {
		//fail("Not yet implemented");
		Message message = new Message();
		message.setType("TestType");
		assertEquals("TestType", message.getType());
	}

}

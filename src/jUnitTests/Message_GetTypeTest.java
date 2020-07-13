package jUnitTests;
import project.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class Message_GetTypeTest {

	@Test
	public void test() {
		//fail("Not yet implemented");
		Message message = new Message();
		assertEquals("Undefined", message.getType());
	}

}

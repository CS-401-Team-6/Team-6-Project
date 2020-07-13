package jUnitTests;
import project.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class Message_GetStatusTest {

	@Test
	public void test() {
		//fail("Not yet implemented");
		Message message = new Message();
		assertEquals("Undefined", message.getStatus());
	}

}

package jUnitTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ Message_GetStatusTest.class, Message_GetTextTest.class, Message_GetTypeTest.class,
		Message_SetStatusTest.class, Message_SetTextTest.class, Message_SetTypeTest.class })
public class AllMessageTests {

}

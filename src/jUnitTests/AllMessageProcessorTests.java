package jUnitTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ MessageProcessor_DoubleDownTest.class, MessageProcessor_ProcessHitTest.class,
		MessageProcessor_SplitTest.class, MessageProcessor_StandTest.class })
public class AllMessageProcessorTests {

}

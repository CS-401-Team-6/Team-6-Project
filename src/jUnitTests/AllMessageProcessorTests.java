package jUnitTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ MessageProcessor_BetTest.class, MessageProcessor_HitTest.class, MessageProcessor_StandTest.class })
public class AllMessageProcessorTests {

}

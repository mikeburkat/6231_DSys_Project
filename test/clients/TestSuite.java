package clients;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({
  UnitTestGameServerAssignment1.class,
  UnitTestGameServerAssignment2.class,
  UnitTestMultiThreadAssignment1.class,
  UnitTestMultiThreadAssignment2.class
})

public class TestSuite {
  // the class remains empty,
  // used only as a holder for the above annotations
}


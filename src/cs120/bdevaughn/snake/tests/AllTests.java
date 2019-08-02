package cs120.bdevaughn.snake.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ControllerTests.class, MushroomTests.class, ScoreTests.class,
		SegmentTests.class, SnakeTests.class, TimeScreenTests.class })
public class AllTests {

}

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ElevatorTest {
	@Test
	void addTest() {
		Elevator e = new Elevator(new Building(3));
		assertEquals(0, e.jobs.size());
		e.createJob(new Person("Rebecca", "Turner"), 2);
		assertEquals(1, e.jobs.size());
		e.processAllJobs();
		assertEquals(0, e.jobs.size());
	}
}

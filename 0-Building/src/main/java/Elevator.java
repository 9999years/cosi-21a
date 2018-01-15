public class Elevator {
	protected ArrayQueue<Job> jobs;
	protected Building building;
	protected int floor;

	protected static final int INITIAL_JOB_CAPACITY = 0;

	public Elevator(Building building) {
		jobs = new ArrayQueue<Job>();
		floor = 1;
		this.building = building;
	}

	protected void moveTo(int newFloor) {
		int inc = (floor < newFloor) ? 1 : -1;
		for(int i = floor; i != newFloor; i += inc) {
			// technically a lie; `floor` isn't updated until after
			// the loop. this elevator simulation is NOT thread
			// safe!
			System.out.println("The elevator is now at floor " + i);
		}
		floor = newFloor;
	}

	/**
	 *  This method should simply add, in the right place, a new job
	 *  to be completed by the elevator.
	 *  @param person the person that requested the elevator
	 *  @param floor the desired floor number
	 */
	public void createJob(Person person, int floor) {
		jobs.add(new Job(person, floor));
	}

	/**
	 *  This method will be used after completing (and removing) a job.
	 *  The use of this method should guarantee that the jobs array is
	 *  in a valid state.
	 */
	public void cleanUpJobs() {
		// implemented within the ArrayQueue class
	}

	/**
	 *  This method should remove jobs one by one (in the right order)
	 *  and process them individually.
	 */
	public void processAllJobs() {
		while(jobs.size() > 0) {
			processJob(jobs.remove());
		}
	}

	/**
	 *  This method should process a job, and move the elevator floor
	 *  by floor (while printing updates) in order to reach the desired
	 *  floor. Then, the exit method should be called, simulating the exit
	 *  of a person (if necessary).
	 *  @param job the job to be processed
	 */
	public void processJob(Job job) {
		moveTo(1);
		moveTo(job.floor);
		exit(job.person, job.floor);
	}

	/**
	 *  This method should call a method on the building for a person
	 *  to enter a given floor (hold a reference to the person in the given
	 *  floor).
	 *  @param person the person exiting at a given floor
	 *  @param floor the floor at which the person is exiting
	 */
	public boolean exit(Person person, int floor) {
		return building.enterFloor(person, floor);
	}

	/**
	 *  The string should be informative yet clean and concise
	 */
	public String toString() {
		return "Elevator[floor=" + floor
			+ ", jobs=" + jobs.size() + "]";
	}
}

public class Elevator {

	// Fields
	// TODO: what object instances should you keep track of?
	/* Note:    You need to keep track of the current floor
	   at which the elevator is at. Every time the
	   elevator moves, a line should be printed to
	   indicate the current floor of the elevator,
	   which should change a floor at a time. */


	// Constructor

	/**
	 *  Make sure to instantiate the required objects, such as the array
	 *  of jobs that the elevator needs to have.
	 */
	public Elevator() {
		// TODO: implement me!

	}

	// Methods

	/**
	 *  This method should simply add, in the right place, a new job
	 *  to be completed by the elevator.
	 *  @param person the person that requested the elevator
	 *  @param floor the desired floor number
	 */
	public boolean createJob(Person person, int floor) {
		// TODO: implement me!
		/* Note:    In order to add jobs to the array you will
		   need to have a variable that will keep track
		   of the index of the job added last, this way
		   you will be able to add a job at the next
		   position in the array. */
		return false;
	}

	/**
	 *  This method will be used after completing (and removing) a job.
	 *  The use of this method should guarantee that the jobs array is
	 *  in a valid state.
	 */
	public void cleanUpJobs() {
		// TODO: implement me!
		/* Note:    To remove a job from the array, you will need
		   to remove the job added first, that is to say
		   the job in jobs[0]. However, after a job is
		   completed, you must shift all the elements of
		   the array to the left, so that jobs[0] contains
		   the next job to be complted. Make sure to also
		   update the variable holding the index of the job
		   added last. This is not efficient but will do
		   for now. */

	}

	/**
	 *  This method should remove jobs one by one (in the right order)
	 *  and process them individually.
	 */
	public void processAllJobs() {
		// TODO: implement me!

	}

	/**
	 *  This method should process a job, and move the elevator floor
	 *  by floor (while printing updates) in order to reach the desired
	 *  floor. Then, the exit method should be called, simulating the exit
	 *  of a person (if necessary).
	 *  @param job the job to be processed
	 */
	public boolean processJob(Job job) {
		// TODO: implement me!
		return false;
	}

	/**
	 *  This method should call a method on the building for a person
	 *  to enter a given floor (hold a reference to the person in the given
	 *  floor).
	 *  @param person the person exiting at a given floor
	 *  @param floor the floor at which the person is exiting
	 */
	public boolean exit(Person person, int floor) {
		// TODO: implement me!
		return false;
	}

	/**
	 *  The string should be informative yet clean and concise
	 */
	public String toString() {
		// TODO: implement me!
		return null;
	}
}

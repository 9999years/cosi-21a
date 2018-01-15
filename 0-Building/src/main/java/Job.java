public class Job {
	public final Person person;
	public final int floor;

	/**
	 *  Make sure to pass the necessary parameters in order to
	 *  instantiate a Job object that holds a person and a floor.
	 */
	public Job(Person person, int floor) {
		this.person = person;
		this.floor = floor;
	}

	/**
	 *  The string should be informative yet clean and concise
	 */
	public String toString() {
		return "Job[person=" + person
			+ ", destination=floor"
			+ floor + "]";
	}
}

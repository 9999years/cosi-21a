public class Building {
	protected Floor[] floors;
	protected Elevator elevator;

	/**
	 *  Make sure to instantiate the building's elevator and the array
	 *  of floors. You can decide on the specifics of the building.
	 */
	public Building() {
		this(10);
	}

	public Building(int floors) {
		this.floors = new Floor[floors];
		for(int i = 0; i < this.floors.length; i++) {
			this.floors[i] = new Floor();
		}
		elevator = new Elevator(this);
	}

	/**
	 *  This method will process the request made by a person to enter the
	 *  building. Then, it should pass on the request to an elevator
	 *  instance.
	 *  Make sure that the elevator visits the first floor and then the floor
	 *  requested by the person.
	 *  @param person the person that has requested access to the building
	 *  @param floor the number of the desired floor
	 */
	public boolean enterElevator(Person person, int floor) {
		if(floor > floors.length) {
			return false;
		}
		elevator.createJob(person, floor);
		person.location = "elevator";
		return true;
	}

	/**
	 *  This will call a method in the elevator instance that should process
	 *  all current jobs.
	 */
	public void startElevator() {
		elevator.processAllJobs();
	}

	/**
	 *  This method should simply access the given floor object and
	 *  call a method to save a reference to the person in the given
	 *  floor.
	 *  @param person the person to acceess the floor
	 *  @param floor the floor to be entered
	 */
	public boolean enterFloor(Person person, int floor) {
		if(floor > floors.length) {
			return false;
		}
		person.location = "floor " + floor;
		return floors[floor - 1].enterFloor(person);
	}

	/**
	 *  The string should be informative yet clean and concise
	 */
	public String toString() {
		return "Building[floors=" + floors.length + "]";
	}
}

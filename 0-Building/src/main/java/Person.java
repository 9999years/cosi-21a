public class Person {
	public final String personal;
	public final String family;

	// (shrug)
	// updated by other classes
	public String location = "purgatory";

	/**
	 *  Make sure to pass as parameter the necessary information to
	 *  initialize the object with a name and a last name.
	 */
	public Person(String personal, String family) {
		this.personal = personal;
		this.family = family;
	}

	// Methods

	/**
	 *  This method will simply call the appropriate method from the
	 *  building instance to enter the elevator and request a job.
	 *  @param building the building to be entered
	 *  @param floor the floor requested
	 */
	public boolean enterBuilding(Building building, int floor) {
		building.enterElevator(this, floor);
		return false;
	}

	/**
	 *  You should hold a variable (that you will need to update at certain
	 *  moments) that will say the location of a person. You can choose how
	 *  to implement this, but you should return strings like "In lobby",
	 *  "In Elevator", or "In floor 4", etc...
	 */
	public String getLocation() {
		return "In " + location;
	}

	/**
	 *  The string should be informative yet clean and concise
	 */
	public String toString() {
		// TODO: implement me!
		return null;
	}
}

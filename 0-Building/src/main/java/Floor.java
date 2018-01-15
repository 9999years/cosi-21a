public class Floor {
	// Note: you may assume that the capacity won't be surpassed.
	public static final int floorCapacity = 100;

	protected ArrayQueue<Person> people;

	/**
	 *  Make sure to instantiate the required fields.
	 */
	public Floor() {
		people = new ArrayQueue<Person>(floorCapacity);
	}

	// Methods

	/**
	 *  This method should save a reference to the person.
	 *  @param person the person to enter the floor.
	 */
	public boolean enterFloor(Person person) {
		if(people.size() >= floorCapacity) {
			return false;
		} else {
			people.add(person);
			return true;
		}
	}

	/**
	 *  The string should be informative yet clean and concise
	 */
	public String toString() {
		return "Floor[people=" + people.size() + "]";
	}
}

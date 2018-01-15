public class Floor {
	// Note: you may assume that the capacity won't be surpassed.
	public static final int floorCapacity = 100;

	public final int number;
	protected ArrayQueue<Person> people;

	/**
	 *  Make sure to instantiate the required fields.
	 */
	public Floor(int number) {
		people = new ArrayQueue<Person>(floorCapacity);
		this.number = number;
	}

	public void log(String msg) {
		System.out.println("FLOOR " + number + ": " + msg);
	}

	/**
	 *  This method should save a reference to the person.
	 *  @param person the person to enter the floor.
	 */
	public boolean enterFloor(Person person) {
		if(people.size() >= floorCapacity) {
			return false;
		} else {
			people.add(person);
			log(person + " added to floor; there are now " + people.size() + " people on the floor");
			return true;
		}
	}

	/**
	 *  The string should be informative yet clean and concise
	 */
	public String toString() {
		return "Floor[number=" + number + ", people=" + people.size() + "]";
	}
}

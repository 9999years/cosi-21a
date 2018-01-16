public class Floor {
	// Note: you may assume that the capacity won't be surpassed.
	public static final int floorCapacity = 100;

	public final int number;
	protected Person[] people;
	protected int peopleCount;

	public Floor(int number) {
		people = new Person[floorCapacity];
		peopleCount = 0;
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
		if(peopleCount >= floorCapacity) {
			return false;
		} else {
			people[peopleCount] = person;
			peopleCount++;
			log(person + " added to floor; there are now "
				+ peopleCount + " people on floor "
				+ number);
			return true;
		}
	}

	public String toString() {
		return "Floor[number=" + number
			+ ", people=" + peopleCount + "]";
	}
}

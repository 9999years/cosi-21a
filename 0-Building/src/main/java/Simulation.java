import java.util.Random;

/**
 * i'll be honest, i *hate* writing "client programs" like this
 */
public class Simulation {
	public static final int PEOPLE = 10;
	public static final int MAX_FLOORS = 8;
	public static final int MIN_FLOORS = 2;

	// hey, did you know java allows trailing commas in array
	// initializations? i didnt!
	// https://blogs.ancestry.com/cm/calling-james-smith-10-most-common-first-and-surname-combinations/
	public static final String[] names = {
		"Mary", "Elizabeth", "Sarah", "Nancy", "Ann", "Catherine",
		"Margaret", "Jane", "Susan", "Hannah", "John", "William",
		"James", "Thomas", "George", "Joseph", "Samuel", "Henry",
		"David", "Daniel", "Anguish", "Lemmon", "Mercy", "Pepper",
		"Pleasant", "Basket", "Cutlip", "Hoof", "Hardy", "Baptist",
		"Truelove", "Sparks", "Snow", "Frost", "Mourning", "Chestnut",
		"Boston", "Frog", "Jedediah", "Brickhouse", "Hannah",
		"Petticoat", "and", "Hannah", "Cheese", "Ruth", "Shaves",
		"Christy", "Forgot", "Joseph", "Came", "Joseph", "Rodeback",
		"Agreen", "Crabtree", "River", "Jordan", "Booze", "Still",
		"Comfort", "Clock", "Sharp", "Blount", "Sarah", "Simpers",
		"Barbary", "Staggers", "Noble", "Gun",
	};

	// yeah, that kinda defeats the purpose of using an rng,
	// doesn't it. but i don't want to risk you getting different
	// output than me!
	public static Random rand = new Random(1);

	public static int boundedInt(int min, int max) {
		return min + rand.nextInt(max - min);
	}

	public static String name() {
		return names[rand.nextInt(names.length)];
	}

	public static int floor(int count) {
		return boundedInt(1, count);
	}

	public static void main(String[] args) {
		int floorCount = boundedInt(MIN_FLOORS, MAX_FLOORS);
		System.out.println("Creating a building with "
			+ floorCount + " floors");

		// create a building
		Building building = new Building(floorCount);

		// add some people
		for(int i = 1; i <= PEOPLE; i++) {
			// create some random names and a destination
			Person p = new Person(
				name(),
				name() + "-" + i
			);
			int dest = floor(floorCount);

			// shove the new person in the elevator
			building.enterElevator(p, dest);
			System.out.println(p + " has entered the building, "
				+ "and wants to go to floor " + dest);

			// assignment dictates two groups, so we send the
			// elevator to pick up folks halfway through
			//if(rand.nextFloat() > 0.75) {
			if(i == PEOPLE / 2) {
				System.out.println("Starting the elevator!");
				building.startElevator();
			}
		}

		// pick up the stragglers
		System.out.println("Starting the elevator!");
		building.startElevator();
		System.out.println("Finished!");
	}
}

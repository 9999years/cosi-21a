import java.util.Random;

public class Simulation {
	public static void main(String[] args) {
		Random rand = new Random();
		int floorCount = rand.nextInt(10) + 2;
		System.out.println("Creating a building with " + floorCount + " floors");
		Building building = new Building(floorCount);
		for(int i = 0; i < 20; i++) {
			Person p = new Person(
				Integer.toString(i),
				"Human"
			);
			int dest = rand.nextInt(floorCount - 1) + 1;
			building.enterElevator(p, dest);
			System.out.println("Person " + i + " has entered the building, and wants to go to floor " + dest);
			if(rand.nextFloat() > 0.75) {
				System.out.println("Starting the elevator!");
				building.startElevator();
			}
		}
		System.out.println("Bye!");
	}
}

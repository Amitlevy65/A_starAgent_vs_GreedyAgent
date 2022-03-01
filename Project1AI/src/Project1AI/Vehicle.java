package Project1AI;

/**
 * 
 * @author Amit Levy 313268773
 * @author Roie Tzemach 208670018
 */

public abstract class Vehicle {

	int n;
	int totalPeopleAmount;
	int peopleamount;
	int score = 0;
	int penalty = 2;
	Vertex start;
	boolean isAlive = true;
	boolean gameOn = true;

	public Vehicle(int n, int totalpeopleamount, Vertex start) {
		this.n = n;
		this.peopleamount = 0;
		this.score = 0;
		this.start = start;
		this.isAlive = true;
		this.totalPeopleAmount = totalpeopleamount;
	}

	public abstract void check(Vertex currentVertex); // adds the amount of people in the vertex to the vehicle.

	public abstract void updateScore(Vertex currentVertex); // updates the score if arrived to a shelter and checks if
															// the goal has been reached.

}

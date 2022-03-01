package Project1AI;

/**
 * 
 * @author Amit Levy 313268773
 * @author Roie Tzemach 208670018
 */

public class GreedyAgent extends Vehicle implements Runnable {

	Vertex tempVertex = this.start;
	Vertex lastVertex; // holds the previous vertex.
	Vertex closestVertex; // holds the next closest vertex by the relevant computation.
	int totalweight = 0;
	int minweight; // the value of the weight of the closest vertex by the relevent computation.
	boolean peoplethere = false; // changes if any of the next vertices has people in it.
	boolean shelterthere = false; // changes if any of the next vertices has shelter in it.

	public GreedyAgent(int n, int totalpeopleamount, Vertex start) {
		super(n, totalpeopleamount, start);
	}

	public void drive() {
		check(tempVertex); // picks up people from the current vertex onto the vehicle.
		if (this.peopleamount > 0) { // if the vehicle carries people.
			shelterPath(); // creating a path to the closest shelter.
		} else if (this.peopleamount == 0) { // if the vehicle doesn't carrie any people.
			peoplePath(); // creating a path to the closest vertex with people on it.
		} else if (shelterthere == false && peoplethere == false) { // checks if the nearby vertices has people or shelter on it.
			shortestPath(); // creating the path to the closest vertex if needed.	
		} else if(tempVertex.isShelterexists() == true && peoplethere == false) { // checks if current vertex is a shelter and no people around.
			shortestPath();	
		}
		updateScore(tempVertex); // updates the score and checks if the goal has been reached.
	}

	public void check(Vertex currentVertex) { // recieves the vertex the vehicle as drove to.
		if (currentVertex.getPeople() > 0) {// if the vertex has people in it, it will pick them up and set 0 to the
											// amount it now has.
			this.peopleamount += currentVertex.getPeople();
			if (currentVertex.getPeople() == 1) {
				System.out.println("You picked up " + currentVertex.getPeople() + " person, you now carry "
						+ this.peopleamount + " in total.");
				currentVertex.setPeople(0);
			} else {
				System.out.println("You picked up " + currentVertex.getPeople() + " more people, you now carry "
						+ this.peopleamount + " in total.");
				currentVertex.setPeople(0);
			}
		}
	}

	public void updateScore(Vertex currentVertex) {// recieves the vertex the vehicle as drove to.
		if (currentVertex.isShelterexists()) {// checks if it has a shelter, then adds the score and sets 0 to the
												// amount it carries.
			this.score += this.peopleamount;
			System.out.println("@&@&@&@  SHELTER  @&@&@&@");
			System.out.println("You saved " + this.peopleamount + " more people, your SCORE is now: " + this.score);
			this.peopleamount = 0;
			if (this.score == totalPeopleAmount) {// checks if the goal has been reached, comparing the amount of people
													// saved to the population in the game.
				gameOn = false;
			}
		}
	}

	public void shelterPath() {
		minweight = Integer.MAX_VALUE; // gets max value in order to check if it goes any lower then that and so on.
		for (Vertex v : tempVertex.getNextvertices()) { // checks all the nearby vertices
			if (v.shelterexists == true) { // if it has a shelter
				for (Edge e : tempVertex.getNextroads()) { // if it does, if the roads ahead of it connect to this
															// vertex.
					if ((e.getTo() == v || e.getFrom() == v) && e.getWeight() < minweight) { // also checks if the
																								// road's weight is
																								// lower then the last
																								// min weight taken.
						minweight = e.getWeight(); // sets the minweight to the new minimum weight.
						closestVertex = v; // adds the closest vertex to be the closest one so far
					}
				}
				shelterthere = true; // using this to notify that the nearby vertices has a shelter.
			}
		}
		for (Edge e : tempVertex.getNextroads()) {
			if (e.getFrom() == closestVertex || e.getTo() == closestVertex) {
				totalweight += e.getWeight(); // adds the weight of the edge taken into the total weight.
			}
		}
		lastVertex = tempVertex; // remember the previous vertex.
		tempVertex = closestVertex; // moves onto the closest vertex with a shelter.
		tempVertex.setDeadline(tempVertex.getDeadline() - totalweight); // adds the deadline according to the weight of
																		// all the edges taken so far.
		System.out.println("Driving from " + lastVertex + " to " + tempVertex);
		if (tempVertex.getDeadline() <= 0) { // if it's deadline is < 0, destroyes it.
			tempVertex.setDestroyed(true);
			this.isAlive = false; // destroys the agent as well since it presents on the destroyed vertex.
		}
		System.out.println("=================================================");
	}

	private void peoplePath() { // same as the above, but the term checked is different.
		minweight = Integer.MAX_VALUE;
		for (Vertex v : tempVertex.getNextvertices()) {
			if (v.people > 0) { // checks if any of the nearby vertices has people on it.
				for (Edge e : tempVertex.getNextroads()) {
					if ((e.getTo() == v || e.getFrom() == v) && e.getWeight() < minweight) {
						minweight = e.getWeight(); // computes which is the closest one.
						closestVertex = v;
					}
				}
				peoplethere = true; // using it to notify that the nearby vertices has people on it.
			}
		}
		for (Edge e : tempVertex.getNextroads()) {
			if (e.getFrom() == closestVertex || e.getTo() == closestVertex) {
				totalweight += e.getWeight();
			}
		}
		lastVertex = tempVertex;
		tempVertex = closestVertex;
		tempVertex.setDeadline(tempVertex.getDeadline() - totalweight);
		if (isAlive == true) {
			System.out.println("Driving from " + lastVertex + " to " + tempVertex);
		}
		if (tempVertex.getDeadline() <= 0) {
			tempVertex.setDestroyed(true);
			this.isAlive = false;
		}
		System.out.println("=================================================");
	}

	private void shortestPath() { // same as the above but without a term, since it will be checked before using
									// it.
		minweight = Integer.MAX_VALUE; // if the nearby vertices are missing a shelter or people on it.
		for (Vertex v : tempVertex.getNextvertices()) {
			for (Edge e : tempVertex.getNextroads()) {
				if ((e.getTo() == v || e.getFrom() == v) && e.getWeight() < minweight) {
					minweight = e.getWeight();
					closestVertex = v; // computes which is the closest shelter.
				}
			}
		}
		for (Edge e : tempVertex.getNextroads()) {
			if (e.getFrom() == closestVertex || e.getTo() == closestVertex) {
				totalweight += e.getWeight();
			}
		}
		lastVertex = tempVertex;
		tempVertex = closestVertex;
		tempVertex.setDeadline(tempVertex.getDeadline() - totalweight);
		if (isAlive == true) {
			System.out.println("Driving from " + lastVertex + " to " + tempVertex);
		}
		if (tempVertex.getDeadline() <= 0) {
			tempVertex.setDestroyed(true);
			this.isAlive = false;
		}
		System.out.println("=================================================");
	}

	public void run() {
		do {
			drive();
		} while (this.gameOn == true && this.isAlive == true); // ends if the agent dies or the goal is reached.
		if (isAlive == false) {
			System.out.println("======= GAME OVER! ======");
			System.out.println("@@@@@@ This Vertex you arrived to has been DESTROYED! @@@@@@@");
			System.out.println("======= YOU LOST! ======");
			System.out.println("Your total Score is: " + (this.score - (this.peopleamount + penalty)));
		} else if (this.gameOn == false) {
			System.out.println("======= GAME OVER! ======");
			System.out.println("======= YOU WIN! ======");
		}
	}

}

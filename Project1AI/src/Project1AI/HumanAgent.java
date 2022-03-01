package Project1AI;

import java.util.Scanner;
/**
 * 
 * @author Amit Levy 313268773
 * @author Roie Tzemach 208670018
 */

public class HumanAgent extends Vehicle implements Runnable {

	int input;
	int vertexIndex;							//holds the index of the chosen next vertex.
	Vertex tempVertex = this.start;				//the vertex that will be changed according to the chosen route.
	Vertex lastVertex;							//the last vertex we've been to.
	int totalweight = 0;

	public HumanAgent(int n, int totalpeopleamount, Vertex start) {
		super(n, totalpeopleamount, start);
	}

	public void drive() {
		check(tempVertex);							//picks up people from the current vertex onto the vehicle.
		do {										
			System.out.println("Please enter the id of the next vertex you'd like to visit: ");	
			System.out.println("The options are: " + tempVertex.getNextIndexes());	//printing out the next possible moves.
			Scanner scanner = new Scanner(System.in);
			input = scanner.nextInt();				//gets the input from the user.
		} while (!(tempVertex.getNextIndexes().contains(input))); //checks if the input is correct

		for (Vertex vertex : tempVertex.getNextvertices()) {	//gets the index of the chosen next vertex from the list of next possibilities.
			if (vertex.getId() == input) {
				vertexIndex = tempVertex.getNextvertices().indexOf(vertex);
			}
		}

		for (Edge e : tempVertex.getNextroads()) {				//adds the weight of the road onto a totalweight variable.
			if (e.getFrom() == tempVertex.getNextvertices().get(vertexIndex)) {
				totalweight += e.getWeight();
			} else if (e.getTo() == tempVertex.getNextvertices().get(vertexIndex)) {
				totalweight += e.getWeight();
			}
		}
		lastVertex = tempVertex;								//remembers where you came from.
		tempVertex = tempVertex.getNextvertices().get(vertexIndex); // moving to the next one.
		tempVertex.setDeadline(tempVertex.getDeadline() - totalweight);	//decreasing the deadline of the current vertex due to the totalweight -> from all previous moves.
		System.out.println("Driving from " + lastVertex + " to " + tempVertex);// delivering the message of the next move.
		if (tempVertex.getDeadline() <= 0) {	
			tempVertex.setDestroyed(true);		//destroyes the vertex if the deadline is < 0.
			this.isAlive = false;				//destroyes the vehicle too, since it's the vertex he's present at.
		}
		System.out.println("=================================================");
		updateScore(tempVertex);
	}

	public void check(Vertex currentvertex) {	//recieves the vertex the vehicle as drove to.
		if (currentvertex.getPeople() > 0) { // if the vertex has people in it, it will pick them up and set 0 to the amount it now has.
			this.peopleamount += currentvertex.getPeople();
			if (currentvertex.getPeople() == 1) {
				System.out.println("You picked up " + currentvertex.getPeople() + " person, you now carry "
						+ this.peopleamount + " in total.");
				currentvertex.setPeople(0);
			} else {
				System.out.println("You picked up " + currentvertex.getPeople() + " more people, you now carry "
						+ this.peopleamount + " in total.");
				currentvertex.setPeople(0);
			}
		}
	}

	public void updateScore(Vertex currentvertex) { //recieves the vertex the vehicle as drove to.
		if (currentvertex.isShelterexists()) {		//checks if it has a shelter, then adds the score and sets 0 to the amount it carries.
			this.score += this.peopleamount;
			System.out.println("@&@&@&@  SHELTER  @&@&@&@");
			System.out.println("You saved " + this.peopleamount + " more people, your SCORE is now: " + this.score);
			this.peopleamount = 0;
			if (this.score == totalPeopleAmount) {	//checks if the goal has been reached, comparing the amount of people saved to the population in the game.
				gameOn = false;
			}
		}
	}

	public void run() {
		do {
			drive();
		} while (this.gameOn == true && this.isAlive == true); //ends if the agent is dead or reached the goal.
		if (isAlive == false) {
			System.out.println("======= GAME OVER! ======");
			System.out.println("@@@@@@ This Vertex you arrived to has been DESTROYED! @@@@@@@");
			System.out.println("======= YOU LOST! ======");
			System.out.println("Your total Score is: " + (this.score - (this.peopleamount + penalty))); //computes the penalty.
		} else if (this.gameOn == false) {
			System.out.println("======= GAME OVER! ======");
			System.out.println("======= YOU WIN! ======");
		}
	}

}

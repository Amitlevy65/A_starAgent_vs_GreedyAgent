package Part2;

import java.util.ArrayList;
/**
 * 
 * @author Amit Levy 313268773
 * @author Roie Tzemach 208670018
 */

public class aStarAgent extends Vehicle implements Runnable {


	Vertex tempVertex = this.start;				//the vertex that will be changed according to the chosen route.
	Vertex lastVertex;							//the last vertex we've been to.
	int totalweight = 0;						//the total weight taken to be checked against the deadline of the reached vertex.
	ArrayList<Vertex> nexts = new ArrayList<Vertex>(); //a list of the next available vertices.
	int minHeuristic = Integer.MAX_VALUE;		//The minimum weight measured to be remembered.
	int length = 0;								//The total weight that was taken and is checked while calculating the next efficient move.
	int tempweight;
	public aStarAgent(int n, int totalpeopleamount, Vertex start) {
		super(n, totalpeopleamount, start);
	}

	public void drive() {
		check(tempVertex);							//picks up people from the current vertex onto the vehicle.
		calcNextMove();								//finding and moving to the next most efficient vertex according to the algorithm.
		for (Edge e : lastVertex.getNextroads()) {				//adds the weight of the road onto a totalweight variable.
			if (e.getFrom() == tempVertex) {
				totalweight += e.getWeight();
			} else if (e.getTo() == tempVertex) {
				totalweight += e.getWeight();
			}
		}
		System.out.println("Driving from " + lastVertex + " to " + tempVertex);// delivering the message of the next move.
		if (tempVertex.getDeadline() - totalweight <= 0) {	// checking if the vertex is to be destroyed.
			tempVertex.setDestroyed(true);		//destroyes the vertex if the deadline is < 0.
			this.isAlive = false;				//destroyes the vehicle too, since it's the vertex he's present at.
		}
		System.out.println("=================================================");
		updateScore(tempVertex);				//updating the score if there's a shelter and checks if the agent won the game.

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
			if(this.peopleamount > 0) {
				System.out.println("You saved " + this.peopleamount + " more people, your SCORE is now: " + this.score);
			}
			this.peopleamount = 0;
			if (this.score == totalPeopleAmount) {	//checks if the goal has been reached, comparing the amount of people saved to the population in the game.
				gameOn = false;
			}
		}
	}
	public void calcNextMove() {
		for(Edge edge : tempVertex.getNextroads()) { // checking every possible next move.
			length += edge.getWeight();				// adding to the length, later setting it back in order to still not remembering it, because it might not be the chosen road.
			if(tempVertex == edge.getFrom()) {
				edge.getTo().setTempHeuristic(length + edge.getTo().getHeuristic()); //setting the temp heuristic, regarding the current heuristic and the total weight taken so far.
				nexts.add(edge.getTo()); // adding it to the next available list.
			}
			else {
				edge.getFrom().setTempHeuristic(length + edge.getFrom().getHeuristic());
				nexts.add(edge.getFrom());
			}
			length -= edge.getWeight(); // reseting the length's value.
		}
		lastVertex = tempVertex;
		for(Vertex vertex : nexts) { // checking which of the vertices has the minimum heuristic value.
			if( vertex.getTempHeuristic() < minHeuristic ) {
				minHeuristic = vertex.getTempHeuristic(); //the new minimum is the weight of the next minimum measured.
				tempVertex = vertex; // moving to it.
			}
		}
		
		for(Edge e : lastVertex.getNextroads()) { // Adding to the total length taken.
			if(e.getFrom() == tempVertex) {
				length += e.getWeight();
//				tempweight = e.getWeight();
			}
			else if(e.getTo() == tempVertex) {
				length += e.getWeight();
//				tempweight = e.getWeight();
			}
		
		for(Vertex vertex : nexts) {  // Adding the heuristic according to the people taken from it and the deadline that was already taken, which makes it less attractive to the algorithm.
			if(vertex == tempVertex) {
				vertex.setHeuristic(vertex.getHeuristic() + vertex.getPeople() + length); 
			}
		}
		
		}
		minHeuristic = Integer.MAX_VALUE; // reseting the nexts and the minimum heuristic value to be checked on the next cycle.
		nexts.clear();
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

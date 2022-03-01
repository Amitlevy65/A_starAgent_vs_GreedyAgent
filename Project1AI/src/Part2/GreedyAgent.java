package Part2;

import java.util.ArrayList;

/**
 * 
 * @author Amit Levy 313268773
 * @author Roie Tzemach 208670018
 */

public class GreedyAgent extends Vehicle implements Runnable {


	Vertex tempVertex = this.start;				//the vertex that will be changed according to the chosen route.
	Vertex lastVertex = tempVertex; 
	int minHeuristic = Integer.MAX_VALUE;		// minimum heuristic value to be remembered.
	int tempweight;								// the temporary weight taken.
	int totalweight = 0;						// the total weight taken.
	ArrayList<Vertex> nexts = new ArrayList<Vertex>(); // a list of the next available vertices.
	
	public GreedyAgent(int n, int totalpeopleamount, Vertex start) {
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
		if (tempVertex.getDeadline() - totalweight < 0) {	// checking if the vertex is to be destroyed. 
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
				currentvertex.setHeuristic(currentvertex.getHeuristic() + this.peopleamount);
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
		for(Edge edge : tempVertex.getNextroads()) { // checks every road connected to it.
			if(tempVertex == edge.getFrom()) {
				edge.getTo().setTempHeuristic(edge.getWeight() + edge.getTo().getHeuristic()); //setting the temp heuristic according to the weight measured.
				nexts.add(edge.getTo()); // adds the vertex to the next available vertices list.
			}
			else {
				edge.getFrom().setTempHeuristic(edge.getWeight() + edge.getFrom().getHeuristic());
				nexts.add(edge.getFrom());
			}
		}
		lastVertex = tempVertex; 
		for(Vertex vertex : nexts) {
			if( vertex.getTempHeuristic() < minHeuristic ) { // moving to the next most efficient vertex according to the algorithm.
				minHeuristic = vertex.getTempHeuristic();
				tempVertex = vertex;
			}
		}
		for (Edge edge : lastVertex.getNextroads()) {
			if(edge.getTo() == tempVertex) {
				tempweight = edge.getWeight();
			}
			if(edge.getFrom() == tempVertex) {
				tempweight = edge.getWeight();
			}
		}
		for(Vertex vertex : nexts) { // setting the heuristic according to the peopel taken from it and the weight currently measured.
			if(vertex == tempVertex) {
				vertex.setHeuristic(vertex.getHeuristic() + vertex.getPeople() + tempweight);
			}
		}
		minHeuristic = Integer.MAX_VALUE; // reseting the minimum value and the nexts list.
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

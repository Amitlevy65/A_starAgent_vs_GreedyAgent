package Part2;

import java.util.ArrayList;

/**
 * 
 * @author Amit Levy 313268773
 * @author Roie Tzemach 208670018
 */

public class Simulator {

	Vertex start;

	int n;
	int totalPeopleAmount;
	int sheltersamount; // the amount of shelters in the game.
	ArrayList<Vertex> vertices; // all the vertices in the game.
	ArrayList<Edge> roads; // all the edges in the game.
	Vehicle vehicle;
	Thread agent;
	static boolean builtRoads = true;

	public Simulator(int n, int peopleAmount, int sheltersamount, ArrayList<Vertex> vertices, ArrayList<Edge> roads,
			Vehicle vehicle) {
		this.n = n;
		this.totalPeopleAmount = peopleAmount;
		this.sheltersamount = sheltersamount;
		this.vertices = vertices;
		this.roads = roads;
		this.vehicle = vehicle;
		if(builtRoads == true) {
			buildRoads(); // connects betweein the sides of the edges, making the edge passable from both sides.
			builtRoads = false; //Makes sure there's no copying the making of the roads.
		}			
		
		for (Vertex v : this.vertices) {
			v.setNextvertices(buildNextVertices(v)); // for every vetrtex, creates a list of the vertices he's directly
														// connected to.
			v.setHeuristic(100 - v.getPeople() - v.getDeadline()); // Setting the heuristics for every vertex in the graph.
		}
		
		
	}

	public void buildRoads() {
		for (Edge e : this.roads) {
			e.from.getNextroads().add(e); // adds one side of the edge to the other side's roads.
			e.to.getNextroads().add(e);
		}
	}

	public ArrayList<Vertex> buildNextVertices(Vertex v) {
		ArrayList<Vertex> nextvertices = new ArrayList<Vertex>();
		for (Vertex vertex : this.vertices) { // checks for every vertex in the list.
			for (Edge e : vertex.getNextroads()) { // checks every edge in the current searched vertex's connected
													// roads.
				if (vertex == e.getTo()) { // if the vertex is one side, it adds the other.
					nextvertices.add(e.getFrom());
				} else if (vertex == e.getFrom()) {
					nextvertices.add(e.getTo()); // the opposite.
				}
			}
		}
		return nextvertices;
	}
	
	public Vertex getStart() {
		return start;
	}

	public void setStart(Vertex start) {
		this.start = start;
	}

	public int getPeopleAmount() {
		return totalPeopleAmount;
	}

	public void setPeopleAmount(int peopleAmount) {
		this.totalPeopleAmount = peopleAmount;
	}

	public int getN() {
		return n;
	}

	public ArrayList<Vertex> getVertices() {
		return vertices;
	}

	public ArrayList<Edge> getRoads() {
		return roads;
	}

}

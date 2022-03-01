package Project1AI;

public class Edge {
	/**
	 * 
	 * @author Amit Levy 313268773
	 * @author Roie Tzemach 208670018
	 */

	int weight;
	Vertex from, to; // The vertices on each side of the edge.

	public Edge(int weight, Vertex from, Vertex to) {
		this.weight = weight;
		this.from = from;
		this.to = to;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public Vertex getFrom() {
		return from;
	}

	public void setFrom(Vertex from) {
		this.from = from;
	}

	public Vertex getTo() {
		return to;
	}

	public void setTo(Vertex to) {
		this.to = to;
	}
}

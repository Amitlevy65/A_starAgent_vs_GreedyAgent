package Project1AI;

import java.util.ArrayList;

/**
 * 
 * @author Amit Levy 313268773
 * @author Roie Tzemach 208670018
 */

public class Vertex {
	
	int n;
	int id;
	int people;
	int deadline;
	boolean shelterexists = false;			
	boolean destroyed;
	ArrayList<Edge> nextroads;
	ArrayList<Integer> nextindexes;
	ArrayList<Vertex> nextvertices;
	

	public Vertex(int id,int n,int people,int deadline,boolean shelterexists) {
		this.n = n;
		this.id = id;
		this.people = people;
		this.deadline = deadline;
		this.shelterexists = shelterexists;
		this.destroyed = false;
		this.nextroads = new ArrayList<Edge>();
		this.nextindexes = new ArrayList<Integer>();
		this.nextvertices = new ArrayList<Vertex>();
	}

	public int getPeople() {
		return people;
	}

	public void setPeople(int people) {
		this.people = people;
	}

	public int getDeadline() {
		return deadline;
	}

	public void setDeadline(int deadline) {
		this.deadline = deadline;
	}

	public int getId() {
		return id;
	}
	
	public boolean isDestroyed() {
		return this.destroyed;
	}

	public void setDestroyed(boolean destroyed) {
		this.destroyed = destroyed;
	}
	
	public ArrayList<Edge> getNextroads() {
		return nextroads;
	}
	
	public boolean isShelterexists() {
		return shelterexists;
	}

	public void setShelterexists(boolean shelterexists) {
		this.shelterexists = shelterexists;
	}
	
	public ArrayList<Integer> getNextIndexes() {
		return nextindexes;
	}
	
	public ArrayList<Vertex> getNextvertices() {
		return nextvertices;
	}
	
	public void setNextvertices(ArrayList<Vertex> nextvertices) {
		this.nextvertices = nextvertices;
	}
	
	@Override
	public String toString() {
		return "Vertex" +id ;
	}
	
	

}

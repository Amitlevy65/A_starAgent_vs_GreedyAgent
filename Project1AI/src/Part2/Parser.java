package Part2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * 
 * @author Amit Levy 313268773
 * @author Roie Tzemach 208670018
 */

public class Parser {

	private int verticesAmount;
	private int peopleAmount = 0;
	private int shelterAmount = 0;

	private ArrayList<Vertex> vertices = new ArrayList<Vertex>();

	private ArrayList<Edge> edges = new ArrayList<Edge>();

	public void parse(String filename) {
		File file = new File(filename);

		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;

			while ((line = br.readLine()) != null) {
				if (line.charAt(0) == '#') {
					switch (line.charAt(1)) {
					case 'N': {
						totalVertexParser(line.substring(3));
						break;
					}
					case 'E': {
						edgeParser(line.substring(2));
						break;
					}
					case 'V': {
						vertexParser(line.substring(2));
						break;
					}
					default:
						System.out.println("Bad file!");
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void totalVertexParser(String input) {
		String[] parsedTotalVertex = input.split(" ");
		String temp = parsedTotalVertex[0];
		this.setVerticesAmount(Integer.parseInt(temp));
	}

	public void edgeParser(String input) {
		String[] getedge = input.split(" ");
		int from = Integer.parseInt(getedge[1]);
		int to = Integer.parseInt(getedge[2]);
		int weight = Integer.parseInt(getedge[3].substring(1));
		this.edges.add(new Edge(weight,this.vertices.get(from-1), this.vertices.get(to-1)));
	}

	public void vertexParser(String input) {
		String[] getvertex = input.split(" ");
		int vertexIndex = Integer.parseInt(getvertex[0].substring(0));
		this.vertices.add(new Vertex(vertexIndex, this.verticesAmount, 0, 0, false));
		if (getvertex[1].charAt(0) == 'D') {
			this.vertices.get(vertexIndex-1).setDeadline(Integer.parseInt(getvertex[1].substring(1)));
		} else {
			System.out.println("Bad File.");
			System.out.println(input);
		}

		if (getvertex[2].charAt(0) == 'S') {
			this.vertices.get(vertexIndex-1).setShelterexists(true);
			this.shelterAmount += 1;
		} else if (getvertex[2].charAt(0) == 'P') {
			this.vertices.get(vertexIndex-1).setPeople(Integer.parseInt(getvertex[2].substring(1)));
			this.peopleAmount += Integer.parseInt(getvertex[2].substring(1));
		} else {
			System.out.println("Bad File.");
			System.out.println(input);
		}
	}

	public int getVerticesAmount() {
		return verticesAmount;
	}

	public void setVerticesAmount(int verticesAmount) {
		this.verticesAmount = verticesAmount;
	}

	public int getPeopleAmount() {
		return peopleAmount;
	}

	public void setPeopleAmount(int peopleAmount) {
		this.peopleAmount = peopleAmount;
	}

	public ArrayList<Vertex> getVertices() {
		return vertices;
	}

	public ArrayList<Edge> getEdges() {
		return edges;
	}

	public int getShelterAmount() {
		return shelterAmount;
	}
}

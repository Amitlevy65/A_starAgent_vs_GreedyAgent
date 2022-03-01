package Part2;

import java.util.Scanner;

/**
 * 
 * @author Amit Levy 313268773
 * @author Roie Tzemach 208670018
 */

public class Main {

	public static void main(String[] args) {

		Parser parser = new Parser();
		parser.parse("text.txt");

		aStarAgent a_star_agent = new aStarAgent(parser.getVerticesAmount(), parser.getPeopleAmount(),
				parser.getVertices().get(0));
		GreedyAgent greedyAgent = new GreedyAgent(parser.getVerticesAmount(), parser.getPeopleAmount(),
				parser.getVertices().get(0));

		Simulator greedyAgentGame = new Simulator(parser.getVerticesAmount(), parser.getPeopleAmount(),
				parser.getShelterAmount(), parser.getVertices(), parser.getEdges(), greedyAgent);

		Simulator aStarAgentGame = new Simulator(parser.getVerticesAmount(), parser.getPeopleAmount(),
				parser.getShelterAmount(), parser.getVertices(), parser.getEdges(), a_star_agent);

		Thread t1 = new Thread(a_star_agent);
		Thread t2 = new Thread(greedyAgent);
		Scanner sc = new Scanner(System.in);

		int input;
		do {
			System.out.println("Please choose a number: ");
			System.out.println("1 for A* Agent ");
			System.out.println("2 for Greedy Agent ");
			input = sc.nextInt();
		} while (input != 1 && input != 2);
		if (input == 1) {
			t1.start();
		} else {
			t2.start();
		}
		
	}
}



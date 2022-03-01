package Project1AI;

import java.util.ArrayList;
import java.util.Random;

public class Hurricane implements Runnable{

	// Continue with the answered questions by Amnon
	int n;
	int total;
	int gonext; 
	Vertex whereAt,start,next;
	ArrayList<Integer> visited = new ArrayList<Integer>();
	int destroyed = 0;
	
	public Hurricane(int n) {
		this.n = n;
		this.total = n;
	}

	public void run() {
//		start = Simulator.getStart();
		gonext = start.getDeadline();
		next = start;
		do {
			try {
				
				Thread.sleep(gonext * 1000);			
			} catch (Exception e) {
				e.getMessage();
			}
			
			whereAt = next;
			
			if(whereAt.shelterexists == false) {
				whereAt.setPeople(0);
				whereAt.setDestroyed(true);
				destroyed ++;
			}
			
			int temp = chooseRandomly(whereAt);
			
			next = whereAt.getNextroads().get(temp).getTo();
			
		} while (destroyed < total);
	}

	public int chooseRandomly(Vertex vertex) {
		Random rn = new Random();
		int next = rn.nextInt(vertex.getNextroads().size())+1;
		while(vertex.getNextroads().get(vertex.getNextroads().size()).getTo().isDestroyed() == true) {
			next = rn.nextInt(vertex.getNextroads().size())+1;
		}
		return next;
	}
	
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
}

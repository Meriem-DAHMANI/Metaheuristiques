package work;

import java.util.ArrayList;

public class ResultGA {
	private int numberGenerations;
	private int bestFitness;
	private ArrayList <String> gbest;
	
	//constructor
	public ResultGA(int numberGenerations, int bestFitness, ArrayList<String> gbest) {
		super();
		this.numberGenerations = numberGenerations;
		this.bestFitness = bestFitness;
		this.gbest = gbest;
	}
	
	public void display() {
        System.out.println("number of created generations is : "+this.numberGenerations);
		System.out.println(" best sequence of movements is: "+this.gbest);
        System.out.println("best fitness of this solution is : "+this.bestFitness);
	}
	
	
	

}

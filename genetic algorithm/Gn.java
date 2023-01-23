package work;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Gn {
	private Node startState, goalState;
    //constructor
	public Gn(ArrayList <Integer> start, ArrayList <Integer> goal) {
		this.startState = new Node(start, null,0, "start");
		this.goalState = new Node(goal, null,0, "end");
	}
	
	public Gn() {
		
	}
	
	//methods
	
	//la méthode init permet de construire la population initiale
	public ArrayList<ArrayList<Integer>> init(int N, int chromosomeSize) {
		ArrayList<ArrayList<Integer>> generation = new ArrayList <ArrayList<Integer>>();
		ArrayList <Integer> givenList = new ArrayList <Integer>(Arrays.asList(1, -1, 3, -3));
		
		for(int i=0; i<N; i++) {
			ArrayList <Integer> randomChromosome = new ArrayList <Integer>();
			Random rand = new Random();
			for (int j=0; j<chromosomeSize; j++) {
				int randomElement = givenList.get(rand.nextInt(givenList.size()));
				randomChromosome.add(randomElement);
			}
		   // generation.add(valid(randomChromosome));
			generation.add(randomChromosome);
		}
		return generation;
	}
	
	
	//la méthode fitness permet d’évaluer chaque chromosome et rend une liste qui contient les fitness de tous les individus
	public ArrayList<Integer> fitness(ArrayList<ArrayList<Integer>> generation) {
		ArrayList <Integer> fitnessList = new ArrayList <Integer>();
		ArrayList <Integer> goalStateList = this.goalState.getStateList();
		int fitnessChromosome;
		for (ArrayList<Integer> chromosome : generation) {
			ArrayList<Integer> chromosomeNode = this.getNode(chromosome).getStateList();
			fitnessChromosome = 0;
			for(int i = 0; i< chromosomeNode.size(); i++) {
				if(chromosomeNode.get(i) == goalStateList.get(i)) {
					fitnessChromosome++;
				}
			}
			fitnessList.add(fitnessChromosome);
		}
		
		return fitnessList;
	}
	
	
	//cette méthode va faire les mouvements possibles du chromosome
	public Node getNode(ArrayList <Integer> chromosome) {
		   Node currentState = this.startState;
		   Step p = new Step();
		   Node newState = new Node();
		   for(int move : chromosome) {
			   
			   if (move == -3) {
				   newState = p.moveUp(currentState);
				   if (newState != null) currentState = newState;
			   }
			   
			   if (move == 3) {
				   newState = p.moveDown(currentState);
				   if (newState != null) currentState = newState;
			   }
			   
			   if (move == -1) {
				   newState = p.moveLeft(currentState);
				   if (newState != null) currentState = newState;
			   }
			   
			   if (move == 1) {
				   newState = p.moveRight(currentState);
				   if (newState != null) currentState = newState;
			   } 
			   
		   }
		   return currentState;
	   }
	
	
	//la méthode select permet de trier la génération selon la fitness
	public ArrayList<ArrayList<Integer>> select(ArrayList <Integer> fitnessList, ArrayList<ArrayList<Integer>> generation){
		ArrayList<ArrayList<Integer>> sortedGeneration = new ArrayList<ArrayList<Integer>>();
		ArrayList <Integer> fitnessSorted = new ArrayList <Integer>();
		for(int i : fitnessList) {
			fitnessSorted.add(i);
		}
		Collections.sort(fitnessSorted, Collections.reverseOrder());
		int posMax = 0;
		for(int i : fitnessSorted) {
			posMax = fitnessList.indexOf(i);
			sortedGeneration.add(generation.get(posMax));
			fitnessList.set(posMax, 0);
		}
		return sortedGeneration;
	}
	
	//créer une paire d’enfants de la nouvelle génération en faisant du croisement selon pc
	public ArrayList<ArrayList<Integer>> croisement(ArrayList <Integer> parent1, ArrayList <Integer> parent2, double pc) {
		
		Random rand = new Random();
		int pointCroisement = 0;
		if(pc > rand.nextFloat(0, 1)) {
			pointCroisement = rand.nextInt(parent1.size());
			
			ArrayList <Integer> child1 = new ArrayList <Integer>();
			child1.addAll(parent1.subList(0, pointCroisement));
			child1.addAll(parent2.subList(pointCroisement, parent2.size()));
			
			ArrayList <Integer> child2 = new ArrayList <Integer>();
			child2.addAll(parent2.subList(0, pointCroisement));
			child2.addAll(parent1.subList(pointCroisement, parent1.size()));
			
			return new ArrayList<>(Arrays.asList(child1, child2));
		}
		return new ArrayList<>(Arrays.asList(parent1, parent2));
	}
	
	
	//appliquer la mutation sur chaque individu de la nouvelle génération selon pm
	public ArrayList <Integer> mutation(ArrayList <Integer> child, double pm){
		Random rand = new Random();
		for (int i = 0; i<child.size(); i++) {
			if(pm > rand.nextFloat(0, 1)) child.set(i, -1*child.get(i));
		}
		return child;
	}
	
	//cette méthode va vérifier la validité du chromosome donné en paramètre et supprimer les mouvemets non valides
	public ArrayList <Integer> getValid(ArrayList <Integer> child) {
		
		   ArrayList <Integer> validChild = new ArrayList <Integer>(); 
		   Node currentState = this.startState;
		   Step p = new Step();
		   Node newState = new Node();
	       int j = 0;
		   while(j < child.size()){
			   if (child.get(j) == -3) {
				   newState = p.moveUp(currentState);
				   if(newState != null) {
					   validChild.add(-3);
					   currentState = newState;
				   }
			   }
			   
			   if (child.get(j) == 3) {
				   newState = p.moveDown(currentState);
				   if(newState != null) {
					   validChild.add(3);
					   currentState = newState;
				   }
			   }
			   
			   if (child.get(j) == -1) {
				   newState = p.moveLeft(currentState);
				   if(newState != null) {
					   validChild.add(-1);
					   currentState = newState;
				   }
			   }
			   
			   if (child.get(j) == 1) {
				   newState = p.moveRight(currentState);
				   if(newState != null) {
					   validChild.add(1);
					   currentState = newState;
				   }
			   } 
			   j++;
		   }
		return validChild;
	}
	
	
	//cette méthode permet d'optimiser la solution finale
	public ArrayList <Integer> optimization (ArrayList <Integer> child) {
		   ArrayList <Integer> optimizedChild = new ArrayList <Integer>(); 
		   int j = 0;
		   while(j< child.size()-1) {
			   if(child.get(j) == -1 * child.get(j+1)) j+= 2;
			   else {
				   optimizedChild.add(child.get(j));
				   j+=1;
			   }
		   }
		   //traitement du dernier élément si la longueur de la liste est impaire
		   if(j<child.size()) optimizedChild.add(child.get(j));
		   return optimizedChild;   
		   }
	
	//l'algorithme principal
	public ResultGA application(int N, int chromosomeSize, double pc, double pm, int maxIter) {
		int iter = 0;
		
		//définir les variables qu'on va utiliser dans pp
		ArrayList<Integer> fitnessList = new ArrayList <Integer>();
		ArrayList<ArrayList<Integer>> selected = new ArrayList<ArrayList<Integer>> ();
		ArrayList<ArrayList<Integer>> cc = new ArrayList<ArrayList<Integer>> ();
		ArrayList<Integer> child = new ArrayList<Integer> ();
		ArrayList<ArrayList<Integer>> bestGeneration = new ArrayList<ArrayList<Integer>>(); ;
		
		//pp
		//construire la population initiale
		ArrayList<ArrayList<Integer>> currentGeneration = this.init(N, chromosomeSize);
		int currentFitness = 0;
		int bestFitness = 0;
		
		while(bestFitness < 9 && iter<maxIter) {
		    System.out.println("iter = "+iter);
			int i = 0;
			int j = 0;
			
			//calculer la fitness de la nouvelle génération afin de tester le test d'arret
			fitnessList = this.fitness(currentGeneration);
			currentFitness = Collections.max(fitnessList);
			selected = this.select(fitnessList, currentGeneration);
			System.out.println("     current fitness = " + currentFitness);
			System.out.println("     selected generation = " + selected);
			if(currentFitness>bestFitness) {
				bestGeneration = new ArrayList<ArrayList<Integer>>();
				bestFitness = currentFitness;
				for (int z = 0 ; z<selected.size();z++) {
					ArrayList <Integer> a = new ArrayList<Integer>();
					ArrayList <Integer> b = selected.get(z);
					for(int k = 0; k<b.size(); k++) {
						a.add(b.get(k));
					}
					bestGeneration.add(a);
				}
			}
			if(bestFitness == 9) {
				break;
			}

			//sinon on constitue une nouvelle génération
			ArrayList<ArrayList<Integer>> newGeneration = new ArrayList<ArrayList<Integer>>();
			while(newGeneration.size()<N && i<selected.size() && j<selected.size()) {
				cc = this.croisement(selected.get(i), selected.get(j), pc);
				ArrayList <Integer> CCC = new ArrayList <Integer>(); 
				ArrayList <Integer> z = cc.get(0);
				for(int m = 0; m<z.size();m++) {
					CCC.add(z.get(m));
				}
				child = this.mutation(CCC, pm);
				newGeneration.add(child);
				j ++;
				if(j == chromosomeSize - 1) {
					i++;
					j=0;
				}
			}
			currentGeneration = newGeneration;
			iter ++;
		}
		
		//récupérer la meilleur solution de la dernière génération
		System.out.println("best generation = "+bestGeneration);
		ArrayList <String> movementsList = this.bestSolution(bestGeneration);
		
		//retourner le résultat final
		 return new ResultGA(iter, bestFitness, movementsList);
	}

	//cette méthode permet de retourner la meilleur séquence de mouvements qui représente la solution
	public ArrayList <String> bestSolution(ArrayList<ArrayList<Integer>> currentGeneration){
		//ne garder que les mouvements valides de la solution
		ArrayList <Integer> gbest = this.getValid(currentGeneration.get(0));
		//supprimer les mouvements séquentiels opposés
		gbest = this.optimization(gbest);
		ArrayList <String> movementsList = new ArrayList <String>();
		for(int i = 0; i<gbest.size(); i++) {
			switch(gbest.get(i)) {
			case -3 : movementsList.add("UP"); break;
			case  3 : movementsList.add("DOWN"); break;
			case -1 : movementsList.add("LEFT"); break;
			case  1 : movementsList.add("RIGHT"); break;
			}
		}
		return movementsList;
	}
	
	//main just for test
	public static void main(String[] args) {
		/*ArrayList <Integer> startState = new ArrayList<Integer>(Arrays.asList(1, 2, 3, 8, 4, 7, 0, 6, 5));
		ArrayList <Integer> goalState = new ArrayList <Integer>(Arrays.asList(1, 2, 3, 8, 0, 4, 7, 6, 5));*/
		/*ArrayList <Integer> startState = new ArrayList<Integer>(Arrays.asList(6, 2, 5, 1, 8, 0, 7, 4, 3));
		ArrayList <Integer> goalState = new ArrayList <Integer>(Arrays.asList(6, 0, 2, 1, 8, 5, 7, 4, 3));*/
		//ArrayList <Integer> goalState = new ArrayList <Integer>(Arrays.asList(6, 2, 5, 1, 8, 3, 7, 4, 0));
		ArrayList <Integer> startState = new ArrayList<Integer>(Arrays.asList(2, 8, 3, 1, 6, 4, 7, 0, 5));
		ArrayList <Integer> goalState = new ArrayList <Integer>(Arrays.asList(1, 2, 3, 8, 0, 4, 7, 6, 5));
		Gn algo = new Gn(startState, goalState);
		
		//initialisation des variables principales
		int N = 5; //number of chromosomes in each generation
		int chromosomeSize = 12;
		double pc = 0.3;
		double pm = 0.3;
		int maxIter = 100;
		//appeler la fonction principale de l'algorithme génétique avec les paramètres définis
		ResultGA r = algo.application(N, chromosomeSize, pc, pm, maxIter); 
		r.display();
		
	}	
	
}

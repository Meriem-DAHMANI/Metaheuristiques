package com.PSO;

import com.AStar.heuristic.EuclideanDistanceHeuristic;
import com.AStar.heuristic.ManhattanDistanceHeuristic;
import com.AStar.heuristic.MisplacedTilesHeuristic;
import com.Result;
import java.util.*;

public class AlgoPSO extends Util {

    private ArrayList<Integer> initialBoard;
    private ArrayList<Particle> particles;
    private com.AStar.algorithm.Heuristic heuristic;
    private PriorityQueue<Particle> open;
    private double c1;
    private double c2;
    private double w;
    private Integer numberOfParticles;
    private Integer numberOfIteration;
    private String mode;
    private Stack<String> path;
    private Result result;


    public AlgoPSO(ArrayList<Integer> initialBoard,
                   ArrayList<Integer> goalBoard,
                   int heuristicMode,
                   double c1,
                   double c2,
                   double w,
                   Integer numberOfParticles,
                   Integer numberOfIteration){
        this.initialBoard=initialBoard;
        this.target = goalBoard;
        this.open = new PriorityQueue<>();
        this.particles = new ArrayList<>();
        this.path=new Stack<>();
        this.c1 = c1;
        this.c2 = c2;
        this.w=w;
        this.numberOfIteration = numberOfIteration;
        this.numberOfParticles = numberOfParticles;
        defineMode(heuristicMode);
    }

    /**
     * <p>Sets the heuristic to Manhattan, misplaced tiles or euclidean distance.
     * Updates a String to be used in informing the user of
     * which heuristic is under use. <br />
     * Default is Manhattan Distance.
     * </p>
     * @param mode
     */
    private void defineMode(int mode) {
        switch (mode) {
            case (1):
                heuristic = new ManhattanDistanceHeuristic();
                this.mode="Manhattan Distance Heuristic";
                break;
            case (2):
                heuristic = new MisplacedTilesHeuristic();
                this.mode="Misplaced Tiles Heuristic ";
                break;
            case (3):
                heuristic = new EuclideanDistanceHeuristic();
                this.mode="Euclidean Distance Heuristic";
                break;
            default:
                heuristic = new ManhattanDistanceHeuristic();
        }
    }

    /**
     * This method will create random particles,
     * it will update all particles till it'll find a solution
     * @return the method will return the path containing all moves of the bestParticle(the last leader)
     */
    public Result PSO(){
        
        // create a defined number of particles
        State initialState = new State(initialBoard, null, null);
        int k=0;
        while(k<this.numberOfParticles){
            Particle p = createInitialParticles(initialState, target, 1);
            open.add(p);
            particles.add(p);
            k++;
        }

        // update particles till the goal or number of iteration is reached
        int i=0;
        while(i<this.numberOfIteration) {

            Particle bestParticle = open.peek();
            State s = bestParticle.getFinalState();

            // if fitness ==0 the goal is reached, we'll look for all previous moves
            if(bestParticle.getFitness()==0){
                path.add(s.getNextMove());
                while(s.getPredecessor()!=null){
                    s=s.getPredecessor();
                    path.add(s.getNextMove());
                }
                result = new Result(path);
                
                System.out.println(path);
                System.out.println("Yeahhh u made it");

                return  result;
            }
            
            // the program will clear open and will add the updated particles
            open.clear();
            double gBest = bestParticle.getPBest();
            int j=0;
            while(j<this.numberOfParticles){
                open.add(updateParticles(gBest, particles.get(j)));
                j++;
            }
            i++;
        }
        return null;
    }

    /**
     * Create first particles
     * @param initialState
     * @param target
     * @param velocity
     * @return
     */
    @Override
    public Particle createInitialParticles(State initialState,
                                           ArrayList<Integer> target,
                                           double velocity){
        // create random steps for the first particles
        ArrayList<Integer> randomPosition = returnRandomSolutions();
        // find the final state for each particle
        State s = finalChild(initialState, randomPosition);
        // calculate the fitness value of the final state
        double fitness  = heuristic.getHeuristic(s.getBoard(), target);

        return new Particle(s,positionValue(randomPosition),fitness, velocity);
    }

    /**
     * update all particles
     * @param gBest
     * @param p
     * @return
     */

    public Particle updateParticles(double gBest, Particle p){
        p.update(gBest, this.c1, this.c2, this.w);
        // create new moves with new position value
        ArrayList<Integer> positions = positionValueInverse(p.getPosition());
        // update the final state with the new moves steps
        p.setFinalState(finalChild(p.getFinalState(), positions));
        // calculate the new fitness
        p.setFitness(heuristic.getHeuristic(p.getFinalState().getBoard(), target));

        return  p;
    }

}


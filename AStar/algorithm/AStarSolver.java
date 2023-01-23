package com.AStar.algorithm;

import com.AStar.heuristic.EuclideanDistanceHeuristic;
import com.AStar.heuristic.ManhattanDistanceHeuristic;
import com.AStar.heuristic.MisplacedTilesHeuristic;
import com.Result;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * @author : beline
 * @description : Implementation of A* algorithm to solve 8-puzzle
 *
 * <p>
 * Uses a priority queue to ensure the least costing state is always at the front of
 * the frontier to be expanded upon.
 * </p>
 *
 */
public class AStarSolver extends Util {

    private ArrayList<Integer> initialBoard;
    private Heuristic heuristic;
    private PriorityQueue<State> open;
    private Set<State>  closed;
    private Stack<String> path;
    private String mode;
    private Result result;
    private int shortestPath=0;
    private int numberOfExploredNodes=1;
    private int  numberOfDevelopedNodes=1;
    /**
     * <p>
     * New A* Search creates new lists and sets the goal.
     * Also determines what 'heuristic mode' the A* Search should use.
     * </p>
     *
     * @param initialBoard
     * @param goal
     */
    public AStarSolver(ArrayList<Integer> initialBoard, ArrayList<Integer> goal, int heuristicMode) {
        this.initialBoard=initialBoard;
        this.target = goal;
        open = new PriorityQueue<>();
        closed = new HashSet<>(181440);
        path = new Stack<>();
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
     * Astar() function initialize the fist state and add it to open, it'll take the minimum  value in open
     * and will compare it to the goal, if the path exist the function will return the result containing
     * all moves to reach the goal, number of all nodes explored, all nodes developed, and the shortest path to
     * the goal
     * @return Result
     */
    public  Result AStar() throws IOException {
        FileWriter myWriter = new FileWriter("tests.txt");
        boolean solutionFound = false;
        int minimumRemainingCostToTarget= heuristic.getHeuristic(initialBoard, target);
        State source = new State( initialBoard,null,0, minimumRemainingCostToTarget);
        open.add(source);
        int j=0;

        while(!open.isEmpty()){
            State currentState = open.peek();
            if (currentState.getBoard().equals(target)) {
                path.add(currentState.getNextMove());

                /*
                 * on utilise les etats précedents pour le chemin le plus court, on utilise un pile, pour avoir
                 * le bon ordre à la sortie des éléments
                 */
                while(currentState.getPredecessor()!=null){
                    currentState=currentState.getPredecessor();
                    path.add(currentState.getNextMove());
                    shortestPath++;
                }
                result = new Result(path, numberOfExploredNodes,numberOfDevelopedNodes,shortestPath);
                System.out.println(result.display());
                myWriter.write("/* "+result.display()+"*/");

                return result;
            }

            /*
            * remove the state with the minimum cosSum and put it in closed list
             */
            open.remove(currentState);
            closed.add(currentState);
            numberOfDevelopedNodes++;
            /*
             * Search through possible steps (Up, left, right, down) of empty
             * tiles and find the current state's children to be explored next
             */
            this.findChildren(currentState);
        }
        /*
         * If by the end of this while loop, no solution has been found, inform
         * the user
         */
        if (!solutionFound) {
            System.out.println("No solution for this puzzle");
        }
        return  null;
    }

    @Override
    public void movement(int zeroIndex, int direction, State current) {
        if(rulesForMovement(zeroIndex, direction)){
            //Create a new child based on the current state
            State child = createChild(current, zeroIndex, direction, heuristic, target);
            /*
             * If the child state is not in the open or closed list, we haven't
             * explored it at all yet, so add to the open list.
             */
            if(!inClosedList(child) && !inOpenList(child)){
                open.add(child);
                numberOfExploredNodes++;
            }else{
                State checker = inList(child, open);
                /*
                 * If it is in the open list, check to see if it's cost is
                 * better now than it was in the previous open list.
                 */
                if (checker != null) {
                    if (child.getCostSum() < checker.getCostSum()) {
                        removeFromOpen(checker);
                        addToOpen(child);
                    } else {
                        /*
                         * Else check if it's in the closed list, and if it's
                         * state was better, then we 'update' the closed list
                         * with the new child.
                         */
                        checker = inList(child, closed);
                        if (checker != null) {
                            if (child.getCostSum() < checker.getCostSum()) {
                                removeFromClosed(checker);
                                addToClosed(child);
                            }
                        }
                    }
                }
            }
        }
    }
    public void addToOpen(State state) {open.add(state);}
    public boolean inOpenList(State state) {return open.contains(state);}
    public void removeFromOpen(State state) {open.remove(state);}
    public void addToClosed(State state) {closed.add(state);}
    public boolean inClosedList(State state) {return closed.contains(state);}
    public void removeFromClosed(State state) {closed.remove(state);}
}
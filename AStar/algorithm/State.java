package com.AStar.algorithm;
import java.util.ArrayList;

/**
 * <p>The state class, used to represent each state and their
 * children/successors. Implements comparable to be used for determining
 * heuristic values with a priority queue in A* Search.
 * </p>
 * @author beline
 *
 */

public class State<N extends Comparable<N>> implements Comparable<State<N>> {

    private  ArrayList<Integer> board;
    private Integer intBoard;
    private State<N> predecessor;
    private double totalCostFromStart; //g(x)
    private double minimumRemainingCostToTarget; //h(x)
    private double costSum; //f(x)
    private String nextMove;
    public State(ArrayList<Integer> board,
                 State<N> predecessor,
                 double minimumRemainingCostToTarget,
                 String nextMove) {
        this.board = board;
        this.predecessor = predecessor;
        this.totalCostFromStart = predecessor == null ? 0 : predecessor.totalCostFromStart + 1;
        this.minimumRemainingCostToTarget = minimumRemainingCostToTarget;
        this.nextMove=nextMove;
        calculateCostSum();
        this.intFromList();
    }

    public State(ArrayList<Integer> board,
                 State<N> predecessor,
                 double totalCostFromStart,
                 double minimumRemainingCostToTarget) {
        this.board = board;
        this.predecessor = predecessor;
        this.totalCostFromStart = totalCostFromStart;
        this.minimumRemainingCostToTarget = minimumRemainingCostToTarget;
        calculateCostSum();
        this.intFromList();
    }
    private void calculateCostSum() {
        this.costSum = this.totalCostFromStart + this.minimumRemainingCostToTarget;
    }

    public double getCostSum() {return costSum;}
    public ArrayList<Integer> getBoard() {return board;}
    public String getNextMove(){return nextMove;}
    public State<N> getPredecessor() {return predecessor;}
    public Integer getIntBoard() {return intBoard;}


    @Override
    public int compareTo(State<N> nNode) {
        int compare = Double.compare(this.costSum, nNode.costSum);
        if (compare == 0)
        {
            int compareH = Double.compare(this.minimumRemainingCostToTarget, nNode.minimumRemainingCostToTarget);
            if (compareH==0) return 0;
            else return this.minimumRemainingCostToTarget>nNode.minimumRemainingCostToTarget ? 1:-1;
        }
        else return this.costSum>nNode.costSum ? 1:-1;
    }


    //cette méthode retourne stateNumber à partir de stateList
    public void intFromList() {
        int i = 1;
        this.intBoard=0;
        for(int num: this.board) {
            this.intBoard += i*num;
            i *=10;
        }
    }

    @Override
    public boolean equals(Object obj) {
        State testState = (State) obj;
        Integer test = testState.getIntBoard();
        if (test.equals(this.intBoard)) {
            return true;
        } else {
            return false;
        }
    }

    public void display(){
        System.out.println("\n"+getNextMove());
        for (int i = 0; i < board.size();i++) {
            System.out.print(board.get(i));
        }
    }
}
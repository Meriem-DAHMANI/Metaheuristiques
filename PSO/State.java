package com.PSO;
import java.util.ArrayList;

/**
 * @author beline
 * <p>The state class, used to represent each state and their
 * successors and moves that the particle took to reach the goal
 * </p>
 */

public class State{

    private  ArrayList<Integer> board;
    private  State predecessor;
    private String nextMove;

    /**
     * State constructor
     * @param board
     * @param predecessor
     * @param nextMove
     */
    public State(ArrayList<Integer> board,State predecessor, String nextMove ){
        this.board = board;
        this.predecessor=predecessor;
        this.nextMove=nextMove;
    }

    public ArrayList<Integer> getBoard() {return board;}
    public String getNextMove(){return nextMove;}
    public State getPredecessor() {return predecessor;}

    /**
     * this method will compare the board of two states
     * @param obj
     * @return
     */
    public boolean equals(Object obj) {
        State testState = (State) obj;
        ArrayList test = testState.getBoard();
        if (test.equals(board)) {
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
package com;
import java.util.Stack;

public class Result {

    private Stack<String> movesForTheShortestPath;
    private int numberOfExploredNodes;
    private int  numberOfDevelopedNodes;
    private int shortestPath;

    public Result(Stack<String> movesForTheShortestPath,
                  int numberOfExploredNodes,
                  int numberOfDevelopedNodes,
                  int shortestPath) {
        
        this.movesForTheShortestPath = movesForTheShortestPath;
        this.numberOfExploredNodes = numberOfExploredNodes;
        this.numberOfDevelopedNodes = numberOfDevelopedNodes;
        this.shortestPath= shortestPath;
    }

    public Result(Stack<String> path) {
        this.movesForTheShortestPath=path;
    }

    public Stack<String> getMovesForTheShortestPath() {return movesForTheShortestPath;}
    public int getNumberOfExploredNodes() {return numberOfExploredNodes;}
    public int getNumberOfDevelopedNodes() {return numberOfDevelopedNodes;}
    public int getShortestPath() {return shortestPath;}

    public String display(){

        return " moves are: "+this.movesForTheShortestPath+"\n"+
                "the number of explored nodes: "+this.getNumberOfExploredNodes()+"\n"+
                "the number of developed nodes: "+this.getNumberOfDevelopedNodes()+"\n"+
                "the shortest path to reach the goal: "+this.getShortestPath();
    }
}

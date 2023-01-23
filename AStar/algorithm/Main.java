package com.AStar.algorithm;

import java.io.IOException;
import java.util.ArrayList;


public class Main {

    public static ArrayList<Integer> solvedBoard = new ArrayList<>();
    public static ArrayList<Integer> initialBoard = new ArrayList<>();
    public static AStarSolver solution;

    public static void main(String[] args) throws IOException {

        String initial= "412608573";
        String goal = "123804765";
        for(int i=0; i<9;i++){
            initialBoard.add(Character.getNumericValue(initial.charAt(i)));
            solvedBoard.add(Character.getNumericValue(goal.charAt(i)));
        }
        solution = new AStarSolver(initialBoard, solvedBoard, 1);
        solution.AStar();

    }
}

package com.PSO;
import java.util.ArrayList;

public class Main {

    public static ArrayList<Integer> solvedBoard = new ArrayList<>();
    public static ArrayList<Integer> initialBoard = new ArrayList<>();
    public static AlgoPSO solution;
    public static void main(String[] args){

        String initial= "412608573";
        String goal = "123456780";
        for(int i=0; i<9;i++){
            initialBoard.add(Character.getNumericValue(initial.charAt(i)));
            solvedBoard.add(Character.getNumericValue(goal.charAt(i)));
        }

        solution = new AlgoPSO( initialBoard,
                                solvedBoard,
                                2, 2, 2, 1,
                                30000,
                                100);
        solution.PSO();

    }
}
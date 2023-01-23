package com.AStar.heuristic;
import com.AStar.algorithm.Heuristic;

import java.util.ArrayList;

/**
 * This heuristic count the  Euclidean Distance between the current state to the goal
 * @author beline
 *
 */

public class EuclideanDistanceHeuristic implements Heuristic {
    @Override
    public int getHeuristic(ArrayList<Integer> currentBoard, ArrayList<Integer> targetBoard) {
        int euclideanSum = 0;
        for(int i = 0; i < currentBoard.size(); i++) {
            int currentBoardValue = currentBoard.get(i);
            int solvedBoardValue = targetBoard.get(i);
            if(currentBoardValue != solvedBoardValue && currentBoardValue != 0)
                euclideanSum  += calculateEuclideanDistance(targetBoard, i, currentBoardValue);
        }
        return euclideanSum ;
    }

    public static double calculateEuclideanDistance(ArrayList<Integer> targetBoard, int index, int value) {
        int currentRow = (index / 3) + 1;
        int currentColumn = (index % 3) + 1;
        int targetIndex = targetBoard.indexOf(value);
        int targetRow = (targetIndex / 3) + 1;
        int targetColumn = (targetIndex % 3) + 1;

        return Math.sqrt(Math.pow((targetColumn - currentColumn),2) + Math.pow((targetRow - currentRow),2));
    }
}

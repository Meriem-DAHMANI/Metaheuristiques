package com.AStar.heuristic;
import com.AStar.algorithm.Heuristic;

import java.util.ArrayList;


/**
 * This heuristic count the number of misplaced tiles in our board
 * @author beline
 *
 */
public class MisplacedTilesHeuristic implements Heuristic {
    @Override
    public int getHeuristic(ArrayList<Integer> currentBoard,ArrayList<Integer> targetBoard ) {
        int misplacedCounter = 0;
        for(int i = 0; i < currentBoard.size(); i++) {
            int currentBoardValue = currentBoard.get(i);
            int solvedBoardValue = targetBoard.get(i);
            if(currentBoardValue != solvedBoardValue && currentBoardValue != 0)
                misplacedCounter++;
        }
        return misplacedCounter;
    }
}
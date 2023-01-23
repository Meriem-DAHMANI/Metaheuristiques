package com.AStar.algorithm;
import java.util.ArrayList;

/**
 * <p>
 * Heuristics must have a cost method.
 * </p>
 * @author beline
 *
 */
public interface Heuristic {
    int getHeuristic(ArrayList<Integer> currentBoard, ArrayList<Integer> targetBoard );
}

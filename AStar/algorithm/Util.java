package com.AStar.algorithm;

import java.util.ArrayList;
import java.util.Collection;

public abstract class Util {

    public ArrayList<Integer> target;
    public abstract void movement(int zeroIndex, int direction, State current);

    /**
     * <p>Static directions allow us to always find the legal set of possible
     * directions of the empty state and create children from the current parent
     * state.</p>
     *
     * @param current State just explored
     */
    public  void findChildren(State current) {
        int zeroIndex = current.getBoard().indexOf(0);
        /*
         * Directions are always -3, -1, +1 or +3 in our arrayList
         * as we are using a 3x3 grid
         */
        this.movement(zeroIndex, 3, current);
        this.movement(zeroIndex, -3, current);
        this.movement(zeroIndex, -1, current);
        this.movement(zeroIndex, 1, current);
    }

    /**
     * <p>Using the location of 0 tile and the intended direction,
     * see if it is a legal move to make, or if it would send the tile
     * out of the bounds of the state or 'wrap' around from one side
     * of the state to another.</p>
     *
     * @param zeroIndex
     *            - Current location of the empty space
     * @param direction
     *            - Movement of the empty space
     * @return
     */
    public boolean rulesForMovement(int zeroIndex, int direction) {
        boolean withinPossibility = false;
        // Check the index is not on the 'edge' and going to 'wrap' over.
        if ((zeroIndex == 2 || zeroIndex == 5 || zeroIndex == 8)
                && direction == 1) {
            withinPossibility = false;
        } else if ((zeroIndex == 0 || zeroIndex == 3 || zeroIndex == 6)
                && direction == -1) {
            withinPossibility = false;
            // Check the zero is not on the top or bottom row and trying to move out of bounds.
        } else if (((zeroIndex <= 2) && (direction == -3))
                || ((zeroIndex >= 6) && (direction == 3))) {
            withinPossibility = false;
        }

        // Check that the calculated movement is within range (0 - 8)
        else {
            int indexToBe = zeroIndex + direction;
            if (indexToBe >= 0 && indexToBe <= 8) {
                withinPossibility = true;
            }
        }
        // return result of checks
        return withinPossibility;
    }

    /**
     * <p>Create a child node based on if it is a legal state after a tile
     * movement.</p>
     *
     * @param current
     *            state of the parent
     * @param zeroIndex
     *            location of the empty tile in the parent state
     * @param direction
     *            direction of movement of the empty tile
     * @param heuristic
    heuristic used to calculate the minimum Remaining Cost T oTarget
     * @return the created child State
     */
    protected State createChild(State current,
                                int zeroIndex,
                                int direction,
                                Heuristic heuristic,
                                ArrayList<Integer> target) {
        int size= current.getBoard().size();
        ArrayList<Integer> newBoard = new ArrayList<>(size);
        for(int i=0; i<size;i++){
            int element=(int)current.getBoard().get(i);

            newBoard.add(element);
        }
        // Get the character that will be replaced by the blank tile.
        int toMove = newBoard.get(zeroIndex + direction);
        // Replace the moved tile with the blank tile
        newBoard.set(zeroIndex + direction, 0);
        // Old position of 0/empty space becomes what it has swapped with
        newBoard.set(zeroIndex, toMove);
        // Calculate the minimum remaining cost to target
        int minimumRemainingCostToTarget = heuristic.getHeuristic(newBoard, target);
        // Return a new 'Child' GridState, using the char array with the moved tiles
        return new State(newBoard,current,minimumRemainingCostToTarget, moves(direction));
    }
    /**
     * <p>Looks through the given list for the given
     * State and returns either the found State
     * if it exists in the list, or null if not.
     * @param direction
     * @return move
     */
    public String moves(int direction){
        String move = null;
        switch (direction) {
            case 1:
                move="RIGHT";
                break;
            case -1:
                move="LEFT";
                break;
            case 3:
                move="DOWN";
                break;
            case -3:
                move="UP";
                break;
        }
        return move;
    }
    /**
     * <p>Looks through the given list for the given
     * State and returns either the found State
     * if it exists in the list, or null if not.
     * @param state
     * @param list
     * @return
     */
    public State inList(State state, Collection<State> list) {
        for (State checker : list) {
            if (state.equals(checker)) {
                return checker;
            }
        }
        return null;
    }

}
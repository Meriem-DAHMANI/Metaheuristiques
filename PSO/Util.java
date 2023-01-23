package com.PSO;
import java.util.*;

public abstract class Util {

    public ArrayList<Integer> target;
    public abstract Particle createInitialParticles(State initialState,
                                                    ArrayList<Integer> target,
                                                    double velocity);
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
    heuristic used to calculate the minimum Remaining Cost T oTarget
     * @return the created child State
     */
    protected State createChild(State current,
                                int zeroIndex,
                                int direction) {

        int size= current.getBoard().size();
        ArrayList<Integer> newBoard = new ArrayList<>(size);
        for(int i=0; i<size;i++){
            int element=current.getBoard().get(i);
            newBoard.add(element);
        }
        // Get the character that will be replaced by the blank tile.
        int toMove = newBoard.get(zeroIndex + direction);
        // Replace the moved tile with the blank tile
        newBoard.set(zeroIndex + direction, 0);
        // Old position of 0/empty space becomes what it has swapped with
        newBoard.set(zeroIndex, toMove);
        // Return a new 'Child' GridState, using the char array with the moved tiles
        return new State(newBoard,current,moves(direction));
    }

    /**
     * Create children from a particular solution
     * @param current state of the parent
     * @param eventualSolution a random solution of one particle
     * In this method we need to verify if the move is legal or not,
     * if not we need to reverse the value of the move
     * @return the created final child of all moves of random solution
     */

    public State finalChild(State current, ArrayList<Integer> eventualSolution){
        int size = eventualSolution.size();
        State newState = current;
        for(int i=0; i<size; i++){
            if(rulesForMovement(current.getBoard().indexOf(0), eventualSolution.get(i))) {
                newState = createChild(current, current.getBoard().indexOf(0), eventualSolution.get(i));
            }else{
                switch (eventualSolution.get(i)){
                    case 1:
                        newState = createChild(current, current.getBoard().indexOf(0), -1);
                        break;
                    case -1:
                        newState = createChild(current, current.getBoard().indexOf(0), 1);
                        break;
                    case 3:
                        newState = createChild(current, current.getBoard().indexOf(0), -3);
                        break;
                    case -3:
                        newState = createChild(current, current.getBoard().indexOf(0), 3);
                        break;
                }
            }
            current = new State(newState.getBoard(), newState.getPredecessor(), newState.getNextMove());
        }
        return newState;
    }

    /**
     * This method calculate the position value
     * the program will use it only once for the initial state
     * @param eventualSolution the paren
     */
    public int positionValue(ArrayList<Integer> eventualSolution){
        int size = eventualSolution.size();
        int newPosition=0;
        byte x;
        for(int i=0; i<size; i++){
                x = movesInBinary(eventualSolution.get(i));
                newPosition = (newPosition << 2) | x;
            }
        return newPosition;
    }

    /**
     * this method return an array list of moves from the current position value
     * @param currentPositionValue
     * @return
     */

    public ArrayList<Integer> positionValueInverse(double currentPositionValue){
        String currentValue = Integer.toBinaryString(Math.abs((int)currentPositionValue));
        ArrayList<Integer> value = new ArrayList<>();
        int currentValueLength = currentValue.length();
        if(currentPositionValue>0){
            int i= currentValue.length();
            while(i<currentValueLength+2){
                currentValue = "0"+currentValue;
                i++;
            }
        }else if(currentPositionValue<0){
            int i= currentValue.length();
            while(i<currentValueLength+1){
                currentValue = "0"+currentValue;
                i++;
            }
            currentValue = "1"+currentValue;
        }
        int chunkSize=2;
        String[] chunks = currentValue.split("(?<=\\G.{" + chunkSize + "})");
        int j=0;
        while(j<chunks.length){
            switch (chunks[j]){
                case "11":
                    value.add(1);
                    break;
                case "10":
                    value.add(-1);
                    break;
                case "01":
                    value.add(3);
                    break;
                case "00":
                    value.add(-3);
                    break;
            }
            j++;
        }
        return value;
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
     * <p>Convert moves to binary</p>
     * @param direction
     * @return moveInBinary
     */
    public Byte movesInBinary(int direction){
        Byte moveInBinary = 0B00;
        switch (direction) {
            case 1:
                moveInBinary=0B011;
                break;
            case -1:
                moveInBinary=0B10;
                break;
            case 3:
                moveInBinary=0B01;
                break;
            case -3:
                moveInBinary=0B00;
                break;
        }
        return moveInBinary;
    }

    /**
     * Methode pour retourner une random solution
     */
    /**
     * A method that will return a random solution
     * this method will be used to generate random position for the initial particles
     * @return
     */
    public ArrayList<Integer> returnRandomSolutions(){
        List<Integer> givenList = Arrays.asList(1, -1, 3, -3);
        ArrayList<Integer> randomSolution = new ArrayList<>();
        Random rand = new Random();
        for(int i=0; i<4; i++){
            int randomElement = givenList.get(rand.nextInt(givenList.size()));
            randomSolution.add(randomElement);
        }
        return randomSolution;
    }
}

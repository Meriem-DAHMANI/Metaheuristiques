package work;

public class Step {
//constructeur
public Step() {
	
}

//les méthodes
public Node moveUp(Node currentState) {
	Node newState = null;
    int emptyPosition = currentState.getPos(0);
    if (emptyPosition>2) {
    	newState = new Node();
    	newState.setStateList(currentState.affectState());  
    	newState.setDepth(currentState.getDepth()+1);
    	newState.setPredecessor(currentState);
    	newState.setMove("UP");
    	//inverser les deux cases
        int temp = newState.getNum(emptyPosition - 3);
        newState.setNum(emptyPosition, temp);
        newState.setNum(emptyPosition-3, 0);
    }
    return newState;
}

public Node moveRight(Node currentState) {
	Node newState = null;
	int emptyPosition = currentState.getPos(0);
    if (emptyPosition % 3 != 2) {
    	newState = new Node();
    	newState.setStateList(currentState.affectState());
    	newState.setDepth(currentState.getDepth()+1);
    	newState.setPredecessor(currentState);
    	newState.setMove("RIGHT");
    	//inverser les deux cases
        int temp = newState.getNum(emptyPosition + 1);
        newState.setNum(emptyPosition, temp);
        newState.setNum(emptyPosition+1, 0);
    }
    return newState;
}

public Node moveDown(Node currentState) {
	Node newState = null;
	int emptyPosition = currentState.getPos(0);
    if (emptyPosition < 6) {
    	newState = new Node();
    	newState.setStateList(currentState.affectState());
    	newState.setDepth(currentState.getDepth()+1);
    	newState.setPredecessor(currentState);
    	newState.setMove("DOWN");
    	//inverser les deux cases
        int temp = newState.getNum(emptyPosition + 3);
        newState.setNum(emptyPosition, temp);
        newState.setNum(emptyPosition+3, 0);
    }
    return newState;
}

public Node moveLeft(Node currentState) {
	Node newState = null;
	int emptyPosition = currentState.getPos(0);
    if (emptyPosition % 3 != 0) {
    	newState = new Node();
    	newState.setStateList(currentState.affectState());
    	newState.setDepth(currentState.getDepth() + 1);
    	newState.setPredecessor(currentState);
    	newState.setMove("LEFT");
    	//inverser les deux cases
        int temp = newState.getNum(emptyPosition - 1);
        newState.setNum(emptyPosition, temp);
        newState.setNum(emptyPosition-1, 0);
    }
    return newState;
}

}

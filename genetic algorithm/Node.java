package work;

import java.util.ArrayList;

public class Node {
	private int stateNumber;
	private Node predecessor = null;
	private int depth = 0;
	private String move;
	private ArrayList <Integer> stateList;

//constructeurs
//1
public Node() {
}

//2
public Node(ArrayList <Integer> stateList, Node predecessor, int depth, String move) {
   this.stateList = stateList;
   this.predecessor = predecessor;
   this.depth = depth;
   this.move = move;
   this.intFromList();
}

//cette méthode retourne stateNumber à partir de stateList
public void intFromList() {
	int i = 1;
	this.stateNumber = 0;
	for(int num: this.stateList) {
		this.stateNumber += i*num;
		i *=10;
	}
}

//setters & getters
public int getNum(int pos) {
   return stateList.get(pos);
}

public void setNum(int pos, int num) {
   stateList.set(pos, num);
   this.intFromList();
}

public int getPos(int num) {
 return this.stateList.indexOf(num);
}

public Node getPredecessor() {
	return predecessor;
}

public void setPredecessor(Node predecessor) {
	this.predecessor = predecessor;
}

public int getDepth() {
	return depth;
}

public void setDepth(int depth) {
	this.depth = depth;
}

public int getStateNumber() {
	return stateNumber;
}

public void setStateNumber(int stateNumber) {
	this.stateNumber = stateNumber;
}

public ArrayList<Integer> getStateList() {
	return stateList;
}

public String getMove() {
	return move;
}

public void setMove(String move) {
	this.move = move;
}

public void setStateList(ArrayList<Integer> stateList) {
	this.stateList = stateList;
	intFromList();
}

//méthode pour affecter le contenu du noeud à un ature
public ArrayList<Integer> affectState() {
	ArrayList<Integer> newState = new ArrayList<Integer>();
	for(int i=0;i<9;i++) {
		newState.add(this.stateList.get(i));
	}
	return newState;
}

//méthodes pour vérifier si deux noeud sont égaux en se basant sur leurs contenus
public boolean equal(Node n) {
return this.stateNumber == n.getStateNumber();
}

//méthode toString
public String toString() {
 String output = this.move+"\n";
 int pos = 0;
 for (int x = 0; x < 3; x++) {
     for (int y = 0; y < 3; y++) {
         output += stateList.get(pos)+" ";
         pos ++;
     }
     output += "\n";
 }

 return output;
}

}

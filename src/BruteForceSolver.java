import java.util.*;

public class BruteForceSolver {
	private HashMap<State,Move> reachedStates;
	private State initialState;
	private int length;
	private Move lastMove;
	private Queue<State> beingVisited;
	private Queue<State> toVisit;
	
	public BruteForceSolver(State initialState){
		/*
		 * Initializes a solver with an initial state
		 */
		
		this.initialState = initialState;
		reachedStates = new HashMap<State,Move>();
		reachedStates.put(initialState, null);
		beingVisited = new LinkedList<State>();
		toVisit = new LinkedList<State>();
		beingVisited.add(initialState);
		length = 1;//the depth of beingVisited.nextStates in the tree of possible states starting from initialState
	}
	
	
	public void solve(){
		/*
		 * Computes a shortest solution by brute force and fills 'length' and 'lastMove' fields.
		 */
		
		if (initialState.canFinish()){
			return;
		}
		
		//browse the tree of possible states in width
		while (beingVisited.isEmpty() == false){
					
			while (beingVisited.isEmpty() == false){
				//for each state of beingVisited, find the nextStates possible, check if they can end the game, and add them to the State to Visit
				State currentState = beingVisited.poll();
				LinkedList<State> nextStates = currentState.nextStates(reachedStates);
				for(State s : nextStates){
					if (s.canFinish()){
						length++;//+1 for the concluding move
						lastMove = reachedStates.get(s);
						return;
					}
				}
				toVisit.addAll(nextStates);
			}
			//once beingVisited is empty, pour the states of toVisit into beingVisited
			beingVisited.addAll(toVisit);
			toVisit.clear();
			length ++;		
	}
		length = 0;//all possible states have been tried, none could finish the game
		return;
	
}
	
	public void displaySolution(){
		/*
		 * Displays the solution if solve() have been executed
		 */
		
		switch (length){
		case 0: 
			System.out.println("No solution. All possible states have been tried.");
			break;
		case 1:
			System.out.println("Solution in 1 move :");
			System.out.println("Just slide the red car to the right.");
			break;
		default:
			System.out.println("Solution in "+ length + " moves :");
			System.out.println(lastMove.fullSequence()+"Slide the red car to the exit !\n");
		}
	}
	
}

import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;


public class HeuristicSolver {
	private HashMap<State,Move> reachedStates;
	private State initialState;
	private Move lastMove;
	private PriorityQueue<State> toVisit;
	private int length = 1;
	
	public HeuristicSolver (State initialState, Heuristic h) {
		/*
		 * Initializes a solver with an initial state
		 */
		
		this.initialState = initialState;
		reachedStates = new HashMap<State,Move>();
		reachedStates.put(initialState, null);
		toVisit = new PriorityQueue<State>(new StateComparator(h));
		toVisit.add(initialState);
	}
	
	public void solve(){
		/*
		 * Computes a shortest solution by brute force and fills 'lastMove' field.
		 */
		
		if (initialState.canFinish()){
			return;
		}
		
		//browse the tree with a priority queue
		while (!toVisit.isEmpty()){
			//for each state of beingVisited, find the nextStates possible, check if they can end the game, and add them to the State to Visit
			State currentState = toVisit.poll();
			LinkedList<State> nextStates = currentState.nextStates(reachedStates);
			for(State s : nextStates){//adds states that will be processed in the order given by the heuristic
				s.statesFromInit = currentState.statesFromInit+1;//updates the distance to the first move
				if (s.canFinish()){
					lastMove = reachedStates.get(s);
					length = s.statesFromInit+1;
					return;
				}
				toVisit.add(s);
			}
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

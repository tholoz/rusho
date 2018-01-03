import java.io.*;
import java.util.*;

public class State implements Comparable{
	
	@SuppressWarnings("serial")
	class InvalidStateException extends Exception{
		InvalidStateException(){
			
		}
	}
	
	/*grid and vehicules. The first one in the list is the one
	 *we have to take out of the grid.*/
	int gridSize;
	int num;
	Vehicule mainVehicule;
	Vehicule[] vehicules;
	int vehiclesToExit;
	int statesFromInit;
	
	public State (File f) {
		/*
		 * constructs a state from a .txt file
		 */
		
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(f.getAbsolutePath()));
			this.gridSize = Integer.parseInt(br.readLine());
			this.num = Integer.parseInt(br.readLine());
			vehicules = new Vehicule[num];
			String vehicule;
			while ((vehicule = br.readLine()) != null) {
				process(vehicules, vehicule); 
				/* s'occupe d'ajouter le véhicule à la place adéquate.
				 */
			}

			/*On ajoute un test d'intersection qui lève
			 *une exception si l'entrée n'est pas correcte.*/
			checkForInvalidity();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidStateException i) {
			i.printStackTrace();
			System.out.println("The input file is invalid: some vehicles intersect or are out of the grid.");
		}
		vehiclesToExit = vehiclesToExit();
		statesFromInit = 0;
	}
	
	public State (Vehicule[] vehicles){
		/*
		 * Constructs a state from an array of vehicles
		 * Such a state may be invalid (we will check Validity outside the constructor)
		 */
		this.gridSize = vehicles[0].getGridSize();
		this.num = vehicles.length;
		this.vehicules = vehicles;
		
	}
	
	public State (Vehicule[] vehicles, int vehiclesToExit) {
		//Used for heuristical algorithm
		this.gridSize = vehicles[0].getGridSize();
		this.num = vehicles.length;
		this.vehicules = vehicles;
		this.vehiclesToExit = vehiclesToExit;
	}
	

	private void checkForInvalidity() throws InvalidStateException {
		/*
		 * Throws an exception if the state is invalid (overlapping vehicles or vehicles out of the grid
		 */
		
		int[][] grid = new int[this.gridSize][this.gridSize];
		for (Vehicule v : this.vehicules) {
			LinkedList<int[]> tiles = v.tiles();
			for (int[]pos :tiles) {
				if (pos[0]<0 || pos[0] >= this.gridSize || pos[1]<0 || pos[1]>= this.gridSize){
					throw (new InvalidStateException());
				}
				
				
				if (grid[pos[0]][pos[1]]>0) {
					throw (new InvalidStateException());
					//We stop if the tile is already filled
				}
				grid[pos[0]][pos[1]] = v.getId();

			}
		}
		
	}


	void process(Vehicule[] vehicules, String vehicule) {
		/*
		 * Appends 'vehicule' to the list of vehicles
		 */
		
		String[] str = vehicule.split(" ");
		int id = Integer.parseInt(str[0]);
		char ori = str[1].charAt(0);
		int size = Integer.parseInt(str[2]);
		int i = Integer.parseInt(str[3]);
		int j = Integer.parseInt(str[4]);
		vehicules[id-1]=(new Vehicule(id, ori, size, i, j, this.gridSize));
		if (id==1) {
			mainVehicule=new Vehicule(id, ori, size, i, j, this.gridSize);
		}
	}
	
	
	public int[][] toGrid() {
		/* 
		 * Returns a grid of integers that represents the state 
		 */
		
		int[][] grid = new int[this.gridSize][this.gridSize];
		for (int i=0; i<this.gridSize; i++) {
			for (int j=0; j<this.gridSize; j++) {
				grid[i][j]=0;
			}
		}
		for (Vehicule v : this.vehicules) {
			int i = v.getId();
			LinkedList<int[]> tiles = v.tiles();
			for (int[]pos :tiles) {
				grid[pos[0]][pos[1]] = i;
			}
		}
		return grid;
	}
	
	
	
	
	public void display() {
		/*
		 * Displays the grid
		 */
		
		String res = "";
		int[][] grid = toGrid();
		for (int i=0; i<this.gridSize; i++) {
			for (int j=0; j<this.gridSize; j++) {
				if (grid[i][j]==0) {
					res += "* ";
				}
				else res += grid[i][j]+" ";
			}
			res += "\n";
		}
		System.out.println(res);
	}


	public int[][] getPositions() {
		/*
		 * Returns a two-column array (x,y coordinates) with a line by vehicle
		 */
		
		int [][] res = new int[num][2];
		for (int i=0; i<num; i++) {
			res[i] = vehicules[i].getPos();
		}
		return res;
	}
	
	@Override
	public int hashCode(){
		/*
		 * Two equal grids (cell by cell) must return the same hash
		 */
		
		int[][] grid = this.toGrid();
		return Arrays.deepHashCode(grid);//a simple HashCode would depend of the instance of the array
	}
	
	@Override
	public boolean equals(Object obj){
		/* 
		 * Two states are equal iif their grids are equal (cell by cell)
		 */
		
		if (obj instanceof State == false){
			return false;
		}
		int[][] grid = this.toGrid();
		int[][] objGrid = ((State) obj).toGrid();
		if (grid.length != objGrid.length || grid[0].length != objGrid[0].length){
			return false;
		}
		for (int i=0; i < grid.length; i++){
			for (int j = 0; j < grid[0].length;j++){
				if (grid[i][j] != objGrid[i][j]){
					return false;
				}
			}
		}
		
		return true;
	}
	
	public boolean canFinish(){
		/*
		 * Returns true iif this state can lead to victory in one move
		 * We assume that the exit is at the middle-right, and that the red car is horizontal (else, rotate the game and modify the computation of the exit)
		 */
		
		Vehicule redCar = vehicules[0];
		//int[][] grid = this.toGrid();
		//int x = redCar.getPos()[0];
		int y = redCar.getPos()[1];
		
		if (y != (this.gridSize/2)){
			//check if the red car is on the right line
			return false;
		}
		//for (int X = x + redCar.getSize(); X<= this.gridSize; X++ ){
			//check that there is no car between the red car and the exit
		//	if (grid[y-1][X-1] != 0){
		//		return false;
		//	}
		//}
		
		return vehiclesToExit==0;
		
	}
	
	
	public LinkedList<State> nextStates(HashMap<State,Move> reachedStates){
		/*
		 * Returns a LinkedList of States reacheable from this State, that have not already been reached
		 * Modifies the HashMap reachedStates which associates to each State the Move leading to it (only already reached States are mapped)
		 * 
		 */

		
		LinkedList<State> states = new LinkedList<State>();
		Move previousMove = reachedStates.get(this);//gets the last move of the sequence of moves leading to the current state (from the start)
		
		
		for(Vehicule movingVehicle : vehicules){
			//finds the possible moves for each vehicle
			int id = movingVehicle.getId();
			int ori = movingVehicle.getOri();
			int size = movingVehicle.getSize();
			int[] pos = movingVehicle.getPos();		
			int gridSize = movingVehicle.getGridSize();
					
			//horizontal moves			
			if (ori == 0){
				
				//move to the right
				int k = 1;
				boolean validState = true;
				while (validState) {
					Vehicule[] testVehicles = vehicules.clone();//this is not a deep copy
					Vehicule movedVehicle = new Vehicule(id,'h',size,pos[0]+k,pos[1],gridSize);//...then we create a new vehicle, leaving the older state unchanged
					testVehicles[id -1] = movedVehicle;		
					int toExit = vehiclesToExit + updateToExit(movingVehicle, movedVehicle);
					State testState = new State(testVehicles, toExit);//potentially invalid
										
					try{
						testState.checkForInvalidity();
						//continue if the state is valid (else goto catch)
						//and if it is also new we add it
						if (reachedStates.containsKey(testState) == false){
							states.add(testState);
							Move nextMove = new Move(id,0,k,previousMove);
							reachedStates.put(testState, nextMove);
						}
						k++;
					}
					//Invalid state
					catch (InvalidStateException i) {
						validState = false;//we can't keep moving the car in this direction
					}

				}
				
				//move to the left
				k = -1;
				validState = true;
				while (validState) {
					Vehicule[] testVehicles = vehicules.clone();//this is not a deep copy
					Vehicule movedVehicle = new Vehicule(id,'h',size,pos[0]+k,pos[1],gridSize);//...then we create a new vehicle, leaving the older state unchanged
					testVehicles[id -1] = movedVehicle;
					int toExit = vehiclesToExit + updateToExit(movingVehicle, movedVehicle);
					State testState = new State(testVehicles, toExit);
					try{
						testState.checkForInvalidity();
						//continue if the state is valid (else goto catch)
						//and if it is also new we add it
						if (reachedStates.containsKey(testState) == false){
							states.add(testState);
							Move nextMove = new Move(id,0,k,previousMove);
							reachedStates.put(testState, nextMove);
						}
						k--;						
						
					}
					//Invalid state
					catch (InvalidStateException i) {
						validState = false;//we can't keep moving the car in this direction
					}

				}

				
				
				
			}else{
			//vertical moves
				//move down
				int k = 1;
				boolean validState = true;
				while (validState) {
					Vehicule[] testVehicles = vehicules.clone();//this is not a deep copy
					Vehicule movedVehicle = new Vehicule(id,'v',size,pos[0],pos[1]+k,gridSize);//...then we create a new vehicle, leaving the older state unchanged
					testVehicles[id -1] = movedVehicle;
					int toExit = vehiclesToExit + updateToExit(movingVehicle, movedVehicle);
					State testState = new State(testVehicles, toExit);
					
					try{
						testState.checkForInvalidity();
						//continue if the state is valid (else goto catch)
						//and if it is also new we add it
						if (reachedStates.containsKey(testState) == false){
							states.add(testState);
							Move nextMove = new Move(id,1,k,previousMove);
							reachedStates.put(testState, nextMove);
						}
						k++;
						
					}
					//Invalid state
					catch (InvalidStateException i) {
						validState = false;//we can't keep moving the car in this direction
					}

				}
				
				//move up
				k = -1;
				validState = true;
				while (validState) {
					Vehicule[] testVehicles = vehicules.clone();//this is not a deep copy
					Vehicule movedVehicle = new Vehicule(id,'v',size,pos[0],pos[1]+k,gridSize);//...then we create a new vehicle, leaving the older state unchanged
					testVehicles[id -1] = movedVehicle;
					int toExit = vehiclesToExit + updateToExit(movingVehicle, movedVehicle);
					State testState = new State(testVehicles, toExit);
					try{
						testState.checkForInvalidity();
						//continue if the state is valid (else goto catch)
						//and if it is also new we add it
						if (reachedStates.containsKey(testState) == false){
							states.add(testState);
							Move nextMove = new Move(id,1,k,previousMove);
							reachedStates.put(testState, nextMove);
							
						}
						k--;
					}
					//Invalid state
					catch (InvalidStateException i) {
						validState = false;//we can't keep moving the car in this direction
					}

				}
				
			}
			
		}
		
		return states;
	}
	
	
	private int updateToExit(Vehicule prev, Vehicule after) {
		if (prev.getId()==1) {
			return 0;
		}
		else{
			return after.blocking(vehicules[0].getPos())-prev.blocking(vehicules[0].getPos());
		}
	}

	public int vehiclesToExit() {
		//gives the amount of vehicles between the main vehicle and the exit.
		int[][] grid = this.toGrid();
		LinkedList<Integer> seen = new LinkedList<Integer>();
		mainVehicule = vehicules[0]; 
		int i = mainVehicule.getPos()[1];
		for (int j = mainVehicule.getPos()[0]; j<=mainVehicule.getGridSize(); j++) {
			int k = grid[i-1][j-1];
			if (k>1 && !seen.contains(k)) {
				seen.add(k);
			}
		}
		return seen.size();
	}

	@Override
	public int compareTo(Object o) {
		State s = (State) o;
		return (this.vehiclesToExit+this.statesFromInit-s.vehiclesToExit-s.statesFromInit);
	}
	
	
}

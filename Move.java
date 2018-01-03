
public class Move {
	private int id;
	private int ori;
	private int value;
	private Move previousMove;
	
	public Move(int id, int orientation, int value, Move previousMove){
		this.id = id;
		this.ori = orientation;
		this.value = value;
		this.previousMove = previousMove;		
	}
	
	public int getId(){
		//returns the id of the car moved
		return id;
	}
	
	public int getOri(){
		//returns its orientation (0 = horizontal)
		return ori;
	}
	
	public int getValue(){
		//returns the length of the move (positive = to the right or to the bottom)
		return value;
	}
	
	public Move getPreviousMove(){
		//returns the previous move if there is any, null if this move is the first
		return previousMove;
	}
	
	@Override
	public String toString(){
		if (ori == 0) {
			return("Move horizontally the car " + id + " of " + value + " cells.\n");
		}
		else{
			return("Move vertically the car " + id + " of " + value + " cells.\n");
		}
	}
	
	public String fullSequence(){
		/*
		 * Returns the sequence of moves from the beginning (null excluded) to this move
		 */
		
		if (previousMove == null){
			return this.toString();
		}
		return(previousMove.fullSequence() + this.toString());
	}
}



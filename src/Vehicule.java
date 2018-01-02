import java.util.*;


public class Vehicule {

	private int id;
	private int ori;
	private int size;
	private int[] pos;
	private int gridSize;//stores the grid size to be able to create a State with only a list of Vehicles

	Vehicule(int id, char orientation, int size, int i, int j, int gridSize){
		this.id = id;
		this.size = size;
		if (orientation=='h') {
			ori = 0;
		}
		else ori = 1;

		pos = new int[] {i,j};
		this.gridSize = gridSize;
	}
	
	//getters & setters
	public int getId(){
		return id;
	}
	
	public int getOri(){
		return ori;
	}
	
	public int getSize(){
		return size;
	}
	
	public int[] getPos(){
		return pos;
	}
	
	public int getGridSize(){
		return gridSize;
	}

	public void setPos(int[] pos){
		this.pos = pos;
	}
	
	LinkedList<int[]> tiles(){
		/* permet d'obtenir les cases (a partir de zero) qu'occupe le vÃ©hicule
		 * sous forme d'une liste.*/
		LinkedList<int[]> res = new LinkedList<int[]>();
		int i = pos[1]-1;//the i^th line is the ordinate (y) minus 1 (starts at zero)
		int j = pos[0]-1;//the j^th column is the absciss (x) minus 1
		if (ori==0) {
			for (int k=j; k<j+size; k++) {
				res.add(new int[] {i,k});//the line is constant (horizontal)
			}
		}
		else {
			for (int k=i; k<i+size; k++) {
				res.add(new int[] {k,j});//the column is constant (vertical)
			}
		}
		return res;
	}

}

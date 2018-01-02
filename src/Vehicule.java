import java.util.*;


public class Vehicule {

	private int id;
	private int ori;
	private int size;
	private int[] pos;

	Vehicule(int id, char orientation, int size, int i, int j){
		this.id = id;
		this.size = size;
		if (orientation=='h') {
			ori = 0;
		}
		else ori = 1;

		pos = new int[] {i,j};
	}
	
	//getters
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

	LinkedList<int[]> tiles(){
		/* permet d'obtenir les cases (a partir de zero) qu'occupe le véhicule
		 * sous forme d'une liste.*/
		LinkedList<int[]> res = new LinkedList<int[]>();
		int i = pos[0]-1;
		int j = pos[1]-1;
		if (ori==0) {
			for (int k=j; k<j+size; k++) {
				res.add(new int[] {i,k});
			}
		}
		else {
			for (int k=i; k<i+size; k++) {
				res.add(new int[] {k,j});
			}
		}
		return res;
	}

}

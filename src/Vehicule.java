import java.util.*;


public class Vehicule {

	int id;
	int ori;
	int size;
	int[] pos;

	Vehicule(int id, char orientation, int size, int i, int j){
		this.id = id;
		this.size = size;
		if (orientation=='h') {
			ori = 0;
		}
		else ori = 1;

		pos = new int[] {i,j};
	}

	LinkedList<int[]> tiles(){
		/* permet d'obtenir les cases qu'occupe le véhicule
		 * sous forme d'une liste.*/
		LinkedList<int[]> res = new LinkedList<int[]>();
		int i = pos[0]-1;
		int j = pos[1]-1;
		if (ori==0) {
			for (int k=j; k<j+size; k++) {
				res.add(new int[] {i,k});
			}
		}
		return res;
	}

}

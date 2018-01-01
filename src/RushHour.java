import java.io.*;
import java.util.*;


public class RushHour {

	@SuppressWarnings("serial")
	class Intersection extends Exception{
		Intersection(){
			System.out.println("Le fichier donné en entée est incorrect, il y a intersection des véhicules.");
		}
	}

	/*grid and vehicules. The first one in the list is the one
	 *we have to take out of the grid.*/
	int gridSize;
	int num;
	LinkedList<Vehicule> vehicules;


	public RushHour(File f) {
		LinkedList<Vehicule> vehicules = new LinkedList<Vehicule>();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(f.getAbsolutePath()));
			String size = br.readLine();
			String num = br.readLine();
			String vehicule;
			while ((vehicule = br.readLine()) != null) {
				process(vehicules, vehicule);
			}
			this.vehicules = vehicules;
			this.gridSize = Integer.parseInt(size);
			this.num = Integer.parseInt(num);
			/*On ajoute un test d'intersection qui l�ve
			 *une exception si l'entr�e n'est pas correcte.*/
			checkForIntersection();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Intersection i) {
			i.printStackTrace();
		}
	}


	private void checkForIntersection() throws Intersection {
		int[][] grid = new int[this.gridSize][this.gridSize];
		for (Vehicule v : this.vehicules) {
			LinkedList<int[]> tiles = v.tiles();
			for (int[]pos :tiles) {
				if (grid[pos[0]][pos[1]]==1) {
					throw (new Intersection());
					//On s'arr�te si la case a d�j� �t� remplie.
				}
				grid[pos[0]][pos[1]] = 1;
			}
		}

	}


	void process(LinkedList<Vehicule> vehicules, String vehicule) {
		String[] str = vehicule.split(" ");
		int id = Integer.parseInt(str[0]);
		char ori = str[1].charAt(0);
		int size = Integer.parseInt(str[2]);
		int i = Integer.parseInt(str[3]);
		int j = Integer.parseInt(str[4]);
		vehicules.add(new Vehicule(id, ori, size, i, j));
	}

	public void display() {
		int[][] grid = new int[this.gridSize][this.gridSize];
		for (int i=0; i<this.gridSize; i++) {
			for (int j=0; j<this.gridSize; j++) {
				grid[i][j]=0;
			}
		}
		for (Vehicule v : this.vehicules) {
			int i = v.id;
			LinkedList<int[]> tiles = v.tiles();
			for (int[]pos :tiles) {
				grid[pos[0]][pos[1]] = i;
			}
		}
		for (int i=0; i<this.gridSize; i++) {
			for (int j=0; j<this.gridSize; j++) {
				if (grid[i][j]==0) {
					System.out.print(" ");
				}
				else System.out.print(Integer.toString(grid[i][j]));
			}
			System.out.println();
		}
	}

}

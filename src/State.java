import java.io.*;
import java.util.*;


public class State {

	@SuppressWarnings("serial")
	class Intersection extends Exception{
		Intersection(){
			System.out.println("Le fichier donné en entrée est incorrect, il y a intersection des véhicules.");
		}
	}

	/*grid and vehicules. The first one in the list is the one
	 *we have to take out of the grid.*/
	int gridSize;
	int num;
	LinkedList<Vehicule> vehicules;


	public State(File f) {

		//reads the file and checks for intersections
		LinkedList<Vehicule> vehicules = new LinkedList<Vehicule>();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(f.getAbsolutePath()));
			String size = br.readLine();
			String num = br.readLine();
			String vehicule;
			while ((vehicule = br.readLine()) != null) {
				append(vehicules, vehicule);
			}
			this.vehicules = vehicules;
			this.gridSize = Integer.parseInt(size);
			this.num = Integer.parseInt(num);
			/*On ajoute un test d'intersection qui lève
			 *une exception si l'entrée n'est pas correcte.*/
			checkForIntersection();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Intersection i) {
			i.printStackTrace();
		}
	}

	//checks for intersections between vehicules
	private void checkForIntersection() throws Intersection {
		int[][] grid = new int[this.gridSize][this.gridSize];
		for (Vehicule v : this.vehicules) {
			LinkedList<int[]> tiles = v.tiles();
			for (int[]pos :tiles) {
				if (grid[pos[0]][pos[1]]==1) {
					throw (new Intersection());
					//On s'arrete si la case a deja ete remplie.
				}
				grid[pos[0]][pos[1]] = 1;
			}
		}
		//check if a vehicule is off the grid ?
	}

	//appends 'vehicule' to the list 'vehicules'
	void append(LinkedList<Vehicule> vehicules, String vehicule) {
		String[] str = vehicule.split(" ");
		int id = Integer.parseInt(str[0]);
		char ori = str[1].charAt(0);
		int size = Integer.parseInt(str[2]);
		int i = Integer.parseInt(str[3]);
		int j = Integer.parseInt(str[4]);
		vehicules.add(new Vehicule(id, ori, size, i, j));
	}

	//displays the grid
	public void display() {
		int[][] grid = new int[this.gridSize][this.gridSize];

		//fills the grid with zeroes
		for (int i=0; i<this.gridSize; i++) {
			for (int j=0; j<this.gridSize; j++) {
				grid[i][j]=0;
			}
		}

		//for each vehicules, fills the tiles occupied with the vehicule's id.
		for (Vehicule v : this.vehicules) {
			int i = v.id;
			LinkedList<int[]> tiles = v.tiles();
			for (int[]pos :tiles) {
				grid[pos[0]][pos[1]] = i;
			}
		}

		//prints the grid (blank for zeroes, id else)
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

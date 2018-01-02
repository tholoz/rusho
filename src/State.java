import java.io.*;
import java.util.*;


public class State {
	
	@SuppressWarnings("serial")
	class Intersection extends Exception{
		Intersection(){
			System.out.println("Le fichier donné en entrée est incorrect,"
					+ " il y a intersection des véhicules.");
		}
	}
	
	/*grid and vehicules. The first one in the list is the one
	 *we have to take out of the grid.*/
	int gridSize;
	int num;
	Vehicule mainVehicule;
	Vehicule[] vehicules;
	
	public RushHour() {
		
	}
	
	
	public void init (File f) {
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
				if (grid[pos[0]][pos[1]]>0) {
					throw (new Intersection());
					//On s'arrête si la case a déjà été remplie.
				}
				grid[pos[0]][pos[1]] = v.getId();
			}
		}
		
	}


	void process(Vehicule[] vehicules, String vehicule) {
		//ajoute 'vehicule' à la place adéquate
		String[] str = vehicule.split(" ");
		int id = Integer.parseInt(str[0]);
		char ori = str[1].charAt(0);
		int size = Integer.parseInt(str[2]);
		int i = Integer.parseInt(str[3]);
		int j = Integer.parseInt(str[4]);
		vehicules[id-1]=(new Vehicule(id, ori, size, i, j));
		if (id==1) {
			mainVehicule=new Vehicule(id, ori, size, i, j);
		}
	}
	
	
	public int[][] toGrid() {
		/* Renvoie une grille d'entiers décrivant la situation 
		 * des véhicules.
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
		//displays the grid
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
		int [][] res = new int[num][2];
		for (int i=0; i<num; i++) {
			res[i] = vehicules[i].getPos();
		}
		return res;
	}
	
	
	
}

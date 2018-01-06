import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;



public class RushHourTest {

	public static void main(String[] args) {
		//State test1 = new State(new File("D:/Cours/Java/rusho-master/test/GameP10.txt"));
		//test1.display();
		//BruteForceSolver solver = new BruteForceSolver(test1);
		//HeuristicSolver solver = new HeuristicSolver(test1, Heuristic.BlockingVehiclesImproved);
		//solver.solve();
		//solver.displaySolution();
		allGamesTimeTest(10, Heuristic.BlockingVehiclesImproved);
		
	}
	
	public static void timeTest(File f, int times) {
		State test = new State(f);
		double top = System.currentTimeMillis();
		for (int i=1; i<times; i++) {
			BruteForceSolver solver = new BruteForceSolver(test);
			solver.solve();
		}
		BruteForceSolver solver = new BruteForceSolver(test);
		solver.solve();
		solver.displaySolution();
		System.out.println("Processing time for "+ times + " attempts using BruteForceSolver : "+(System.currentTimeMillis()-top)+"ms.\n");
		top = System.currentTimeMillis();
		for (int i=1; i<times; i++) {
			HeuristicSolver hsolver = new HeuristicSolver(test, Heuristic.BlockingVehicles);
			hsolver.solve();
		}
		HeuristicSolver hsolver = new HeuristicSolver(test, Heuristic.BlockingVehicles);
		hsolver.solve();
		hsolver.displaySolution();
		System.out.println("Processing time for "+ times + " attempts using HeuristicSolver : "+(System.currentTimeMillis()-top)+"ms.");
	}
	
	public static void allGamesTimeTest(int times, Heuristic h) {
		try {
			FileWriter dos = new FileWriter(new File(h.toString()+"test.txt"));
			for(int k=0; k<4; k++) {
				for (int j=0; j<10; j++) {
					String fileName = "GameP"+k+j+".txt";
					State test = new State(new File("D:/Cours/Java/rusho-master/test/"+fileName));
					double top = System.currentTimeMillis();
					for (int i=1; i<times; i++) {
						HeuristicSolver hsolver = new HeuristicSolver(test, Heuristic.BlockingVehicles);
						hsolver.solve();
					}
					double time = System.currentTimeMillis()-top;
					dos.write(Integer.toString((int)time));
					dos.write("\n");
				}
			}
			
			dos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}



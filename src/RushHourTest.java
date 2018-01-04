import java.io.File;



public class RushHourTest {

	public static void main(String[] args) {
		//State test1 = new State(new File("D:/Cours/Java/rusho-master/test/GameP10.txt"));
		//test1.display();
		//BruteForceSolver solver = new BruteForceSolver(test1);
		//HeuristicSolver solver = new HeuristicSolver(test1);
		//solver.solve();
		//solver.displaySolution();
		timeTest(new File("D:/Cours/Java/rusho-master/test/GameP30.txt"), 50);
		
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

}



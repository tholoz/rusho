import java.io.File;


public class RushHourTest {

	public static void main(String[] args) {
		State test1 = new State(new File("D:/Cours/Java/rusho-master/test/GameP10.txt"));
		test1.display();
		BruteForceSolver solver = new BruteForceSolver(test1);
		//HeuristicSolver solver = new HeuristicSolver(test1);
		solver.solve();
		solver.displaySolution();
		
	}

}

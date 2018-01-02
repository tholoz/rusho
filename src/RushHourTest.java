import java.io.File;
import java.util.*;

public class RushHourTest {

	public static void main(String[] args) {
		State test1 = new State(new File("C:/Users/Pierre/github/Rusho/test/RushHour2.txt"));
		test1.display();
		BruteForceSolver solver = new BruteForceSolver(test1);
		solver.solve();
		solver.displaySolution();
		
	}

}

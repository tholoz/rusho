import java.io.File;

public class RushHourTest {

	public static void main(String[] args) {
		int[][] grid = new int [2][3];
		System.out.println(grid.toString());
		
		
		State test1 = new State(new File("C:/Users/Pierre/github/Rusho/test/GameP01.txt"));
		test1.display();
	}

}

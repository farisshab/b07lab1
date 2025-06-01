import java.io.File;
import java.io.FileNotFoundException;

public class Driver {
	public static void main(String [] args) {
		double [] a1 = {2, 4, 3};
		int [] b1 = {0, 1, 2};
		double [] a2 = {4, 1, 2, 1};
		int [] b2 = {0, 1, 2, 3};
		Polynomial p = new Polynomial(a1, b1);
		Polynomial q = new Polynomial(a2, b2);
		Polynomial r = p.multiply(q);
		Polynomial z = p.add(q);
		
		System.out.println("TEST #1: Multiplying Polynomials p and q:");
		r.print();
		System.out.println();
		
		System.out.println("TEST #2: Adding Polynomials p and q:");
		z.print();
		System.out.println();
		
		System.out.println("TEST #3: Reading Polynomial from file poly.txt:");
		File file = new File("poly.txt");
		System.out.println("Looking for file at: " + file.getAbsolutePath());
		
		try {
			Polynomial y = new Polynomial(file);
			y.print();
		} catch  (FileNotFoundException e) {
			System.out.println("File not found: " + e.getMessage());
		}
		
		System.out.println();
		
		System.out.println("TEST #4: Writing Polynomial r to file output.txt:");
		r.saveToFile("output.txt");
		System.out.println();
		
		System.out.println("TEST #5: Check that saveToFile works (match the following output to TEST #1):");
		File file2 = new File("output.txt");
		System.out.println("Looking for file at: " + file2.getAbsolutePath());
		try {
			Polynomial x = new Polynomial(file2);
			x.print();
		} catch  (FileNotFoundException e) {
			System.out.println("File not found: " + e.getMessage());
		}
	}
}
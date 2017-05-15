
import java.util.ArrayList;
import java.util.Scanner;

public class NearDuplicates {
	public static void main(String[] args) {
		

		Scanner input = new Scanner(System.in);
		System.out.println("Enter number of Permutations: ");
		int numPermutation = input.nextInt();
		
		System.out.println("Enter number of bands: ");
		int bands = input.nextInt();
		
		System.out.println("Enter smilarity: ");
		float similarity =input.nextFloat();
		
		System.out.println("Enter direcory like this format C:\\Users\\pa2 : ");
		String folder = input.next();

		System.out.println("Enter name of file: ");
		String fileName = input.next();
		
		input.close();
		
		System.out.println(mYNearDuplicates(folder, numPermutation, bands, similarity, fileName));
	}

	public static ArrayList mYNearDuplicates(String folder, int numPermutation, int bands, float similarity,
			String fileName) {

		MinHash mh = new MinHash(folder, numPermutation);
		String[] docName = mh.allDocs();
		int[][] minHashMatrix = mh.minHashMatrix();
		LSH.LSH(minHashMatrix, docName, bands);
		String[] ND = LSH.nearDuplicatesOf(fileName);
		System.out.println("Near Documents: " + ND.length);
		ArrayList ReturnArray = new ArrayList();
		for (int i = 0; i < ND.length; i++) {

			// System.out.println(mh.approximateJaccard(fileName, ND[i]));
			if (!(mh.exactJaccard(fileName, ND[i]) < similarity)) {
				ReturnArray.add(ND[i]);
			}
		}
		System.out.println("FalsePositive=" + Integer.toString(ND.length - ReturnArray.size()) + " Out of "  + ND.length);
		return ReturnArray; 
	}
}

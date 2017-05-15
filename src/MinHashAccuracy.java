
public class MinHashAccuracy {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String folder = "C:\\Users\\hbagheri\\space";
		int numPermutations = 400;
		int counter = 0;
		float e = 0.04f; // error epsilon
		MinHash mh = new MinHash(folder, numPermutations);

		int numdoc = mh.allDocs().length;
		String[] Docs = new String[numdoc];
		Docs = mh.Docs;

		for (int i = 0; i < numdoc; i++)
			for (int j = i + 1; j < numdoc; j++) {
				// System.out.println(Math.abs(mh.exactJaccard(Docs[i],Docs[j])-mh.approximateJaccard(Docs[i],Docs[j])));
				if (Math.abs(mh.exactJaccard(Docs[i], Docs[j]) - mh.approximateJaccard(Docs[i], Docs[j])) > e) {
					counter++;
				}

			}

		System.out.println("Counter : " + counter);

	}
}

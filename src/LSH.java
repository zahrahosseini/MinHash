
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

///THIS CLASS NEED TO CHECK 
public class LSH {
	// HashTable variable related to each bands.
	public static Hashtable<Integer, Set<String>>[] hashTable;

	// get method for HashTable
	public static Hashtable<Integer, Set<String>>[] getHashTable() {
		return hashTable;
	}

	// Set method for HashTable
	public static void setHashTable(Hashtable<Integer, Set<String>>[] hashTable) {
		LSH.hashTable = hashTable;
	}

	// For TEst!
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] docName = new String[10];
		// Sample Name for Doc
		for (int i = 0; i < 10; i++)
			docName[i] = "Doc" + Integer.toString(i);
		// Sample of minHashMatrix
		int[][] minHashMatrix = new int[10][10];
		for (int j = 0; j < 10; j++) {
			for (int i = 0; i < 10; i++) {
				if ((i + j) % 2 != 0)
					minHashMatrix[i][j] = 1;
				else
					minHashMatrix[i][j] = 0;

			}

		}
		// Print minHashMatrix
		for (int j = 0; j < 10; j++) {
			for (int i = 0; i < 10; i++) {
				System.out.print(minHashMatrix[i][j] + " ");
			}
			System.out.print("\n");
		}
		// Call LSH
		LSH(minHashMatrix, docName, 5);
		// Call nearDuplicatesOf
		nearDuplicatesOf("Doc1");
	}

	public static int DocID;
	public static int DocNo;

	public static int getDocNo() {
		return DocNo;
	}

	public static void setDocNo(int docNo) {
		DocNo = docNo;
	}

	// Take name of Document and based on HashTable return an array list of
	// names of the near duplicate Documents
	public static int getDocID() {
		return DocID;
	}

	public static void setDocID(int docID) {
		DocID = docID;
	}

	private static int docID(String fileName) {
		int docId = -1;
		for (int i = 0; i < docName.length; i++) {
			if (fileName.equals(docName[i])) {
				docId = i;
				// break;
			}
		}
		return docId;
	}

	public static String[] nearDuplicatesOf(String docName) {
		// Return set of Similar Documents
		Set<String> SimilarSet = new HashSet<String>();
		// For All of Hashtables
		////
		int mrows=rows;
		
			// For all of rows in the minHashMatrix
			for (int i = 0; i < mrows; i = i + r) {
				// Bj is the bucket, in this step we try to get bucket from
				// minhashmatrix
				String Bj = "";
				int j= docID(docName);
				//System.out.println("***"+j+"&&&"+i);
				// if this is not the last bucket
				if (i + r  < rows) {
					for (int x = i; x < i + r; x++) {
						Bj += Integer.toString(minHashMatrix[x][j]*x);
					}

				}

				// if this is the regular bucket
				else
				{
					for (int x = i; x < rows; x++) {
						Bj += Integer.toString(minHashMatrix[x][j]*x);
					}
						
					}
				// For TEst
				//System.out.println("i= " + i + "j= " + j + "**" + Bj);
				// Create a set for adding new Document.
				Set<String> myset = new HashSet<String>();
				// For TEst
				FNVHash f = new FNVHash();
				if (SimilarSet.isEmpty())
						SimilarSet.addAll(hTable[(i / r)].get((FNVHash.H_function((i / r), Bj)) % DocNo));
					else {
						// Else we need to add all of new Hashtable[i]
						SimilarSet.addAll(hTable[(i / r)].get((FNVHash.H_function((i / r), Bj)) % DocNo));
					}
		}
		// Remove Itself
		SimilarSet.remove(docName);
		// For TEST!
		//System.out.println("****");
		//System.out.println(SimilarSet);
		// Return SimilarSet
		return (String[]) SimilarSet.toArray(new String[SimilarSet.size()]);

	}

	public static int rows = 0;
	public static int r = 0;
	public static int[][] minHashMatrix;

	public static int[][] getMinHashMatrix() {
		return minHashMatrix;
	}

	public static void setMinHashMatrix(int[][] minHashMatrix) {
		LSH.minHashMatrix = minHashMatrix;
	}

	public static int getR() {
		return r;
	}

	public static void setR(int r) {
		LSH.r = r;
	}

	public static int getRows() {
		return rows;
	}

	public static void setRows(int rows) {
		LSH.rows = rows;
	}

	public static Hashtable<Integer, Set<String>>[] hTable;

	public static Hashtable<Integer, Set<String>>[] gethTable() {
		return hTable;
	}

	public static void sethTable(Hashtable<Integer, Set<String>>[] hTable) {
		LSH.hTable = hTable;
	}

	public static String[] docName;

	public static String[] getDocName() {
		return docName;
	}

	public static void setDocName(String[] docName) {
		LSH.docName = docName;
	}

	public static void LSH(int[][] minHashMatrix, String[] docName, int bands) {
		setMinHashMatrix(minHashMatrix);
		setDocName(docName);
		@SuppressWarnings("unchecked")
		Hashtable<Integer, Set<String>>[] hTable = new Hashtable[bands];
		// Create band HAshTable based
		for (int index = 0; index < bands; index++) {
			hTable[index] = new Hashtable<Integer, Set<String>>();

		}
		// number of Doc
		int DocNo = docName.length;
		setDocNo(DocNo);
		// number of row in minHashMatrix
		int rows = minHashMatrix.length;
		// number of cols in minHashMatrix
		setRows(rows);
		int cols = minHashMatrix[0].length;
		// r is the value of row/band
		int r = rows / bands;
		// if rows % bands != 0 we need to add r
		if (rows % bands != 0)
			r++;
		// For Test
		// System.out.println("r=" + r);

		// System.out.println("row="+rows+"col="+cols);
		// For all of Column in the minHashMatrix
		setR(r);
		for (int j = 0; j < cols; j++) {
			// For all of rows in the minHashMatrix
			for (int i = 0; i < rows; i = i + r) {
				// Bj is the bucket, in this step we try to get bucket from
				// minhashmatrix
				String Bj = "";
				// System.out.println("***"+j+"&&&"+i);
				// if this is not the last bucket
				if (i + r < rows) {
					for (int x = i; x < i + r; x++) {
						Bj += Integer.toString(minHashMatrix[x][j] * x);
					}

				}

				// if this is the regular bucket
				else {
					for (int x = i; x < rows; x++) {
						Bj += Integer.toString(minHashMatrix[x][j] * x);
					}
				}
				// For TEst
				// System.out.println("i= " + i + "j= " + j + "**" + Bj);
				// Create a set for adding new Document.
				Set<String> myset = new HashSet<String>();
				// For TEst
				FNVHash f = new FNVHash();
				// System.out.println("&&" +Bj+"^^^"+ f.H_function((i / r), Bj)
				// + "XXX"
				// + (FNVHash.H_function((i / r), Bj)) % DocNo + "index=" +
				// Integer.toString((i / r)));
				// // If we have something in the specific index of hashtable
				if (hTable[(i / r)].get((FNVHash.H_function((i / r), Bj)) % DocNo) != null)
					// Add all of them to myset
					myset.addAll(hTable[(i / r)].get((FNVHash.H_function((i / r), Bj)) % DocNo));
				// myset always add new documents
				myset.add(docName[j]);
				// put myset to the correct place in hash table
				hTable[(i / r)].put((FNVHash.H_function((i / r), Bj)) % DocNo, myset);
				// For Test
				// System.out.println("DocNo="+DocNo);
				// if (((FNVHash.H_function((i / r), Bj)) % DocNo)>DocNo)
				// System.out.println("MORE"+"i="+FNVHash.H_function((i / r),
				// Bj));
				// System.out.println("HASH Ind" + Integer.toString((i / r)) +
				// "put"+(FNVHash.H_function((i / r), Bj)) % DocNo+"i=" + i +
				// "r=" + r);

			}
		}
		// For Test
//		 System.out.println("&&&");
//		 for (int index = 0; index < bands; index++) {
//		 System.out.println("index= " + index + "**" + hTable[index]);
//		 }
		// Set the global HashTable for using in nearDuplicateof
		sethTable(hTable);
	}
}

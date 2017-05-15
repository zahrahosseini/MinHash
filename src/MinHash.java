

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class MinHash {

	// list of all items
	public HashMap<String, Integer> hashTerms = new HashMap<String, Integer>();
	public String[] Docs;

	public int[][] minHash;

	public int[][] TermMatrix;

	// Binary vector for docs impelmented by HashMap
	public ArrayList<HashMap<Integer, Integer>> allDocsMap = new ArrayList<HashMap<Integer, Integer>>();

	// number of permutation functions
	public static int numPermutations;
	// Permutations matrix
	public int[][] permutation;
	// Directory path here
	public static String pathDoc;

	public static void main(String[] args) {		
		
		Scanner input = new Scanner(System.in);
		System.out.println("Enter direcory like this format C:\\Users\\pa2 : ");
		String path = input.next();
		
		//String path = "C:\\Users\\hbagheri\\space";
		numPermutations = 400;
		MinHash m= new MinHash(path, numPermutations) ;
		// System.out.println(m.approximateJaccard("space-1.txt",
		// "space-2.txt"));
		// m.allDocs();
		// m.CreatePermutation();
		// m.CreateTermMatrix(pathDoc);

		// m.PrintTermMatrix();
		// m.InsertDocTerms("space-0.txt");

		 System.out.println("Numbe of terms: " + m.numTerms());
		 System.out.println("number of Docs: "+ m.allDocs().length);

	}

	public MinHash(String folder, int numPermutations) {

		int[] perm;
		pathDoc = folder;
		MinHash.numPermutations = numPermutations;
		allDocs();

		TermMatrix = new int[Docs.length][hashTerms.size()];
		CreateTermMatrix();
		permutation = new int[numPermutations][hashTerms.size()];

		for (int p = 0; p < numPermutations; p++) {
			perm = CreatePermutation();
			permutation[p] = perm;
		}
		initializeMinHash(numPermutations, Docs.length);

		// Create MinHash Matrix based on the TermMatrix and Permutation matrix
		int[] binVec;
		for (int r = 0; r < hashTerms.size(); r++) {
			for (int c = 0; c < Docs.length; c++) {
				binVec = TermMatrix[c];
				if (binVec[r] == 1) {
					for (int h = 0; h < numPermutations; h++) {
						if (permutation[h][r] < minHash[h][c])
							minHash[h][c] = permutation[h][r];
					}
				}
			}

		}

	}

	public String[] allDocs() {
		String files;
		File folder = new File(pathDoc);
		File[] listOfFiles = folder.listFiles();

		// System.out.println(listOfFiles + " -" + pathDoc );

		Docs = new String[listOfFiles.length];

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				files = listOfFiles[i].getName();
				// Copy name of the file to the Docs array
				Docs[i] = files;
				InsertDocTerms(files);
			}
		}

		return Docs;
	}

	public float exactJaccard(String file1, String file2) {
		int termNo = numTerms();
		int sig1[], sig2[] = new int[termNo];
		int intersection = 0, uniondocs = 0;
		// sig1 = ReturnBinaryVector(file1);
		// sig2 = ReturnBinaryVector(file2);
		sig1 = TermMatrix[docID(file1)];
		sig2 = TermMatrix[docID(file2)];

		for (int i = 0; i < termNo; i++) {
			if (sig1[i] == 1 && sig2[i] == 1)
				intersection++;

			if ((sig1[i] == 1) || (sig2[i] == 1))
				uniondocs++;
		}
		if (uniondocs != 0)
			return (float) intersection / uniondocs;
		else
			return 0;
	}

	// Returns the MinHash the minhash signature of the document
	// named fileName, which is an array of integers.
	public int[] minHashSig(String fileName) {
		int[] sig = new int[numPermutations];
		int docID = docID(fileName);
		for (int i = 0; i < numPermutations(); i++) {
			sig[i] = minHash[i][docID];
		}
		return sig;
	}

	// returns the index of the fileName
	private int docID(String fileName) {
		int docId = -1;
		for (int i = 0; i < Docs.length; i++) {
			if (fileName.equals(Docs[i])) {
				docId = i;
				// break;
			}
		}
		return docId;
	}

	// Estimates and returns the Jaccard similarity of
	// documents file1 and file2 by comparing the MinHash signatures of file1
	// and file2.
	public float approximateJaccard(String file1, String file2) {

		int sig1[], sig2[] = new int[numPermutations];
		int counter = 0;
		sig1 = minHashSig(file1);
		sig2 = minHashSig(file2);

		for (int i = 0; i < numPermutations; i++)
			if (sig1[i] == sig2[i])
				counter++;

		return (float) counter / numPermutations;

	}

	// Returns the MinHash Matrix of the collection.

	public int[][] minHashMatrix() {
		return minHash;

	}

	// Returns the number of terms in the document collection.
	public int numTerms() {

		return hashTerms.size();

	}

	// Returns the number of permutations used to construct the MinHash matrix.
	public int numPermutations() {
		return numPermutations;

	}

	// Initialize MinHash Matrix with the Max Int value
	private void initializeMinHash(int numHashFunctions, int numDocs) {
		minHash = new int[numHashFunctions][numDocs];
		for (int i = 0; i < numHashFunctions; i++) {
			for (int j = 0; j < numDocs; j++) {
				minHash[i][j] = Integer.MAX_VALUE;
			}
		}

	}

	// Create M*N Matrix M=all terms and N= Docs numbers
	public void CreateTermMatrix() {
		HashMap<Integer, Integer> docMap = new HashMap<Integer, Integer>();
		// int[] binVec= new int[hashTerms.size()];

		for (int i = 0; i < Docs.length; i++) {

			// binVec=
			// binVec = BinaryVector(Docs[i]);
			// allDocsMap.add(i, docMap);
			TermMatrix[i] = BinaryVector(Docs[i]);
		}

	}

	//
	private void InsertDocTerms(String FileName) {
		String line;
		String[] words;

		try {
			BufferedReader br = new BufferedReader(new FileReader(pathDoc + "\\" + FileName));

			while ((line = br.readLine()) != null) {
				words = line.split("[.,;': ]");
				for (int i = 0; i < words.length; i++) {
					if (words[i].length() > 2 && !words[i].toLowerCase().equals("the"))
						addWord2Hash(words[i]);
				}
			}
			br.close();

		} catch (IOException e) {
			System.err.println(e.getMessage());
		}

	}

	// Create Binary Vector for a specific file
	private int[] BinaryVector(String FileName) {
		String line;
		String[] words;
		int[] binVec = new int[hashTerms.size()];

		// Set binary vectors to zeros
		for (int i = 0; i < hashTerms.size(); i++)
			// docMap.put(i, 0);
			binVec[i] = 0;

		try {

			BufferedReader br = new BufferedReader(new FileReader(pathDoc + "\\" + FileName));
			// line = br.readLine();
			// read line while end of file
			while ((line = br.readLine()) != null) {
				words = line.split("[.,;': ]");
				for (int i = 0; i < words.length; i++) {
					// put the <word[i],1> to BinaryVector
					if ((words[i].length() > 2) && (!words[i].toLowerCase().equals("the")))
						binVec[hashTerms.get(words[i].toLowerCase())] = 1;
				}
			}
			br.close();

		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		return binVec;
	}

	// Add every word to HashMap if it is not added before
	private void addWord2Hash(String word) {
		int wordCounter;
		wordCounter = hashTerms.size();
		// int i= hashTerms.get(word);
		if (!hashTerms.containsKey(word.toLowerCase())) {
			hashTerms.put(word.toLowerCase(), wordCounter);
		}

	}

	private int[] CreatePermutation() {
		List<Integer> permList = new ArrayList<>();
		int[] perm = new int[hashTerms.size()];

		for (int i = 0; i < hashTerms.size(); i++) {
			permList.add(i);
		}
		Collections.shuffle(permList);
		for (int i = 0; i < hashTerms.size(); i++) {
			perm[i] = permList.get(i).intValue();
		}
		return perm;
	}

}

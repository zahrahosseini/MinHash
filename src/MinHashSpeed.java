import java.util.Scanner;

public class MinHashSpeed {

	public static void main(String[] args) {

		Scanner input = new Scanner(System.in);
		System.out.println("Enter direcory like this format C:\\Users\\pa2 : ");
		String folder = input.next();
		
		
		int numPermutations = 400;
		calcApproxJacTime(folder, numPermutations);
		input.close();
	}
	
	
	public static void calcExactJacTime(String folder, int numPermutations){
        
		long startTime = System.currentTimeMillis();		
		
		MinHash mh= new MinHash(folder, numPermutations);		
		
		int numdoc=mh.allDocs().length;
		String[] Docs= new String[numdoc];
		Docs=mh.Docs;			
				
		for (int i=0;i<numdoc;i++)
			for (int j=i+1;j<numdoc;j++){
				mh.exactJaccard(Docs[i],Docs[j]);						     
				
			}
		
		long endTime   = System.currentTimeMillis();
		long totalTime = (endTime - startTime)/1000;
		System.out.println("Total Time for Exact Jacard Similarity is:"+totalTime+"   Seconds");
		
	}

public static void calcApproxJacTime(String folder, int numPermutations){
        
		long startTime = System.currentTimeMillis();		
		
		MinHash mh= new MinHash(folder, numPermutations);		
		
		int numdoc=mh.allDocs().length;
		String[] Docs= new String[numdoc];
		Docs=mh.Docs;			
				
		for (int i=0;i<numdoc;i++)
			for (int j=i+1;j<numdoc;j++){
				mh.approximateJaccard(Docs[i],Docs[j]);						     
				
			}
		
		long endTime   = System.currentTimeMillis();
		long totalTime = (endTime - startTime)/1000;
		System.out.println("Total Time for Approximate Jacard Similarity is:"+totalTime+"   Seconds");
	}

}

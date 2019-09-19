import java.io.*;
import java.util.*;


public class Analyzer {
	public static void main(String[] args) throws Exception {
		if (args.length <= 0) {
			System.out.println("usage: java Analyzer filename [n-gram]");
			return;
		}
		String filename = args[0];

		System.out.println("reading file...");
		String content = readFile(filename);
	
		System.out.println("total number of letters = "+numLetters(content));
		System.out.println("index of Coincidence = "+indexOfCoincidence(content));
		System.out.println("frequency counting...");
		charFreq(content);
		
		if (args.length > 1) {
			int ngram = Integer.parseInt(args[1]);

			String trimmed = trimWhitespaces(content);
			// String trimmed = content;

			for (int i=2; i<=ngram; i++) {
				System.out.println("--- "+i+"-grams counting ------------");
				nGramFreq(trimmed, i);
			}
		}
	}
	
	// read file to string
	private static String readFile(String filename) throws Exception {
		FileInputStream fis = new FileInputStream(filename);
		byte[] buffer = new byte[fis.available()];
		fis.read(buffer);
		fis.close();
		return new String(buffer).toLowerCase();
	}

	// count the number of characters (excluding whitespaces)
	private static int numLetters(String content) {
		int n = 0;
		char ch;
		for (int i=0; i<content.length(); i++) {
			ch = content.charAt(i);
			if (Character.isLetter(ch))
				n++;
		}
		return n;
	} /* numLetters */


	// count char
	private static void charFreq(String content) {
		int[] freq = new int[26];
		char ch;
		int total = numLetters(content);
		for (int i=0; i<content.length(); i++) {
			ch = content.charAt(i);
			if (Character.isLetter(ch)) {
				freq[ch-'a']++;
			}
		}
		
		// print occurred char in descending order
		int[] sorted = new int[26];
		System.arraycopy(freq, 0, sorted, 0, 26);
		Arrays.sort(sorted);
		for (int i=sorted.length-1; i>=0 && sorted[i]>0; i--) {
			for (int j=0; j<freq.length; j++) {
				if (freq[j] == sorted[i]) {
					System.out.println(
								Character.toString((char)('a'+j)) + " " +
								sorted[i]+" probability="+
								sorted[i]*1.0f/total);
					freq[j] = -1;
					break;
				}
			}
		}
	}

	// trim white spaces on the given String and return a new string 
	private static String trimWhitespaces(String content) {
		int i, j;
		char[] buffer = new char[numLetters(content)];
		char ch;
		for (i = j = 0; i < content.length(); i++)
		{
			ch = content.charAt(i);
			if (Character.isLetter(ch))
			{
				buffer[j++] = ch;
			}
		}
		return new String(buffer);
	} /* trimWhitespaces */


	// count n-grams
	private static void nGramFreq(String content, int n) {
		Vector countedStr = new Vector();
		int offset = 0;
		char[] buffer = new char[n];

		while (offset < content.length()-n+1) {
			boolean stop = false;
			for (int i=0; i<n; i++) {
				buffer[i] = content.charAt(offset+i);
				if (Character.isWhitespace(buffer[i])) {
					offset += (i+1);
					stop = true;
					break;
				}
			}
			if (stop) continue;
			
			String str = new String(buffer);
			if (!countedStr.contains(str)) {
				int count = countOccur(content, str);
				if (count > 1 || (n==2 && buffer[0]==buffer[1])) {
					// only display multiple occurrence and double char 
				   System.out.print(str + " " + count+" ");
					if (n > 2)
					{
						/* print list of position where the pattern found */
						int fi = 0; /* fromIndex */
						int loc;
						for (loc = content.indexOf(str, fi); loc > 0; fi++)
						{
							/* print the location using convention 1..n */
							/* rather java program array convention 0..n-1 */
							if (fi == 0)
								System.out.print("position="+(loc+1));
							else
								System.out.print(","+(loc+1));
							loc = content.indexOf(str, loc+1);
						}
						System.out.println();
					}
					else /* n == 2 */
					   System.out.println("probability="+
										  count*1.0f/(26*26));
				}
				countedStr.add(str);
			}
			offset++;
		}
	}

	private static int countOccur(String content, String str) {
		int offset = 0;
		int count = 0;
		while (offset < content.length()) {
			int index = content.indexOf(str, offset);
			if (index == -1) break;
			count++;
			offset = index + str.length();
		}
		return count;
	}

	private static double indexOfCoincidence(String content)
	{
		int[] freq = new int[26];
		char ch;
		int total = numLetters(content);
		for (int i=0; i<content.length(); i++) {
			ch = content.charAt(i);
			if (Character.isLetter(ch)) {
				freq[ch-'a']++;
			}
		}
		double ic = 0.0;
		for (int i=0; i < 26; i++)
		{
			if (freq[i] >= 2)
			{
				ic += freq[i]*1.0*(freq[i]-1);
			}
			/* otherwise it is always zero */
		}
		ic /= (total-1)*total;
		return ic;
	}
}

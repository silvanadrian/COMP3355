import java.io.*;
import java.util.*;


public class Kasiski {
	public static void main(String[] args) throws Exception {
		if (args.length <= 0) {
			System.out.println("usage: java Kasiski filename [n-sequence]");
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
			int num_seq = Integer.parseInt(args[1]);
			if (num_seq <= 0)
			{
				System.out.println("n-sequence must be greater than zero");
				System.exit(1);
			}

			String trimmed = trimWhitespaces(content);
			// String trimmed = content;

			for (int i=0; i< num_seq; i++) {
				System.out.println("------ sequence "+(i+1)+"-------------");
				String str_seq = extractSequence(trimmed, i, num_seq);
				
				System.out.println("Cipher:");
				String s = padSequence(trimmed, str_seq, i, num_seq);
				printText(s);
				System.out.println("number of letters = "+numLetters(str_seq));
				System.out.println("index of coincidence = "+
								indexOfCoincidence(str_seq));
				charFreq(str_seq);
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

	public static String extractSequence(String content, int offset, 
										 int separation)
	{
		int num_letters = numLetters(content);
		char[] buffer = new char[(num_letters+separation-1)/separation];
		int i, j;

		for (i = offset, j = 0; i < num_letters; i+= separation, j++)
		{
			buffer[j] = content.charAt(i);
		}
		return new String(buffer);

	} /* extractSequence */

	public static String padSequence(String content, String sequence,
								     int offset, int separation)
	{
		int len = content.length();
		char [] buffer = new char[len];

		for (int i = 0; i < len; i++)
			buffer[i]='-';
		// System.out.println("content.length ="+content.length());
		// System.out.println("sequence.length ="+sequence.length());
		for (int i = 0; i < sequence.length(); i++)
		{
			// System.out.println("offset+i*separation="+(offset+i*separation));
			// System.out.flush();
			if (offset+i*separation < len)
				buffer[offset+i*separation] = sequence.charAt(i);
		}
		return new String(buffer);

	} /* padSequence */

	private static void printText(String s)
	{
		final int COLUMN_SIZE = 5;
		final int NUM_COLUMNS = 10;
		int n_letters, n_columns;

		n_letters = n_columns = 0;
		for (int i = 0; i < s.length(); i++)
		{
			if (n_letters == COLUMN_SIZE)
			{
				System.out.print(" ");
				n_letters = 0;
			}
			if (n_columns == NUM_COLUMNS)
			{
				System.out.println();
				n_columns = 0;
			}
			System.out.print(s.charAt(i));
			n_letters++;
			if (n_letters == COLUMN_SIZE)
				n_columns++;
		}
		System.out.println();
	}
}

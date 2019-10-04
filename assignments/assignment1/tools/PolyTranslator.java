import java.io.*;
import java.util.*;

public class PolyTranslator {
	public static void main(String[] args) throws Exception {
		if (args.length < 2) {
			System.out.println("usage: java PolyTranslator encryptedfile n-sequence map_1 ... map_n");
			System.exit(0);
		}
		String encFile = args[0];
		int num_seq = Integer.parseInt(args[1]);
		if (num_seq <= 0)
		{
			System.out.println("n-sequence must be greater than zero.");
			System.exit(1);
		}
		else if (args.length < 2+num_seq)
		{
			System.out.println("insufficient number of command line arguments");
			System.exit(1);
		}
		System.out.println("num_seq="+num_seq);

		String [] mapFiles = new String[num_seq];
		for (int i = 0; i < num_seq; i++)
		{
			mapFiles[i] = new String(args[i+2]);
			System.out.println("mapfile "+(i+1)+"="+mapFiles[i]);
		}
	
		String ciphertext = readFile(encFile);
		String trimmed = trimWhitespaces(ciphertext);
		char [] buffer = new char[trimmed.length()];

		/* initialize (to check if any ciphertext has not been decrypted) */
		for (int i = 0; i < buffer.length; i++)
			buffer[i] = '-';

		for (int i = 0; i < num_seq; i++)
		{
			FileInputStream fis = new FileInputStream(mapFiles[i]);
			Properties mapping = new Properties();
			mapping.load(fis);
			fis.close();
			translate(trimmed, mapping, i, num_seq, buffer);
			System.out.println("plaintext by mapfile"+(i+1)+" :");
			printText(new String(buffer));
		}
		System.out.println("ciphertext: ");
		printText(trimmed);
		System.out.println("plaintext : ");
		printText(new String(buffer));
	}
	
	private static String readFile(String filename) throws Exception {
		FileInputStream fis = new FileInputStream(filename);
		byte[] buffer = new byte[fis.available()];
		fis.read(buffer);
		fis.close();
		return new String(buffer).toLowerCase();
	}
	
	private static void translate(String ciphertext, Properties mapping,
								  int offset, int separation,
								  char [] buffer)
		throws Exception 
	{
		/*
		int len = ciphertext.length();
		StringBuffer buffer = new StringBuffer(len);
		for (int i=0; i<len; i++) {
			char c = ciphertext.charAt(i);
			String p = mapping.getProperty(String.valueOf(c));
			if (p == null) {
				buffer.append('_');
			} else {
				buffer.append(p);
			}
		}
		return buffer.toString();
		*/

		for (int i = offset; i < ciphertext.length(); i+= separation)
		{
			char c = ciphertext.charAt(i);
			String p = mapping.getProperty(String.valueOf(c));
			if (p != null)
				buffer[i] = p.charAt(0);
		}

	} /* translate */

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

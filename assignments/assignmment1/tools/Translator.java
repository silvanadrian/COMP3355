import java.io.*;
import java.util.*;

public class Translator {
	public static void main(String[] args) throws Exception {
		if (args.length < 2) {
			System.out.println("usage: java Translator encryptedfile mappingfile");
			System.exit(0);
		}
		String encFile = args[0];
		String mapFile = args[1];
		
		String ciphertext = readFile(encFile);
		FileInputStream fis = new FileInputStream(mapFile);
		Properties mapping = new Properties();
		mapping.load(fis);
		fis.close();
		String trimmed = trimWhitespaces(ciphertext);
		String plaintext = translate(trimmed, mapping);
		System.out.println("ciphertext:");
		printText(trimmed);
		System.out.println();
		System.out.println("plaintext :");
		printText(plaintext);
	}
	
	private static String readFile(String filename) throws Exception {
		FileInputStream fis = new FileInputStream(filename);
		byte[] buffer = new byte[fis.available()];
		fis.read(buffer);
		fis.close();
		return new String(buffer).toLowerCase();
	}
	
	private static String translate(String ciphertext, Properties mapping) throws Exception {
		int len = ciphertext.length();
		StringBuffer buffer = new StringBuffer(len);
		for (int i=0; i<len; i++) {
			char c = ciphertext.charAt(i);
			String p = mapping.getProperty(String.valueOf(c));
			if (p == null) {
				buffer.append('-');
			} else {
				buffer.append(p);
			}
		}
		return buffer.toString();
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

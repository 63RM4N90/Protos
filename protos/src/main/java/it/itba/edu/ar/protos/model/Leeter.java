package it.itba.edu.ar.protos.model;

/**
 * This class is used to transform an input text onto l33t format. It changes
 * the a's for 4's, the e's for 3's, the i's for 1's, the o's for 0's and the
 * c's for <'s.
 *
 */
public class Leeter {

/**
	 * This is the only method offered by the Leeter class. It receives a
	 * String input and returns a new one changing the a's for 4's, the e's for
	 * 3's, the i's for 1's, the o's for 0's and the * c's for <'s from the
	 * original.
	 * 
	 * @param text The original text input.
	 * @return
	 * 			A new one changing the a's for 4's, the e's for 3's, the i's 
	 * for 1's, the o's for 0's and the * c's for <'s from the
	 * original.
	 */
	public static String leetify(String text) {
		text = text.replaceAll("e", "3");
		text = text.replaceAll("a", "4");
		text = text.replaceAll("c", "<");
		text = text.replaceAll("i", "1");
		text = text.replaceAll("o", "0");
		return text;
	}
}

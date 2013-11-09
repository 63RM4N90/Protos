package it.itba.edu.ar.protos.model;

public class Leeter {

	public static String leetify(String text) {
		text = text.replaceAll("e", "3");
		text = text.replaceAll("a", "4");
		text = text.replaceAll("c", "<");
		text = text.replaceAll("i", "1");
		text = text.replaceAll("o", "0");
		return text;
	}
}

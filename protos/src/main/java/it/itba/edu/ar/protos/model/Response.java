package it.itba.edu.ar.protos.model;

import java.nio.ByteBuffer;

/**
 * This is a subclass of HttpPacket and it contains all the information related
 * to an HTTP response, such as the status code and the status message of such
 * response.
 */
public class Response extends HttpPacket {
	private String status;
	private String statusMessage;

	@Override
	public boolean parseFirstLine(String first) {
		int spaces = 0;
		int startFrom = 0;

		for (int i = 0; i < first.length(); i++) {
			if (first.charAt(i) == ' ') {
				switch (spaces) {
				case 0:
					setHttpVersion(first.substring(startFrom, i));
					break;
				case 1:
					status = first.substring(startFrom, i);
					break;
				case 2:
					statusMessage = first.substring(startFrom, i);
					break;
				}
				startFrom = i + 1;
				spaces++;
			}
		}
		return validateFirstLine();
	}

	private boolean validateFirstLine() {
		return true;// TODO implement validator
	}

	@Override
	public boolean validateHeader(String[] header) {
		return true;// TODO implement validator
	}

	@Override
	public void generateFirstLine(ByteBuffer packet) {
		// TODO Auto-generated method stub

	}
}

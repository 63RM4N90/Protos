package it.itba.edu.ar.protos.model;

import java.nio.ByteBuffer;

public class Response extends HttpPacket {
	private String status;
	private String statusMessage;
	
	@Override
	public boolean parseFirstLine(String first) {
		int spaces = 0;
		int startFrom = 0;
		System.out.println("first line to parse:");
		System.out.println(first);
		
		for(int i=0 ; i<first.length() ; i++) {
			if(first.charAt(i) == ' ') {
				switch(spaces) {
				case 0:
					setHttpVersion(first.substring(startFrom, i));
					break;
				case 1:
					status = first.substring(startFrom, i);
					statusMessage = first.substring(i+1, first.length()).trim();
					return validateFirstLine();
				}
				startFrom = i+1;
				spaces++;
			}
		}
		return false;	
	}
	
	public boolean validateFirstLine(){
		return true;//TODO implement validator
	}
	
	public boolean validateHeader(String[] header) {
		if(header[0].toLowerCase().contains("content-length")) {
			initializeBody(Integer.parseInt(header[1].trim()));
			System.out.println("length");
			System.out.println(header[1]);
		}
		return true;
	}

	public boolean parseContent(Data data) {
		//TODO y aca que carajo hacemooo
		return false;
	}

	@Override
	public void generateFirstLine(ByteBuffer packet) {
		packet.put(getHttpVersion().trim().getBytes());
		packet.put(" ".getBytes());
		packet.put(status.getBytes());
		packet.put(" ".getBytes());
		System.out.println("status message to get:");
		System.out.println(statusMessage);
		packet.put(statusMessage.getBytes());
		packet.put("\r\n".getBytes());
	}
}

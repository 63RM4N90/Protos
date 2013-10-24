package it.itba.edu.ar.protos.model;

public class Response extends HttpPacket {
	private String status;
	private String statusMessage;
	
	private boolean parseFirstLine(String first) {
		int spaces = 0;
		int startFrom = 0;
		
		for(int i=0 ; i<first.length() ; i++) {
			if(first.charAt(i) == ' ') {
				switch(spaces) {
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
				startFrom = i+1;
				spaces++;
			}
		}
		return validateFirstLine();	
	}
	
	private boolean validateFirstLine(){
		return true;//TODO implement validator
	}
	
	private boolean validateHeader(String[] header) {
		return true;//TODO implement validator
	}

	private boolean parseContent(Data data) {
		//TODO y aca que carajo hacemooo
		return false;
	}
}

package it.itba.edu.ar.protos.model;

public class RequestPacket extends HttpPacket{
	private boolean isHead;
	
	public RequestPacket () {
		super();
		isHead = false;
	}
	public void parsePacket(PacketData data){
		String[] headers = super.initializeParse(data);
		
		String first = headers[0];
		
		incrementByteAmount(first.length() + 2);
		String[] params = first.split(" ");
		addHeader("method", params[0]);
		addHeader("uri", params[1]);
		addHeader("version", params[2]);
		
		parseHeaders(headers);
	
		if( params[0].equals("HEAD") || params[0].equals("GET")){
			hasBody(false);
			if(params[0].equals("HEAD")){	
				isHead = true;
			}
		}
	}
	
	public boolean isHead(){
		return isHead;
	}
}

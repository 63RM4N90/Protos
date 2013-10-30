package it.itba.edu.ar.protos.model;

public class Request extends HttpPacket{
	
	private String method;
	private String uri;
	
	public boolean parseFirstLine(String first) {
		String[] params = first.split(" ");
		if(params.length != 3) {
			return false;
		}
		method = params[0];
		uri = params[1];
		setHttpVersion(params[2]);	
		return validateFirstLine();	
	}
	
	public boolean validateFirstLine(){
		if(!(method.equals("GET") || method.equals("POST") || method.equals("HEAD"))) {
			return false;
		}
		if(!getHttpVersion().startsWith("HTTP/")) {
			return false;
		}
		return true;//TODO implement validator
	}
	
	public boolean parseHeader(String line) {
		if(line.contains(":")){
			String[] header = line.split(":");
			addHeader(header[0].trim(), header[1].trim());
			return validateHeader(header);
		}
		return false;
	}
	
	public boolean validateHeader(String[] header) {
		
		if(header[0].toLowerCase().contains("host")) {
			if(header.length == 3)	{
				port = Integer.parseInt(header[2].trim());
			} else {
				port = 80;
			}
		} else if(header[0].toLowerCase().contains("content-type")) {
			hasBody = true;
		} else if(header.length != 2 ) {
			return false;
		}
		return true;
	}

	public boolean parseContent(Data data) {
		//TODO y aca que carajo hacemooo
		return false;
	}
	
	//esto porhay vuela
	
	public String getMethod(){
		return method;
	}
	
	public void setMethod(String method) {
		this.method = method;
	}
	
	//	public void getUri(){
	//		return uri;
	//	}
	
	public void setUri(String method) {
		this.uri = method;
	}

}

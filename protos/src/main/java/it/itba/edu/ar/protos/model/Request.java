package it.itba.edu.ar.protos.model;

public class Request extends HttpPacket{
	
	private String method;
	private String uri;
	
	private boolean parseFirstLine(String first) {
		String[] params = first.split(" ");
		method = params[0];
		uri = params[1];
		setHttpVersion(params[2]);	
		return validateFirstLine();	
	}
	
	private boolean validateFirstLine(){
		return true;//TODO implement validator
	}
	
	private boolean parseHeader(String line) {
		if(line.contains(":")){
			String[] header = line.split(":");
			addHeader(header[0], header[1]);
			return validateHeader(header);
		} 
		return false;
	}
	
	private boolean validateHeader(String[] header) {
		return true;//TODO implement validator
	}

	private boolean parseContent(Data data) {
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
	
	public String getUri(){
		return uri;
	}
	
	public void setUri(String method) {
		this.uri = method;
	}

}

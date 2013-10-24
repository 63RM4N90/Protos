package it.itba.edu.ar.protos.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class HttpPacket {
	private Map<String, String> headers;
	private boolean hasBody;
	private int byteAmount;
	private Data content;
	private String httpVersion;

	
	public HttpPacket(){
		headers = new HashMap<String,String>();
		byteAmount = 0;
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
		/*should not implement*/
		return true;
	}

	/*
	 * methods used to handle headers
	 */
	
	public void addHeader(String key, String value) {
		headers.put(key, value);
	}
	
	public String getHeader(String headerKey){
		return headers.get(headerKey);
	}
	
	public Set<String> getHeaderNames() {
		return headers.keySet();
	}	
	
	/*
	 * methods used to handle first line stuff
	 */
	
	public String getHttpVersion(){
		return httpVersion;
	}
	
	public void setHttpVersion(String httpVersion) {
		this.httpVersion = httpVersion;
	}
	
	
}

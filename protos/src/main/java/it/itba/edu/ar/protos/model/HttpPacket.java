package it.itba.edu.ar.protos.model;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class HttpPacket {
	private Map<String, String> headers;
	protected boolean hasBody;
	private int byteAmount;
	private Data content;
	private String httpVersion;
	private byte[] body;
	protected int port;

	
	public HttpPacket(){
		headers = new HashMap<String,String>();
		byteAmount = 0;
		hasBody = false;
	}
	
	public boolean parseHeader(String line) {
		if(line.contains(":")){
			String trimmedLine = line.trim();
			String[] header = trimmedLine.split(":");
			addHeader(header[0], header[1]);
			return validateHeader(header);
		} 
		return false;
	}
	
	public boolean validateHeader(String[] header) {
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
	
	public boolean hasBody() {
		return hasBody;
	}

	public boolean parseFirstLine(String operation) {
		/*should not implement*/
		return false;
	}

	public int getPort() {
		return port;
	}

	public int getBodyAmount() {
		String l = headers.get("content-length");
		return Integer.parseInt(l);
	}
	
	public byte[] getBody() {
		return body;
	}
	
	protected void initializeBody(int i) {
		body = new byte[i];
	}

}

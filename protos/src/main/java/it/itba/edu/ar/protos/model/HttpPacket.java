package it.itba.edu.ar.protos.model;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class HttpPacket {
	private Map<String, String> headers;
	protected boolean hasBody;
	private int byteAmount;
	private Data content;
	private String httpVersion;
	private ByteBuffer body;
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
	
	public ByteBuffer getBody() {
		return body;
	}
	
	protected void initializeBody(int i) {
		body = ByteBuffer.allocateDirect(i);
	}

	public ByteBuffer generatePacket(int size) {
		ByteBuffer packet = ByteBuffer.allocate(size);
		generateFirstLine(packet);
		
		Set<String> headers = getHeaderNames();
		for(String h : headers){
			packet.put(h.getBytes());
			packet.put(": ".getBytes());
			packet.put(getHeader(h).getBytes());
			packet.put("\r\n".getBytes());
		}
		packet.put("\r\n".getBytes());
		if(hasBody) {
			packet.put(getBody());
		}
		return packet;
	}
	
	public abstract void generateFirstLine(ByteBuffer packet);
}

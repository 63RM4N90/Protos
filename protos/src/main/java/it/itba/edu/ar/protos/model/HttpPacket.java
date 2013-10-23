package it.itba.edu.ar.protos.model;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public abstract class HttpPacket {
	private Map<String, String> headers;
	private int byteAmount;
	private boolean hasBody;

	
	public HttpPacket(){
		headers = new HashMap<String,String>();
		byteAmount = 0;
		hasBody = true;
	}
	
	public String[] initializeParse(PacketData data){
		CharBuffer cb = Charset.forName("ISO-8859-1").
				decode(ByteBuffer.wrap(data.getContent(), 0, data.getSize()));
		String s = new String(cb.array());
		String headers[] = s.split("\r\n");
		return headers;
	}
	
	public void parsePacket(PacketData data){
		/*should not be implemented*/
	}
	
	public void parseHeaders(String[] lines){
		boolean hasFinished = false;
		for(int i=0 ; i<lines.length ; i++) {
			if(lines[i].isEmpty()){
				hasFinished = true;
				byteAmount += 2;
			}
			String h[] = lines[i].split(":");
			headers.put(h[0], h[1]);
			byteAmount += lines[i].length() + 2;
		}
		if(!hasFinished){
			hasFinished = true;
			byteAmount += 2;
		}
	}
	
	public String getHeader(String headerKey){
		return headers.get(headerKey);
	}
	
	public void incrementByteAmount(int amount){
		byteAmount += amount;
	}
	
	public void addHeader(String key, String value) {
		headers.put(key, value);
	}

	public void hasBody(boolean status){
		hasBody = status; 
	}
	
	public int getBytesRead(){
		return byteAmount;
	}
	
	public boolean hasBody(){
		return hasBody;
	}
	
	public Map<String,String> getHeaders(){
		return headers;
	}
}

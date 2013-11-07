package it.itba.edu.ar.protos.model;

import java.nio.ByteBuffer;
import java.util.LinkedHashMap;
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

	public HttpPacket() {
		headers = new LinkedHashMap<String, String>();
		byteAmount = 0;
		hasBody = false;
	}

	public boolean parseHeader(String line) {
		String headerAndValue[] = line.split(":", 2);
		headers.put(headerAndValue[0].toLowerCase(), headerAndValue[1].trim());
		return true;
	}

	public boolean validateHeader(String[] header) {
		/* should not implement */
		return true;
	}

	/*
	 * methods used to handle headers
	 */

	public String getHeader(String headerKey) {
		String key = headerKey.toLowerCase();
		return headers.get(key);
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public Set<String> getHeaderNames() {
		return headers.keySet();
	}

	/*
	 * methods used to handle first line stuff
	 */

	public String getHttpVersion() {
		return httpVersion;
	}

	public void setHttpVersion(String httpVersion) {
		this.httpVersion = httpVersion;
	}

	public boolean hasBody() {
		return hasBody;
	}

	public boolean parseFirstLine(String operation) {
		/* should not implement */
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
		for (String h : headers) {
			packet.put(h.getBytes());
			packet.put(":".getBytes());
			
			packet.put(getHeader(h).getBytes());
			packet.put("\r\n".getBytes());
		}
		if (hasBody) {
			packet.put(getBody());
		}
		return packet;
	}

	public abstract void generateFirstLine(ByteBuffer packet);

	public void printHeaderMap() {
		for (Map.Entry<String, String> entry : headers.entrySet()) {
			System.out.println(entry.getKey() + ":" + entry.getValue());
		}
	}
}

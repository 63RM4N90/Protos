package it.itba.edu.ar.protos.model;

import java.nio.ByteBuffer;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * This is an abstract class that represents an HTTP packet, both request and
 * response. The subclasses implement more specific methods.
 * 
 */
public abstract class HttpPacket {
	private Map<String, String> headers;
	private String httpVersion;
	private ByteBuffer body;
	protected int port;

	public HttpPacket() {
		headers = new LinkedHashMap<String, String>();
	}

	public boolean parseHeader(String line) {
		String headerAndValue[] = line.split(":", 2);
		if (validateHeader(headerAndValue)) {
			headers.put(headerAndValue[0].toLowerCase(),
					headerAndValue[1].trim());
			return true;
		}
		return false;
	}

	public abstract boolean validateHeader(String[] header);

	/*
	 * methods used to handle headers
	 */

	public String getHeader(String headerKey) {
		return headers.get(headerKey.toLowerCase());
	}

	public void addBody(byte b) {
		body.put(b);
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
		return headers.containsKey("content-length");
	}

	public abstract boolean parseFirstLine(String operation);

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
		packet.put("\r\n".getBytes());
		if (hasBody()) {
			packet.put(getBody());
		}
		return packet;
	}

	public abstract void generateFirstLine(ByteBuffer packet);

	public void printHeaderMap() {
		for (Map.Entry<String, String> entry : headers.entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}
	}
}

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
		if (line.contains(":")) {
			String trimmedLine = line.trim();
			String[] header = trimmedLine.split(":");
			if (line.contains("Cookie")) {
				String content = "";
				for (int i = 1; i < header.length; i++) {
					if (i != header.length - 1) {
						content += header[i] + ":";
					} else {
						content += header[i];
					}
				}
				addHeader(header[0], content);
			} else {
				//TODO: Posible .trim() en header[1]
				addHeader(header[0], header[1]);
			}
			return validateHeader(header);
		}
		return false;
	}

	public boolean validateHeader(String[] header) {
		/* should not implement */
		return true;
	}

	/*
	 * methods used to handle headers
	 */

	public void addHeader(String key, String value) {
		headers.put(key, value);
	}

	public String getHeader(String headerKey) {
		return headers.get(headerKey);
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
		System.out.println("entre");
		ByteBuffer packet = ByteBuffer.allocate(size);
		generateFirstLine(packet);

		Set<String> headers = getHeaderNames();
		for (String h : headers) {
			packet.put(h.getBytes());
			packet.put(": ".getBytes());
			packet.put(getHeader(h).getBytes());
			packet.put("\r\n".getBytes());
		}
		System.out.println("pase");
		packet.put("\r\n".getBytes());
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

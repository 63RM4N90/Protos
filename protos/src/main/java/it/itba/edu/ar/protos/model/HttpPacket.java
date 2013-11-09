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

	/**
	 * Parses a header line and stores it into a map of header-value. Returns a
	 * boolean value indicating whether the header could be parsed successfully
	 * or not.
	 * 
	 * @param line
	 *            A String representing a header line.
	 * @return A boolean value indicating whether the header could be parsed
	 *         successfully or not.
	 */
	public boolean parseHeader(String line) {
		String headerAndValue[] = line.split(":", 2);
		if (validateHeader(headerAndValue)) {
			headers.put(headerAndValue[0].toLowerCase(),
					headerAndValue[1].trim());
			return true;
		}
		return false;
	}

	/**
	 * This method determines wether the header is valid or not.
	 * 
	 * @param header
	 *            An array of String objects of dimension two that contains at
	 *            position 0 the header name and at position 1 the header value.
	 * @return a boolean value that determines wether the header is valid or
	 *         not. Because this is an abstract method, it should be implemented
	 *         by the subclasses.
	 */
	public abstract boolean validateHeader(String[] header);

	/**
	 * This method returns a determined value for a headerKey.
	 * 
	 * @param headerKey
	 *            Represents the header name.
	 * @return The headerKey correspondent value.
	 */
	public String getHeader(String headerKey) {
		return headers.get(headerKey.toLowerCase());
	}

	/**
	 * Adds information to the body segment of the HTTP packet.
	 * 
	 * @param b
	 *            The data that will be put into the HTTP body of the packet.
	 */
	public void addBody(byte b) {
		body.put(b);
	}

	/**
	 * A getter that returns a Map containing as key the header names and as
	 * value the header value of such header name.
	 * 
	 * @return A Map containing as key the header names and as value the header
	 *         value of such header name.
	 */
	public Map<String, String> getHeaders() {
		return headers;
	}

	/**
	 * A getter that returns a Set containing the header names.
	 * 
	 * @return A set containing the header names
	 */
	public Set<String> getHeaderNames() {
		return headers.keySet();
	}

	/**
	 * A getter that returns the HTTP version of the packet in String format.
	 * 
	 * @return A String that contains the HTTP version of the packet.
	 */
	public String getHttpVersion() {
		return httpVersion;
	}

	/**
	 * A setter for the HTTP version.
	 * 
	 * @param httpVersion
	 *            The String that contains the HTTP version to be set.
	 */
	public void setHttpVersion(String httpVersion) {
		this.httpVersion = httpVersion;
	}

	/**
	 * This method that that determines if the HTTP packet has a body or not.
	 * This is determined by checking if the "content-length" header is settted.
	 * 
	 * @return A boolean value that determines if the HTTP packet has a body or
	 *         not. This is determined by checking if the "content-length"
	 *         header is settted.
	 */
	public boolean hasBody() {
		return headers.containsKey("content-length");
	}

	/**
	 * This method parse the first line of an HTTP request/response to recover
	 * all the relevant data stored in there.
	 * 
	 * @param operation
	 *            Represents the first line of an HTTP request/response.
	 * @return A boolean value that determines whether the first line is valid
	 *         or not.
	 */
	public abstract boolean parseFirstLine(String operation);

	/**
	 * A getter that returns the port value in which the proxy is running.
	 * 
	 * @return The port value in which the proxy is running.
	 */
	public int getPort() {
		return port;
	}

	/**
	 * A getter that returns the amount of bytes contained in the body.
	 * 
	 * @return The amount of bytes contained in the body.
	 */
	public int getBodyAmount() {
		String l = headers.get("content-length");
		return Integer.parseInt(l);
	}

	/**
	 * This methos is used to allocate the body buffer.
	 * 
	 * @param i
	 *            An int value used to allocate the body buffer.
	 */
	protected void initializeBody(int i) {
		body = ByteBuffer.allocateDirect(i);
	}

	/**
	 * This method returns a buffer with all the data ready to be written onto a
	 * channel.
	 * 
	 * @param size
	 *            The amount of bytes used to allocate the HTTP packet.
	 * @return A ByteBuffer initialized.
	 */
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
			packet.put(body);
		}
		return packet;
	}

	/**
	 * Reconstructs the first line of an HTTP request and writes it down to the
	 * buffer.
	 * 
	 * @param packet
	 *            The buffer in which the first line will be written.
	 */
	public abstract void generateFirstLine(ByteBuffer packet);
}

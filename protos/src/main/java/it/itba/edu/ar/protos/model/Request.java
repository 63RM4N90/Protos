package it.itba.edu.ar.protos.model;

import java.nio.ByteBuffer;

/**
 * This is a subclass of HttpPacket and it contains all the information related
 * to an HTTP request, such as the method and the uri to which is headed the
 * request.
 */
public class Request extends HttpPacket {

	private String method;
	private String uri;

	@Override
	public boolean parseFirstLine(String first) {
		String[] params = first.split(" ");
		if (params.length != 3) {
			return false;
		}
		method = params[0];
		uri = params[1];
		setHttpVersion(params[2]);
		return validateFirstLine();
	}

	private boolean validateFirstLine() {
		if (!(method.equals("GET") || method.equals("POST") || method
				.equals("HEAD"))) {
			return false;
		}
		if (!getHttpVersion().startsWith("HTTP/")) {
			return false;
		}
		return true;// TODO implement validator
	}

	@Override
	public boolean validateHeader(String[] header) {

		if (header[0].toLowerCase().contains("host")) {
			if (header.length == 3) {
				port = Integer.parseInt(header[2].trim());
			} else {
				port = 80;
			}
		} else if (header[0].toLowerCase().contains("content-length")) {
			initializeBody(Integer.parseInt(header[1].trim()));
		} else if (header.length != 2) {
			return false;
		}
		return true;
	}

	/**
	 * This method returns the uri to which is directed the request. It is
	 * retrieved from the Host header of such request.
	 * 
	 * @return The uri to which is directed the request.
	 */
	public String getUri() {
		if (uri.startsWith("/")) {
			if (getHeader("host").startsWith("http://")) {
				return getHeader("host").trim() + uri;
			} else {
				return "http://" + getHeader("host").trim() + uri;
			}
		}
		return uri;
	}

	@Override
	public void generateFirstLine(ByteBuffer packet) {
		packet.put(method.getBytes());
		packet.put(" ".getBytes());
		packet.put(uri.getBytes());
		packet.put(" ".getBytes());
		packet.put(getHttpVersion().trim().getBytes());
		packet.put("\r\n".getBytes());
	}
}

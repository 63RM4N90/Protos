package it.itba.edu.ar.protos.resources;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This is a singleton used to record statistics to monitor the functionality of
 * the proxy server. The amount of accesses mede on a certain date to a specific
 * url, the amount of bytes transfered from a specific url and the status codes
 * got in the past can be recorded with this object.
 * 
 */
public class Statistics {
	private static Statistics instance;
	// Ejemplo: <10/08/1990 15:25:25.226, http://www.google.com>
	private Map<String, String> accesses = new LinkedHashMap<String, String>();

	// Ejemplo: <http://www.google.com, 1237512736512376B>
	private Map<String, Long> transferedBytes = new LinkedHashMap<String, Long>();

	// Ejemplo: <400, 15>
	private Map<String, Long> statusCodes = new LinkedHashMap<String, Long>();

	/**
	 * Returns a map that contains as key a specific url, and as value the
	 * amount of times users accessed such url.
	 * 
	 * @return A map that contains as key a specific url, and as value the
	 *         amount of times users accessed such url.
	 */
	public Map<String, String> getAccesses() {
		return accesses;
	}

	/**
	 * Returns a map that contains as key a specific url, and as value the
	 * amount of bytes transfered by such url.
	 * 
	 * @return A map that contains as key a specific url, and as value the
	 *         amount of bytes transfered by such url.
	 */
	public Map<String, Long> getTransferedBytes() {
		return transferedBytes;
	}

	/**
	 * Returns a map that contains as key the status code, and as value the
	 * amount of times that a response gave such status code.
	 * 
	 * @return A map that contains as key the status code, and as value the
	 *         amount of times that a response gave such status code.
	 */
	public Map<String, Long> getStatusCodes() {
		return statusCodes;
	}

	/**
	 * Returns an instance of a Statistics object. Because this object is
	 * implemented under the singleton pattern, this is the way to obtain an
	 * instance of this class.
	 * 
	 * @return an instance of a Statistics object.
	 */
	public static Statistics getInstance() {
		if (instance == null) {
			instance = new Statistics();
			instance.initializeStatusCodes();
		}
		return instance;
	}

	/**
	 * Increments the amount of times the url parameter has been accessed.
	 * 
	 * @param url
	 *            A String containing the url of the site with which the proxy
	 *            is going to establish a connection.
	 */
	public void addConnection(String url) {
		Date currentDate = new Date();
		String stringFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.S")
				.format(currentDate);
		accesses.put(stringFormat, url);
	}

	/**
	 * Increment by bytes amount the amount of bytes transfered by the url
	 * parameter.
	 * 
	 * @param url
	 *            A String containing the url of the site with which the proxy
	 *            is going to establish a connection.
	 * @param bytes
	 *            the amount of bytes transfered by the url site.
	 */
	public void addTransferedBytes(String url, Long bytes) {
		if (transferedBytes.containsKey(url)) {
			transferedBytes.put(url, transferedBytes.get(url) + bytes);
		} else {
			transferedBytes.put(url, bytes);
		}
	}

	/**
	 * Increments the amount of times the status code parameter has been
	 * returned.
	 * 
	 * @param code
	 *            The status code to be added to the register.
	 */
	public void addStatusCode(String code) {
		statusCodes.put(code, statusCodes.get(code) + 1);
	}

	private void initializeStatusCodes() {
		statusCodes.put("100", new Long(0));
		statusCodes.put("101", new Long(0));
		statusCodes.put("200", new Long(0));
		statusCodes.put("201", new Long(0));
		statusCodes.put("202", new Long(0));
		statusCodes.put("203", new Long(0));
		statusCodes.put("204", new Long(0));
		statusCodes.put("205", new Long(0));
		statusCodes.put("206", new Long(0));
		statusCodes.put("300", new Long(0));
		statusCodes.put("301", new Long(0));
		statusCodes.put("302", new Long(0));
		statusCodes.put("303", new Long(0));
		statusCodes.put("304", new Long(0));
		statusCodes.put("305", new Long(0));
		statusCodes.put("307", new Long(0));
		statusCodes.put("400", new Long(0));
		statusCodes.put("401", new Long(0));
		statusCodes.put("402", new Long(0));
		statusCodes.put("403", new Long(0));
		statusCodes.put("404", new Long(0));
		statusCodes.put("405", new Long(0));
		statusCodes.put("406", new Long(0));
		statusCodes.put("407", new Long(0));
		statusCodes.put("408", new Long(0));
		statusCodes.put("409", new Long(0));
		statusCodes.put("410", new Long(0));
		statusCodes.put("411", new Long(0));
		statusCodes.put("412", new Long(0));
		statusCodes.put("413", new Long(0));
		statusCodes.put("414", new Long(0));
		statusCodes.put("415", new Long(0));
		statusCodes.put("416", new Long(0));
		statusCodes.put("417", new Long(0));
		statusCodes.put("500", new Long(0));
		statusCodes.put("501", new Long(0));
		statusCodes.put("502", new Long(0));
		statusCodes.put("503", new Long(0));
		statusCodes.put("504", new Long(0));
		statusCodes.put("505", new Long(0));
		// Non standar codes next...
		statusCodes.put("428", new Long(0));
		statusCodes.put("429", new Long(0));
		statusCodes.put("431", new Long(0));
		statusCodes.put("511", new Long(0));
	}

	/**
	 * Returns a String containing the data collected by the Statistics
	 * singleton at the time in a Json format.
	 * 
	 * @return A String containing the data collected by the Statistics
	 *         singleton at the time in a Json format.
	 */
	public String getJsonStatistics() {

		StringBuffer html = new StringBuffer("{\n");
		html.append("\t\"accesses\": {\n");

		for (Map.Entry<String, String> entry : accesses.entrySet()) {
			html.append("\t\t\"");
			html.append(entry.getKey());
			html.append("\": ");
			html.append("\"" + entry.getValue() + "\",\n");
		}
		html.setLength(html.length() - 2);
		html.append("\n\t},\n");

		html.append("\t\"transferedBytes\": {\n");
		for (Map.Entry<String, Long> entry : transferedBytes.entrySet()) {
			html.append("\t\t\"");
			html.append(entry.getKey());
			html.append("\": ");
			html.append("\"" + entry.getValue() + "\",\n");
		}
		html.setLength(html.length() - 2);
		html.append("\n\t},\n");

		html.append("\t\"statusCodes\": {\n");
		for (Map.Entry<String, Long> entry : statusCodes.entrySet()) {
			html.append("\t\t\"");
			html.append(entry.getKey());
			html.append("\": ");
			html.append("\"" + entry.getValue() + "\",\n");
		}
		html.setLength(html.length() - 2);
		html.append("\n\t}\n}");

		return html.substring(0);
	}

	/**
	 * Returns a String containing the data collected by the Statistics
	 * singleton at the time in an HTML format.
	 * 
	 * @return A String containing the data collected by the Statistics
	 *         singleton at the time in an HTML format.
	 */
	public String getHTMLStatistics() {
		StringBuffer html = new StringBuffer(
				"<html><body><h3>Proxy Statistics:</h3><div>Total accesses:");

		html.append(accesses.entrySet().size());

		html.append("</div><div>Access per url:<br/>");

		for (Map.Entry<String, String> entry : accesses.entrySet()) {
			html.append(entry.getKey() + " = " + entry.getValue() + "<br/>");
		}

		html.append("</div><div>Bytes:<br/>");

		for (Map.Entry<String, Long> entry : transferedBytes.entrySet()) {
			html.append(entry.getKey() + " = " + entry.getValue() + "<br/>");
		}

		html.append("</div><div>Status Codes:<br/>");

		for (Map.Entry<String, Long> entry : statusCodes.entrySet()) {
			html.append(entry.getKey() + " = " + entry.getValue() + "<br/>");
		}
		html.append("</div></body></html>");
		return html.substring(0);
	}
}

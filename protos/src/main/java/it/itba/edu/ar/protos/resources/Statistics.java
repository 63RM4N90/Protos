package it.itba.edu.ar.protos.resources;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class Statistics {
	private static Statistics instance;
	// Ejemplo: <10/08/1990 15:25:25.226, 192.168.0.1>
	private Map<Date, String> accesses = new LinkedHashMap<Date, String>();

	// Ejemplo: <192.168.0.1, 1237512736512376B>
	private Map<String, Long> transferedBytes = new LinkedHashMap<String, Long>();

	// Ejemplo: <400, 15>
	private Map<String, Long> statusCodes = new LinkedHashMap<String, Long>();

	public Statistics getInstance() {
		if (instance == null) {
			instance = new Statistics();
			instance.initializeStatusCodes();
		}
		return instance;
	}

	public void addConnection(String ip) {
		Date currentDate = new Date();
		accesses.put(currentDate, ip);
	}

	public void addTransferedBytes(String ip, Long bytes) {
		if (transferedBytes.containsKey(ip)) {
			transferedBytes.put(ip, transferedBytes.get(ip) + bytes);
		} else {
			transferedBytes.put(ip, bytes);
		}
	}

	public void addStatusCode(String code) {
		statusCodes.put(code, statusCodes.get(code) + 1);
	}

	// TODO
	// public void store(String filePath) {
	//
	// File file = new File(filePath);
	// String connections = this.accesses.toString();
	// String requests = this.transferedBytes.toString();
	// String responses = this.statusCodes.toString();
	// }

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
}

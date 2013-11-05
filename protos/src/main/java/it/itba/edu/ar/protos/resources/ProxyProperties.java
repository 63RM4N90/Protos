package it.itba.edu.ar.protos.resources;

import java.io.IOException;
import java.util.Properties;

public class ProxyProperties {
	private final String CONFIG_FILE_NAME = "it/itba/edu/ar/protos/resources/proxy.properties";
	private Properties properties = null;

	public ProxyProperties() {
		this.properties = new Properties();
		try {
			properties.load(ProxyProperties.class.getClassLoader()
					.getResourceAsStream(CONFIG_FILE_NAME));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getPortClient() {
		return Integer.parseInt(getAtribute("portclient"));
	}

//	public int getPortAdmin() {
//		return Integer.parseInt(getAtribute("portadmin"));
//	}

	private String getAtribute(String atr) {
		return properties.getProperty(atr);
	}
}

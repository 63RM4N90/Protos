package it.itba.edu.ar.protos.resources;

import java.io.IOException;
import java.util.Properties;

/**
 * The ProxyProperties class is used to recover the proxy settings, such as the
 * port, the admin username and password, and more. Each property is collected
 * from the proxy.properties file, which is editable by any user anytime.
 * 
 */
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

	/**
	 * Returns the port with which the client will establish connection with the
	 * proxy server. Such value is collected from the proxy.properties file,
	 * which is editable by any user anytime.
	 * 
	 * @return The port with which the client will establish connection with the
	 *         proxy server.
	 */
	public int getPortClient() {
		return Integer.parseInt(getAtribute("portclient"));
	}

	// public int getPortAdmin() {
	// return Integer.parseInt(getAtribute("portadmin"));
	// }

	private String getAtribute(String atr) {
		return properties.getProperty(atr);
	}
}

package it.itba.edu.ar.protos.Interfaces;

import it.itba.edu.ar.protos.model.HttpPacket;
import it.itba.edu.ar.protos.model.Data;
import it.itba.edu.ar.protos.model.ProxyHeader;

public interface PacketHandler {
	
	/**
	 *checks if there is data to read
	 */
	public boolean isEmpty();
	
	/**
	 * for a given header key returns the value of that header 
	 */
	public String getHeaderValue(String headerKey);
	
	/**
	 * returns true if all the headers could be read
	 */
	public boolean readHeaders(Data data); //mejorar metodo completeHeaders
	
	/**
	 * checks request/response packet and determines if it's complete or not
	 */
	public void analizePacket(Data data);//checkear que se analiza
	
	public void reset(); // resetea la info del decoder habria que ver si conviene usarlo
		
	/**
	 * parses request/response headers
	 */
	public void parseHeaders(Data data);
	
	/**
	 * returns packet
	 */
	public HttpPacket getPacket();
	
	/**
	 * appends proxy information to request/response
	 */	
	public ProxyHeader appendProxyDataToHeader();
	
	/**
	 * creates the html response when blocking
	 * @param cause: reason to block
	 */
	public Data blockedHtml(String cause);
	
	/**
	 * checks if request/response has Content-length header set
	 */
	public boolean hasBody();
	
	
	
}

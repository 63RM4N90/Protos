package it.itba.edu.ar.protos.handler;



public class ResponseHandler extends PacketHandlerImplementation {

	/**
	 * determines if Content-Type is plain text
	 */
	public boolean isText();
	
	/**
	 * gets transformed text to l33t 
	 */
	public byte[] transform();
	
	/**
	 * sends the response with blocked HTML to the user
	 * @param cause: reason to send this response
	 * @param client: person who will receive the response
	 */
	public void proxyResponse(String cause, OutputStream client);
}

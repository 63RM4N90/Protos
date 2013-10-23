package it.itba.edu.ar.protos.handler;

import it.itba.edu.ar.protos.Interfaces.PacketHandler;
import it.itba.edu.ar.protos.model.HttpPacket;
import it.itba.edu.ar.protos.model.PacketData;
import it.itba.edu.ar.protos.model.ProxyHeader;

public class PacketHandlerImplementation implements PacketHandler{
	
	private boolean empty;
	private HttpPacket packet;
	
	@Override
	public boolean isEmpty() {
		return empty;
	}

	@Override
	public String getHeaderValue(String headerKey) {
		return packet.getHeader(headerKey);
	}

	@Override
	public boolean readHeaders(PacketData data) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void analizePacket(PacketData data) {
		// TODO Auto-generated method stub
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void parseHeaders(PacketData data) {
		packet.parsePacket(data);
	}

	@Override
	public HttpPacket getPacket() {
		return packet;
	}

	@Override
	public ProxyHeader appendProxyDataToHeader() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PacketData blockedHtml(String cause) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasBody() {
		// TODO Auto-generated method stub
		return false;
	}

}

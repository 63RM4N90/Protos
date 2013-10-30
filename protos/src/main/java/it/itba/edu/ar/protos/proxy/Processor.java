package it.itba.edu.ar.protos.proxy;

import it.itba.edu.ar.protos.model.Data;
import it.itba.edu.ar.protos.model.HttpPacket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;



public class Processor {

	private Socket socket;
	private OutputStream clientOS;
	private InputStream clientIS;
//	private UserAgent ua;
	private HttpPacket packet;

	public Processor() {
		packet = new HttpPacket() {
		};
	}

//	public void process(Data data, Socket socket) {
//		this.socket = socket;
//		boolean hasResponse = processRequest(data);
//
//	}
//
//	public boolean processRequest(Data data) throws IOException {
//		clientOS = socket.getOutputStream();
//		clientIS = socket.getInputStream();

		// Si por alguna razón el parseo de headers fallara, hay que lanzar un
		// error 501, por ejemplo...

//		ua = UserAgent.parseUserAgentString(packet.getHeader("User-Agent"));
//		
//		packet.getHeaders();
//	}
}

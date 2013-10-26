package it.itba.edu.ar.protos.handler;

import it.itba.edu.ar.protos.Interfaces.TCPProtocol;
import it.itba.edu.ar.protos.attachment.RequestAttachment;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;



public class TCPRequestHandler implements TCPProtocol  {

	@Override
	public void handleAccept(SelectionKey key) throws IOException {
		SocketChannel clntChan = ((ServerSocketChannel) key.channel()).accept();
		clntChan.register(key.selector(), SelectionKey.OP_READ, new RequestAttachment());	
	}

	@Override
	public void handleRead(SelectionKey key) throws IOException {
		SocketChannel clnChan = (SocketChannel) key.channel();
		RequestAttachment reqAttach = (RequestAttachment) key.attachment();
		final long bytesread = clnChan.read(reqAttach.getBuffer());
		if (bytesread == -1) {
			clnChan.close();
		} else {
			reqAttach.setState(reqAttach.getState().handleRead(clnChan, reqAttach));
		}
		
	}
	
	@Override
	public void handleWrite(SelectionKey key) throws IOException {
		// TODO Auto-generated method stub
		
	}

}

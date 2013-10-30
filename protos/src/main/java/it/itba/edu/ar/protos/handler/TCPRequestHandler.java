package it.itba.edu.ar.protos.handler;

import it.itba.edu.ar.protos.Interfaces.TCPProtocol;
import it.itba.edu.ar.protos.attachment.RequestAttachment;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URL;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;



public class TCPRequestHandler implements TCPProtocol  {

	@Override
	public void handleAccept(SelectionKey key) throws IOException {
		SocketChannel clntChan = ((ServerSocketChannel) key.channel()).accept();
		clntChan.configureBlocking(false);
		clntChan.register(key.selector(), SelectionKey.OP_READ, new RequestAttachment());	
	}

	@Override
	public void handleRead(SelectionKey key) throws IOException {
		SocketChannel clnChan = (SocketChannel) key.channel();
		RequestAttachment reqAttach = (RequestAttachment) key.attachment();
		while(reqAttach.getBuffer().hasRemaining()) {
			long bytesread = clnChan.read(reqAttach.getBuffer());
			if (bytesread == -1) {
				System.out.println("te cierro el channel");
				clnChan.close();
				break;
			} else {
				if(reqAttach.getState() == State.BODY && !reqAttach.getPacket().hasBody()){
					break;
				}
				reqAttach.setState(reqAttach.getState().handleRead(clnChan, reqAttach));
			}
		}
		
		reqAttach.setState(State.INIT);
		key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
	}
	
	@Override
	public void handleWrite(SelectionKey key) throws IOException {
		RequestAttachment reqAttach = (RequestAttachment) key.attachment();
		String url = reqAttach.getPacket().getHeader("Host");
		int port = reqAttach.getPacket().getPort();
		
		SocketChannel sc = SocketChannel.open();
		sc.configureBlocking(false);
		
		sc.register(key.selector(), SelectionKey.OP_READ, reqAttach);
		System.out.println("url");
		System.out.println(url);
		System.out.println("port");
		System.out.println(port);
		sc.connect(new InetSocketAddress(url, port));
		
		while(!sc.finishConnect())
			System.out.println(".");
		
		sc.write(reqAttach.getBuffer());
		
		sc.register(key.selector(), SelectionKey.OP_READ);
		
		
		// TODO Auto-generated method stub
	}

}

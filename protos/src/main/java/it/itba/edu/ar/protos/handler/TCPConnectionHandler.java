package it.itba.edu.ar.protos.handler;

import it.itba.edu.ar.protos.Interfaces.TCPProtocol;
import it.itba.edu.ar.protos.attachment.Attachment;
import it.itba.edu.ar.protos.model.HttpPacket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;



public class TCPConnectionHandler implements TCPProtocol  {

	@Override
	public void handleAccept(SelectionKey key) throws IOException {
		SocketChannel clntChan = ((ServerSocketChannel) key.channel()).accept();
		clntChan.configureBlocking(false);
		clntChan.register(key.selector(), SelectionKey.OP_READ, new Attachment());	
	}

	@Override
	public void handleRead(SelectionKey key) throws IOException {
		SocketChannel clnChan = (SocketChannel) key.channel();
		Attachment attach = (Attachment) key.attachment();
		while(attach.getBuffer().hasRemaining()) {
			long bytesread = clnChan.read(attach.getBuffer());
			if (bytesread == -1) {
				System.out.println("te cierro el channel");
				clnChan.close();
				break;
			} else {
				if(attach.getState() == State.BODY && !attach.getPacket().hasBody()){
					break;
				}
				attach.setState(attach.getState().handleRead(clnChan, attach));
			}
		}
		
		attach.setState(State.INIT);
		key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
	}
	
	@Override
	public void handleWrite(SelectionKey key) throws IOException {
		Attachment attach = (Attachment) key.attachment();
		String url = attach.getPacket().getHeader("Host");
		int port = attach.getPacket().getPort();
		
		SocketChannel sc = SocketChannel.open();
		sc.configureBlocking(false);
		
		sc.register(key.selector(), SelectionKey.OP_READ, attach);
		sc.connect(new InetSocketAddress(url, port));
		
		while(!sc.finishConnect());
		
		sc.write(attach.getPacket().generatePacket(attach.getPacketSize()));
		
		sc.register(key.selector(), SelectionKey.OP_READ, new Attachment());
	}

}

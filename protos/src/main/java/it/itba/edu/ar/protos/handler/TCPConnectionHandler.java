package it.itba.edu.ar.protos.handler;

import it.itba.edu.ar.protos.Interfaces.TCPProtocol;
import it.itba.edu.ar.protos.attachment.Attachment;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
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
		SocketChannel server;
		if((server = attach.getServer()) == null) {
			String url = attach.getPacket().getHeader("Host");
			int port = attach.getPacket().getPort();
			server = SocketChannel.open();
			server.configureBlocking(false);
			server.register(key.selector(), SelectionKey.OP_READ, attach);
			if (!server.connect(new InetSocketAddress(url, port))) {
				while (!server.finishConnect()) {
                    System.out.print(".");
				}
			}
			attach.setServer(server);
		}
		
		SocketChannel sender = attach.getSender();
		SocketChannel receiver = attach.getOposite(sender);
		

		ByteBuffer packet = attach.getPacket().generatePacket(attach.getPacketSize());
		receiver.write(packet);
		receiver.register(key.selector(), SelectionKey.OP_READ, attach); // receiver
        key.interestOps(SelectionKey.OP_READ);
	}

}

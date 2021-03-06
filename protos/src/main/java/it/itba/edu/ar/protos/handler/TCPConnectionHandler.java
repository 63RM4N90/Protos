package it.itba.edu.ar.protos.handler;

import it.itba.edu.ar.protos.Interfaces.TCPProtocol;
import it.itba.edu.ar.protos.attachment.Attachment;
import it.itba.edu.ar.protos.model.HttpPacket;
import it.itba.edu.ar.protos.model.Request;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class TCPConnectionHandler implements TCPProtocol {

	@Override
	public void handleAccept(SelectionKey key) throws IOException {
		SocketChannel clntChan = ((ServerSocketChannel) key.channel()).accept();
		clntChan.configureBlocking(false);
		Attachment attach = new Attachment();
		attach.setClient(clntChan);
		clntChan.register(key.selector(), SelectionKey.OP_READ,attach);
	}

	@Override
	public void handleRead(SelectionKey key) throws IOException {
		SocketChannel sender = (SocketChannel) key.channel();
		Attachment attach = (Attachment) key.attachment();
		long bytesRead;
		
		while ((bytesRead = sender.read(attach.getBuffer())) != 0) {
			if (bytesRead == -1) {
				sender.close();
				break;
			} else {
				while(attach.getBuffer().position()!= 0) {
					attach.setState(attach.getState().handleRead(sender, attach));
				}
			}
		}

		attach.setState(State.INIT);
		key.interestOps(SelectionKey.OP_WRITE | SelectionKey.OP_READ);
		if(sender.isOpen()) {
			sender.register(key.selector(), SelectionKey.OP_WRITE, attach);
		}
	}

	@Override
	public void handleWrite(SelectionKey key) throws IOException {
		SocketChannel sender = (SocketChannel)key.channel();
		Attachment attach = (Attachment) key.attachment();
		HttpPacket packet = attach.getPacket();

		SocketChannel server;
		if((server = attach.getServer()) == null) {
			URL url = new URL(((Request) packet).getUri());
			server = SocketChannel.open();
			server.configureBlocking(false);
			server.register(key.selector(), SelectionKey.OP_READ, attach);
			int port = url.getPort() == -1 ? 80 : url.getPort();
			if (!server.connect(new InetSocketAddress(url.getHost(), port))) {
				while (!server.finishConnect());
			}
			attach.setServer(server);
		}
		System.out.println("attach.getPacketSize");
		System.out.println(attach.getPacketSize());
		ByteBuffer packetBuff = packet.generatePacket(attach.getPacketSize());
		packetBuff.flip();
		SocketChannel receiver = attach.getOpposite(sender);
		receiver.write(packetBuff);
		key.interestOps(SelectionKey.OP_READ);
		receiver.register(key.selector(), SelectionKey.OP_READ, attach);
	}
}

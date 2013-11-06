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
		clntChan.register(key.selector(), SelectionKey.OP_READ,
				new Attachment());
	}

	@Override
	public void handleRead(SelectionKey key) throws IOException {
		SocketChannel clnChan = (SocketChannel) key.channel();
		Attachment attach = (Attachment) key.attachment();
		while (attach.getBuffer().hasRemaining()) {
			long bytesread = clnChan.read(attach.getBuffer());
			if (bytesread == -1) {
				System.out.println("te cierro el channel");
				clnChan.close();
				break;
			} else {
				if (attach.getState() == State.BODY
						&& !attach.getPacket().hasBody()) {
					break;
				}
				attach.setState(attach.getState().handleRead(clnChan, attach));
			}
		}

		attach.setState(State.INIT);
		key.interestOps(SelectionKey.OP_WRITE | SelectionKey.OP_READ);
		clnChan.register(key.selector(), SelectionKey.OP_WRITE, attach);
	}

	@Override
	public void handleWrite(SelectionKey key) throws IOException {
		Attachment attach = (Attachment) key.attachment();
		SocketChannel server;
		HttpPacket packet = attach.getPacket();
		System.out.println("IS NULL = " + (attach.getServer() == null));
		if((server = attach.getServer()) == null) {
			URL url = new URL(((Request) packet).getUri());
			System.out.println("URI = " + url.getHost());
			server = SocketChannel.open();
			server.configureBlocking(false);
			server.register(key.selector(), SelectionKey.OP_READ, attach);
			int port = url.getPort() == -1 ? 80 : url.getPort();
			System.out.println("CHANNEL PORT = " + url.getPort());
			if (!server.connect(new InetSocketAddress(url.getHost(), port))) {
				while (!server.finishConnect()) {
                    System.out.print(".");
				}
			}
			attach.setServer(server);
		}
		
		SocketChannel sender = attach.getSender();
		SocketChannel receiver = attach.getOposite(sender);

		ByteBuffer packetBuff = attach.getPacket().generatePacket(attach.getPacketSize());
		packetBuff.flip();
		System.out.println(new String(packetBuff.array()));
		receiver.write(packetBuff);
		receiver.register(key.selector(), SelectionKey.OP_READ, attach); // receiver
        key.interestOps(SelectionKey.OP_READ);
	}
}

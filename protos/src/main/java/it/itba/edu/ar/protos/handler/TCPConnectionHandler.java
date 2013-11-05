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
		SocketChannel channel = (SocketChannel) key.channel();
		HttpPacket message = attach.getPacket();
		SocketChannel serverchannel;
		System.out.println("IS NULL = " + (attach.getServer() == null));
		if ((serverchannel = attach.getServer()) == null) {
			URL uri = new URL(((Request) message).getUri());
			System.out.println("URI = " + uri.getHost());
			serverchannel = SocketChannel.open();
			serverchannel.configureBlocking(false);
			serverchannel
					.register(key.selector(), SelectionKey.OP_READ, attach);
			int port = uri.getPort() == -1 ? 80 : uri.getPort();
			System.out.println("CHANNEL PORT = " + uri.getPort());
			if (!serverchannel.connect(new InetSocketAddress(uri.getHost(),
					port))) {
				while (!serverchannel.finishConnect()) {
					System.out.print(".");
				}
			}
			attach.setServer(serverchannel);
		}
		// System.out.println("\nASDF");
		// System.out.println("content = "
		// + new String(attach.getPacket()
		// .generatePacket(attach.getPacketSize()).array()));
		SocketChannel receiver = attach.getOppositeChannel(channel);
		ByteBuffer buf = attach.getPacket().generatePacket(
				attach.getPacketSize());
		buf.flip();
		System.out.println(new String(buf.array()));
		receiver.write(buf);
		receiver.register(key.selector(), SelectionKey.OP_READ, attach);
		key.interestOps(SelectionKey.OP_READ);
	}
}

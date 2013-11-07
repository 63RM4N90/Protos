package it.itba.edu.ar.protos.proxy;

import it.itba.edu.ar.protos.Interfaces.TCPProtocol;
import it.itba.edu.ar.protos.handler.TCPConnectionHandler;
import it.itba.edu.ar.protos.resources.ProxyProperties;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;

public class ProxyServer {

	private static final int TIMEOUT = 3000; // Wait timeout (milliseconds)

	public static void main(String[] args) throws IOException {
		// if (args.length != 1) {
		// throw new IllegalArgumentException("Parameter(s): <Port> ...");
		// }
		// String port = args[0];

		Selector selector = Selector.open();
		ServerSocketChannel listnChannel = ServerSocketChannel.open();
		ProxyProperties properties = new ProxyProperties();
		listnChannel.socket().bind(
				new InetSocketAddress(properties.getPortClient()));
		listnChannel.configureBlocking(false);
		listnChannel.register(selector, SelectionKey.OP_ACCEPT);
		TCPProtocol protocol = new TCPConnectionHandler();
		while (true) {
			if (selector.select(TIMEOUT) == 0) {
				System.out.println(".");
				continue;
			}
			Iterator<SelectionKey> keyIter = selector.selectedKeys()
					.iterator();
			while (keyIter.hasNext()) {
				SelectionKey key = keyIter.next();
				if (key.isAcceptable()) {
					System.out.println("\nA");
					protocol.handleAccept(key);
				}
				if (key.isReadable()) {
					System.out.println("\nR");
					protocol.handleRead(key);
				}
				if (key.isValid() && key.isWritable()) {
					System.out.println("\nW");
					protocol.handleWrite(key);
				}
				keyIter.remove();
			}
		}
	}
}

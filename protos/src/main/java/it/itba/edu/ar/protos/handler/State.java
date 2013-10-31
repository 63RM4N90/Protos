package it.itba.edu.ar.protos.handler;

import it.itba.edu.ar.protos.attachment.Attachment;

import java.nio.channels.SocketChannel;

public enum State {
	INIT {
		
		@Override
		protected State handleRead(final SocketChannel channel, final Attachment attach) {
		    State ret = this;
		    attach.getBuffer().flip();
		    while(attach.getBuffer().hasRemaining()) {
		        final byte c = attach.getBuffer().get();
		        if(c == '\n') {
		            final String operation = new String(attach.getLineBuffer(), 0, attach.getLineBufferIndex());
		            attach.setLineBufferIndex(0);
		            ret = State.HEADERS;
		            //System.out.println(operation);
		            attach.determinePacketType(operation);
		            attach.getPacket().parseFirstLine(operation);
		            break;
		        }
		        attach.setElemInLineBuffer(c, attach.getLineBufferIndex());
		        attach.incrementLineBufferIndex(1);
		        
		    }
		    attach.getBuffer().compact();
		    return ret;
		}
	 
	},
	HEADERS {
		
		@Override
		protected State handleRead(SocketChannel channel, Attachment attach) {
			State ret = this;
			attach.getBuffer().flip();
			while (attach.getBuffer().hasRemaining()) {
				final byte c = attach.getBuffer().get();
				if (c == '\n') {
					if (attach.getLineBufferIndex() == 1) {
						ret = State.BODY;
						System.out.println("------");
					} else {
						final String header = new String(attach.getLineBuffer(), 0, attach.getLineBufferIndex() - 1);
			           // System.out.println(header);
						attach.getPacket().parseHeader(header);
					}
					attach.setLineBufferIndex(0);
					break;
				}
				attach.setElemInLineBuffer(c, attach.getLineBufferIndex());
		        attach.incrementLineBufferIndex(1);
			}
			attach.getBuffer().compact();
			return ret;
		}
	},
	BODY {

		@Override
		protected State handleRead(SocketChannel channel, Attachment attach) {
			State ret = this;
			attach.getBuffer().flip();
			final int bytesToRead = attach.getPacket().getBodyAmount();
			int i = 0;
			byte[] body = attach.getPacket().getBody();
			while(bytesToRead > 0 && attach.getBuffer().hasRemaining()) {
				final byte b = attach.getBuffer().get();
				body[i] = b;
				i++;
				System.out.println("--begin-body--");
				System.out.print(b);
				System.out.println("--end-body--");
			}
			ret = State.INIT;
			return ret;

		}
	}
	;
	protected abstract State handleRead(final SocketChannel channel, final Attachment attach);
}

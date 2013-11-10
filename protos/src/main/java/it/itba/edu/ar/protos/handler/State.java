package it.itba.edu.ar.protos.handler;

import it.itba.edu.ar.protos.attachment.Attachment;

import java.nio.ByteBuffer;
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
		            attach.incrementPacketSize(operation.length()+1);
		            attach.setLineBufferIndex(0);
		            ret = State.HEADERS;
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
					if (attach.getLineBufferIndex() == 1 || attach.getLineBufferIndex() == 0) {
						ret = State.BODY;
						attach.incrementPacketSize(2);
					} else {
						final String header = new String(attach.getLineBuffer(), 0, attach.getLineBufferIndex());
						attach.incrementPacketSize(header.length() + 1);
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
			if(!attach.getPacket().hasBody()){
				return State.INIT;
			}
			int bodyBytes = attach.getPacket().getBodyAmount();
			while(attach.getBuffer().hasRemaining()) {
				if(bodyBytes == attach.getPacket().getBody().position()){
					return State.INIT;
				}
				final byte b = attach.getBuffer().get();
				attach.getPacket().addBody(b);
				attach.incrementPacketSize(1);
			}
			return ret;

		}
	}
	;
	protected abstract State handleRead(final SocketChannel channel, final Attachment attach);
}

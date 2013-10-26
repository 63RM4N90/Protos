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
		            final String operacion = new String(attach.getLineBuffer(), 0, attach.getLineBufferIndex());
		            attach.setLineBufferIndex(0);
		            ret = State.HEADERS;
		            System.out.println(operacion);
		            break;
		        }
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
					} else {
						final String operacion = new String(attach.getLineBuffer(), 0,
								attach.getLineBufferIndex());
						System.out.println(operacion);
					}
					attach.setLineBufferIndex(0);
				}
			}
			attach.getBuffer().compact();
			return ret;

		}
	},
	BODY {

		@Override
		protected State handleRead(SocketChannel channel, Attachment attach) {
			return this;

		}
	}
	;
	protected abstract State handleRead(final SocketChannel channel, final Attachment attach);
}
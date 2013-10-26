package it.itba.edu.ar.protos.attachment;

import it.itba.edu.ar.protos.handler.State;

import java.nio.ByteBuffer;

public class Attachment {
	private State state = State.INIT;
	private byte[] lineBuffer;
	private int lineBufferIndex;
	private int bufferSize; 
	private ByteBuffer buffer;
	
	public Attachment() {
		lineBuffer = new byte[1024];
		lineBufferIndex = 0;
		this.bufferSize = bufferSize;
		buffer = ByteBuffer.allocate(1024);
		
	}
	
	public State getState() {
		return state;
	}
	
	public void setState(State state) {
		this.state = state;
	}
	
	public ByteBuffer getBuffer() {
		return buffer;
	}
	
	public void setLineBufferIndex(int i) {
		lineBufferIndex = i;
	}
	
	public byte[] getLineBuffer() {
		return lineBuffer;
	}
	
	public int getLineBufferIndex() {
		return lineBufferIndex;
	}
}

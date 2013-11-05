package it.itba.edu.ar.protos.attachment;

import it.itba.edu.ar.protos.handler.State;
import it.itba.edu.ar.protos.model.HttpPacket;
import it.itba.edu.ar.protos.model.Request;
import it.itba.edu.ar.protos.model.Response;

import java.nio.ByteBuffer;

public class Attachment {
	private State state = State.INIT;
	private byte[] lineBuffer;
	private int lineBufferIndex;
	private ByteBuffer buffer;
	private HttpPacket packet;
	private int packetSize;
	
	private int bufferSize = 1024;
	
	public Attachment() {
		lineBuffer = new byte[bufferSize];
		lineBufferIndex = 0;
		buffer = ByteBuffer.allocate(bufferSize);
	}
	
	public void determinePacketType(String op) {
		if(op.startsWith("GET") || op.startsWith("POST") || op.startsWith("HEAD")) {
			packet = new Request();
		} else {
			packet = new Response();
		}
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

	public void setElemInLineBuffer(byte c, int i) {
		lineBuffer[i] = c;
	}

	public void incrementLineBufferIndex(int i) {
		lineBufferIndex += i;
		if (lineBufferIndex == this.bufferSize) {
			this.bufferSize *= 2;
			byte[] auxBuffer = new byte[this.bufferSize];
			for (int j = 0 ; i < this.lineBufferIndex ; i++) {
				auxBuffer[j] = this.lineBuffer[j];
			}
			this.lineBuffer = auxBuffer;
		}
	}

	public HttpPacket getPacket() {
		return packet;
	}
	
	public int getPacketSize() {
		return packetSize;
	}
	
	public void incrementPacketSize(int amount) {
		packetSize += amount;
	}
}

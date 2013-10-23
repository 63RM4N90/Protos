package it.itba.edu.ar.protos.model;

public class PacketData {
	private byte[] content;
	private int size;
	
	public PacketData(byte[] content, int size){
		this.content = content;
		this.size = size;
	}
	
	public int getSize(){
		return size;
	}
	
	public byte[] getContent(){
		return content;
	}
}

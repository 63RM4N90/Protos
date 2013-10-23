package it.itba.edu.ar.protos.model;

public class ProxyHeader {
	private byte[] header;
	private int size;
	
	public ProxyHeader(byte[] header, int size){
		this.header = header;
		this.size = size;
	}
	
	public int getSize(){
		return size;
	}
	
	public byte[] getContent(){
		return header;
	}
}

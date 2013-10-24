package it.itba.edu.ar.protos.model;

public class Data {
	private byte[] content;
	private int size;
	
	public Data(byte[] content, int size){
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

package it.itba.edu.ar.protos.attachment;

import it.itba.edu.ar.protos.model.Request;

public class RequestAttachment extends Attachment{

	private Request request;
	
	public RequestAttachment() {
		super();
		request = new Request();
	}

}

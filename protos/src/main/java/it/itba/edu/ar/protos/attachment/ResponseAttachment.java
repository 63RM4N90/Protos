package it.itba.edu.ar.protos.attachment;

import it.itba.edu.ar.protos.model.Request;

public class ResponseAttachment extends Attachment{

	private Request request;
	
	public ResponseAttachment() {
		super();
		request = new Request();
	}
}

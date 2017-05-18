package org.komparator.mediator.ws;

import java.util.TimerTask;

import org.komparator.mediator.ws.cli.MediatorClient;
import org.komparator.mediator.ws.cli.MediatorClientException;

public class LifeProof extends TimerTask {
	private MediatorEndpointManager endpointManager;

	String url = null;
	
	public LifeProof(String url) {
		this.url = url;
	}
	
	public void run() {
		if (endpointManager.getwsURL().equals("http://localhost:8071/mediator-ws/endpoint")) {
			try {
				MediatorClient cli = new MediatorClient(url);
				cli.imAlive();
				System.out.println("got in!!!");
			} catch (MediatorClientException e) {
				e.printStackTrace();
			}
			
		}
	}
}

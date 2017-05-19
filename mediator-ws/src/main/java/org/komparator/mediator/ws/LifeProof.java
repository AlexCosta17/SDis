package org.komparator.mediator.ws;

import java.util.Timer;
import java.util.TimerTask;

import org.komparator.mediator.ws.cli.MediatorClient;
import org.komparator.mediator.ws.cli.MediatorClientException;

public class LifeProof extends TimerTask {
	private MediatorEndpointManager endpointManager = null;
	Timer timer1 = new Timer();
	Timer timer2 = new Timer();
	int i = 1;
	
	String wsURL = null;
	
	public LifeProof(String url) {
		this.wsURL = url;
	}
	
	public void run() {
		try {
			if (endpointManager.getwsURL().equals("http://localhost:8071/mediator-ws/endpoint")) {
				System.out.println("Primary");
				MediatorClient cli = new MediatorClient("http://localhost:8072/mediator-ws/endpoint");
				imAlivePrim(cli, 5);
			}
			else {
				
			}
		} catch (MediatorClientException e) {
			e.printStackTrace();
		}
	}
	
	public void imAlivePrim(MediatorClient cli, int seconds) {
		timer1.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				try {
					cli.imAlive();
				}
				catch(Exception e) {
				}
			}
		}, seconds*1000, seconds*1000);
	}/**
	public void imAliveWait(MediatorPortImpl med, int seconds) {
		timer2.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				if (i!=0) {
					i = med.
				}
			}
		}, seconds*1000, seconds*1000);
	}*/
}

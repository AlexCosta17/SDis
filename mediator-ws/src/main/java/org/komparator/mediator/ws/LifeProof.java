package org.komparator.mediator.ws;

import java.sql.Timestamp;
import java.util.Timer;
import java.util.TimerTask;

import org.komparator.mediator.ws.cli.MediatorClient;
import org.komparator.mediator.ws.cli.MediatorClientException;

public class LifeProof extends TimerTask {
	
	MediatorEndpointManager epm = null;
	Timer timer1 = new Timer();
	Timer timer2 = new Timer();
	Timestamp times;
	
	String wsURL = null;
	
	public LifeProof(MediatorEndpointManager epm, String url) {
		this.epm = epm;
		this.wsURL = url;
	}
	
	public void run() {
		try {
			if (epm.getwsURL().equals("http://localhost:8071/mediator-ws/endpoint")) {
				System.out.println("Primary");
				MediatorClient cli = new MediatorClient("http://localhost:8072/mediator-ws/endpoint");
				imAlivePrim(cli, 5);
			}
			else {
				MediatorPortImpl medPort = epm.getPort();
				imAliveWait(medPort,1);
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
	}
	
	public void imAliveWait(MediatorPortImpl med, int seconds) {
		timer2.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				times = new Timestamp(System.currentTimeMillis());
				if ((times.getTime() - med.getTime().getTime()) < 5000) {
					System.out.println("It's Secondary Alive");
				}
				else {
					System.out.println("Primary Died");
					timer2.cancel();
					timer2.purge();
				}
			}
		}, seconds*1000, seconds*1000);
	}
}

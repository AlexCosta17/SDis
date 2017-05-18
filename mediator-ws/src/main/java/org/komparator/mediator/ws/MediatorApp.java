package org.komparator.mediator.ws;

import java.util.Timer;

public class MediatorApp {

	public static void main(String[] args) throws Exception {
		// Check arguments
		if (args.length == 0 || args.length == 2) {
			System.err.println("Argument(s) missing!");
			System.err.println("Usage: java " + MediatorApp.class.getName() + " wsURL OR uddiURL wsName wsURL");
			return;
		}
		String uddiURL = null;
		String wsName = null;
		String wsURL = null;

		// Create server implementation object, according to options
		MediatorEndpointManager endpoint = null;
		if (args[0].equals("http://localhost:8072/mediator-ws/endpoint")) {
			wsURL = args[0];
			endpoint = new MediatorEndpointManager(wsURL);
			System.out.println("Correr secundário");
		} else {
			uddiURL = args[0];
			wsName = args[1];
			wsURL = args[2];
			endpoint = new MediatorEndpointManager(uddiURL, wsName, wsURL);
			endpoint.setVerbose(true);
			System.out.println("Correr primário");
		}

		try {
			endpoint.start();
			System.out.println("Started the damn server");
			Timer timer = new Timer(true);
			//LifeProof lifeProof = new LifeProof("http://localhost:8072/mediator-ws/endpoint");
			//timer.schedule(lifeProof, 0 * 1000, 5 * 1000);
			Thread.sleep(5 * 1000);
			
			endpoint.awaitConnections();
		} finally {
			endpoint.stop();
		}

	}

}

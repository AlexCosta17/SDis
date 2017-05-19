package org.komparator.mediator.ws;


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
		if (args.length == 1) {
			wsURL = args[0];
			endpoint = new MediatorEndpointManager(wsURL);
			
		} else if (args.length >= 3){
			uddiURL = args[0];
			wsName = args[1];
			wsURL = args[2];
			endpoint = new MediatorEndpointManager(uddiURL, wsName, wsURL);
			endpoint.setVerbose(true);
		}

		try {
			endpoint.start();
			
			if (endpoint.getwsURL().equals("http://localhost:8071/mediator-ws/endpoint")) {
				System.out.println("Started Primary Server and LifeProof!");
				LifeProof lifeProof = new LifeProof(endpoint, endpoint.getwsURL());
				lifeProof.run();
				endpoint.awaitConnections();
			}
			else {
				System.out.println("Started Secondary Server and LifeProof!");
				LifeProof lifeProof1 = new LifeProof(endpoint, endpoint.getwsURL());
				lifeProof1.run();
				endpoint.awaitConnections();
			}			
		} finally {
			endpoint.stop();
		}

	}

}

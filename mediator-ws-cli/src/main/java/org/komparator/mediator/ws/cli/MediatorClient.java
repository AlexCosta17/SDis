package org.komparator.mediator.ws.cli;

import static javax.xml.ws.BindingProvider.ENDPOINT_ADDRESS_PROPERTY;

import java.util.List;
import java.util.Map;

import javax.xml.ws.BindingProvider;


import org.komparator.mediator.ws.*;

// TODO uncomment after generate-sources
//import org.komparator.mediator.ws.*;

import pt.ulisboa.tecnico.sdis.ws.uddi.UDDINaming;


/**
 * Client.
 *
 * Adds easier endpoint address configuration and 
 * UDDI lookup capability to the PortType generated by wsimport.
 */
public class MediatorClient implements MediatorPortType {

// TODO uncomment after generate-sources
    // /** WS service */
    MediatorService service = null;

// TODO uncomment after generate-sources
    // /** WS port (port type is the interface, port is the implementation) */
    MediatorPortType port = null;

    /** UDDI server URL */
    private String uddiURL = null;

    /** WS name */
    private String wsName = null;

    /** WS endpoint address */
    private String wsURL = null; // default value is defined inside WSDL

    public String getWsURL() {
        return wsURL;
    }

    /** output option **/
    private boolean verbose = false;

    public boolean isVerbose() {
        return verbose;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    /** constructor with provided web service URL */
    public MediatorClient(String wsURL) throws MediatorClientException {
        this.wsURL = wsURL;
        createStub();
    }

    /** constructor with provided UDDI location and name */
    public MediatorClient(String uddiURL, String wsName) throws MediatorClientException {
        this.uddiURL = uddiURL;
        this.wsName = wsName;
        uddiLookup();
        createStub();
    }

    /** UDDI lookup */
    private void uddiLookup() throws MediatorClientException {
        try {
            if (verbose)
                System.out.printf("Contacting UDDI at %s%n", uddiURL);
            UDDINaming uddiNaming = new UDDINaming(uddiURL);

            if (verbose)
                System.out.printf("Looking for '%s'%n", wsName);
            wsURL = uddiNaming.lookup(wsName);

        } catch (Exception e) {
            String msg = String.format("Client failed lookup on UDDI at %s!",
                    uddiURL);
            throw new MediatorClientException(msg, e);
        }

        if (wsURL == null) {
            String msg = String.format(
                    "Service with name %s not found on UDDI at %s", wsName,
                    uddiURL);
            throw new MediatorClientException(msg);
        }
    }

    /** Stub creation and configuration */
    private void createStub() {
        if (verbose)
            System.out.println("Creating stub ...");
        service = new MediatorService();
        port = service.getMediatorPort();

        if (wsURL != null) {
            if (verbose)
                System.out.println("Setting endpoint address ...");
            BindingProvider bindingProvider = (BindingProvider) port;
            Map<String, Object> requestContext = bindingProvider
                    .getRequestContext();
            requestContext.put(ENDPOINT_ADDRESS_PROPERTY, wsURL);
        }
    }


    // remote invocation methods ----------------------------------------------
    
     @Override
	 public void clear() {
		 port.clear();
	 }

	 public String ping(String arg0) {
		 return port.ping(arg0);
	 }

     @Override
	 public List<ItemView> searchItems(String descText) throws InvalidText_Exception {
		 return port.searchItems(descText);
	 }

     @Override
	 public List<CartView> listCarts() {
		 return port.listCarts();
	 }

	 @Override
	 public List<ItemView> getItems(String productId) throws InvalidItemId_Exception {
		 return port.getItems(productId);
	 }

	 @Override
	 public ShoppingResultView buyCart(String cartId, String creditCardNr)
			 throws EmptyCart_Exception, InvalidCartId_Exception, InvalidCreditCard_Exception {
		 return port.buyCart(cartId, creditCardNr);
	 }

	 @Override
	 public void addToCart(String cartId, ItemIdView itemId, int itemQty) throws InvalidCartId_Exception,
			 InvalidItemId_Exception, InvalidQuantity_Exception, NotEnoughItems_Exception {
		 port.addToCart(cartId, itemId, itemQty);
	 }

	@Override
	 public List<ShoppingResultView> shopHistory() {
		 return port.shopHistory();
	 }
 
}
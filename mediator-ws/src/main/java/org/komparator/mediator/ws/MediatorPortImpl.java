package org.komparator.mediator.ws;

import java.util.Collection;
import java.util.List;

import javax.jws.WebService;

import org.komparator.supplier.ws.BadProductId;
import org.komparator.supplier.ws.BadProductId_Exception;
import org.komparator.supplier.ws.cli.SupplierClient;
import org.komparator.supplier.ws.cli.SupplierClientException;

import pt.ulisboa.tecnico.sdis.ws.uddi.UDDINaming;
import pt.ulisboa.tecnico.sdis.ws.uddi.UDDINamingException;

@WebService(
		endpointInterface = "org.komparator.mediator.ws.MediatorPortType", 
		wsdlLocation = "mediator.wsdl", 
		name = "MediatorWebService", 
		portName = "MediatorPort", 
		targetNamespace = "http://ws.mediator.komparator.org/", 
		serviceName = "MediatorService"
)

public class MediatorPortImpl implements MediatorPortType{

	// end point manager
	private MediatorEndpointManager endpointManager;

	public MediatorPortImpl(MediatorEndpointManager endpointManager) {
		this.endpointManager = endpointManager;
	}

	// Main operations -------------------------------------------------------
    @Override
	public List<ItemView> getItems(String productId) throws InvalidItemId_Exception {
    	// check product id
    	if (productId == null)
    		throwInvalidItemId("Item identifier cannot be null!");
    		productId = productId.trim();
    	if (productId.length() == 0)
    		throwInvalidItemId("Item identifier cannot be empty or whitespace!");

    	
    	
    	
		return getItems(productId);
	}
	
    @Override
	public List<ItemView> searchItems(String descText) throws InvalidText_Exception {
		return searchItems(descText);
	}
	
    @Override
	public void addToCart(String cartId, ItemIdView itemId, int itemQty) throws InvalidCartId_Exception,
	InvalidItemId_Exception, InvalidQuantity_Exception, NotEnoughItems_Exception {
		addToCart(cartId, itemId, itemQty);
	}
	
    @Override
	public ShoppingResultView buyCart(String cartId, String creditCardNr) throws EmptyCart_Exception,
	InvalidCartId_Exception, InvalidCreditCard_Exception {
		return buyCart(cartId, creditCardNr);
	}
    
	// Auxiliary operations --------------------------------------------------	
	@Override
    public String ping(String url) {
		try {
			
			UDDINaming uddiNaming = new UDDINaming(url);
			
			
			Collection<String> args = uddiNaming.list("T53_Supplier"+"%");
			StringBuilder builder = new StringBuilder();
			for(String s: args){
				SupplierClient client = new SupplierClient(s);
				builder.append(client.ping("Client") + "\n");
			}
			return builder.toString();
		} catch (UDDINamingException e) {
			e.printStackTrace();
		} catch (SupplierClientException e) {
			e.printStackTrace();
		}
		return ping(url);
    }

	@Override
	public void clear() {
		clear();
	}
	
	@Override
	public List<CartView> listCarts() {
		return listCarts();
	}
	
	@Override
	public List<ShoppingResultView> shopHistory() {
		return shopHistory();
	}

	
	// View helpers -----------------------------------------------------
	
    // TODO

    
	// Exception helpers -----------------------------------------------------

    // TODO
	/** Helper method to throw new BadProductId exception */
	private void throwInvalidItemId(final String message) throws InvalidItemId_Exception {
		InvalidItemId faultInfo = new InvalidItemId();
		faultInfo.message = message;
		throw new InvalidItemId_Exception(message, faultInfo);
	}

}

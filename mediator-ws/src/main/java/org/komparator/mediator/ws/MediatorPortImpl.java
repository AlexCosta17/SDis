package org.komparator.mediator.ws;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.jws.WebService;

import org.komparator.supplier.ws.BadProductId;
import org.komparator.supplier.ws.BadProductId_Exception;
import org.komparator.supplier.ws.BadText;
import org.komparator.supplier.ws.BadText_Exception;
import org.komparator.supplier.ws.ProductView;
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
		if(productId == null)
			throwInvalidItemId("Product identifier cannot be null!");
		productId = productId.trim();
		if (productId.length() == 0)
			throwInvalidItemId("Product identifier cannot be empty or whitespace!");
    	
		try {
			UDDINaming uddiNaming = endpointManager.getUddiNaming();
			Collection<String> args = uddiNaming.list("T53_Supplier"+"%");

			List<ItemView> prod = new ArrayList<ItemView>(); 
			for(String s: args) {
				SupplierClient client = new SupplierClient(s);
				ItemIdView idView = newItemIdView(client.getProduct(productId), s);
				ItemView iView = newItemView(client.getProduct(productId), idView);
				prod.add(iView);
			}
			return prod;
		} catch (UDDINamingException e) {
			e.printStackTrace();
		} catch (SupplierClientException e) {
			e.printStackTrace();
		} catch (BadProductId_Exception e) {
			e.printStackTrace();
		}
			
		return getItems(productId);
	}
	
    @Override
	public List<ItemView> searchItems(String descText) throws InvalidText_Exception {
		if (descText == null)
			throwInvalidText("Product description cannot be null!");
		descText = descText.trim();
		if (descText.length() == 0)
			throwInvalidText("Product description cannot be empty whitespaces!");
		
		try {
			UDDINaming uddiNaming = endpointManager.getUddiNaming();
			Collection<String> args = uddiNaming.list("T53_Supplier"+"%");
			
			List<ItemView> fin = new ArrayList<ItemView>();
			for(String s: args) {
				SupplierClient client = new SupplierClient(s);
				for(ProductView p: client.searchProducts(descText)) {
					ItemIdView idView = newItemIdView(p,s);
					ItemView iView = newItemView(p,idView);
					fin.add(iView);
				}
			}
			return fin;
		} catch (UDDINamingException e) {
			e.printStackTrace();
		} catch (SupplierClientException e) {
			e.printStackTrace();
		} catch (BadText_Exception e) {
			e.printStackTrace();
		}

    	return searchItems(descText);
	}
	
    @Override
	public void addToCart(String cartId, ItemIdView itemId, int itemQty) throws InvalidCartId_Exception,
	InvalidItemId_Exception, InvalidQuantity_Exception, NotEnoughItems_Exception {
		if (cartId == null)
			throwInvalidCartId("Cart ID cannot be null!");
		cartId = cartId.trim();
		if (cartId.length() == 0)
			throwInvalidCartId("Cart ID cannot be empty or whitespace!");
		if (itemId == null)
			throwInvalidItemId("Item ID view cannot be null!");
		if (itemQty<=0)
			throwInvalidQuantity_Exception("Quantity must be a positive number!");
		
    	
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
	private ItemIdView newItemIdView(ProductView prod, String sId) {
		ItemIdView view = new ItemIdView();
		view.setProductId(prod.getId());
		view.setSupplierId(sId);
		return view;
	}
	private ItemView newItemView(ProductView prod, ItemIdView idV) {
		ItemView view = new ItemView();
		view.setItemId(idV);
		view.setDesc(prod.getDesc());
		view.setPrice(prod.getPrice());
		return view;
	}

    
	// Exception helpers -----------------------------------------------------

	private void throwInvalidText(final String message) throws InvalidText_Exception {
		InvalidText faultInfo = new InvalidText();
		faultInfo.setMessage(message);
		throw new InvalidText_Exception(message, faultInfo);
	}
	
	private void throwInvalidItemId(final String message) throws InvalidItemId_Exception {
		InvalidItemId faultInfo = new InvalidItemId();
		faultInfo.message = message;
		throw new InvalidItemId_Exception(message, faultInfo);
	}
	private void throwInvalidCartId(final String message) throws InvalidCartId_Exception {
		InvalidCartId faultInfo = new InvalidCartId();
		faultInfo.message = message;
		throw new InvalidCartId_Exception(message, faultInfo);
	}
	private void throwInvalidQuantity_Exception(final String message) throws InvalidQuantity_Exception {
		InvalidQuantity faultInfo = new InvalidQuantity();
		faultInfo.message = message;
		throw new InvalidQuantity_Exception(message, faultInfo);
	}

}

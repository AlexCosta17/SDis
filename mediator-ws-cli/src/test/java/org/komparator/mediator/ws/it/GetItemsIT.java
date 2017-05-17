package org.komparator.mediator.ws.it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.komparator.supplier.ws.ProductView;
import org.komparator.mediator.ws.InvalidItemId_Exception;
import org.komparator.mediator.ws.ItemView;
import org.komparator.supplier.ws.BadProductId_Exception;
import org.komparator.supplier.ws.BadProduct_Exception;
import org.komparator.supplier.ws.cli.SupplierClient;
import org.komparator.supplier.ws.cli.SupplierClientException;



public class GetItemsIT extends BaseIT  {
	//mediatorClient.clear();
	
	// fill-in test products
	
	public static void oneTimeSetUp() throws InvalidItemId_Exception, BadProductId_Exception, BadProduct_Exception {
		
		try{
			
		SupplierClient supplier1 = new SupplierClient("http://localhost:8081/supplier-ws/endpoint");
		SupplierClient supplier2 = new SupplierClient("http://localhost:8082/supplier-ws/endpoint");
	{
		ProductView product = new ProductView();
		product.setId("X1");
		product.setDesc("Basketball");
		product.setPrice(10);
		product.setQuantity(10);
		supplier1.createProduct(product);
	}
	{
		ProductView product = new ProductView();
		product.setId("Y2");
		product.setDesc("Baseball");
		product.setPrice(20);
		product.setQuantity(20);
		supplier2.createProduct(product);
	}
	{
		ProductView product = new ProductView();
		product.setId("Z3");
		product.setDesc("Soccer ball");
		product.setPrice(30);
		product.setQuantity(30);
		supplier1.createProduct(product);
	}
		} catch (SupplierClientException e) {
		}
}

	@AfterClass
	public static void oneTimeTearDown() {
		// clear remote service state after all tests
		mediatorClient.clear();
	}

	@Before
	public void setUp() {
	}

	@After
	public void tearDown() {
	}
	
	// tests
			// assertEquals(expected, actual);

			// List<ItemView> searchItems(String descText) throws
			// InvalidText_Exception {

			// bad input tests
	
	@Test(expected = InvalidItemId_Exception.class)
	public void getItemsNullTest() throws InvalidItemId_Exception {
		mediatorClient.getItems(null);
	}
	
	@Test(expected = InvalidItemId_Exception.class)
	public void getItemsEmptyTest() throws InvalidItemId_Exception {
		mediatorClient.getItems("");
	}
	
	@Test(expected = InvalidItemId_Exception.class)
	public void getItemsWhitSpacesTest() throws InvalidItemId_Exception {
		mediatorClient.getItems("   ");
	}

	
	@Test(expected = InvalidItemId_Exception.class)
	public void getItemsTabTest() throws InvalidItemId_Exception {
		mediatorClient.getItems("\t");
	}
	
	@Test(expected = InvalidItemId_Exception.class)
	public void getItemsNewLineTest() throws InvalidItemId_Exception {
		mediatorClient.getItems("\n");
	}
	
	// main tests
	@Test
	public void getProductsExistsTest() throws InvalidItemId_Exception {
		List<ItemView> pvs = mediatorClient.getItems("X1");
		ItemView product = pvs.get(0);
		assertEquals("X1", pvs.get(0).getItemId().getProductId());
		assertEquals(10, product.getPrice());
		assertEquals("Basketball", product.getDesc());
	}
	
	@Test
	public void getProductsExistsTest2() throws InvalidItemId_Exception {
		List<ItemView> pvs = mediatorClient.getItems("Y1");
		ItemView product = pvs.get(0);
		assertEquals("Y2", pvs.get(0).getItemId().getProductId());
		assertEquals(20, product.getPrice());
		assertEquals("Baseball", product.getDesc());
	}
	
	@Test
	public void getProductsNotExistsTest() throws InvalidItemId_Exception {
		List<ItemView> product = mediatorClient.getItems("Y5");
		assertTrue(product.isEmpty());
	}
	
	@Test
	public void getProductsLowercaseNotExistsTest() throws InvalidItemId_Exception {
		List<ItemView> product = mediatorClient.getItems("y1");
		assertTrue(product.isEmpty());
	}
	
	
	
	@AfterClass
	public static void cleanup() {
	}

}

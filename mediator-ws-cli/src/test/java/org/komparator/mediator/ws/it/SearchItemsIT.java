package org.komparator.mediator.ws.it;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.komparator.mediator.ws.InvalidText_Exception;
import org.komparator.mediator.ws.ItemView;
import org.komparator.supplier.ws.BadProductId_Exception;
import org.komparator.supplier.ws.BadProduct_Exception;
import org.komparator.supplier.ws.ProductView;
import org.komparator.supplier.ws.cli.SupplierClient;
import org.komparator.supplier.ws.cli.SupplierClientException;

public class SearchItemsIT extends BaseIT{
	// static members



	// one-time initialization and clean-up
	@BeforeClass
	public static void oneTimeSetUp() throws BadProductId_Exception, BadProduct_Exception {
		// clear remote service state before all tests
		mediatorClient.clear();

		// fill-in test products
		// (since getProduct is read-only the initialization below
		// can be done once for all tests in this suite)
		
		try {
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
				product.setId("X2");
				product.setDesc("Basketball");
				product.setPrice(10);
				product.setQuantity(10);
				supplier2.createProduct(product);
			}
			{
				ProductView product = new ProductView();
				product.setId("Y2");
				product.setDesc("Baseball");
				product.setPrice(20);
				product.setQuantity(20);
				supplier1.createProduct(product);
			}
			{
				ProductView product = new ProductView();
				product.setId("Y2");
				product.setDesc("Baseball");
				product.setPrice(30);
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
			{
				ProductView product = new ProductView();
				product.setId("A0");
				product.setDesc("Volleyball");
				product.setPrice(40);
				product.setQuantity(40);
				supplier2.createProduct(product);
			}
			{
				ProductView product = new ProductView();
				product.setId("A0");
				product.setDesc("Volleyball");
				product.setPrice(50);
				product.setQuantity(50);
				supplier1.createProduct(product);
			}
		} catch (SupplierClientException e) {
		}
	}
		@AfterClass
		public static void oneTimeTearDown() {
			// clear remote service state after all tests
			//mediatorClient.clear();
		}
		// members

		// initialization and clean-up for each test
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
			@Test(expected = InvalidText_Exception.class)
			public void getItemNullTest() throws InvalidText_Exception {
				mediatorClient.searchItems(null);
			}
			@Test(expected = InvalidText_Exception.class)
			public void getItemEmptyTest() throws InvalidText_Exception {
				mediatorClient.searchItems("");
			}
			@Test(expected = InvalidText_Exception.class)
			public void getItemWhitespaceTest() throws InvalidText_Exception {
				mediatorClient.searchItems(" ");
			}

			@Test(expected = InvalidText_Exception.class)
			public void getItemTabTest() throws InvalidText_Exception {
				mediatorClient.searchItems("\t");
			}

			@Test(expected = InvalidText_Exception.class)
			public void getItemNewlineTest() throws InvalidText_Exception {
				mediatorClient.searchItems("\n");
			}
			
			
		
		// main tests
		@Test
		public void searchItemsExistsTest() throws InvalidText_Exception {
			List<ItemView> pvs = mediatorClient.searchItems("Basketball");
			assertEquals("X1", pvs.get(0).getItemId().getProductId());
			assertEquals("Basketball", pvs.get(0).getDesc());
			assertEquals("http://localhost:8081/supplier-ws/endpoint", pvs.get(0).getItemId().getSupplierId());
			assertEquals("X2", pvs.get(1).getItemId().getProductId());
			assertEquals("Basketball", pvs.get(1).getDesc());
			assertEquals("http://localhost:8082/supplier-ws/endpoint", pvs.get(1).getItemId().getSupplierId());
		}
		
		@Test
		public void searchItemsAnotherExistsTest() throws InvalidText_Exception {
			List<ItemView> pvs = mediatorClient.searchItems("Soccer");
			assertEquals("Z3", pvs.get(0).getItemId().getProductId());
			assertEquals("Soccer ball", pvs.get(0).getDesc());
		}
		
		@Test
		public void searchItemsYetAnotherExistsTest() throws InvalidText_Exception {
			List<ItemView> pvs = mediatorClient.searchItems("Vol");
			assertEquals("A0", pvs.get(0).getItemId().getProductId());
			assertEquals("Volleyball", pvs.get(0).getDesc());
			assertEquals("http://localhost:8082/supplier-ws/endpoint", pvs.get(0).getItemId().getSupplierId());
			assertEquals("A0", pvs.get(1).getItemId().getProductId());
			assertEquals("Volleyball", pvs.get(1).getDesc());
			assertEquals("http://localhost:8081/supplier-ws/endpoint", pvs.get(1).getItemId().getSupplierId());
		}
		
		@Test
		public void searchItemsBallTest() throws InvalidText_Exception {
			List<ItemView> pvs = mediatorClient.searchItems("ball");
			assertEquals("A0", pvs.get(0).getItemId().getProductId());
			assertEquals("Volleyball", pvs.get(0).getDesc());
			assertEquals("http://localhost:8082/supplier-ws/endpoint", pvs.get(0).getItemId().getSupplierId());
			assertEquals("A0", pvs.get(1).getItemId().getProductId());
			assertEquals("Volleyball", pvs.get(1).getDesc());
			assertEquals("http://localhost:8081/supplier-ws/endpoint", pvs.get(1).getItemId().getSupplierId());
			
			assertEquals("X1", pvs.get(2).getItemId().getProductId());
			assertEquals("Basketball", pvs.get(2).getDesc());
			assertEquals("X2", pvs.get(3).getItemId().getProductId());
			assertEquals("Basketball", pvs.get(3).getDesc());
			
			assertEquals("Y2", pvs.get(4).getItemId().getProductId());
			assertEquals("Baseball", pvs.get(4).getDesc());
			assertEquals("http://localhost:8081/supplier-ws/endpoint", pvs.get(4).getItemId().getSupplierId());
			assertEquals("Y2", pvs.get(5).getItemId().getProductId());
			assertEquals("Baseball", pvs.get(5).getDesc());
			assertEquals("http://localhost:8082/supplier-ws/endpoint", pvs.get(5).getItemId().getSupplierId());
			
			assertEquals("Z3", pvs.get(6).getItemId().getProductId());
			assertEquals("Soccer ball", pvs.get(6).getDesc());
			
		}
		
				
				
			
}
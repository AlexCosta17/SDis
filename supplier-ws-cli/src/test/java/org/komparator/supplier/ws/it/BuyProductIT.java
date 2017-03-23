package org.komparator.supplier.ws.it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.komparator.supplier.ws.*;

/**
 * Test suite
 */
public class BuyProductIT extends BaseIT {

	// static members

	// one-time initialization and clean-up
	@BeforeClass
	public static void oneTimeSetUp() throws BadProductId_Exception, BadProduct_Exception {
		
		
		client.clear();
		
	{
		ProductView product = new ProductView();
		product.setId("X1");
		product.setDesc("Basketball");
		product.setPrice(10);
		product.setQuantity(15);
		client.createProduct(product);
	}
	}

	@AfterClass
	public static void oneTimeTearDown() {
		client.clear();
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

	// public String buyProduct(String productId, int quantity)
	// throws BadProductId_Exception, BadQuantity_Exception,
	// InsufficientQuantity_Exception {

	// bad input tests

	@Test(expected = BadProductId_Exception.class)
	public void buyProductNullTest() throws BadProductId_Exception, BadQuantity_Exception, InsufficientQuantity_Exception {
		client.buyProduct(null, 0);
	}

	@Test(expected = BadProductId_Exception.class)
	public void buyProductEmptyTest() throws BadProductId_Exception, BadQuantity_Exception, InsufficientQuantity_Exception {
		client.buyProduct("", 0);
	}

	@Test(expected = BadProductId_Exception.class)
	public void buyProductWhitespaceTest() throws BadProductId_Exception, BadQuantity_Exception, InsufficientQuantity_Exception {
		client.buyProduct(" ", 0);
	}

	@Test(expected = BadProductId_Exception.class)
	public void buyProductTabTest() throws BadProductId_Exception, BadQuantity_Exception, InsufficientQuantity_Exception {
		client.buyProduct("\t", 0);
	}

	@Test(expected = BadProductId_Exception.class)
	public void buyProductNewlineTest() throws BadProductId_Exception, BadQuantity_Exception, InsufficientQuantity_Exception {
		client.buyProduct("\n", 0);
	}
	

	// main tests
	
	@Test
	public void buyProductExistsTest() throws BadProductId_Exception, BadQuantity_Exception, InsufficientQuantity_Exception {
		String purchaseId = client.buyProduct("X1", 10);
		ProductView product = client.getProduct("X1");
		assertEquals(5, product.getQuantity());
	}

	
	
}

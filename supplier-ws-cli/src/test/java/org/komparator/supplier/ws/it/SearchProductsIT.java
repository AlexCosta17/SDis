package org.komparator.supplier.ws.it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.komparator.supplier.ws.*;

/**
 * Test suite
 */
public class SearchProductsIT extends BaseIT {

	// static members

	// one-time initialization and clean-up
	@BeforeClass
	public static void oneTimeSetUp() throws BadProductId_Exception, BadProduct_Exception {
		// clear remote service state before all tests
		client.clear();
		// fill-in test products
				// (since getProduct is read-only the initialization below
				// can be done once for all tests in this suite)
				{
					ProductView product = new ProductView();
					product.setId("X1");
					product.setDesc("Basketball");
					product.setPrice(10);
					product.setQuantity(10);
					client.createProduct(product);
				}
				{
					ProductView product = new ProductView();
					product.setId("Y2");
					product.setDesc("Baseball");
					product.setPrice(20);
					product.setQuantity(20);
					client.createProduct(product);
				}
				{
					ProductView product = new ProductView();
					product.setId("Z3");
					product.setDesc("Soccer ball");
					product.setPrice(30);
					product.setQuantity(30);
					client.createProduct(product);
				}
				{
					ProductView product = new ProductView();
					product.setId("X2");
					product.setDesc("Basketball");
					product.setPrice(10);
					product.setQuantity(10);
					client.createProduct(product);
				}
	}

	@AfterClass
	public static void oneTimeTearDown() {
		// clear remote service state after all tests
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

	// public List<ProductView> searchProducts(String descText) throws
	// BadText_Exception

	// bad input tests

	@Test(expected = BadText_Exception.class)
	public void SearchProductNameNull() throws BadText_Exception {
		client.searchProducts(null);
	}
	@Test(expected = BadText_Exception.class)
	public void SearchProductNameEmpty() throws BadText_Exception {
		client.searchProducts("");
	}
	@Test(expected = BadText_Exception.class)
	public void SearchProductNameWhitespaces() throws BadText_Exception {
		client.searchProducts("  ");
	}
	@Test(expected = BadText_Exception.class)
	public void SearchProductTabTest() throws BadText_Exception {
		client.searchProducts("\t");
	}
	@Test(expected = BadText_Exception.class)
	public void SearchProductNewLineTest() throws BadText_Exception {
		client.searchProducts("\n");
	}

	// main tests

	@Test
	public void SearchProductExistsTest() throws BadText_Exception {
		List <ProductView> product = client.searchProducts("Baseball");
		assertEquals("Y2", product.get(0).getId());
		assertEquals("Baseball", product.get(0).getDesc());
		assertEquals(20, product.get(0).getPrice());
		assertEquals(20, product.get(0).getQuantity());
	}
	@Test
	public void SearchProductAnotherExistsTest() throws BadText_Exception {
		List <ProductView> product = client.searchProducts("Soccer ball");
		assertEquals("Z3", product.get(0).getId());
		assertEquals("Soccer ball", product.get(0).getDesc());
		assertEquals(30, product.get(0).getPrice());
		assertEquals(30, product.get(0).getQuantity());
	}
	@Test
	public void SearchProductExistsTwoTest() throws BadText_Exception {
		List <ProductView> product = client.searchProducts("Basketball");
		assertEquals(2, product.size());
		assertEquals("X1", product.get(0).getId());
		assertEquals("X2", product.get(1).getId());
		assertEquals("Basketball", product.get(0).getDesc());
		assertEquals("Basketball", product.get(1).getDesc());
		assertEquals(10, product.get(0).getPrice());
		assertEquals(10, product.get(0).getQuantity());
		assertEquals(10, product.get(1).getPrice());
		assertEquals(10, product.get(1).getQuantity());
	}
	@Test
	public void SearchProductExistsManyTest() throws BadText_Exception {
		List <ProductView> product = client.searchProducts("ball");
		assertEquals(4, product.size());
		assertEquals("Basketball", product.get(0).getDesc());
		assertEquals("Baseball", product.get(1).getDesc());
		assertEquals("Soccer ball", product.get(2).getDesc());
		assertEquals("Basketball", product.get(3).getDesc());
	}
	@Test
	public void SearchProductNotExistsTest() throws BadText_Exception {
		List <ProductView> product = client.searchProducts("fail-description");
		assertEquals(0, product.size());
		
	}
	@Test
	public void SearchProductLowercaseNotExistsTest() throws BadText_Exception {
		List <ProductView> product = client.searchProducts("basketball");
		assertEquals(0, product.size());
	}

}

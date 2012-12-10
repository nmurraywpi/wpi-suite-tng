package edu.wpi.cs.wpisuitetng.network.configuration;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import org.junit.*;

public class TestNetworkConfiguration {
	class MockObserver implements Observer {
		
		public MockObserver() {}

		/**
		 * @see java.util.Observable#update
		 */
		@Override
		public void update(Observable observable, Object arg) {
			// Do nothing
		}
	}

	private NetworkConfiguration config;
	private String url = "http://wpi.edu";

	@Before
	public void setUp() {
		config = new NetworkConfiguration(url);
	}

	@Test
	public void testGetApiUrl() {
		assertTrue(url.equals(config.getApiUrl()));
	}

	@Test
	public void testAddObserverNullObserverException() {
		try {
			config.addObserver(null);
			fail("No NullPointerException thrown when calling addObserver with null observer parameter.");
		}
		catch (NullPointerException e) {
			assertTrue("The observer must not be null.".equals(e.getMessage()));
		}
	}
	
	@Test
	public void testAddObserver() {
		assertEquals(0, config.getObservers().size());
		config.addObserver(new MockObserver());
		config.addObserver(new MockObserver());
		assertEquals(2, config.getObservers().size());
		
		Iterator<Observer> observersI = config.getObservers().iterator();
		
		while (observersI.hasNext()) {
			assertTrue(observersI.next() instanceof MockObserver);
		}
	}

	@Test
	public void testAddRequestHeaderNullKeyException() {
		try {
			config.addRequestHeader(null, "value");
			fail("No NullPointerException thrown when calling addRequestHeader with null key parameter.");
		}
		catch (NullPointerException e) {
			assertTrue("The key must not be null.".equals(e.getMessage()));
		}
	}

	@Test
	public void testAddRequestHeaderNullValueException() {
		try {
			config.addRequestHeader("header", null);
			fail("No NullPointerException thrown when calling addRequestHeader with null value parameter.");
		}
		catch (NullPointerException e) {
			assertTrue("The value must not be null.".equals(e.getMessage()));
		}
	}

	@Test
	public void testAddRequestHeader() {
		config.addRequestHeader("header1", "value1");
		config.addRequestHeader("header2", "value2a");
		config.addRequestHeader("header2", "value2b");
		config.addRequestHeader("header3", "value3");

		assertTrue(config.getRequestHeaders().containsKey("header1"));
		assertTrue(config.getRequestHeaders().containsKey("header2"));
		assertTrue(config.getRequestHeaders().containsKey("header3"));

		assertEquals(1, config.getRequestHeaders().get("header1").size());
		assertEquals(2, config.getRequestHeaders().get("header2").size());
		assertEquals(1, config.getRequestHeaders().get("header3").size());

		assertTrue(config.getRequestHeaders().get("header1").contains("value1"));
		assertTrue(config.getRequestHeaders().get("header2").contains("value2a"));
		assertTrue(config.getRequestHeaders().get("header2").contains("value2b"));
		assertTrue(config.getRequestHeaders().get("header3").contains("value3"));
	}
}

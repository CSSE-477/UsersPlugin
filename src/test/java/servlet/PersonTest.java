package servlet;

import static org.junit.Assert.*;

import org.junit.Test;

public class PersonTest {

	@Test
	public void testGettersAndConstructor() {
		Person p = new Person("First", "Last", "1234567", "123 Main St.");
		assertEquals("First", p.getFirstName());
		assertEquals("Last", p.getLastName());
		assertEquals("1234567", p.getPhoneNumber());
		assertEquals("123 Main St.", p.getAddress());
	}
	
	@Test
	public void testSetters() {
		Person p = new Person("First", "Last", "1234567", "123 Main St.");
		
		p.setFirstName("Bob");
		assertEquals("Bob", p.getFirstName());
		
		p.setLastName("Smith");
		assertEquals("Smith", p.getLastName());
		
		p.setPhoneNumber("7654321");
		assertEquals("7654321", p.getPhoneNumber());
		
		p.setAddress("123 New St.");
		assertEquals("123 New St.", p.getAddress());
	}
	
	@Test
	public void toStringTest() {
		Person p = new Person("First", "Last", "1234567", "123 Main St.");
		assertEquals("Person [firstName=First, lastName=Last, phoneNumber=1234567, address=123 Main St.]", p.toString());
	}
	
	@Test
	public void hashCodeTest() {
		Person p = new Person("First", "Last", "1234567", "123 Main St.");
		assertEquals(1950645915, p.hashCode());
	}
	
	@Test
	public void equalsTest() {
		Person p = new Person("First", "Last", "1234567", "123 Main St.");
		Person p2 = new Person("First", "Last", "1234567", "123 Main St.");
		assertTrue(p.equals(p));
		assertTrue(p.equals(p2));
		assertFalse(p.equals(null));
		assertFalse(p.equals(new Integer(1)));
		
		p.setAddress("");
		assertFalse(p.equals(p2));
		p2.setAddress("1");
		assertFalse(p.equals(p2));
		
		p = new Person("First", "Last", "1234567", "123 Main St.");
		p2 = new Person("First", "Last", "1234567", "123 Main St.");
		
		p.setPhoneNumber("");
		assertFalse(p.equals(p2));
		p2.setPhoneNumber("1");
		assertFalse(p.equals(p2));
		
		p = new Person("First", "Last", "1234567", "123 Main St.");
		p2 = new Person("First", "Last", "1234567", "123 Main St.");
		
		p.setFirstName("");
		assertFalse(p.equals(p2));
		p2.setFirstName("1");
		assertFalse(p.equals(p2));
		
		p = new Person("First", "Last", "1234567", "123 Main St.");
		p2 = new Person("First", "Last", "1234567", "123 Main St.");
		
		p.setLastName("");
		assertFalse(p.equals(p2));
		p2.setLastName("1");
		assertFalse(p.equals(p2));
	}

}

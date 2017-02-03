package servlet;

import static org.junit.Assert.*;

import org.junit.Test;

public class GroupTest {

	Person testPerson = new Person("John", "Doe", "1234567","Address");
	Person testPerson2 = new Person("John", "Doe2", "12345as67", "Afasdddress");
	
	@Test
	public void personConstructorTest() {
		Group g = new Group(testPerson);
		assertEquals(testPerson, g.getMember(0));
	}
	
	@Test
	public void addMemberTest() {
		Group g = new Group();
		g.addMember(testPerson2);
		assertEquals(testPerson2, g.getMember(0));
		assertEquals(1, g.getSize());
	}
	
	@Test
	public void toStringTest() {
		Group g = new Group(testPerson);
		assertEquals("Group [members=[Person [firstName=John, lastName=Doe, phoneNumber=1234567, address=Address]]]",g.toString());
	}
	
	@Test
	public void hashCodeTest() {
		Group g = new Group(testPerson);
		assertEquals(-1010011600, g.hashCode());
	}
	
	@Test
	public void equalsTest() {
		Group g = new Group();
		Group g2 = new Group(testPerson);
		
		assertFalse(g.equals(null));
		assertFalse(g.equals(new Object()));
		assertFalse(g.equals(g2));
		assertTrue(g.equals(g));
	}

}

package servlet;

import java.util.ArrayList;
import java.util.List;

public class Group {
	
	private List<Person> members;

	public Group(Person p) {
		this.members = new ArrayList();
		this.members.add(p);
	}

	public Person getMember(int i) {
		return this.members.get(i);
	}
	
	public void addMember(Person p) {
		this.members.add(p);
	}
}

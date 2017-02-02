package servlet;

import java.util.ArrayList;
import java.util.List;

public class Group {
	
	private List<Person> members;

	public Group() {
		this.members = new ArrayList<Person>();
	}
	
	public Group(Person p) {
		this.members = new ArrayList<Person>();
		this.members.add(p);
	}

	public Person getMember(int i) {
		return this.members.get(i);
	}
	
	public void addMember(Person p) {
		this.members.add(p);
	}
	
	public int getSize() {
		return members.size();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Group [members=" + members + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((members == null) ? 0 : members.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Group other = (Group) obj;
		if (members == null) {
			if (other.members != null)
				return false;
		} else if (!members.equals(other.members))
			return false;
		return true;
	}
}

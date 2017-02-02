package servlet;

import java.util.HashMap;
import java.util.Map;

import protocol.HttpRequest;
import protocol.HttpResponseBuilder;

public class GroupServlet extends AHttpServlet {

	private Map<Integer, Person> groupsMap;
	
	public GroupServlet(String resourcePath) {
		super(resourcePath);
	}

	@Override
	public void init() {
		Person p1 = new Person("Adam", "Smith", "6143612", "1234 Street Road");
		Person p2 = new Person("Some", "Guy", "4123651", "1 1st Street");
		this.groupsMap = new HashMap<Integer, Person>();
		Group g1 = new Group();
		g1.addMember(p1);
		g1.addMember(p2);
	}

	@Override
	public void destroy() {
		this.groupsMap.clear();
	}

	@Override
	public void doGet(HttpRequest request, HttpResponseBuilder responseBuilder) {
		// TODO Auto-generated method stub

	}

	@Override
	public void doHead(HttpRequest request, HttpResponseBuilder responseBuilder) {
		// TODO Auto-generated method stub

	}

	@Override
	public void doPost(HttpRequest request, HttpResponseBuilder responseBuilder) {
		// TODO Auto-generated method stub

	}

	@Override
	public void doPut(HttpRequest request, HttpResponseBuilder responseBuilder) {
		// TODO Auto-generated method stub

	}

	@Override
	public void doDelete(HttpRequest request, HttpResponseBuilder responseBuilder) {
		// TODO Auto-generated method stub

	}

}

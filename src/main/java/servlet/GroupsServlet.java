package servlet;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import protocol.HttpRequest;
import protocol.HttpResponseBuilder;
import protocol.Keywords;
import protocol.Protocol;
import utils.SwsLogger;

public class GroupsServlet extends AHttpServlet {

	private Map<Integer, Group> groupsMap;

	public GroupsServlet(String resourcePath) {
		super(resourcePath);
	}

	@Override
	public void init() {
		Person p1 = new Person("Adam", "Smith", "6143612", "1234 Street Road");
		Person p2 = new Person("Some", "Guy", "4123651", "1 1st Street");
		this.groupsMap = new HashMap<Integer, Group>();
		Group g1 = new Group();
		g1.addMember(p1);
		g1.addMember(p2);
		this.groupsMap.put(1, g1);
	}

	@Override
	public void destroy() {
		this.groupsMap.clear();
	}

	@Override
	public void doGet(HttpRequest request, HttpResponseBuilder responseBuilder) {
		Protocol protocol = Protocol.getProtocol();
		try {
			String arg = request.getUri().toString().split("/")[3];
			Integer index = Integer.parseInt(arg);
			Group g = this.groupsMap.get(index);
			if (g == null) {
				responseBuilder.setStatus(404).setPhrase(protocol.getStringRep(protocol.getCodeKeyword(404)));
				SwsLogger.accessLogger.info("Unable to find group " + index + " sending 404 response");
				return;
			}
			Gson responseGson = new Gson();
			String responseBody = responseGson.toJson(g);

			responseBuilder.setStatus(200).setPhrase(protocol.getStringRep(protocol.getCodeKeyword(200)))
					.putHeader(protocol.getStringRep(Keywords.CONTENT_TYPE), "application/json").setBody(responseBody);
			SwsLogger.accessLogger.info("Sending 200OK for GET request to group " + index);
			return;
		} catch (IndexOutOfBoundsException e) {
			responseBuilder.setStatus(400).setPhrase(protocol.getStringRep(protocol.getCodeKeyword(400)));
			SwsLogger.errorLogger.error("Unable to parse HTTP request. Sending 400 Bad Request.");
		}

	}

	@Override
	public void doHead(HttpRequest request, HttpResponseBuilder responseBuilder) {
		Protocol protocol = Protocol.getProtocol();
		try {
			String arg = request.getUri().toString().split("/")[3];
			Integer index = Integer.parseInt(arg);
			Group g = this.groupsMap.get(index);
			if (g != null) {
				responseBuilder.setStatus(200).setPhrase(protocol.getStringRep(protocol.getCodeKeyword(200)))
						.putHeader("Num-Groups", this.groupsMap.size() + "")
						.putHeader("Num-Users-In-Group", g.getSize() + "");
				SwsLogger.accessLogger.info("Sending 200OK for HEAD request to group " + index);
				return;
			}
			responseBuilder.setStatus(404).setPhrase(protocol.getStringRep(protocol.getCodeKeyword(404)));
			SwsLogger.accessLogger.info("Unable to find group " + index + ". Sending 404 response");
			return;
		} catch (IndexOutOfBoundsException e) {
			responseBuilder.setStatus(400).setPhrase(protocol.getStringRep(protocol.getCodeKeyword(400)));
			SwsLogger.errorLogger.error("Unable to parse HTTP request. Sending 400 Bad Request.");
		}
	}

	@Override
	public void doPost(HttpRequest request, HttpResponseBuilder responseBuilder) {
		Protocol protocol = Protocol.getProtocol();
		try {
			String arg = request.getUri().toString().split("/")[3];
			Integer index = Integer.parseInt(arg);

			String body = new String(request.getBody());
			Gson gson = new Gson();
			Person p = (Person) gson.fromJson(body, Person.class);
			System.out.println(p.toString());

			if (this.groupsMap.get(index) != null) {
				this.groupsMap.get(index).addMember(p);
			} else {
				this.groupsMap.put(index, new Group(p));
			}
			Gson responseGson = new Gson();
			String responseBody = responseGson.toJson(this.groupsMap.get(index));

			responseBuilder.setStatus(200).setPhrase(protocol.getStringRep(protocol.getCodeKeyword(200)))
					.putHeader(protocol.getStringRep(Keywords.CONTENT_TYPE), "application/json").setBody(responseBody);

		} catch (IndexOutOfBoundsException e) {
			responseBuilder.setStatus(400).setPhrase(protocol.getStringRep(protocol.getCodeKeyword(400)));
			SwsLogger.errorLogger.error("Unable to parse HTTP request. Sending 400 Bad Request.");
		}
	}

	@Override
	public void doPut(HttpRequest request, HttpResponseBuilder responseBuilder) {
		Protocol protocol = Protocol.getProtocol();
		try {
			String arg = request.getUri().toString().split("/")[3];
			Integer index = Integer.parseInt(arg);

			String body = new String(request.getBody());
			Gson gson = new Gson();
			Group g = (Group) gson.fromJson(body, Group.class);

			if (this.groupsMap.containsKey(index))
				this.groupsMap.remove(index);
			this.groupsMap.put(index, g);

			Gson responseGson = new Gson();
			String responseBody = responseGson.toJson(this.groupsMap.get(index));

			responseBuilder.setStatus(200).setPhrase(protocol.getStringRep(protocol.getCodeKeyword(200)))
					.putHeader(protocol.getStringRep(Keywords.CONTENT_TYPE), "application/json").setBody(responseBody);
			SwsLogger.accessLogger.info("Replaced Group " + index + ". Sending 200 OK");
			return;
		} catch (IndexOutOfBoundsException e) {
			responseBuilder.setStatus(400).setPhrase(protocol.getStringRep(protocol.getCodeKeyword(400)));
			SwsLogger.errorLogger.error("Unable to parse HTTP request. Sending 400 Bad Request.");
		}

	}

	@Override
	public void doDelete(HttpRequest request, HttpResponseBuilder responseBuilder) {
		Protocol protocol = Protocol.getProtocol();
		try {
			String arg = request.getUri().toString().split("/")[3];
			Integer index = Integer.parseInt(arg);
			Group g = this.groupsMap.get(index);
			if (g != null) {
				this.groupsMap.remove(index);
				responseBuilder.setStatus(204).setPhrase(protocol.getStringRep(protocol.getCodeKeyword(204)));
				SwsLogger.accessLogger.info("Sending 204 NO CONTENT for DELETE request to Group " + index);
				return;
			}
			responseBuilder.setStatus(404).setPhrase(protocol.getStringRep(protocol.getCodeKeyword(404)));
			SwsLogger.accessLogger.info("Sending 404 NOT FOUND for DELETE request on user " + index);
			return;
		} catch (Exception e) {
			responseBuilder.setStatus(400).setPhrase(protocol.getStringRep(protocol.getCodeKeyword(400)));
			SwsLogger.errorLogger.error("Unable to parse HTTP request. Sending 400 Bad Request.");
		}
	}
}

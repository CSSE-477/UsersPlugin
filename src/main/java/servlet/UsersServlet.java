package servlet;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import protocol.HttpRequest;
import protocol.HttpResponseBuilder;
import protocol.Keywords;
import protocol.Protocol;
import utils.SwsLogger;

public class UsersServlet extends AHttpServlet {

	private Map<Integer, Person> usersMap;

	public UsersServlet(String resourcePath) {
		super(resourcePath);
	}

	@Override
	public void init() {
		this.usersMap = new HashMap<Integer, Person>();
		this.usersMap.put(0, new Person("John", "Smith", "1234567", "1234 Cherry Lane"));
		this.usersMap.put(1, new Person("John", "Doe", "7654321", "1243 Main Street"));
		this.usersMap.put(2, new Person("Tayler", "How", "2628516", "Somewhere in Hawaii"));
	}

	@Override
	public void destroy() {
		this.usersMap.clear();
	}

	@Override
	public void doGet(HttpRequest request, HttpResponseBuilder responseBuilder) {
		Protocol protocol = Protocol.getProtocol();
		try {
			String arg = request.getUri().toString().split("/")[3];
			Integer index = Integer.parseInt(arg);
			Person p = this.usersMap.get(index);
			if (p == null) {
				responseBuilder.setStatus(404).setPhrase(protocol.getStringRep(protocol.getCodeKeyword(404)));
				SwsLogger.accessLogger.info("Unable to find user " + index + " sending 404 response");
				return;
			}
			Gson responseGson = new Gson();
			String responseBody = responseGson.toJson(p);

			responseBuilder.setStatus(200).setPhrase(protocol.getStringRep(protocol.getCodeKeyword(200))).putHeader(protocol.getStringRep(Keywords.CONTENT_TYPE), "application/json")
					.setBody(responseBody);
			SwsLogger.accessLogger.info("Sending 200OK for GET request to user " + index);
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
			Person p = this.usersMap.get(index);
			if (p != null) {
				responseBuilder.setStatus(200).setPhrase(protocol.getStringRep(protocol.getCodeKeyword(200)))
						.putHeader("Num-Users", this.usersMap.size() + "").putHeader("First Name", p.getFirstName())
						.putHeader("Last Name", p.getLastName()).putHeader("Phone Num.", p.getPhoneNumber());
				SwsLogger.accessLogger.info("Sending 200OK for HEAD request to user " + index);
				return;
			}
			responseBuilder.setStatus(404).setPhrase(protocol.getStringRep(protocol.getCodeKeyword(404)));
			SwsLogger.accessLogger.info("Unable to find user " + index + " sending 404 response");
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

			Person p2 = this.usersMap.get(index);
			
			if (p2 == null) {
				this.usersMap.put(index, p);
			} else {
				if (p.getFirstName() != null && !p.getFirstName().equals(""))
					p2.setFirstName(p.getFirstName());
				if (p.getLastName() != null && !p.getLastName().equals(""))
					p2.setLastName(p.getLastName());
				if (p.getPhoneNumber() != null && !p.getPhoneNumber().equals(""))
					p2.setPhoneNumber(p.getPhoneNumber());
				if (p.getAddress() != null && !p.getAddress().equals(""))
					p2.setAddress(p.getAddress());
			}
			Gson responseGson = new Gson();
			String responseBody = responseGson.toJson(p2);

			responseBuilder.setStatus(200).setPhrase(protocol.getStringRep(protocol.getCodeKeyword(200))).putHeader(protocol.getStringRep(Keywords.CONTENT_TYPE), "application/json")
					.setBody(responseBody);

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
			Person p = (Person) gson.fromJson(body, Person.class);

			if (this.usersMap.containsKey(index))
				this.usersMap.remove(index);
			this.usersMap.put(index, p);

			Gson responseGson = new Gson();
			String responseBody = responseGson.toJson(p);

			responseBuilder.setStatus(200).setPhrase(protocol.getStringRep(protocol.getCodeKeyword(200))).putHeader(protocol.getStringRep(Keywords.CONTENT_TYPE), "application/json")
					.setBody(responseBody);
			SwsLogger.accessLogger.info("Replaced user " + index + ". Sending 200 OK");
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
			Person p = this.usersMap.get(index);
			if (p != null) {
				this.usersMap.remove(index);
				responseBuilder.setStatus(204).setPhrase(protocol.getStringRep(protocol.getCodeKeyword(204)));
				SwsLogger.accessLogger.info("Sending 204 NO CONTENT for DELETE request to user " + index);
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

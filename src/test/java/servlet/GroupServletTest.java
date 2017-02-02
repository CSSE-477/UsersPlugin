package servlet;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;

import org.junit.Test;

import com.google.gson.Gson;

import protocol.HttpRequest;
import protocol.HttpResponse;
import protocol.HttpResponseBuilder;
import protocol.Protocol;

public class GroupServletTest {

	@Test
	public void testGet() throws Exception{
		AHttpServlet servlet = new GroupsServlet("Not Used");
		servlet.init();
		
		String requestLine = "GET /userapp/groups/1 HTTP/1.1\r\n";
		InputStream in = new ByteArrayInputStream(requestLine.getBytes());
		HttpRequest req = HttpRequest.read(in);
		HttpResponseBuilder builder = new HttpResponseBuilder();
		
		servlet.doGet(req, builder);
		HttpResponse response = builder.generateResponse();
		
		assertEquals(200, response.getStatus());
		String jsonResponse = response.getBody();
		System.out.println(jsonResponse);
		Gson gson = new Gson();
		Group actual = (Group) gson.fromJson(jsonResponse, Group.class);
		
		Person p1 = new Person("Adam", "Smith", "6143612", "1234 Street Road");
		Person p2 = new Person("Some", "Guy", "4123651", "1 1st Street");
		Group expected = new Group();
		expected.addMember(p1);
		expected.addMember(p2);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testHead() throws Exception{
		AHttpServlet servlet = new GroupsServlet("Not Used");
		servlet.init();
		
		String requestLine = "HEAD /userapp/groups/1 HTTP/1.1\r\n";
		InputStream in = new ByteArrayInputStream(requestLine.getBytes());
		HttpRequest req = HttpRequest.read(in);
		HttpResponseBuilder builder = new HttpResponseBuilder();
		
		servlet.doHead(req, builder);
		HttpResponse response = builder.generateResponse();
		
		assertEquals(200, response.getStatus());
		Protocol proto = Protocol.getProtocol();
		assertEquals(proto.getStringRep(proto.getCodeKeyword(200)), response.getPhrase());
		assertEquals(1, Integer.parseInt(response.getHeader().get("Num-Groups")));
		assertEquals(2, Integer.parseInt(response.getHeader().get("Num-Users-In-Group")));
	}
	
	@Test
	public void testPostOnExistingGroup() throws Exception {
		AHttpServlet servlet = new GroupsServlet("Not Used");
		servlet.init();
		
		String requestLine = "POST /userapp/groups/1 HTTP/1.1\r\n";	
		
		InputStream in = new ByteArrayInputStream(requestLine.getBytes());
		HttpRequest req = HttpRequest.read(in);
		HttpResponseBuilder builder = new HttpResponseBuilder();
		
		Person samplePerson = new Person("Steve", "Trotta", "???????", "Somewhere in Indiana?");
		Gson personGson = new Gson();
		String reqBody = personGson.toJson(samplePerson);
		
		Field body = req.getClass().getDeclaredField("body");
		body.setAccessible(true);
		body.set(req,reqBody.toCharArray());
		
		
		servlet.doPost(req, builder);
		HttpResponse response = builder.generateResponse();
		
		assertEquals(200, response.getStatus());
		Protocol proto = Protocol.getProtocol();
		assertEquals(proto.getStringRep(proto.getCodeKeyword(200)), response.getPhrase());
		
		Group expected = new Group();
		Person p1 = new Person("Adam", "Smith", "6143612", "1234 Street Road");
		Person p2 = new Person("Some", "Guy", "4123651", "1 1st Street");
		expected.addMember(p1);
		expected.addMember(p2);
		expected.addMember(samplePerson);
		
		String jsonResponse = response.getBody();
		Gson gson = new Gson();
		Group actual = (Group) gson.fromJson(jsonResponse, Group.class);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testPostOnNewGroup() throws Exception {
		AHttpServlet servlet = new GroupsServlet("Not Used");
		servlet.init();
		
		String requestLine = "POST /userapp/groups/2 HTTP/1.1\r\n";	
		
		InputStream in = new ByteArrayInputStream(requestLine.getBytes());
		HttpRequest req = HttpRequest.read(in);
		HttpResponseBuilder builder = new HttpResponseBuilder();
		
		Person samplePerson = new Person("Steve", "Trotta", "???????", "Somewhere in Indiana?");
		Gson personGson = new Gson();
		String reqBody = personGson.toJson(samplePerson);
		
		Field body = req.getClass().getDeclaredField("body");
		body.setAccessible(true);
		body.set(req,reqBody.toCharArray());
		
		
		servlet.doPost(req, builder);
		HttpResponse response = builder.generateResponse();
		
		assertEquals(200, response.getStatus());
		Protocol proto = Protocol.getProtocol();
		assertEquals(proto.getStringRep(proto.getCodeKeyword(200)), response.getPhrase());
		
		Group expected = new Group(samplePerson);
		
		String jsonResponse = response.getBody();
		Gson gson = new Gson();
		Group actual = (Group) gson.fromJson(jsonResponse, Group.class);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testPut() throws Exception {
		AHttpServlet servlet = new GroupsServlet("Not Used");
		servlet.init();
		
		String requestLine = "PUT /userapp/groups/2 HTTP/1.1\r\n";	
		
		InputStream in = new ByteArrayInputStream(requestLine.getBytes());
		HttpRequest req = HttpRequest.read(in);
		HttpResponseBuilder builder = new HttpResponseBuilder();
		
		Person samplePerson = new Person("Steve", "Trotta", "???????", "Somewhere in Indiana?");
		Group sampleGroup = new Group(samplePerson);
		Gson groupGson = new Gson();
		String reqBody = groupGson.toJson(sampleGroup);
		
		Field body = req.getClass().getDeclaredField("body");
		body.setAccessible(true);
		body.set(req,reqBody.toCharArray());
		
		
		servlet.doPut(req, builder);
		HttpResponse response = builder.generateResponse();
		
		assertEquals(200, response.getStatus());
		Protocol proto = Protocol.getProtocol();
		assertEquals(proto.getStringRep(proto.getCodeKeyword(200)), response.getPhrase());
		
		assertEquals(reqBody, response.getBody());
		
		String getRequest = "GET /userapp/groups/2 HTTP/1.1\r\n";
		in = new ByteArrayInputStream(getRequest.getBytes());
		req = HttpRequest.read(in);
		builder = new HttpResponseBuilder();
		
		//GET it back out, test that it wrote to datastore
		servlet.doGet(req, builder);
		
		HttpResponse responseGet = builder.generateResponse();
		
		assertEquals(200, responseGet.getStatus());
		String jsonResponse = responseGet.getBody();
		Gson gson = new Gson();
		Group actual = (Group) gson.fromJson(jsonResponse, Group.class);
		assertEquals(sampleGroup, actual);
	}
	
	@Test
	public void testDelete() throws Exception {
		AHttpServlet servlet = new GroupsServlet("Not Used");
		servlet.init();
		
		String requestLine = "DELETE /userapp/groups/1 HTTP/1.1\r\n";	
		
		InputStream in = new ByteArrayInputStream(requestLine.getBytes());
		HttpRequest req = HttpRequest.read(in);
		HttpResponseBuilder builder = new HttpResponseBuilder();
		
		servlet.doDelete(req, builder);
		HttpResponse response = builder.generateResponse();
		
		Protocol proto = Protocol.getProtocol();
		assertEquals(204, response.getStatus());
		assertEquals(proto.getStringRep(proto.getCodeKeyword(204)), response.getPhrase());
		
		//GET it back out, test that it deleted to datastore
		String deleteRequestLine = "GET /userapp/groups/1 HTTP/1.1\r\n";
		in = new ByteArrayInputStream(deleteRequestLine.getBytes());
		req = HttpRequest.read(in);
		builder = new HttpResponseBuilder();
		
		servlet.doGet(req, builder);
		response = builder.generateResponse();
		
		assertEquals(404, response.getStatus());
	}

}

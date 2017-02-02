package servlet;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;

import org.junit.Test;

import com.google.gson.Gson;

import protocol.HttpRequest;
import protocol.HttpResponse;
import protocol.HttpResponseBuilder;
import protocol.Protocol;

public class UsersServletTest {
	
	@Test
	public void testGet() throws Exception{
		AHttpServlet servlet = new UsersServlet("Not Used");
		servlet.init();
		
		String requestLine = "GET /userapp/users/1 HTTP/1.1\r";
		InputStream in = new ByteArrayInputStream(requestLine.getBytes());
		HttpRequest req = HttpRequest.read(in);
		HttpResponseBuilder builder = new HttpResponseBuilder();
		
		servlet.doGet(req, builder);
		HttpResponse response = builder.generateResponse();
		
		assertEquals(200, response.getStatus());
		String jsonResponse = response.getBody();
		Gson gson = new Gson();
		Person p = (Person) gson.fromJson(jsonResponse, Person.class);
		Person expected = new Person("John", "Doe", "7654321", "1243 Main Street");
		assertEquals(expected, p);
	}
	
	@Test
	public void testHead() throws Exception{
		AHttpServlet servlet = new UsersServlet("Not Used");
		servlet.init();
		
		String requestLine = "HEAD /userapp/users/1 HTTP/1.1\r\n";
		InputStream in = new ByteArrayInputStream(requestLine.getBytes());
		HttpRequest req = HttpRequest.read(in);
		HttpResponseBuilder builder = new HttpResponseBuilder();
		
		servlet.doHead(req, builder);
		HttpResponse response = builder.generateResponse();
		
		assertEquals(200, response.getStatus());
		Protocol proto = Protocol.getProtocol();
		assertEquals(proto.getStringRep(proto.getCodeKeyword(200)), response.getPhrase());
		assertEquals(3, Integer.parseInt(response.getHeader().get("Num-Users")));
	}
	
	@Test
	public void testPost() throws Exception{
		AHttpServlet servlet = new UsersServlet("Not Used");
		servlet.init();
		
		String requestLine = "POST /userapp/users/1 HTTP/1.1\r\n";	
		
		InputStream in = new ByteArrayInputStream(requestLine.getBytes());
		HttpRequest req = HttpRequest.read(in);
		HttpResponseBuilder builder = new HttpResponseBuilder();
		
		Person samplePerson = new Person("", "", "", "1243 Changed Street");
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
		
		Person expectedPerson = new Person("John", "Doe", "7654321", "1243 Changed Street");
		String expected = new Gson().toJson(expectedPerson);
		
		
		assertEquals(expected, response.getBody());
	}
	
	@Test
	public void testPut() throws Exception {
		AHttpServlet servlet = new UsersServlet("Not Used");
		servlet.init();
		
		String requestLine = "PUT /userapp/users/1 HTTP/1.1\r\n";	
		
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
		
		assertEquals(reqBody, response.getBody());
		
		String getRequest = "GET /userapp/users/1 HTTP/1.1\r\n";
		in = new ByteArrayInputStream(getRequest.getBytes());
		req = HttpRequest.read(in);
		builder = new HttpResponseBuilder();
		
		//GET it back out, test that it wrote to datastore
		servlet.doGet(req, builder);
		
		HttpResponse responseGet = builder.generateResponse();
		
		assertEquals(200, responseGet.getStatus());
		String jsonResponse = responseGet.getBody();
		Gson gson = new Gson();
		Person p = (Person) gson.fromJson(jsonResponse, Person.class);
		Person expected = new Person("Steve", "Trotta", "???????", "Somewhere in Indiana?");
		assertEquals(expected, p);
	}
	
	@Test
	public void testDelete() throws Exception {
		AHttpServlet servlet = new UsersServlet("Not Used");
		servlet.init();
		
		String requestLine = "DELETE /userapp/users/1 HTTP/1.1\r\n";	
		
		InputStream in = new ByteArrayInputStream(requestLine.getBytes());
		HttpRequest req = HttpRequest.read(in);
		HttpResponseBuilder builder = new HttpResponseBuilder();
		
		servlet.doDelete(req, builder);
		HttpResponse response = builder.generateResponse();
		
		Protocol proto = Protocol.getProtocol();
		assertEquals(204, response.getStatus());
		assertEquals(proto.getStringRep(proto.getCodeKeyword(204)), response.getPhrase());
	}

}

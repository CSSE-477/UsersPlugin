package servlet;

import java.io.InputStream;

public class UsersServletManager extends AServletManager {
	
	public UsersServletManager(String filePath, InputStream configStream) {
		super(filePath, configStream);
		// nothing to do 
	}

	@Override
	public void init() {
		//nothing to do
	}

	@Override
	public void destroy() {
		for(AHttpServlet servlet : servletMap.values()) {
			servlet.destroy();
		}
	}
}

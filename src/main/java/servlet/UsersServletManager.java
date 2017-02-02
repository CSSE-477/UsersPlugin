package servlet;

import java.net.URLClassLoader;

public class UsersServletManager extends AServletManager {
	
	public UsersServletManager(String filePath, URLClassLoader loader) {
		super(filePath, loader);
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

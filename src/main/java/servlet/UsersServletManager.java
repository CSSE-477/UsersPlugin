package servlet;

public class UsersServletManager extends AServletManager {
	
	public UsersServletManager(String filePath) {
		super(filePath);
		// nothing to do 
	}

	@Override
	public void init() {
		parseConfigFile();
	}

	@Override
	public void destroy() {
		for(AHttpServlet servlet : servletMap.values()) {
			servlet.destroy();
		}
	}
}

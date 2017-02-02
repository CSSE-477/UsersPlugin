package servlet;

import java.io.File;

public class UsersServletManager extends AServletManager {
	
	public UsersServletManager(String filePath) {
		super(filePath);
		// nothing to do 
	}

	@Override
	public void init() {
		this.configFile =  new File(this.getClass().getClassLoader().getResource("config.csv").getFile());
	}

	@Override
	public void destroy() {
		for(AHttpServlet servlet : servletMap.values()) {
			servlet.destroy();
		}
	}
}

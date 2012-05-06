package net.onpage.server.main;

import net.onpage.server.handler.OnPageHandler;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class OnPageApiServer {

	public static void main(String[] args) throws Exception {
		
		Server server = new Server(8080);
		 
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/page");
        
        context.addServlet(new ServletHolder(new OnPageHandler()),"/*");
        context.addServlet(new ServletHolder(new OnPageHandler()),"/1/*");
        context.addServlet(new ServletHolder(new OnPageHandler()),"/2/*");
 
 
        ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.setHandlers(new Handler[] { context });
 
        server.setHandler(contexts);
 
        server.start();
        server.join();
        
	}
		

}

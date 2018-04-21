package hu.fuz.service;

import org.eclipse.jetty.server.*;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.ssl.SslContextFactory;


public class BFCallbackServer {

    private void start() {
        Server jettyServer = new Server();

        jettyServer.setHandler(getServletContext());
        jettyServer.setConnectors(
                new Connector[] {
                        getHttpConnector(jettyServer, 9998),
                        getHttpsConnector(jettyServer, 9999)
                });



        try {
            jettyServer.start();
            jettyServer.join();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            try {
                jettyServer.stop();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private Connector getHttpsConnector(Server server, int port) {
        // HTTPS
        HttpConfiguration https = new HttpConfiguration();
        https.addCustomizer(new SecureRequestCustomizer());

        // HTTPS connector
        SslContextFactory sslContextFactory = new SslContextFactory();
        //sslContextFactory.setKeyStorePath(getKeyStorePath());
        sslContextFactory.setKeyStoreResource(getKeyStoreResource());
        sslContextFactory.setKeyManagerPassword("bf18fuz");
        sslContextFactory.setKeyStorePassword("bf18fuz");

        ServerConnector sslServerConnector = new ServerConnector(
                server,
                new SslConnectionFactory(sslContextFactory,"http/1.1"),
                new HttpConnectionFactory(https));
        sslServerConnector.setPort(port);
        return sslServerConnector;
    }

    private Connector getHttpConnector(Server server, int port) {
        //HTTPS Connector
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(port);
        return connector;
    }

    private Handler getServletContext() {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        ServletHolder jerseyServlet = context.addServlet(
                org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);

        // Tells the Jersey Servlet which REST service/class to load.
        jerseyServlet.setInitParameter(
                "jersey.config.server.provider.classnames",
                CallbackService.class.getCanonicalName());

        return context;
    }

    private String getKeyStorePath() {
        return BFCallbackServer.class.getProtectionDomain()
                .getCodeSource().getLocation() + "/keystore/keystore.jks";
    }

    private Resource getKeyStoreResource() {
        Resource resource = Resource.newClassPathResource("/keystore/keystore.jks");
        if(!resource.exists()){
            System.out.println(resource);
            throw new RuntimeException("Resource problem van! Nincs meg a keystore!");
        }
        return resource;
    }

    public static void main(String[] args) {
        BFCallbackServer bfCallbackServer = new BFCallbackServer();
        bfCallbackServer.start();
    }
}

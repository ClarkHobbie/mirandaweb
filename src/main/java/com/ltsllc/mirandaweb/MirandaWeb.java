package com.ltsllc.mirandaweb;

import com.ltsllc.mirandaweb.property.UndefinedPropertyException;
import com.ltsllc.mirandaweb.util.PropertiesUtils;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.util.ssl.SslContextFactory;

import java.io.File;
import java.util.Properties;

/**
 * Created by Clark on 4/30/2017.
 */
public class MirandaWeb {
    public static final String JETTY_BASE = "jetty.base";
    public static final String JETTY_HOME = "jetty.home";
    public static final String JETTY_TAG = "jetty.tag.version";
    public static final String DEFAULT_JETTY_TAG = "master";

    private MirandaWebCommandLine commandLine;
    private Server server;
    private MirandaWebProperties properties;

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public MirandaWebCommandLine getCommandLine() {
        return commandLine;
    }

    public MirandaWebProperties getProperties() {
        return properties;
    }

    public void setProperties(MirandaWebProperties properties) {
        this.properties = properties;
    }

    public static void main(String[] argv) throws Exception {
        MirandaWebCommandLine commandLine = new MirandaWebCommandLine(argv);
        commandLine.parse();
        MirandaWeb mirandaWeb = new MirandaWeb(commandLine);
        mirandaWeb.start();
    }

    public MirandaWeb (MirandaWebCommandLine commandLine) {
        this.commandLine = commandLine;
        this.server = new Server(443);
        this.properties = new MirandaWebProperties();
    }

    public void setupProperties () {
        Properties properties = PropertiesUtils.copy(System.getProperties());
        PropertiesUtils.overwrite(properties, getProperties().asProperties());

        Properties env = PropertiesUtils.mapToProperties(System.getenv());
        PropertiesUtils.overwrite(properties, env);

        PropertiesUtils.overwrite(properties, getCommandLine().asProperties());

        getProperties().setProperties(properties);
    }

    public void start () throws Exception {
        setupProperties();
        Server server = buildJetty();
        server.start();
    }


    public void checkProperty (String name, String value) throws UndefinedPropertyException {
        if (null == value || value.equals("")) {
            System.err.println("The property, " + name + " is null or empty");
            throw new UndefinedPropertyException(name);
        }
    }

    public Server buildJetty () throws Exception {
        //
        // jetty wants some properties defined
        //
        File file = new File(getProperties().getProperty(MirandaWebProperties.PROPERTY_BASE));
        String base = file.getCanonicalPath();

        getProperties().setProperty(JETTY_BASE, base);
        getProperties().setProperty(JETTY_HOME, base);
        getProperties().setProperty(JETTY_TAG, DEFAULT_JETTY_TAG);
        getProperties().updateSystemProperties();

        Server jetty = new Server();

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(true);
        resourceHandler.setWelcomeFiles(new String[]{ "index.html" });
        resourceHandler.setResourceBase(base);

        HandlerCollection handlerCollection = new HandlerCollection();
        handlerCollection.addHandler(resourceHandler);

        jetty.setHandler(handlerCollection);

        String serverKeyStoreFilename = getProperties().getProperty(MirandaWebProperties.PROPERTY_KEYSTORE);
        checkProperty(MirandaWebProperties.PROPERTY_KEYSTORE, serverKeyStoreFilename);

        String serverKeyStorePassword = getProperties().getProperty(MirandaWebProperties.PROPERTY_PASSWORD);
        checkProperty(MirandaWebProperties.PROPERTY_PASSWORD, serverKeyStorePassword);

        HttpConfiguration https = new HttpConfiguration();
        https.addCustomizer(new SecureRequestCustomizer());

        SslContextFactory sslContextFactory = new SslContextFactory();
        sslContextFactory.setKeyStorePath(serverKeyStoreFilename);
        sslContextFactory.setKeyStorePassword(serverKeyStorePassword);
        sslContextFactory.setKeyManagerPassword(serverKeyStorePassword);

        int sslPort = getProperties().getIntegerProperty(MirandaWebProperties.PROPERTY_PORT);

        ServerConnector sslConnector = new ServerConnector(jetty,
                new SslConnectionFactory(sslContextFactory, "http/1.1"),
                new HttpConnectionFactory(https));
        sslConnector.setPort(sslPort);

        jetty.setConnectors(new Connector[] { sslConnector });

        return jetty;
    }
}

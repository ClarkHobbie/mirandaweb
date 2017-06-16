package com.ltsllc.mirandaweb;

import com.ltsllc.mirandaweb.property.UndefinedPropertyException;
import com.ltsllc.mirandaweb.util.PropertiesUtils;
import com.ltsllc.mirandaweb.util.Utils;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.util.ssl.SslContextFactory;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.util.Properties;
import java.util.Scanner;

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
    private String keyStorePassword;
    private String trustStorePasswod;

    public String getKeyStorePassword() {
        return keyStorePassword;
    }

    public void setKeyStorePassword(String keyStorePassword) {
        this.keyStorePassword = keyStorePassword;
    }

    public String getTrustStorePassword() {
        return trustStorePasswod;
    }

    public void setTrustStorePasswod(String trustStorePasswod) {
        this.trustStorePasswod = trustStorePasswod;
    }

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

    public void setupProperties () throws IOException {
        MirandaWebProperties mirandaWebProperties = new MirandaWebProperties(getPropertiesFileName());
        Properties p = mirandaWebProperties.getProperties();

        Properties properties = PropertiesUtils.copy(System.getProperties());
        PropertiesUtils.overwrite(p, properties);

        Properties env = PropertiesUtils.mapToProperties(System.getenv());
        PropertiesUtils.overwrite(p, env);

        PropertiesUtils.overwrite(p, getCommandLine().asProperties());

        getProperties().setProperties(p);
    }


    public void checkFile (String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            throw new ShutdownException ("truststore, " + filename + ", does not exist");
        }
    }

    public static final String TRUST_STORE_PROPERTY = "javax.net.ssl.trustStore";

    public void start () throws Exception {
        setupPasswords();
        setupProperties();

        Server server = buildJetty();
        server.start();
    }


    /**
     * Ensure that we have the necessary key store and trust store passwords.
     */
    public void setupPasswords () {
        Scanner scanner = new Scanner(System.in);

        if (null != getCommandLine().getKeyStorePassword())
            setKeyStorePassword(getCommandLine().getKeyStorePassword());
        else {
            System.out.print("Keystore password:");
            setKeyStorePassword(scanner.nextLine());
        }

        if (null != getCommandLine().getTrustStorePassword())
            setTrustStorePasswod(getCommandLine().getTrustStorePassword());
        else {
            System.out.print("Truststore password:");
            setTrustStorePasswod(scanner.nextLine());
        }
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
        File file = new File(getProperties().getProperty(MirandaWebProperties.PROPERTY_HTML_BASE));
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

        String keyStoreFilename = getProperties().getProperty(MirandaWebProperties.PROPERTY_KEYSTORE_FILENAME);
        System.out.println("Using " + keyStoreFilename + " as the keystore");

        HttpConfiguration https = new HttpConfiguration();
        https.addCustomizer(new SecureRequestCustomizer());

        SslContextFactory sslContextFactory = new SslContextFactory();
        sslContextFactory.setKeyStorePath(keyStoreFilename);
        sslContextFactory.setKeyStorePassword(getKeyStorePassword());
        sslContextFactory.setKeyManagerPassword(getKeyStorePassword());

        int sslPort = getProperties().getIntegerProperty(MirandaWebProperties.PROPERTY_PORT);
        System.out.println("Using " + sslPort + " as the port");

        ServerConnector sslConnector = new ServerConnector(jetty,
                new SslConnectionFactory(sslContextFactory, "http/1.1"),
                new HttpConnectionFactory(https));
        sslConnector.setPort(sslPort);

        jetty.setConnectors(new Connector[] { sslConnector });

        return jetty;
    }

    public String getPropertiesFileName () {
        return "mirandaweb.properties";
    }
}

package com.ltsllc.mirandaweb;

import com.ltsllc.mirandaweb.commadline.CommandLine;

import java.util.Properties;


/**
 * Created by Clark on 4/30/2017.
 */
public class MirandaWebCommandLine extends CommandLine {
    public static final String USAGE = "mirandaweb [-b <html base>] [-k <keystore>] [-p <password>] [-r <port>]";

    public static final String BASE_SHORT = "-b";
    public static final String BASE_LONG = "--base";

    public static final String KEYSTORE_SHORT = "-k";
    public static final String KEYSTORE_LONG = "--keystore";

    public static final String PASSWORD_SHORT = "-p";
    public static final String PASSWORD_LONG = "--password";

    public static final String PORT_SHORT = "-r";
    public static final String PORT_LONG = "--port";

    public static final String PROPERTIES_FILE_SHORT = "-t";
    public static final String PROPERTIES_FILE_LONG = "--properties";

    private String base;
    private String keystore;
    private String password;
    private int port;
    private String propertiesFile;

    public String getPropertiesFile() {
        return propertiesFile;
    }

    public void setPropertiesFile(String propertiesFile) {
        this.propertiesFile = propertiesFile;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setPort (String value) {
        this.port = Integer.parseInt(value);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getKeystore() {
        return keystore;
    }

    public void setKeystore(String keystore) {
        this.keystore = keystore;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public MirandaWebCommandLine(String[] argv) {
        super(argv);
        setBase(MirandaWebProperties.DEFAULT_BASE);
        setKeystore(MirandaWebProperties.DEFAULT_KEYSTORE);
        setPort(MirandaWebProperties.DEFAULT_PORT);
        setPropertiesFile(MirandaWebProperties.DEFAULT_PROPERTIES_FILE);
    }

    public void setBase() {
        if (getArg() == null) {
            System.err.println("http base missing argument");
            System.err.println(USAGE);
            return;
        } else {
            setBase(getArgAndAdvance());
        }

    }

    public void setKeystore() {
        if (null == getArg()) {
            System.err.println("keystore missing argument");
            System.err.println(USAGE);
            return;
        }

        setKeystore(getArgAndAdvance());
    }

    public void setPassword() {
        if (null == getArg()) {
            System.err.println("password missing argument");
            System.err.println(USAGE);
            return;
        }

        setPassword(getArgAndAdvance());
    }

    public void setPort () {
        if (null == getArg()) {
            System.err.println("port missing argument");
            System.err.println(USAGE);
            return;
        }

        setPort(getArgAndAdvance());
    }

    public void setPropertiesFile () {
        if (null == getArg()) {
            System.err.println("missing properties file argument");
            System.err.println(USAGE);
            return;
        }

        setPropertiesFile(getArgAndAdvance());
    }

    public void parse() {
        while (null != getArg()) {
            if (getArg().equals(BASE_SHORT) || getArg().equals(BASE_LONG)) {
                advance();
                setBase();
            } else if (getArg().equals(KEYSTORE_SHORT) || (getArg().equals(KEYSTORE_LONG))) {
                advance();
                setKeystore();
            } else if (getArg().equals(PASSWORD_SHORT) || (getArg().equals(PASSWORD_LONG))) {
                advance();
                setPassword();
            } else if (getArg().equals(PORT_SHORT) || (getArg().equals(PORT_LONG))) {
                advance();
                setPort();
            } else if (getArg().equals(PROPERTIES_FILE_SHORT) || (getArg().equals(PROPERTIES_FILE_LONG))) {
                advance();
                setPropertiesFile();
            }
        }
    }


    public Properties asProperties() {
        Properties properties = new Properties();

        properties.setProperty(MirandaWebProperties.PROPERTY_BASE, getBase());
        properties.setProperty(MirandaWebProperties.PROPERTY_KEYSTORE, getKeystore());

        String temp = getPassword();
        if (null != temp)
            properties.setProperty(MirandaWebProperties.PROPERTY_PASSWORD, temp);

        properties.setProperty(MirandaWebProperties.PROPERTY_PROPERTIES_FILE, getPropertiesFile());

        return properties;
    }
}

package com.ltsllc.mirandaweb;

import com.ltsllc.mirandaweb.commadline.CommandLine;

import java.util.Properties;


/**
 * Created by Clark on 4/30/2017.
 */
public class MirandaWebCommandLine extends CommandLine {
    public static final String USAGE = "mirandaweb [-b <html base>] [-k <key store filename>] [-p <keyStorePassword>] [-r <port>]";

    public static final String BASE_SHORT = "-b";
    public static final String BASE_LONG = "--base";

    public static final String KEY_STORE_FILENAME_SHORT = "-k";
    public static final String KEY_STORE_FILENAME_LONG = "--keystore";

    public static final String KEY_STORE_PASSWORD_SHORT = "-p";
    public static final String KEY_STORE_PASSWORD_LONG = "--keyStorePassword";

    public static final String PORT_SHORT = "-r";
    public static final String PORT_LONG = "--port";

    public static final String PROPERTIES_FILENAME_SHORT = "-s";
    public static final String PROPERTIES_FILENAME_LONG = "--propertiesFileName";

    public static final String TRUST_STORE_PASSWORD_SHORT = "-t";
    public static final String TRUST_STORE_PASSWORD_LONG = "--trustStorePassword";

    public static final String TRUST_STORE_FILENAME_SHORT = "-T";
    public static final String TRUST_STORE_FILENAME_LONG = "--trustStoreFileName";

    public enum Options {
        Unknown,

        Base,
        KeyStoreFileName,
        KeyStorePassword,
        Port,
        PropertiesFileName,
        TrustStorePassword,
        TrustStoreFileName
    }


    private String base;
    private String keyStoreFilename;
    private String keyStorePassword;
    private int port;
    private String propertiesFile;
    private String trustStorePassword;
    private String trustStoreFilename;

    public String getTrustStoreFilename() {
        return trustStoreFilename;
    }

    public void setTrustStoreFilename(String trustStoreFilename) {
        this.trustStoreFilename = trustStoreFilename;
    }

    public String getTrustStorePassword() {

        return trustStorePassword;
    }

    public void setTrustStorePassword(String trustStorePassword) {
        this.trustStorePassword = trustStorePassword;
    }

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

    public String getKeyStorePassword() {
        return keyStorePassword;
    }

    public void setKeyStorePassword(String keyStorePassword) {
        this.keyStorePassword = keyStorePassword;
    }

    public String getKeyStoreFilename() {
        return keyStoreFilename;
    }

    public void setKeyStoreFilename(String filename) {
        this.keyStoreFilename = filename;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Options stringToOptions (String string) {
        Options option = Options.Unknown;

        if (string.equals(BASE_SHORT) || string.equals(BASE_LONG))
            option = Options.Base;
        else if (string.equals(KEY_STORE_FILENAME_SHORT) || string.equals(KEY_STORE_FILENAME_LONG))
            option = Options.KeyStoreFileName;
        else if (string.equals(KEY_STORE_PASSWORD_SHORT) || string.equals(KEY_STORE_PASSWORD_LONG))
            option = Options.KeyStorePassword;
        else if (string.equals(PORT_SHORT) || string.equals(PORT_LONG))
            option = Options.Port;
        else if (string.equals(PROPERTIES_FILENAME_SHORT) || string.equals(PROPERTIES_FILENAME_LONG))
            option = Options.PropertiesFileName;
        else if (string.equals(TRUST_STORE_PASSWORD_SHORT) || string.equals(TRUST_STORE_PASSWORD_LONG))
            option = Options.TrustStorePassword;
        else if (string.equals(TRUST_STORE_FILENAME_SHORT) || string.equals(TRUST_STORE_FILENAME_LONG))
            option = Options.TrustStoreFileName;

        return option;
    }


    public MirandaWebCommandLine(String[] argv) {
        super(argv);
        setBase(MirandaWebProperties.DEFAULT_BASE);
        setKeyStoreFilename(MirandaWebProperties.DEFAULT_KEYSTORE_FILENAME);
        setPort(MirandaWebProperties.DEFAULT_PORT);
        setPropertiesFile(MirandaWebProperties.DEFAULT_PROPERTIES_FILE);
        setTrustStoreFilename(MirandaWebProperties.DEFAULT_TRUST_STORE_FILENAME);
    }



    public void parse() {
        while (hasMoreArgs()) {
            String optionString = getArgAndAdvance();
            Options option = stringToOptions(optionString);

            switch (option) {
                case Base: {
                    processBaseOption();
                    break;
                }

                case Port: {
                    processPortOption();
                    break;
                }

                case KeyStoreFileName: {
                    processKeyStoreFilenameOption();
                    break;
                }

                case KeyStorePassword: {
                    processKeyStorePasswordOption();
                    break;
                }

                case PropertiesFileName: {
                    processPropertiesFileNameOption();
                    break;
                }

                case TrustStorePassword: {
                    processTrustStorePasswordOption();
                    break;
                }

                case TrustStoreFileName: {
                    processTrustStoreFileNameOption();
                    break;
                }

                default: {
                    System.err.println ("Unrecognized option: " + getArg());
                    System.exit(-1);
                }
            }
        }
    }


    public Properties asProperties() {
        Properties properties = new Properties();

        properties.setProperty(MirandaWebProperties.PROPERTY_BASE, getBase());
        Integer port = new Integer(getPort());
        properties.setProperty(MirandaWebProperties.PROPERTY_PORT, port.toString());
        properties.setProperty(MirandaWebProperties.PROPERTY_KEYSTORE_FILENAME, getKeyStoreFilename());
        properties.setProperty(MirandaWebProperties.PROPERTY_PROPERTIES_FILE, getPropertiesFile());
        properties.setProperty(MirandaWebProperties.PROPERTY_TRUST_STORE_FILENAME, getTrustStoreFilename());

        return properties;
    }

    public void processBaseOption () {
        String base = getArgAndAdvance();

        if (base == null)
            printErrorAndExit("Missing base");
        else {
            setBase(base);
        }
    }

    public void processPortOption () {
        String portString = getArgAndAdvance();

        if (portString == null) {
            printErrorAndExit("Missing port");
        } else {
            try {
                int port = Integer.parseInt(portString);
                setPort(port);
            } catch (NumberFormatException e) {
                printErrorAndExit("Invalid port: " + portString);
            }
        }
    }

    public void printErrorAndExit (String error) {
        System.err.println (error);
        System.err.println (USAGE);

        System.exit(-1);
    }


    public void processKeyStoreFilenameOption () {
        String filename = getArgAndAdvance();

        if (filename == null) {
            printErrorAndExit("Missing properties file name");
        }

        setKeyStoreFilename(filename);
    }

    public void processKeyStorePasswordOption () {
        String password = getArgAndAdvance();

        if (null == password) {
            printErrorAndExit("Missing keystore password");
        }

        setKeyStorePassword(password);
    }


    public void processPropertiesFileNameOption () {
        String filename = getArgAndAdvance();

        if (null == filename) {
            printErrorAndExit("Missing properties filename");
        }

        setPropertiesFile(filename);
    }

    public void processTrustStorePasswordOption () {
        String password = getArgAndAdvance();

        if (null == password) {
            printErrorAndExit("Missing trust store password");
        }

        setTrustStorePassword(password);
    }

    public void processTrustStoreFileNameOption () {
        String filename = getArgAndAdvance();

        if (null == filename) {
            printErrorAndExit("Missing trust store filename");
        }

        setTrustStoreFilename(filename);
    }
}

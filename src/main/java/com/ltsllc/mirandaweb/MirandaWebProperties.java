package com.ltsllc.mirandaweb;

import com.ltsllc.mirandaweb.util.PropertiesUtils;
import com.ltsllc.mirandaweb.util.PropertyDelegate;
import com.ltsllc.mirandaweb.util.Utils;

import javax.xml.bind.annotation.XmlType;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Clark on 4/30/2017.
 */
public class MirandaWebProperties extends PropertyDelegate {
    public static final String BASE_PACKAGE = "com.ltsllc.mirandaweb.";
    public static final String PROPERTY_BASE = BASE_PACKAGE + "base";
    public static final String PROPERTY_KEYSTORE = BASE_PACKAGE + "keystore";
    public static final String PROPERTY_PASSWORD = BASE_PACKAGE + "password";
    public static final String PROPERTY_PORT = BASE_PACKAGE + "port";
    public static final String PROPERTY_TRUST_STORE_FILENAME = BASE_PACKAGE + "trustStore";
    public static final String PROPERTY_PROPERTIES_FILE = BASE_PACKAGE + "properties";
    public static final String PROPERTY_HTML_BASE = BASE_PACKAGE + "html";

    public static final String DEFAULT_BASE = "html";
    public static final String DEFAULT_KEYSTORE = "keystore";
    public static final String DEFAULT_PORT = "443";
    public static final String DEFAULT_TRUST_STORE_FILENAME = "truststore";
    public static final String DEFAULT_PROPERTIES_FILE = "mirandaweb.properties";
    public static final String DEFAULT_HTML_BASE = "html";

    public static final String[][] defaults = {
            { PROPERTY_BASE, DEFAULT_BASE },
            { PROPERTY_KEYSTORE, DEFAULT_KEYSTORE },
            { PROPERTY_PORT, DEFAULT_PORT },
            { PROPERTY_TRUST_STORE_FILENAME, DEFAULT_TRUST_STORE_FILENAME },
            { PROPERTY_PROPERTIES_FILE, DEFAULT_PROPERTIES_FILE },
            { PROPERTY_HTML_BASE, DEFAULT_HTML_BASE }
    };

    public MirandaWebProperties () {
        Properties properties = PropertiesUtils.buildFrom(defaults);
        setProperties(properties);
    }

    public Properties loadPropertiesFile (String filename) throws IOException {
        Properties p = new Properties();

        File file = new File(filename);
        if (file.exists()) {
            FileReader fileReader = null;

            try {
                fileReader = new FileReader(file);
                p.load(fileReader);
            } finally {
                Utils.closeIgnoreExceptions(fileReader);
            }
        }

        return p;
    }

    public MirandaWebProperties (String filename) throws IOException {
        Properties defaultProperties = PropertiesUtils.buildFrom(defaults);

        Properties p = loadPropertiesFile(filename);

        PropertiesUtils.overwrite(defaultProperties, p);

        setProperties(defaultProperties);
    }

    public Properties asProperties () {
        return getProperties();
    }
}

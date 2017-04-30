package com.ltsllc.mirandaweb.util;

import java.io.*;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Set;

/**
 * Created by Clark on 4/30/2017.
 */
public class PropertyDelegate {
    private Properties properties;

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public void updateSystemProperties () {
        rectify();
        Properties properties = System.getProperties();
        PropertiesUtils.overwrite(properties, getProperties());
    }

    public void rectify() {
    }

    public synchronized Object setProperty(String key, String value) {
        rectify();
        return getProperties().setProperty(key, value);
    }

    public synchronized void load(Reader reader) throws IOException {
        rectify();
        getProperties().load(reader);
    }

    public synchronized void load(InputStream inStream) throws IOException {
        rectify();
        getProperties().load(inStream);
    }

    public void save(OutputStream out, String comments) {
        rectify();
        getProperties().save(out, comments);
    }

    public void store(Writer writer, String comments) throws IOException {
        rectify();
        getProperties().store(writer, comments);
    }

    public void store(OutputStream out, String comments) throws IOException {
        rectify();
        getProperties().store(out, comments);
    }

    public String getProperty(String key) {
        rectify();
        return getProperties().getProperty(key);
    }

    public String getProperty(String key, String defaultValue) {
        rectify();
        return getProperties().getProperty(key, defaultValue);
    }

    public Enumeration<?> propertyNames() {
        rectify();
        return getProperties().propertyNames();
    }

    public Set<String> stringPropertyNames() {
        rectify();
        return getProperties().stringPropertyNames();
    }

    public void list(PrintStream out) {
        rectify();
        getProperties().list(out);
    }

    public void list(PrintWriter out) {
        rectify();
        getProperties().list(out);
    }

    public int getIntegerProperty (String name) throws Exception {
        String value = getProperty(name);

        int intValue = -1;

        if (null != value) {
            try {
                intValue = Integer.parseInt(value);
            } catch (NumberFormatException e) {
                throw new Exception(e);
            }
        }

        return intValue;
    }
}

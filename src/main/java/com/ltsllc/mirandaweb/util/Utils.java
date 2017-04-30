package com.ltsllc.mirandaweb.util;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.security.*;
import java.security.cert.X509Certificate;


/**
 * Created by Clark on 2/3/2017.
 */
public class Utils {
    public static PrivateKey loadKey(String filename, String passwordString, String alias) throws GeneralSecurityException, IOException {
        PrivateKey privateKey = null;
        FileInputStream fileInputStream = null;

        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            fileInputStream = new FileInputStream(filename);
            keyStore.load(fileInputStream, passwordString.toCharArray());
            privateKey = (PrivateKey) keyStore.getKey(alias, passwordString.toCharArray());
        } finally {
            closeIgnoreExceptions(fileInputStream);
        }

        return privateKey;
    }


    public static X509Certificate loadCertificate(String filename, String passwordString, String alias)
            throws GeneralSecurityException, IOException {
        X509Certificate certificate = null;
        FileInputStream fileInputStream = null;

        try {
            fileInputStream = new FileInputStream(filename);
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(fileInputStream, passwordString.toCharArray());
            certificate = (X509Certificate) keyStore.getCertificate(alias);
        } finally {
            closeIgnoreExceptions(fileInputStream);
        }

        return certificate;
    }


    public static KeyStore loadKeyStore(String filename, String passwordString)
            throws GeneralSecurityException, IOException {
        KeyStore keyStore = null;
        FileInputStream fileInputStream = null;

        try {
            fileInputStream = new FileInputStream(filename);
            keyStore = KeyStore.getInstance(KeyStore.getDefaultType());

            keyStore.load(fileInputStream, passwordString.toCharArray());
        } finally {
            closeIgnoreExceptions(fileInputStream);
        }

        return keyStore;
    }


    public static void closeIgnoreExceptions(InputStream inputStream) {
        if (null != inputStream) {
            try {
                inputStream.close();
            } catch (IOException e) {
            }
        }
    }

    public static void closeIgnoreExceptions(Writer writer) {
        if (null != writer) {
            try {
                writer.close();
            } catch (IOException e) {
            }
        }
    }

    public static void closeIgnoreExceptions(OutputStream outputStream) {
        if (null != outputStream) {
            try {
                outputStream.close();
            } catch (IOException e) {

            }
        }
    }

    public static void closeIgnoreExceptions(Socket socket) {
        if (null != socket) {
            try {
                socket.close();
            } catch (IOException e) {

            }
        }
    }

    public static void closeIgnoreExceptions(Reader r) {
        if (null != r)
            try {
                r.close();
            } catch (IOException e) {

            }
    }

    private static final int BUFFER_SIZE = 8192;

    public static byte[] calculateSha1(FileInputStream fileInputStream)
            throws NoSuchAlgorithmException, IOException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

        byte buffer[] = new byte[BUFFER_SIZE];
        int bytesRead;

        do {
            bytesRead = fileInputStream.read(buffer);
            messageDigest.update(buffer);
        } while (BUFFER_SIZE == bytesRead);

        return messageDigest.digest();
    }


    private static char[] DIGITS = "0123456789ABCDEF".toCharArray();

    public static String byteToHexString(byte b) {
        StringBuffer sb = new StringBuffer();

        int value = b & 0xf0;
        value = value >> 4;
        sb.append(DIGITS[value]);

        value = b & 0xf;
        sb.append(DIGITS[value]);

        return sb.toString();
    }


    public static String bytesToString(byte[] bytes) {
        StringWriter stringWriter = new StringWriter();

        for (byte b : bytes) {
            stringWriter.append(byteToHexString(b));
        }

        return stringWriter.toString();
    }


    public static byte[] hexStringToBytes(String hexString) throws IOException {
        StringReader reader = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        reader = new StringReader(hexString);

        char[] buffer = new char[2];

        int bytesRead = reader.read(buffer);

        while (-1 != bytesRead) {
            byte b = toByte(buffer);
            byteArrayOutputStream.write(b);
            bytesRead = reader.read(buffer);
        }

        return byteArrayOutputStream.toByteArray();
    }


    public static byte toByte(char[] buffer) {
        int value = toNibble(buffer[0]);
        value = value << 4;
        int temp = 0xF & toNibble(buffer[1]);
        value = value | temp;

        return (byte) value;
    }


    public static int toNibble(char c) {
        if (c >= '0' && c <= '9')
            return (c - '0');
        else if (c >= 'A' && c <= 'F')
            return (c - 'A' + 10);
        else {
            throw new IllegalArgumentException();
        }
    }


    public static String calculateSha1(byte[] buffer) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = null;

        messageDigest = MessageDigest.getInstance("SHA");
        messageDigest.update(buffer);

        String digest = bytesToString(messageDigest.digest());
        return digest;
    }


    public static String calculateSha1(String s) throws NoSuchAlgorithmException {
        byte[] buffer = s.getBytes();
        return calculateSha1(buffer);
    }

    public static TrustManagerFactory createTrustManagerFactory(String filename, String passwordString)
            throws IOException, GeneralSecurityException {
        FileInputStream fileInputStream = null;
        TrustManagerFactory trustManagerFactory = null;

        try {
            fileInputStream = new FileInputStream(filename);
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(fileInputStream, passwordString.toCharArray());

            trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
        } finally {
            closeIgnoreExceptions(fileInputStream);
        }

        return trustManagerFactory;
    }

    public static KeyManagerFactory createKeyManagerFactoy(String filename, String password)
            throws IOException, GeneralSecurityException {
        KeyManagerFactory keyManagerFactory = null;

        KeyStore keyStore = loadKeyStore(filename, password);
        keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, password.toCharArray());

        return keyManagerFactory;
    }

    public static SSLContext createSocketServerSslContext(String serverFilename, String serverPassword, String serverAlias,
                                                          String trustStoreFilename, String trustStorePassword, String trustStoreAlias)
            throws GeneralSecurityException, IOException {
        PrivateKey key = loadKey(serverFilename, serverPassword, serverAlias);
        X509Certificate certificate = loadCertificate(trustStoreFilename, trustStorePassword, trustStoreAlias);

        SSLContext sslContext = SSLContext.getDefault();
        return sslContext;
    }

    public static String hexStringToString(String hexString) throws IOException {
        byte[] bytes = hexStringToBytes(hexString);
        return new String(bytes);
    }

    public static byte[] toBytes (long value) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(Long.BYTES);
        byteBuffer.putLong(value);
        return byteBuffer.array();
    }

}

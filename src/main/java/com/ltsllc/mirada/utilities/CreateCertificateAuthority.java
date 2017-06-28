package com.ltsllc.utilities;

import sun.security.x509.*;

import java.io.IOException;
import java.io.StringWriter;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Date;

/**
 * Created by clarkhobbie on 6/27/17.
 */
public class CreateCertificateAuthority {
    private CertificatesCommandLine commandLine;

    public CertificatesCommandLine getCommandLine() {
        return commandLine;
    }

    public CreateCertificateAuthority (CertificatesCommandLine certificatesCommandLine) {
        this.commandLine = certificatesCommandLine;
    }

    public Certificate createCertificate (KeyPair keyPair, String distinguishedName, int days, String algorithm)
            throws IOException, GeneralSecurityException
    {
        PrivateKey privateKey = keyPair.getPrivate();
        X509CertInfo info = new X509CertInfo();
        Date from = new Date();
        Date to = new Date(from.getTime() + days * 86400000l);
        CertificateValidity interval = new CertificateValidity(from, to);
        BigInteger sn = new BigInteger(64, new SecureRandom());
        X500Name owner = new X500Name(distinguishedName);

        info.set(X509CertInfo.VALIDITY, interval);
        info.set(X509CertInfo.SERIAL_NUMBER, new CertificateSerialNumber(sn));
        info.set(X509CertInfo.SUBJECT, owner);
        info.set(X509CertInfo.ISSUER, owner);
        info.set(X509CertInfo.KEY, new CertificateX509Key(keyPair.getPublic()));
        info.set(X509CertInfo.VERSION, new CertificateVersion(CertificateVersion.V3));
        AlgorithmId algo = new AlgorithmId(AlgorithmId.md5WithRSAEncryption_oid);
        info.set(X509CertInfo.ALGORITHM_ID, new CertificateAlgorithmId(algo));

        // Sign the cert to identify the algorithm that's used.
        X509CertImpl cert = new X509CertImpl(info);
        cert.sign(privateKey, algorithm);

        // Update the algorithm, and resign.
        algo = (AlgorithmId) cert.get(X509CertImpl.SIG_ALG);
        info.set(CertificateAlgorithmId.NAME + "." + CertificateAlgorithmId.ALGORITHM, algo);
        cert = new X509CertImpl(info);
        cert.sign(privateKey, algorithm);

        return cert;
    }

    public void createCertificateAuthority () {
        String distinguishedName = constructDistinguishedName();
        KeyPair keyPair = constructKeyPair();
        Certificate certificate = createCertificate(keyPair, distinguishedName, getCertificatesCommandLine().getDays(),
                "RSA");
        writeCertificate(getCertificatesCommandLine().getCertificateFilename(), certificate);
    }

    public String constructDistinguishedName () {
        StringWriter stringWriter = new StringWriter();

        stringWriter.write("c=" + getCommandLine().getCountry() + ", ");
        stringWriter.write("st=" + getCommandLine().getState() + ", ");
        stringWriter.write("l=" + getCommandLine().getCity());
        stringWriter.write("o=" + getCommandLine().getCompany());
        stringWriter.write("ou=" + getCommandLine().getOrganizationalUnit());
        stringWriter.write("cn=" + getCommandLine().getName());

        return stringWriter.toString();
    }

    public KeyPair constructKeyPair () {
        PublicKey publicKey = obtainPublicKey();
        PrivateKey privateKey = obtainPrivateKey();
        KeyPair keyPair = new KeyPair(publicKey, privateKey);
        return keyPair;
    }

    public PublicKey obtainPublicKey () throws IOException {
        String filename = getCertificatesCommandLine().getPublicKeyFilename();

        if (isPemFile(filename))
            return readPemPublicKey(filename);
        else if (isJksFile(filename))
            return readJksPublicKey(filename);
        else {
            printErrorAndExit("the file uses an unrecognized file type: " + filename);
            return null;
        }
    }

    public boolean isPemFile (String filename) {
        return filename.endsWith(".pem");
    }

    public boolean isJksFile (String filename) {
        return filename.endsWith(".jks");
    }


    public KeyPair createKeyPair () throws GeneralSecurityException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        return keyPairGenerator.genKeyPair();
    }

    public void printErrorAndExit (String message) {
        System.err.println(message);
        System.exit(-1);
    }

    public void go () {

    }
}

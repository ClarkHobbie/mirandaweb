package com.ltsllc.utilities;

import com.ltsllc.common.application.Application;
import com.ltsllc.common.commadline.CommandLine;
import sun.security.x509.*;

import java.io.IOException;
import java.io.StringWriter;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Date;

/**
 * Created by clarkhobbie on 6/26/17.
 */
public class Utilites extends Application {
    public static void main (String[] argv) {
        Utilites certificates = new Utilites(argv);
        certificates.go();
    }

    public Utilites(String[] argv) {
        super(argv);
    }

    @Override
    public CommandLine createCommandLine(String[] argv) {
        return new CertificatesCommandLine(argv);
    }

    public CertificatesCommandLine getCertificatesCommandLine () {
        return (CertificatesCommandLine) getCommandLine();
    }

    public void go () {
        super.go();

        try {
            switch (getCertificatesCommandLine().getCommand()) {
                case CreateCertificateAuthority: {
                    createCertificateAuthority();
                    break;
                }

                case CreateKeyPair: {
                    createKeyPair();
                    break;
                }

                case CreateNode: {
                    createNode();
                    break;
                }

                case CreateUser: {
                    createUser();
                    break;
                }

                case CreateSubscription: {
                    createSubscription();
                    break;
                }

                default: {

                }
            }
        }
        catch (Exception e) {
           System.err.println("The system encountered an exception while trying to perform an action.");
           e.printStackTrace();
           System.exit(-1);
        }
    }

    public void createNode () {
        throw new IllegalStateException("not implemented");
    }

    public void createUser () {
        throw new IllegalStateException("not implemented");
    }

    public void createSubscription () {
        throw new IllegalStateException("not implemented");
    }

    public void createCertificateAuthority () {
        CreateCertificateAuthority createCertificateAuthority = new CreateCertificateAuthority(getCertificatesCommandLine());
        createCertificateAuthority.go();
    }

    public void createKeyPair () {
        CreateKeyPair createKeyPair = new CreateKeyPair(getCertificatesCommandLine());
        createKeyPair.go();
    }
}

package com.ltsllc.utilities;

import com.ltsllc.common.commadline.CommandLine;

/**
 * Created by clarkhobbie on 6/25/17.
 */
public class CertificatesCommandLine extends CommandLine {
    public static String SWITCH_CREATE_CERTIFICATE_SHORT = "-c";
    public static String SWITCH_CREATE_CERTIFICATE_LONG = "--createCertificate";

    public static String SWITCH_CREATE_KEY_PAIR_SHORT = "-k";
    public static String SWITCH_CREATE_KEY_PAIR_LONG = "--createKeyPair";

    public static String SWITCH_COUNTRY_SHORT = "-u";
    public static String SWITCH_COUNTRY_LONG = "--country";

    public static String SWITCH_STATE_SHORT = "-s";
    public static String SWITCH_STATE_LONG = "--state";

    public static String SWITCH_CITY_SHORT = "-t";
    public static String SWITCH_CITY_LONG = "--city";

    public static String SWITCH_COMPANY_SHORT = "-a";
    public static String SWITCH_COMPANY_LONG = "--company";

    public static String SWITCH_NAME_SHORT = "-n";
    public static String SWITCH_NAME_LONG = "--name";

    public static String SWITCH_EMAIL_SHORT = "-e";
    public static String SWITCH_EMAIL_LONG = "--email";

    public static String SWITCH_DAYS_SHORT = "-d";
    public static String SWITCH_DAYS_LONG = "--days";

    public static String SWITCH_PUBLIC_KEY_FILE_SHORT = "-u";
    public static String SWITCH_PUBLIC_KEY_FILE_LONG = "--publicKey";

    public static String SWITCH_PUBLIC_KEY_PASSWORD_SHORT = "-v";
    public static String SWITCH_PUBLIC_KEY_PASSWORD_LONG = "--publicKeyPassword";

    public static String SWITCH_PRIVATE_KEY_FILE_SHORT = "-r";
    public static String SWITCH_PRIVATE_KEY_FILE_LONG = "--privateKey";

    public static String SWITCH_PRIVATE_KEY_PASSWORD_SHORT = "-w";
    public static String SWITCH_PRIVATE_KEY_PASSWORD_LONG = "--privateKeyPassword";

    public static String SWITCH_ORGANIZATIONAL_UNIT_SHORT = "-u";
    public static String SWITCH_ORGANIZATIONAL_UNIT_LONG = "--organizationalUnit";


    public enum CertificateCommandLineSwitches {
        Unknown(Switches.Unknown.getIndex()),
        CreateCertificateAuthority(Switches.LAST.getIndex()),
        CreateKeyPair(1 + Switches.LAST.getIndex()),
        Country(2 + Switches.LAST.getIndex()),
        State(3 + Switches.LAST.getIndex()),
        City(4 + Switches.LAST.getIndex()),
        Company(5 + Switches.LAST.getIndex()),
        Name(6 + Switches.LAST.getIndex()),
        Email(7 + Switches.LAST.getIndex()),
        Days(8 + Switches.LAST.getIndex()),
        PublicKey(9 + Switches.LAST.getIndex()),
        PrivateKey(10 + Switches.LAST.getIndex()),
        OrganizationalUnit(11 + Switches.LAST.getIndex()),
        PublicKeyPassword(12 + Switches.LAST.getIndex()),
        PrivateKeyPassword(13 + Switches.LAST.getIndex());

        private int index;

        CertificateCommandLineSwitches(int index) {
            this.index = index;
        }

        public int getIndex() {
            return index;
        }

        public static CertificateCommandLineSwitches toCertificateCommandLineSwitches (Switches aSwitch) {
            int index = aSwitch.getIndex();
            CertificateCommandLineSwitches aCertificateCommandLineSwitch = Unknown;

            if (index == Unknown.getIndex())
                aCertificateCommandLineSwitch = CertificateCommandLineSwitches.Unknown;
            else if (index == CreateCertificateAuthority.getIndex())
                aCertificateCommandLineSwitch = CreateCertificateAuthority;
            else if (index == CreateKeyPair.getIndex())
                aCertificateCommandLineSwitch = CreateKeyPair;
            else if (index == Country.getIndex())
                aCertificateCommandLineSwitch = Country;
            else if (index == State.getIndex())
                aCertificateCommandLineSwitch = State;
            else if (index == City.getIndex())
                aCertificateCommandLineSwitch = City;
            else if (index == Company.getIndex())
                aCertificateCommandLineSwitch = Company;
            else if (index == Name.getIndex())
                aCertificateCommandLineSwitch = Name;
            else if (index == Email.getIndex())
                aCertificateCommandLineSwitch = Email;
            else if (index == Days.getIndex())
                aCertificateCommandLineSwitch = Days;
            else if (index == PublicKey.getIndex())
                aCertificateCommandLineSwitch = PublicKey;
            else if (index == PrivateKey.getIndex())
                aCertificateCommandLineSwitch = PrivateKey;
            else if (index == OrganizationalUnit.getIndex())
                aCertificateCommandLineSwitch = OrganizationalUnit;
            else if (index == PublicKeyPassword.getIndex())
                aCertificateCommandLineSwitch = PublicKeyPassword;
            else if (index == PrivateKeyPassword.getIndex())
                aCertificateCommandLineSwitch = PrivateKeyPassword;

            return aCertificateCommandLineSwitch;
        }
    }

    public enum Commands {
        Unknown,
        CreateCertificateAuthority,
        CreateKeyPair,
        CreateUser,
        CreateNode,
        CreateSubscription
    }

    public static final String UNKNOWN = "Unknown";
    public static final int DAYS_365 = 365;
    public static final String DEFAULT_PUBLIC_KEY_FILENAME = "public.pem";
    public static final String DEFAULT_PRIVATE_KEY_FILENAME = "private.pem";

    private Commands command;
    private String country = UNKNOWN;
    private String state = UNKNOWN;
    private String city = UNKNOWN;
    private String company = UNKNOWN;
    private String organizationalUnit = UNKNOWN;
    private String name = UNKNOWN;
    private String email = UNKNOWN;
    private int days = DAYS_365;
    private String publicKeyFilename = DEFAULT_PUBLIC_KEY_FILENAME;
    private String privateKeyFilename = DEFAULT_PRIVATE_KEY_FILENAME;
    private String publicKeyPassword;
    private String privateKeyPassword;

    public CertificatesCommandLine (String[] argv) {
        super(argv);

        this.command = Commands.Unknown;
    }

    public String getPrivateKeyPassword() {
        return privateKeyPassword;
    }

    public void setPrivateKeyPassword(String privateKeyPassword) {
        this.privateKeyPassword = privateKeyPassword;
    }

    public String getPublicKeyPassword() {

        return publicKeyPassword;
    }

    public void setPublicKeyPassword(String publicKeyPassword) {
        this.publicKeyPassword = publicKeyPassword;
    }

    public String getOrganizationalUnit() {
        return organizationalUnit;
    }

    public void setOrganizationalUnit(String organizationalUnit) {
        this.organizationalUnit = organizationalUnit;
    }

    public String getPrivateKeyFilename() {
        return privateKeyFilename;
    }

    public void setPrivateKeyFilename(String privateKeyFilename) {
        this.privateKeyFilename = privateKeyFilename;
    }

    public String getPublicKeyFilename() {
        return publicKeyFilename;
    }

    public void setPublicKeyFilename(String publicKeyFilename) {
        this.publicKeyFilename = publicKeyFilename;
    }

    public int getDays() {
        return days;
    }

    public void setDays (int days) {
        this.days = days;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Commands getCommand() {
        return command;
    }

    public void setCommand(Commands command) {
        this.command = command;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {

        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getState() {

        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public Switches toSwitch (String argument) {
        CertificateCommandLineSwitches aSwitch = CertificateCommandLineSwitches.Unknown;

        if (argument.equals(SWITCH_CREATE_CERTIFICATE_SHORT) || argument.equals(SWITCH_CREATE_CERTIFICATE_LONG)) {
            aSwitch = CertificateCommandLineSwitches.CreateCertificateAuthority;
        } else if (argument.equals(SWITCH_CREATE_KEY_PAIR_SHORT) || argument.equals(SWITCH_CREATE_KEY_PAIR_LONG)) {
            aSwitch = CertificateCommandLineSwitches.CreateKeyPair;
        } else if (argument.equals(SWITCH_COUNTRY_SHORT) || argument.equals(SWITCH_COUNTRY_LONG)) {
            aSwitch = CertificateCommandLineSwitches.Country;
        } else if (argument.equals(SWITCH_STATE_SHORT) || argument.equals(SWITCH_STATE_LONG)) {
            aSwitch = CertificateCommandLineSwitches.State;
        } else if (argument.equals(SWITCH_CITY_SHORT) || argument.equals(SWITCH_CITY_LONG)) {
            aSwitch = CertificateCommandLineSwitches.City;
        } else if (argument.equals(SWITCH_COMPANY_SHORT) || argument.equals(SWITCH_COMPANY_LONG)) {
            aSwitch = CertificateCommandLineSwitches.Company;
        } else if (argument.equals(SWITCH_NAME_SHORT) || argument.equals(SWITCH_NAME_LONG)) {
            aSwitch = CertificateCommandLineSwitches.Name;
        } else if (argument.equals(SWITCH_EMAIL_SHORT) || argument.equals(SWITCH_EMAIL_LONG)) {
            aSwitch = CertificateCommandLineSwitches.Email;
        } else if (argument.equals(SWITCH_DAYS_SHORT) || argument.equals(SWITCH_DAYS_LONG)) {
            aSwitch = CertificateCommandLineSwitches.Days;
        } else if (argument.equals(SWITCH_PUBLIC_KEY_FILE_SHORT) || argument.equals(SWITCH_PUBLIC_KEY_FILE_LONG)) {
            aSwitch = CertificateCommandLineSwitches.PublicKey;
        } else if (argument.equals(SWITCH_PRIVATE_KEY_FILE_SHORT) || argument.equals(SWITCH_PRIVATE_KEY_FILE_LONG)) {
            aSwitch = CertificateCommandLineSwitches.PrivateKey;
        } else if (argument.equals(SWITCH_ORGANIZATIONAL_UNIT_SHORT) || argument.equals(SWITCH_ORGANIZATIONAL_UNIT_LONG)) {
            aSwitch = CertificateCommandLineSwitches.OrganizationalUnit;
        }

        Switches aSuperClassSwitch = Switches.PlaceHolder;
        aSuperClassSwitch.setIndex(aSwitch.getIndex());
        return aSuperClassSwitch;
    }

    @Override
    public String getUsageString() {
        return "Usage: certificate <command>\n"
                 + "\twhere <command> is one of\n"
                 + "\t" + SWITCH_CREATE_CERTIFICATE_SHORT + " or " + SWITCH_CREATE_CERTIFICATE_LONG + "\n"
                 + "\t" + SWITCH_CREATE_KEY_PAIR_SHORT + " or " + SWITCH_CREATE_KEY_PAIR_LONG;
    }

    @Override
    public void processSwitch(Switches aSwitch) {
        CertificateCommandLineSwitches certificateCommandLineSwitch =
                CertificateCommandLineSwitches.toCertificateCommandLineSwitches(aSwitch);

        switch (certificateCommandLineSwitch) {
            case City: {
                processCitySwitch();
                break;
            }

            case Company: {
                processCompanySwitch();
                break;
            }

            case Country: {
                processCountrySwitch();
                break;
            }

            case CreateCertificateAuthority: {
                processCommandSwitch(Commands.CreateCertificateAuthority);
                break;
            }

            case CreateKeyPair: {
                processCommandSwitch(Commands.CreateKeyPair);
                break;
            }

            case Days: {
                processDaysSwitch();
                break;
            }

            case Email: {
                processEmailSwitch();
                break;
            }

            case Name: {
                processNameSwitch();
                break;
            }

            case OrganizationalUnit: {
                processOrganizationalUnit();
                break;
            }

            case PrivateKey: {
                processPrivateKeyFileSwitch();
                break;
            }

            case PublicKey: {
                processPublicKeyFileSwitch();
                break;
            }

            case State: {
                processStateSwitch();
                break;
            }

            default: {
                printErrorAndUsageAndExit("unrecognized switch " + certificateCommandLineSwitch.toString());
                break;
            }
        }
    }

    public void processPrivateKeyFileSwitch () {
        String privateKeyFilename = getRequiredArg("missing private key filename");
        setPrivateKeyFilename(privateKeyFilename);
    }

    public void processPublicKeyFileSwitch () {
        String publicKeyFilename = getRequiredArg("missing public key filename");
        setPublicKeyFilename(publicKeyFilename);
    }

    public void processOrganizationalUnit () {
        String organizationalUnit = getRequiredArg("missing organizational unit argument");
        setOrganizationalUnit(organizationalUnit);
    }

    public void processCommandSwitch (Commands command) {
        setCommand(command);
    }

    public String getRequiredArg (String message) {
        advance();
        if (!hasMoreArgs())
            printErrorAndUsageAndExit(message);

        return getArg();
    }

    public void processCountrySwitch () {
        String country = getRequiredArg("missing country argument");
        setCountry(country);
    }

    public void processStateSwitch () {
        String state = getRequiredArg("missing state argument");
        setState(state);
    }

    public void processCitySwitch () {
        String city = getRequiredArg("missing city argument");
        setCity(city);
    }

    public void processCompanySwitch () {
        String company = getRequiredArg("missing company argument");
        setCompany(company);
    }

    public void processNameSwitch () {
        String name = getRequiredArg("missing name argument");
        setName(name);
    }

    public void processEmailSwitch () {
        String email = getRequiredArg("missing email argument");
        setEmail(email);
    }

    public void processDaysSwitch () {
        String daysString = getRequiredArg("minning number of days the certificate is good for");

        int days = -1;

        try {
            days = Integer.parseInt(daysString);
        } catch (NumberFormatException e) {
            printErrorAndUsageAndExit("invalid number of days: " + daysString);
        }

        if (days <= 0) {
            printErrorAndUsageAndExit("number of days must be greater than 0: " + daysString);
        }

        setDays(days);
    }
}

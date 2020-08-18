package connection;

import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;

public class Conn {
    private static SOAPConnection soapConnection;

    private Conn() throws SOAPException {
        SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
        soapConnection = soapConnectionFactory.createConnection();
    }

    public static SOAPConnection getSoapConnection() throws SOAPException {
        if (soapConnection == null) {
            new Conn();
        }
        return soapConnection;
    }

    public static void close() throws SOAPException {
        soapConnection.close();
    }
}

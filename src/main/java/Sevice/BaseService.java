package Sevice;

import connection.Conn;
import constants.Consts;
import entity.nested.Birth;
import entity.nested.Name;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class BaseService {
    protected static SOAPMessage createSoapMessageForId(String xmlPath, long id) throws IOException, SOAPException {
        return getSoapMessageFromString(
                String.format(fromXmlToString(Consts.BASE_XML_PATH + xmlPath), id)
        );
    }

    protected static SOAPMessage createSoapMessageForGetAllAuthors(
            String orderType, int page, Boolean pagination, int size)
            throws IOException, SOAPException {
        return getSoapMessageFromString(
                String.format(
                        fromXmlToString(
                                Consts.BASE_XML_PATH + Consts.GET_ALL_AUTHORS_XML_PATH),
                        orderType, page, pagination, size));
    }

    protected static SOAPMessage createSoapMessageForPostAuthor(
            Long authorId, Name authorName, String nationality, Birth birth, String authorDescription)
            throws IOException, SOAPException {
        return getSoapMessageFromString(
                String.format(
                        fromXmlToString(
                                Consts.BASE_XML_PATH + Consts.POST_AUTHOR_XML_PATH),
                        authorId,
                        authorName.getFirst(),
                        authorName.getSecond(),
                        nationality,
                        birth.getDate(),
                        birth.getCountry(),
                        birth.getCity(),
                        authorDescription));
    }

    protected static SOAPMessage getSoapMessageFromString(String xml) throws SOAPException, IOException {
        MessageFactory factory = MessageFactory.newInstance();
        return factory.createMessage(
                new MimeHeaders(), new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
    }

    protected static String fromXmlToString(String xmlPath) throws IOException {
        File xmlFile = new File(xmlPath);
        Reader fileReader = new FileReader(xmlFile);
        BufferedReader bufReader = new BufferedReader(fileReader);
        StringBuilder sb = new StringBuilder();
        String line = bufReader.readLine();
        while (line != null) {
            sb.append(line).append("\n");
            line = bufReader.readLine();
        }
        return sb.toString();
    }

    protected String execute(SOAPMessage soapMessage) throws IOException, SOAPException, TransformerException {
//        SOAPMessage soapMessage = getSoapMessageFromString(
//                fromXmlToString(Consts.BASE_XML_PATH + path,id)
//        );
//        MessageFactory messageFactory = MessageFactory.newInstance();
//        SOAPMessage soapMessage = messageFactory.createMessage();
//        SOAPPart soapPart = soapMessage.getSOAPPart();
//
//        final String libraryNamespace = "lib";
//        final String libraryNamespaceURI = "libraryService";
//
//        SOAPEnvelope envelope = soapPart.getEnvelope();
//        envelope.addNamespaceDeclaration(libraryNamespace,libraryNamespaceURI);
//
//        SOAPBody body = envelope.getBody();
//        SOAPElement getAuthorsRequestElement = body.addChildElement("getAuthorsRequest",libraryNamespace);
//        SOAPElement searchElement = getAuthorsRequestElement.addChildElement("search",libraryNamespace);
//        SOAPElement sortOrderElement = searchElement.addChildElement("orderType",libraryNamespace);
//        sortOrderElement.addTextNode("asc");
//        SOAPElement pageElement = searchElement.addChildElement("page",libraryNamespace);
//        pageElement.addTextNode("1");
//        SOAPElement paginationElement = searchElement.addChildElement("pagination",libraryNamespace);
//        paginationElement.addTextNode("true");
//        SOAPElement sizeElement = searchElement.addChildElement("size",libraryNamespace);
//        sizeElement.addTextNode("10");
        soapMessage.saveChanges();
        SOAPMessage soapResponse = Conn.getSoapConnection().call(soapMessage, Consts.SOAP_ENDPOINT_URL);

        Source sourceContent = soapResponse.getSOAPPart().getContent();
        Transformer transformer = TransformerFactory.newInstance().newTransformer();


        StringWriter writer = new StringWriter();

        StreamResult result = new StreamResult(writer);
        transformer.transform(sourceContent, result);

        return writer.toString();
    }
}

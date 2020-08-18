package Sevice;

import constants.Consts;
import entity.Author;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.soap.SOAPException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.StringReader;

public class AuthorService extends BaseService {
    public Author getAuthor(long id) throws SOAPException, TransformerException, IOException, JAXBException {
        String fullXmlString = execute(createSoapMessageForId(Consts.GET_AUTHOR_XML_PATH, id));

        return getAuthorFromXml(fullXmlString, "<ns2:author>", "</ns2:getAuthorResponse>");
    }

    public void deleteAuthor(long id) throws SOAPException, TransformerException, IOException {
        String fullXmlString = execute(createSoapMessageForId(Consts.DELETE_AUTHOR_XML_PATH, id));

        String s1 = "<ns2:status>";
        String s2 = "</ns2:deleteAuthorResponse>";
        try {
            String xmlString = fullXmlString.
                    substring(fullXmlString.indexOf(s1), fullXmlString.indexOf(s2)).
                    replaceAll("ns2:", "");
            System.out.println(xmlString);
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("Cannot delete author with this id because some books are referenced to him or invalid id");
            throw e;
        }

    }

//    public void getAllAuthors(String orderType, int page, Boolean pagination, int size) throws SOAPException, TransformerException, IOException {
//        execute(createSoapMessageForGetAllAuthors(orderType, page, pagination, size));
//    }

    public Author postAuthor(Author author)
            throws JAXBException, IOException, SOAPException, TransformerException {
        String fullXmlString = execute(
                createSoapMessageForPostAuthor(
                        author.getAuthorId(),
                        author.getAuthorName(),
                        author.getNationality(),
                        author.getBirth(),
                        author.getAuthorDescription()));

        return getAuthorFromXml(fullXmlString, "<ns2:author>", "</ns2:createAuthorResponse>");
    }

    private Author getAuthorFromXml(String xml, String beginPointXmlString, String endpointXmlString) throws JAXBException {
        try {
            String xmlString = xml.
                    substring(xml.indexOf(beginPointXmlString), xml.indexOf(endpointXmlString)).
                    replaceAll("ns2:", "");
//            System.out.println(xmlString);

            JAXBContext jaxbContext;
            jaxbContext = JAXBContext.newInstance(Author.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            StreamSource streamSource = new StreamSource(new StringReader(xmlString));

            JAXBElement<Author> jaxbElement = jaxbUnmarshaller.unmarshal(streamSource, Author.class);

            return jaxbElement.getValue();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Author with this id already exists(post) or not found(get)");
            throw e;
        }
    }
}

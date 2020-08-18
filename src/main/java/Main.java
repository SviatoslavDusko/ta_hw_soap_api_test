import Sevice.AuthorService;
import connection.Conn;

import javax.xml.bind.JAXBException;
import javax.xml.soap.SOAPException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws SOAPException, TransformerException, IOException, JAXBException {
        AuthorService authorService = new AuthorService();
//        authorService.postAuthor(1);
        System.out.println(authorService.getAuthor(20));
//        authorService.getAllAuthors("asc",1,true,10);
//        authorService.deleteAuthor(1);

        Conn.close();
    }
}

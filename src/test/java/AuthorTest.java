import Sevice.AuthorService;
import connection.Conn;
import entity.Author;
import entity.nested.Birth;
import entity.nested.Name;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.xml.bind.JAXBException;
import javax.xml.soap.SOAPException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

public class AuthorTest {
    AuthorService authorService = new AuthorService();

    @Test
    public void getAuthorById() throws IOException, JAXBException, SOAPException, TransformerException {
        Author author = authorService.getAuthor(1);
        Assert.assertEquals((long) author.getAuthorId(), 1L);
        Conn.close();
    }

    @Test
    public void deleteAuthorById() throws SOAPException, TransformerException, IOException {
        authorService.deleteAuthor(1);
        Conn.close();
    }

    @Test
    public void postAuthor() throws IOException, TransformerException, SOAPException, JAXBException {
        Author author = new Author(
                1L,
                new Name("Sviatoslav", "Dusko"),
                "Ukraine",
                new Birth("1997-06-18", "ukraine", "Lviv"),
                "Description");
        Assert.assertEquals(authorService.postAuthor(author), author);
        Conn.close();
    }
}

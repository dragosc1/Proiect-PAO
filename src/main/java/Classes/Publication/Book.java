package Classes.Publication;

import Classes.Services.GenericCRUDService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class Book extends Publication {

    private String ISBN;

    // Book constructor
    public Book(int id, String title, Author author, Section section, int publicationYear, int numberOfCopies, String ISBN) {
        super(id, title, author, section, publicationYear, numberOfCopies);
        this.ISBN = ISBN;
    }

    public Book createCopy() {
        return new Book(id, title, author, section, publication_year, number_of_copies, ISBN);
    }

    // Book string representation
    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author=" + author +
                ", section=" + section +
                ", publicationYear=" + publication_year +
                ", numberOfCopies=" + number_of_copies +
                ", ISBN='" + ISBN + '\'' +
                '}';
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    // book parse result set
    public static Book parseResultSet(ResultSet resultSet) throws SQLException {
        // gather data
        int id = resultSet.getInt("id");

        GenericCRUDService<Publication> publicationService = GenericCRUDService.getInstance();
        publicationService.openConnection();
        Publication publication = publicationService.retrieveOneId(Publication.class, id);
        publicationService.closeConnection();

        String title = publication.title;
        Integer authorId = publication.author_id;
        Integer sectionId = publication.section_id;
        int publicationYear = publication.publication_year;
        int numberOfCopies = publication.number_of_copies;
        String ISBN = resultSet.getString("ISBN");

        // get author and section
        GenericCRUDService<Author> authorService = GenericCRUDService.getInstance();
        GenericCRUDService<Section> sectionService = GenericCRUDService.getInstance();

        authorService.openConnection();
        sectionService.openConnection();
        Author author = null;
        Section section = null;

        if (authorId != null)
            author = authorService.retrieveOneId(Author.class, authorId);

        if (sectionId != null)
            section = sectionService.retrieveOneId(Section.class, sectionId);

        authorService.closeConnection();
        sectionService.closeConnection();

        // return book
        return new Book(id, title, author, section, publicationYear, numberOfCopies, ISBN);
    }
}
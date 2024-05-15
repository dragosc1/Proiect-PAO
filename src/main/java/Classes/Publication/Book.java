package Classes.Publication;

import Classes.Services.GenericCRUDService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class Book extends Publication {

    private String ISBN;

    public Book(int id, String title, Author author, Section section, int publicationYear, int numberOfCopies, String ISBN) {
        super(id, title, author, section, publicationYear, numberOfCopies);
        this.ISBN = ISBN;
    }

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

    public static Book parseResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");

        GenericCRUDService<Publication> publicationService = GenericCRUDService.getInstance();
        Publication publication = publicationService.retrieveOneId(Publication.class, id);

        String title = publication.title;
        Integer authorId = publication.author_id;
        Integer sectionId = publication.section_id;
        int publicationYear = publication.publication_year;
        int numberOfCopies = publication.number_of_copies;
        String ISBN = resultSet.getString("ISBN");

        GenericCRUDService<Author> authorService = GenericCRUDService.getInstance();
        GenericCRUDService<Section> sectionService = GenericCRUDService.getInstance();

        Author author = null;
        Section section = null;

        if (authorId != null)
            author = authorService.retrieveOneId(Author.class, authorId);

        if (sectionId != null)
            section = sectionService.retrieveOneId(Section.class, sectionId);

        return new Book(id, title, author, section, publicationYear, numberOfCopies, ISBN);
    }
}
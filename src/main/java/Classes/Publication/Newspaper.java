package Classes.Publication;

import Classes.Services.GenericCRUDService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Objects;

public class Newspaper extends Publication {
    private Date publication_date;

    public Newspaper(int id, String title, Author author, Section section, int publicationYear, int numberOfCopies, Date publicationDate) {
        super(id, title, author, section, publicationYear, numberOfCopies);
        this.publication_date = publicationDate;
    }

    public Newspaper createCopy() {
        return new Newspaper(id, title, author, section, publication_year, number_of_copies, publication_date);
    }

    public Date getPublicationDate() {
        return publication_date;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publication_date = publicationDate;
    }

    @Override
    public String toString() {
        return "Newspaper{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author=" + author +
                ", section=" + section +
                ", publicationYear=" + publication_year +
                ", numberOfCopies=" + number_of_copies +
                ", publicationDate=" + publication_date +
                '}';
    }

    public static Newspaper parseResultSet(ResultSet resultSet) throws SQLException {
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
        Date publicationDate = resultSet.getDate("publication_date");

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

        return new Newspaper(id, title, author, section, publicationYear, numberOfCopies, publicationDate);
    }
}
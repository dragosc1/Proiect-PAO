package Classes.Publication;

import Classes.Services.GenericCRUDService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class Magazine extends Publication {

    private int issue_number;

    public Magazine(int id, String title, Author author, Section section, int publicationYear, int numberOfCopies, int issueNumber) {
        super(id, title, author, section, publicationYear, numberOfCopies);
        this.issue_number = issueNumber;
    }

    public int getIssueNumber() {
        return issue_number;
    }

    public void setIssueNumber(int issueNumber) {
        this.issue_number = issueNumber;
    }

    @Override
    public String toString() {
        return "Magazine{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author=" + author +
                ", section=" + section +
                ", publicationYear=" + publication_year +
                ", numberOfCopies=" + number_of_copies +
                ", issueNumber=" + issue_number +
                '}';
    }

    public static Magazine parseResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");

        GenericCRUDService<Publication> publicationService = GenericCRUDService.getInstance();
        Publication publication = publicationService.retrieveOneId(Publication.class, id);

        String title = publication.title;
        Integer authorId = publication.author_id;
        Integer sectionId = publication.section_id;
        int publicationYear = publication.publication_year;
        int numberOfCopies = publication.number_of_copies;
        int issueNumber = resultSet.getInt("issue_number");

        GenericCRUDService<Author> authorService = GenericCRUDService.getInstance();
        GenericCRUDService<Section> sectionService = GenericCRUDService.getInstance();

        Author author = null;
        Section section = null;

        if (authorId != null)
            author = authorService.retrieveOneId(Author.class, authorId);

        if (sectionId != null)
            section = sectionService.retrieveOneId(Section.class, sectionId);

        return new Magazine(id, title, author, section, publicationYear, numberOfCopies, issueNumber);
    }
}
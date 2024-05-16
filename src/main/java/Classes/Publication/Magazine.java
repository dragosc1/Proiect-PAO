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

    public Magazine createCopy() {
        return new Magazine(id, title, author, section, publication_year, number_of_copies, issue_number);
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
        publicationService.openConnection();
        Publication publication = publicationService.retrieveOneId(Publication.class, id);
        publicationService.closeConnection();


        String title = publication.title;
        Integer authorId = publication.author_id;
        Integer sectionId = publication.section_id;
        int publicationYear = publication.publication_year;
        int numberOfCopies = publication.number_of_copies;
        int issueNumber = resultSet.getInt("issue_number");

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

        return new Magazine(id, title, author, section, publicationYear, numberOfCopies, issueNumber);
    }
}
package Classes.Publication;

import Classes.Services.GenericCRUDService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class Publication {
    protected int id;
    protected String title;
    protected Author author;
    protected Section section;
    protected int publication_year;
    protected int number_of_copies;

    protected Integer author_id;
    protected Integer section_id;

    public Publication(int id, String title, Author author, Section section, int publicationYear, int numberOfCopies) {
        this.title = title;
        this.author = author;
        this.section = section;
        this.publication_year = publicationYear;
        this.number_of_copies = numberOfCopies;
        if (author != null)
            this.author_id = author.getId();
        else this.author_id = null;
        if (section != null)
            this.section_id = section.getId();
        else this.section_id = null;
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    // Getters and setters for common attributes
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public int getPublicationYear() {
        return publication_year;
    }

    public void setPublicationYear(int publicationYear) {
        this.publication_year = publicationYear;
    }

    public int getNumberOfCopies() {
        return number_of_copies;
    }

    public void setNumberOfCopies(int numberOfCopies) {
        this.number_of_copies = numberOfCopies;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Publication publication = (Publication) obj;
        return publication_year == publication.publication_year &&
                number_of_copies == publication.number_of_copies &&
                Objects.equals(title, publication.title) &&
                Objects.equals(author, publication.author) &&
                Objects.equals(section, publication.section);
    }

    @Override
    public String toString() {
        return "Publication{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author=" + author +
                ", section=" + section +
                ", publication_year=" + publication_year +
                ", number_of_copies=" + number_of_copies +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, section, publication_year, number_of_copies);
    }

    public static Publication parseResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String title = resultSet.getString("title");
        Integer authorId = resultSet.getInt("author_id");
        Integer sectionId = resultSet.getInt("section_id");
        int publicationYear = resultSet.getInt("publication_year");
        int numberOfCopies = resultSet.getInt("number_of_copies");

        GenericCRUDService<Author> authorService = GenericCRUDService.getInstance();
        GenericCRUDService<Section> sectionService = GenericCRUDService.getInstance();

        Author author = null;
        Section section = null;

        if (authorId != null)
            author = authorService.retrieveOneId(Author.class, authorId);

        if (sectionId != null)
            section = sectionService.retrieveOneId(Section.class, sectionId);

        return new Publication(id, title, author, section, publicationYear, numberOfCopies);
    }
}
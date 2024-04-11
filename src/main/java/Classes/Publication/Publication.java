package Classes.Publication;

import java.util.Objects;

public class Publication {
    protected String title;
    protected Author author;
    protected Section section;
    protected int publicationYear;
    protected int numberOfCopies;

    public Publication(String title, Author author, Section section, int publicationYear, int numberOfCopies) {
        this.title = title;
        this.author = author;
        this.section = section;
        this.publicationYear = publicationYear;
        this.numberOfCopies = numberOfCopies;
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
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public int getNumberOfCopies() {
        return numberOfCopies;
    }

    public void setNumberOfCopies(int numberOfCopies) {
        this.numberOfCopies = numberOfCopies;
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
        return publicationYear == publication.publicationYear &&
                numberOfCopies == publication.numberOfCopies &&
                Objects.equals(title, publication.title) &&
                Objects.equals(author, publication.author) &&
                Objects.equals(section, publication.section);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, section, publicationYear, numberOfCopies);
    }
}
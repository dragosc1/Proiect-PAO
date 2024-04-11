package Classes.Publication;

// Book class (extends Publication)
public class Book extends Publication {

    public Book(String title, Author author, Section section, int publicationYear, int numberOfCopies) {
        super(title, author, section, publicationYear, numberOfCopies);
    }

    @Override
    public String toString() {
        return "Book: " + this.title;
    }
}
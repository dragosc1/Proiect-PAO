package Classes.Publication;

// Magazine class (extends Publication)
public class Magazine extends Publication {

    public Magazine(String title, Author author, Section section, int publicationYear, int numberOfCopies) {
        super(title, author, section, publicationYear, numberOfCopies);
    }

    @Override
    public String toString() {
        return "Magazine: " + this.title;
    }
}
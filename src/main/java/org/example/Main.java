package org.example;

import Classes.Actions.Loan;
import Classes.Publication.*;
import Classes.Services.*;
import Classes.User.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Main {

    public static void seeding() {
        // All services
        GenericCRUDService<User> userService = GenericCRUDService.getInstance();
        GenericCRUDService<Author> authorService = GenericCRUDService.getInstance();
        GenericCRUDService<Section> sectionService = GenericCRUDService.getInstance();
        GenericCRUDService<Publication> publicationService = GenericCRUDService.getInstance();
        GenericCRUDService<Book> bookService = GenericCRUDService.getInstance();
        GenericCRUDService<Magazine> magazineService = GenericCRUDService.getInstance();
        GenericCRUDService<Newspaper> newspaperService = GenericCRUDService.getInstance();
        GenericCRUDService<Loan> loanService = GenericCRUDService.getInstance();

        userService.openConnection();
        authorService.openConnection();
        sectionService.openConnection();
        publicationService.openConnection();
        bookService.openConnection();
        magazineService.openConnection();
        newspaperService.openConnection();
        loanService.openConnection();

        // Deleting all data
        loanService.deleteAll(Loan.class);
        userService.deleteAll(User.class);
        bookService.deleteAll(Book.class);
        magazineService.deleteAll(Magazine.class);
        newspaperService.deleteAll(Newspaper.class);
        publicationService.deleteAll(Publication.class);
        sectionService.deleteAll(Section.class);
        authorService.deleteAll(Author.class);

        // Add users
        User user1 = new User(1, "John Doe", "123 Main Street", "555-1234");
        User user2 = new User(2, "Jane Smith", "456 Oak Avenue", "555-5678");
        User user3 = new User(3, "Alice Johnson", "789 Elm Street", "555-9876");
        User user4 = new User(4, "Michael Brown", "567 Pine Street", "555-5432");
        User user5 = new User(5, "Emily Davis", "890 Maple Street", "555-7890");

        userService.insert(user1);
        userService.insert(user2);
        userService.insert(user3);
        userService.insert(user4);
        userService.insert(user5);

        // Adding authors
        Author author1 = new Author(1, "King", "Stephen");
        Author author2 = new Author(2, "Rowling", "J.K.");
        Author author3 = new Author(3, "Christie", "Agatha");
        Author author4 = new Author(4, "Brown", "Dan");
        Author author5 = new Author(5, "Murakami", "Haruki");
        Author author6 = new Author(6, "Hovey", "Grosvenor Gilbert");
        Author author7 = new Author(7, "Luce", "Henry");
        Author author8 = new Author(8, "Porter", "Rafus");
        Author author9 = new Author(9, "Turnure", "Arthur");
        Author author10 = new Author(10, "Ross", "Harold Wallace");

        authorService.insert(author1);
        authorService.insert(author2);
        authorService.insert(author3);
        authorService.insert(author4);
        authorService.insert(author5);
        authorService.insert(author6);
        authorService.insert(author7);
        authorService.insert(author8);
        authorService.insert(author9);
        authorService.insert(author10);

        // Adding sections
        Section section1 = new Section(1, "Fiction", "Explore imaginary worlds and characters.");
        Section section2 = new Section(2, "Fantasy", "Discover magical realms and epic adventures.");
        Section section3 = new Section(3, "Mystery", "Solve intriguing puzzles and uncover hidden secrets.");
        Section section4 = new Section(4, "Science Fiction", "Journey to futuristic worlds and encounter advanced technology.");
        Section section5 = new Section(5, "Romance", "Experience love stories and heartwarming relationships.");
        Section section6 = new Section(6, "Geography", "Learn about Earth's landscapes, climates, and cultures.");
        Section section7 = new Section(7, "News", "Stay informed with the latest current events and headlines.");
        Section section8 = new Section(8, "Science", "Explore the wonders of the natural world and scientific discoveries.");
        Section section9 = new Section(9, "Fashion", "Discover the latest trends in clothing, accessories, and style.");
        Section section10 = new Section(10, "Satire", "Enjoy humorous and satirical commentary on politics, society, and culture.");
        Section section11 = new Section(11, "Politics", "News related to politics and government policies");
        Section section12 = new Section(12, "Business", "News related to business and finance");
        Section section13 = new Section(13, "Sports", "News related to sports events and activities");
        Section section14 = new Section(14, "Entertainment", "News related to entertainment industry");
        Section section15 = new Section(15, "Health", "News related to health and wellness");

        sectionService.insert(section1);
        sectionService.insert(section2);
        sectionService.insert(section3);
        sectionService.insert(section4);
        sectionService.insert(section5);
        sectionService.insert(section6);
        sectionService.insert(section7);
        sectionService.insert(section8);
        sectionService.insert(section9);
        sectionService.insert(section10);
        sectionService.insert(section11);
        sectionService.insert(section12);
        sectionService.insert(section13);
        sectionService.insert(section14);
        sectionService.insert(section15);

        // Adding books
        Publication publication1 = new Publication(1, "The Shining", author1, section1, 1977, 2);
        Publication publication2 = new Publication(2, "Harry Potter and the Philosopher's Stone", author2, section2, 1997, 1);
        Publication publication3 = new Publication(3, "Murder on the Orient Express", author3, section3, 1934, 1);
        Publication publication4 = new Publication(4, "The Da Vinci Code", author4, section4, 2003, 3);
        Publication publication5 = new Publication(5, "Norwegian Wood", author5, section5, 1987, 4);
        Publication publication6 = new Publication(6, "To Kill a Mockingbird", author1, section1, 1960, 5);
        Publication publication7 = new Publication(7, "The Hobbit", author2, section2, 1937, 0);
        Publication publication8 = new Publication(8, "The Catcher in the Rye", author3, section1, 1951, 0);
        Publication publication9 = new Publication(9, "1984", author4, section4, 1949, 5);
        Publication publication10 = new Publication(10, "Pride and Prejudice", author5, section5, 1813, 3);

        publicationService.insert(publication1);
        publicationService.insert(publication2);
        publicationService.insert(publication3);
        publicationService.insert(publication4);
        publicationService.insert(publication5);
        publicationService.insert(publication6);
        publicationService.insert(publication7);
        publicationService.insert(publication8);
        publicationService.insert(publication9);
        publicationService.insert(publication10);


        Book book1 = new Book(1, "The Shining", author1, section1, 1977, 2, "9780385121675");
        Book book2 = new Book(2, "Harry Potter and the Philosopher's Stone", author2, section2, 1997, 1, "9781408855611");
        Book book3 = new Book(3, "Murder on the Orient Express", author3, section3, 1934, 1, "9780007119318");
        Book book4 = new Book(4, "The Da Vinci Code", author4, section4, 2003, 3, "9780307474278");
        Book book5 = new Book(5, "Norwegian Wood", author5, section5, 1987, 4, "9780375704024");
        Book book6 = new Book(6, "To Kill a Mockingbird", author1, section1, 1960, 5, "9780061120084");
        Book book7 = new Book(7, "The Hobbit", author2, section2, 1937, 0, "9780261102217");
        Book book8 = new Book(8, "The Catcher in the Rye", author3, section1, 1951, 0, "9780316769488");
        Book book9 = new Book(9, "1984", author4, section4, 1949, 5, "9780451524935");
        Book book10 = new Book(10, "Pride and Prejudice", author5, section5, 1813, 3, "9780486284736");

        bookService.insert(book1);
        bookService.insert(book2);
        bookService.insert(book3);
        bookService.insert(book4);
        bookService.insert(book5);
        bookService.insert(book6);
        bookService.insert(book7);
        bookService.insert(book8);
        bookService.insert(book9);
        bookService.insert(book10);

        // Adding publication
        Publication publication11 = new Publication(11, "National Geographic", author6, section6, 1888, 1);
        Publication publication12 = new Publication(12, "Time", author7, section7, 1923, 2);
        Publication publication13 = new Publication(13, "Scientific American", author8, section8, 1845, 3);
        Publication publication14 = new Publication(14, "Vogue", author9, section9, 1892, 5);
        Publication publication15 = new Publication(15, "The New Yorker", author10, section10, 1925, 0);

        publicationService.insert(publication11);
        publicationService.insert(publication12);
        publicationService.insert(publication13);
        publicationService.insert(publication14);
        publicationService.insert(publication15);

        // Adding magazines
        Magazine magazine1 = new Magazine(11, "National Geographic", author6, section6, 1888, 1, 207);
        Magazine magazine2 = new Magazine(12, "Time", author7, section7, 1923, 2, 1);
        Magazine magazine3 = new Magazine(13, "Scientific American", author8, section8, 1845, 3, 1);
        Magazine magazine4 = new Magazine(14, "Vogue", author9, section9, 1892, 5, 1);
        Magazine magazine5 = new Magazine(15, "The New Yorker", author10, section10, 1925, 0, 1);


        magazineService.insert(magazine1);
        magazineService.insert(magazine2);
        magazineService.insert(magazine3);
        magazineService.insert(magazine4);
        magazineService.insert(magazine5);


        Publication publication16 = new Publication(16, "The Onion", null, section10, 1988, 1);
        Publication publication17 = new Publication(17, "The Washington Post", null, section11, 1877, 2);
        Publication publication18 = new Publication(18, "The Wall Street Journal", null, section12, 1889, 3);
        Publication publication19 = new Publication(19, "ESPN", null, section13, 1998, 4);
        Publication publication20 = new Publication(20, "The Guardian", null, section14, 1821, 0);

        publicationService.insert(publication16);
        publicationService.insert(publication17);
        publicationService.insert(publication18);
        publicationService.insert(publication19);
        publicationService.insert(publication20);

        Newspaper newspaper1 = new Newspaper(16, "The Onion", null, section10, 1988, 1, new Date(123, 2, 1)); // March 1, 2023
        Newspaper newspaper2 = new Newspaper(17, "The Washington Post", null, section11, 1877, 2, new Date(122, 10, 10)); // November 10, 2022
        Newspaper newspaper3 = new Newspaper(18, "The Wall Street Journal", null, section12, 1889, 3, new Date(123, 7, 20)); // August 20, 2023
        Newspaper newspaper4 = new Newspaper(19, "ESPN", null, section13, 1998, 4, new Date(124, 0, 5)); // January 5, 2024
        Newspaper newspaper5 = new Newspaper(20, "The Guardian", null, section14, 1821, 0, new Date(123, 8, 25)); // September 25, 2023


        newspaperService.insert(newspaper1);
        newspaperService.insert(newspaper2);
        newspaperService.insert(newspaper3);
        newspaperService.insert(newspaper4);
        newspaperService.insert(newspaper5);

        // Loans
        Loan loan1 = new Loan(1, user1, book1, new Date(), null, 1, book1.getId());
        Loan loan2 = new Loan(2, user2, magazine1, new Date(), null, 2, magazine1.getId());
        Loan loan3 = new Loan(3, user3, book2, new Date(), null,3, book2.getId());
        Loan loan4 = new Loan(4, user4, book3, new Date(), null, 4, book3.getId());
        Loan loan5 = new Loan(5, user5, book4, new Date(), null, 5, book4.getId());

        LoanService loanservice2 = LoanService.getInstance();
        loanservice2.openConnection();

        if (loanservice2.canMakeLoan(loan1))
            loanService.insert(loan1);
        else System.out.println("The Publication can't be loaned");
        if (loanservice2.canMakeLoan(loan2))
            loanService.insert(loan2);
        else System.out.println("The publication can't be loaned");
        if (loanservice2.canMakeLoan(loan3))
            loanService.insert(loan3);
        else System.out.println("The publication can't be loaned");
        if (loanservice2.canMakeLoan(loan4))
            loanService.insert(loan4);
        else System.out.println("The publication can't be loaned");
        if (loanservice2.canMakeLoan(loan5))
            loanService.insert(loan5);
        else System.out.println("The publication can't be loaned");

        loanservice2.closeConnection();

        userService.closeConnection();
        authorService.closeConnection();
        sectionService.closeConnection();
        publicationService.closeConnection();
        bookService.closeConnection();
        magazineService.closeConnection();
        newspaperService.closeConnection();
        loanService.closeConnection();
    }
    public static void main(String[] args) {
        // Seeding data
        seeding();

        // 1. Search all publications in a section
        PublicationService publicationService = PublicationService.getInstance();
        publicationService.openConnection();
        publicationService.searchPublicationsFromASection("Fiction");
        publicationService.closeConnection();

        // 2. Show all library users
        UserService userService = UserService.getInstance();
        userService.displayAllLibraryUsers();
        userService.closeConnection();

        // 3. Display all books / magazines / newspapers ordered by title
        publicationService.openConnection();
        publicationService.DisplayAllTypesOfPublicationsOrderedByTitle();
        publicationService.closeConnection();

        // 4. Display all publications which can be loaned
        publicationService.openConnection();
        publicationService.displayAllPublicationsWhichCanBeLoaned();
        publicationService.closeConnection();

        // 5. Display all publications made by an author
        publicationService.openConnection();
        publicationService.searchPublicationsByAuthor("King", "Stephen");
        publicationService.closeConnection();

        // 6. Display all publications loaned by a user
        LoanService loanService = LoanService.getInstance();
        loanService.openConnection();
        loanService.searchPublicationsBorrowedByUser(1);
        loanService.closeConnection();

        // 7. Make a query such that you can return a Publication from a user perspective.
        loanService.openConnection();
        loanService.returnPublication(1, 1);
        loanService.closeConnection();

        // 8. Display books sorted by publication year
        publicationService.openConnection();
        publicationService.displayBooksSortedByPublicationYear();
        publicationService.closeConnection();

        // 9. Display magazines sorted by number of copies
        publicationService.openConnection();
        publicationService.displayMagazinesSortedByNumberOfCopies();
        publicationService.closeConnection();

        // 10. Display publications sorted by author name
        publicationService.openConnection();
        publicationService.displayPublicationsSortedByAuthorName();
        publicationService.closeConnection();
    }
}
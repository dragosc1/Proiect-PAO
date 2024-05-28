package Classes.Menu;

import Classes.Actions.Loan;
import Classes.Publication.*;
import Classes.Services.*;
import Classes.User.User;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Scanner;

public class Menu {
    private static Menu instance;

    private PublicationService publicationService;
    private UserService userService;
    private LoanService loanService;
    private AuthorService authorService;
    private SectionService sectionService;

    private Menu() {
        this.publicationService = PublicationService.getInstance();
        this.userService = UserService.getInstance();
        this.loanService = LoanService.getInstance();
        this.authorService = AuthorService.getInstance();
        this.sectionService = SectionService.getInstance();
    }

    public static synchronized Menu getInstance() {
        if (instance == null) {
            instance = new Menu();
        }
        return instance;
    }

    // Menu operations
    public void displayMenu() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\nLibrary Menu:");
            System.out.println("1. Search all publications in a section");
            System.out.println("2. Show all library users");
            System.out.println("3. Display all books / magazines / newspapers ordered by title");
            System.out.println("4. Display all publications which can be loaned");
            System.out.println("5. Display all publications made by an author");
            System.out.println("6. Display all publications loaned by a user");
            System.out.println("7. Return a publication from a user");
            System.out.println("8. Display books sorted by publication year");
            System.out.println("9. Display magazines sorted by number of copies");
            System.out.println("10. Display publications sorted by author name");
            System.out.println("11. Delete newspapers from a year");
            System.out.println("12. Insert an author");
            System.out.println("13. Insert a section");
            System.out.println("14. Insert a new publication");
            System.out.println("15. Insert a new book");
            System.out.println("16. Insert a new magazine");
            System.out.println("17. Insert a new newspaper");
            System.out.println("18. Add new Loan");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter section name: ");
                    String sectionName = scanner.nextLine();
                    publicationService.openConnection();
                    publicationService.searchPublicationsFromASection(sectionName);
                    publicationService.closeConnection();
                    break;
                case 2:
                    userService.openConnection();
                    userService.displayAllLibraryUsers();
                    userService.closeConnection();
                    break;
                case 3:
                    publicationService.openConnection();
                    publicationService.DisplayAllTypesOfPublicationsOrderedByTitle();
                    publicationService.closeConnection();
                    break;
                case 4:
                    publicationService.openConnection();
                    publicationService.displayAllPublicationsWhichCanBeLoaned();
                    publicationService.closeConnection();
                    break;
                case 5:
                    System.out.print("Enter author's first name: ");
                    String firstName = scanner.nextLine();
                    System.out.print("Enter author's last name: ");
                    String lastName = scanner.nextLine();
                    publicationService.openConnection();
                    publicationService.searchPublicationsByAuthor(lastName, firstName);
                    publicationService.closeConnection();
                    break;
                case 6:
                    System.out.print("Enter user ID: ");
                    int userId = scanner.nextInt();
                    scanner.nextLine();
                    loanService.openConnection();
                    loanService.searchPublicationsBorrowedByUser(userId);
                    loanService.closeConnection();
                    break;
                case 7:
                    System.out.print("Enter user ID: ");
                    int returnUserId = scanner.nextInt();
                    System.out.print("Enter publication ID: ");
                    int publicationId = scanner.nextInt();
                    scanner.nextLine();
                    loanService.openConnection();
                    loanService.returnPublication(returnUserId, publicationId);
                    loanService.closeConnection();
                    break;
                case 8:
                    publicationService.openConnection();
                    publicationService.displayBooksSortedByPublicationYear();
                    publicationService.closeConnection();
                    break;
                case 9:
                    publicationService.openConnection();
                    publicationService.displayMagazinesSortedByNumberOfCopies();
                    publicationService.closeConnection();
                    break;
                case 10:
                    publicationService.openConnection();
                    publicationService.displayPublicationsSortedByAuthorName();
                    publicationService.closeConnection();
                    break;
                case 11:
                    System.out.print("Enter year: ");
                    int year = scanner.nextInt();
                    scanner.nextLine();
                    publicationService.openConnection();
                    publicationService.deleteNewspapersFromYear(year);
                    publicationService.closeConnection();
                    break;
                case 12:
                    System.out.print("Enter author's first name: ");
                    String newFirstName = scanner.nextLine();
                    System.out.print("Enter author's last name: ");
                    String newLastName = scanner.nextLine();
                    authorService.openConnection();
                    authorService.insertAuthorIfItsNot(newLastName, newFirstName);
                    authorService.closeConnection();
                    break;
                case 13:
                    System.out.print("Enter section name: ");
                    String newSectionName = scanner.nextLine();
                    System.out.print("Enter section description: ");
                    String description = scanner.nextLine();
                    sectionService.openConnection();
                    sectionService.insertSectionIFItsNot(newSectionName, description);
                    sectionService.closeConnection();
                    break;
                case 14:
                    System.out.print("Enter publication title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter author's first name: ");
                    String pubFirstName = scanner.nextLine();
                    System.out.print("Enter author's last name: ");
                    String pubLastName = scanner.nextLine();
                    System.out.print("Enter section name: ");
                    String pubSectionName = scanner.nextLine();
                    System.out.print("Enter publication year: ");
                    int pubYear = scanner.nextInt();
                    System.out.print("Enter number of copies: ");
                    int copies = scanner.nextInt();
                    scanner.nextLine();

                    authorService.openConnection();
                    authorService.insertAuthorIfItsNot(pubFirstName, pubLastName);
                    Author author = authorService.getAuthorByFirstAndLastName(pubFirstName, pubLastName);
                    authorService.closeConnection();

                    sectionService.openConnection();
                    sectionService.insertSectionIFItsNot(pubSectionName, "");
                    Section section = sectionService.retrieveSectionByName(pubSectionName);
                    sectionService.closeConnection();

                    publicationService.openConnection();
                    Publication publication = new Publication(publicationService.getNextId(), title, author, section, pubYear, copies);
                    publicationService.addNewPublication(publication);
                    publicationService.closeConnection();
                    break;
                case 15:
                    System.out.print("Enter book title: ");
                    String bookTitle = scanner.nextLine();
                    System.out.print("Enter author's first name: ");
                    String bookFirstName = scanner.nextLine();
                    System.out.print("Enter author's last name: ");
                    String bookLastName = scanner.nextLine();
                    System.out.print("Enter section name: ");
                    String bookSectionName = scanner.nextLine();
                    System.out.print("Enter publication year: ");
                    int bookYear = scanner.nextInt();
                    System.out.print("Enter number of copies: ");
                    int bookCopies = scanner.nextInt();
                    System.out.print("Enter ISBN: ");
                    scanner.nextLine();
                    String ISBN = scanner.nextLine();

                    authorService.openConnection();
                    authorService.insertAuthorIfItsNot(bookFirstName, bookLastName);
                    Author bookAuthor = authorService.getAuthorByFirstAndLastName(bookFirstName, bookLastName);
                    authorService.closeConnection();

                    sectionService.openConnection();
                    sectionService.insertSectionIFItsNot(bookSectionName, "");
                    Section bookSection = sectionService.retrieveSectionByName(bookSectionName);
                    sectionService.closeConnection();

                    publicationService.openConnection();
                    Book book = new Book(publicationService.getNextId(), bookTitle, bookAuthor, bookSection, bookYear, bookCopies, ISBN);
                    publicationService.addNewBook(book);
                    publicationService.closeConnection();
                    break;
                case 16:
                    System.out.print("Enter magazine title: ");
                    String magazineTitle = scanner.nextLine();
                    System.out.print("Enter author's first name: ");
                    String magazineFirstName = scanner.nextLine();
                    System.out.print("Enter author's last name: ");
                    String magazineLastName = scanner.nextLine();
                    System.out.print("Enter section name: ");
                    String magazineSectionName = scanner.nextLine();
                    System.out.print("Enter publication year: ");
                    int magazineYear = scanner.nextInt();
                    System.out.print("Enter number of copies: ");
                    int magazineCopies = scanner.nextInt();
                    System.out.print("Enter issue number: ");
                    int issueNumber = scanner.nextInt();
                    scanner.nextLine();

                    authorService.openConnection();
                    authorService.insertAuthorIfItsNot(magazineFirstName, magazineLastName);
                    Author magazineAuthor = authorService.getAuthorByFirstAndLastName(magazineFirstName, magazineLastName);
                    authorService.closeConnection();

                    sectionService.openConnection();
                    sectionService.insertSectionIFItsNot(magazineSectionName, "");
                    Section magazineSection = sectionService.retrieveSectionByName(magazineSectionName);
                    sectionService.closeConnection();

                    publicationService.openConnection();
                    Magazine magazine = new Magazine(publicationService.getNextId(), magazineTitle, magazineAuthor, magazineSection, magazineYear, magazineCopies, issueNumber);
                    publicationService.addNewMagazine(magazine);
                    publicationService.closeConnection();
                    break;
                case 17:
                    System.out.print("Enter newspaper title: ");
                    String newspaperTitle = scanner.nextLine();
                    System.out.print("Enter author's first name: ");
                    String newspaperFirstName = scanner.nextLine();
                    System.out.print("Enter author's last name: ");
                    String newspaperLastName = scanner.nextLine();
                    System.out.print("Enter section name: ");
                    String newspaperSectionName = scanner.nextLine();
                    System.out.print("Enter publication year: ");
                    int newspaperYear = scanner.nextInt();
                    System.out.print("Enter number of copies: ");
                    int newspaperCopies = scanner.nextInt();
                    System.out.print("Enter publication date (yyyy-mm-dd): ");
                    scanner.nextLine();
                    String publicationDateString = scanner.nextLine();
                    Date publicationDate = Date.valueOf(publicationDateString);

                    authorService.openConnection();
                    authorService.insertAuthorIfItsNot(newspaperFirstName, newspaperLastName);
                    Author newspaperAuthor = authorService.getAuthorByFirstAndLastName(newspaperFirstName, newspaperLastName);
                    authorService.closeConnection();

                    sectionService.openConnection();
                    sectionService.insertSectionIFItsNot(newspaperSectionName, "");
                    Section newspaperSection = sectionService.retrieveSectionByName(newspaperSectionName);
                    sectionService.closeConnection();

                    publicationService.openConnection();
                    Newspaper newspaper = new Newspaper(publicationService.getNextId(), newspaperTitle, newspaperAuthor, newspaperSection, newspaperYear, newspaperCopies, publicationDate);
                    publicationService.addNewNewspaper(newspaper);
                    publicationService.closeConnection();
                    break;
                case 18:
                    // Get user input for loan details
                    System.out.print("Enter user ID: ");
                    userId = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Enter publication ID: ");
                    publicationId = scanner.nextInt();
                    scanner.nextLine();

                    // Get loan date (assuming current date)
                    Timestamp loanDate = new Timestamp(System.currentTimeMillis());

                    GenericCRUDService<User> userGenericCRUDService = GenericCRUDService.getInstance();
                    GenericCRUDService<Publication> publicationGenericCRUDService = GenericCRUDService.getInstance();

                    userGenericCRUDService.openConnection();
                    publicationGenericCRUDService.openConnection();

                    LoanService loanService = LoanService.getInstance();
                    loanService.openConnection();

                    // Create a new loan object
                    Loan newLoan = new Loan(loanService.getNextId(), userGenericCRUDService.retrieveOneId(User.class, userId), publicationGenericCRUDService.retrieveOneId(Publication.class, publicationId), loanDate, null, userId, publicationId);

                    // Add the new loan using the LoanService
                    loanService.addNewLoan(newLoan);
                    loanService.closeConnection();
                    userGenericCRUDService.closeConnection();
                    publicationGenericCRUDService.closeConnection();

                    break;
                case 0:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        }
        scanner.close();
    }
}

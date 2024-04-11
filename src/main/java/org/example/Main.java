package org.example;

import Classes.Actions.Loan;
import Classes.Publication.*;
import Classes.Services.LoanService;
import Classes.Services.PublicationService;
import Classes.Services.UserService;
import Classes.User.User;

import java.util.Date;
import java.util.List;

public class Main {
    private static UserService userService;
    private static PublicationService publicationService;
    private static LoanService loanService;


    public static void seeding() {
        User user1 = new User("John Doe", "123 Main Street", "555-1234");
        User user2 = new User("Jane Smith", "456 Oak Avenue", "555-5678");
        User user3 = new User("Alice Johnson", "789 Elm Street", "555-9876");
        User user4 = new User("Michael Brown", "567 Pine Street", "555-5432");
        User user5 = new User("Emily Davis", "890 Maple Street", "555-7890");

        userService = new UserService();
        userService.addUser(user1);
        userService.addUser(user2);
        userService.addUser(user3);
        userService.addUser(user4);
        userService.addUser(user5);

        // Adding authors
        Author author1 = new Author("King", "Stephen");
        Author author2 = new Author("Rowling", "J.K.");
        Author author3 = new Author("Christie", "Agatha");
        Author author4 = new Author("Brown", "Dan");
        Author author5 = new Author("Murakami", "Haruki");
        Author author6 = new Author("Hovey", "Grosvenor Gilbert");
        Author author7 = new Author("Luce", "Henry");
        Author author8 = new Author("Porter", "Rafus");
        Author author9 = new Author("Turnure", "Arthur");
        Author author10 = new Author("Ross", "Harold Wallace");

        // Adding sections
        Section section1 = new Section("Fiction");
        Section section2 = new Section("Fantasy");
        Section section3 = new Section("Mystery");
        Section section4 = new Section("Science Fiction");
        Section section5 = new Section("Romance");
        Section section6 = new Section("Geography");
        Section section7 = new Section("News");
        Section section8 = new Section("Science");
        Section section9 = new Section("Fashion");
        Section section10 = new Section("Satire");

        // Adding books
        Book book1 = new Book("The Shining", author1, section1, 1977, 2);
        Book book2 = new Book("Harry Potter and the Philosopher's Stone", author2, section2, 1997, 1);
        Book book3 = new Book("Murder on the Orient Express", author3, section3, 1934, 1);
        Book book4 = new Book("The Da Vinci Code", author4, section4, 2003, 3);
        Book book5 = new Book("Norwegian Wood", author5, section5, 1987, 4);
        Book book6 = new Book("To Kill a Mockingbird", author1, section1, 1960, 5);
        Book book7 = new Book("The Hobbit", author2, section2, 1937, 0);
        Book book8 = new Book("The Catcher in the Rye", author3, section1, 1951, 0);
        Book book9 = new Book("1984", author4, section4, 1949, 5);
        Book book10 = new Book("Pride and Prejudice", author5, section5, 1813, 3);

        // Adding magazines
        Magazine magazine1 = new Magazine("National Geographic", author6, section6, 1888, 1);
        Magazine magazine2 = new Magazine("Time", author7, section7, 1923, 2);
        Magazine magazine3 = new Magazine("Scientific American", author8, section8, 1845, 3);
        Magazine magazine4 = new Magazine("Vogue", author9, section9, 1892, 5);
        Magazine magazine5 = new Magazine("The New Yorker", author10, section10, 1925, 0);

        // Adding to the publication service
        publicationService = new PublicationService();
        publicationService.addPublication(book1);
        publicationService.addPublication(book2);
        publicationService.addPublication(book3);
        publicationService.addPublication(book4);
        publicationService.addPublication(book5);
        publicationService.addPublication(book6);
        publicationService.addPublication(book7);
        publicationService.addPublication(book8);
        publicationService.addPublication(book9);
        publicationService.addPublication(book10);

        publicationService.addPublication(magazine1);
        publicationService.addPublication(magazine2);
        publicationService.addPublication(magazine3);
        publicationService.addPublication(magazine4);
        publicationService.addPublication(magazine5);


        // Loans
        Loan loan1 = new Loan(user1, book1, new Date(), null);
        Loan loan2 = new Loan(user2, magazine1, new Date(), null);
        Loan loan3 = new Loan(user3, book2, new Date(), null);
        Loan loan4 = new Loan(user4, book3, new Date(), null);
        Loan loan5 = new Loan(user5, book4, new Date(), null);

        loanService = new LoanService();
        loanService.addLoan(loan1);
        loanService.addLoan(loan2);
        loanService.addLoan(loan3);
        loanService.addLoan(loan4);
        loanService.addLoan(loan5);
    }
    public static void main(String[] args) {
        // Introducere date
        seeding();

        // 1. Cautarea tuturor publicatiilor dintr-o anumita sectie
        System.out.println("Searching for publications in the 'Fiction' section:");
        for (Publication publication : publicationService.searchPublicationsBySection(new Section("Fiction"))) {
            System.out.println(publication.getTitle());
        }
        System.out.println();


        // 2. Afisarea tuturor utilizatorilor bibliotecii
        System.out.println("Displaying all library users:");
        for (User user : userService.getUserList()) {
            System.out.println("User name: " + user.getName());
            System.out.println("Address: " + user.getAddress());
            System.out.println("Phone number: " + user.getPhoneNumber());
            System.out.println();
        }

        // 3. Sa se afiseze cartile / revistele in ordinea crescatoare a titlului
        System.out.println("Books / Magazines in ascending order of title\n");
        System.out.println("----- Books -----");
        for (Book book : publicationService.getBooksSortedByTitle()) {
            System.out.println(book);
        }
        System.out.println();
        System.out.println("----- Magazines -----");
        for (Magazine magazine : publicationService.getMagazinesSortedByTitle()) {
            System.out.println(magazine);
        }

        // 4. Sa se afiseze publicațiile disponibile pentru împrumut
        System.out.println("\nPublications available for loan:");
        for (Publication publication : publicationService.getPublicationsAvailableForLoan(loanService))
            System.out.println(publication.getTitle());

        // 5. Sa se afiseze publicatiile unui autor
        System.out.println("\nPublications by Stephen King are:\n");
        Author author = new Author("Stephen", "King");
        List<Publication> authorPublications = publicationService.searchPublicationsByAuthor(author);
        for (Publication publication : authorPublications) {
            System.out.println(publication.getTitle());
        }


        // 6. Sa se afiseze toate publicatiile imprumutate de catre un utilizator
        User user = new User("John Doe", "123 Main Street", "555-1234");
        List<Publication> borrowedPublications = loanService.searchPublicationsBorrowedByUser(user);
        System.out.println();
        System.out.println("Publications borrowed by user " + user.getName() + ":");
        for (Publication publication : borrowedPublications) {
            System.out.println("Title: " + publication.getTitle());
        }

        // 7. Sa se poata returna o carte din perspectiva unui utilizator
        Section section = new Section("Fiction");
        Book book = new Book("The Shining", author, section, 1977, 1);

        loanService.returnPublication(user, book);
        System.out.println();

        // 8. Afisarea cartilor sortate dupa anul de publicatie
        System.out.println("Books sorted by publication year:");
        for (Book sortedBook : publicationService.getBooksSortedByYear()) {
            System.out.println(sortedBook);
        }
        System.out.println();

        // 9. Afisarea revistelor sortate dupa numarul de exemplare
        System.out.println("Magazines sorted by number of copies:");
        for (Magazine magazine : publicationService.getMagazinesSortedByNumberOfCopies()) {
            System.out.println(magazine);
        }
        System.out.println();

        // 10. Afisarea publicatiilor sortate dupa numele autorului
        System.out.println("Publications sorted by author name:");
        for (Publication publication : publicationService.getPublicationsSortedByAuthorName()) {
            System.out.println(publication);
        }

    }
}
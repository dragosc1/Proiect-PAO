package Classes.Services;

import Classes.Actions.Loan;
import Classes.Publication.Publication;
import Classes.User.User;

import java.util.*;

public class LoanService {
    private Set<Loan> loanList;

    public LoanService() {
        loanList = new TreeSet<>(Comparator.comparing(Loan::getLoanDate));
    }

    // Methods for managing loans
    public void addLoan(Loan loan) {
        for (Loan existingLoan : loanList) {
            if (existingLoan.getPublication().equals(loan.getPublication())
                    && existingLoan.getUser().equals(loan.getUser()) && existingLoan.getReturnDate() == null) {
                System.out.println("A loan for the same publication and user already exists!");
                return;
            }
        }

        if (loan.getReturnDate() == null || loan.getReturnDate().after(new Date())) {
            if (loan.getPublication().getNumberOfCopies() > 0) {
                loan.getPublication().setNumberOfCopies(loan.getPublication().getNumberOfCopies() - 1);
                loanList.add(loan);
            } else {
                System.out.println("No more copies available for loan!");
            }
        } else {
            System.out.println("Return date must be null or in the future!");
        }
    }

    public List<Publication> searchPublicationsBorrowedByUser(User user) {
        List<Publication> borrowedPublications = new ArrayList<>();

        for (Loan loan : loanList) {
            if (loan.getUser().equals(user)) {
                borrowedPublications.add(loan.getPublication());
            }
        }

        return borrowedPublications;
    }

    public void returnPublication(User user, Publication publication) {
        for (Loan loan : loanList) {
            if (loan.getUser().equals(user) && loan.getPublication().equals(publication) && loan.getReturnDate() == null) {
                loan.setReturnDate(new Date());
                loan.getPublication().setNumberOfCopies(loan.getPublication().getNumberOfCopies() + 1);
                return;
            }
        }
        System.out.println("The publication was not borrowed by this user.");
    }

    public Set<Loan> getLoanList() {
        return loanList;
    }
}

package Classes.Actions;

import Classes.Publication.Publication;
import Classes.User.User;

import java.util.Date;
import java.util.Objects;

// Loan class
public class Loan {
    private User user;
    private Publication publication;
    private Date loanDate;
    private Date returnDate;

    public Loan(User user, Publication publication, Date loanDate, Date returnDate) {
        this.user = user;
        this.publication = publication;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
    }

    // Getters and setters
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Publication getPublication() {
        return publication;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
    }

    public Date getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Loan loan = (Loan) obj;
        return Objects.equals(user, loan.user) &&
                Objects.equals(publication, loan.publication) &&
                Objects.equals(loanDate, loan.loanDate) &&
                Objects.equals(returnDate, loan.returnDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, publication, loanDate, returnDate);
    }
}
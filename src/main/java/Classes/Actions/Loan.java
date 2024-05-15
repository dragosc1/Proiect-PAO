package Classes.Actions;

import Classes.Publication.Publication;
import Classes.Services.GenericCRUDService;
import Classes.User.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Objects;

// Loan class
public class Loan {
    private int id;
    private User user;
    private Publication publication;
    private Date loan_date;
    private Date return_date;

    private int user_id;
    private int publication_id;

    public Loan(int id, User user, Publication publication, Date loanDate, Date returnDate, int userId, int publicationId) {
        this.id = id;
        this.user = user;
        this.publication = publication;
        this.loan_date = loanDate;
        this.return_date = returnDate;
        this.user_id = userId;
        this.publication_id = publicationId;
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
        return loan_date;
    }

    public void setLoanDate(Date loanDate) {
        this.loan_date = loanDate;
    }

    public Date getReturnDate() {
        return return_date;
    }

    public void setReturnDate(Date returnDate) {
        this.return_date = returnDate;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "id=" + id +
                ", user=" + user +
                ", publication=" + publication +
                ", loan_date=" + loan_date +
                ", return_date=" + return_date +
                '}';
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
                Objects.equals(loan_date, loan.loan_date) &&
                Objects.equals(return_date, loan.return_date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, publication, loan_date, return_date);
    }

    public static Loan parseResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        // Assuming user_id and publication_id are retrieved from the ResultSet
        int userId = resultSet.getInt("user_id");
        int publicationId = resultSet.getInt("publication_id");
        Date loanDate = resultSet.getDate("loan_date");
        Date returnDate = resultSet.getDate("return_date");

        GenericCRUDService<User> userService = GenericCRUDService.getInstance();
        GenericCRUDService<Publication> publicationService = GenericCRUDService.getInstance();
        User user = userService.retrieveOneId(User.class, userId);
        Publication publication = publicationService.retrieveOneId(Publication.class, publicationId);

        return new Loan(id, user, publication, loanDate, returnDate, userId, publicationId);
    }
}
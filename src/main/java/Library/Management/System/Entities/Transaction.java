package Library.Management.System.Entities;

import Library.Management.System.Enums.TransactionStatus;
import Library.Management.System.Enums.TransactionType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Entity
@Table(name = "transactions")
@Getter
@Setter
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String transactionId;

    private TransactionStatus transactionStatus;

    @CreatedDate
    private Date createdOn;

    private int fineAmount;

    private TransactionType transactionType;


    @JoinColumn
    @ManyToOne
    private LibraryCard libraryCard;


    @JoinColumn
    @ManyToOne
    private Book book;
}
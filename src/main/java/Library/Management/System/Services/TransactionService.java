package Library.Management.System.Services;

import Library.Management.System.Entities.Book;
import Library.Management.System.Entities.LibraryCard;
import Library.Management.System.Entities.Transaction;
import Library.Management.System.Enums.TransactionStatus;
import Library.Management.System.Enums.TransactionType;
import Library.Management.System.Exceptions.BookNotAvailableException;
import Library.Management.System.Exceptions.BookNotFoundException;
import Library.Management.System.Exceptions.CardNotFoundException;
import Library.Management.System.Exceptions.MaxLimitReachedException;
import Library.Management.System.Repository.BookRepository;
import Library.Management.System.Repository.CardRepository;
import Library.Management.System.Repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CardRepository cardRepository;

    public String issueBook(Integer cardId,Integer bookId)throws Exception{

        Transaction transaction = new Transaction();
        transaction.setTransactionType(TransactionType.ISSUED);
        transaction.setTransactionStatus(TransactionStatus.ONGOING);
        transaction.setCreatedOn(new Date());

        //1. Get the book and card Entity from DB

        Optional<Book> bookOptional = bookRepository.findById(bookId);

        if(bookOptional.isEmpty()){
            throw new BookNotFoundException("BookId entered is invalid");
        }

        Optional<LibraryCard> optionalLibraryCard = cardRepository.findById(cardId);

        if(optionalLibraryCard.isEmpty()){
            throw new CardNotFoundException("Card Id Entered is Invalid");
        }

        Book book = bookOptional.get();
        LibraryCard card = optionalLibraryCard.get();
        //2. validate book and card Entity variables

        //Check for availability
        if(book.getIsAvailable()==Boolean.FALSE){
            transaction.setTransactionStatus(TransactionStatus.FAILURE);
            transaction = transactionRepository.save(transaction);
            throw new BookNotAvailableException("Book with the bookId is not available. TransactionId "+transaction.getTransactionId());
        }
        //Check for max book issued
        if(card.getNoOfBooksIssued()>=LibraryCard.MAX_NO_OF_ALLOWED_BOOKS){
            transaction.setTransactionStatus(TransactionStatus.FAILURE);
            transaction = transactionRepository.save(transaction);
            throw new MaxLimitReachedException("You have reached the max limit of issed books" +
                    "please return a book in order to issue new " +
                    "Transaction Id "+transaction.getTransactionId());
        }


        //If you have reached that means all validations are OK

        transaction.setTransactionStatus(TransactionStatus.SUCCESS);
        transaction.setBook(book);
        transaction.setLibraryCard(card);

        //3. update the card and the book status
        book.setIsAvailable(Boolean.FALSE);
        card.setNoOfBooksIssued(card.getNoOfBooksIssued()+1);

        //Save the child as it will cascade to both of the Parents
        transaction = transactionRepository.save(transaction);

        return "The transaction with Id "+transaction.getTransactionId()+" has been saved to the DB";
    }

    public String returnBook(Integer cardId, Integer bookId) throws Exception {

        Transaction transaction = new Transaction();
        transaction.setTransactionType(TransactionType.RETURN);
        transaction.setTransactionStatus(TransactionStatus.ONGOING);
        transaction.setCreatedOn(new Date());

        Optional<Book> bookOptional = bookRepository.findById(bookId);

        if(bookOptional.isEmpty()){
            throw new BookNotFoundException("BookId entered is invalid");
        }

        Optional<LibraryCard> optionalLibraryCard = cardRepository.findById(cardId);

        if(optionalLibraryCard.isEmpty()){
            throw new CardNotFoundException("Card Id Entered is Invalid");
        }

        Book book = bookOptional.get();
        LibraryCard card = optionalLibraryCard.get();

        transaction.setTransactionStatus(TransactionStatus.SUCCESS);
        transaction.setBook(book);
        transaction.setLibraryCard(card);

        List<Transaction> oldTransactions = transactionRepository.findAll();
        Transaction oldTransaction=null;
        for(Transaction temp : oldTransactions){
            if(temp.getTransactionStatus().equals(TransactionStatus.SUCCESS) &&
                temp.getTransactionType().equals(TransactionType.ISSUED) &&
                temp.getBook().getBookId()==bookId && temp.getLibraryCard().getCardId()==cardId){
                oldTransaction = temp;
                break;
            }
        }
        int fine=0;
        Date currDate=new Date();
        Date oldDate=oldTransaction.getCreatedOn();
        int days=oldDate.getDate()-currDate.getDate();
        fine = 5 + days*2;

        transaction.setFineAmount(fine);

        book.setIsAvailable(Boolean.TRUE);
        card.setNoOfBooksIssued(card.getNoOfBooksIssued()-1);

        transaction = transactionRepository.save(transaction);
        return "The transaction with Id "+transaction.getTransactionId()+" has been saved to the DB. "
                +"Fine Amount : "+transaction.getFineAmount();
    }
}
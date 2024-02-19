package Library.Management.System.Controllers;

import Library.Management.System.Services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("issueBook")
    public ResponseEntity<String> issueBook(@RequestParam("cardId")Integer cardId,
                                            @RequestParam("bookId")Integer bookId){

        try{
            String result = transactionService.issueBook(cardId,bookId);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @PostMapping("returnBook")
    public ResponseEntity<String> returnBook(@RequestParam("cardId")Integer cardId,
                                             @RequestParam("bookId")Integer bookId){
        try {
            String result = transactionService.returnBook(cardId, bookId);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }
}
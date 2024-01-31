package Library.Management.System.Controllers;

import Library.Management.System.RequestDtos.AddBookRequest;
import Library.Management.System.Services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("book")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/addBook")
    public String addBook(@RequestBody AddBookRequest addBookRequest){

        String response = bookService.addBook(addBookRequest);
        return response;
    }


}
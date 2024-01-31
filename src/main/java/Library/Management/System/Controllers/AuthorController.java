package Library.Management.System.Controllers;

import Library.Management.System.Services.AuthorService;
import com.example.librarymanagementsystem.RequestDtos.AddAuthorRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @PostMapping("/addAuthor")
    public String addAuthor(@RequestBody AddAuthorRequest addAuthorRequest) {

        String result = authorService.addAuthor(addAuthorRequest);
        return result;
    }



}
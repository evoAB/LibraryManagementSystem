package Library.Management.System.Controllers;


import Library.Management.System.Services.CardService;
import com.example.librarymanagementsystem.RequestDtos.AssociateCardStudentRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/card")
public class CardController {

    @Autowired
    private CardService cardService;

    @PostMapping("generateACard")
    public String addCard(){

        String result = cardService.getFreshCard();
        return result;
    }

    @PutMapping("/associateCardAndStudent")
    public ResponseEntity associateCardAndStudent(@RequestBody AssociateCardStudentRequest associateCardStudentRequest){

        try{
            String result = cardService.associateCardAndStudent(associateCardStudentRequest);
            return new ResponseEntity(result,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }





}

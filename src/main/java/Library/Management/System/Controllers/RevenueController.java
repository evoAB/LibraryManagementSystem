package Library.Management.System.Controllers;

import Library.Management.System.Services.RevenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("revenue")
public class RevenueController {
    @Autowired
    private RevenueService revenueService;
    @GetMapping("revenue")
    public ResponseEntity<String> revenue(){
        try {
            String result = revenueService.totalRevenue();
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }
}

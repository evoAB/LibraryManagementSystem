package Library.Management.System.Controllers;

import Library.Management.System.Entities.Student;
import Library.Management.System.RequestDtos.AddStudentRequest;
import Library.Management.System.Services.StudentService;
import com.example.librarymanagementsystem.RequestDtos.ModifyPhnNoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
public class StudentController {


    @Autowired
    private StudentService studentService;

    @PostMapping("/addStudent")
    public ResponseEntity<String> addStudent(@RequestBody AddStudentRequest addStudentRequest){

        String result = studentService.addStudent(addStudentRequest);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/findById")
    public ResponseEntity findStudentById(@RequestParam("studentId")Integer studentId) {

        try{
            Student student = studentService.findStudentById(studentId);
            return new ResponseEntity(student,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/modifyPhnNo")
    public String modifyPhnNo(@RequestBody ModifyPhnNoRequest modifyPhnNoRequest){

        String result = studentService.modifyPhnNo(modifyPhnNoRequest);

        return result;

    }

    @DeleteMapping("/removeStudent")
    public ResponseEntity removeStudent(@RequestParam("studentId")Integer studentId){
        try{
            String result = studentService.removeStudent(studentId);
            return new ResponseEntity(result,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

}
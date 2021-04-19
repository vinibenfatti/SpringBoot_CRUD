package com.example.spring_crud_um.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController  //Todo o metodo irá ter o @ResponseBody
@RequestMapping(path = "api/v1/student") // EndPoint
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getAllStudents() {
       return studentService.getAllStudents();
    }

    @GetMapping(path = "{studentId}")
    public List<Student> getStudentsById(
        @PathVariable("studentId") Long studentId) throws Exception {
        return studentService.getStudentsById(studentId);
    }

    @PostMapping
    public Student registerNewStudent(@RequestBody Student student) throws  Exception{
        return studentService.addNewStudent(student);
    }

    @DeleteMapping(path = "{studentId}")
    public void deleteStudent(
            @PathVariable("studentId") Long studentId){
        studentService.deleteStudent(studentId);
    }

    @PutMapping(path = "{studentId}")
    public void updateStudent(
            @PathVariable("studentId") Long studentId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email){
       studentService.updateStudent(studentId, name, email);
    }
}

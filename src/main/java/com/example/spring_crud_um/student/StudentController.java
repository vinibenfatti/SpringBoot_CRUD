package com.example.spring_crud_um.student;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getAllStudents() {
       return studentService.getAllStudents();
    }

    @GetMapping(path = "{studentId}")
    public List<Student> getStudentsById(
        @PathVariable("studentId") Long studentId){
        return studentService.getStudentsById(studentId);
    }

    @PostMapping
    public Student registerNewStudent(@RequestBody Student student){
        return studentService.addNewStudent(student);
    }

    @DeleteMapping(path = "{studentId}")
    public void deleteStudent(
            @PathVariable("studentId") Long studentId){
        studentService.deleteStudent(studentId);
    }

    @PutMapping(path = "{studentId}")
    public List<Student> updateStudent(
            @PathVariable("studentId") Long studentId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email){
       return studentService.updateStudent(studentId, name, email);
    }
}

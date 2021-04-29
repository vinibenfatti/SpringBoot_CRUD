package com.example.spring_crud_um.student.controller;

import com.example.spring_crud_um.student.service.StudentService;
import com.example.spring_crud_um.student.model.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/student")
@Slf4j
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getAllStudents() {
       log.info("########## GetAllStudents endpoint was called ##########");
       return studentService.getAllStudents();

    }

    @GetMapping(path = "{studentId}")
    public List<Student> getStudentsById(
        @PathVariable("studentId") Long studentId){
        log.info("########## GetStudentById endpoint was called ##########");
        return studentService.getStudentsById(studentId);
    }

    @PostMapping
    public String registerNewStudent(@RequestBody Student student){
        log.info("########## RegisterNewStudent endpoint was called ##########");
        return studentService.addNewStudent(student) + " posted";
    }

    @DeleteMapping(path = "{studentId}")
    public String deleteStudent(
            @PathVariable("studentId") Long studentId){
        log.info("########## DeleteStudent endpoint was called ##########");
        studentService.deleteStudent(studentId);
        return ("The student with ID:"+ studentId + " deleted");
    }

    @PutMapping(path = "{studentId}")
    public List<Student> updateStudent(
            @PathVariable("studentId") Long studentId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email){
        log.info("########## UpdateStudent endpoint was called ##########");
       return studentService.updateStudent(studentId, name, email);
    }
}

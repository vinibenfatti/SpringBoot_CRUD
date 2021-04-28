package com.example.spring_crud_um.student.service;


import com.example.spring_crud_um.student.repository.StudentRepository;
import com.example.spring_crud_um.student.model.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class StudentService {

    private final StudentRepository studentRepository;


    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    public List<Student> getAllStudents() {
        log.info("Returning GetAllStudent");
       return studentRepository.findAll();
    }

    public List<Student> getStudentsById(Long studentId) {
        boolean exists = studentRepository.existsById(studentId);
        if(!exists){
            throw new IllegalStateException(
                    "Student with id: "+ studentId + " does not exists");
        }
        log.info("Returning FindStudentById");
        return studentRepository.findStudentById(studentId);
    }

    public Student addNewStudent(Student student) {
        Optional<Student> studentOpt = studentRepository
                .findStudentByEmail(student.getEmail());
        if(studentOpt.isPresent()){
            throw new IllegalStateException("Email already exists");
        }
        if (student.getName().isBlank() || student.getEmail().isBlank() || student.getDob()==null) {
            throw new IllegalStateException("Parameter error, bad request");
        }
        else {
            log.info("Saving NewStudent");
            studentRepository.save(student);
            log.info("Returning Student Posted");
            return student;
        }
    }

    public Long deleteStudent(Long studentId) {
        boolean exists = studentRepository.existsById(studentId);
        if(!exists){
            throw new IllegalStateException(
                           "Student with id: "+ studentId + " does not exists");
        }
        log.info("Deleting Student");
        studentRepository.deleteById(studentId);
        log.info("Returning Student Deleted");
        return studentId;
    }

    @Transactional
    public List<Student> updateStudent(Long studentId, String name, String email) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new IllegalStateException(
                "Student with id: "+ studentId + "does not exists"));

        if(name != null && name.length() > 0 && !Objects.equals(student.getName(),name)){
            log.info("Updating Student Name");
            student.setName(name);
        }

        if(email != null && email.length() > 0 && !Objects.equals(student.getEmail(),email)) {
            Optional<Student> studentOptional = studentRepository.findStudentByEmail(email);
            if (studentOptional.isPresent()) {
                throw new IllegalStateException("Email already exists");
            }
            log.info("Updating Student Email");
            student.setEmail(email);

        }
        log.info("Returning Student Updated");
        return this.getStudentsById(studentId);

    }
}

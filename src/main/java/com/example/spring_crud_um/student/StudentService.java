package com.example.spring_crud_um.student;


import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;


    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    public List<Student> getAllStudents() {
       return studentRepository.findAll();
    }

    public List<Student> getStudentsById(Long studentId) {
        boolean exists = studentRepository.existsById(studentId);
        if(!exists){
            throw new IllegalStateException(
                    "Student with id: "+ studentId + " does not exists");
        }
        return studentRepository.findStudentById(studentId);
    }

    public Student addNewStudent(Student student) {
        Optional<Student> studentOpt = studentRepository
                .findStudentByEmail(student.getEmail());
        if(studentOpt.isPresent()){
            throw new IllegalStateException("Email already exists");
        }
        if (!student.getName().isBlank() & !student.getEmail().isBlank() & student.getDob() != null) {
            studentRepository.save(student);
            return student;
        }
            throw new IllegalStateException("Parameter error, bad request");
    }

    public void deleteStudent(Long studentId) {
               boolean exists = studentRepository.existsById(studentId);
               if(!exists){
                   throw new IllegalStateException(
                           "Student with id: "+ studentId + " does not exists");
               }
               studentRepository.deleteById(studentId);
    }

    @Transactional
    public List<Student> updateStudent(Long studentId, String name, String email) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new IllegalStateException(
                "Student with id: "+ studentId + "does not exists"));
        if(name != null && name.length() > 0 && !Objects.equals(student.getName(),name)){
            student.setName(name);
        }

        if(email != null && email.length() > 0 && !Objects.equals(student.getEmail(),email)) {
            Optional<Student> studentOptional = studentRepository.findStudentByEmail(email);
            if (studentOptional.isPresent()) {
                throw new IllegalStateException("Email already exists");
            }
            student.setEmail(email);

        }
        return this.getStudentsById(studentId);

    }
}

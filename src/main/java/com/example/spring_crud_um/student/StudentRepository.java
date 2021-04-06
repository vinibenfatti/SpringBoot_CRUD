package com.example.spring_crud_um.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface StudentRepository
        extends JpaRepository<Student, Long> {

    //  SELECT * FROM student WHERE email = X
    @Query("SELECT s FROM Student s WHERE s.email= ?1 ")
    Optional<Student> findStudentByEmail(String email);
}

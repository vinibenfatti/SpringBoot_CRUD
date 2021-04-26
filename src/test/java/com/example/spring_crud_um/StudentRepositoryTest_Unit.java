package com.example.spring_crud_um;

import com.example.spring_crud_um.student.model.Student;
import com.example.spring_crud_um.student.repository.StudentRepository;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@DataJpaTest
class StudentRepositoryTest_Unit {

    @Autowired
    private StudentRepository underTest;

    @AfterEach
    void tearDOwn(){
        underTest.deleteAll();
    }

    @Test
    void itShouldCheckIfStudentEmailExists() {

        //Given
        String email = "test@gmail.com";
        Student student =new Student (1L,"Test",email, LocalDate.now(),0);
        underTest.save(student);

        //When
        Optional<Student> studentOptional = underTest.findStudentByEmail(email);

        //Then
        Assert.assertNotNull(studentOptional);
        Assert.assertEquals(email,studentOptional.get().getEmail());

    }
    @Test
    void itShouldCheckIfStudentEmailDoesNotExists() {

        //Given
        String email = "test@gmail.com";

        //When
        Optional<Student> studentOptional = underTest.findStudentByEmail(email);

        //Then
        Assertions.assertThat(studentOptional.isEmpty());

    }
    @Test
    void itShouldCheckIfStudentIdExists() {
        //Given
        Long id = 1L;
        Student student =new Student (id,"Test","test@gmail.com", LocalDate.now(),0);
        underTest.save(student);

        //When
        List<Student> studentList= underTest.findStudentById(id);

        //Then
        Assert.assertNotNull(studentList);
        Assert.assertEquals(id,studentList.get(0).getId());
    }
    @Test
    void itShouldCheckIfStudentIdDoesNotExists() {
        //Given
        Long id = 1L;

        //When
        List<Student> studentList= underTest.findStudentById(id);

        //Then
        Assertions.assertThat(studentList.isEmpty());
    }
}
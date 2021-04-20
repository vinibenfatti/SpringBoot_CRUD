package com.example.spring_crud_um;

import com.example.spring_crud_um.student.Student;
import com.example.spring_crud_um.student.StudentRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE )
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository repository;

    @Test
    public void findStudentByEmailTest() {
        //Given
        String emailStudentTest="mariam.jamala@gmail.com";

        //When
        Optional<Student> studentOptional = repository.findStudentByEmail(emailStudentTest);

        //Then
        Assert.assertNotNull(studentOptional);
        Assert.assertEquals(emailStudentTest,studentOptional.get().getEmail());
    }

    @Test
    public void findStudentByIdTest() {
        //Given
        Long idStudentTest=1L;

        //When
        List<Student> studentList = repository.findStudentById(idStudentTest);

        //Then
        Assert.assertNotNull(studentList);
        Assert.assertEquals(idStudentTest,studentList.get(0).getId());
    }
}
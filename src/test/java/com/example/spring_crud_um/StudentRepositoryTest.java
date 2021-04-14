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

import java.util.Optional;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE )
//@ActiveProfile("test")//Para forçar a pegar o profile do DB de teste
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
}
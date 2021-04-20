package com.example.spring_crud_um;

import com.example.spring_crud_um.student.Student;
import com.example.spring_crud_um.student.StudentRepository;
import com.example.spring_crud_um.student.StudentService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE )
public class StudentServiceTest {

    StudentService studentService;

    StudentRepository studentRepository;

    @Test
    public void whenGetAllStudents() {
    }

    @Test
    public void getStudentsById() {
    }

    @Test
    public void addNewStudent() {
    }

    @Test
    public void deleteStudent() {
    }

    @Test
    public void updateStudent() {
    }
}
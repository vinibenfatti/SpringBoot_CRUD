package com.example.spring_crud_um;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.*;

import javax.annotation.PostConstruct;

import com.example.spring_crud_um.student.Student;
import com.example.spring_crud_um.student.StudentRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;



import io.restassured.response.Response;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {

    @LocalServerPort
    private int port;

    private String uri;

    @PostConstruct
    public void init() {
        uri = "http://localhost:" + port;
    }

    @MockBean
    StudentRepository studentRepository;

    @Test
    public void getAllStudents() {



        List<Student> testListStudent = new ArrayList<Student>();
        testListStudent.add(new Student ("Test","test@gmail.com", LocalDate.now()));
        testListStudent.add(new Student ("Test2","test2@gmail.com", LocalDate.now()));
        when(studentRepository.findAll()).thenReturn(testListStudent);

        //Teste de retorno
        //assertThat(studentRepository.findAll()).isEqualTo(testListStudent);

        //Teste de autenticação
        /*given().auth()
                .basic("admin", "admin")
                .when()
                .get(uri +  "/api/v1/student")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());*/


        //List<Student> resultStudent = get(uri +  "/api/v1/student")
        //.then()
        //.assertThat()
        //.statusCode(HttpStatus.UNAUTHORIZED.value())
        //.extract()
        //.as((Type) Student.class);
        //assertThat(resultStudent).isEqualTo(1);
    }

    @Test
    public void registerNewStudent() {
    }

    @Test
    public void deleteStudent() {
    }

    @Test
    public void updateStudent() {
    }
}
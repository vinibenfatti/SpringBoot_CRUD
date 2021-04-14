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
import com.example.spring_crud_um.student.StudentService;
import com.jayway.jsonpath.JsonPath;
import groovy.json.JsonBuilder;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;

import net.minidev.json.JSONObject;
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
    StudentService studentService;

    @Test
    public void getAllStudents() {

        List<Student> testListStudent = new ArrayList<Student>();
        testListStudent.add(new Student (1L,"Test","test@gmail.com", LocalDate.now(),0));
        testListStudent.add(new Student (2L,"Test2","test2@gmail.com", LocalDate.now(),0));
        when(studentService.getStudents()).thenReturn(testListStudent);

        List<Student> resultStudent =
                Arrays.asList(
                        given()
                                .auth()
                                .basic("admin", "admin")
                                .contentType("application/json")
                        .when()
                                .get(uri +  "/api/v1/student")
                        .then()
                                .statusCode(HttpStatus.OK.value())
                                .extract()
                                .as(Student[].class));

        assertThat(resultStudent).isEqualTo(testListStudent);

    }

    @Test
    public void registerNewStudent(){


        //RestAssured.defaultParser = Parser.JSON;

        /*Map<String, String> request = new HashMap<String, String>();
        request.put("id", "5");
        request.put("email", "test@gmail.com");
        request.put("dob", "2000-07-19");
        request.put("age", "0");
        request.put("name", "Test");*/

        JSONObject request = new JSONObject();
        request.put("id", "5");
        request.put("email", "test@gmail.com");
        request.put("dob", "2000-07-19");
        request.put("age", "0");
        request.put("name", "Test");



        /*String request = "{\r\n" +
                "   \"id\":\"5\",\r\n" +
                "   \"email\":\"test@gmail.com\",\r\n" +
                "   \"dob\":\"2000-07-19\",\r\n" +
                "   \"age\":\"0\"\r\n" +
                "   \"name\":\"Test\"\r\n" +
                "}";*/

        System.out.println(request.toString());


        int studentId =
                given()
                        .contentType("application/json")
                        .header("Content-Type", "application/json")
                        .auth()
                        .basic("admin", "admin")
                        .body(request.toString())
                .when()
                        .post(uri +  "/api/v1/student")
                .then()
                        .assertThat()
                        .statusCode(HttpStatus.OK.value())
                        .extract()
                        .path("id");

        assertThat(studentId).isEqualTo(5);


        /*Map<String, Object> data = new HashMap<String, Object>();
        data.put( "name", "Mars" );
        data.put( "age", 32 );
        data.put( "city", "NY" );
        JSONObject json = new JSONObject();
        json.putAll( data );
        System.out.printf( "JSON: %s", json.toString(2) );*/





    }

    @Test
    public void deleteStudent() {
    }

    @Test
    public void updateStudent() {
    }
}
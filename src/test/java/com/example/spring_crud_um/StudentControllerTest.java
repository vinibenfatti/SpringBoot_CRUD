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
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;

import net.minidev.json.JSONObject;
import org.assertj.core.api.Assertions;
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
                        given().log().all()
                                .auth()
                                .basic("admin", "admin")
                                .contentType("application/json")
                        .when().log().all()
                                .get(uri +  "/api/v1/student")
                        .then().log().all()
                                .statusCode(HttpStatus.OK.value())
                                .extract()
                                .as(Student[].class));

        assertThat(resultStudent).isEqualTo(testListStudent);

    }

    @Test
    public void registerNewStudent(){

        Student newStudent = new Student (1L,"Test","test@gmail.com", LocalDate.now(),0);
        Student savedStudent = new Student();
        when(studentService.addNewStudent(newStudent)).thenReturn(newStudent);

        JSONObject request = new JSONObject();
        request.put("id", 5);
        request.put("email", "test@gmail.com");
        request.put("dob", 2000-07-19);
        request.put("age", 0);
        request.put("name", "Test");

       Response response = given().log().all()
                        .headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON)
                        .contentType(ContentType.JSON)
                        .auth()
                        .basic("admin", "admin")
                        .body(request)
        .when()
                        .post(uri +  "/api/v1/student")
        .then().log().all()
                        .assertThat()
                        .statusCode(HttpStatus.OK.value())
                        .extract()
                        .response();




    }

    @Test
    public void deleteStudent() {

        Student testStudent = new Student (1L,"Test","test@gmail.com", LocalDate.now(),0);


        Response response=
                given()
                        .auth()
                        .basic("admin", "admin")
                        .header("Content-type", "application/json")
                .when()
                        .delete(uri +  "/api/v1/student/" + testStudent.getId())
                        .then()
                        .statusCode(HttpStatus.OK.value())
                        .extract()
                        .response();

        Assert.assertEquals(200,response.statusCode());


    }

    @Test
    public void updateStudent() {

        Student testStudent = new Student (1L,"Test","test@gmail.com", LocalDate.now(),0);
        String newName="Vinicius";
        String newEmail="vinicius@gmail.com";

        Response response=
                given()
                        .auth()
                        .basic("admin", "admin")
                        .header("Content-type", "application/json")
                .when()
                        .put(uri +  "/api/v1/student/" + testStudent.getId() +  "?name=" + newName + "&email=" + newEmail)
                        .then()
                        .statusCode(HttpStatus.OK.value())
                        .extract()
                        .response();

        Assert.assertEquals(200,response.statusCode());

    }
}
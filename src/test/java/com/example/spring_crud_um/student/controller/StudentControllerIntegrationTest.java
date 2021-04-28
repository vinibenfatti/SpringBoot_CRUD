package com.example.spring_crud_um.student.controller;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.*;

import javax.annotation.PostConstruct;


import com.example.spring_crud_um.student.model.Student;
import com.example.spring_crud_um.student.service.StudentService;
import io.restassured.http.ContentType;

import net.minidev.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;


import io.restassured.response.Response;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class StudentControllerIntegrationTest {

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
    public void whenGetAllStudentsShouldReturnStatus200() {

        List<Student> testListStudent = new ArrayList<>();
        testListStudent.add(new Student (1L,"Test","test@gmail.com", LocalDate.now(),0));
        testListStudent.add(new Student (2L,"Test2","test2@gmail.com", LocalDate.now(),0));
        when(studentService.getAllStudents()).thenReturn(testListStudent);

        List<Student> resultStudent =
                Arrays.asList(
                        given().log().all()
                                .auth()
                                .basic("admin", "admin")
                        .when().log().all()
                                .get(uri +  "/api/v1/student")
                        .then().log().all()
                                .statusCode(HttpStatus.OK.value())
                                .extract()
                                .as(Student[].class));

        assertThat(resultStudent).isEqualTo(testListStudent);

    }
    @Test
    public void whenGetAllStudentsShouldReturnStatus404() {

                        given().log().all()
                                .auth()
                                .basic("admin", "admin")
                        .when().log().all()
                                .get(uri +  "/api/v1/stud")
                        .then().log().all()
                                .statusCode(HttpStatus.NOT_FOUND.value());
    }
    @Test
    public void whenGetAllStudentsShouldReturnStatus401() {

                        given().log().all()
                                .auth()
                                .basic("", "")
                        .when().log().all()
                                .get(uri +  "/api/v1/student")
                        .then().log().all()
                                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }
    @Test
    public void whenGetStudentsByIdShouldReturnStatus200() {

        List<Student> testListStudent = new ArrayList<>();
        testListStudent.add(new Student (1L,"Test","test@gmail.com", LocalDate.now(),0));
        when(studentService.getStudentsById(1L)).thenReturn(testListStudent);

        List<Student> resultStudent = Arrays.asList(
                        given().log().all()
                                .auth()
                                .basic("admin", "admin")
                                .contentType("application/json")
                        .when().log().all()
                                .get(uri +  "/api/v1/student/"+1L)
                        .then().log().all()
                                .statusCode(HttpStatus.OK.value())
                                .extract()
                                .as(Student[].class));

        assertThat(resultStudent).isEqualTo(testListStudent);

    }
    @Test
    public void whenRegisterNewStudentShouldReturnStatus200(){
        Student testStudent = new Student (1L,"Test","test@gmail.com", LocalDate.now(),0);

        JSONObject request = new JSONObject();
        request.put("id", testStudent.getId());
        request.put("email", testStudent.getEmail());
        request.put("dob", LocalDate.now());
        request.put("age", testStudent.getAge());
        request.put("name", testStudent.getName());

        when(studentService.addNewStudent(testStudent)).thenReturn(testStudent);

       Response response =
               given().log().all()
                        .headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON)
                        .contentType(ContentType.JSON)
                        .auth()
                        .basic("admin", "admin")
                        .body(request)
               .when().log().all()
                        .post(uri +  "/api/v1/student")
               .then().log().all()
                        .assertThat()
                        .extract()
                        .response();

        Assertions.assertEquals(200,response.statusCode());
        Assertions.assertEquals(testStudent + " posted", response.getBody().asString());
    }

    @Test
    public void whenDeleteStudentShouldReturnStatus200() {

        Student testStudent = new Student (1L,"Test","test@gmail.com", LocalDate.now(),0);
        when(studentService.deleteStudent(testStudent.getId())).thenReturn(testStudent.getId());

        Response response=
                given().log().all()
                        .auth()
                        .basic("admin", "admin")
                        .header("Content-type", "application/json")
                .when().log().all()
                        .delete(uri +  "/api/v1/student/" + testStudent.getId())
                        .then()
                        .extract()
                        .response();

        Assertions.assertEquals(200,response.statusCode());
        Assertions.assertEquals("The student with ID:"+ testStudent.getId() + " deleted", response.getBody().asString());

    }

    @Test
    public void whenUpdateStudentShouldReturnStatus200() {

        Student testStudent = new Student (1L,"Test","test@gmail.com", LocalDate.now(),0);
        String newName="Vinicius";
        String newEmail="vinicius@gmail.com";

        List<Student> testListNewStudent = new ArrayList<>();
        testListNewStudent.add(new Student (1L,newName,newEmail, LocalDate.now(),0));

        when(studentService.updateStudent(testStudent.getId(),newName,newEmail))
                .thenReturn(testListNewStudent);

        List<Student> resultStudent =
                Arrays.asList(
                given().log().all()
                        .auth()
                        .basic("admin", "admin")
                        .header("Content-type", "application/json")
                .when().log().all()
                        .put(uri +  "/api/v1/student/" + testStudent.getId() +  "?name=" + newName + "&email=" + newEmail)
                .then()
                        .statusCode(HttpStatus.OK.value())
                        .extract()
                        .as(Student[].class));

        assertThat(resultStudent).isEqualTo(testListNewStudent);

    }
}
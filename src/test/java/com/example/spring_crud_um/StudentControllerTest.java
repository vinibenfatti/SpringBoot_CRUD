package com.example.spring_crud_um;

import com.example.spring_crud_um.student.Student;
import com.example.spring_crud_um.student.StudentService;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.minidev.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

import static io.restassured.RestAssured.given;

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

    @Test
    public void whenRegisterNewStudentShouldReturnStatus200(){

        JSONObject request = new JSONObject();
        request.put("email", "");
        request.put("dob", "2000-07-19");
        request.put("name", "");

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
                .extract()
                .response();

        Assert.assertEquals(200,response.statusCode());
    }

    @Test
    public void whenDeleteStudentShouldReturnStatus200() {

        Student testStudent = new Student (1L,"Test","test@gmail.com", LocalDate.now(),0);

        Response response=
                given()
                        .auth()
                        .basic("admin", "admin")
                        .header("Content-type", "application/json")
                        .when()
                        .delete(uri +  "/api/v1/student/" + testStudent.getId())
                        .then()
                        .extract()
                        .response();

        Assert.assertEquals(200,response.statusCode());

    }

    @Test
    public void whenUpdateStudentShouldReturnStatus200() {

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
                        .extract()
                        .response();

        Assert.assertEquals(200,response.statusCode());

    }
}
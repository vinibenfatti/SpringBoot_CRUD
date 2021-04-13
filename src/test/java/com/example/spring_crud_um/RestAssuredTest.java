package com.example.spring_crud_um;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT)
public class RestAssuredTest {


    @Test
    public void TestGet200Value(){
        given().auth()
                .basic("admin", "admin")
                .when()
                .get("http://localhost:8080/api/v1/student")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());

    }
    @Test
    public void TestGet401Value(){
        given().auth()
                .basic("", "")
                .when()
                .get("http://localhost:8080/api/v1/student")
                .then()
                .assertThat()
                .statusCode(HttpStatus.UNAUTHORIZED.value());

    }
    @Test
    public void TestGet404Value(){
        given().auth()
                .basic("admin", "admin")
                .when()
                .get("http://localhost:8080/api/v1/stud")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());

    }
}

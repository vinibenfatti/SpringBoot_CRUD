package com.example.spring_crud_um.student.controller;

import com.example.spring_crud_um.SpringCrudUmApplication;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;


import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;


@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class BasicAuthenticationIntegrationTest {


    @Test
    public void BasicAuthenticationTest_whenStatusCode_200(){
        given().auth()
                .basic("admin", "admin")
                .when()
                    .get("http://localhost:8080/api/v1/student")
                .then()
                    .assertThat()
                    .statusCode(HttpStatus.OK.value());

    }
    @Test
    public void BasicAuthenticationTest_whenStatusCode_401(){
        given().auth()
                .basic("", "")
                .when()
                .get("http://localhost:8080/api/v1/student")
                .then()
                .assertThat()
                .statusCode(HttpStatus.UNAUTHORIZED.value());

    }
    @Test
    public void BasicAuthenticationTest_whenStatusCode_404(){
        given().auth()
                .basic("admin", "admin")
                .when()
                .get("http://localhost:8080/api/v1/stud")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());

    }
}

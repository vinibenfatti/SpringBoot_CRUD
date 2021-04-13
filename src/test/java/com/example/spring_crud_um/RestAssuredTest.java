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
    public void TestGet(){
        given().auth()
                .basic("admin", "admin")
                .when()
                .get("http://localhost:8080/api/v1/student")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());

    }
}

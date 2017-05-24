package com.example.demo.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(SpringRunner.class)
public class GreetingApiTest {
    @Test
    public void should_get_a_content_with_self_link() throws Exception {
        given().standaloneSetup(new GreetingApi())
            .accept("application/hal+json;charset=UTF-8")
            .when()
            .get("/greeting")
            .prettyPeek()
            .then().statusCode(200)
            .body("content", equalTo("Hello, World"))
            .body("_links.self.href", endsWith("/greeting?name=World"));
    }
}

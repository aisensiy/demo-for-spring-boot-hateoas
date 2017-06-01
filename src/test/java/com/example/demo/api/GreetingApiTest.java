package com.example.demo.api;

import com.example.demo.DemoForSpringBootHateoasApplicationTests;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(SpringRunner.class)
@WebMvcTest(GreetingApi.class)
public class GreetingApiTest {
    @Autowired
    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        RestAssuredMockMvc.mockMvc(mvc);
    }

    @Test
    public void should_get_a_content_with_self_link() throws Exception {
        given()
            .accept("application/hal+json;charset=UTF-8")
            .when()
            .get("/greeting")
            .prettyPeek()
            .then().statusCode(200)
            .body("content", equalTo("Hello, World"))
            .body("_links.self.href", endsWith("/greeting?name=World"));
    }
}

package com.example.demo.api;

import com.example.demo.domain.Order;
import com.example.demo.domain.OrderRepository;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.codehaus.groovy.runtime.InvokerHelper.asList;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(OrderApi.class)
public class OrderApiTest {
    @MockBean
    private OrderRepository orderRepository;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    public void should_get_pay_link_with_new_created_order() throws Exception {
        Order order = new Order("123", asList(new Order.LineItem("product1", 1.22, 1)));
        when(orderRepository.ofId(eq("123"))).thenReturn(Optional.of(order));

        given()
            .accept("application/hal+json")
            .when()
            .get("/orders/{orderId}", order.getId())
            .then().statusCode(200)
            .body("_links.payment.href", endsWith("payment"));

    }

    @Test
    public void should_get_pay_link_with_paid_order() throws Exception {
        Order order = new Order("123", asList(new Order.LineItem("product1", 1.22, 1)));
        order.pay();
        when(orderRepository.ofId(eq("123"))).thenReturn(Optional.of(order));

        given()
            .accept("application/hal+json")
            .when()
            .get("/orders/{orderId}", order.getId())
            .then().statusCode(200)
            .body("_links.payment", nullValue());

    }

    @Test
    public void should_get_order_list() throws Exception {
        Order order = new Order("123", asList(new Order.LineItem("product1", 1.22, 1)));
        when(orderRepository.all()).thenReturn(asList(order));

        given()
            .accept("application/hal+json")
            .when()
            .get("/orders")
            .prettyPeek()
            .then().statusCode(200)
            .body("_links.self.href", endsWith("/orders"));
    }
}

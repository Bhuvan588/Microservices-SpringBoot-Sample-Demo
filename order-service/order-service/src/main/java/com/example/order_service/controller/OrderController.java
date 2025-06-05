package com.example.order_service.controller;


import com.example.order_service.client.UserClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final UserClient userClient;


    public OrderController(UserClient userClient) {
        this.userClient = userClient;
    }

    @GetMapping
    @CircuitBreaker(name="orderService",fallbackMethod = "orderFallBack")
    public String getOrders()
    {
        String user_info_received= userClient.getUsers();
        return "Orders fetched. Also the user info is : " + user_info_received;
    }

    public String orderFallBack(){
        return "User Service is currently unavailable. Please try later!!";
    }
}

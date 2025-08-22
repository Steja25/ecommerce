package com.example.demo.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.dtos.User;

@FeignClient(name = "userservice")
public interface UserClient {
    @GetMapping("/api/auth/users/{id}")
    User getUserById(@PathVariable Long id);
}

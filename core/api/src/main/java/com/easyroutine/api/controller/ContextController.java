package com.easyroutine.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContextController {

    @GetMapping("/context")
    public String context() {
        return "Context is working!";
    }
}

package com.aeribmm.filmcritic.Controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@SecurityRequirement(name = "bearerAuth")
public class CRUDController {

    @GetMapping("")
    public Map<String, String> hello() {
        return Map.of("message", "Hello, Swagger!");
    }
}

package com.aeribmm.filmcritic.Controller;

import com.aeribmm.filmcritic.Model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
public class CRUDController {

    @GetMapping("")
    public Map<String, String> hello() {
        return Map.of("message", "Hello, Swagger!");
    }
}

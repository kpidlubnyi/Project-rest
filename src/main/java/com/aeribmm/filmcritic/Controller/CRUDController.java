package com.aeribmm.filmcritic.Controller;

import com.aeribmm.filmcritic.Model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class CRUDController {

    @GetMapping("")
    public String home(){
        return "TEST";
    }
}

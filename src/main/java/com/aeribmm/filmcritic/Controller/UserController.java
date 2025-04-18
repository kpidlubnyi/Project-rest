package com.aeribmm.filmcritic.Controller;

import com.aeribmm.filmcritic.Exception.UserNotFoundException;
import com.aeribmm.filmcritic.Model.User;
import com.aeribmm.filmcritic.Model.UserDTO;
import com.aeribmm.filmcritic.Service.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@SecurityRequirement(name = "bearerAuth")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{id}")//handled
    public ResponseEntity<UserDTO> getUser(@PathVariable Integer id){
        User user = userService.getUserById(id);
        return ResponseEntity.ok(userService.convertToDTO(user));//status 200
    }

    @GetMapping("/list")
    public ResponseEntity<List<User>> getAll(){
        return new ResponseEntity<>(userService.getAll(),HttpStatus.OK);
    }
    @PostMapping("/create")//method is not using
    public ResponseEntity<Void> createUser(@RequestBody User user){
        userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        User user = userService.getUserById(id);
        if (user != null) {
            userService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id,@RequestBody User updatedUser){
        User existingUser = userService.getUserById(id);
        updatedUser.setId(id);
        userService.createUser(updatedUser);
        return ResponseEntity.ok(updatedUser);
    }
    //todo patch
}

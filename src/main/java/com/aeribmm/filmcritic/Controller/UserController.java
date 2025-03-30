package com.aeribmm.filmcritic.Controller;

import com.aeribmm.filmcritic.Model.User;
import com.aeribmm.filmcritic.Model.UserDTO;
import com.aeribmm.filmcritic.Service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Integer id){
        User user = userService.getUserById(id);
        return ResponseEntity.ok(userService.convertToDTO(user));//status 200
    }

    @GetMapping("/list")
    public ResponseEntity<List<User>> getAll(){
        return ResponseEntity.ok(userService.getAll());
    }
    @PostMapping("/create")
    public ResponseEntity<Void> createUser(@RequestBody User user){
        userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id){
        userService.deleteById(id);
        return ResponseEntity.ok().build();
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

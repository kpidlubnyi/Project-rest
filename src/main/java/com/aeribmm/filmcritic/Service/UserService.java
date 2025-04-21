package com.aeribmm.filmcritic.Service;

import com.aeribmm.filmcritic.DAO.UserRepository;
import com.aeribmm.filmcritic.Exception.userException.UserNotFoundException;
import com.aeribmm.filmcritic.Model.UserModel.User;
import com.aeribmm.filmcritic.Model.UserModel.UserDTO;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User getUserById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new UserNotFoundException("user not found"));
    }

    public UserDTO convertToDTO(User user) {
        return new UserDTO(user.getId(),user.getName(),user.getEmail());
    }

    public void createUser(User user) {
        User newUser = new User();
        System.out.println("old value: " + newUser);
        newUser.setUsername(user.getName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        //todo change password giving to object
        System.out.println("new value: " + newUser);
        repository.save(user);
    }

    public List<User> getAll() {
        return repository.findAll();
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}

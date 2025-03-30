package com.aeribmm.filmcritic.DAO;

import com.aeribmm.filmcritic.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>{
}

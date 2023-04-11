package com.example.licenta.repository;

import com.example.licenta.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {

        @Query("select u from User u where u.role = 'admin'")
        public User findAdminAccount();

        @Query("select u from User u where u.email = ':email'")
        User findUserByEmail(@Param("email") String email);

//        @Query("select u from User u where u.email = ':email' and u.password = ':pass'")
//        User findUserByEmailAndPassword(@Param("email") String email, @Param("pass") String password);

        public User findUserByEmailAndPassword(String email, String password);
}

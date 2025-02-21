package com.team2.demo.domain.user.repository;

import com.team2.demo.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    @Query("SELECT m FROM User m WHERE m.email = :reqEmail")
    User findByEmail(String reqEmail);
}

package com.learning.simran.prod_ready_feature_tutorial.repositories;

import com.learning.simran.prod_ready_feature_tutorial.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}

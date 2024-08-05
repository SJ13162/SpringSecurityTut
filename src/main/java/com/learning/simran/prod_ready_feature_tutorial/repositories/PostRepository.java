package com.learning.simran.prod_ready_feature_tutorial.repositories;

import com.learning.simran.prod_ready_feature_tutorial.entities.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
}

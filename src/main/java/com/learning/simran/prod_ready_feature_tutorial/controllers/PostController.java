package com.learning.simran.prod_ready_feature_tutorial.controllers;

import com.learning.simran.prod_ready_feature_tutorial.entities.PostEntity;
import com.learning.simran.prod_ready_feature_tutorial.entities.User;
import com.learning.simran.prod_ready_feature_tutorial.repositories.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {

    private static final Logger log = LoggerFactory.getLogger(PostController.class);
    private final PostRepository postRepository;

    public PostController(PostRepository postRepository){
        this.postRepository = postRepository;
    }

    @GetMapping
    public ResponseEntity<List<PostEntity>> getPosts(){
        List<PostEntity> entities = postRepository.findAll();
        return new ResponseEntity<>(entities, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostEntity> getPostById(@PathVariable Long id){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        log.info("User: {}", user);

        PostEntity post = postRepository.findById(id).orElseThrow(() -> new RuntimeException());
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PostEntity> savePost(@RequestBody PostEntity postEntity) {
        postRepository.save(postEntity);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<PostEntity> savePost(@RequestBody PostEntity postEntity, @PathVariable Long id) {
        Optional<PostEntity> getPostFromDB = postRepository.findById(id);

        return new ResponseEntity<>(getPostFromDB.map(p -> {
            postEntity.setId(id);
            postRepository.save(postEntity);
            return postEntity;
        }).orElse(null), HttpStatus.CREATED);
    }
}

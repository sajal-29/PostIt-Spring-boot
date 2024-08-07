package com.sajal.rest.webservices.restful_web_services.user;

import com.sajal.rest.webservices.restful_web_services.jpa.UserRepository;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class UserJpaResource {
    private UserRepository repo;
    private PostRepository postRepo;

    public UserJpaResource(UserRepository repo, PostRepository postRepo){

        this.repo = repo;
        this.postRepo = postRepo;
    }
    // GET /users
    @GetMapping("/jpa/users")
    public List<User> retrieveAllUsers(){

        return repo.findAll();
    }

    @GetMapping("/jpa/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable Integer id){
        Optional<User> user = repo.findById(id);
        if(user.isEmpty()){
            throw new UserNotFountException("id:" +id);
        }
        EntityModel<User> entityModel = EntityModel.of(user.get());
        WebMvcLinkBuilder link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(link.withRel("all-users"));

        return entityModel;
    }

    @PostMapping("/jpa/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        User savedUser = repo.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PostMapping("/jpa/users/{id}/posts")
    public ResponseEntity<User> createPostForUser(@PathVariable int id, @Valid @RequestBody Post post){
        Optional<User> user = repo.findById(id);
        if(user.isEmpty()){
            throw new UserNotFountException("id:" +id);
        }
        post.setUser(user.get());
        Post savePost = postRepo.save(post);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savePost.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/jpa/users/{id}")
    public void deleteUser(@PathVariable Integer id){
        repo.deleteById(id);
    }

    @GetMapping("/jpa/users/{id}/posts")
    public List<Post> retrievePostsForUser(@PathVariable Integer id){
        Optional<User> user = repo.findById(id);
        if(user.isEmpty()){
            throw new UserNotFountException("id:" +id);
        }
        System.out.println(id);
        System.out.println(user.get().getPost().toString());
        return user.get().getPost();
    }

    @GetMapping("/jpa/users/{id}/posts/{postId}")
    public Post retrievePostsForUser(@PathVariable Integer id, @PathVariable Integer postId){
        Optional<User> user = repo.findById(id);
        if(user.isEmpty()) {
            throw new UserNotFountException("id:" + id);
        }
        Post post = postRepo.findById(postId).get();

        if(post.getUser().getId().equals(id)){
            return post;
        }
        return null;
    }


}

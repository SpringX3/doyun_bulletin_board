package com.example.demo.controller;

import com.example.demo.entity.Post;
import com.example.demo.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //클래스 안의 메서드들은 REST API용 HTTP 응답을 반환하는 컨트롤러
@RequestMapping("/posts") //어떤 URL과 HTTP 요청을 처리할지 지정
public class PostController {
    private final PostService postService;
    public PostController(PostService postService) {
        this.postService = postService;
    }

    // Create
    @PostMapping
    public ResponseEntity<Post> create(@RequestBody Post post) {
        return ResponseEntity.ok(postService.save(post));
    }

    // Read All
    @GetMapping
    public ResponseEntity<List<Post>> getAll() {
        return ResponseEntity.ok(postService.findAll());
    }

    // Read One
    @GetMapping("/{id}")
    public ResponseEntity<Post> getOne(@PathVariable Long id) {
        return postService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<Post> update(@PathVariable Long id, @RequestBody Post post) {
        return postService.findById(id)
                .map(existing -> {
                    existing.setTitle(post.getTitle());
                    existing.setContent(post.getContent());
                    return ResponseEntity.ok(postService.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        postService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

package com.example.demo.service;

import com.example.demo.entity.Post;
import com.example.demo.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PostServiceTest {

    @Autowired
    private PostRepository postRepository;

    @Test
    void 글_저장_및_조회() {
        // 글 생성
        Post post = new Post("테스트 제목", "테스트 내용");
        Post savedPost = postRepository.save(post);

        // ID 자동 생성 확인
        assertThat(savedPost.getId()).isNotNull();

        // 글 조회
        Optional<Post> found = postRepository.findById(savedPost.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getTitle()).isEqualTo("테스트 제목");
    }

    @Test
    void 글_삭제() {
        Post post = new Post("삭제 제목", "삭제 내용");
        Post savedPost = postRepository.save(post);

        postRepository.deleteById(savedPost.getId());

        Optional<Post> found = postRepository.findById(savedPost.getId());
        assertThat(found).isEmpty();
    }

    @Test
    void 글_전체조회() {
        postRepository.save(new Post("제목1", "내용1"));
        postRepository.save(new Post("제목2", "내용2"));

        List<Post> allPosts = postRepository.findAll();
        assertThat(allPosts.size()).isEqualTo(2);
    }
}

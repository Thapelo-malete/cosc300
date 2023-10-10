package com.cosc300.suicidal.service;

import com.cosc300.suicidal.model.*;
import com.cosc300.suicidal.repository.DislikeRepository;
import com.cosc300.suicidal.repository.LikeRepository;
import com.cosc300.suicidal.repository.PostRepository;
import com.cosc300.suicidal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final DislikeRepository dislikeRepository;

    public void addPost(Post post) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        User user = userRepository.findUserByEmail(
                (
                        (MyUserDetails)(authentication.getPrincipal())
                ).getUsername()
        );
        post.setAuthor(user);
        post.setDateTime(LocalDateTime.now());
        Post savedPost = postRepository.save(post);
    }
    public List<Post> allPosts() {
        return postRepository.findAll();
    }

    public void likePost(Integer id) {
        Post post = postRepository.findById(id).orElseThrow();

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        User user = userRepository.findUserByEmail(
                (
                        (MyUserDetails)(authentication.getPrincipal())
                ).getUsername()
        );

        if (likeRepository.findByAuthorAndPost(user, post) != null) {
            throw new ResponseStatusException(HttpStatus.ALREADY_REPORTED);
        }

        if (dislikeRepository.findByAuthorAndPost(user, post) != null) {
            dislikeRepository.delete(dislikeRepository.findByAuthorAndPost(user, post));
        }

        PostLike like = new PostLike();
        like.setAuthor(user);
        like.setPost(post);

        likeRepository.save(like);
    }

    public void dislikePost(Integer id) {
        Post post = postRepository.findById(id).orElseThrow();

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        User user = userRepository.findUserByEmail(
                (
                        (MyUserDetails)(authentication.getPrincipal())
                ).getUsername()
        );

        if (dislikeRepository.findByAuthorAndPost(user, post) != null) {
            throw new ResponseStatusException(HttpStatus.ALREADY_REPORTED);
        }

        if (likeRepository.findByAuthorAndPost(user, post) != null) {
            likeRepository.delete(likeRepository.findByAuthorAndPost(user, post));
        }

        PostDislike dislike = new PostDislike();
        dislike.setAuthor(user);
        dislike.setPost(post);

        dislikeRepository.save(dislike);
    }
}

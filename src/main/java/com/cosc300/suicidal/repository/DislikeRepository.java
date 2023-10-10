package com.cosc300.suicidal.repository;


import com.cosc300.suicidal.model.Post;
import com.cosc300.suicidal.model.PostDislike;
import com.cosc300.suicidal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DislikeRepository extends JpaRepository<PostDislike, Integer> {
    PostDislike findByAuthorAndPost(User author, Post post);
}

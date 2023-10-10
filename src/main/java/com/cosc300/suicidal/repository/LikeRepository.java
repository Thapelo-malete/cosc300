package com.cosc300.suicidal.repository;

import com.cosc300.suicidal.model.Post;
import com.cosc300.suicidal.model.PostLike;
import com.cosc300.suicidal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<PostLike, Integer> {
    PostLike findByAuthorAndPost(User author, Post post);
}

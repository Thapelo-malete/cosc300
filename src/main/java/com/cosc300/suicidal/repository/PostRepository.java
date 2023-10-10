package com.cosc300.suicidal.repository;

import com.cosc300.suicidal.model.Crime;
import com.cosc300.suicidal.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
}

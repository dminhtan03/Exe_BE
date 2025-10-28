package com.demoProject.demo.service;

import com.demoProject.demo.model.dto.response.PostResponse;
import com.demoProject.demo.model.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {
    Post createPost(String userId, String content, String imageUrl);
     Page<PostResponse> getAllPosts(Pageable pageable);
}

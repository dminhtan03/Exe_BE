package com.demoProject.demo.service.impl;

import com.demoProject.demo.model.dto.response.CommentResponse;
import com.demoProject.demo.model.dto.response.PostResponse;
import com.demoProject.demo.model.entity.Comment;
import com.demoProject.demo.model.entity.Post;
import com.demoProject.demo.model.entity.User;
import com.demoProject.demo.repository.CommentRepository;
import com.demoProject.demo.repository.PostRepository;
import com.demoProject.demo.repository.UserRepository;
import com.demoProject.demo.service.PostService;
import com.demoProject.demo.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private LikeService likeService;

    @Override
    public Post createPost(String userId, String content, String imageUrl) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = new Post();
        post.setUser(user);
        post.setContent(content);
        post.setImageUrl(imageUrl);

        return postRepository.save(post);
    }

     @Override
    public Page<PostResponse> getAllPosts(Pageable pageable) {
        Page<Post> posts = postRepository.findAllByOrderByCreatedAtDesc(pageable);

        return posts.map(post -> {
            PostResponse dto = new PostResponse();
            dto.setId(post.getId());
            dto.setContent(post.getContent());
            dto.setImageUrl(post.getImageUrl());
            dto.setCreatedAt(post.getCreatedAt());

            dto.setUserName(post.getUser().getUserInfo().getFullName());
            dto.setUserAvatar(post.getUser().getUserInfo().getAvatarUrl());

            dto.setTotalLikes(likeService.countLikes(post.getId()));
            dto.setTotalComments(post.getComments().size());

            List<CommentResponse> commentDTOs = post.getComments().stream().map(c -> {
                CommentResponse cdto = new CommentResponse();
                cdto.setId(c.getId());
                cdto.setContent(c.getContent());
                cdto.setCreatedAt(c.getCreatedAt());
                cdto.setUserName(c.getUser().getUserInfo().getFullName());
                cdto.setUserAvatar(c.getUser().getUserInfo().getAvatarUrl());
                return cdto;
            }).collect(Collectors.toList());

            dto.setComments(commentDTOs);
            return dto;
        });
    }
}

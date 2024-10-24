package com.MyBlog2.service;

import com.MyBlog2.payload.PostDto;
import com.MyBlog2.payload.PostResponse;

import java.util.List;

public interface PostService {

    public PostDto createPost(PostDto postDto);

    void deletePostByPostId(int postId);

    PostDto getPostByPostId(int postId);

   PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir);
}

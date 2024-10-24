package com.MyBlog2.service;

import com.MyBlog2.payload.CommentDto;

public interface CommentService {

    CommentDto saveComment(CommentDto commentDto,long postId);


    void deleteByCommentId(long id);

    CommentDto updateByComment(CommentDto commentDto,long id);
}

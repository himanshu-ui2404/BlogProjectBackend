package com.MyBlog2.service.Impl;

import com.MyBlog2.entity.Comment;
import com.MyBlog2.entity.Post;
import com.MyBlog2.exception.ResourceNotFound;
import com.MyBlog2.payload.CommentDto;
import com.MyBlog2.repository.CommentRepository;
import com.MyBlog2.repository.PostRepository;
import com.MyBlog2.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepo;

    @Autowired
    private PostRepository postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto saveComment(CommentDto commentDto, long postId) {
        Post post = postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFound("Comment Not Found with id" + postId)
        );

//       Comment comment = new Comment();
//       comment.setId(commentDto.getId());
//       comment.setName(commentDto.getName());
//       comment.setEmail(commentDto.getEmail());
//       comment.setBody(commentDto.getBody());
//       comment.setPost(post);

        Comment comment = mapToComment(commentDto);
        comment.setPost(post);

        Comment saveComment = commentRepo.save(comment);

        CommentDto dto = new CommentDto();
        dto.setId(saveComment.getId());
        dto.setName(saveComment.getName());
        dto.setEmail(saveComment.getEmail());
        dto.setBody(saveComment.getBody());

        return dto;
    }

    @Override
    public void deleteByCommentId(long id) {
        commentRepo.deleteById(id);
    }

    @Override
    public CommentDto updateByComment(CommentDto commentDto, long id) {

        Comment comment = commentRepo.findById(id).orElseThrow(
                () -> new ResourceNotFound("Comment not Found with id" + id)
        );

        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        Comment saveComment = commentRepo.save(comment);
        CommentDto dto = new CommentDto();
        dto.setId(saveComment.getId());
        dto.setName(saveComment.getName());
        dto.setEmail(saveComment.getEmail());
        dto.setBody(saveComment.getBody());
        dto.setPost(saveComment.getPost());

        return dto;
    }

    public Comment mapToComment(CommentDto dto) {
        Comment comment = modelMapper.map(dto, Comment.class);
//        Comment comment = new Comment();
//        comment.setId(dto.getId());
//        comment.setName(dto.getName());
//        comment.setEmail(dto.getEmail());
//        comment.setBody(dto.getBody());

        return comment;
    }
}
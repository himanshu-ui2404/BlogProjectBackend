package com.MyBlog2.service.Impl;

import com.MyBlog2.entity.Post;
import com.MyBlog2.exception.ResourceNotFound;
import com.MyBlog2.payload.PostDto;
import com.MyBlog2.payload.PostResponse;
import com.MyBlog2.repository.PostRepository;
import com.MyBlog2.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepo;

    public PostServiceImpl(PostRepository postRepo, ModelMapper modelMapper) {
        this.postRepo = postRepo;
        this.modelMapper = modelMapper;
    }

    private ModelMapper modelMapper;



    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = new Post();
        post.setId(postDto.getId());
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        Post saved = postRepo.save(post);
        PostDto dto = new PostDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setDescription(post.getDescription());
        dto.setContent(post.getContent());
        return dto;
    }

    @Override
    public void deletePostByPostId(int postId) {
        Post post = postRepo.findById((long) postId).orElseThrow(
                () -> new ResourceNotFound("Post not Found with id" + postId)
        );


        postRepo.deleteById((long)postId);
    }

    @Override
    public PostDto getPostByPostId(int postId) {

        Post post = postRepo.findById((long) postId).orElseThrow(
                () -> new ResourceNotFound("Post not Found with id" + postId)
        );
        return mapToDto(post);
    }

    @Override
    public PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> all = postRepo.findAll(pageable);
        List<Post> posts = all.getContent();
        List<PostDto> dtos = posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(dtos);
        postResponse.setPageNO(all.getNumber());
        postResponse.setTotalPages(all.getTotalPages());
        postResponse.setTotalElements((int)all.getTotalElements());
        postResponse.setPageSize(all.getSize());
        postResponse.setLast(all.isLast());

        return postResponse;

    }

    PostDto mapToDto(Post post){

        PostDto dto = modelMapper.map(post, PostDto.class);
//        PostDto dto = new PostDto();
//        dto.setId(post.getId());
//        dto.setTitle(post.getTitle());
//        dto.setDescription(post.getDescription());
//        dto.setContent(post.getContent());
        return dto;

    }
}

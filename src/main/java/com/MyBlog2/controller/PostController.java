package com.MyBlog2.controller;

import com.MyBlog2.payload.PostDto;
import com.MyBlog2.payload.PostResponse;
import com.MyBlog2.service.PostService;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {


    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }
    //http://localhost:8080/api/posts

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createPost(
            @Valid @RequestBody PostDto postDto,
            BindingResult result
    ){
        if (result.hasErrors()){
            return  new ResponseEntity<>(result.getFieldError()
                    .getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

        }


        PostDto post = postService.createPost(postDto);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")

    @DeleteMapping("{postId}")
    public ResponseEntity<String> deletePostBypostId(@PathVariable int postId){
        postService.deletePostByPostId(postId);

        return new ResponseEntity<>("Post is delete with id:"+postId,HttpStatus.OK);
    }

    @GetMapping("{postId}")
    public ResponseEntity<PostDto> getPostBypostId(@PathVariable int postId){
        PostDto dto = postService.getPostByPostId(postId);

        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @GetMapping
    public PostResponse getAllPost(

            @RequestParam(value = "pageNo", defaultValue = "0", required = false)  int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir

            ){
        PostResponse postResponse = postService.getAllPost(pageNo,pageSize,sortBy,sortDir);
        return postResponse;
    }

}

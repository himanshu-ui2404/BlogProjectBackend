package com.MyBlog2.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {

    private List<PostDto> content;
    private int pageNO;
    private int pageSize;
    private int TotalElements;
    private int TotalPages;
    private Boolean last;
}

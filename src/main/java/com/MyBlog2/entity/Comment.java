package com.MyBlog2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String email;
    private String body;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_Id",nullable = false)
    private Post post;


}

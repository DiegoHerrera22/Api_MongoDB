package com.semestre.apimongodb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.semestre.apimongodb.model.Blog;
import com.semestre.apimongodb.service.BlogService;

@RestController
@RequestMapping("/blogs")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @PostMapping
    @ResponseStatus(org.springframework.http.HttpStatus.CREATED)
    public Blog createBlog(@RequestBody Blog blog) {
        return blogService.addBlog(blog);
    }

    @GetMapping
    public List<Blog> getAllBlogs() {
        return blogService.findAllBlogs();
    }

    @GetMapping("/{id}")
    public Blog getBlogById(@PathVariable String id) {
        return blogService.findBlogById(id);
    }

    @PutMapping
    public Blog updateBlog(@RequestBody Blog blog) {
        return blogService.updateBlog(blog);
    }

    @DeleteMapping("/{id}")
    public String deleteBlog(@PathVariable String id) {
        return blogService.deleteBlog(id);
    }
}
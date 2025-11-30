package com.semestre.apimongodb.service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.semestre.apimongodb.model.Blog;
import com.semestre.apimongodb.repository.BlogRepository;

@Service
public class BlogService {

    @Autowired
    private BlogRepository blogRepository;

    public Blog addBlog(Blog blog) {
        blog.setId(UUID.randomUUID().toString().split("-")[0]);
        if (blog.getPublishedAt() == null) {
            blog.setPublishedAt(Instant.now());
        }
        return blogRepository.save(blog);
    }

    public List<Blog> findAllBlogs() {
        return blogRepository.findAll();
    }

    public Blog findBlogById(String id) {
        return blogRepository.findById(id).orElse(null);
    }

    public Blog updateBlog(Blog blog) {
        Blog existingBlog = blogRepository.findById(blog.getId()).orElse(null);
        if (existingBlog != null) {
            existingBlog.setTitle(blog.getTitle());
            existingBlog.setContent(blog.getContent());
            existingBlog.setAuthorId(blog.getAuthorId());
            existingBlog.setPublishedAt(blog.getPublishedAt());
            return blogRepository.save(existingBlog);
        }
        return null;
    }

    public String deleteBlog(String id) {
        blogRepository.deleteById(id);
        return "Blog with id " + id + " has been deleted.";
    }
}
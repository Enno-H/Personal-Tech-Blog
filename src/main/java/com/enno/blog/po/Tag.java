package com.enno.blog.po;

import com.sun.org.glassfish.gmbal.ManagedAttribute;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_tag")
public class Tag {

    @Id
    @GeneratedValue
    private Long Id;
    private String name;

    @ManyToMany(mappedBy = "tags")
    private List<Blog> blogs =  new ArrayList<>();

    public Tag() {
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                '}';
    }
}

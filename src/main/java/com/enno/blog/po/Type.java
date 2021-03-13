package com.enno.blog.po;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TABLE_TYPE")
public class Type {

    @Id
    @GeneratedValue
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @Column(unique=true)
    @NotBlank(message = "Type cannot be empty.")
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "type", cascade = CascadeType.REMOVE) // if removing a type, all blogs of this type will be removed.
    private List<Blog> blogs = new ArrayList<>();

    public Type() {
    }

    public Type(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    @JsonIgnore
    public void setId(Long id) {
        this.id = id;
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
        return "Type{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

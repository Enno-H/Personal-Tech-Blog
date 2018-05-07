package com.enno.blog.service;

import com.enno.blog.po.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {

    Tag saveTag(Tag tag);

    Tag getTag(Long id);

    Page<Tag> listTag(Pageable pageable);

    List<Tag> listTag();

    List<Tag> listTag(String id);

    Tag updateTag(Long id, Tag tag);

    Tag getTagByName(String name);

    void deleteTag(Long id);



}

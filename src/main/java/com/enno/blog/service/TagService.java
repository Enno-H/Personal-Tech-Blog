package com.enno.blog.service;

import com.enno.blog.po.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TagService {

    Tag saveTag(Tag tag);

    Tag getTag(Long id);

    Page<Tag> listTag(Pageable pageable);

    Tag updateTag(Long id, Tag tag);

    Tag getTagByName(String name);

    void deleteTag(Long id);



}

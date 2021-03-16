package com.enno.blog.service;

import com.enno.blog.NotFoundException;
import com.enno.blog.dao.TagRepository;
import com.enno.blog.handler.TagNotFoundException;
import com.enno.blog.po.Tag;
import com.enno.blog.po.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImp implements TagService{

    private final TagRepository tagRepository;

    public TagServiceImp(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public Tag getTag(Long id) {
        return tagRepository.findById(id)
                .orElseThrow(() -> new TagNotFoundException(id));
    }

    @Override
    public List<Tag> listTag() {
        return tagRepository.findAll();
    }

    @Override
    public List<Tag> listTag(String ids) {
        return tagRepository.findAllById(convertToList(ids));
    }

    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Override
    public List<Tag> listTagTop(Integer size) {
        Pageable pageable = PageRequest.of(0, size, Sort.by("blogs.size").descending());
        return tagRepository.findTop(pageable);
    }

    private List<Long> convertToList(String ids) {
        List<Long> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null) {
            String[] idList = ids.split(",");
            for (String s : idList) {
                list.add(Long.parseLong(s));
            }
        }
        return list;
    }

    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag t = tagRepository.getOne(id);
        if(t == null){
            throw new NotFoundException("The tag does not exist.");
        }
        BeanUtils.copyProperties(tag, t);
        return tagRepository.save(t);
    }

    @Override
    public Tag getTagByName(String name) {
        return tagRepository.findByName(name);
    }

    @Override
    public Boolean existsTagByName(String name) {
        return tagRepository.existsTagByName(name);
    }

    @Override
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }

    public ArrayList<Tag> getOrElseCreateTagList(ArrayList<String> tagNameList){
        ArrayList<Tag> tagList = new ArrayList<>();
        for(String tagName : tagNameList){
            Tag tag;
            if(this.existsTagByName(tagName)){
                tag = this.getTagByName(tagName);
            }
            else {
                tag = this.saveTag(new Tag(tagName));
            }
            tagList.add(tag);
        }
        return tagList;
    }
}

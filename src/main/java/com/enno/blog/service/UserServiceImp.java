package com.enno.blog.service;

import com.enno.blog.dao.UserRepository;
import com.enno.blog.po.User;
import com.enno.blog.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkuser(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username,MD5Utils.code(password));
        return user;
    }
}

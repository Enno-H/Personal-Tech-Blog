package com.enno.blog.service;

import com.enno.blog.po.User;

public interface UserService {

    User checkuser(String username, String password);
}

package com.enno.blog;

import com.enno.blog.dao.UserRepository;
import com.enno.blog.po.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(UserRepository repository) {

        return args -> {
            log.info("Preloading " + repository.save(new User("enno", "password", "enno@gmail.com", 0, "href1", "enno")));
            log.info("Preloading " + repository.save(new User("root", "root", "root@gmail.com", 0, "href2", "root")));
        };
    }
}
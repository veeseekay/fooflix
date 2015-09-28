package com.bar.fooflix.services;


import com.bar.fooflix.entities.User;
import com.bar.fooflix.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsersService {

    private static final Logger LOG = LoggerFactory.getLogger(UsersService.class);

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User getUser(String id) throws Exception {
        //TODO
        return null;
    }
}

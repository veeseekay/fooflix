package com.bar.fooflix.services;


import com.bar.fooflix.entities.User;
import com.bar.fooflix.exceptions.ExceptionType;
import com.bar.fooflix.exceptions.FooflixException;
import com.bar.fooflix.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsersService {

    private static final Logger LOG = LoggerFactory.getLogger(UsersService.class);

    @Autowired
    private UserRepository userRepository;

    public User getUser(String login) throws Exception {
        User user = userRepository.findByLogin(login);
        if(user == null) {
            throw new FooflixException("User not found").setType(ExceptionType.NOT_FOUND);
        }
        return user;
    }

    @Transactional
    public User addUser(User user) throws Exception {
        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(User user) throws Exception {
        User repoUser = userRepository.findByLogin(user.getLogin());

        if(repoUser == null) {
            throw new FooflixException("User not found").setType(ExceptionType.NOT_FOUND);
        }
        repoUser.setName(user.getName());
        return userRepository.save(repoUser);
    }

    public Page<User> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Transactional
    public Iterable<User> addUsers(List<User> users) {
        return userRepository.save(users);
    }

    @Transactional
    public Iterable<User> updateUsers(List<User> users) {
        for (User user : users) {
            User repoUser = userRepository.findByLogin(user.getLogin());

            if (repoUser != null) {
                repoUser.setName(user.getName());
                userRepository.save(repoUser);
            } else {
                LOG.error("User {} not found", user.getLogin());
            }
        }
        return users;
    }
}

    package com.datagroup.eslstest.serviceimpl;

import com.datagroup.eslstest.dao.UserDao;
import com.datagroup.eslstest.entity.User;
import com.datagroup.eslstest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service("UserService")
public class UserServiceImpl extends BaseServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public User findByName(String name) {
        return userDao.findByName(name);
    }

    @Override
    public User findById(Long id) {
        Optional<User> user = userDao.findById(id);
        if(user.isPresent())
            return userDao.findById(id).get();
        else
            return null;
    }
}

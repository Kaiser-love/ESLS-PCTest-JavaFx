package com.datagroup.eslstest.service;


import com.datagroup.eslstest.entity.*;

import java.util.List;

public interface UserService extends Service{
    User findByName(String name);
    User findById(Long id);
}

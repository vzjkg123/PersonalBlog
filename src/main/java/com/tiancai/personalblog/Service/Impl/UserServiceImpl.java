package com.tiancai.personalblog.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tiancai.personalblog.Dao.UserDao;
import com.tiancai.personalblog.Entity.User;
import com.tiancai.personalblog.Service.IUserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements IUserService {
}

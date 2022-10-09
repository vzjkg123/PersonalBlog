package com.tiancai.personalblog.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tiancai.personalblog.Entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao extends BaseMapper<User> {
}

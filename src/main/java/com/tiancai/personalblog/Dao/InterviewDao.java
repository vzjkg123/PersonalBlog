package com.tiancai.personalblog.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tiancai.personalblog.Entity.Interview;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InterviewDao extends BaseMapper<Interview> {
}

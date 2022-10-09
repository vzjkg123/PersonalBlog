package com.tiancai.personalblog.Dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tiancai.personalblog.Entity.Diary;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DiaryDao extends BaseMapper<Diary> {
}

package com.tiancai.personalblog.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tiancai.personalblog.Dao.StarDao;
import com.tiancai.personalblog.Entity.Star;
import com.tiancai.personalblog.Service.IStarService;
import org.springframework.stereotype.Service;

@Service
public class StarServiceImpl extends ServiceImpl<StarDao, Star> implements IStarService{

}

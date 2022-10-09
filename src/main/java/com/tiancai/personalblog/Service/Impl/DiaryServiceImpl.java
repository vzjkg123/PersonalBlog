package com.tiancai.personalblog.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tiancai.personalblog.Dao.DiaryDao;
import com.tiancai.personalblog.Entity.Diary;
import com.tiancai.personalblog.Service.IDiaryService;
import org.springframework.stereotype.Service;

@Service
public class DiaryServiceImpl extends ServiceImpl<DiaryDao, Diary> implements IDiaryService {
}

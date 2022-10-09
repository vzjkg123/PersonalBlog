package com.tiancai.personalblog.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tiancai.personalblog.Dao.InterviewDao;
import com.tiancai.personalblog.Entity.Interview;
import com.tiancai.personalblog.Service.IInterviewService;
import org.springframework.stereotype.Service;

@Service
public class InterviewServiceImpl extends ServiceImpl<InterviewDao, Interview> implements IInterviewService {
}

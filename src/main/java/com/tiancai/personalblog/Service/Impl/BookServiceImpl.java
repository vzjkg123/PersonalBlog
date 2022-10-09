package com.tiancai.personalblog.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tiancai.personalblog.Dao.BookDao;
import com.tiancai.personalblog.Entity.Book;
import com.tiancai.personalblog.Service.IBookService;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl extends ServiceImpl<BookDao, Book> implements IBookService {
}

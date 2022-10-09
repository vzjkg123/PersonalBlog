package com.tiancai.personalblog.Controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tiancai.personalblog.Entity.User;
import com.tiancai.personalblog.Service.ISendMailService;
import com.tiancai.personalblog.Service.IUserService;
import com.tiancai.personalblog.Utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RequestMapping("email")
@RestController
public class EmailController {
    @Autowired
    private ISendMailService sendMailService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private IUserService iUserService;

    @CrossOrigin
    @PostMapping    //status   注册账号：1，  修改密码： 2
    public R testifyEmail(@RequestParam String email, @RequestParam String status) {
        if (status.equals("1") && isExist(email))
            return new R(false, "账号已经存在");

        if (status.equals("2") && !isExist(email))
            return new R(false, "账号不存在");

        String code = UUID.randomUUID().toString().substring(0, 7);
        stringRedisTemplate.opsForValue().set(email, code, 10, TimeUnit.MINUTES);
        sendMailService.sendMail(email, code, status);
        return new R(true, null);
    }

    public boolean isExist(String account) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("account", account);
        wrapper.or().eq("email", account);
        User temp = iUserService.getOne(wrapper);
        return temp != null;
    }
}

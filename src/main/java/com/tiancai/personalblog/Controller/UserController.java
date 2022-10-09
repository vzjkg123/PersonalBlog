package com.tiancai.personalblog.Controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.tiancai.personalblog.Entity.Interview;
import com.tiancai.personalblog.Entity.Star;
import com.tiancai.personalblog.Entity.User;
import com.tiancai.personalblog.Service.IInterviewService;
import com.tiancai.personalblog.Service.IStarService;
import com.tiancai.personalblog.Service.IUserService;
import com.tiancai.personalblog.Utils.R;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private IStarService iStarService;
    @Autowired
    private IInterviewService iInterviewService;

    @CrossOrigin
    @PostMapping("login")
    public R login(@RequestParam String account, @RequestParam String password) {
        try {
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq("account", account);
            wrapper.or().eq("email", account);
            User temp = iUserService.getOne(wrapper);
            if (temp.getPassword().equals(password)) {
                String token = setToken(account);
                HashMap<String, String> res = new HashMap<>();
                res.put("token", token);
                return new R(true, res);
            } else {
                return new R(false, null);
            }
        } catch (NullPointerException e) {
            return new R(false, "notExist");
        } catch (Exception e) {
            e.printStackTrace();
            return new R(false, "error");
        }

    }

    @CrossOrigin
    @PostMapping("name")
    public R modifyUserName(@RequestParam String email, @RequestParam String code, @RequestParam String name) {
        try {
            if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(email)) && AuthenticationAndDel(email, code)) {
                UpdateWrapper<User> wrapper = new UpdateWrapper<>();
                wrapper.eq("email", email).set("name", name);
                iUserService.update(wrapper);
                return new R(true, "success");
            } else return new R(false, null);
        } catch (Exception e) {
            return new R(false, e.toString());
        }
    }


    @CrossOrigin
    @PostMapping("register")
    public R register(@RequestParam String name, @RequestParam String email, @RequestParam String code, @RequestParam String password) {
        if (!AuthenticationAndDel(email, code))
            return new R(false, "codeError");

        iUserService.save(new User(name, password, email));
        return new R(true, "success");

    }

    public String setToken(String id) {
        String token = UUID.randomUUID().toString();
        stringRedisTemplate.opsForValue().set(id, token, 30, TimeUnit.DAYS);
        return token;
    }

    @CrossOrigin
    @PostMapping
    public R Authentication(@RequestParam String account, @RequestParam String token) {
        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(account)) && Objects.equals(stringRedisTemplate.opsForValue().get(account), token)) {
            return new R(true, null);
        } else {
            return new R(false, null);
        }
    }

    public boolean AuthenticationAndDel(String id, String code) {
        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(id)) && Objects.equals(stringRedisTemplate.opsForValue().get(id), code)) {
            stringRedisTemplate.delete(id);
            return true;
        } else return false;

    }

    public boolean AuthenticationForInner(@RequestParam String account, @RequestParam String token) {
        return Boolean.TRUE.equals(stringRedisTemplate.hasKey(account)) && Objects.equals(stringRedisTemplate.opsForValue().get(account), token);
    }

    @CrossOrigin
    @PostMapping("update")
    public R updatePassword(@RequestParam String email, @RequestParam String code, @RequestParam String password) {
        try {
            if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(email)) && AuthenticationAndDel(email, code)) {
                UpdateWrapper<User> wrapper = new UpdateWrapper<>();
                wrapper.eq("email", email).set("password", password);
                iUserService.update(wrapper);
                return new R(true, "success");
            } else {
                return new R(false, "codeError");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new R(false, "interError");
        }
    }

    @CrossOrigin
    @PostMapping("get")
    public R getPersonalInformation(@RequestParam String account, @RequestParam String code) {
        if (AuthenticationForInner(account, code)) {
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq("account", account);
            wrapper.or().eq("email", account);
            User temp = iUserService.getOne(wrapper);
            temp.setPassword("forbid");
            temp.setTel("");
            return new R(true, temp);
        }
        return new R(false, "notFound");
    }


    @CrossOrigin
    @PostMapping("starStatus")
    public R getStar(@RequestParam String email, @RequestParam String code, @RequestParam int page) {
        System.out.println(email + ":" + code + ":" + page);
        try {
            if (AuthenticationForInner(email, code)) {
                QueryWrapper<Star> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("email", email).eq("page", page);
                if (iStarService.getOne(queryWrapper) != null) {
                    return new R(true, null);
                }
            }
        } catch (Exception e) {
            return new R(false, e.toString());
        }

        return new R(false, null);
    }

    @CrossOrigin
    @PostMapping("starList")
    public R getStarList(@RequestParam String email, @RequestParam String code) {
        System.out.println("email:"+email+"\ncode"+code);
        try {
            if (AuthenticationForInner(email, code)) {
                QueryWrapper<Star> queryForPage = new QueryWrapper<>();
                queryForPage.select("page").eq("email", email);
                QueryWrapper<Interview> queryForInterview = new QueryWrapper<>();
                queryForInterview.select("id", "title", "url");
                System.out.println(iStarService.list(queryForPage).toString());
                for (Star e : iStarService.list(queryForPage)) {
                    queryForInterview.or().eq("id", e.getPage());
                }


                List<HashMap<String, Object>> response = new ArrayList<>();

                for (Interview e : iInterviewService.list(queryForInterview)) {
                    HashMap<String, Object> res = new HashMap<>();
                    res.put("url", e.getUrl());
                    res.put("id", e.getId());
                    res.put("title", e.getTitle());
                    res.put("status","ok");
                    response.add(res);
                }
                return new R(true, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new R(false, "InnerError");
        }
        System.out.println(email+":"+code+AuthenticationForInner(email, code));
        return new R(false, null);
    }

    @CrossOrigin
    @PostMapping("star")
    public R addStar(@RequestParam String email, @RequestParam String code, @RequestParam int page) {
        try {
            if (AuthenticationForInner(email, code)) {
                iStarService.save(new Star(page, email));
                return new R(true, null);
            }
        } catch (Exception e) {
            return new R(false, e.toString());
        }
        return new R(false, null);
    }

    @CrossOrigin
    @PostMapping("instar")
    public R delStar(@RequestParam String email, @RequestParam String code, @RequestParam int page) {
        try {
            if (AuthenticationForInner(email, code)) {
                QueryWrapper<Star> qw = new QueryWrapper<>();
                qw.eq("email", email).eq("page", page);
                iStarService.remove(qw);
                return new R(true, null);
            }
        } catch (Exception e) {
            return new R(false, e.toString());
        }
        return new R(false, null);
    }
}

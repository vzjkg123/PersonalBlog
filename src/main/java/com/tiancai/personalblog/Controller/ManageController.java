package com.tiancai.personalblog.Controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.tiancai.personalblog.Entity.Diary;
import com.tiancai.personalblog.Entity.Interview;
import com.tiancai.personalblog.Entity.User;
import com.tiancai.personalblog.Service.IDiaryService;
import com.tiancai.personalblog.Service.IInterviewService;
import com.tiancai.personalblog.Service.IUserService;
import com.tiancai.personalblog.Utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.*;

@RestController
@RequestMapping("administrator")
public class ManageController {
    @Autowired
    private IUserService iUserService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private IInterviewService iInterviewService;

    @Autowired
    private IDiaryService iDiaryService;

    private boolean AuthenticationForAdministrator(String email, String code) {
        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(email)) && Objects.equals(stringRedisTemplate.opsForValue().get(email), code)) {
            QueryWrapper<User> qw = new QueryWrapper<>();
            qw.select("role").eq("email", email);
            User user = iUserService.getOne(qw);
            return user.getRole().equals("root");
        }
        return true;
    }


    @CrossOrigin
    @PostMapping
    public R getTables(@RequestParam String email, @RequestParam String code) {
        if (AuthenticationForAdministrator(email, code)) {
            HashMap<String, Object> response = new HashMap<>();
            try {
                response.put("users", iUserService.list());
                response.put("interviews", iInterviewService.list());
                return new R(true, response);
            } catch (Exception e) {
                e.printStackTrace();
                return new R(false, e.toString());
            }
        } else {
            return new R(false, "Permission denied");
        }
    }


    /*
    PathVariable
    1.删除用户
    2.删除文章
     */
    @CrossOrigin
    @PostMapping("article")
    public R delArticle(@RequestParam String id, @RequestParam String email, @RequestParam String code) {
        if (AuthenticationForAdministrator(email, code)) {
            try {
                QueryWrapper<Interview> qw = new QueryWrapper<>();
                qw.eq("id", id);
                Interview temp = iInterviewService.getById(id);
                String name = temp.getUuid() + ".md";
                File f = new File("var/www/interview/" + name);
                if (f.exists()) f.delete();
                return new R(iInterviewService.removeById(id), null);
            } catch (Exception e) {
                return new R(false, e.toString());
            }
        } else {
            return new R(false, "Permission denied");
        }
    }

    @CrossOrigin
    @PostMapping("delUser")
    public R delUser(@RequestParam String id, @RequestParam String email, @RequestParam String code) {
        if (AuthenticationForAdministrator(email, code)) {
            return new R(iUserService.removeById(id), null);
        }
        return new R(false, "Permission denied");
    }

    @CrossOrigin
    @PostMapping("addUser")
    public R addUser(@RequestParam String email, @RequestParam String code, @RequestParam String userName, @RequestParam String userPassword,
                     @RequestParam String userEmail, @RequestParam String userRole) {
        if (AuthenticationForAdministrator(email, code)) {
            try {
                return new R(iUserService.save(new User(userName, userPassword, userEmail, userRole)), null);
            } catch (Exception e) {
                return new R(false, e.toString());
            }
        }
        return new R(false, "Permission denied");
    }

    @CrossOrigin
    @PostMapping("updateUser")//id指要更新的用户
    public R updateUser(@RequestParam String email, @RequestParam String code, @RequestParam String id, @RequestParam String password) {
        if (AuthenticationForAdministrator(email, code))
            try {
                UpdateWrapper<User> uw = new UpdateWrapper<>();
                uw.set("password", password).eq("account", id);
                return new R(iUserService.update(uw), null);
            } catch (Exception e) {
                return new R(false, e.toString());
            }
        return new R(false, "Permission denied");
    }

    @CrossOrigin
    @PostMapping("addDiary")
    public R addDiary(@RequestParam String email, @RequestParam String code, @RequestParam String des) {
        if (AuthenticationForAdministrator(email, code))
            try {
                return new R(iDiaryService.save(new Diary(des)), null);

            } catch (Exception e) {
                return new R(false, e.toString());
            }
        return new R(false, "Permission denied");
    }


}

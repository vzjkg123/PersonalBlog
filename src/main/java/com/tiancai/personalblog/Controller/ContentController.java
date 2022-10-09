package com.tiancai.personalblog.Controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tiancai.personalblog.Entity.Interview;
import com.tiancai.personalblog.Service.IInterviewService;
import com.tiancai.personalblog.Utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/article")
public class ContentController {

    @Autowired
    private IInterviewService iInterviewService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    public boolean AuthenticationForPost(@RequestParam String account, @RequestParam String token) {
        return Boolean.TRUE.equals(stringRedisTemplate.hasKey(account)) && Objects.equals(stringRedisTemplate.opsForValue().get(account), token);
    }

    @PostMapping
    @CrossOrigin
    public R postArticle(@RequestParam String email, @RequestParam String code, @RequestParam String title, @RequestParam String text) {
        String uid = UUID.randomUUID().toString();
        try {
            if (AuthenticationForPost(email, code)) {
                String digest;
                digest = text.length() <= 40 ? text : text.substring(0, 40);
                if (text.length() < 20)
                    digest = text;
                String name = uid + ".md";
                Interview temp = new Interview(null, "https://www.ltcnb.top/essays/" + name, title, digest, LocalDate.now().toString(), null, uid, email);
                if (iInterviewService.save(temp)) {
                    File f = new File("/var/www/interview/" + name);///Users/tiancai/Downloads/
                    if (!f.createNewFile()) return new R(false, "InnerError_Create");
                    try (FileWriter fw = new FileWriter(f)) {
                        fw.write(text);
                    } catch (Exception e) {
                        return new R(false, "InnerError_write");
                    }
                    return new R(true, "success");
                }
            } else return new R(false, "AuthenticationError");
        } catch (Exception e) {
            QueryWrapper<Interview> qw = new QueryWrapper<>();
            qw.eq("uuid", uid);
            iInterviewService.remove(qw);
            return new R(false, e.toString());
        }
        return new R(false, null);
    }

}

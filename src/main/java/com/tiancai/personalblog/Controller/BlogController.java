package com.tiancai.personalblog.Controller;


import com.tiancai.personalblog.Utils.R;
import com.tiancai.personalblog.Entity.Interview;
import com.tiancai.personalblog.Service.IBookService;
import com.tiancai.personalblog.Service.IDiaryService;
import com.tiancai.personalblog.Service.IInterviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/data")
public class BlogController {

    @Autowired
    private IInterviewService iInterviewService;
    @Autowired
    private IDiaryService iDiaryService;
    @Autowired
    private IBookService iBookService;



    @CrossOrigin
    @DeleteMapping("{id}")
    public R delArticle(@PathVariable int id) {
        iInterviewService.removeById(id);
        return new R(true, null);
    }

    @CrossOrigin
    @GetMapping
    public R getAll() {
        return new R(true, iInterviewService.list());
    }

    @CrossOrigin
    @PostMapping("/suggestion")
    public R putSuggestion(@RequestBody String suggestion) {
        try {
            File f = new File("/webpack/suggestion.txt");
            if (!f.exists()) {
                if(!f.createNewFile())
                    return new R(false,null);
            }
            FileWriter fw = new FileWriter(f, true);
            fw.append(suggestion);
            fw.close();

        } catch (Exception e) {
            e.printStackTrace();
            return new R(false, "出错了，请以后再试！");
        }
        Map<String, String> map = new HashMap<>();
        map.put("msg", "感谢您的宝贵意见！");
        return new R(true, map);
    }

    @GetMapping("/diary")
    @CrossOrigin
    public R getDiary() {
        return new R(true, iDiaryService.list());
    }

    //点赞
    @PutMapping("/traffic")
    @CrossOrigin
    public R addTraffic(@RequestBody Integer id) {
        Interview temp = iInterviewService.getById(id);
        final int i = temp.getTraffic() + 1;
        temp.setTraffic(i);
        iInterviewService.updateById(temp);
        return new R(true, null);
    }

    @CrossOrigin
    @GetMapping("/books")
    public R getAllBooks() {
        return new R(true, iBookService.list());
    }
}

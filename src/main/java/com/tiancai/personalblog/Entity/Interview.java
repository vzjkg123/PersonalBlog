package com.tiancai.personalblog.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class Interview {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String url;
    private String title;
    private String digest;
    private String change_time;
    private Integer traffic;
    private String uuid;
    private String poster;
    public Interview(Integer id,String title,String url,String poster){
        this.id = id;
        this.title = title;
        this.url = url;
        this.poster = poster;
    }
    public Interview(Integer id,String title,String url){
        this.id = id;
        this.title = title;
        this.url = url;
    }
}
